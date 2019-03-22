// -----------------------------------------------------
// Assignment (3)
// Question: (include question/part number, if applicable)
// Written by: (Najim Ghafourzadeh 40064350)
// -----------------------------------------------------

/**
 * FileInvalidException inherits from the Exception Class. It is thrown when a .bib file is invalid.
 * 
 * @author Najim Ghafourzadeh
 *
 */
public class FileInvalidException extends Exception{

	/**
	 * Default Constructor which prints an error message.
	 */
	public FileInvalidException() {
		System.out.println("Error: Input file cannot be parsed due to missing"
							+ " information (i.e. month={}, title={}, etc.)");
	}
	
	/**
	 * A Constructor which can take a String (error message) as its parameter. The String will be printed 
	 * upon the call of the getMessage() method from the Exception Class(inherited).
	 * 
	 * @param msg A String which is used to display the error message.
	 */
	public FileInvalidException(String msg) {
		
		super(msg);
	}
	
}
