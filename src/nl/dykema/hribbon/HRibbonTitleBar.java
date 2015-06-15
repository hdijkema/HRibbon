package nl.dykema.hribbon;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;
import java.awt.image.RescaleOp;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JSeparator;

import net.miginfocom.swing.MigLayout;
import nl.dykema.hribbon.HRibbonBand.Priority;
import nl.dykema.hribbon.utils.BufferedImageBuilder;
import nl.dykema.hribbon.utils.IconFactory;

import org.pushingpixels.substance.api.SubstanceLookAndFeel;
import org.pushingpixels.substance.internal.ui.SubstanceRootPaneUI;
import org.pushingpixels.substance.internal.utils.SubstanceTitlePane;

public class HRibbonTitleBar extends SubstanceTitlePane {
	
    private static final long serialVersionUID = 1L;

    private static JFrame 		_frame;
    private static JComponent 	_title;
	private static String 		s_resourceLocation;
	private static boolean      _initialized = false;
	private static int 			_nth = 0;
	private static int			_seps = 0;
	private static Vector<Runnable> _actions;
	private static ComponentAdapter _runOnce;
	private static float			_brightenfactor = 1.2f;
	
	public HRibbonTitleBar(JRootPane root, SubstanceRootPaneUI ui) {
        super(root, ui);
	}
	
	public static void prepare(JFrame frame, String resourceLocation) throws Exception {
		if (frame.isVisible()) { throw new Exception("Execute these methods befor your frame is visible"); }
		_frame = frame;
		s_resourceLocation = resourceLocation;
		_actions = new Vector<Runnable>();
		_actions.add(new Runnable() {
			public void run() {
				_title = SubstanceLookAndFeel.getTitlePaneComponent(frame);
			}
		});
		_initialized = true;

		_runOnce = new ComponentAdapter() {
			public void componentShown(ComponentEvent e) {
				for(Runnable r : _actions) {
					r.run();
				}
				frame.removeComponentListener(_runOnce);
			}
		};
		
		frame.addComponentListener(_runOnce);
	}
	
	public static void addButton(String imageName, String tooltip, String command, ActionListener l) throws Exception {
		if (!_initialized) { throw new Exception("Use the prepare() method first"); }
		_actions.add(new Runnable() {
			public void run() {
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
				b.setContentAreaFilled(false);
				BufferedImage img = BufferedImageBuilder.bufferImage(icn.getImage(), BufferedImage.TYPE_INT_ARGB);
		        /*ImageFilter colorfilter = new RGBImageFilter() {
	                  public int filterRGB(int x, int y, int rgb) {
	                	  System.out.println(x + ", "+ y+ ", "+ rgb);
	                	  //if ((rgb&0x00ffffff) == 0xffffff) {
	                	  //if (rgb == 0) {
	                		  rgb = 0xffd5e1f2;
	                		  //rgb = 0;
	                	  //}
	                	  return rgb;
	                  }
		        };
		        ImageProducer prod = new FilteredImageSource(img.getSource(), colorfilter);
		        Image image = Toolkit.getDefaultToolkit().createImage(prod);
		 		*/
				RescaleOp op = new RescaleOp(_brightenfactor, 0, null);
				Image image = op.filter(img, img);
				ImageIcon ricn = new ImageIcon(image);
				b.setRolloverIcon(ricn);
				_title.add(b);
			}
		});
	}

	public static void addToggleButton(String task, String imageName, String text, String tooltip, String command, ActionListener l) throws Exception {
		if (!_initialized) { throw new Exception("Use the prepare() method first"); }
		_actions.add(new Runnable() {
			public void run() {
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
				b.setContentAreaFilled(false);
				BufferedImage img = BufferedImageBuilder.bufferImage(icn.getImage(), BufferedImage.TYPE_INT_ARGB);
				RescaleOp op = new RescaleOp(_brightenfactor, 0, null);
				Image image = op.filter(img, img);
				ImageIcon ricn = new ImageIcon(image);
				b.setRolloverIcon(ricn);
				_title.add(b);
			}
		});
	}
	
	public static void addSeparator() throws Exception {
		if (!_initialized) { throw new Exception("Use the prepare() method first"); }
		_actions.add(new Runnable() {
			public void run() {
				JSeparator sep = new JSeparator();
				sep.putClientProperty("substancelaf.internal.titlePane.extraComponentKind", SubstanceTitlePane.ExtraComponentKind.LEADING);
				Rectangle r = _title.getBounds();
				int width = r.height / 3;
				sep.setBounds(r.x + _nth * r.height + _seps, r.y, width, r.height - 1);
				_seps += width;
			}
		});
	}

}
