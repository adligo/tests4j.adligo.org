package org.adligo.tests4j.run.output;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.PrintStream;
import java.util.Formatter;
import java.util.Locale;

import org.adligo.tests4j.shared.common.Tests4J_System;
import org.adligo.tests4j.shared.output.ByteListOutputStream;
import org.adligo.tests4j.shared.output.I_OutputBuffer;

/**
 * this is used to replace System.out and System.err,
 * so all methods are synchronized since, I have no idea
 * what threading model may call this.
 * It is generally the same as PrintStream with the exception
 * that new lines  
 * @author scott
 *
 */
public class JsePrintOutputStream extends PrintStream {
	public static final String NULL = "null";
	private ByteListOutputStream out;
	private I_OutputBuffer outBuffer;
	private Formatter formatter;
	
	public JsePrintOutputStream(I_OutputBuffer pOut) {
		//note this is just to make PrintStream happy,
		//it should never actually be written to, since all methods are overridden 
		super(new ByteListOutputStream(1));
		out = new ByteListOutputStream(64);
		outBuffer = pOut;
	}

	@Override
	public void flush() {
		synchronized (this) {
			outBuffer.add(out.flushString());
		}
	}

	@Override
	public void close() {
		//do nothing
	}

	@Override
	public void write(int b) {
		try {
			synchronized (this) {
				out.write(b);
			}
		} catch (InterruptedIOException x) {
			Thread.currentThread().interrupt();
		} catch (IOException e) {
			setError();
		}
	}

	@Override
	public void write(byte[] buf, int off, int len) {
		try {
			synchronized (this) {
				out.write(buf, off, len);
			}
		} catch (InterruptedIOException x) {
			Thread.currentThread().interrupt();
		} catch (IOException e) {
			setError();
		}
	}

	@Override
	public void print(boolean b) {
		print(b ? "true" : "false");
	}

	@Override
	public void print(char c) {
		print(String.valueOf(c));
	}

	@Override
	public void print(int i) {
		print(String.valueOf(i));
	}

	@Override
	public void print(long l) {
		print(String.valueOf(l));
	}

	@Override
	public void print(float f) {
		print(String.valueOf(f));
	}

	@Override
	public void print(double d) {
		print(String.valueOf(d));
	}

	@Override
	public void print(char[] s) {
		print(new String(s));
	}

	@Override
	public void print(String s) {
		if (s == null) {
			s = NULL;
		}
		byte [] bytes = s.getBytes();
		try {
			synchronized (this) {
				
			}
			out.write(bytes);
		} catch (IOException e) {
			super.setError();
		}
	}

	@Override
	public void print(Object obj) {
		print(String.valueOf(obj));
	}

	@Override
	public void println() {
		print(Tests4J_System.lineSeperator());
		flush();
	}

	@Override
	public void println(boolean x) {
		outBuffer.add(String.valueOf(x));
	}

	@Override
	public void println(char x) {
		outBuffer.add(String.valueOf(x));
	}

	@Override
	public void println(int x) {
		outBuffer.add(String.valueOf(x));
	}

	@Override
	public void println(long x) {
		outBuffer.add(String.valueOf(x));
	}

	@Override
	public void println(float x) {
		outBuffer.add(String.valueOf(x));
	}

	@Override
	public void println(double x) {
		outBuffer.add(String.valueOf(x));
	}

	@Override
	public void println(char[] x) {
		outBuffer.add(new String(x));
	}

	@Override
	public void println(String x) {
		outBuffer.add(x);
	}

	@Override
	public void println(Object x) {
		outBuffer.add(String.valueOf(x));
	}

	@Override
	public PrintStream printf(String format, Object... args) {
		if ((formatter == null)
				  || (formatter.locale() != Locale.getDefault()))
				  formatter = new Formatter((Appendable) this);
		formatter.format(Locale.getDefault(), format, args);
		return this;
	}

	@Override
	public PrintStream printf(Locale l, String format, Object... args) {
		if ((formatter == null)
				 || (formatter.locale() != l))
				 formatter = new Formatter(this, l);
		formatter.format(Locale.getDefault(), format, args);
		return this;
	}

	@Override
	public PrintStream format(String format, Object... args) {
		if ((formatter == null)
				  || (formatter.locale() != Locale.getDefault()))
				  formatter = new Formatter((Appendable) this);
		formatter.format(Locale.getDefault(), format, args);
		return this;
	}

	@Override
	public PrintStream format(Locale l, String format, Object... args) {
		if ((formatter == null)
				 || (formatter.locale() != l))
				 formatter = new Formatter(this, l);
		formatter.format(Locale.getDefault(), format, args);
		return this;
	}

	@Override
	public PrintStream append(CharSequence csq) {
		outBuffer.add(csq.toString());
		return this;
	}

	@Override
	public PrintStream append(CharSequence csq, int start, int end) {
		if (csq == null) {
			print(NULL);
		} else {
			print(csq.subSequence(start, end));
		}
		return this;
	}

	@Override
	public PrintStream append(char c) {
		write(c);
		return this;
	}
	
}
