package nl.dykema.hribbon.utils;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.swing.ImageIcon;

public class IconFactory {

	private interface Changer {
		public String change(String path, String name);
	}

	public static int ptToScreenPx(int pt) {
		int pixels_per_inch = Toolkit.getDefaultToolkit().getScreenResolution();
		System.out.println(pixels_per_inch);
		double px_to_pt_factor = ((double) pixels_per_inch) / 72.0;
		return (int) (pt * px_to_pt_factor);
	}

	public static ImageIcon readIcon(String resourcePath, String name, int width_in_pt, int height_in_pt) {
		Class cls;
		try {
			int width_in_px = ptToScreenPx(width_in_pt);
			int height_in_px = ptToScreenPx(height_in_pt);
			
			cls = Class.forName("nl.dykema.hribbon.utils.IconFactory");
			ClassLoader cl = cls.getClassLoader();
			URL u = null;
			Changer[] changes = { new Changer() { public String change(String path, String name) { return path + name + ".png"; } },
					      		  new Changer() { public String change(String path, String name) { return path + name.replace('-', '_') + ".png"; } },
					      		  new Changer() { public String change(String path, String name) { return path + name + ".svg"; } },
					      		  new Changer() { public String change(String path, String name) { return path + name.replace('-', '_') + ".svg"; } },
					      		  new Changer() { public String change(String path, String name) { return path + name + ".jpg"; } },
					      		  new Changer() { public String change(String path, String name) { return path + name.replace('-', '_') + ".jpg"; } },
								};
			resourcePath = resourcePath.replace('.', '/');
			if (resourcePath.charAt(0) == '/') { resourcePath = resourcePath.substring(1); }
			if (!resourcePath.endsWith("/")) { resourcePath += "/"; }
			
			for (Changer chg: changes) {
				String path = chg.change(resourcePath, name);
				u = cl.getResource(path);
				if (u != null) {
					ImageIcon im = new ImageIcon(u);
					BufferedImage img = BufferedImageBuilder.getScaledInstance(im.getImage(), width_in_px, height_in_px);
					return new ImageIcon(img);
				}
			}
			
			return null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static ImageIcon readIcon(String resourcePath, String name) {
		return readIcon(resourcePath, name, 18, 18);
	}
	
	
}
