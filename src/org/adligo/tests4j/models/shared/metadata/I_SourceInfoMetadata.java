package org.adligo.tests4j.models.shared.metadata;

/**
 * This interface represents metadata about a .java files
 * content which may potentially be obtained
 * in one of two ways.
 * 
 * 1) Use reflection on the in memory class
 * 		This method has the following advantages;
 * 		1a1) The source is not required to be on the classpath
 * 
 *      This method has the following disadvantages;
 *      1b1) Inner classes(enums) inside of .java files where 
 *      the primary type is a interface will be considered an interface,
 *      and will not have the hasClass or hasEnum flag set to true.
 *      
 * 2) Parse the actual source file
 *      This method of obtaining the source info
 *      is currently not implemented.
 * 		//TODO write a .java file parser
 * 
 * @author scott
 *
 */
public interface I_SourceInfoMetadata {
	/**
	 * the full package.java file name.
	 * ie this class would have 
	 * "org.adligo.tests4j.models.shared.metadata.I_SourceInfo"
	 * @return
	 */
	public abstract String getName();

	/**
	 * @return
	 * if available (.java file was parsed) then
	 * true if this .java file contains a class keyword
	 * http://docs.oracle.com/javase/specs/jls/se8/html/jls-3.html#jls-3.9
	 * 
	 */
	public abstract boolean hasClass();
	/**
	 * @return
	 * if available (.java file was parsed) then
	 * true if this .java file contains a interface keyword
	 * http://docs.oracle.com/javase/specs/jls/se8/html/jls-3.html#jls-3.9
	 */
	public abstract boolean hasInterface();
	/**
	 * @return
	 * if available (.java file was parsed) then
	 * true if this .java file contains a enum keyword
	 * http://docs.oracle.com/javase/specs/jls/se8/html/jls-3.html#jls-3.9
	 */
	public abstract boolean hasEnum();

	/**
	 * if the .java file is available on the classpath
	 * @return
	 */
	public abstract boolean isAvailable();

}