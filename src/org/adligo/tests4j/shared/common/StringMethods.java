package org.adligo.tests4j.shared.common;

/**
 * a collection of thread safe methods
 * which do things that should probably get added to 
 * the actual java String class.
 * 
 * @author scott
 *
 */
public class StringMethods {
	public static boolean isEmpty(String p) {
		if (p == null) {
			return true;
		}
		if (p.trim().length() == 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * Throws a IllegalArgumentException
	 * when the parameter p isEmpty(p); 
	 * 
	 * @param p
	 * @param message
	 */
	public static void isEmpty(String p, String message) {
		if (isEmpty(p)) {
			throw new IllegalArgumentException(message);
		}
	}
	

	public static int indexBoundsFix(String text, int index) {
		if (index < 0) {
			return 0;
		}
		if (index > text.length()) {
			return text.length();
		}
		return index;
	}

  /**
   * This method orders lines for left to right (English) <br/>
   * and right to left (Arabic) languages, when a <br/>
   * line header or tab is required (i.e); <br/>
   *  <br/>
   * Left to right (spaces simulate tabs) <br/>
   * Tests4j: setup <br/>
   *    org.adligo.tests4j.Foo 12.0% <br/>
   *    org.adligo.tests4j.Boo 13.0% <br/>
   *     <br/>
   * Right to left (spaces simulate tabs at right only)<br/>
   *                   setup :Tests4j<br/>
   *  12.0% org.adligo.tests4j.Foo   <br/>
   *  13.0% org.adligo.tests4j.Boo   <br/>
   * Also note I don't speak any or know much about any
   * right to left languages so this will take some time to get right.
   *  I assumed that java class names would stay left to right,
   *  and that the percent sign would still be to the right of the 
   *  number (after all it is the Arabic numeral system).
   *  The name of the Tests4J product would not change, as 
   *    translating it into other languages would be confusing.
   *    
   * @param p
   * @return
   */
  public static String orderLine(boolean leftToRight, String ... p) {
    if (p.length == 1) {
      return p[0];
    }
    StringBuilder sb = new StringBuilder();
    if (leftToRight) {
      for (int i = 0; i < p.length; i++) {
        sb.append(p[i]);
      }
    } else {
      for (int i = p.length - 1; i >= 0; i--) {
        sb.append(p[i]);
      }
    }
    return sb.toString();
  }
	

}
