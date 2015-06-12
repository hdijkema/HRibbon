package nl.dykema.hribbon;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;

import net.miginfocom.swing.MigLayout;
import nl.dykema.hribbon.utils.ExpandIcon;
import nl.dykema.hribbon.utils.IconFactory;

public class HRibbonBand extends JPanel {

	public interface Priority {
		static final int TOP = 1;
		static final int MEDIUM = 2;
		static final int LOW = 3;
	}
	
	private static final long serialVersionUID = 1L;
	
	private String s_title;
	private JLabel l_title;
	private JPanel p_buttons;
	private JPanel p_current_group;
	private int    _max_low_buttons_per_row = 2;
	private int    _current_low_buttons_on_row = 0;
	private int    _max_low_medium_rows = 3;
	private int    _current_row = 0; 
	private String s_resourceLocation;
	
	public String getTitle() {
		return s_title;
	}
	
	public void setTitle(String s) {
		s_title = s;
		int size = (int) (l_title.getFont().getSize() * 0.9f);
		System.out.println(size);
		l_title.setText("<html><font size="+size+"pt>" + s_title + "</font></html>");
		l_title.setHorizontalAlignment(SwingConstants.CENTER);
		
	}
	
	private void newGroupIfNecessary() {
		if (p_current_group == null) {
			newGroup();
		} else {
			if (_current_row >= _max_low_medium_rows) {
				newGroup();
			}
		}
	}
	
	public void addComponent(Component c, int priority) {
		if (priority == Priority.TOP) {
			newGroup();
			p_current_group.add(c, "growy");
			finishGroup();
		} else {
			newGroupIfNecessary();
			p_current_group.add(c, "growx, wrap");
		}
		c.setFocusable(false);
	}
	
