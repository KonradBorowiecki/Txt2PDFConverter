package txt2PDFConverter;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import info.clearthought.layout.TableLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import utility.ReadWriteTextFileWithEncoding;

/**
 * This panel contains a common part of every type of the converter program e.g.
 * IN (the program works only with 'txt' files within the current folder),
 * OUT (the program lets to locate a path to a file).
 * @author Konrad Borowiecki
 */
public class CommonPanel extends JPanel implements ActionListener
{
    private static final long serialVersionUID = 1L;
    /** Lets to select type of encoding to use when copying and saving data to a file. */
    private JComboBox encodingCB;
    /** Lets to select a type of font to use.*/
    private JComboBox fontCB;
    /** Lets to specify font size in float precision. */
    private JTextField fontSizeTF;
    /** Radio button marking that a specified font should be used. */
    private JRadioButton fontSizeSpecRB;
    /** Radio button marking that the text when writing should 
     * fit in a pdf page exactly as visible in a text file, 
     * taking all width of a page.*/
    private JRadioButton fitTextToWidthRB;
    /** Lets a user to select portrait layout of pdf pages. Default.*/
    private JRadioButton portraitRB;
    /** Lets a user to select landscape layout of pdf pages.*/
    private JRadioButton landscapeRB;
    /** Button which calls convert action for the settings specified by a user. */
    private JButton convertB;
    /** Button which closes the program. */
    private JButton exitB;
    /** If it is marked the output file will be overwritten if it already exists. */
    private JCheckBox overwriteOutputFileChB;
    /** Reference to panel where user can specify the name of file to convert and to output.*/
    private IFileSelectionPanel fileSelectionPanel;


