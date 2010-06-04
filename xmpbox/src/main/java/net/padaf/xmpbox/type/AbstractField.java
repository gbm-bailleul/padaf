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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.padaf.xmpbox.XMPMetadata;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Astract Object representation of a XMP field (Properties and specific Schemas)
 * @author Germain Costenobel
 *
 */
public abstract class AbstractField implements Elementable {

	/**
	 * ALL PROPERTIES MUST NOT BE USED MORE THAN ONE TIME
	 * BECAUSE THE SAME ELEMENT CANNOT BE MORE THAN ONE TIME IN THE SAME DOM DOCUMENT
	 * (if you choose to use the same property in different places in the same document, the element associated
	 * will not appear)
	 */
	
	
	protected Element element;
	private String namespaceURI, prefix, propertyName;
	protected Document parent;
	private Map<String, Attribute> attributes;
	
	/**
	 * Constructor of a XMP field without namespaceURI
	 * @param metadata
	 * @param prefix
	 * @param propertyName
	 */
	public AbstractField(XMPMetadata metadata, String prefix, String propertyName){
		String qualifiedName;
		this.prefix=prefix;
		qualifiedName=prefix+":"+propertyName;
		this.parent=metadata.getFuturOwner();
		this.propertyName=propertyName;
		element=parent.createElement(qualifiedName);
		attributes= new HashMap<String, Attribute>();
	}
	
	/**
	 * Constructor of a XMP Field
	 * @param metadata
	 * @param namespaceURI
	 * @param prefix
	 * @param propertyName
	 */
	public AbstractField(XMPMetadata metadata, String namespaceURI, String prefix, String propertyName){
		String qualifiedName;
		this.prefix=prefix;
		qualifiedName=prefix+":"+propertyName;
		this.parent=metadata.getFuturOwner();
		this.namespaceURI=namespaceURI;
		this.propertyName=propertyName;
		element=parent.createElementNS(namespaceURI, qualifiedName);
		attributes= new HashMap<String, Attribute>();
	}
	
	
	
	public Element getElement(){
		return element;
	}



	public String getNamespace() {
		return namespaceURI;
	}

	
	public String getPrefix() {
		return prefix;
	}


	public String getQualifiedName() {
		return prefix+":"+propertyName;
		
	}
	
	public String getPropertyName(){
		return propertyName;
	}

	public void setAttribute(Attribute value){
		if(attributes.containsKey(value.getQualifiedName())){
			//if same name in element, attribute will be replaced
			attributes.remove(value.getQualifiedName());
		}
		if(value.getNamespace()==null){
			attributes.put(value.getQualifiedName(), value);
			element.setAttribute(value.getQualifiedName(), value.getValue());
		}
		else{
			attributes.put(value.getQualifiedName(), value);
			element.setAttributeNS(value.getNamespace(), value.getQualifiedName(), value.getValue());
		}
	}
	
	public boolean containsAttribute(String qualifiedName){
		return attributes.containsKey(qualifiedName);
	}
	
	public Attribute getAttribute(String qualifiedName){
		return attributes.get(qualifiedName);
	}
	
	public List<Attribute> getAllAttributes(){
		return new ArrayList<Attribute>(attributes.values());
	}
	
	public void removeAttribute(String qualifiedName){
		if(containsAttribute(qualifiedName)){
			element.removeAttribute(qualifiedName);
			attributes.remove(qualifiedName);
		}
		
	}

}
