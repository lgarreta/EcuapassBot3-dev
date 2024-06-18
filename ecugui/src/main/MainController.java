package main;

import config.SettingsController;
import config.FeedbackView;
import documento.DocModel;
import documento.DocRecord;
import exceptions.EcuapassExceptions;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import widgets.ImageViewLens;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import org.json.simple.parser.ParseException;
import results.ResultsController;
import widgets.ProgressDialog;
import workers.ServerWorker;

public class MainController extends Controller {

	String appRelease = "0.903"; //Improved GUI responsiveness using InvokeLater 
	// String appRelease = "0.902"; // Redesigned: three threads for GUI, Server, and Webdrive
	// String appRelease = "0.87"; //One app: GUI calling Server and Server calling Webdrive

	DocModel doc;             // Handles invoice data: selected, processed, and no procesed
	MainView mainView;
	InputsView inputsView;
	FeedbackView feedbackView;
	JTabbedPane tabsPanel;
	ImageViewLens imageView;
	//ResultsView resultsView;
	ResultsController resultsController;
	ServerWorker serverWorker;          // Client for sending messages to python server
	ProgressDialog progressDialog;     // Dialog showed when document processing starts

	//SettingsController settingsController; // Initial configuration parameters
	SettingsController configController;
	Timer timer;
	// Current selected file from FileChooser
	File selectedFile = null;

	public MainController () {
		try {
			System.setProperty ("file.encoding", "UTF-8");
			doc = new DocModel ();
			doc.initGlobalPaths ();
			initializeComponents ();
		} catch (Exception ex) {
			ex.printStackTrace ();
			Logger.getLogger (MainController.class.getName ()).log (Level.SEVERE, null, ex);
		}
		out (">>>>>>>>>>>>>>>> GUI version: " + appRelease + " <<<<<<<<<<<<<<<<<<<<");
	}

	public JFrame getMainView () {
		return mainView;
	}

	// Add the views to this Frame
	private void initializeComponents () {
		try {
			// Main views
			mainView = new MainView ();
			mainView.setController (this);
			mainView.setVisible (true);
			mainView.setDefaultCloseOperation (WindowConstants.DO_NOTHING_ON_CLOSE);

			// Inital configuration settings
			configController = new SettingsController (this);
			if (configController.initSettings (mainView) == null) {
				System.out.println ("+++ Settings nulo");
				System.exit (-1);
			}
			DocModel.companyName = configController.getSettingsValue ("empresa");
			feedbackView = configController.feedbackView;

			// Init InputsView
			inputsView = new InputsView ();
			inputsView.setController (this);
			tabsPanel = mainView.createTabs ();
			tabsPanel.addTab ("Entradas:", inputsView);
			tabsPanel.addTab ("Mensajes", feedbackView);

			// Get components from views
			imageView = inputsView.getImageView ();

			doc.printGlobalPaths (this);

			// Server worker
			serverWorker = new ServerWorker (this, doc);
			serverWorker.copyResourcesToTempDir ();
			serverWorker.waitForPortFile ();
			serverWorker.execute ();
		} catch (Exception ex) {
			ex.printStackTrace ();
		}
	}

	// Start document processing after button pressed in InpusView
	@Override
	public void onStartProcessing () {
		// Start resultsController
		if (resultsController != null)
			tabsPanel.remove (resultsController.resultsView);
		resultsController = new ResultsController (this, doc, serverWorker);
		tabsPanel.addTab ("Resultados", resultsController.resultsView);
		// Call to server to start processing documents
		String docFilepath = serverWorker.copyDocToProjectsDir (doc.currentRecord);
		DocRecord docRecord = doc.currentRecord;
		new Thread (() -> {
			try {
				serverWorker.startProcess ("doc_processing", docFilepath, DocModel.runningPath);
			} catch (EcuapassExceptions.ConnectionError ex) {
				Logger.getLogger (MainController.class.getName ()).log (Level.SEVERE, null, ex);
			}
		}).start ();
		// Shows a progress dialog while the process is running
		SwingUtilities.invokeLater (() -> {
			try {
				progressDialog = new ProgressDialog (this, mainView);
				progressDialog.startProcess ();
			} catch (Exception e) {
			}
		});

	}