    public CommonPanel(IFileSelectionPanel fileSelectionPanel)
    {
	this.fileSelectionPanel = fileSelectionPanel;
	this.fontSizeTF = new JTextField(4);
	fontSizeTF.setToolTipText("Enter the font size, e.g. 12, 10.5, 22.7");
	fontSizeTF.addKeyListener(new KeyAdapter() {
	    @Override
	     public void keyPressed(KeyEvent e)
	    {
		if(!fontSizeSpecRB.isSelected())
		    fontSizeSpecRB.setSelected(true);
	    }
	});
	fontSizeTF.addMouseListener(new MouseAdapter() {
	    @Override
	    public void mousePressed(MouseEvent e)
	    {
		super.mousePressed(e);
		if(!fontSizeSpecRB.isSelected())
		    fontSizeSpecRB.setSelected(true);
		System.out.println("fontSizeTF mousePressed");
	    }
	    @Override
	    public void mouseExited(MouseEvent e)
	    {
		MouseEvent event =  new MouseEvent(fontSizeSpecRB, e.getID(), e.getWhen(),
			e.getModifiers(), 1, 1, 1, e.isPopupTrigger(), e.getButton());
		fontSizeSpecRB.dispatchEvent(event);
	    }
	    @Override
	    public void mouseEntered(MouseEvent e)
	    {
		MouseEvent event =  new MouseEvent(fontSizeSpecRB, e.getID(), e.getWhen(),
			e.getModifiers(), 1, 1, 1, e.isPopupTrigger(), e.getButton());
		fontSizeSpecRB.dispatchEvent(event);
	    }
	});
	String fontSizeRBToolTip = "<html>When this option is selected it enforces that<br> the entered font size is used when creating the output file.";
	this.fontSizeSpecRB = new JRadioButton("Use the font size ");
	fontSizeSpecRB.setOpaque(false);
	fontSizeSpecRB.setToolTipText(fontSizeRBToolTip);
	JPanel fontSizeSpecPanel = new JPanel();
	fontSizeSpecPanel.setToolTipText(fontSizeRBToolTip);
	//pass events to radiobutton
	fontSizeSpecPanel.addMouseListener(new MouseAdapter() {
	    @Override
	    public void mousePressed(MouseEvent e)
	    {
		MouseEvent event =  new MouseEvent(fontSizeSpecRB, e.getID(), e.getWhen(),
			e.getModifiers(), 1, 1, 1, e.isPopupTrigger(), e.getButton());
		fontSizeSpecRB.dispatchEvent(event);
	    }

	    @Override
	    public void mouseReleased(MouseEvent e)
	    {    
		MouseEvent event =  new MouseEvent(fontSizeSpecRB, e.getID(), e.getWhen(),
			e.getModifiers(), 1, 1, e.getClickCount(), e.isPopupTrigger(), e.getButton());
		fontSizeSpecRB.dispatchEvent(event);
	    }
	    @Override
	    public void mouseExited(MouseEvent e)
	    {
		MouseEvent event =  new MouseEvent(fontSizeSpecRB, e.getID(), e.getWhen(),
			e.getModifiers(), 1, 1, e.getClickCount(), e.isPopupTrigger(), e.getButton());
		fontSizeSpecRB.dispatchEvent(event);
	    }
	    @Override
	    public void mouseEntered(MouseEvent e)
	    {
		MouseEvent event =  new MouseEvent(fontSizeSpecRB, e.getID(), e.getWhen(),
			e.getModifiers(), 1, 1, e.getClickCount(), e.isPopupTrigger(), e.getButton());
		fontSizeSpecRB.dispatchEvent(event);
	    }
	});
	fontSizeSpecPanel.add(fontSizeSpecRB);
	fontSizeSpecPanel.add(fontSizeTF);
	fontSizeSpecPanel.setBorder(BorderFactory.createEtchedBorder());
	fontSizeSpecPanel.setOpaque(false);
	this.fitTextToWidthRB = new JRadioButton("Fit text to page width ");
	fitTextToWidthRB.setBorder(BorderFactory.createEtchedBorder());
	fitTextToWidthRB.setBorderPainted(true);
	fitTextToWidthRB.setOpaque(false);
	fitTextToWidthRB.setToolTipText("<html>Don't care about the font size, but make sure<br> the width of input file content will fit into "
		+ "<br>a whole width of a 'pdf' page.");
	
	ButtonGroup fontBG = new ButtonGroup();
	fontBG.add(fontSizeSpecRB);
	fontBG.add(fitTextToWidthRB);
	fitTextToWidthRB.setSelected(true);

	double p = TableLayout.PREFERRED;
	TableLayout fontSelectPL = new TableLayout(new double[]{.5,p,p,p,.5}, new double[]{.5,p,.5});
	JPanel fontSelectP = new JPanel(fontSelectPL);
	fontSelectP.setOpaque(false);
	fontSelectP.add(fontSizeSpecPanel, "1,1,L,C");
	fontSelectP.add(new JLabel(" or "), "2,1,C,C");
	fontSelectP.add(fitTextToWidthRB, "3,1,R,F");

	String[] fonts = {BaseFont.COURIER, BaseFont.HELVETICA,
	    BaseFont.SYMBOL, BaseFont.TIMES_ROMAN,BaseFont.ZAPFDINGBATS};//, "fonts/lucon.ttf"};
	ClassLoader classLoader = getClass().getClassLoader();
	File fontsFolder = new File(classLoader.getResource("fonts").getFile());
	//currently we just make sure we will not take folders
	FileFilter fontFileFilter = new FileFilter() {
	    public boolean accept(File file)
	    {
		if(!file.isDirectory())
		{
		    return true;
//		    String fileName = file.getName();
//		    if(fileName.lastIndexOf('.') < 0)
//			return false;
//		    if(fileName.substring(fileName.lastIndexOf('.')+1,
//			    fileName.length()).equalsIgnoreCase("txt"))
//			return true;
		}
		return false;
	    }
	};
	File[] fontFiles = fontsFolder.listFiles(fontFileFilter);
	int initialFontsSize = fonts.length;
	if(fontFiles.length > 0)
	{
	    int newFontsSize = initialFontsSize + fontFiles.length;
	    fonts = Arrays.copyOf(fonts, newFontsSize);
	    for(int i = initialFontsSize; i < newFontsSize; i++)
		fonts[i] = "fonts/"+fontFiles[i - initialFontsSize].getName();
	}

	this.fontCB = new JComboBox(fonts);
	fontCB.setBorder(BorderFactory.createEtchedBorder());
	fontCB.setToolTipText("Select the font to use for output.");
	String[] encodings = {BaseFont.CP1252, BaseFont.CP1250, BaseFont.CP1257,
	    BaseFont.MACROMAN};
	this.encodingCB = new JComboBox(encodings);
	encodingCB.setBorder(BorderFactory.createEtchedBorder());
	encodingCB.setToolTipText("Select encoding used by the input file.");
	this.portraitRB = new JRadioButton("Portrait ");
	portraitRB.setBorder(BorderFactory.createEtchedBorder());
	portraitRB.setBorderPainted(true);
	portraitRB.setOpaque(false);
	portraitRB.setToolTipText("The output file will have pages in portrait orientation.");
	this.landscapeRB = new JRadioButton("Landscape ");
	landscapeRB.setBorder(BorderFactory.createEtchedBorder());
	landscapeRB.setBorderPainted(true);
	landscapeRB.setOpaque(false);
	landscapeRB.setToolTipText("The output file will have pages in landscape orientation.");
	ButtonGroup pageOrientBG = new ButtonGroup();
	pageOrientBG.add(portraitRB);
	pageOrientBG.add(landscapeRB);
	portraitRB.setSelected(true);
	
	//panel with page orientation settings
	TableLayout pageOrientPL = new TableLayout(new double[]{5,p,p,p,5}, new double[]{TableLayout.FILL});
	JPanel pageOrientP = new JPanel(pageOrientPL);
	pageOrientP.setOpaque(false);
	pageOrientP.add(portraitRB, "1,0,L,F");
	pageOrientP.add(new JLabel(" or "), "2,0,C,C");
	pageOrientP.add(landscapeRB, "3,0,R,F");
	pageOrientP.setBorder(BorderFactory.createEtchedBorder());

	TableLayout encodeOrientPL = new TableLayout(new double[]{10,p,5,p,TableLayout.FILL,p,10}, new double[]{.5,p,.5});
	JPanel fontEncodeOrientP = new JPanel(encodeOrientPL);
	fontEncodeOrientP.setOpaque(false);
	fontEncodeOrientP.add(fontCB, "1,1,L,F");
	fontEncodeOrientP.add(encodingCB, "3,1,L,F");
	fontEncodeOrientP.add(pageOrientP, "5,1,R,F");

	this.overwriteOutputFileChB = new JCheckBox("Overwrite output file ");
	overwriteOutputFileChB.setToolTipText("When selected it will overwrite the output file if it exists.");
	overwriteOutputFileChB.setBorder(BorderFactory.createEtchedBorder());
	overwriteOutputFileChB.setBorderPainted(true);
	overwriteOutputFileChB.setOpaque(false);
	int bW = 80;
	int bH = 30;
	this.convertB = new JButton("Convert");
	convertB.addActionListener(this);
	convertB.setPreferredSize(new Dimension(bW, bH));
	convertB.setToolTipText("Convert the input 'txt' file applying all the selected settings.");
	this.exitB = new JButton("Exit");
	exitB.addActionListener(this);
	exitB.setPreferredSize(new Dimension(bW, bH));
	exitB.setToolTipText("Exit the program.");
	TableLayout buttonPL = new TableLayout(new double[]{.25,p,.5,p,.25}, new double[]{5,p,5});
	JPanel buttonP = new JPanel(buttonPL);
	buttonP.setOpaque(false);
	buttonP.add(convertB, "1,1,C,C");
	buttonP.add(exitB, "3,1,C,C");

	JLabel infoL = new JLabel("<html><p>&nbsp Select conversion settings.</p>");
	TableLayout layout = new TableLayout(new double[]{.5,p,.5}, new double[]{.5,p,5,p,5, p,5, p,10,p,.5});
	setLayout(layout);
	add(infoL, "1,1,L,C");
	add(fontSelectP, "1,3,F,C");
	add(fontEncodeOrientP, "1,5,F,C");
	add(overwriteOutputFileChB, "1,7,C,F");
	add(buttonP, "1,9,F,C");
    }

