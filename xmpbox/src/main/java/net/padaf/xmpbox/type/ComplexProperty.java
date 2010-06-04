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
 * Object representation of a Complex XMP Property
 * (Represents Ordered, Unordered and Alternative Arrays builder)
 * @author Germain Costenobel
 *
 */
public class ComplexProperty extends AbstractField {

	public static final String UNORDERED_ARRAY="Bag";
	public static final String ORDERED_ARRAY="Seq";
	public static final String ALTERNATIVE_ARRAY="Alt";
	
	protected ComplexPropertyContainer container;
	
	/**
	 * Contructor of a complex property
	 * @param metadata
	 * @param prefix 
	 * @param propertyName
	 * @param type type of complexProperty (Bag, Seq, Alt)
	 */
	public ComplexProperty(XMPMetadata metadata, String prefix,
			String propertyName, String type) {
		super(metadata, prefix, propertyName);
		container=new ComplexPropertyContainer(metadata, "rdf", type);
		element.appendChild(container.getElement());
	}

	/**
	 * Contructor of a complex property
	 * @param metadata
	 * @param nameSpace
	 * @param prefix 
	 * @param propertyName
	 * @param type type of complexProperty (Bag, Seq, Alt)
	 */
	public ComplexProperty(XMPMetadata metadata, String namespace, String prefix,
			String propertyName, String type) {
		super(metadata, namespace, prefix, propertyName);
		container=new ComplexPropertyContainer(metadata, "rdf", type);
		element.appendChild(container.getElement());
	}
	
	/**
	 * Return the container of this Array
	 * @return
	 */
	public ComplexPropertyContainer getContainer(){
		return container;
	}
	
	/**
	 * Check if this complex property equals to another
	 * @param prop2
	 * @return
	 */
	public boolean isSameProperty(AbstractField prop2){
		if(this.getQualifiedName().equals(prop2.getQualifiedName())){
				if(this.getElement().getTextContent().equals(prop2.getElement().getTextContent())){
					return true;
				}
		}
		return false;
	}
	
	
		
}
