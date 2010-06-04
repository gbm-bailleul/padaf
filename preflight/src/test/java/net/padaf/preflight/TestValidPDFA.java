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

import javax.activation.FileDataSource;

import net.padaf.preflight.ValidationException;
import net.padaf.preflight.ValidationResult;

import org.junit.Test;

public class TestValidPDFA extends AbstractTestValidPDFA {
  @Test
  public void test1() throws ValidationException {
    if (pdfPath != null) {
      ValidationResult result = validator.validate(new FileDataSource(pdfPath
          + "/TestPDFAWithFields.pdf"));
      if (!result.isValid()) {
        showErrors(result);
      }
      result.closePdf();
    }
  }

  @Test
  public void test2() throws ValidationException {
    if (pdfPath != null) {
      ValidationResult result = validator.validate(new FileDataSource(pdfPath
          + "/TestPDFAWithFieldsAndBookMark.pdf"));
      if (!result.isValid()) {
        showErrors(result);
      }
      result.closePdf();
    }
  }

  @Test
  public void test3() throws ValidationException {
    if (pdfPath != null) {
      ValidationResult result = validator.validate(new FileDataSource(pdfPath
          + "/BookMarkHierarchy.pdf"));
      if (!result.isValid()) {
        showErrors(result);
      }
      result.closePdf();
    }
  }
}
