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
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.activation.FileDataSource;

import net.padaf.preflight.ValidationResult.ValidationError;
import net.padaf.preflight.annotations.MyAnnotationFactory;
import net.padaf.preflight.annotations.MyAnnotationFactory.MyAnnotationValidator;
import net.padaf.preflight.helpers.MyFontValidationHelper;

import org.junit.BeforeClass;
import org.junit.Test;

public class TestConfiguredValidator {
	private static File[] pdfFiles = null;
	
	public static String VALID_PDF_WITH_ANNOTATION = "pdfa-with-annotations-square.pdf";
	public static String VALID_PDF_WITH_ANNOTATION_AND_FONT = "pdfa-with-truetype-of.pdf";
	
	@BeforeClass
	public static void beforeClass() throws Exception {
		File directory = null;
    String pdfPath = System.getProperty("pdfa.valid", null);
    if ("${pdfa.valid}".equals(pdfPath)) {pdfPath=null;}
    if (pdfPath!=null) {
      directory = new File(pdfPath);
      if (!directory.exists()) throw new Exception ("directory does not exists : "+directory.getAbsolutePath());
      if (!directory.isDirectory()) throw new Exception ("not a directory : "+directory.getAbsolutePath());

      pdfFiles = directory.listFiles();
    }
	}

	@Test
	public void testCustomHelper() throws ValidationException, IOException {
		
		if (pdfFiles != null && pdfFiles.length > 0) {
			
			File f = null;
			for (File file : pdfFiles) {
				if (file.getName().equals(VALID_PDF_WITH_ANNOTATION_AND_FONT)) {
					f = file;
				}
			}
			
			if (f == null) {
				return;
			}
			
			/* =========================== */
			/* Create Custom Configuration */ 
			/* =========================== */
			ValidatorConfig config = PdfAValidatorFactory.getStandardPDFA1BConfiguration();
			config.addPriorHelpers(ValidatorConfig.FONT_FILTER, MyFontValidationHelper.class);

			config.setAnnotationFactory(MyAnnotationFactory.class);

			PdfAValidator customValidator = new PdfAValidatorFactory().createValidatorInstance(PdfAValidatorFactory.PDF_A_1_b, config);
			ValidationResult result = customValidator.validate(new FileDataSource(f));
			
			assertFalse(result.isValid());
			
			boolean inconnuFound = false;
			List<ValidationError> le = result.getErrorsList();
			for (ValidationError validationError : le) {
				if ("UNCODEINCONNU".equals(validationError.getErrorCode())) {
					inconnuFound = true;
				}
			}

			assertTrue(inconnuFound);
			result.closePdf();
		}
	}
	
	@Test
	public void testCustomAnnotFactory() throws ValidationException, IOException {
		if (pdfFiles != null && pdfFiles.length > 0) {
			
			File f = null;
			for (File file : pdfFiles) {
				if (file.getName().equals(VALID_PDF_WITH_ANNOTATION)) {
					f = file;
				}
			}
			
			if (f == null) {
				return;
			}
			
			/* =========================== */
			/* Create Custom Configuration */ 
			/* =========================== */
			ValidatorConfig config = PdfAValidatorFactory.getStandardPDFA1BConfiguration();
			config.setAnnotationFactory(MyAnnotationFactory.class);

			PdfAValidator customValidator = new PdfAValidatorFactory().createValidatorInstance(PdfAValidatorFactory.PDF_A_1_b, config);
			ValidationResult result = customValidator.validate(new FileDataSource(f));
			
			assertFalse(result.isValid());
			
			boolean errorFound = false;
			List<ValidationError> le = result.getErrorsList();
			for (ValidationError validationError : le) {
				if (MyAnnotationValidator.ERROR.equals(validationError.getErrorCode())) {
					errorFound = true;
				}
			}

			assertTrue(errorFound);
			result.closePdf();
		}
	}
}
