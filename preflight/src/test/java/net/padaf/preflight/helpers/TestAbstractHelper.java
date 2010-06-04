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
package net.padaf.preflight.helpers;

import net.padaf.preflight.PdfAValidatorFactory;
import net.padaf.preflight.ValidationException;
import net.padaf.preflight.helpers.AcroFormValidationHelper;
import net.padaf.preflight.util.DocumentHandlerStub;

import org.junit.Test;

public class TestAbstractHelper {

  @Test(expected = ValidationException.class)
  public void testAbstractHelperCheckMethod() throws ValidationException {
    AcroFormValidationHelper afvh = new AcroFormValidationHelper(PdfAValidatorFactory.pdfa1bStandardConfig);
    afvh.validate(new DocumentHandlerStub(null));
  }

  @Test(expected = ValidationException.class)
  public void testAbstractHelperCheckMethod2() throws ValidationException {
    AcroFormValidationHelper afvh = new AcroFormValidationHelper(PdfAValidatorFactory.pdfa1bStandardConfig);
    afvh.validate(null);
  }
}
