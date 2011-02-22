package txt2PDFConverter.out;

import info.clearthought.layout.TableLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ToolTipManager;
import javax.swing.filechooser.FileNameExtensionFilter;
import txt2PDFConverter.CommonPanel;
import txt2PDFConverter.IFileSelectionPanel;
import txt2PDFConverter.ProgramMenuBar;

/**
 * Creates a PDF file in memory from a given text file.
 * The program can work OUTside of its launch folder.
 * User needs to specify input and output file locations.
 * @author Konrad Borowiecki
 */
public class Txt2PDFConverterOUT extends JFrame implements IFileSelectionPanel, ActionListener
{
    private static final long serialVersionUID = 1L;
    private CommonPanel commonPanel;
    /** Field to present path and name of the input file.*/
    private JTextField inputTF;
    /** Field to present path and name of the output file.*/
    private JTextField outputTF;
    /** Button opening file chooser where user can locate the input file.*/
    private JButton inputBrowseB;
    /** Button opening file chooser where user can specify location and name of the output file.*/
    private JButton outputBrowseB;
    /** The file chooser finding txt files. Used to find location of the input file.*/
    private JFileChooser txtFileChooser;
    /** The file chooser finding pdf files. Used to find/specify location of the output file.*/
    private JFileChooser pdfFileChooser;
    private Txt2PDFConverterOUT()
    {
	ToolTipManager tm = ToolTipManager.sharedInstance();
	tm.setDismissDelay(7000);
	tm.setInitialDelay(100);
	tm.setReshowDelay(100);
	setTitle("Txt2PDFConverter v.OUT");
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setSize(400, 300);

	int textFieldSize = 25;
	this.inputTF = new JTextField(textFieldSize);
	this.outputTF = new JTextField(textFieldSize);
	this.txtFileChooser = new JFileChooser(new File("."));
	txtFileChooser.setDialogTitle("Select a 'txt' file which you want to convert.");
	txtFileChooser.setAcceptAllFileFilterUsed(false);
	FileNameExtensionFilter txtFilter = new FileNameExtensionFilter(
		"TXT files", "txt");
	txtFileChooser.setFileFilter(txtFilter);
	this.pdfFileChooser = new JFileChooser(new File("."));
	pdfFileChooser.setDialogTitle("What is the output file? Select a 'pdf' file or name a new one.");
	pdfFileChooser.setAcceptAllFileFilterUsed(false);
	FileNameExtensionFilter pdfFilter = new FileNameExtensionFilter(
		"PDF files", "pdf");
	pdfFileChooser.setFileFilter(pdfFilter);

	this.inputBrowseB = new JButton("Browse");
	inputBrowseB.addActionListener(this);
	JPanel inputP = new JPanel();
	inputP.add(inputTF);
	inputP.add(inputBrowseB);
	this.outputBrowseB = new JButton("Browse");
	outputBrowseB.addActionListener(this);
	JPanel outputP = new JPanel();
	outputP.add(outputTF);
	outputP.add(outputBrowseB);

	//create components
	JLabel infoL = new JLabel("<html><p>&nbsp Specify input and output file names"
		+ ", respectively, in the fields below. &nbsp </p>");
	double p = TableLayout.PREFERRED;
	TableLayout topPL = new TableLayout(new double[]
		{
		    .5, p, .5
		}, new double[]
		{
		    .5, p, 5, p, 5, p, .5
		});
	JPanel topP = new JPanel(topPL);
	topP.add(infoL, "1,1,L,C");
	topP.add(inputP, "1,3,C,C");
	topP.add(outputP, "1,5,C,C");

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
    }

    public String getInputFileName()
    {
	return inputTF.getText();
    }

    public String getOutputFileName()
    {
	return outputTF.getText();
    }

    public void setInputFileName(String inputFileName)
    {
	inputTF.setText(inputFileName);
    }

    public void setOutputFileName(String outputFileName)
    {
	outputTF.setText(outputFileName);
    }

    public void actionPerformed(ActionEvent e)
    {
	Object source = e.getSource();
	if(source == inputBrowseB)
	{
	    int returnVal = txtFileChooser.showOpenDialog(this);
	    if(returnVal == JFileChooser.APPROVE_OPTION)
	    {
		System.out.println("You chose to open this file: "
			+ txtFileChooser.getSelectedFile().getName());
		String path = txtFileChooser.getSelectedFile().getPath();
		System.out.println("path =" + path);
		inputTF.setText(path);
	    }
	}
	else if(source == outputBrowseB)
	{
	    int returnVal = pdfFileChooser.showOpenDialog(this);
	    if(returnVal == JFileChooser.APPROVE_OPTION)
	    {
		System.out.println("You chose to open this file: "
			+ pdfFileChooser.getSelectedFile().getName());
		String path = pdfFileChooser.getSelectedFile().getPath();
		System.out.println("path =" + path);
		outputTF.setText(path);
	    }
	}
    }

    public static void main(String[] args)
    {
	//Schedule a job for the event-dispatching thread:
	//creating and showing this application's GUI.
	javax.swing.SwingUtilities.invokeLater(new Runnable()
	{
	    public void run()
	    {
		new Txt2PDFConverterOUT();
	    }

	});
    }

}
