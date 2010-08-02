/*******************************************************************************
 * Copyright 2010 Atos Worldline SAS
 * 
 * Licensed by Atos Worldline SAS under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * Atos Worldline SAS licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package net.padaf.preflight;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import javax.activation.FileDataSource;

import net.padaf.preflight.util.ByteArrayDataSource;
import net.padaf.preflight.util.IsartorPdfProvider;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestValidationResult {

  private static File[] validPdfFiles = null;
  private static File[] invalidPdfFiles = null;

  @BeforeClass
  public static void beforeClass() throws Exception {
    String pdfPath = System.getProperty("pdfa.valid", null);
    if ("${pdfa.valid}".equals(pdfPath)) {pdfPath=null;}
    if (pdfPath!=null) {
      File directory = new File(pdfPath);
      if (!directory.exists()) throw new Exception ("directory does not exists : "+directory.getAbsolutePath());
      if (!directory.isDirectory()) throw new Exception ("not a directory : "+directory.getAbsolutePath());

      validPdfFiles = directory.listFiles();
    }

    pdfPath = System.getProperty("pdfa.invalid", null);
    if ("${pdfa.invalid}".equals(pdfPath)) {pdfPath=null;}
    if (pdfPath!=null) {
      File directory = new File(pdfPath);
      if (!directory.exists()) throw new Exception ("directory does not exists : "+directory.getAbsolutePath());
      if (!directory.isDirectory()) throw new Exception ("not a directory : "+directory.getAbsolutePath());
      invalidPdfFiles = directory.listFiles();
    }
  }

  @Test
  public void validationSucceed() throws ValidationException {
    if (validPdfFiles != null && validPdfFiles .length > 0) {
      PdfAValidator validator = new PdfAValidatorFactory().createValidatorInstance(PdfAValidatorFactory.PDF_A_1_b);
      ValidationResult result = validator.validate(new FileDataSource(validPdfFiles[0].getAbsolutePath()));
      assertTrue(result.isValid());
      assertNotNull(result.getPdf());
      assertNotNull(result.getXmpMetaData());

      // try to access PDDocument content
      PDDocument doc = result.getPdf();
      int numPage = doc.getNumberOfPages();
      assertTrue( numPage > 0);
      COSDocument cosdoc = doc.getDocument();
      assertNotNull(cosdoc);
      List<?> objs = cosdoc.getObjects();
      assertNotNull(objs);
      assertFalse(objs.isEmpty());

      result.closePdf();
    }
  }

  @Test
  public void validationFailed() throws ValidationException {
    if (invalidPdfFiles != null && invalidPdfFiles.length > 0) {
      PdfAValidator validator = new PdfAValidatorFactory().createValidatorInstance(PdfAValidatorFactory.PDF_A_1_b);
      ValidationResult result = validator.validate(new FileDataSource(invalidPdfFiles[0].getAbsolutePath()));
      assertFalse(result.isValid());

      if (result.getPdf()!=null) {
        // try to access PDDocument content
        PDDocument doc = result.getPdf();
        int numPage = doc.getNumberOfPages();
        assertTrue( numPage > 0);
        COSDocument cosdoc = doc.getDocument();
        assertNotNull(cosdoc);
        List<?> objs = cosdoc.getObjects();
        assertNotNull(objs);
        assertFalse(objs.isEmpty());
      }
      result.closePdf();
    }
  }

  @Test
  public void validationSyntaxError() throws Exception {
    if (invalidPdfFiles != null && invalidPdfFiles.length > 0) {
      PdfAValidator validator = new PdfAValidatorFactory().createValidatorInstance(PdfAValidatorFactory.PDF_A_1_b);
      ValidationResult result = validator.validate(new ByteArrayDataSource(IsartorPdfProvider.getIsartorDocument("/Isartor testsuite/PDFA-1b/6.1 File structure/6.1.8 Indirect objects/isartor-6-1-8-t01-fail-a.pdf")));
      assertFalse(result.isValid());
      assertNull(result.getPdf());
    }
  }
}
