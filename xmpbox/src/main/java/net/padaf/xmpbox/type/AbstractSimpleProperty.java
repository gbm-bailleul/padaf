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
package net.padaf.xmpbox.type;

import net.padaf.xmpbox.XMPMetadata;

/**
 * Abstract Class of an Simple XMP Property
 * @author Germain Costenobel
 *
 */
public abstract class AbstractSimpleProperty extends AbstractField {

	protected Object objValue;
	
	/**
	 * Property specific type constructor (namespaceURI is not given)
	 * @param metadata
	 * @param prefix
	 * @param propertyName
	 * @param value
	 * @throws InappropriateTypeException
	 */
	public AbstractSimpleProperty(XMPMetadata metadata, String prefix, String propertyName, Object value) {
		super(metadata, prefix, propertyName);
		setValue(value);
	}

	
	/**
	 * Property specific type constructor (namespaceURI is given)
	 * @param metadata
	 * @param namespaceURI
	 * @param prefix
	 * @param propertyName
	 * @param value
	 * @throws InappropriateTypeException
	 */
	public AbstractSimpleProperty(XMPMetadata metadata, String namespaceURI, String prefix, String propertyName, Object value) {
		super(metadata, namespaceURI, prefix, propertyName);
		setValue(value);
		
	}
	
	/**
	 * Must be rewritten in each special XMP type Class
	 * to check if the value type corresponding to the XMP Type
	 * @param value
	 * @return
	 */
	public abstract boolean isGoodType(Object value);
	
	
	/**
	 * Check and set new property value 
	 * (in Element and in its Object Representation)
	 * @param value
	 * @return
	 */
	public abstract void setValue(Object value) throws IllegalArgumentException;
	
		
	/**
	 * Return the property value
	 * @return a string
	 */
	public String getStringValue(){
		return element.getTextContent();
	}
	
	
		
}
