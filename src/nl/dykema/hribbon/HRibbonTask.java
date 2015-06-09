package nl.dykema.hribbon;

import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

public class HRibbonTask extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private String s_title;

	public String getTitle() {
		return s_title;
	}
	
	public void setTitle(String t) {
		s_title = t;
	}
	
	public void addBand(HRibbonBand band) {
		super.add(band, "align left, growy");
	}
	
	public HRibbonTask() {
		super.setLayout(new MigLayout("insets 0, gap 0, filly"));
	}

	public HRibbonTask(String t, HRibbonBand... bands) {
		super.setLayout(new MigLayout("insets 0, gap 3, filly"));
		s_title = t;
		for(HRibbonBand band: bands) {
			band.finishGroup();
			addBand(band);
		}
		super.setFocusable(false);
	}
}
