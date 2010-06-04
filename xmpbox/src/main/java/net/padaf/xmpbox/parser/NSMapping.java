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

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import net.padaf.xmpbox.XMPMetadata;
import net.padaf.xmpbox.schema.AdobePDFSchema;
import net.padaf.xmpbox.schema.DublinCoreSchema;
import net.padaf.xmpbox.schema.PDFAExtensionSchema;
import net.padaf.xmpbox.schema.PDFAFieldDescription;
import net.padaf.xmpbox.schema.PDFAIdentificationSchema;
import net.padaf.xmpbox.schema.PDFAPropertyDescription;
import net.padaf.xmpbox.schema.PDFAValueTypeDescription;
import net.padaf.xmpbox.schema.PropertyAttributesAnnotation;
import net.padaf.xmpbox.schema.PropertyType;
import net.padaf.xmpbox.schema.SchemaDescription;
import net.padaf.xmpbox.schema.XMPBasicSchema;
import net.padaf.xmpbox.schema.XMPMediaManagementSchema;
import net.padaf.xmpbox.schema.XMPRightsManagementSchema;
import net.padaf.xmpbox.schema.XMPSchema;



/**
 * Retrieve information about schemas 
 * @author Germain Costenobel
 *
 */
public class NSMapping {

	
	protected Map<String, XMPSchemaFactory> nsMaps;
	public static final List<String> basicTypes;
	public static final HashMap<String, String> complexBasicTypes;
	
	protected Map<String, String> complexBasicTypesDeclaration_EntireXMPLevel;
	protected Map<String, String> complexBasicTypesDeclaration_SchemaLevel;
	protected Map<String, String> complexBasicTypesDeclaration_PropertyLevel;
	
	
	static{
		basicTypes=new ArrayList<String>();
		basicTypes.add("Text");
		basicTypes.add("Integer");
		basicTypes.add("Boolean");
		basicTypes.add("Date");
		basicTypes.add("URI");
		basicTypes.add("URL");
		basicTypes.add("bag Text");
		basicTypes.add("bag ProperName");
		basicTypes.add("bag Job");
		basicTypes.add("bag Xpath");
		basicTypes.add("seq Text");
		basicTypes.add("seq Field");
		basicTypes.add("seq Date");
		basicTypes.add("Lang Alt");
		
		complexBasicTypes=new HashMap<String, String>();
		complexBasicTypes.put("http://ns.adobe.com/xap/1.0/g/img/", "Thumbnail");
	}
	
	
	/**
	 * Constructor of the NameSpace mapping
	 * @throws IOException 
	 * @throws XmpSchemaException 
	 */
	public NSMapping() throws IOException, XmpSchemaException{
		nsMaps=new HashMap<String, XMPSchemaFactory>();
		complexBasicTypesDeclaration_EntireXMPLevel= new HashMap<String, String>();
		complexBasicTypesDeclaration_SchemaLevel= new HashMap<String, String>();
		complexBasicTypesDeclaration_PropertyLevel= new HashMap<String, String>();
		init();
		
		
	}
	
	/**
	 * Add mapping of common schemas
	 * @throws IOException 
	 * @throws XmpSchemaException 
	 */
	private void init() throws IOException, XmpSchemaException{
		addNameSpace("http://ns.adobe.com/xap/1.0/", XMPBasicSchema.class);
		addNameSpace("http://purl.org/dc/elements/1.1/", DublinCoreSchema.class);
		addNameSpace("http://www.aiim.org/pdfa/ns/extension/", PDFAExtensionSchema.class);
		addNameSpace("http://ns.adobe.com/xap/1.0/mm/", XMPMediaManagementSchema.class);
		addNameSpace("http://ns.adobe.com/pdf/1.3/", AdobePDFSchema.class);
		addNameSpace("http://www.aiim.org/pdfa/ns/id/", PDFAIdentificationSchema.class);
		addNameSpace("http://ns.adobe.com/xap/1.0/rights/", XMPRightsManagementSchema.class);
	}
	
	protected void addNameSpace (String ns, Class<? extends XMPSchema> classSchem) throws XmpSchemaException {
		nsMaps.put(ns, new XMPSchemaFactory(ns, classSchem, initializePropMapping(ns, classSchem)));
	}
	
