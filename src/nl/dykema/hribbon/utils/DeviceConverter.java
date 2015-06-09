package nl.dykema.hribbon.utils;

import java.awt.Toolkit;

public class DeviceConverter {

	public static int ptToScreenPx(int pt) {
		int pixels_per_inch = Toolkit.getDefaultToolkit().getScreenResolution();
		System.out.println(pixels_per_inch);
		double px_to_pt_factor = ((double) pixels_per_inch) / 72.0;
		return (int) (pt * px_to_pt_factor);
	}

}
