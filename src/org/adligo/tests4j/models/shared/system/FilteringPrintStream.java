package org.adligo.tests4j.models.shared.system;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Locale;

public class FilteringPrintStream extends PrintStream {
	private PrintStream delegate;
	
	public FilteringPrintStream(OutputStream out, PrintStream pDelegate) {
		super(out);
		delegate = pDelegate;
	}

	@Override
	public void flush() {
		super.flush();
		delegate.flush();
	}

	@Override
	public void close() {
		super.close();
		delegate.close();
	}

	@Override
	public boolean checkError() {
		return super.checkError();
	}

	@Override
	protected void setError() {
		super.setError();
	}

	@Override
	protected void clearError() {
		super.clearError();
	}

	@Override
	public void write(int b) {
		super.write(b);
		delegate.write(b);
	}

	@Override
	public void write(byte[] buf, int off, int len) {
		super.write(buf, off, len);
		delegate.write(buf, off, len);
	}

	@Override
	public void print(boolean b) {
		super.print(b);
		delegate.print(b);
	}

	@Override
	public void print(char c) {
		super.print(c);
		delegate.print(c);
	}

	@Override
	public void print(int i) {
		super.print(i);
		delegate.print(i);
	}

	@Override
	public void print(long l) {
		super.print(l);
		delegate.print(l);
	}

	@Override
	public void print(float f) {
		super.print(f);
		delegate.print(f);
	}

	@Override
	public void print(double d) {
		super.print(d);
		delegate.print(d);
	}

	@Override
	public void print(char[] s) {
		super.print(s);
		delegate.print(s);
	}

	@Override
	public void print(String s) {
		super.print(s);
		delegate.print(s);
	}

	@Override
	public void print(Object obj) {
		super.print(obj);
		delegate.print(obj);
	}

	@Override
	public void println() {
		super.println();
		delegate.println();
	}

	@Override
	public void println(boolean x) {
		super.println(x);
		delegate.println(x);
	}

	@Override
	public void println(char x) {
		super.println(x);
		delegate.println(x);
	}

	@Override
	public void println(int x) {
		super.println(x);
		delegate.println(x);
	}

	@Override
	public void println(long x) {
		super.println(x);
		delegate.println(x);
	}

	@Override
	public void println(float x) {
		super.println(x);
		delegate.println(x);
	}

	@Override
	public void println(double x) {
		super.println(x);
		delegate.println(x);
	}

	@Override
	public void println(char[] x) {
		super.println(x);
		delegate.println(x);
	}

	@Override
	public void println(String x) {
		super.println(x);
		delegate.println(x);
	}

	@Override
	public void println(Object x) {
		super.println(x);
		delegate.println(x);
	}

	@Override
	public PrintStream printf(String format, Object... args) {
		delegate.printf(format, args);
		return super.printf(format, args);
	}

	@Override
	public PrintStream printf(Locale l, String format, Object... args) {
		delegate.printf(l, format, args);
		return super.printf(l, format, args);
	}

	@Override
	public PrintStream format(String format, Object... args) {
		delegate.format(format, args);
		return super.format(format, args);
	}

	@Override
	public PrintStream format(Locale l, String format, Object... args) {
		delegate.format(l, format, args);
		return super.format(l, format, args);
	}

	@Override
	public PrintStream append(CharSequence csq) {
		delegate.append(csq);
		return super.append(csq);
	}

	@Override
	public PrintStream append(CharSequence csq, int start, int end) {
		delegate.append(csq, start, end);
		return super.append(csq, start, end);
	}

	@Override
	public PrintStream append(char c) {
		delegate.append(c);
		return super.append(c);
	}

}
