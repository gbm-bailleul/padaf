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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.activation.FileDataSource;

import net.padaf.preflight.ValidationException;
import net.padaf.preflight.ValidationResult;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.junit.Test;

public class TestValidationResult extends AbstractTestValidPDFA  {


	@Test
	public void validationSucceed() throws ValidationException {
		if (pdfPath != null) {
			ValidationResult result = validator.validate(new FileDataSource(pdfPath
					+ "actions/pdf_hideAction_false.pdf"));
			assertTrue(result.isValid());
			assertNotNull(result.getPdf());
			assertNotNull(result.getXmpMetaData());

			// try to access PDDocument content
			PDDocument doc = result.getPdf();
			int numPage = doc.getNumberOfPages();
			assertTrue( numPage > 0);
			COSDocument cosdoc = doc.getDocument();
			assertNotNull(cosdoc);
			List<?> objs = cosdoc.getObjects();
			assertNotNull(objs);
			assertFalse(objs.isEmpty());
			
			result.closePdf();
		}
	}

	@Test
	public void validationFailed() throws ValidationException {
		if (pdfPath != null) {
			ValidationResult result = validator.validate(new FileDataSource(pdfPath
					+ "actions/pdf_hide_noT.pdf"));
			assertFalse(result.isValid());
			assertNotNull(result.getPdf());

			// try to access PDDocument content
			PDDocument doc = result.getPdf();
			int numPage = doc.getNumberOfPages();
			assertTrue( numPage > 0);
			COSDocument cosdoc = doc.getDocument();
			assertNotNull(cosdoc);
			List<?> objs = cosdoc.getObjects();
			assertNotNull(objs);
			assertFalse(objs.isEmpty());
			
			result.closePdf();
		}
	}

	@Test
	public void validationSyntaxError() throws ValidationException {
		if (pdfPath != null) {
			ValidationResult result = validator.validate(new FileDataSource(pdfPath
					+ "syntaxeError.pdf"));
			assertFalse(result.isValid());
			assertNull(result.getPdf());
		}
	}
}
