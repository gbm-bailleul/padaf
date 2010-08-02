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

import java.util.List;

import net.padaf.preflight.DocumentHandler;
import net.padaf.preflight.ValidationException;
import net.padaf.preflight.ValidatorConfig;
import net.padaf.preflight.ValidationResult.ValidationError;
import net.padaf.preflight.font.FontValidator;

/**
 * Class used by the TestConfiguredValidator class
 */
public class MyFontValidationHelper extends FontValidationHelper {

	public MyFontValidationHelper(ValidatorConfig cfg)
	throws ValidationException {
		super(cfg);
	}

	protected void initFontValidatorFactory () {
		super.initFontValidatorFactory();
		System.out.println("Override the initFontValidatorFactory method");
	}
	
	public void validateFont(DocumentHandler handler, FontValidator fontVal,
				List<ValidationError> result) throws ValidationException {
		System.out.println("Override the validateFont method");
		super.validateFont(handler, fontVal, result);
		result.add(new ValidationError("UNCODEINCONNU"));
	}
}
