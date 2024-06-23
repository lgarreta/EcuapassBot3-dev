package config;

import javax.swing.JTextField;
import widgets.PasswordFieldWithToggle;

public class UserPasswordPanel extends javax.swing.JPanel {

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {
    java.awt.GridBagConstraints gridBagConstraints;

    userLabel = new javax.swing.JLabel();
    userInput = new javax.swing.JTextField();
    passwordLabel = new javax.swing.JLabel();
    passwordInput = new widgets.PasswordFieldWithToggle();

    setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
    setLayout(new java.awt.GridBagLayout());

    userLabel.setText("Usuario:");
    add(userLabel, new java.awt.GridBagConstraints());

    userInput.setMinimumSize(new java.awt.Dimension(150, 27));
    userInput.setPreferredSize(new java.awt.Dimension(200, 27));
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
    add(userInput, gridBagConstraints);

    passwordLabel.setText("Contraseña:");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    add(passwordLabel, gridBagConstraints);

    passwordInput.setMinimumSize(new java.awt.Dimension(150, 27));
    passwordInput.setPreferredSize(new java.awt.Dimension(200, 26));
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
    add(passwordInput, gridBagConstraints);
  }// </editor-fold>//GEN-END:initComponents


  // Variables declaration - do not modify//GEN-BEGIN:variables
  private widgets.PasswordFieldWithToggle passwordInput;
  private javax.swing.JLabel passwordLabel;
  private javax.swing.JTextField userInput;
  private javax.swing.JLabel userLabel;
  // End of variables declaration//GEN-END:variables

	public UserPasswordPanel () {
		initComponents ();
	}

	public String getUser () {
		return (this.userInput.getText ());
	}

	public String getPassword () {
		return (this.passwordInput.getText ());
	}

	public void setUser (String user) {
		this.userInput.setText (user);
	}

	public void setPassword (String password) {
		this.passwordInput.setText (password);
	}
}