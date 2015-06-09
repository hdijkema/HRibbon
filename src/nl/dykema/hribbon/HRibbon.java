package nl.dykema.hribbon;

import java.awt.event.ActionListener;
import java.util.Hashtable;

import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class HRibbon extends JTabbedPane {

	private static final long serialVersionUID = 1L;
	
	private boolean _is_minimized;
	private Hashtable<HRibbonTask, Runnable> _associated_with_task;
	
	public void addRunnable(HRibbonTask t, Runnable r) {
		_associated_with_task.put(t, r);
	}

	public void addTask(HRibbonTask task) {
		super.add(task.getTitle(), task);
	}
	
	public void setSelectedTask(HRibbonTask task) {
		super.setSelectedComponent(task);
	}
	
	public HRibbonTask getSelectedTask() {
		return (HRibbonTask) super.getSelectedComponent();
	}
	
	public boolean isMinimized() {
		return _is_minimized;
	}
	
	public void setMinimized(boolean m) {
		_is_minimized = m;
		int i, N;
		for(i = 0, N = super.getTabCount(); i < N; i++) {
			super.getTabComponentAt(i).setVisible(!m);
		}
	}
	
	public void configureHelp(ImageIcon icon, ActionListener a) {
		// TODO
	}

	public void setApplicationMenu(HApplicationMenu _main) {
		// TODO Auto-generated method stub
		
	}
	
	public HRibbon() {
		_is_minimized = false;
		_associated_with_task = new Hashtable<HRibbonTask, Runnable>();
		super.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				HRibbonTask t = getSelectedTask();
				Runnable r = _associated_with_task.get(t);
				if (r != null) { r.run(); }
			}
		});
		super.setFocusable(false);
	}


	
}
