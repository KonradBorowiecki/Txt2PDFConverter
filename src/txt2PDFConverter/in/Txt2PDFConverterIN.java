package txt2PDFConverter.in;

import info.clearthought.layout.TableLayout;
import java.io.File;
import java.io.FileFilter;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ToolTipManager;
import txt2PDFConverter.CommonPanel;
import txt2PDFConverter.IFileSelectionPanel;
import txt2PDFConverter.ProgramMenuBar;

/**
 * Creates a PDF file in memory from a given text file.
 * The program works only IN its launch folder. It grabs all text files
 * in the folder and converts one which user selects to pdf. The program
 * to work must be in a folder with txt files.
 * @author Konrad Borowiecki
 */
public class Txt2PDFConverterIN extends JFrame implements IFileSelectionPanel
{
    private static final long serialVersionUID = 1L;
    private CommonPanel commonPanel;
    private JComboBox fileNamesCB;
    private Txt2PDFConverterIN()
    {
	ToolTipManager tm = ToolTipManager.sharedInstance();
	tm.setDismissDelay(7000);
	tm.setInitialDelay(100);
	tm.setReshowDelay(100);
	setTitle("Txt2PDFConverter v.IN");
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setSize(400, 300);
	//create components
	JLabel infoL = new JLabel("<html><p>&nbsp Select a 'txt' file from the drop box which you want to convert to 'pdf'.</p>");
	this.fileNamesCB = new JComboBox();
	double p = TableLayout.PREFERRED;
	TableLayout topPL = new TableLayout(new double[]
		{
		    .5, p, .5
		}, new double[]
		{
		    .5, p, 5, p, .5
		});
	JPanel topP = new JPanel(topPL);
	topP.add(infoL, "1,1,L,C");
	topP.add(fileNamesCB, "1,3,C,C");

	this.commonPanel = new CommonPanel(this);

	TableLayout mainPL = new TableLayout(new double[]
		{
		    5, p, 5
		}, new double[]
		{
		    5, p, 5, p, 5
		});
	JPanel mainP = new JPanel(mainPL);
	mainP.add(topP, "1,1,L,C");
	mainP.add(commonPanel, "1,3,C,C");

	setContentPane(mainP);
	setJMenuBar(new ProgramMenuBar(this));
	//Display the window.
	pack();
	setLocationRelativeTo(null);
	setVisible(true);

	File currentFolder = new File(".");
	FileFilter txtFileFilter = new FileFilter()
	{
	    public boolean accept(File file)
	    {
		if(!file.isDirectory())
		{
		    String fileName = file.getName();
		    if(fileName.lastIndexOf('.') < 0)
			return false;
		    if(fileName.substring(fileName.lastIndexOf('.') + 1,
			    fileName.length()).equalsIgnoreCase("txt"))
			return true;
		}
		return false;
	    }

	};

	File[] files = currentFolder.listFiles(txtFileFilter);
	//show a communicate and exit when it is closed
	if(files.length <= 0)
	{
	    JOptionPane.showConfirmDialog(this, "<html>Before you can convert a 'txt' file to 'pdf'"
		    + "<br> using this program, first you have to copy the program "
		    + "<br> to a folder where some 'txt' files are located and run it again.",
		    "No 'txt' file was found.", JOptionPane.CLOSED_OPTION);
	    //close the program since no txt files are in this folder we cannot do a thing
	    System.exit(1);
	}
	else//populate fileNamesCB if txt files are found
	
	    for(int i = 0; i < files.length; i++)
		this.fileNamesCB.addItem(files[i].getName());
    }

    public String getInputFileName()
    {
	String selectedFileName = (String) fileNamesCB.getSelectedItem();
	return selectedFileName;
    }

    public String getOutputFileName()
    {
	String selectedFileName = (String) fileNamesCB.getSelectedItem();
	if(selectedFileName != null)
	    return selectedFileName.substring(0, selectedFileName.lastIndexOf('.')) + ".pdf";
	else
	    return null;
    }

    /** This is not supported since we have no option of setting an input file here.
     * We can only deal we txt files found in the program's start folder.*/
    public void setInputFileName(String inputFileName)
    {
	throw new UnsupportedOperationException("Not supported yet.");
    }

    /** This is not supported since we have no option of setting an output file here.
     * The output file's name is that of the input file just with pdf extension.*/
    public void setOutputFileName(String outputFileName)
    {
	throw new UnsupportedOperationException("Not supported yet.");
    }

    public static void main(String[] args)
    {
	//Schedule a job for the event-dispatching thread:
	//creating and showing this application's GUI.
	javax.swing.SwingUtilities.invokeLater(new Runnable()
	{
	    public void run()
	    {
		new Txt2PDFConverterIN();
	    }

	});
    }

}