	// Selected docFile in  FileChooser or table from InputsFilesViewProjects
	@Override
	public void onFileSelected (File docFilepath) {
		String docNumber = Utils.extractDocNumber (docFilepath.getName ());
		String docType = Utils.getDocumentTypeFromFilename (docFilepath.getName ());
		if (inputsView.checkDocNumberType (docNumber, docType)) {
			selectedFile = docFilepath;
			inputsView.setDocNumberType (docNumber, docType);
			imageView.showImage (docFilepath);
		}
	}

	@Override
	public boolean processSelectedDocument () {
		try {
			String docNumber = inputsView.getDocNumber ();
			if (Utils.extractDocNumber (docNumber) == null) {
				JOptionPane.showMessageDialog (this.mainView, "Número documento: '" + docNumber + "' inválido.");
				return false;
			}
			String selectedFilename = inputsView.getFileWithDocNumberFromFileChooser (docNumber);
			if (selectedFilename == null) {
				selectedFilename = inputsView.createFilenameFromDocNumber (docNumber);
				String docType = inputsView.getDocType ("LONGNAME");
				selectedFile = new File (selectedFilename);
				doc.currentRecord = new DocRecord (selectedFile.toString (), docType);
			} else {
				selectedFile = new File (selectedFilename);
				doc.currentRecord = new DocRecord (selectedFile.toString ());
			}
			this.onFileSelected (selectedFile);
		} catch (Exception ex) {
			out ("EXCEPCION: No se pudo procesar documento : " + selectedFile.getName ());
			ex.printStackTrace ();
			return false;
		}
		return true;
	}

	// InputsView files selected by FileChooser
	// Send selected file to ready table
	// ServeWorker notification 
	@Override
	public void onEndProcessing (String statusMsg, String text) {
		try {
			if (statusMsg.contains ("EXITO")) {
				out ("Documento procesado sin errores");
				String docFilepath = text.split ("'")[1].trim ();
				String jsonFilepath = Utils.getResultsFile (docFilepath, "ECUFIELDS.json");
				String docType = Utils.getDocumentTypeFromFilename (docFilepath);
				DocRecord record = new DocRecord (docType, docFilepath, jsonFilepath);
				out ("+++ Documento record:" + "\n" + record);
				doc.currentRecord = record;
				doc.addProcessedRecord (record);
				resultsController.addProcessedRecord (record);
				resultsController.resultsView.selectFirstRecord ();
				tabsPanel.setSelectedIndex (2);
			} else {
				out ("Documento procesado con errores: " + text);
				String message = text.split (":", 2)[1].replace ("\\", "\n");
				JOptionPane.showMessageDialog (mainView, message);
			}
		} catch (ParseException | IOException ex) {
			ex.printStackTrace ();
			out (ex.toString ());
		} finally {
			progressDialog.endProcess ("document_processed");
		}
	}

	public static void showClosingMessage (String message) {
		Thread thread = new Thread (() -> JOptionPane.showMessageDialog (null, message, "Closing Application", JOptionPane.INFORMATION_MESSAGE));
		thread.start ();
	}

	// Stop cartaporte server if it was opened
	@Override
	public void onWindowClosing () {
		System.out.println ("+++ onWindowClosing");
		try {
			ClosingMessage.showClosingMessage ("Applicación se está cerrando", this.mainView);
			this.forcedExitWithTimer (3);
			out ("+++ Finalizando Cliente...");
			if (serverWorker != null)
				serverWorker.startProcess ("stop", DocModel.runningPath, null);
			createExitFile ();
		} catch (Exception ex) {
			ex.printStackTrace ();
		}
	}

