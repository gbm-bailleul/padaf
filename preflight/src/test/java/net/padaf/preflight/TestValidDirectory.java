package net.padaf.preflight;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.activation.FileDataSource;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class TestValidDirectory {
  
  // TODO Validation CIDKeyed Type2 with Non Identity Map
  // TODO Validation CIDKeyed Type0
	
  protected static PdfAValidator validator = null;
  
  protected File target = null;
  
  
  @BeforeClass
  public static void beforeClass() throws Exception {
    // create validator
    validator = new PdfAValidatorFactory().createValidatorInstance(PdfAValidatorFactory.PDF_A_1_b);
  }
  
  public TestValidDirectory (File file) {
    this.target = file;
  }
  
  @Test
  public void validate () throws Exception {
  	ValidationResult result = null;
  	try {
  		result = validator.validate(new FileDataSource(target));
  		Assert.assertTrue(result.isValid());
  	} finally {
  		if (result != null) {
  			result.closePdf();
  		}
  	}
  }
  
  @Parameters
  public static Collection<Object[]> initializeParameters() throws Exception {
    // check directory
    File directory = null;
    String pdfPath = System.getProperty("pdfa.valid", null);
    if ("${user.pdfa.valid}".equals(pdfPath)) {pdfPath=null;}
    if (pdfPath!=null) {
      directory = new File(pdfPath);
      if (!directory.exists()) throw new Exception ("directory does not exists : "+directory.getAbsolutePath());
      if (!directory.isDirectory()) throw new Exception ("not a directory : "+directory.getAbsolutePath());
    } else {
      System.err.println("System property 'pdfa.valid' not defined, will not run TestValidaDirectory");
    }
    // create list
    if (directory==null) {
      return new ArrayList<Object[]>(0);
    } else {
      File [] files = directory.listFiles();
      List<Object[]> data = new ArrayList<Object[]>(files.length);
      for (File file : files) {
        if (file.isFile()) {
          data.add(new Object [] {file});
        }
      }
      return data;
    }
  }
  
  
  
}
