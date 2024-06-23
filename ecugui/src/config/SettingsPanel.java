package config;

import com.google.gson.JsonObject;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.text.AbstractDocument;
import widgets.UppercaseDocumentFilter;

public class SettingsPanel extends javax.swing.JPanel {

	SettingsController controller;

	public SettingsPanel () {
		initComponents ();
		// Togle to uppercase
		((AbstractDocument) this.empresa.getDocument ()).setDocumentFilter (new UppercaseDocumentFilter ());
	}

	public void setController (SettingsController controller) {
		this.controller = controller;
	}

	// Return settings displayed on the panel
	public JsonObject getSettings () {
		// Create a JSON object
		Map<String, String> settings = new LinkedHashMap ();
		settings.put ("empresa", empresa.getText ());
		settings.put ("urlCodebin", codebinUrl.getText ());
		settings.put ("userColombia", credentialsPanel.getUserColombia ());
		settings.put ("passwordColombia", credentialsPanel.getPasswordColombia ());
		settings.put ("userEcuador", credentialsPanel.getUserEcuador ());
		settings.put ("passwordEcuador", credentialsPanel.getPasswordEcuador ());
		settings.put ("userPeru", credentialsPanel.getUserPeru ());
		settings.put ("passwordPeru", credentialsPanel.getPasswordPeru ());
		settings.put ("NORMAL_PAUSE", normalPause.getText ());
		settings.put ("SLOW_PAUSE", slowPause.getText ());
		settings.put ("FAST_PAUSE", fastPause.getText ());

		// Create a new JsonObject
		JsonObject jsonObject = new JsonObject ();

		// Populate the JsonObject with LinkedHashMap entries (preserving order)
		for (Map.Entry<String, String> entry : settings.entrySet ()) {
			jsonObject.addProperty (entry.getKey (), entry.getValue ());
		}

		return jsonObject;
	}

	public void setSettings (JsonObject settings) {
		empresa.setText ((String) settings.get ("empresa").getAsString ());
		codebinUrl.setText ((String) settings.get ("urlCodebin").getAsString ());

		credentialsPanel.setUserColombia ((String) settings.get ("userColombia").getAsString ());
		credentialsPanel.setUserEcuador ((String) settings.get ("userEcuador").getAsString ());
		credentialsPanel.setUserPeru ((String) settings.get ("userPeru").getAsString ());
		credentialsPanel.setPasswordColombia ((String) settings.get ("passwordColombia").getAsString ());
		credentialsPanel.setPasswordEcuador ((String) settings.get ("passwordEcuador").getAsString ());
		credentialsPanel.setPasswordPeru ((String) settings.get ("passwordPeru").getAsString ());

		normalPause.setText ((String) settings.get ("NORMAL_PAUSE").getAsString ());
		slowPause.setText ((String) settings.get ("SLOW_PAUSE").getAsString ());
		fastPause.setText ((String) settings.get ("FAST_PAUSE").getAsString ());
	}

