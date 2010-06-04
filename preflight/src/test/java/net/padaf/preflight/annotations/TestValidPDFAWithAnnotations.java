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
package net.padaf.preflight.annotations;

import java.io.IOException;

import javax.activation.FileDataSource;

import net.padaf.preflight.AbstractTestValidPDFA;
import net.padaf.preflight.ValidationException;
import net.padaf.preflight.ValidationResult;

import org.junit.Test;

public class TestValidPDFAWithAnnotations extends AbstractTestValidPDFA {

  @Test
  public void testPDFAWithCircle() throws ValidationException, IOException {
    if (pdfPath != null) {
      ValidationResult result = validator.validate(new FileDataSource(pdfPath
          + "annotations/pdfa-with-annotations-circle.pdf"));
      if (!result.isValid()) {
        showErrors(result);
      }
		result.closePdf();
    }
  }

  @Test
  public void testPDFAWithSquare() throws ValidationException, IOException {
    if (pdfPath != null) {
      ValidationResult result = validator.validate(new FileDataSource(pdfPath
          + "annotations/pdfa-with-annotations-square.pdf"));
      if (!result.isValid()) {
        showErrors(result);
      }
		result.closePdf();
    }
  }

  @Test
  public void testPDFAWithText() throws ValidationException, IOException {
    if (pdfPath != null) {
      ValidationResult result = validator.validate(new FileDataSource(pdfPath
          + "annotations/pdfa-with-annotations-text.pdf"));
      if (!result.isValid()) {
        showErrors(result);
      }
		result.closePdf();
    }
  }

  @Test
  public void testPDFAWithLink() throws ValidationException, IOException {
    if (pdfPath != null) {
      ValidationResult result = validator.validate(new FileDataSource(pdfPath
          + "annotations/pdfa-with-annotations-link.pdf"));
      if (!result.isValid()) {
        showErrors(result);
      }
		result.closePdf();
    }
  }

  @Test
  public void testPDFAWithFreeText() throws ValidationException, IOException {
    if (pdfPath != null) {
      ValidationResult result = validator.validate(new FileDataSource(pdfPath
          + "annotations/pdfa-with-annotations-freetext.pdf"));
      if (!result.isValid()) {
        showErrors(result);
      }
		result.closePdf();
    }
  }

  @Test
  public void testPDFAWithLine() throws ValidationException, IOException {
    if (pdfPath != null) {
      ValidationResult result = validator.validate(new FileDataSource(pdfPath
          + "annotations/pdfa-with-annotations-line.pdf"));
      if (!result.isValid()) {
        showErrors(result);
      }
		result.closePdf();
    }
  }

  @Test
  public void testPDFAWithMarkup() throws ValidationException, IOException {
    if (pdfPath != null) {
      ValidationResult result = validator.validate(new FileDataSource(pdfPath
          + "annotations/pdfa-with-annotations-markup.pdf"));
      if (!result.isValid()) {
        showErrors(result);
      }
		result.closePdf();
    }
  }

  @Test
  public void testPDFAWithInk() throws ValidationException, IOException {
    if (pdfPath != null) {
      ValidationResult result = validator.validate(new FileDataSource(pdfPath
          + "annotations/pdfa-with-annotations-ink.pdf"));
      if (!result.isValid()) {
        showErrors(result);
      }
		result.closePdf();
    }
  }

  @Test
  public void testPDFAWithRubberStamp() throws ValidationException, IOException {
    if (pdfPath != null) {
      ValidationResult result = validator.validate(new FileDataSource(pdfPath
          + "annotations/pdfa-with-annotations-rubber.pdf"));
      if (!result.isValid()) {
        showErrors(result);
      }
		result.closePdf();
    }
  }

  @Test
  public void testPDFAWithPopup() throws ValidationException, IOException {
    if (pdfPath != null) {
      ValidationResult result = validator.validate(new FileDataSource(pdfPath
          + "annotations/pdfa-with-annotations-popup.pdf"));
      if (!result.isValid()) {
        showErrors(result);
      }
		result.closePdf();
    }
  }

  @Test
  public void testPDFAWithPopupOnAnnot() throws ValidationException,
      IOException {
    if (pdfPath != null) {
      ValidationResult result = validator.validate(new FileDataSource(pdfPath
          + "annotations/pdfa-with-annotations-popup-on-annot.pdf"));
      if (!result.isValid()) {
        showErrors(result);
      }
		result.closePdf();
    }
  }

  @Test
  public void testPDFAWithPrintermarkAnnot() throws ValidationException,
      IOException {
    if (pdfPath != null) {
      ValidationResult result = validator.validate(new FileDataSource(pdfPath
          + "annotations/pdfa-with-annotations-printermark.pdf"));
      if (!result.isValid()) {
        showErrors(result);
      }
		result.closePdf();
    }
  }

  @Test
  public void testPDFAWithTrapNetAnnot() throws ValidationException,
      IOException {
    if (pdfPath != null) {
      ValidationResult result = validator.validate(new FileDataSource(pdfPath
          + "annotations/pdfa-with-annotations-trapnet.pdf"));
      if (!result.isValid()) {
        showErrors(result);
      }
		result.closePdf();
    }
  }
}