	/**
	 * Initialize the Property Mapping for a given schema
	 * @param ns Namespace URI
	 * @param classSchem
	 * @return
	 * @throws XmpSchemaException 
	 */
	private PropMapping initializePropMapping(String ns, Class<? extends XMPSchema> classSchem) throws XmpSchemaException{
		PropertyType propType;
		PropertyAttributesAnnotation propAtt;
		Field[] fields;
		PropMapping propMap=new PropMapping(ns);
		fields=classSchem.getFields();
		String propName=null;
		for (Field field : fields) {
			if(field.isAnnotationPresent(PropertyType.class)){
				try {
					propName=(String)field.get(propName);
				} catch (Exception e) {
					throw new XmpSchemaException("couldn't read one type declaration, please check accessibility and declaration of fields annoted in "+classSchem.getName(), e.getCause());
				}
				//System.out.println("nameField:"+propName);
				propType=field.getAnnotation(PropertyType.class);
				//System.out.println("Type '"+propInfo.propertyType()+"' defined for "+propName);
				if(!field.isAnnotationPresent(PropertyAttributesAnnotation.class)){
					propMap.addNewProperty(propName, propType.propertyType(), null);
				}else{
					// TODO Case where a special annotation is used to specify attributes
					//NOT IMPLEMENTED YET, JUST TO GIVE A CLUE TO MAKE THIS
					propAtt=field.getAnnotation(PropertyAttributesAnnotation.class);
					List<String> attributes=new ArrayList<String>();
					for(String att : propAtt.expectedAttributes()){
						attributes.add(att);
					}
					propMap.addNewProperty(propName, propType.propertyType(), attributes);
				}
			}
		}
		return propMap;
	}
	
	/**
	  * see if a specific type is known as a basic XMP type
	  * @param type
	  * @return
	  */
	 private boolean isBasicType(String type){
		return basicTypes.contains(type);
		 
	 }
	
	
	 /**
	  * Say if a specific namespace is known
	  * @param namespace
	  * @return
	  */
	 public boolean isContainedNamespace(String namespace){
		 return nsMaps.containsKey(namespace);
	 }
	 
	 /**
	  * Give type of specified property in specified schema (given by its namespaceURI)
	 * @throws XmpUnknownSchemaException 
	 * @throws IOException 
	  * 
	  */
	 public String getSpecifiedPropertyType (String namespace, QName prop) throws XmpUnknownSchemaException, IOException {
		if(nsMaps.containsKey(namespace)){
			return nsMaps.get(namespace).getPropertyType(prop.getLocalPart());
		}
		//check if its a complexbasicValueType and if it's has been declared
		return getComplexBasicValueTypeEffectiveType(prop.getPrefix());
		
	}
	
	
	 private PDFAValueTypeDescription findValueTypeDescription(SchemaDescription desc, String definedValueType) throws XmpUnknownValueTypeException{
		 List<PDFAValueTypeDescription> values=desc.getValueTypes();
			for(PDFAValueTypeDescription val: values){
				if(definedValueType.equals(val.getTypeNameValue())){
					return val;
				}
			}
		throw new XmpUnknownValueTypeException("ValueType '"+definedValueType+"' is unknown. no declaration found in this schema");
	 }
	 
	 
	 
	 
	 
	 /**
	 * Check if valueType used for a specified property description is known
	 * (if it's a normal value type or a value type which has been defined in PDF/A Extension schema)
	 * @param desc
	 * @param definedValueType
	 * @return
	 * @throws XmpUnknownValueTypeException 
	 * 
	 */
	private String getValueTypeEquivalence(SchemaDescription desc, String definedValueType) throws XmpUnknownValueTypeException {
		if(isBasicType(definedValueType)){
			return definedValueType;
		}
		PDFAValueTypeDescription val=findValueTypeDescription(desc, definedValueType);
		if(val.getFields().isEmpty()){
			//if fields value are note defined we suppose the property is a Text type
			return "Text";
		}
		return "Field";
	}
	 
