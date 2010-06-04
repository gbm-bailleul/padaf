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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.padaf.xmpbox.XMPMetadata;

/**
 * Object representation for arrays content
 * This Class could be used to define directly a property with more than one field (structure)
 * and also schemas
 * 
 * @author Germain Costenobel
 *
 */
public class ComplexPropertyContainer extends AbstractField {

		
protected List<AbstractField> properties;
	
	/**
	 * Complex Property type constructor (namespaceURI is not given)
	 * @param metadata
	 * @param prefix
	 * @param propertyName
	 * @param value
	 * @throws InappropriateTypeException
	 */
	public ComplexPropertyContainer(XMPMetadata metadata, String prefix,
			String propertyName) {
		super(metadata, prefix, propertyName);
		properties=new ArrayList<AbstractField>();
		
	}

	/**
	 * Complex Property type constructor (namespaceURI is given)
	 * @param metadata
	 * @param prefix
	 * @param propertyName
	 * @param value
	 * @throws InappropriateTypeException
	 */
	public ComplexPropertyContainer(XMPMetadata metadata, String namespaceURI,
			String prefix, String propertyName) {
		super(metadata, namespaceURI, prefix, propertyName);
		properties=new ArrayList<AbstractField>();
	}
	
	/**
	 * Add a property to the current structure
	 * @param obj the property to add
	 */
	public void addProperty(AbstractField obj){
		if(containsProperty(obj)){
			removeProperty(obj);
		}
		properties.add(obj);
	// COMMENTS REPRESENTS CLUES TO USE SAME PROPERTY AT MORE THAN ONE PLACE
	// BUT IT CREATE PROBLEM TO FIND AND ERASE CLONED ELEMENT 
	//	Node cloned = obj.getElement().cloneNode(true);
	//	parent.adoptNode(cloned);
		element.appendChild(obj.getElement());
	//	element.appendChild(cloned);
	}

	
	/**
	 * Return all children associated to this property
	 * @return
	 */
	public List<AbstractField> getAllProperties(){
		return properties;
	}
	
	/**
	 * Return all properties with this specified localName
	 * @param localName
	 * @return
	 */
	public List<AbstractField> getPropertiesByLocalName(String localName){
		List<AbstractField> absFields=getAllProperties();
		if(absFields!=null){
			List<AbstractField> list=new ArrayList<AbstractField>();
			for (AbstractField abstractField : absFields) {
				if(abstractField.getPropertyName().equals(localName)){
					list.add(abstractField);
				}
			}
			return list;
		}
		return null;
		
	}
	
	/**
	 * Check if two property are similar
	 * @param prop1
	 * @param prop2
	 * @return
	 */
	public boolean isSameProperty(AbstractField prop1, AbstractField prop2){
		if(prop1.getClass().equals(prop2.getClass()) && prop1.getQualifiedName().equals(prop2.getQualifiedName())){
				if(prop1.getElement().getTextContent().equals(prop2.getElement().getTextContent())){
					return true;
				}
			}
		return false;
	}
	
	/**
	 * Check if a XMPFieldObject is in the complex property
	 * @param property
	 * @return
	 */
	public boolean containsProperty(AbstractField property){
		Iterator<AbstractField> it=getAllProperties().iterator();
		AbstractField tmp;
		while(it.hasNext()){
			tmp=it.next();
			if(isSameProperty(tmp, property) ){
				return true;
			}
		}
		return false;
	}
	
	
	
	/**
	 * Remove a property
	 * @param property
	 */
	public void removeProperty(AbstractField property){
		if(containsProperty(property)){
			properties.remove(property);
			element.removeChild(property.getElement());
		}
	}

}
