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
package net.padaf.preflight.actions;

import static org.junit.Assert.assertFalse;

import java.io.IOException;
import java.util.List;

import javax.activation.FileDataSource;

import net.padaf.preflight.AbstractTestValidPDFA;
import net.padaf.preflight.ValidationException;
import net.padaf.preflight.ValidationResult;
import net.padaf.preflight.ValidationResult.ValidationError;

import org.junit.Test;

public class TestValidPDFAWithActions extends AbstractTestValidPDFA {

	@Test
	public void testPDFAWithHideNoT() throws ValidationException, IOException {
		if (pdfPath != null) {
			ValidationResult result = validator.validate(new FileDataSource(pdfPath
					+ "actions/pdf_hide_noT.pdf"));
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
	public void testPDFAWithHideInvalidT() throws ValidationException, IOException {
		if (pdfPath != null) {
			ValidationResult result = validator.validate(new FileDataSource(pdfPath
					+ "actions/pdf_hide_invalid_T.pdf"));
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
	public void testPDFAWithHideTrue() throws ValidationException, IOException {
		if (pdfPath != null) {
			ValidationResult result = validator.validate(new FileDataSource(pdfPath
					+ "actions/pdf_hideAction_true.pdf"));
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
	public void testPDFAWithHideFalse() throws ValidationException, IOException {
		if (pdfPath != null) {
			ValidationResult result = validator.validate(new FileDataSource(pdfPath
					+ "actions/pdf_hideAction_false.pdf"));
			if (!result.isValid()) {
				showErrors(result);
			}
			result.closePdf();
		}
	}

	@Test
	public void testPDFAWithURIInvalidURI() throws ValidationException, IOException {
		if (pdfPath != null) {
			ValidationResult result = validator.validate(new FileDataSource(pdfPath
					+ "actions/pdf_uri_invalid_URI.pdf"));
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
	public void testPDFAWithURINoURI() throws ValidationException, IOException {
		if (pdfPath != null) {
			ValidationResult result = validator.validate(new FileDataSource(pdfPath
					+ "actions/pdf_uri_noURI.pdf"));
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
	public void testPDFAWithSubmit() throws ValidationException, IOException {
		if (pdfPath != null) {
			ValidationResult result = validator.validate(new FileDataSource(pdfPath
					+ "actions/pdf_submit_f.pdf"));
			if (!result.isValid()) {
				showErrors(result);
			}
			result.closePdf();
		}
	}

	@Test
	public void testPDFAWithSubmitNotF() throws ValidationException, IOException {
		if (pdfPath != null) {
			ValidationResult result = validator.validate(new FileDataSource(pdfPath
					+ "actions/pdf_submit_fmissing.pdf"));
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
	public void testPDFAWithGotoRemote() throws ValidationException, IOException {
		if (pdfPath != null) {
			ValidationResult result = validator.validate(new FileDataSource(pdfPath
					+ "actions/pdf_gotoremote.pdf"));
			if (!result.isValid()) {
				showErrors(result);
			}
			result.closePdf();
		}
	}

	@Test
	public void testPDFAWithGotoRemoteNotF() throws ValidationException, IOException {
		if (pdfPath != null) {
			ValidationResult result = validator.validate(new FileDataSource(pdfPath
					+ "actions/pdf_gotoremote_noF.pdf"));
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
	public void testPDFAWithGotoRemoteNotD() throws ValidationException, IOException {
		if (pdfPath != null) {
			ValidationResult result = validator.validate(new FileDataSource(pdfPath
					+ "actions/pdf_gotoremote_noD.pdf"));
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
	public void testPDFAWithGotoRemoteInvalidD() throws ValidationException, IOException {
		if (pdfPath != null) {
			ValidationResult result = validator.validate(new FileDataSource(pdfPath
					+ "actions/pdf_gotoremote_invalid_D.pdf"));
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
	public void testPDFAWithThreadInvalidD() throws ValidationException, IOException {
		if (pdfPath != null) {
			ValidationResult result = validator.validate(new FileDataSource(pdfPath
					+ "actions/pdf_thread_invalid_D.pdf"));
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
	public void testPDFAWithThreadnoD() throws ValidationException, IOException {
		if (pdfPath != null) {
			ValidationResult result = validator.validate(new FileDataSource(pdfPath
					+ "actions/pdf_thread_noD.pdf"));
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
	public void testPDFAWithThread() throws ValidationException, IOException {
		if (pdfPath != null) {
			ValidationResult result = validator.validate(new FileDataSource(pdfPath
					+ "actions/pdf_thread.pdf"));
			if (!result.isValid()) {
				showErrors(result);
			}
			result.closePdf();
		}
	}

	@Test
	public void testPDFAWithNamed() throws ValidationException, IOException {
		if (pdfPath != null) {
			ValidationResult result = validator.validate(new FileDataSource(pdfPath
					+ "actions/pdf_named.pdf"));
			if (!result.isValid()) {
				showErrors(result);
			}
			result.closePdf();
		}
	}
	
	@Test
	public void testPDFAWithNamedNoN() throws ValidationException, IOException {
		if (pdfPath != null) {
			ValidationResult result = validator.validate(new FileDataSource(pdfPath
					+ "actions/pdf_named_noN.pdf"));
			assertFalse(result.isValid());	

			List<ValidationError> lErrors = result.getErrorsList();
			for (ValidationError validationError : lErrors) {
				System.out.println(validationError.getErrorCode() + " : "
						+ validationError.getDetails());
			}
			result.closePdf();
		}
	}
}