	/**
	 * For a specific valuetype declared in this schema. This method add type of its fields
	 * @throws IOException 
	 * @throws XmpUnknownValueTypeException 
	
	 */
	private void declareAssociatedFieldType(SchemaDescription desc, String valueType, PropMapping prop) throws IOException, XmpUnknownValueTypeException{
		
		PDFAValueTypeDescription val=findValueTypeDescription(desc, valueType);
		for(PDFAFieldDescription field : val.getFields()){
			//TODO case where a field call another nspace property ???
			String fieldType=getValueTypeEquivalence(desc, field.getValueTypeValue());
			if(fieldType.equals("Field")){
				throw new XmpUnknownValueTypeException("ValueType Field reference a valuetype unknown");
			}
			prop.addNewProperty(field.getNameValue(), fieldType, null);
			
			
		}
	}
	
	
	/**
	 * Set a new Namespace Definition
	 * @param namespace
	 * @param path
	 * @throws IOException 
	 * @throws XmpUnknownValueTypeException 
	 
	 */
	public void setNamespaceDefinition(SchemaDescription desc) throws  IOException, XmpUnknownValueTypeException{
		PropMapping propMap=new PropMapping(desc.getNameSpaceURI());
		List<PDFAPropertyDescription> props=desc.getProperties();
		for(int i=0; i<props.size(); i++){
			String type= getValueTypeEquivalence(desc, props.get(i).getValueTypeValue());
			propMap.addNewProperty(props.get(i).getNameValue(), type, null);
			if(type.equals("Field")){
				declareAssociatedFieldType(desc, props.get(i).getValueTypeValue(), propMap);
			}
		}
		String nsName=desc.getPrefix();
		String ns=desc.getNameSpaceURI();
		nsMaps.put(ns, new XMPSchemaFactory(nsName, ns, XMPSchema.class, propMap));
	}
	
	/**
	 * Return the specialized schema object if known. In other cases, return null
	 * @param namespace
	 * @return
	 * @throws XmpSchemaException 
	 */
	public XMPSchema getAssociatedSchemaObject(XMPMetadata metadata, String namespace) throws XmpSchemaException{
		if(!nsMaps.containsKey(namespace)){
			return null;
		}
		XMPSchemaFactory factory=nsMaps.get(namespace);
		return factory.createXMPSchema(metadata);
		
	}
	
	public boolean isComplexBasicTypes(String namespace){
		return complexBasicTypes.containsKey(namespace);
	}
	
	public void setComplexBasicTypesDeclarationForLevelXMP(String namespace, String prefix){
		if(isComplexBasicTypes(namespace)){
			complexBasicTypesDeclaration_EntireXMPLevel.put(prefix, namespace);
		}
	}
	
	public void setComplexBasicTypesDeclarationForLevelSchema(String namespace, String prefix){
		if(isComplexBasicTypes(namespace)){
			complexBasicTypesDeclaration_SchemaLevel.put(prefix, namespace);
		}
		
	}
	
	public void setComplexBasicTypesDeclarationForLevelProperty(String namespace, String prefix){
		if(isComplexBasicTypes(namespace)){
			complexBasicTypesDeclaration_PropertyLevel.put(prefix, namespace);
		}
	}
	
	public String getComplexBasicValueTypeEffectiveType(String prefix){
		if(complexBasicTypesDeclaration_PropertyLevel.containsKey(prefix)){
			return complexBasicTypes.get(complexBasicTypesDeclaration_PropertyLevel.get(prefix));
		}
		if(complexBasicTypesDeclaration_SchemaLevel.containsKey(prefix)){
			return complexBasicTypes.get(complexBasicTypesDeclaration_SchemaLevel.get(prefix));
		}
		if(complexBasicTypesDeclaration_EntireXMPLevel.containsKey(prefix)){
			return complexBasicTypes.get(complexBasicTypesDeclaration_EntireXMPLevel.get(prefix));
		}
		return null;
	}
	
	public void resetComplexBasicTypesDeclarationInPropertyLevel(){
		complexBasicTypesDeclaration_PropertyLevel.clear();
	}
	
	public void resetComplexBasicTypesDeclarationInSchemaLevel(){
		complexBasicTypesDeclaration_SchemaLevel.clear();
	}
	
	public void resetComplexBasicTypesDeclarationInEntireXMPLevel(){
		complexBasicTypesDeclaration_EntireXMPLevel.clear();
	}
	
	
}
