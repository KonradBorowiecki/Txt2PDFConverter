package txt2PDFConverter;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import txt2PDFConverter.out.Txt2PDFConverterOUT;
import utility.FileOperations;

/**
 * The menu used by the program.
 * @author Konrad Borowiecki
 */
public class ProgramMenuBar extends JMenuBar {
	private static final long serialVersionUID = 1L;
	private JMenu additionalM;
	private JMenuItem addFontMI;
	private JMenu helpM;
	private JMenuItem aboutMI;
	private JFrame ownerFrame;
	private JFileChooser addFontFC;

	public ProgramMenuBar(JFrame ownerFrame) {
		this.ownerFrame = ownerFrame;
		this.additionalM = new JMenu("Additional");
		this.addFontMI = new JMenuItem(addFontAction);
		additionalM.add(addFontMI);
		this.helpM = new JMenu("Help");
		this.aboutMI = new JMenuItem(aboutAction);
		helpM.add(aboutMI);
		if(ownerFrame instanceof Txt2PDFConverterOUT)
			add(additionalM);
		add(helpM);
		this.addFontFC = new JFileChooser(".");
		addFontFC.setDialogTitle("Select a font to add.");
	}
	private AbstractAction addFontAction = new AbstractAction("Add font...") {
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			int returnVal = addFontFC.showOpenDialog(ownerFrame);
			if(returnVal == JFileChooser.APPROVE_OPTION) {
				File selectedFile = addFontFC.getSelectedFile();
				System.out.println("You chose to open this file: "
						+ selectedFile.getName());
				try {
					File fontsFolder = new File("fonts");
					System.out.println("file.exists=" + fontsFolder.exists()
							+ "; file.getPath()=" + fontsFolder.getPath());
					File saveFile = new File(fontsFolder.getPath() + "/" + selectedFile.getName());
					if(!saveFile.exists()) {
						System.out.println("saveFile.getPath()=" + saveFile.getPath());
						FileOperations.copyFile(selectedFile, saveFile);
						JOptionPane.showMessageDialog(ownerFrame, 
								"<html><p>The font file was successfully copied</p>");
					}
					else
						JOptionPane.showMessageDialog(ownerFrame, 
								"<html><p>The file by this name is already "
								+ "<br>in the fonts folder, overwrite is not allowed</p>");
				}catch(IOException ex) {
					Logger.getLogger(ProgramMenuBar.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		}
	};
	private AbstractAction aboutAction = new AbstractAction("About") {
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			System.out.println("aboutMI pressed");
			JOptionPane.showMessageDialog(ownerFrame, "<html><p>The application was developed by"
					+ "<br> Konrad Borowiecki"
					+ "<br><br> email: <i>konradborowiecki@gmail.com</i></p>");
		}
	};
}
