package nl.dykema.hribbon;

import javax.swing.Icon;
import javax.swing.JButton;

public class HRibbonButton extends JButton {
	
	private static final long serialVersionUID = 1L;

	private void init() {
		//super.setBorderPainted(false);
		//super.setFocusPainted(false);
		//super.setContentAreaFilled(false);
		//super.setRolloverEnabled(true);
		super.setBorderPainted(false);
	}
	
	public HRibbonButton() {
		init();
	}
	
	public HRibbonButton(String txt) {
		super("<html><center>"+txt.replace('#', '\n').replace("\n", "<br />")+"</center></html>");
		init();
	}

	public HRibbonButton(String txt, Icon icn) {
		super("<html><center>"+txt.replace('#', '\n').replace("\n", "<br />")+"</center></html>", icn);
		init();
	}
}
