package txt2PDFConverter;

/**
 * This interface represent functionality of a panel where user can set/get
 * the name of file used as an input for some operations and the name of file
 * where the operations result will be stored.
 * @author Konrad Borowiecki
 */
public interface IFileSelectionPanel
{
    /** Retrieves the name of input file.*/
    public String getInputFileName();
    /** Retrieves the name of output file.*/
    public String getOutputFileName();
    /** Sets the name of input file.*/
    public void setInputFileName(String inputFileName);
    /** Sets the name of output file.*/
    public void setOutputFileName(String outputFileName);
}
