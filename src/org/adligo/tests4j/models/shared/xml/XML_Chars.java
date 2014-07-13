package org.adligo.tests4j.models.shared.xml;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * This class contains some xml characters
 * and some thread safe static methods to assist
 * in transforming text to and from xml.
 * 
 * @author scott
 *
 */
public class XML_Chars {
	public static final String QUOTE = "\"";
	public static final String EQUALS_QUOTE = "=\"";
	public static final String START = "<";
	public static final String END = ">";
	public static final String END_START = "</";
	public static final String START_END = "/>";
	public static final String NEW_LINE_UNIX = "\n";
	public static final String TAB = "\t";
	public static final Map<Character, String> TO_XML_CHARS = getToXmlChars();
	public static final Map<String, String> FROM_XML_CHARS = getFromXmlChars();
	
	private static Map<Character, String> getToXmlChars() {
		Map<Character, String> toRet = new HashMap<Character, String>();
		toRet.put('&',"&amp;");
		toRet.put('<',"&lt;");
		toRet.put('>',"&gt;");
		toRet.put('\'',"&apos;");
		toRet.put('"',"&quot;");
		return Collections.unmodifiableMap(toRet);
	}
	
	private static Map<String, String> getFromXmlChars() {
		Map<String, String> toRet = new HashMap<String, String>();
		toRet.put("&amp;","&");
		toRet.put("&lt;","<");
		toRet.put("&gt;",">");
		toRet.put("&apos;", "\'");
		toRet.put("&quot;", "\"");
		return Collections.unmodifiableMap(toRet);
	}
	
	private XML_Chars() {}
	
	/**
	 * a thread safe method to transform text to xml
	 * ie "a&b" would become "a&amp;b"
	 * @param p
	 * @return
	 */
	public static String toXml(String p) {
		if (p == null) {
			return "null";
		}
		StringBuilder sb = new StringBuilder();
		char [] chars = p.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			char c = chars[i];
			String replacement = TO_XML_CHARS.get(c);
			if (replacement == null) {
				sb.append(c);
			} else {
				sb.append(replacement);
			}
		}
		return sb.toString();
	}
	
	/**
	 * a thread safe method to transform text to xml
	 * ie "a&amp;b" would become "a&b"
	 * @param p
	 * @return
	 */
	public static String fromXml(String p) {
		Set<Entry<String, String>> entries = FROM_XML_CHARS.entrySet();
		for (Entry<String, String> e: entries) {
			String key = e.getKey();
			String value = e.getValue();
			p = p.replaceAll(key, value);
		}
		
		return p;
	}
	
}
