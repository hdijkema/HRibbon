package nl.dykema.hribbon.spikes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import net.miginfocom.swing.MigLayout;
import nl.dykema.hribbon.HRibbon;
import nl.dykema.hribbon.HRibbonBand;
import nl.dykema.hribbon.HRibbonTask;
import nl.dykema.hribbon.utils.IconFactory;

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
			    	try {
			    	    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
			    	        if ("Nimbus".equals(info.getName())) {
			    	            UIManager.setLookAndFeel(info.getClassName());
			    	            break;
			    	        }
			    	    }
			    	} catch (Exception e) {
			    	    // If Nimbus is not available, you can set the GUI to another look and feel.
			    	}
			      } catch(Exception e) {
			    }	
			      
				
				_frame=new JFrame();
				_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
				JPanel p=new JPanel();
				p.setLayout(new MigLayout("insets 0,fill"));
				
				HRibbon ribbon = new HRibbon();
				
				HRibbonBand patientenBand = new HRibbonBand("Patienten");
				patientenBand.addButton("Patienten", readIcon("consult-open"), _.t("_Open een\nconsult"), _.t("Open een consult."), "consult-open", l);
				patientenBand.addButton("Patienten", readIcon("consult-new"),  _.t("_Nieuw\nconsult"),  _.t("Maak een nieuw consult aan."), "consult-new", l);
				patientenBand.addButton("Patienten", readIcon("consult-remove"), _.t("_Verwijder\nconsult."), _.t("Verwijder een consult"), "consult-remove", l);

				HRibbonBand main = new HRibbonBand("main");
				main.addButton("main", readIcon("save"), _.t("Op_slaan"), _.t("Sla de wijzigingen van een patient of casus op."), "save", l);
				main.addButton("main", readIcon("voorkeuren") ,_.t("Voor_keuren"),_.t("Stel de voorkeuren voor de applicatie in"), "voorkeuren",
						new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								System.out.println("Preferences called");
							}
				});
				main.addButton("main", readIcon("beeindigen"), _.t("_Be�indigen"), _.t("Programma be�indigen"), "afsluiten", new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						_frame.setVisible(false);
						System.exit(0);
					}
				});
				
				HRibbonTask anamneseTask = new HRibbonTask("Anamnese", main, patientenBand);

				HRibbonBand repBand = new HRibbonBand("Repertorisatie");
				repBand.addButton("Repertorisatie", readIcon("rep-new"), _.t("_Nieuwe\nrepertorisatie"), _.t("Maak een nieuwe repertorisatie aan"), "rep-new",l);
				repBand.addButton("Repertorisatie", readIcon("rep-del"), _.t("_Verwijder\nrepertorisatie"), _.t("Verwijder de huidig geselecteerde repertorisatie"), "rep-del",l);
				repBand.addButton("Repertorisatie", readIcon("print"), _.t("_Print\nrepertorisatie"), _.t("Druk het repertorisatie resultaat af"), "rep-print", l);
				
				HRibbonTask repTask = new HRibbonTask("Repertorisation", repBand);
				
				ribbon.addTask(anamneseTask);
				ribbon.addTask(repTask);
				ribbon.addRunnable(anamneseTask, new Runnable() { public void run() { System.out.println("anamneseTask"); } } );
				ribbon.addRunnable(repTask, new Runnable() { public void run() { System.out.println("repTask"); } } );
				p.add(ribbon,"north,growx,wrap");
				
				JPanel panel=new JPanel();
				panel.setLayout(new MigLayout("insets 0,fill"));
				
				JEditorPane edt = new JEditorPane();
				panel.add(edt,"growx,growy");
				
				p.add(panel,"growx,growy");
				
				_frame.add(p);
				_frame.pack();
				_frame.setSize(800,400);
				_frame.setVisible(true);
			}
		});
		
	}

	protected static ImageIcon readIcon(String name) {
		return IconFactory.readIcon("nl.dykema.hribbon.resources", name);
	}
	
}