    public void actionPerformed(ActionEvent e)
    {
	Object source = e.getSource();
	if(source == convertB)
	{
	    String verifyResult = verifyInputFields();
	    if(verifyResult.equals(""))
	    {
		//do conversion of txt file to pdf
		String inputFileName = fileSelectionPanel.getInputFileName();
		String outputFileName = fileSelectionPanel.getOutputFileName();
		String encoding = (String) encodingCB.getSelectedItem();
		String outputFontName = (String) fontCB.getSelectedItem();
		boolean isLandscapeMode = landscapeRB.isSelected();
		float fontSize = -1.0f;
		if(fitTextToWidthRB.isSelected())
		{
		    try
		    {
			Rectangle pageSize = PageSize.A4;
			if(isLandscapeMode)
			    pageSize = PageSize.A4.rotate();
			Document document = new Document(pageSize);
			String fileContent = ReadWriteTextFileWithEncoding.read(inputFileName, encoding);
			fontSize = calculateFontSizeFittingAllStringIntoPageWidth(document,
				fileContent, outputFontName, encoding);
		    }catch(IOException ex)
		    {
			Logger.getLogger(CommonPanel.class.getName()).log(Level.SEVERE, null, ex);
		    }catch(DocumentException ex)
		    {
			Logger.getLogger(CommonPanel.class.getName()).log(Level.SEVERE, null, ex);
		    }
		}
		else
		    fontSize = Float.parseFloat(fontSizeTF.getText());
		try
		{
		    convertTextToPDFFile(inputFileName, encoding, outputFontName,
			    fontSize, isLandscapeMode, outputFileName);
		}catch(DocumentException ex)
		{
		    Logger.getLogger(CommonPanel.class.getName()).log(Level.SEVERE, null, ex);
		}catch(IOException ex)
		{
		    Logger.getLogger(CommonPanel.class.getName()).log(Level.SEVERE, null, ex);
		}
	    }
	    else
		JOptionPane.showMessageDialog(this, verifyResult,
			"Cannot convert, becauses:", JOptionPane.INFORMATION_MESSAGE);
	}
	else if(source == exitB)
	{
	    //just exit the program
	    System.exit(0);
	}
    }

