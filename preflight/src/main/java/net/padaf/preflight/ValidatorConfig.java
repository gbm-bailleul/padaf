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

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import net.padaf.preflight.actions.ActionManagerFactory;
import net.padaf.preflight.annotation.AnnotationValidatorFactory;
import net.padaf.preflight.annotation.PDFAbAnnotationFactory;
import net.padaf.preflight.helpers.AbstractValidationHelper;

/**
 * This object contains the configuration of a PdfValidator.
 * In this object, it is possible to define :
 * <ul>
 * <li> a list of priority Helpers that must be executed just after the syntactic validation
 * <li> a list of standard Helpers that must be executed after priority helpers
 * <li> a class which extends of AnnotationManagerFactory to allow the override the annotation validator creation
 * <li> a class which extends of ActionManagerFactory to allow the override the action manager creation
 * <li> a java.util.Properties object to authorize future configuration adds whitout change of the object interface.
 * </ul>
 */
public class ValidatorConfig {
	/**
	 * Container for future properties values
	 */
	private Properties properties = new Properties();
	/**
	 * List of Helpers which have to be executed first. Helpers are called in the order ofa
	 * appearance. 
	 */
	private List<Class<? extends AbstractValidationHelper>> priorHelpers = new ArrayList<Class<? extends AbstractValidationHelper>>();
	/**
	 * List of Helpers which have to be executed after priorHelpers. Helpers are called in the order ofa
	 * appearance. 
	 */
	private List<Class<? extends AbstractValidationHelper>> standHelpers = new ArrayList<Class<? extends AbstractValidationHelper>>();
	/**
	 * Define the AnnotationFactory used by helpers
	 * Default value is PDFAbAnnotationFactory.class
	 */
	private Class<? extends AnnotationValidatorFactory> annotFact = PDFAbAnnotationFactory.class;
	/**
	 * Define the ActionManagerFactory used by helpers
	 * Default value is ActionManagerFactory.class
	 */
	private Class<? extends ActionManagerFactory> actionFact = ActionManagerFactory.class;

	public void setAnnotationFactory(Class<? extends AnnotationValidatorFactory> _annotFact) {
		this.annotFact = _annotFact;
	}

	public void setActionFactory(Class<? extends ActionManagerFactory> _actionFact) {
		this.actionFact = _actionFact;
	}

	public Class<? extends AnnotationValidatorFactory> getAnnotFact() {
		return annotFact;
	}

	public Class<? extends ActionManagerFactory> getActionFact() {
		return actionFact;
	}

	public void addProperty(Object key, Object value) {
		this.properties.put(key, value);
	}

	public Object getProperty(Object key) {
		return this.properties.get(key);
	}

	public void addPriorHelpers(List<Class<? extends AbstractValidationHelper>> priors) {
		priorHelpers.addAll(priors);
	}

	public void addStandHelpers(List<Class<? extends AbstractValidationHelper>> stands) {
		standHelpers.addAll(stands);
	}
	
	public void getStandHelpers(List<Class<? extends AbstractValidationHelper>> stand) {
		standHelpers.addAll(stand);
	}
	
	public List<Class<? extends AbstractValidationHelper>> getPriorHelpers() {
		return priorHelpers;
	}

	public List<Class<? extends AbstractValidationHelper>> getStandHelpers() {
		return standHelpers;
	}
}