	@SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {
    java.awt.GridBagConstraints gridBagConstraints;

    configPanel = new javax.swing.JPanel();
    jPanel1 = new javax.swing.JPanel();
    companyLabel = new javax.swing.JLabel();
    empresa = new javax.swing.JTextField();
    codebinUrl = new javax.swing.JTextField();
    codebiniLabel = new javax.swing.JLabel();
    credentialsPanel = new config.CredentialsPanel();
    jPanel2 = new javax.swing.JPanel();
    normalPauseLabel = new javax.swing.JLabel();
    normalPause = new javax.swing.JTextField();
    slowPauseLabel = new javax.swing.JLabel();
    slowPause = new javax.swing.JTextField();
    fastPauseLabel = new javax.swing.JLabel();
    fastPause = new javax.swing.JTextField();
    panelOptions = new javax.swing.JPanel();
    saveButton = new javax.swing.JButton();
    cancelButton = new javax.swing.JButton();

    setMinimumSize(new java.awt.Dimension(702, 254));
    setPreferredSize(new java.awt.Dimension(702, 254));
    setLayout(new java.awt.BorderLayout());

    configPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Configuración"));
    configPanel.setMinimumSize(new java.awt.Dimension(200, 400));
    configPanel.setOpaque(false);
    configPanel.setPreferredSize(new java.awt.Dimension(700, 100));

    jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Empresa:"));
    jPanel1.setMinimumSize(new java.awt.Dimension(100, 56));
    jPanel1.setLayout(new java.awt.GridBagLayout());

    companyLabel.setText("Nombre");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 0;
    jPanel1.add(companyLabel, gridBagConstraints);

    empresa.setMinimumSize(new java.awt.Dimension(100, 27));
    empresa.setPreferredSize(new java.awt.Dimension(100, 27));
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 2;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.ipadx = 137;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
    jPanel1.add(empresa, gridBagConstraints);

    codebinUrl.setMinimumSize(new java.awt.Dimension(100, 27));
    codebinUrl.setPreferredSize(new java.awt.Dimension(100, 27));
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 2;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.ipadx = 137;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
    jPanel1.add(codebinUrl, gridBagConstraints);

    codebiniLabel.setText("URL Codebin:");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    jPanel1.add(codebiniLabel, gridBagConstraints);

    configPanel.add(jPanel1);

    credentialsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Credenciales:"));
    configPanel.add(credentialsPanel);

    jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Tiempos:"));
    jPanel2.setLayout(new java.awt.GridBagLayout());

    normalPauseLabel.setText("Pausa Normal (0.05 Seg):");
    jPanel2.add(normalPauseLabel, new java.awt.GridBagConstraints());

    normalPause.setText("0.05");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 2;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
    jPanel2.add(normalPause, gridBagConstraints);

    slowPauseLabel.setText("Pausa Lenta (0.5 Seg):");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 1;
    jPanel2.add(slowPauseLabel, gridBagConstraints);

    slowPause.setText("0.5");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 2;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
    jPanel2.add(slowPause, gridBagConstraints);

    fastPauseLabel.setText("Pausa Rápida (0.01 Seg):");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 2;
    jPanel2.add(fastPauseLabel, gridBagConstraints);

    fastPause.setText("0.01");
    fastPause.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        fastPauseActionPerformed(evt);
      }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 2;
    gridBagConstraints.gridy = 2;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
    jPanel2.add(fastPause, gridBagConstraints);

    configPanel.add(jPanel2);

    add(configPanel, java.awt.BorderLayout.CENTER);

    saveButton.setText("Guardar");
    saveButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        saveButtonActionPerformed(evt);
      }
    });
    panelOptions.add(saveButton);

    cancelButton.setText("Cancelar");
    cancelButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        cancelButtonActionPerformed(evt);
      }
    });
    panelOptions.add(cancelButton);

    add(panelOptions, java.awt.BorderLayout.SOUTH);
  }// </editor-fold>//GEN-END:initComponents

  private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
		JsonObject settings = this.getSettings ();
		controller.onSaveSettings (settings);
  }//GEN-LAST:event_saveButtonActionPerformed

  private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
		// TODO add your handling code here:
		controller.onCancelSettings ();
  }//GEN-LAST:event_cancelButtonActionPerformed

  private void fastPauseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fastPauseActionPerformed
    // TODO add your handling code here:
  }//GEN-LAST:event_fastPauseActionPerformed

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton cancelButton;
  private javax.swing.JTextField codebinUrl;
  private javax.swing.JLabel codebiniLabel;
  private javax.swing.JLabel companyLabel;
  private javax.swing.JPanel configPanel;
  private config.CredentialsPanel credentialsPanel;
  private javax.swing.JTextField empresa;
  private javax.swing.JTextField fastPause;
  private javax.swing.JLabel fastPauseLabel;
  private javax.swing.JPanel jPanel1;
  private javax.swing.JPanel jPanel2;
  private javax.swing.JTextField normalPause;
  private javax.swing.JLabel normalPauseLabel;
  private javax.swing.JPanel panelOptions;
  private javax.swing.JButton saveButton;
  private javax.swing.JTextField slowPause;
  private javax.swing.JLabel slowPauseLabel;
  // End of variables declaration//GEN-END:variables

}
