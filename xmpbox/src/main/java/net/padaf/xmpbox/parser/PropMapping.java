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
package net.padaf.xmpbox.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents all properties known for a specific namespace
 * Type and attributes associated to each properties are saved
 * If a specific type well declared is used, this class map it to a basic type
 * @author Germain Costenobel
 *
 * Attribute management pre-implemented in order to give clues to make a attribute management system
 */
public class PropMapping {
	
	
	private String namespace;
	private Map<String, String> types;
	private Map<String, List<String>> attributes;
	
		
	public PropMapping(String namespace){
		this.namespace=namespace;
		types=new HashMap<String, String>();
		attributes=new HashMap<String, List<String>>();
		
	}
	
	/**
	 * Give the NS URI associated to this Property Description
	 * @return
	 */
	public String getConcernedNamespace(){
		return namespace;
	}
	
	/**
	 * Return an iterator on qualifiedName of all properties
	 * @return
	 */
	public List<String> getPropertiesName(){
		return new ArrayList<String>(types.keySet());
	}
	
	/**
	 * Add a new property, an attributes list can be given or can be null
	 * @param name
	 * @param type
	 * @param attr
	 */
	public void addNewProperty(String name, String type, List<String> attr){
		types.put(name, type);
		if(attr!=null){
			attributes.put(name, attr);
		}
	}
	
	/**
	 * Return a type of a property from its qualifiedName
	 * @param name
	 * @return
	 */
	public String getPropertyType(String name){
		return types.get(name);
	}
	
	
	/**
	 * Return an unmodifiable list of property attributes from its qualifiedName
	 * @param qualifiedName
	 * @return
	 */
	public List<String> getPropertyAttributes(String name){
		return attributes.get(name);
	}
	
	
	 
	 
	
	
}
