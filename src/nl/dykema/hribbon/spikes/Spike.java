package nl.dykema.hribbon.spikes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import net.miginfocom.swing.MigLayout;
import nl.dykema.hribbon.HRibbon;
import nl.dykema.hribbon.HRibbonBand;
import nl.dykema.hribbon.HRibbonTask;
import nl.dykema.hribbon.HRibbonTitleBar;

import org.pushingpixels.substance.api.skin.SubstanceOffice2013LookAndFeel;

class _ {
	static public String t(String txt) { return txt; }
}

public class Spike {
	

	public static void main(String argv[]) {
		
    	JFrame.setDefaultLookAndFeelDecorated(true);
		
		SwingUtilities.invokeLater(new Runnable() {
			private JFrame _frame;
			
			ActionListener l=new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					System.out.println(arg0);
				}
			};
			
			//private Logger logger;
			
			public void run() {
			    try {
			    	UIManager.setLookAndFeel(new SubstanceOffice2013LookAndFeel());

			    } catch(Exception e) {
			    	e.printStackTrace();
			    }	
			      
				
				_frame=new JFrame();
				_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				_frame.setTitle("Dit is de title");;
				
				JPanel p=new JPanel();
				p.setLayout(new MigLayout("insets 0, fill")); 
				
				HRibbon ribbon = new HRibbon();
				
				HRibbonBand patientenBand = new HRibbonBand("Patienten", "nl.dykema.hribbon.resources");
				patientenBand.addButton("Patienten", "consult-open", _.t("_Open een\nconsult"), _.t("Open een consult."), "consult-open", l);
				patientenBand.addButton("Patienten", "consult-new",  _.t("_Nieuw\nconsult"),  _.t("Maak een nieuw consult aan."), "consult-new", l);
				patientenBand.addButton("Patienten", "consult-remove", _.t("_Verwijder\nconsult."), _.t("Verwijder een consult"), "consult-remove", l);
				patientenBand.addToggleButton("Patienten", "rep-new", _.t("even kijken"), _.t("tooltip"), "rep-new", l);
				patientenBand.addComponent(new JButton("Test"), HRibbonBand.Priority.TOP);

				HRibbonBand main = new HRibbonBand("main", "nl.dykema.hribbon.resources");
				main.addButton("main", "save", _.t("Op_slaan"), _.t("Sla de wijzigingen van een patient of casus op."), "save", l);
				main.addButton("main", "voorkeuren",_.t("Voor_keuren"),_.t("Stel de voorkeuren voor de applicatie in"), "voorkeuren",
						new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								System.out.println("Preferences called");
							}
				});
				main.addButton("main", "beeindigen", _.t("_Beëindigen"), _.t("Programma beëindigen"), "afsluiten", new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						_frame.setVisible(false);
						System.exit(0);
					}
				});
				
				HRibbonTask anamneseTask = new HRibbonTask("Anamnese", main, patientenBand);

				HRibbonBand repBand = new HRibbonBand("Repertorisatie", "nl.dykema.hribbon.resources");
				repBand.addButton("Repertorisatie", "rep-new", _.t("_Nieuwe\nrepertorisatie"), _.t("Maak een nieuwe repertorisatie aan"), "rep-new",l);
				repBand.addButton("Repertorisatie", "rep-del", _.t("_Verwijder\nrepertorisatie"), _.t("Verwijder de huidig geselecteerde repertorisatie"), "rep-del",l);
				repBand.addButton("Repertorisatie", "print", _.t("_Print\nrepertorisatie"), _.t("Druk het repertorisatie resultaat af"), "rep-print", l);
				
				repBand.addButton("Repertorisatie", "rep-new", _.t("_Nieuwe repertorisatie"), _.t("Maak een nieuwe repertorisatie aan (2)"), "rep-new", l, HRibbonBand.Priority.MEDIUM);
				repBand.addButton("Repertorisatie", "rep-del", _.t("_Verwijder repertorisatie"), _.t("Verwijder de huidig geselecteerde repertorisatie"), "rep-del", l, HRibbonBand.Priority.MEDIUM);
				repBand.addButton("Repertorisatie", "print", _.t("_Print repertorisatie"), _.t("Druk het repertorisatie resultaat af"), "rep-print", l, HRibbonBand.Priority.MEDIUM);
				
				HRibbonBand ffBand = new HRibbonBand("FF", "nl.dykema.hribbon.resources");
				JMenuItem mi1 = new JMenuItem("test1");
				JMenuItem mi2 = new JMenuItem("Grade 2");
				JMenuItem mi3 = new JMenuItem("Grade 3");
				JPopupMenu mn = new JPopupMenu();
				mn.add(mi1);
				mn.add(mi2);
				mn.add(mi3);
				ffBand.addButton("FF", "rep-new", _.t("_Nieuwe repertorisatie ja"), _.t("Maak een nieuwe repertorisatie aan (2)"), "rep-new", l, HRibbonBand.Priority.MEDIUM);
				ffBand.addMenu("Diversen", "rep-del", mn, HRibbonBand.Priority.MEDIUM);
				ffBand.newGroup();

				repBand.addButton("Repertorisatie", "rep-new", _.t("_Nieuwe repertorisatie"), _.t("Maak een nieuwe repertorisatie aan (2)"), "rep-new", l, HRibbonBand.Priority.LOW);
				repBand.addButton("Repertorisatie", "rep-del", _.t("_Verwijder repertorisatie"), _.t("Verwijder de huidig geselecteerde repertorisatie"), "rep-del", l, HRibbonBand.Priority.LOW);
				repBand.addButton("Repertorisatie", "print", _.t("_Print repertorisatie"), _.t("Druk het repertorisatie resultaat af"), "rep-print", l, HRibbonBand.Priority.LOW);
				repBand.addButton("Patienten", "consult-open", _.t("_Open een\nconsult"), _.t("Open een consult."), "consult-open", l, HRibbonBand.Priority.LOW);
				repBand.addButton("Patienten", "consult-new",  _.t("_Nieuw\nconsult"),  _.t("Maak een nieuw consult aan."), "consult-new", l, HRibbonBand.Priority.LOW);
				repBand.addButton("Patienten", "consult-remove", _.t("_Verwijder\nconsult."), _.t("Verwijder een consult"), "consult-remove", l, HRibbonBand.Priority.LOW);
				
				
				HRibbonTask repTask = new HRibbonTask("Repertorisation", repBand, ffBand);
				
				ribbon.addTask(anamneseTask);
				ribbon.addTask(repTask);
				ribbon.addRunnable(anamneseTask, new Runnable() { public void run() { System.out.println("anamneseTask"); } } );
				ribbon.addRunnable(repTask, new Runnable() { public void run() { System.out.println("repTask"); } } );
				p.add(ribbon, "north, growx");
				
				JPanel panel=new JPanel();
				panel.setLayout(new MigLayout("insets 0,fill"));
				
				JEditorPane edt = new JEditorPane();
				panel.add(edt, "growx, growy");
				
				p.add(panel, "growx, growy");
				
				_frame.add(p);
				_frame.pack();
				_frame.setSize(800,400);
				_frame.setVisible(true);
				
				try {
					HRibbonTitleBar.prepare(_frame, "nl.dykema.hribbon.resources");
					HRibbonTitleBar.addButton("consult-new", "Dit is een consult new button", "consult-new", l);
					HRibbonTitleBar.addButton("beeindigen", _.t("Programma beëindigen"), "afsluiten", new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							_frame.setVisible(false);
							System.exit(0);
						}
					});				
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
	}

}
