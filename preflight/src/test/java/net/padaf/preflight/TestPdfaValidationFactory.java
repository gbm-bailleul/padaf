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

import static org.junit.Assert.assertTrue;

import net.padaf.preflight.PdfA1bValidator;
import net.padaf.preflight.PdfAValidator;
import net.padaf.preflight.PdfAValidatorFactory;
import net.padaf.preflight.ValidationException;

import org.junit.Test;

public class TestPdfaValidationFactory {

  @Test
  public void createPDFA1bValidator() throws ValidationException {
    PdfAValidatorFactory fact = new PdfAValidatorFactory();
    PdfAValidator val = fact
        .createValidatorInstance(PdfAValidatorFactory.PDF_A_1_b);
    assertTrue(val instanceof PdfA1bValidator);
  }

  @Test(expected = ValidationException.class)
  public void createUnknownValidator() throws ValidationException {
    PdfAValidatorFactory fact = new PdfAValidatorFactory();
    fact.createValidatorInstance("UnknownPDFFormat");
  }
}
