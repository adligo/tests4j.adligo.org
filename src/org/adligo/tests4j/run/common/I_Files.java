package org.adligo.tests4j.run.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public interface I_Files {
  public File newFile(String path);
  public boolean mkdir(String path);
  /**
   * removes the path and all child files/folders
   * @param path
   * @throws IOException
   */
  public void remove(String path) throws IOException;
  public FileOutputStream newFileOutput(String path) throws IOException;
  public FileInputStream newFileInput(String path) throws IOException;
}
