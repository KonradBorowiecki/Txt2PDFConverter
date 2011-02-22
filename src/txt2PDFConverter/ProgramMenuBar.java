package txt2PDFConverter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import utility.FileOperations;

/**
 *
 * @author Konrad Borowiecki
 */
public class ProgramMenuBar extends JMenuBar implements ActionListener
{
    private static final long serialVersionUID = 1L;
    private JMenu additionalM;
    private JMenuItem addFontMI;

    private JMenu helpM;
    private JMenuItem aboutMI;
    private JFrame ownerFrame;
    private JFileChooser addFontFC;
    public ProgramMenuBar(JFrame ownerFrame)
    {
	this.ownerFrame = ownerFrame;
	this.additionalM = new JMenu("Additional");
	this.addFontMI = new JMenuItem("Add font...");
	addFontMI.addActionListener(this);
	additionalM.add(addFontMI);
	this.helpM = new JMenu("Help");
	this.aboutMI = new JMenuItem("About");
	aboutMI.addActionListener(this);
	helpM.add(aboutMI);
	add(additionalM);
	add(helpM);
	this.addFontFC = new JFileChooser(".");
	addFontFC.setDialogTitle("Select a font to add.");
//	addFontFC.setFileHidingEnabled(false);
//	addFontFC.setControlButtonsAreShown(false);
    }

    public void actionPerformed(ActionEvent e)
    {
	Object source = e.getSource();
	if(source == addFontMI)
	{
	    int returnVal = addFontFC.showOpenDialog(ownerFrame);
	    if(returnVal == JFileChooser.APPROVE_OPTION)
	    {
		File selectedFile = addFontFC.getSelectedFile();
		System.out.println("You chose to open this file: "
			+ selectedFile.getName());
		try
		{
		    ClassLoader classLoader = getClass().getClassLoader();
		    File fontsFolder = new File(classLoader.getResource("fonts").getFile());
		    System.out.println("file.exists="+fontsFolder.exists()
			    +"; file.getPath()="+fontsFolder.getPath());

		    File saveFile = new File(fontsFolder.getPath() +"/"+ selectedFile.getName());
		    if(!saveFile.exists())
		    {
			 System.out.println("saveFile.getPath()="+saveFile.getPath());
			 FileOperations.copyFile(selectedFile,saveFile);
		    }
		    else
			 JOptionPane.showMessageDialog(ownerFrame, "<html><p>The file by this name already is"
				 + "<br>in the fonts folder, overwrite is not allowed</p>");
		}catch(IOException ex)
		{
		    Logger.getLogger(ProgramMenuBar.class.getName()).log(Level.SEVERE, null, ex);
		}
	    }
	}
	else if(source == aboutMI)
	{
	    System.out.println("aboutMI pressed");
	    JOptionPane.showMessageDialog(ownerFrame, "<html><p>The application was developed by"
		    + "<br> Konrad Borowiecki"
		    + "<br><br> email: <i>konradborowiecki@gmail.com</i></p>");
	}
    }

}
