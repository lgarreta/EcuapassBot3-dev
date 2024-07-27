package config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import documento.DocModel;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import main.Controller;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import widgets.InitialSettingsDialog;

public class SettingsController {

	String runningPath = DocModel.runningPath;
	InitialSettingsDialog initialSettingsDialog;
	public FeedbackView feedbackView;
	Controller controller;

	public SettingsController (Controller controller) {
		this.controller = controller;
		feedbackView = new FeedbackView ();
		feedbackView.setController (this);
	}

	public void onSaveSettings (JsonObject settings) {
		if (this.checkForValidSettings (settings) == false)
			return;
		if (this.initialSettingsDialog != null)
			this.initialSettingsDialog.endProcess ();
		this.writeSettings (settings);
	}

	public void onCancelSettings () {
		if (this.initialSettingsDialog != null) {
			this.initialSettingsDialog.endProcess ();
			controller.onWindowClosing ();
		}
	}

	public void onSendFeedback (String feedbackText) {
		controller.onSendFeedback (feedbackText);
	}

	// First time initialization. Set "empresa" name for  cloud document models
	public String initSettings (JFrame parent) {
		String companyName = null;
		JsonObject settings = this.readSettings ();
		if (settings == null) {// Load nombreEmpresa name
			initialSettingsDialog = new InitialSettingsDialog (parent);
			initialSettingsDialog.setController (this);
			initialSettingsDialog.startProcess ();
			settings = this.readSettings ();
			if (settings == null)
				return null;
		}
		this.feedbackView.setSettings (settings);
		companyName = settings.get ("empresa").getAsString ();
		System.out.println (">>>>>>>>> Empresa: " + companyName + " <<<<<<<<<<");
		return companyName;
	}

	public JsonObject readSettings () {
		try {
			File settingsFile = new File (runningPath + "/settings.txt");
			System.out.println (">>> Settings file: " + settingsFile);
			if (settingsFile.exists ()) {
				JsonObject settings = JsonParser.parseReader (new FileReader (settingsFile)).getAsJsonObject ();
				settings = this.updateSettings (settings);
				return settings;
			}
		} catch (FileNotFoundException ex) {
			Logger.getLogger (SettingsController.class.getName ()).log (Level.SEVERE, null, ex);
		}
		return null;
	}

	// Settins as json
	public JsonObject writeSettings (JsonObject settings) {
		File settingsFile = new File (runningPath + "/settings.txt");
		Gson gson = new GsonBuilder ().setPrettyPrinting ().create ();
		String jsonString = gson.toJson (settings);

		// Write the JSON string to a file
		try (FileWriter fileWriter = new FileWriter (settingsFile)) {
			System.out.println (">>> Guardando archivo de configuracion: " + settingsFile);
			fileWriter.write (jsonString);
		} catch (Exception e) {
			e.printStackTrace ();
		}
		return settings;
	}

	// Settings as a map
	public JsonObject writeSettings (Map<String, String> mapSettings) {
		// Create a JsonObject
		JsonObject jsonObject = new JsonObject ();

		// Iterate over the map and add each entry to the JsonObject
		for (Map.Entry<String, String> entry : mapSettings.entrySet ()) {
			jsonObject.addProperty (entry.getKey (), entry.getValue ());
		}
		JsonObject jsonSettings = this.writeSettings (jsonObject);
		return jsonSettings;
	}

	public boolean checkForValidSettings (JsonObject settings) {
		if (settings.get ("empresa").equals ("")) {
			JOptionPane.showMessageDialog (null, "Nombre de empresa inválido");
			return false;
		}
		if (settings.get ("urlCodebin").equals ("")) {
			JOptionPane.showMessageDialog (null, "URL Codebin inválido");
			return false;
		}

		return true;
	}

	// Get value from "settings.json" file located in "runningPath"
	public String getSettingsValue (String key) {
		File settingsFile = new File (this.runningPath + "/settings.txt");

		String value = null;
		try {
			JSONParser parser = new JSONParser ();
			JSONObject jsonObj = (JSONObject) parser.parse (new FileReader (settingsFile));
			value = (String) jsonObj.get (key);

		} catch (IOException | ParseException ex) {
			Logger.getLogger (SettingsController.class
				.getName ()).log (Level.SEVERE, null, ex);
		}
		return value;
	}

	// Create new 'settings.json' file, if it is old
	public JsonObject updateSettings (JsonObject jsonSettings) {
		String empresa = jsonSettings.get ("empresa").getAsString ().toUpperCase ();
		Map settings = jsonSettings.asMap ();

		if (empresa.equals ("BYZA") ) {
			settings.clear ();
			settings.put ("empresa", "BYZA");
			settings.put ("urlCodebin", "https://byza.corebd.net");
			settings.put ("userColombia", "GRUPO BYZA");
			settings.put ("passwordColombia", "GrupoByza2020*");
			settings.put ("userEcuador", "GRUPO BYZA");
			settings.put ("passwordEcuador", "GrupoByza2020*");
			settings.put ("userPeru", "");
			settings.put ("passwordPeru", "");			
			settings.put ("NORMAL_PAUSE", "0.05");
			settings.put ("SLOW_PAUSE", "0.5");
			settings.put ("FAST_PAUSE", "0.01");
			jsonSettings = this.writeSettings (settings);
		} else if (empresa.equals ("NTA") ) {
			settings.clear ();
			settings.put ("empresa", "NTA");
			settings.put ("urlCodebin", "https://nta.corebd.net");
			settings.put ("userColombia", "MARCELA");
			settings.put ("passwordColombia", "NTAIPIALES2023");
			settings.put ("userEcuador", "KARLA");
			settings.put ("passwordEcuador", "NTAIPIALES2023");
			settings.put ("userPeru", "");
			settings.put ("passwordPeru", "");
			settings.put ("NORMAL_PAUSE", "0.05");
			settings.put ("SLOW_PAUSE", "0.5");
			settings.put ("FAST_PAUSE", "0.01");
			jsonSettings = this.writeSettings (settings);
		} else if (empresa.equals ("LOGITRANS")) {
			settings.clear ();
			settings.put ("empresa", "LOGITRANS");
			settings.put ("urlCodebin", "https://logitrans.corebd.net");
			settings.put ("userColombia", "");
			settings.put ("passwordColombia", "");
			settings.put ("userEcuador", "PATO");
			settings.put ("passwordEcuador", "Patologitrans");
			settings.put ("userPeru", "");
			settings.put ("passwordPeru", "");
			settings.put ("NORMAL_PAUSE", "0.05");
			settings.put ("SLOW_PAUSE", "0.5");
			settings.put ("FAST_PAUSE", "0.01");
			jsonSettings = this.writeSettings (settings);
		}
		return jsonSettings;
	}
}
