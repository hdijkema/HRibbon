package nl.dykema.hribbon;

import java.awt.BorderLayout;
import java.awt.Color;
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
		final static int TOP = 1;
		static final int MEDIUM = 2;
	}
	
	private class MenuLabel extends JButton {

		private static final long serialVersionUID = 1L;
		
		JLabel _label=new JLabel();
		
		public MenuLabel(String txt, Icon icn) {
			this(new JLabel(txt), icn);
		}
		
		protected MenuLabel(JLabel l, Icon icn) {
			super.setLayout(new BorderLayout());
			_label = l;
			Dimension d = _label.getPreferredSize();
			_label.setPreferredSize(d);
			super.add(_label, BorderLayout.CENTER);
			ExpandIcon e=new ExpandIcon();
			e.translateY(2);e.translateX(2);
			super.add(new JLabel(e), BorderLayout.EAST);
			JLabel icon = new JLabel(icn);
			icon.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 3));
			if (icn != null) { super.add(icon, BorderLayout.WEST); }
		}
	}	
	
	private static final long serialVersionUID = 1L;
	
	private String s_title;
	private JLabel l_title;
	private JPanel p_buttons;
	private JPanel p_current_group;
	private String s_resourceLocation;
	
	public String getTitle() {
		return s_title;
	}
	
	public void setTitle(String s) {
		s_title = s;
		l_title.setText("<html><b><small>" + s_title + "</small></b></html>");
		l_title.setHorizontalAlignment(SwingConstants.CENTER);
		
	}
	
	private void newGroupIfNecessary() {
		if (p_current_group == null) {
			newGroup();
		}
	}
	
	public void addButton(JButton button, int priority) {
		if (priority == Priority.TOP) {
		    button.setVerticalTextPosition(SwingConstants.BOTTOM);
		    button.setHorizontalTextPosition(SwingConstants.CENTER);		
			newGroup();
			p_current_group.add(button, "growy");
			finishGroup();
		} else {
			newGroupIfNecessary();
			button.setHorizontalAlignment(SwingConstants.LEFT);
			p_current_group.add(button, "growx, wrap");
		}
		button.setBorder(BorderFactory.createEmptyBorder(2,4,2,4));
		button.setFocusable(false);
	}
	
	public void addToggleButton(JToggleButton button, int priority) {
		if (priority == Priority.TOP) {
		    button.setVerticalTextPosition(SwingConstants.BOTTOM);
		    button.setHorizontalTextPosition(SwingConstants.CENTER);		
			newGroup();
			p_current_group.add(button, "growy");
			finishGroup();
		} else {
			newGroupIfNecessary();
			button.setHorizontalAlignment(SwingConstants.LEFT);
			p_current_group.add(button, "align left, growx, wrap");
		}
		button.setBorder(BorderFactory.createEmptyBorder(2,4,2,4));
		button.setFocusable(false);
	}
	
	public void addMenu(String label, String imageName, final JPopupMenu m, int priority) {
		ImageIcon icn = IconFactory.readIcon(s_resourceLocation, imageName, priority);
		MenuLabel b = new MenuLabel(label, icn);
		if (priority == Priority.TOP) {
			newGroup();
			p_current_group.add(b, "growy");
			finishGroup();
		} else {
			newGroupIfNecessary();
			p_current_group.add(b, "growx, wrap");
		}
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				m.show(b, 0, b.getHeight());
			}
		});	
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
		JToggleButton b=new JToggleButton(text);
	    b.setVerticalTextPosition(SwingConstants.BOTTOM);
	    b.setHorizontalTextPosition(SwingConstants.CENTER);		
		if (mnemonic!=null) { b.setMnemonic(mnemonic.charAt(0)); }
		b.setToolTipText(tooltip);
		b.setName(command);
		ImageIcon icn = IconFactory.readIcon(s_resourceLocation, imageName, priority);
		b.setIcon(icn);
		b.addActionListener(l);
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
		JButton b=new JButton(text);
		if (mnemonic!=null) { b.setMnemonic(mnemonic.charAt(0)); }
		b.setName(command);
		ImageIcon icn = IconFactory.readIcon(s_resourceLocation, imageName, priority);
		b.setIcon(icn);
		b.setToolTipText(tooltip);
		b.addActionListener(l);
		addButton(b, priority);
	}

	public void addButton(String task, String imageName, String text, String tooltip, String command, ActionListener l) {
		addButton(task, imageName, text, tooltip, command, l, Priority.TOP);
	}

	public void newGroup() {
		finishGroup();
		p_current_group = new JPanel();
		p_current_group.setLayout(new MigLayout("insets 0, gap 0, fill"));
	}
	
	public void finishGroup() {
		if (p_current_group != null) {
			p_buttons.add(p_current_group, "growy");
		}
		p_current_group = null;
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
