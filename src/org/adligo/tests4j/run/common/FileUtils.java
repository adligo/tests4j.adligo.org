package org.adligo.tests4j.run.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

  /**
   * This is a method to return the current running
   * directory of the trial run, which can be used to discover
   * test data for the test, if it has to deal with actual 
   * file system IO for some reason.  This also establishes
   * the convention of having trial runs running in the project
   * directory;<br/>
   * In Example;<br/>
   * The tests4j_tests.adligo.org project needs some external xml files
   * for testing passing of parameters to tests from a external xml file
   * so the data is placed here;<br/>
   * tests4j_tests.adligo.org/test_data/test_parameters_trial/parameters.xml<br/>
   * @return The absolute path which includes a File.separator at the end.
   */
  public static String getRunDir() {
    File file = new File(".");
    String path = file.getAbsolutePath();
    if (path.lastIndexOf(".") == path.length() - 1) {
      path = path.substring(0, path.length() - 1);
    }
    return path;
  }
  
  public static void removeRecursive(Path path) throws IOException
  {

    /**
     * note this seemed to behave in an asynchronous 
     * manor on mac os 10.9.5, or in other words
     * this seemed to overlap with createDirectories 
     * later on in a single threads execution.
     * It should probably be made synchronous which 
     * I believe is a issue in the JDK implementation 
     * not in my code.
     *   however I worked around it, do recursive 
     *   deletes then a bunch of other logic then add
     *   for both fabricate and tests4j
     */
      Files.walkFileTree(path, new SimpleFileVisitor<Path>()
      {
          @Override
          public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
                  throws IOException
          {
              Files.delete(file);
              return FileVisitResult.CONTINUE;
          }

          @Override
          public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException
          {
              // try to delete the file anyway, even if its attributes
              // could not be read, since delete-only access is
              // theoretically possible
              Files.delete(file);
              return FileVisitResult.CONTINUE;
          }

          @Override
          public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException
          {
              if (exc == null)
              {
                  Files.delete(dir);
                  return FileVisitResult.CONTINUE;
              }
              else
              {
                  // directory iteration failed; propagate exception
                  throw exc;
              }
          }
      });
  }
  
  /**
   * 
   * @param path
   * @param matcher
   * @param ctx
   * @return a list of relative absolute paths to the path parameter
   * which match in the matcher parameter.
   * 
   * @throws IOException
   */
  public List<String> list(Path path, final I_FileMatcher matcher) throws IOException
  {
      final List<String> list = new ArrayList<String>();
      File pathFile = path.toFile();
      final String absPath = pathFile.getAbsolutePath();
      final String simplePathName = pathFile.getName();
      
      final int length = absPath.length();
      Files.walkFileTree(path, new SimpleFileVisitor<Path>()
      {
          @Override
          public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
                  throws IOException
          {
              File f = file.toFile();
              String absPath = f.getAbsolutePath();
              absPath = simplePathName + 
                  absPath.substring(length, absPath.length());
              if (matcher.isMatch(absPath)) {
                list.add(absPath);
              }
              return FileVisitResult.CONTINUE;
          }

          @Override
          public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException
          {
            // this should never occur, unless some permission issue causes it
            if (exc != null) {
              throw exc;
            }
            return FileVisitResult.CONTINUE;
          }

          @Override
          public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException
          {
            // this should never occur, unless some permission issue causes it
            if (exc != null) {
              throw exc;
            }
            return FileVisitResult.CONTINUE;
          }
      });
      return list;
  }
}
