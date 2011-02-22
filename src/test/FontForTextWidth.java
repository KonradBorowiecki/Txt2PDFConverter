package test;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * @author Konrad Borowiecki
 */
public class FontForTextWidth
{
    public static void main(String[] args) throws IOException, DocumentException
    {
	Document document = new Document(PageSize.A4);
	int marignLeft = (int)(document.leftMargin()+1);
	int marginRight = (int)(document.rightMargin()+1);
	int pageWidth = (int)document.getPageSize().getWidth();
	int maxSpaceForText = pageWidth - marignLeft - marginRight;
	System.out.println("marignLeft="+marignLeft+";  marginRight="+marginRight
		+";  pageWidth="+pageWidth+";  maxSpaceForText="+maxSpaceForText);
	System.out.println("left="+document.left()+";  right="+document.right()
		+"; right - left="+(document.right()-document.left())
		+";  pageWidth="+pageWidth+";  maxSpaceForText="+maxSpaceForText);
	String longestLineText = "Téeeeeęęęęłłłł   łłł źźź  żżżż   ńńń kokoko óó  ńń kjkjkjk  hissThidiu    jasdio io io    d asd das asd asd asd asd asd asd asd asd asd s text should fit all into the page width. This text should fit all into the page width.";
	float fontSize = 12.0f;
	String fontName = "fonts/lucon.ttf";
	BaseFont bf = BaseFont.createFont(fontName, "CP1250", BaseFont.EMBEDDED);
        float textWidthForFontSize = bf.getWidthPointKerned(longestLineText, fontSize);
	float requiredFontSize = (fontSize*maxSpaceForText)/textWidthForFontSize;
	System.out.println("; bf.getWidth(longestLineText)="+bf.getWidth(longestLineText)
		+"; bf.getWidthPoint(...)="+bf.getWidthPoint(longestLineText, fontSize)
		+"; bf.getWidthPointKerned(...)="+bf.getWidthPointKerned(longestLineText, fontSize)
		+";  requiredFontSize="+requiredFontSize);
        PdfWriter.getInstance(document, new FileOutputStream("TextSizingTest.pdf"));
        document.open();
	com.itextpdf.text.Font font = new com.itextpdf.text.Font(bf, requiredFontSize);
	document.add(new Paragraph(longestLineText, font));
        document.close();
    }
    public static float calculateFontSizeFittingAllStringIntoPageWidth(
	    Document document, String longestLineOfParagraph,
	    String fontName, String encoding) throws IOException, DocumentException
    {
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
}
