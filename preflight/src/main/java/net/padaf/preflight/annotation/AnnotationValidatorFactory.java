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
package net.padaf.preflight.annotation;

import java.util.List;

import net.padaf.preflight.DocumentHandler;
import net.padaf.preflight.ValidationResult.ValidationError;
import net.padaf.preflight.actions.ActionManagerFactory;

import org.apache.pdfbox.cos.COSDictionary;

public abstract class AnnotationValidatorFactory {
	protected ActionManagerFactory actionFact = null;
	
	public final void setActionFact(ActionManagerFactory _actionFact) {
		this.actionFact = _actionFact;
	}

	/**
	 * Return an instance of AnnotationValidator.
	 * <B>WARNING</B> this method must call the setFactory of each instance of AnnotationValidator.
	 * 
	 * @param annotDic
	 * @param handler
	 * @param errors
	 * @return
	 */
	public final AnnotationValidator getAnnotationValidator(COSDictionary annotDic, 
															DocumentHandler handler,
															List<ValidationError> errors) {
		AnnotationValidator av = instantiateAnnotationValidator(annotDic, handler, errors);
		if (av != null) {
			av.actionFact = actionFact;
		}
		return av;
	}

	public abstract AnnotationValidator instantiateAnnotationValidator(COSDictionary annotDic, 
	DocumentHandler handler,
	List<ValidationError> errors);
}
