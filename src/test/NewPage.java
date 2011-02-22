/*
 * This class is part of the book "iText in Action - 2nd Edition"
 * written by Bruno Lowagie (ISBN: 9781935182610)
 * For more info, go to: http://itextpdf.com/examples/
 * This example only works with the AGPL version of iText.
 */

package test;


import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class NewPage {

    /** Path to the resulting PDF file. */
    public static final String RESULT = "new_page.pdf";

    /**
     * Main method creating the PDF.
     * @param    args    no arguments needed
     * @throws IOException
     * @throws DocumentException
     */
    public static void main(String[] args) throws IOException, DocumentException {
    	// step 1
        Document document = new Document();
        // step 2
        PdfWriter writer
            = PdfWriter.getInstance(document, new FileOutputStream(RESULT));
        // step 3
        document.open();
        // step 4
        document.add(new Paragraph("This page will NOT be followed by a blank page!"));
        document.newPage();
        // we don't add anything to this page: newPage() will be ignored
        document.newPage();
        document.add(new Paragraph("This page will be followed by a blank page!"));
        document.newPage();
        writer.setPageEmpty(false);
        document.newPage();
        document.add(new Paragraph("The previous page was a blank page!"));
        // step 5
        document.close();
    }
}
