package nl.dykema.hribbon;

import java.awt.Color;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;

import net.miginfocom.swing.MigLayout;

public class HRibbonBand extends JPanel {

	public interface Priority {
		final static int TOP = 1;
		static final int MEDIUM = 2;
	}
	
	private static final long serialVersionUID = 1L;
	
	private String s_title;
	private JLabel l_title;
	private JPanel p_buttons;
	
	public String getTitle() {
		return s_title;
	}
	
	public void setTitle(String s) {
		s_title = s;
		l_title.setText("<html><b><small>" + s_title + "</small></b></html>");
		l_title.setHorizontalAlignment(SwingConstants.CENTER);
		
	}
	
	public void addButton(JButton button) {
		p_buttons.add(button, "growy");
	}
	
	public void addButton(JToggleButton button) {
		p_buttons.add(button);
	}
	
	public JToggleButton addToggleButton(String task,String imageName, String text, String tooltip, String command, ActionListener l, int priority) {
		int index=text.indexOf('_');
		String mnemonic=null;
		if (index>=0) {
			mnemonic=new String()+text.charAt(index+1);
			text=text.substring(0,index)+text.substring(index+1);
		}
		text=text.replace("#", "<br />");
		text = text.replace("\n", "<br />");
		text = "<html><center>" + text + "</center></html>";
		JToggleButton b=new JToggleButton(text);
	    b.setVerticalTextPosition(SwingConstants.BOTTOM);
	    b.setHorizontalTextPosition(SwingConstants.CENTER);		
		if (mnemonic!=null) { b.setMnemonic(mnemonic.charAt(0)); }
		b.setToolTipText(tooltip);
		b.setName(command);
		b.addActionListener(l);
		addButton(b);
		return b;
	}
	
	
	public void addButton(String task, ImageIcon icn, String text, String tooltip, String command, ActionListener l, int priority) {
		int index=text.indexOf('_');
		String mnemonic=null;
		if (index>=0) {
			mnemonic=new String()+text.charAt(index+1);
			text=text.substring(0,index)+text.substring(index+1);
		}
		System.out.println(text);
		text = text.replace("#", "<br />");
		text = text.replace("\n", "<br />");
		text = "<html><center>" + text + "</center></html>";
		System.out.println(text);
		JButton b=new JButton(text);
	    b.setVerticalTextPosition(SwingConstants.BOTTOM);
	    b.setHorizontalTextPosition(SwingConstants.CENTER);		
		if (mnemonic!=null) { b.setMnemonic(mnemonic.charAt(0)); }
		b.setName(command);
		b.setIcon(icn);
		b.setToolTipText(tooltip);
		b.addActionListener(l);
		addButton(b);
	}
	
	public void addButton(String task, ImageIcon icn, String text, String tooltip, String command, ActionListener l) {
		addButton(task, icn, text, tooltip, command, l, Priority.TOP);
	}

	public void newGroup() {
	}

	public HRibbonBand(String title) {
		this.setLayout(new MigLayout("insets 1"));
		l_title = new JLabel();
		
		p_buttons = new JPanel();
		p_buttons.setLayout(new MigLayout("insets 0, gap 0, fill"));
		
		this.add(p_buttons, "height 90%, growy, wrap");
		this.add(l_title,"height 10%, center, growx, wrap");
		this.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.gray));
		
		setTitle(title);
	}


}