     /**
     * Calculates the maximum font size, which will fits all characters
     * of the given fileContent into the document's page width.
     * @param document where the text will be inputed.
     * @param fileContent the content of the file the will be written.
     * @param fontName the name of the font which will be used while writing into a file.
     * @param encoding the encoding which will be used while writing into a file.
     * @return the max size of the font that will fit the string into the given 
     * document's page width.
     * @throws IOException
     * @throws DocumentException
     */
    public static float calculateFontSizeFittingAllStringIntoPageWidth(
	    Document document, String fileContent,
	    String fontName, String encoding) throws IOException, DocumentException
    {
	String longestLineOfParagraph = "";
	String newLineMarker = System.getProperty("line.separator");
	String[] lineArray = fileContent.split(newLineMarker);
//	System.out.println("lineArray.length="+lineArray.length);
	for(int i = 0; i < lineArray.length; i++)
	{
	    String line = lineArray[i];
	    if(longestLineOfParagraph.length() < line.length())
		longestLineOfParagraph = line;
	}
	//Round margins' size up...
	int marignLeft = (int)(document.leftMargin()+1);
	int marginRight = (int)(document.rightMargin()+1);
	//round page width down...
	int pageWidth = (int)document.getPageSize().getWidth();
	//...to make sure we have correct max space for text.
	int maxSpaceForText = pageWidth - marignLeft - marginRight;
	System.out.println("marignLeft="+marignLeft+";  marginRight="+marginRight
		+";  pageWidth="+pageWidth+";  maxSpaceForText="+maxSpaceForText);
	float fontSize = 12.0f;
	BaseFont bf = BaseFont.createFont(fontName, encoding, BaseFont.EMBEDDED);
        float textWidthForFontSize = bf.getWidthPointKerned(longestLineOfParagraph, fontSize);
	float requiredFontSize = (fontSize*maxSpaceForText)/textWidthForFontSize;
	return requiredFontSize;
    }

