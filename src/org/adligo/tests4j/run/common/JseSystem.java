package org.adligo.tests4j.run.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileAttribute;

public class JseSystem implements I_JseSystem {
	/**
	 * capture the initial output stream at
	 * class loading time, so that it can't be changed
	 */
	private static final PrintStream OUT = System.out;
	private static final boolean POSIX =
	    FileSystems.getDefault().supportedFileAttributeViews().contains("posix");
	/**
	 * when jdk 1.7 is last minor version change to 
	 * System.lineSeperator();
	 */
	private static final String LineSeperator = System.getProperty("line.seperator","\n");
	
	public JseSystem() {}
	
	@Override
	public void println(String p) {
		OUT.println(p);
	}

	@Override
	public void exitJvm(int p) {
		System.exit(p);
	}

	@Override
	public long getTime() {
		return System.currentTimeMillis();
	}

	@Override
	public String lineSeperator() {
		return LineSeperator;
	}

	@Override
	public String getCurrentThreadName() {
		return Thread.currentThread().getName();
	}

	@Override
	public PrintStream getOut() {
		return OUT;
	}

	@Override
	public String getJseVersion() {
		return System.getProperty("java.version", "");
	}

	@Override
	public boolean isMainSystem() {
		return true;
	}

  @Override
  public File newFile(String path) {
    return new File(path);
  }

  @Override
  public boolean mkdir(String path) {
    File file = newFile(path);
    try {
      //I was seeing a lot of exceptions from createDirectories
      Files.createDirectory(file.toPath());
    } catch (IOException e) {
      throw new RuntimeException("There was a problem creating the directories " + path, e);
    }
    return true;
  }

  @Override
  public FileOutputStream newFileOutput(String path) throws IOException {
    try {
      return new FileOutputStream(new File(path));
    } catch (FileNotFoundException x) {
      throw new IOException(x);
    }
  }

  @Override
  public FileInputStream newFileInput(String path) throws IOException {
    try {
      return new FileInputStream(new File(path));
    } catch (FileNotFoundException x) {
      throw new IOException(x);
    }
  }

  @Override
  public void remove(String path) throws IOException {
    FileUtils.removeRecursive(new File(path).toPath());;
  }

  @Override
  public String getCurrentThreadGroupName() {
    Thread t = Thread.currentThread();
    ThreadGroup tg = t.getThreadGroup();
    if (tg == null) {
      return "UnknownGroup";
    }
    return tg.getName();
  }

}
