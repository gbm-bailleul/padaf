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
package net.padaf.preflight.graphics;

import static org.junit.Assert.assertFalse;

import java.io.IOException;
import java.util.List;

import javax.activation.FileDataSource;

import net.padaf.preflight.AbstractTestValidPDFA;
import net.padaf.preflight.ValidationException;
import net.padaf.preflight.ValidationResult;
import net.padaf.preflight.ValidationResult.ValidationError;

import org.junit.Test;

public class TestValidPDFAWithGraphics extends AbstractTestValidPDFA {

  @Test
  /**
   * This PDF is considered as valid by Callas. However the Indexed color space contains 
   * an invalid hexadeximal string. ( \n and spaces are used)
   * Here is an extract of the PDF/A spacification : 
   * " § 6.1.6 String objects : Hexadecimal strings shall contains an even number
   * of <B>non-whitespace characters</B>, each in the range 0-9, A-F or a-f.
   */
  public void testInvalidPDFAWithImage() throws ValidationException,
      IOException {
    if (pdfPath != null) {
      ValidationResult result = validator.validate(new FileDataSource(pdfPath
          + "graphics/pdfa-with-image.pdf"));
      assertFalse(result.isValid());

      List<ValidationError> lErrors = result.getErrorsList();
      for (ValidationError validationError : lErrors) {
        System.out.println(validationError.getErrorCode() + " : "
            + validationError.getDetails());
      }
		result.closePdf();
    }
  }

  @Test
  public void testPDFAWithJpeg() throws ValidationException, IOException {
    if (pdfPath != null) {
      ValidationResult result = validator.validate(new FileDataSource(pdfPath
          + "graphics/pdfa-valid-with-jpeg.pdf"));
      if (!result.isValid()) {
        showErrors(result);
      }
		result.closePdf();
    }
  }

  @Test
  public void testPDFAWithGif() throws ValidationException, IOException {
    if (pdfPath != null) {
      ValidationResult result = validator.validate(new FileDataSource(pdfPath
          + "graphics/pdfa-valid-with-gif.pdf"));
      if (!result.isValid()) {
        showErrors(result);
      }
		result.closePdf();
    }
  }

  @Test
  /**
   * This PDF is a valid PDF/A with simple shape ( Rectangles and Lines in a Content Stream ) 
   */
  public void testPDFAWithDraw() throws ValidationException, IOException {
    if (pdfPath != null) {
      ValidationResult result = validator.validate(new FileDataSource(pdfPath
          + "graphics/pdfa-with-draw.pdf"));
      if (!result.isValid()) {
        showErrors(result);
      }
		result.closePdf();
    }
  }
}