    /**
     * Converts the given txt file to pdf. It writes the result into the file
     * by the name given in the outputFileName variable.
     * @param inputFileName the name of a text file to convert to PDF
     * @param encoding used by the file
     * @param outputFontName for Polish files recommended is to use "lucon.ttf"
     * @param fontSize the size of font to use in the pdf
     * @param isLandscapeMode if true the pdf pages will be in landscape otherwise they are in portrait
     * @param outputFileName the name of a pdf file to convert to
     * @throws DocumentException
     * @throws IOException
     */
    public static void convertTextToPDFFile(String inputFileName, 
	    String encoding, String outputFontName, float fontSize,
	    boolean isLandscapeMode, String outputFileName)
        throws DocumentException, IOException
    {
	String fileContent = ReadWriteTextFileWithEncoding.read(inputFileName,encoding);
//	System.out.println("??? czy zawiera ten char ("+newPageChar+") -1 znaczy brak="+
//		fileContent.indexOf(newPageChar));
//	System.out.println("fileContent="+fileContent);
	Rectangle pageSize = PageSize.A4;
	if(isLandscapeMode)
	    pageSize = PageSize.A4.rotate();
        Document document = new Document(pageSize);
        // we'll create the file in memory
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	PdfWriter.getInstance(document, baos);
        document.open();
	//stworz czcionke kompatibilna z polskimi literami/kodowaniem
	BaseFont bf = BaseFont.createFont(outputFontName, encoding, BaseFont.EMBEDDED);
	Font font = new Font(bf, fontSize);
	Paragraph par = new Paragraph(fileContent, font);
        document.add(par);
        document.close();
        // let's write the file in memory to a file anyway
	FileOutputStream fos = new FileOutputStream(outputFileName);
        fos.write(baos.toByteArray());
        fos.close();
	System.out.println("Convertion finished, result stored in file '"+outputFileName+"'");
    }
    /**
     * Converts the given txt file to pdf. The name of the output file
     * will be the same as the name of the txt file, just with pdf extention.
     * @param inputFileName the name of a text file to convert to PDF
     * @param encoding used by the file
     * @param outputFontName for Polish files recommended is to use "lucon.ttf"
     * @param fontSize the size of font to use in the pdf
     * @param isLandscapeMode if true the pdf pages will be in landscape otherwise they are in portrait
     * @throws DocumentException
     * @throws IOException
     */
    public static void convertTextToPDFFile(String inputFileName,
	    String encoding, String outputFontName, float fontSize, boolean isLandscapeMode)
        throws DocumentException, IOException
    {
	String outputFileName = inputFileName.substring(0, inputFileName.lastIndexOf('.'))+".pdf";
	convertTextToPDFFile(inputFileName, encoding, outputFontName, fontSize, isLandscapeMode, outputFileName);
    }

    /** Returns empty string if verification was successful, otherwise it
     * returns a message describing what went wrong. */
    private String verifyInputFields()
    {
	String result = "";
	String inputFileName = "";
	String outputFileName = "";
	if(fileSelectionPanel != null)
	{
	    inputFileName = fileSelectionPanel.getInputFileName();
	    outputFileName = fileSelectionPanel.getOutputFileName();
	}
	
	File inF = new File(inputFileName);
	if(!inF.exists())
	    result += "\n *Specified input file does not exists";
	else
	{
	    int extIndex = inputFileName.lastIndexOf(".");
	    boolean isTextFile = true;
	    if(extIndex != -1)
	    {
		String ext = inputFileName.substring(extIndex+1, inputFileName.length());
		System.out.println("ext = "+ext);
		if(!ext.equalsIgnoreCase("txt"))
		    isTextFile = false;
	    }
	    else
		isTextFile = false;
	    if(isTextFile == false)
		result += "\n *Specified input file must be a txt file";
	}

	int extIndex = outputFileName.lastIndexOf(".");
	boolean isPDFFile = true;
	if(extIndex != -1)
	{
	    String ext = outputFileName.substring(extIndex+1, outputFileName.length());
	    System.out.println("ext = "+ext);
	    if(!ext.equalsIgnoreCase("pdf"))
		isPDFFile = false;
	}
	else
	    isPDFFile = false;
	if(isPDFFile == false)
	    result += "\n *Specified output file must be a pdf file";
	else
	{
	    File outF = new File(outputFileName);
	    if(outF.exists())
	    {
		if(!overwriteOutputFileChB.isSelected())
		    result += "\n *Specified output file exists";
	    }
	    else
	    {
		//by creation of a file we are checking if the output file name is proper
		try
		{
		    outF.createNewFile();
		}catch(IOException ex)
		{
		    Logger.getLogger(CommonPanel.class.getName()).log(Level.SEVERE, null, ex);
		    result += "\n *Specified output file name has not permissible characters";
		}
	    }
	}

	if(fontSizeSpecRB.isSelected())
	    try
	    {
		Float.parseFloat(fontSizeTF.getText());
	    }catch(NumberFormatException e)
	    {
		result += "\n *Specified font size is not a float number, e.g. 10.5, 12, 22.1";
	    }
	return result;
    }

    public static void main(String[] args)
    {
	JPanel p = new CommonPanel(null);
	p.setBackground(Color.GREEN);
	JFrame f = new JFrame();
	f.setContentPane(p);
	f.pack();//setSize(400, 400);
	f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	f.setVisible(true);
    }
}
