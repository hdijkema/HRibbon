package nl.dykema.hribbon.utils;

import java.awt.Font;
import java.util.Enumeration;

import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

public class CorrectDPILaf {
	
	public static void correctLafForDPI() {
		float factor = 1.5f;
		Enumeration test = UIManager.getDefaults().keys();
		

		while ( test.hasMoreElements() ) {  

			Object key = test.nextElement();  
			Object value = UIManager.get( key );  
			System.out.println(key + " = " + value);
			if ( value instanceof Font ) {  
				Font f = (Font) value;
				UIManager.put( key, f.deriveFont(f.getSize2D() * factor) );  
			} else if (value instanceof FontUIResource) {
				FontUIResource f = (FontUIResource) value;
				UIManager.put( key, new FontUIResource(f.deriveFont(f.getSize2D() * factor)));
			}
		}		
		
		
	}

}
