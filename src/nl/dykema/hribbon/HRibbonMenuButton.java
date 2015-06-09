package nl.dykema.hribbon;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPopupMenu;

import nl.dykema.hribbon.utils.ExpandIcon;

public class HRibbonMenuButton extends HRibbonButton {

	private static final long serialVersionUID = 1L;

	private JLabel 		_label = new JLabel();
	private JPopupMenu 	_menu = null;

	public void setMenu(JPopupMenu mnu) {
		_menu = mnu;
	}

	public JPopupMenu getMenu() {
		return _menu;
	}

	public HRibbonMenuButton(String txt) {
		this(new JLabel(txt), null, null);
	}

	public HRibbonMenuButton(String txt, Icon icn) {
		this(new JLabel(txt), icn, null);
	}

	public HRibbonMenuButton(String txt, JPopupMenu mnu) {
		this(new JLabel(txt), null, mnu);
	}

	public HRibbonMenuButton(String txt, Icon icn, JPopupMenu mnu) {
		this(new JLabel(txt), icn, mnu);
	}

	protected HRibbonMenuButton(JLabel l, Icon icn, JPopupMenu mnu) {
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
		_menu = mnu;
		super.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				if (_menu != null) { _menu.show(HRibbonMenuButton.this, 0, HRibbonMenuButton.this.getHeight()); }
			}
		});

	}
}
