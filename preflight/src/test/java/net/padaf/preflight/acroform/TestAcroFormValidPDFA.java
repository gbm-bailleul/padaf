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
package net.padaf.preflight.acroform;

import java.io.IOException;
import java.util.List;

import javax.activation.FileDataSource;

import net.padaf.preflight.AbstractTestValidPDFA;
import net.padaf.preflight.ValidationException;
import net.padaf.preflight.ValidationResult;
import net.padaf.preflight.ValidationResult.ValidationError;

import org.junit.Test;
import static org.junit.Assert.*;


public class TestAcroFormValidPDFA extends AbstractTestValidPDFA {
	@Test
	public void testPDFAWithAction() throws ValidationException, IOException {
		if ( pdfPath != null) {
			ValidationResult result = validator.validate(new FileDataSource(pdfPath + "acroform/InvalidPDFA_AcroFormWithAction.pdf"));
			assertFalse(result.isValid());
			List<ValidationError> lErrors = result.getErrorsList();
			for (ValidationError validationError : lErrors) {
				System.out.println(validationError.getErrorCode() + " : " + validationError.getDetails());
			}
			result.closePdf();
		}
	}

	@Test
	public void testPDFAWith2AP() throws ValidationException, IOException {
		if ( pdfPath != null) {
			ValidationResult result = validator.validate(new FileDataSource(pdfPath + "acroform/InvalidPDFA_AcroFormWith2AP.pdf"));
			assertFalse(result.isValid());
			List<ValidationError> lErrors = result.getErrorsList();
			for (ValidationError validationError : lErrors) {
				System.out.println(validationError.getErrorCode() + " : " + validationError.getDetails());
			}
			result.closePdf();
		}
	}

	@Test
	public void testPDFAWithValidAcroForm() throws ValidationException, IOException {
		if ( pdfPath != null) {
			ValidationResult result = validator.validate(new FileDataSource(pdfPath + "acroform/ValidPDFA_AcroForm.pdf"));
			if ( !result.isValid() ) {
				showErrors(result);
			}
			result.closePdf();
		}
	}
}
