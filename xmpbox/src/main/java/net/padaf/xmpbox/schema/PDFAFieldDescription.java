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
package net.padaf.xmpbox.schema;

import java.util.Iterator;

import net.padaf.xmpbox.XMPMetadata;
import net.padaf.xmpbox.type.AbstractField;
import net.padaf.xmpbox.type.Attribute;
import net.padaf.xmpbox.type.ComplexPropertyContainer;
import net.padaf.xmpbox.type.TextType;


/**
 * Representation of a PDF/A Field schema (used in PDFAValueTypeDescription)
 * @author Germain Costenobel
 *
 */
public class PDFAFieldDescription{

	public static final String PDFAFIELDPREFIX="pdfaField";
	public static final String PDFAFIELDPREFIXSEP="pdfaField:";
	
	@PropertyType(propertyType="Text")
	public static final String NAME="name";

    @PropertyType(propertyType="Text")
	public static final String VALUETYPE="valueType";

    @PropertyType(propertyType="Text")
	public static final String DESCRIPTION="description";
	
	
	protected XMPMetadata metadata;
	protected ComplexPropertyContainer content;
	/**
	 * Build a new PDF/A field description
	 * @param metadata
	 */
	public PDFAFieldDescription(XMPMetadata metadata) {
		this.metadata=metadata;
		content=new ComplexPropertyContainer(metadata, "rdf", "li");
		content.setAttribute(new Attribute(null, "rdf", "parseType", "Resource"));
	}
	
	/**
	 * set the name of this field
	 * @param name
	 */
	public void setNameValue(String name){
			content.addProperty(new TextType(metadata, PDFAFIELDPREFIX, NAME, name));
	}
	
	/**
	 *  set the valueType of this field
	 * @param type
	 */
	public void setValueTypeValue(String valueType){
			content.addProperty(new TextType(metadata, PDFAFIELDPREFIX, VALUETYPE, valueType));
	}
	
	/**
	 *  set the description of this field
	 * @param type
	 */
	public void setDescriptionValue(String description){
			content.addProperty(new TextType(metadata, PDFAFIELDPREFIX, DESCRIPTION, description));
	}
	
		
	private String getFieldPropertyValue(String qualifiedName){
		Iterator<AbstractField> it=content.getAllProperties().iterator();
		AbstractField tmp;
		while(it.hasNext()){
			tmp=it.next();
			if(tmp.getQualifiedName().equals(qualifiedName)){
				return ((TextType)tmp).getStringValue();
			}
		}
		return null;
	}
	
	/**
	 * Return the current defined field name (in a string)
	 * @return
	 */
	public String getNameValue(){
		return getFieldPropertyValue(PDFAFIELDPREFIXSEP+NAME);
	}
	
	/**
	 * Return the current defined field valueType (in a string)
	 * @return
	 */
	public String getValueTypeValue(){
		return getFieldPropertyValue(PDFAFIELDPREFIXSEP+VALUETYPE);
	}

	/**
	 * Return the current field description (in a string)
	 * @return
	 */
	public String getDescriptionValue(){
		return getFieldPropertyValue(PDFAFIELDPREFIXSEP+DESCRIPTION);
	}
	
	private TextType getFieldProperty(String qualifiedName){
		Iterator<AbstractField> it=content.getAllProperties().iterator();
		AbstractField tmp;
		while(it.hasNext()){
			tmp=it.next();
			if(tmp.getQualifiedName().equals(qualifiedName)){
				return (TextType)tmp;
			}
		}
		return null;
	}
	
	/**
	 * Return the property corresponding to the field name definition
	 * @return
	 */
	public TextType getName(){
		return getFieldProperty(PDFAFIELDPREFIXSEP+NAME);
	}
	
	/**
	 * Return the property corresponding to the field namespaceURI definition
	 * @return
	 */
	public TextType getValueType(){
		return getFieldProperty(PDFAFIELDPREFIXSEP+VALUETYPE);
	}

	
	/**
	 * Return the property corresponding to the field description definition
	 * @return
	 */
	public TextType getDescription(){
		return getFieldProperty(PDFAFIELDPREFIXSEP+DESCRIPTION);
	}
	
	
}
