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
 * Object representation of a Real XMP type
 * @author Germain Costenobel
 *
 */
public class RealType extends AbstractSimpleProperty {

	
	/**
	 * Property Real type constructor (namespaceURI is not given)
	 * @param metadata
	 * @param prefix
	 * @param propertyName
	 * @param value
	 * @throws InappropriateTypeException
	 */
	public RealType(XMPMetadata metadata,
			String prefix, String propertyName, Object value)
			 {
		super(metadata, prefix, propertyName, value);
		
	}		
	
	/**
	 * Property Real type constructor (namespaceURI is given)
	 * @param metadata
	 * @param prefix
	 * @param propertyName
	 * @param value
	 * @throws InappropriateTypeException
	 */
	public RealType(XMPMetadata metadata, String namespaceURI,
			String prefix, String propertyName, Object value)
			 {
		super(metadata, namespaceURI, prefix, propertyName, value);
		
	}

	/**
	 * return the property value 
	 * @return float
	 */
	public float getValue() {
		return (Float)objValue;
	}
	
	

	/**
	 * Set property value
	 * @param value the new element value
	 */
	private void setValueFromFloat(float value) {
		objValue=value;
		element.setTextContent(""+value);
	}

	
	public boolean isGoodType(Object value) {
		if(value instanceof Float){
			return true;
		}else if(value instanceof String){
			try{
				Float.parseFloat((String)value);
				return true;
			}
			catch(NumberFormatException e){
				return false;
			}
		}
		return false;
	}

	
	public void setValue(Object value) {
		if(!isGoodType(value)){
			throw new IllegalArgumentException("Value given is not allowed for the Real type.");
		}
		else{
			// if string object
			if(value instanceof String){
				setValueFromString((String)value); 
			}else{
				// if Real (float)
				setValueFromFloat((Float)value);
			}
			
		}
		
	}
	
	private void setValueFromString(String value){
		setValueFromFloat(Float.parseFloat(value));
	}
	
}
