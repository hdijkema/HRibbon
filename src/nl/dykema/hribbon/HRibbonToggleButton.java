package nl.dykema.hribbon;

import javax.swing.Icon;
import javax.swing.JToggleButton;

public class HRibbonToggleButton extends JToggleButton {

	private static final long serialVersionUID = 1L;

	private void init() {
	}

	public HRibbonToggleButton() {
		init();
	}
	
	public HRibbonToggleButton(String txt) {
		super(txt);
		init();
	}

	public HRibbonToggleButton(String txt, Icon icn) {
		super(txt, icn);
		init();
	}
}
