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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.activation.FileDataSource;

import net.padaf.preflight.ValidationResult.ValidationError;
import net.padaf.preflight.actions.ActionManagerFactory;
import net.padaf.preflight.annotations.MyAnnotationFactory;
import net.padaf.preflight.annotations.MyAnnotationFactory.MyAnnotationValidator;
import net.padaf.preflight.helpers.AbstractValidationHelper;
import net.padaf.preflight.helpers.AcroFormValidationHelper;
import net.padaf.preflight.helpers.BookmarkValidationHelper;
import net.padaf.preflight.helpers.CatalogValidationHelper;
import net.padaf.preflight.helpers.FileSpecificationValidationHelper;
import net.padaf.preflight.helpers.FontValidationHelper;
import net.padaf.preflight.helpers.GraphicsValidationHelper;
import net.padaf.preflight.helpers.MetadataValidationHelper;
import net.padaf.preflight.helpers.MyFontValidationHelper;
import net.padaf.preflight.helpers.PagesValidationHelper;
import net.padaf.preflight.helpers.StreamValidationHelper;
import net.padaf.preflight.helpers.TrailerValidationHelper;

import org.junit.Test;

public class TestConfiguredValidator extends AbstractTestValidPDFA {

	@Test
	public void testCustomHelper() throws ValidationException, IOException {
		if (pdfPath != null) {
			/* =========================== */
			/* Create Custom Configuration */ 
			/* =========================== */
			ValidatorConfig config = new ValidatorConfig();
			List<Class<? extends AbstractValidationHelper>> priors = new ArrayList<Class<? extends AbstractValidationHelper>>();
			priors.add(CatalogValidationHelper.class);
			priors.add(StreamValidationHelper.class);
			priors.add(MyFontValidationHelper.class);
			priors.add(GraphicsValidationHelper.class);
			config.addPriorHelpers(priors);

			List<Class<? extends AbstractValidationHelper>> stands = new ArrayList<Class<? extends AbstractValidationHelper>>();
			stands.add(TrailerValidationHelper.class);
			stands.add(BookmarkValidationHelper.class);
			stands.add(AcroFormValidationHelper.class);
			stands.add(FileSpecificationValidationHelper.class);
			// Page Helper must be called after the FontHelper to check
			// if Fonts used by the page content are embedded in the PDF file.
			stands.add(PagesValidationHelper.class);
			stands.add(MetadataValidationHelper.class);
			config.addStandHelpers(stands);

			config.setActionFactory(ActionManagerFactory.class);
			config.setAnnotationFactory(MyAnnotationFactory.class);

			PdfAValidator customValidator = new PdfAValidatorFactory().createValidatorInstance(PdfAValidatorFactory.PDF_A_1_b, config);
			ValidationResult result = customValidator.validate(new FileDataSource(pdfPath
			          + "font/pdfa-with-truetype-of.pdf"));
			
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
		if (pdfPath != null) {
			/* =========================== */
			/* Create Custom Configuration */ 
			/* =========================== */
			ValidatorConfig config = new ValidatorConfig();
			List<Class<? extends AbstractValidationHelper>> priors = new ArrayList<Class<? extends AbstractValidationHelper>>();
			priors.add(CatalogValidationHelper.class);
			priors.add(StreamValidationHelper.class);
			priors.add(FontValidationHelper.class);
			priors.add(GraphicsValidationHelper.class);
			config.addPriorHelpers(priors);

			List<Class<? extends AbstractValidationHelper>> stands = new ArrayList<Class<? extends AbstractValidationHelper>>();
			stands.add(TrailerValidationHelper.class);
			stands.add(BookmarkValidationHelper.class);
			stands.add(AcroFormValidationHelper.class);
			stands.add(FileSpecificationValidationHelper.class);
			// Page Helper must be called after the FontHelper to check
			// if Fonts used by the page content are embedded in the PDF file.
			stands.add(PagesValidationHelper.class);
			stands.add(MetadataValidationHelper.class);
			config.addStandHelpers(stands);

			config.setActionFactory(ActionManagerFactory.class);
			config.setAnnotationFactory(MyAnnotationFactory.class);

			PdfAValidator customValidator = new PdfAValidatorFactory().createValidatorInstance(PdfAValidatorFactory.PDF_A_1_b, config);
		      ValidationResult result = customValidator.validate(new FileDataSource(pdfPath
		              + "annotations/pdfa-with-annotations-square.pdf"));
			
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
