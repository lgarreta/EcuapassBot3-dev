package main;

import database.Entity;
import java.awt.Dimension;
import java.util.Map;
import javax.swing.ImageIcon;

public class DocPanelDeclaracion extends DocPanel {

	public DocPanelDeclaracion () {
		initComponents ();
		docType = "declaracion";
		imageIcon = (ImageIcon) imageLabel.getIcon ();
	}
	
	public Entity getEntityDB () {
		// Initialize with default info from super
		Entity entityDB = super.getEntityDB ();
		// Get own data
		String cartaporte = txt15.getText ().split (System.lineSeparator ())[0];
		String manifiesto = txt12.getText ().split ("[ -]+")[0];

		Map valuesMap = entityDB.valuesMap;
		valuesMap.put ("numero", txt00.getText ());
		valuesMap.put ("cartaporte", cartaporte);
		valuesMap.put ("manifiesto", manifiesto);
		valuesMap.put ("document", this.dbFilename);

		return entityDB;
	}	

	public void setConstraintsToTextAreas () {
		this.txt00.setFontSize ("large");
		this.txt00.setText (docNumber);

		// Footer text: "ORIGINAL" or "COPIA"
		txt27.setFontSize ("large");
		txt27.setAlignment ("alignCenter");
		txt27.setText ("ORIGINAL");
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    txt00 = new main.DocText();
    txt01 = new main.DocText();
    txt02 = new main.DocText();
    txt03 = new main.DocText();
    txt04 = new main.DocText();
    txt05 = new main.DocText();
    txt06 = new main.DocText();
    txt07 = new main.DocText();
    txt08 = new main.DocText();
    txt09 = new main.DocText();
    txt10 = new main.DocText();
    txt11 = new main.DocText();
    txt12 = new main.DocText();
    txt13 = new main.DocText();
    txt14 = new main.DocText();
    txt15 = new main.DocText();
    txt16 = new main.DocText();
    txt17 = new main.DocText();
    txt18 = new main.DocText();
    txt19_1 = new main.DocText();
    txt19_2 = new main.DocText();
    txt19_3 = new main.DocText();
    txt19_4 = new main.DocText();
    txt20_1 = new main.DocText();
    txt20_2 = new main.DocText();
    txt21 = new main.DocText();
    txt22 = new main.DocText();
    txt23 = new main.DocText();
    txt24 = new main.DocText();
    txt25 = new main.DocText();
    txt26 = new main.DocText();
    txt27 = new main.DocText();
    imageLabel = new javax.swing.JLabel();

    setBackground(new java.awt.Color(255, 255, 255));
    setLayout(null);
    add(txt00);
    txt00.setBounds(860, 75, 190, 35);
    add(txt01);
    txt01.setBounds(45, 145, 512, 125);
    add(txt02);
    txt02.setBounds(45, 298, 512, 50);
    add(txt03);
    txt03.setBounds(45, 374, 512, 52);
    add(txt04);
    txt04.setBounds(45, 450, 512, 52);
    add(txt05);
    txt05.setBounds(45, 526, 512, 52);
    add(txt06);
    txt06.setBounds(45, 602, 512, 52);
    add(txt07);
    txt07.setBounds(45, 678, 512, 52);
    add(txt08);
    txt08.setBounds(563, 142, 495, 23);
    add(txt09);
    txt09.setBounds(563, 188, 495, 33);
    add(txt10);
    txt10.setBounds(563, 246, 495, 72);
    add(txt11);
    txt11.setBounds(563, 343, 495, 82);
    add(txt12);
    txt12.setBounds(563, 450, 495, 76);
    add(txt13);
    txt13.setBounds(563, 570, 495, 33);
    add(txt14);
    txt14.setBounds(563, 630, 495, 100);
    txt14.getAccessibleContext().setAccessibleName("");

    add(txt15);
    txt15.setBounds(45, 780, 131, 322);
    add(txt16);
    txt16.setBounds(180, 780, 329, 322);
    add(txt17);
    txt17.setBounds(513, 780, 122, 322);
    add(txt18);
    txt18.setBounds(640, 780, 141, 322);
    add(txt19_1);
    txt19_1.setBounds(788, 780, 85, 322);
    add(txt19_2);
    txt19_2.setBounds(788, 1110, 85, 77);
    add(txt19_3);
    txt19_3.setBounds(880, 780, 85, 322);
    add(txt19_4);
    txt19_4.setBounds(880, 1110, 85, 77);
    add(txt20_1);
    txt20_1.setBounds(972, 780, 85, 322);
    add(txt20_2);
    txt20_2.setBounds(972, 1110, 85, 77);
    add(txt21);
    txt21.setBounds(45, 1140, 359, 47);
    add(txt22);
    txt22.setBounds(410, 1140, 265, 47);
    add(txt23);
    txt23.setBounds(105, 1342, 452, 30);
    add(txt24);
    txt24.setBounds(563, 1210, 495, 45);
    add(txt25);
    txt25.setBounds(563, 1280, 495, 37);
    add(txt26);
    txt26.setBounds(563, 1352, 495, 26);

    txt27.setFont(new java.awt.Font("FreeSans", 1, 18)); // NOI18N
    txt27.setText("ORIGINAL");
    add(txt27);
    txt27.setBounds(470, 1385, 180, 30);

    imageLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image-declaracion-vacia-NTA.png"))); // NOI18N
    imageLabel.setAlignmentY(0.0F);
    add(imageLabel);
    imageLabel.setBounds(0, 0, 1100, 1424);
  }// </editor-fold>//GEN-END:initComponents


  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JLabel imageLabel;
  private main.DocText txt00;
  private main.DocText txt01;
  private main.DocText txt02;
  private main.DocText txt03;
  private main.DocText txt04;
  private main.DocText txt05;
  private main.DocText txt06;
  private main.DocText txt07;
  private main.DocText txt08;
  private main.DocText txt09;
  private main.DocText txt10;
  private main.DocText txt11;
  private main.DocText txt12;
  private main.DocText txt13;
  private main.DocText txt14;
  private main.DocText txt15;
  private main.DocText txt16;
  private main.DocText txt17;
  private main.DocText txt18;
  private main.DocText txt19_1;
  private main.DocText txt19_2;
  private main.DocText txt19_3;
  private main.DocText txt19_4;
  private main.DocText txt20_1;
  private main.DocText txt20_2;
  private main.DocText txt21;
  private main.DocText txt22;
  private main.DocText txt23;
  private main.DocText txt24;
  private main.DocText txt25;
  private main.DocText txt26;
  private main.DocText txt27;
  // End of variables declaration//GEN-END:variables

}