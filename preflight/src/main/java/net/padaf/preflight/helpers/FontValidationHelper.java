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

import java.util.ArrayList;
import java.util.List;

import net.padaf.preflight.DocumentHandler;
import net.padaf.preflight.ValidationException;
import net.padaf.preflight.ValidatorConfig;
import net.padaf.preflight.ValidationResult.ValidationError;
import net.padaf.preflight.font.FontValidator;
import net.padaf.preflight.font.FontValidatorFactory;
import net.padaf.preflight.font.Type3FontValidator;
import net.padaf.preflight.font.FontContainer.State;

import org.apache.pdfbox.cos.COSBase;
import org.apache.pdfbox.cos.COSDictionary;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.cos.COSObject;
import org.apache.pdfbox.pdmodel.PDDocument;

/**
 * This Validation helper validates font objects contained by the PDF File. This
 * class stores all validated fonts in the DocumentHandler to allow some
 * validation like ContentStream.
 * 
 * So FontValidationHelper must be one of the first validation helper to be
 * called.
 */
public class FontValidationHelper extends AbstractValidationHelper {
	protected FontValidatorFactory fontValidationFactory = null; 

	public FontValidationHelper(ValidatorConfig cfg) throws ValidationException {
		super(cfg);
		initFontValidatorFactory();
	}

	/**
	 * instantiate the FontValidatorFactory used by the FontValidationHelper
	 */
	protected void initFontValidatorFactory () {
		this.fontValidationFactory = new FontValidatorFactory();	
	}
	
	@Override
	public List<ValidationError> innerValidate(DocumentHandler handler)
	throws ValidationException {
		List<ValidationError> result = new ArrayList<ValidationError>(0);
		PDDocument pdfDoc = handler.getDocument();
		COSDocument cDoc = pdfDoc.getDocument();

		List<?> lCOSObj = cDoc.getObjects();

		List<FontValidator> lType3 = new ArrayList<FontValidator>();

		for (Object o : lCOSObj) {
			COSObject cObj = (COSObject) o;

			// If this object represents a Stream, the Dictionary must contain the
			// Length key
			COSBase cBase = cObj.getObject();
			if (cBase instanceof COSDictionary) {
				COSDictionary dic = (COSDictionary) cBase;
				String type = dic.getNameAsString(COSName
						.getPDFName(DICTIONARY_KEY_TYPE));
				if (type != null && FONT_DICTIONARY_VALUE_FONT.equals(type)) {
					FontValidator fontVal = fontValidationFactory.getFontValidator(cObj,
							handler);
					if (fontVal instanceof Type3FontValidator) {
						lType3.add(fontVal);
					} else {
						validateFont(handler, fontVal, result);
					}
				}
			}
		}

		// ---- Type 3 can contain other font, so type 3 are validated at the end.
		for (FontValidator t3FontVal : lType3) {
			validateFont(handler, t3FontVal, result);
		}

		return result;
	}

	public void validateFont(DocumentHandler handler, FontValidator fontVal,
			List<ValidationError> result) throws ValidationException {
		if (fontVal != null) {
			fontVal.validate(); 
			if (fontVal.getState() == State.INVALID) {
				result.addAll(fontVal.getValdiationErrors());
				// If State is MAYBE, the Error must be checked when the font is used.
			}
		}
	}
}
