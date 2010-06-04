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

import java.lang.reflect.Constructor;
import java.util.List;

import net.padaf.xmpbox.XMPMetadata;
import net.padaf.xmpbox.schema.XMPSchema;

/**
 * A factory for each kind of schemas
 * @author Germain Costenobel
 *
 */
public class XMPSchemaFactory {

	protected String namespace;
	protected Class<? extends XMPSchema> schemaClass;
	protected PropMapping propDef;
	protected String nsName;
	protected boolean isDeclarative;
	
	
	/**
	 * Factory Constructor for basic known schemas 
	 * @param namespace
	 * @param schemaClass
	 * @param propDef
	 */
	public XMPSchemaFactory(String namespace, Class<? extends XMPSchema> schemaClass, PropMapping propDef){
		this.isDeclarative=false;
		this.namespace=namespace;
		this.schemaClass=schemaClass;
		this.propDef=propDef;
	}
	

	/**
	 * Factory constructor for declarative XMP Schemas
	 * @param nsName
	 * @param namespace
	 * @param schemaClass
	 * @param propDef
	 */
	public XMPSchemaFactory(String nsName, String namespace,
			Class<? extends XMPSchema> schemaClass, PropMapping propDef) {
		this.isDeclarative=true;
		this.namespace=namespace;
		this.schemaClass=schemaClass;
		this.propDef=propDef;
		this.nsName=nsName;
	}
	
	public String getNamespace(){
		return namespace;
	}
	
	public String getPropertyType(String name){
		return propDef.getPropertyType(name);
	}
	
	public List<String> getPropertyAttributes(String name){
		return propDef.getPropertyAttributes(name);
	}
	
	@SuppressWarnings("unchecked")
	public XMPSchema createXMPSchema(XMPMetadata metadata) throws XmpSchemaException{
		XMPSchema schema=null;
		Class[] argsClass ;
		Object[] schemaArgs;
		
		if(isDeclarative){
			argsClass = new Class[] {XMPMetadata.class, String.class, String.class};
			schemaArgs = new Object[] { metadata, nsName, namespace};
		}
		else{
			argsClass = new Class[] {XMPMetadata.class};
			schemaArgs = new Object[] { metadata};
		}
		
		Constructor<? extends XMPSchema> schemaConstructor;
		try {
			schemaConstructor = schemaClass.getConstructor(argsClass);
			schema = schemaConstructor.newInstance(schemaArgs);
			if(schema!=null){
				metadata.addSchema(schema);
			}
			return schema;
		} catch (Exception e) {
			throw new XmpSchemaException("Cannot Instanciate specified Object Schema", e);
		}
	}
	
	
}