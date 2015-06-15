package nl.dykema.hribbon.utils;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import nl.dykema.hribbon.HRibbonBand;
import nl.dykema.hribbon.HRibbonTask;

public class IconFactory {

	private interface Changer {
		public String change(String path, String name);
	}
	
	public static ImageIcon deriveIcon(ImageIcon src, int width_in_pt, int height_in_pt) {
		int width_in_px = DeviceConverter.ptToScreenPx(width_in_pt);
		int height_in_px = DeviceConverter.ptToScreenPx(height_in_pt);
		BufferedImage img = BufferedImageBuilder.getScaledInstance(src.getImage(), width_in_px, height_in_px);
		return new ImageIcon(img);
	}

	private static ImageIcon readIcon(String resourcePath, String name, File f, int width_in_pt, int height_in_pt) {
		Class cls;
		try {
			int width_in_px = DeviceConverter.ptToScreenPx(width_in_pt);
			int height_in_px = DeviceConverter.ptToScreenPx(height_in_pt);
			
			if (f == null) {
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
			} else {
				URL u = f.toURI().toURL();
				ImageIcon im = new ImageIcon(u);
				BufferedImage img = BufferedImageBuilder.getScaledInstance(im.getImage(), width_in_px, height_in_px);
				return new ImageIcon(img);
			}
			
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static ImageIcon readIcon(String resourcePath, String name, int priority) {
		int pts = (priority == HRibbonBand.Priority.TOP) ? 18 : 12;
		return readIcon(resourcePath, name, null, pts, pts);
	}
	
	public static ImageIcon readIcon(File fromFile, int priority) {
		int pts = (priority == HRibbonBand.Priority.TOP) ? 18 : 12;
		return readIcon(null, null, fromFile, pts, pts);
	}
	
	
}
