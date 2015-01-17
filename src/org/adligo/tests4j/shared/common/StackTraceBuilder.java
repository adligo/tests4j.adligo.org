package org.adligo.tests4j.shared.common;



/**
 * A few methods for building 
 * a stack trace string, which can be clicked on 
 * from a Eclipse console.
 * 
 * @author scott
 *
 */
public class StackTraceBuilder {
  public static final String DEFAULT_INDENT = "\t";
	private static final I_System SYS = new DefaultSystem();
	private String indent_ = DEFAULT_INDENT;
	private String initalIndent_ = "";
	/**
	 * Java will always be left to right (since it is English),
	 * but indentation may be right to left for Hebrew or Arabic users.
	 * Although IDE (Eclipse, NetBeans) support may require at in English and 
	 * therefore left to right indentation.  By IDE support I mean specifically
	 * the ability to double click on a stack trace in Eclipse to open the
	 * corresponding source file.
	 */
	private boolean leftToRight_ = true;
	private String at_ = "at";
	
  public String toString(Throwable t) {
    return toString(t, true);
  }
  
	public String toString(Throwable t, boolean deep) {
		StringBuilder sb = new StringBuilder();
		StringBuilder currentIndent = new StringBuilder(initalIndent_);
    
		toString(t, sb, currentIndent.toString(), indent_);
		if (deep) {
			Throwable cause = t.getCause();
			while (cause != null) {
			  currentIndent.append(indent_);
			  toString(cause, sb, currentIndent.toString(), indent_);
				cause = cause.getCause();
			}
		}
		return sb.toString();
	}
	
	public void toString(Throwable t, StringBuilder sb, String currentIndent, String indent) {
		
	  if (leftToRight_) {
	    sb.append(currentIndent + t.toString()  + SYS.lineSeperator());
	  } else {
	    sb.append(t.toString() + currentIndent + SYS.lineSeperator());
	  }
		StackTraceElement [] stack = t.getStackTrace();
		if (stack != null) {
			//stack trace will, be null in GWT client
			for (int i = 0; i < stack.length; i++) {
			  StackTraceElement ste = stack[i];
			  String message = StringMethods.orderLine(leftToRight_, currentIndent, indent, at_, " ",
			      ste.getClassName() + "." + ste.getMethodName() + "(" + ste.getFileName() + ":" +
			      ste.getLineNumber() + ")") + SYS.lineSeperator();
			  sb.append(message);
			}
		}
	}

  public String getIndent() {
    return indent_;
  }

  public void setIndent(String indent) {
    if (indent != null) {
      indent_ = indent;
    }
  }

  public String getInitalIndent() {
    return initalIndent_;
  }

  public void setInitalIndent(String initalIndent) {
    if (initalIndent != null) {
      initalIndent_ = initalIndent;
    }
  }

  public boolean isLeftToRight() {
    return leftToRight_;
  }

  public void setLeftToRight(boolean leftToRight) {
    this.leftToRight_ = leftToRight;
  }

  public String getAt() {
    return at_;
  }

  public void setAt(String at) {
    at_ = at;
  }
}