	public void createExitFile () {
		try {
			System.out.println ("+++ Creando archivo de salida forzada...!");
			File myFile = new File ("exit.txt");
			myFile.createNewFile ();
		} catch (IOException e) {
			e.printStackTrace ();
		}
	}

	private void forcedExitWithTimer (int timeInSeconds) {
		timer = new Timer (timeInSeconds * 1000, new ActionListener () {  // Timer fires after 5 seconds
			@Override
			public void actionPerformed (ActionEvent e) {
				out ("...Finalizado tiempo de salida forzada.");
				System.exit (0);
			}
		});
		timer.start (); // Start the timer
	}

	//  InputsFileView for "reinitialize" selection
	@Override
	public void onReinitialize () {
		inputsView.clearSelectedFiles ();
		doc.removeAllFiles ();
	}

	// Write message text to both: stdout and FeedbackView
	@Override
	public void out (String s) {
		System.out.println (s);
		if (feedbackView != null)
			SwingUtilities.invokeLater (() -> {
				try {
					feedbackView.println (s);
				} catch (Exception e) {
				}
			});
	}

	@Override
	public void setWindowState (String state) {
		if (state.equals ("minimize"))
			mainView.setState (JFrame.ICONIFIED);
		else if (state.equals ("restore"))
			mainView.setState (JFrame.NORMAL);
	}

	@Override
	public void onServerRunning () {
		out ("CLIENTE: Servidor listo");
		serverWorker.getServerUrl ();
		inputsView.enableProcessingButton (true);
	}

	// Send feedback to cloud
	@Override
	public void onSendFeedback (String feedbackText) {

		String zipFilepath = Utils.createTempCompressedFileFromText (feedbackText);
		// Call to server to start processing documents
		System.out.println ("-- " + zipFilepath);
		//serverWorker.startProcess ("send_feedback", zipFilepath, docFilepath);
	}

	public String getAppRelease () {
		return this.appRelease;
	}

	public void onWorkingCountrySelected (String workingCountry) {
		System.out.println ("Country Selected: " + workingCountry);
	}

	public void openCreadorDocumentosEcuapass () {
		try {
			String url = configController.getSettingsValue ("ecuapassdocs_url");
			serverWorker.startProcess ("open_ecuapassdocs_URL", url, null);

//		String url = this.settingsController.getSettingsValue ("url_creador");
//		try {
//			if (Desktop.isDesktopSupported ()) {
//				Desktop desktop = Desktop.getDesktop ();
//				desktop.browse (new URI (url));
//			} else
//				System.out.println ("Desktop API is not supported on this platform.");
//		} catch (Exception e) {
//			e.printStackTrace ();
//		}
		} catch (EcuapassExceptions.ConnectionError ex) {
			Logger.getLogger (MainController.class.getName ()).log (Level.SEVERE, null, ex);
		}
	}

	public static void main (String args[]) {

		//Set the Nimbus look and feel
		//<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
		/*
		 * If Nimbus (introduced in Java SE 6) is not available, stay with the
		 * default look and feel. For details see
		 * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
		 */
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels ()) {
				UIManager.setLookAndFeel (UIManager.getCrossPlatformLookAndFeelClassName ());
				//if ("Nimbus".equals (info.getName ())) {
				//	javax.swing.UIManager.setLookAndFeel (info.getClassName ());
				//	break;
				//}

			}
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger (MainView.class
				.getName ()).log (java.util.logging.Level.SEVERE, null, ex);
		}
		//</editor-fold>

		//</editor-fold>
		//  Create and display the form
		java.awt.EventQueue.invokeLater (new Runnable () {
			public void run () {
				new MainController ();
			}
		});
	}
}

class ClosingMessage {

	public static void showClosingMessage (String message, Component mainWindow) {
		Thread thread = new Thread (() -> JOptionPane.showMessageDialog (mainWindow, message, "Cerrando aplicación", JOptionPane.INFORMATION_MESSAGE));
		thread.start ();
	}
}
