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
 * Object representation of an Boolean XMP type
 * @author Germain Costenobel
 *
 */
public class BooleanType extends AbstractSimpleProperty {

	
	private static final String TRUE="True";
	private static final String FALSE="False";
	
	/**
	 * Property Boolean type constructor (namespaceURI is not given)
	 * @param metadata
	 * @param prefix
	 * @param propertyName
	 * @param value
	 * @throws InappropriateTypeException
	 */
	public BooleanType(XMPMetadata metadata, String prefix, String propertyName, Object value){
		super(metadata, prefix, propertyName, value);
	}
	
	/**
	 * Property Boolean type constructor (namespaceURI is given)
	 * @param metadata
	 * @param prefix
	 * @param propertyName
	 * @param value
	 * @throws InappropriateTypeException
	 */
	public BooleanType(XMPMetadata metadata, String namespaceURI, String prefix, String propertyName, Object value) {
		super(metadata, namespaceURI, prefix, propertyName, value);
	}
	
	public boolean isGoodType(Object value){
		if(value instanceof Boolean){
			return true;
		}else if(value instanceof String){
			return value.equals(TRUE) || value.equals(FALSE);
		}
		return false;
	}
	
	

	/**
	 * return the property value 
	 * 
	 * @return boolean
	 */
	public boolean getValue() {
		return (Boolean)objValue;
	}
	
	
	/**
	 * BooleanTypeObject accept String value or a boolean
	 */
	public void setValue(Object value) {
		if(!isGoodType(value)){
			throw new IllegalArgumentException("Value given is not allowed for the boolean type.");
		}
		else{
			// if string object
			if(value instanceof String){
				setValueFromString((String)value); 
			}else{
				// if boolean
				setValueFromBool((Boolean)value);
			}
			
		}
	}
	
	
	/**
	 * Set property value
	 * @param value the new boolean element value
	 */
	private void setValueFromBool(boolean value) {
		objValue=value;
		if(value){
			element.setTextContent(TRUE);
		}else{
			element.setTextContent(FALSE);
		}
	}


	private void setValueFromString(String value) {
		if(value.equals(TRUE)){
			setValueFromBool(true);
		}
		else{
			setValueFromBool(false);
		}
	}

	
}
