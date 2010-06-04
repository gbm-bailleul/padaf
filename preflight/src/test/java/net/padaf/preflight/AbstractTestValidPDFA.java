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

import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.List;

import net.padaf.preflight.ValidationResult.ValidationError;

import org.junit.BeforeClass;

public abstract class AbstractTestValidPDFA {
  protected static String pdfPath = null;
  protected static PdfAValidator validator = null;

  @BeforeClass
  public static void init() throws ValidationException, IOException {
    pdfPath = System.getProperty("pdf4test.path", null);
    if ("${user.pdf4test.path}".equals(pdfPath)) {
      pdfPath = null;
    }
    validator = new PdfAValidatorFactory().createValidatorInstance(PdfAValidatorFactory.PDF_A_1_b);
  }

  protected void showErrors(ValidationResult result) {
    List<ValidationError> lErrors = result.getErrorsList();
    for (ValidationError validationError : lErrors) {
      System.out.println(validationError.getErrorCode() + " : "
          + validationError.getDetails());
    }
    fail();
  }
}