	public void addButton(HRibbonButton button, int priority) {
		if (priority == Priority.TOP) {
		    button.setVerticalTextPosition(SwingConstants.BOTTOM);
		    button.setHorizontalTextPosition(SwingConstants.CENTER);		
			newGroup();
			p_current_group.add(button, "growy");
			finishGroup();
			//button.setBorder(BorderFactory.createEmptyBorder(2, 4, 2, 4));
		} else if (priority == Priority.LOW) {
			newGroupIfNecessary();
			button.setText(null);
			_current_low_buttons_on_row += 1;
			if (_current_low_buttons_on_row  >= _max_low_buttons_per_row) {
				p_current_group.add(button, "wrap");
				_current_low_buttons_on_row = 0;
				_current_row += 1;
			} else {
				p_current_group.add(button);
			}
		} else {
			newGroupIfNecessary();
			button.setHorizontalAlignment(SwingConstants.LEFT);
			p_current_group.add(button, "growx, wrap");
			_current_row += 1;
			//button.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		}
		button.setFocusable(false);
	}
	
	public void addToggleButton(HRibbonToggleButton button, int priority) {
		if (priority == Priority.TOP) {
		    button.setVerticalTextPosition(SwingConstants.BOTTOM);
		    button.setHorizontalTextPosition(SwingConstants.CENTER);		
			newGroup();
			p_current_group.add(button, "growy");
			finishGroup();
			//button.setBorder(BorderFactory.createEmptyBorder(2, 4, 2, 4));
		} else {
			newGroupIfNecessary();
			button.setHorizontalAlignment(SwingConstants.LEFT);
			p_current_group.add(button, "align left, growx, wrap");
			//button.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		}
		button.setFocusable(false);
	}
	
	public void addMenu(HRibbonMenuButton b, final JPopupMenu m, int priority) {
		if (priority == Priority.TOP) {
			newGroup();
			p_current_group.add(b, "growy");
			finishGroup();
			//b.setBorder(BorderFactory.createEmptyBorder(2, 4, 2, 4));
		} else {
			newGroupIfNecessary();
			p_current_group.add(b, "growx, wrap");
			//b.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		}
		b.setFocusable(false);
	}
	
	public void addMenu(String label, String imageName, final JPopupMenu m, int priority) {
		ImageIcon icn = IconFactory.readIcon(s_resourceLocation, imageName, priority);
		HRibbonMenuButton b = new HRibbonMenuButton(label, icn, m);
		addMenu(b, m, priority);
	}
	
	public JToggleButton addToggleButton(String task, String imageName, String text, String tooltip, String command, ActionListener l, int priority) {
		int index=text.indexOf('_');
		String mnemonic=null;
		if (index>=0) {
			mnemonic=new String()+text.charAt(index+1);
			text=text.substring(0,index)+text.substring(index+1);
		}
		text = text.replace("#", "<br />");
		text = text.replace("\n", "<br />");
		text = "<html><center>" + text + "</center></html>";
		HRibbonToggleButton b=new HRibbonToggleButton(text);
	    b.setVerticalTextPosition(SwingConstants.BOTTOM);
	    b.setHorizontalTextPosition(SwingConstants.CENTER);		
		if (mnemonic!=null) { b.setMnemonic(mnemonic.charAt(0)); }
		b.setToolTipText(tooltip);
		b.setName(command);
		ImageIcon icn = IconFactory.readIcon(s_resourceLocation, imageName, priority);
		b.setIcon(icn);
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ActionEvent n = new ActionEvent(e.getSource(), e.getID(), b.getName(), e.getWhen(), e.getModifiers());
				l.actionPerformed(n);
			}
		});
		addToggleButton(b, priority);
		return b;
	}
	
	
	public void addButton(String task, String imageName, String text, String tooltip, String command, ActionListener l, int priority) {
		int index=text.indexOf('_');
		String mnemonic=null;
		if (index>=0) {
			mnemonic=new String()+text.charAt(index+1);
			text=text.substring(0,index)+text.substring(index+1);
		}
		text = text.replace("#", "<br />");
		text = text.replace("\n", "<br />");
		text = "<html><center>" + text + "</center></html>";
		HRibbonButton b=new HRibbonButton(text);
		if (mnemonic!=null) { b.setMnemonic(mnemonic.charAt(0)); }
		b.setName(command);
		ImageIcon icn = IconFactory.readIcon(s_resourceLocation, imageName, priority);
		b.setIcon(icn);
		b.setToolTipText(tooltip);
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ActionEvent n = new ActionEvent(e.getSource(), e.getID(), b.getName(), e.getWhen(), e.getModifiers());
				l.actionPerformed(n);
			}
		});
		addButton(b, priority);
	}

	public void addButton(String task, String imageName, String text, String tooltip, String command, ActionListener l) {
		addButton(task, imageName, text, tooltip, command, l, Priority.TOP);
	}

	public void addToggleButton(String task, String imageName, String text, String tooltip, String command, ActionListener l) {
		addToggleButton(task, imageName, text, tooltip, command, l, Priority.TOP);
	}

	public void newGroup() {
		finishGroup();
		p_current_group = new JPanel();
		p_current_group.setLayout(new MigLayout("insets 0, gap 0, fill"));
		this._current_low_buttons_on_row = 0;
		this._current_row = 0;
	}
	
	public void finishGroup() {
		if (p_current_group != null) {
			p_buttons.add(p_current_group, "growy");
		}
		p_current_group = null;
	}
	
	public void setMaxLowButtonsPerRow(int max) {
		this._max_low_buttons_per_row = max;
	}
	
	public void setMaxLowMediumRows(int max) {
		this._max_low_medium_rows = max;
	}

	public HRibbonBand(String title, String resourceLocation) {
		s_resourceLocation = resourceLocation;
		
		this.setLayout(new MigLayout("insets 1, fill"));
		l_title = new JLabel();
		
		p_buttons = new JPanel();
		p_buttons.setLayout(new MigLayout("insets 0, gap 0, fill"));
		
		this.add(p_buttons, "growy");
		this.add(l_title,"south, growx");
		this.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.gray));
		
		setTitle(title);
		
		super.setFocusable(false);
	}


}
