package nl.dykema.hribbon;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JSeparator;

import net.miginfocom.swing.MigLayout;
import nl.dykema.hribbon.HRibbonBand.Priority;
import nl.dykema.hribbon.utils.IconFactory;

import org.pushingpixels.substance.api.SubstanceLookAndFeel;
import org.pushingpixels.substance.internal.ui.SubstanceRootPaneUI;
import org.pushingpixels.substance.internal.utils.SubstanceTitlePane;

public class HRibbonTitleBar extends SubstanceTitlePane {
	
    private static final long serialVersionUID = 1L;

    private static JFrame 		_frame;
    private static JPanel 		_buttons;
    private static JComponent 	_title;
	private static String 		s_resourceLocation;
	private static boolean      _initialized = false;
	private static int 			_nth = 0;
	private static int			_seps = 0;
	
	public HRibbonTitleBar(JRootPane root, SubstanceRootPaneUI ui) {
        super(root, ui);
	}
	
	private static void init(JFrame frame, String resourceLocation) {
		s_resourceLocation = resourceLocation;
		_frame = frame;
		_buttons = new JPanel();
		_buttons.setLayout(new MigLayout("insets 0, gap 1, fill"));
		_title = SubstanceLookAndFeel.getTitlePaneComponent(frame);
		//_title.setLayout(new MigLayout("insets 0, gap 1, fill"));
		//_buttons.putClientProperty("substancelaf.internal.titlePane.extraComponentKind", SubstanceTitlePane.ExtraComponentKind.LEADING);
		//_title.add(_buttons);
	}
	
	public static void prepare(JFrame frame, String resourceLocation) {
		init(frame, resourceLocation);
		_initialized = true;
	}
	
	public static void addButton(String imageName, String tooltip, String command, ActionListener l) throws Exception {
		if (!_initialized) { throw new Exception("Use the prepare() method first"); }
		HRibbonButton b=new HRibbonButton();
		b.setName(command);
		ImageIcon icn = IconFactory.readIcon(s_resourceLocation, imageName, HRibbonBand.Priority.TITLEBAR);
		b.setIcon(icn);
		b.setToolTipText(tooltip);
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ActionEvent n = new ActionEvent(e.getSource(), e.getID(), b.getName(), e.getWhen(), e.getModifiers());
				l.actionPerformed(n);
			}
		});
		b.putClientProperty("substancelaf.internal.titlePane.extraComponentKind", SubstanceTitlePane.ExtraComponentKind.LEADING);
		Rectangle r = _title.getBounds();
		_nth += 1;
		b.setBounds(r.x + _nth * r.height + _seps, r.y, r.height - 1, r.height - 1);
		_title.add(b);
	}

	public static void addToggleButton(String task, String imageName, String text, String tooltip, String command, ActionListener l) throws Exception {
		if (!_initialized) { throw new Exception("Use the prepare() method first"); }
		HRibbonToggleButton b=new HRibbonToggleButton();
		b.setName(command);
		ImageIcon icn = IconFactory.readIcon(s_resourceLocation, imageName, HRibbonBand.Priority.TITLEBAR);
		b.setIcon(icn);
		b.setToolTipText(tooltip);
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ActionEvent n = new ActionEvent(e.getSource(), e.getID(), b.getName(), e.getWhen(), e.getModifiers());
				l.actionPerformed(n);
			}
		});
		b.putClientProperty("substancelaf.internal.titlePane.extraComponentKind", SubstanceTitlePane.ExtraComponentKind.LEADING);
		Rectangle r = _title.getBounds();
		_nth += 1;
		b.setBounds(r.x + _nth * r.height + _seps, r.y, r.height - 1, r.height - 1);
		_title.add(b);
	}
	
	public static void addSeparator() {
		JSeparator sep = new JSeparator();
		sep.putClientProperty("substancelaf.internal.titlePane.extraComponentKind", SubstanceTitlePane.ExtraComponentKind.LEADING);
		Rectangle r = _title.getBounds();
		int width = r.height / 3;
		sep.setBounds(r.x + _nth * r.height + _seps, r.y, width, r.height - 1);
		_seps += width;
	}

}
