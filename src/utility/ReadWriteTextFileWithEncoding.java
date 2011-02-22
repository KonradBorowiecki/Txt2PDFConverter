package utility;

import java.io.*;
import java.util.Scanner;

/**
Read and write a file using an explicit encoding.
Removing the encoding from this code will simply cause the
system's default encoding to be used instead.
 */
public final class ReadWriteTextFileWithEncoding
{
    /** Requires two arguments - the file name, and the encoding to use.  */
    public static void main(String... aArgs) throws IOException
    {
	String fileName = aArgs[0];
	String encoding = aArgs[1];
	ReadWriteTextFileWithEncoding.write(fileName, encoding, "to write sonmnbjvgy");
	ReadWriteTextFileWithEncoding.read(fileName, encoding);
    }

    private ReadWriteTextFileWithEncoding()
    {
    }

    /** Write fixed content to the given file. */
    public static void write(String fileName, String encoding,String toWrite) throws IOException
    {
	log("Writing to file named " + fileName + ". Encoding: " + encoding);
	Writer out = new OutputStreamWriter(new FileOutputStream(fileName), encoding);
	try
	{
	    out.write(toWrite);
	}finally
	{
	    out.close();
	}
    }

    /** Read the contents of the given file. */
    public static String read(String fileName, String encoding) throws IOException
    {
	log("Reading from file.");
	StringBuilder text = new StringBuilder();
	String newLineMarker = System.getProperty("line.separator");
	Scanner scanner = new Scanner(new FileInputStream(fileName), encoding);
	try
	{
	    while(scanner.hasNextLine())
		text.append(scanner.nextLine()).append(newLineMarker);
	}finally
	{
	    scanner.close();
	}
	//log("Text read in: " + text);
	return text.toString();
    }

    private static void log(String aMessage)
    {
	System.out.println(aMessage);
    }

}
