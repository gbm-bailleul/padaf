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
package net.padaf.xmpbox;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;

import net.padaf.xmpbox.parser.XMLPropertiesDescriptionManager;
import net.padaf.xmpbox.parser.XMLValueTypeDescriptionManager;
import net.padaf.xmpbox.schema.PDFAExtensionSchema;
import net.padaf.xmpbox.schema.PropertyExtensionDefinition;
import net.padaf.xmpbox.schema.PropertyType;
import net.padaf.xmpbox.schema.SchemaDescription;
import net.padaf.xmpbox.schema.SchemaExtensionDefinition;
import net.padaf.xmpbox.schema.XMPSchema;
import net.padaf.xmpbox.type.BadFieldValueException;
import net.padaf.xmpbox.type.PropertyDescription;
import net.padaf.xmpbox.type.ValueTypeDescription;

/**
 * 
 * This class help you to build a PDF/A extension schema description thanks to
 * annotation present in annotations included in the schema class representation
 * 
 * @author a183132
 * 
 */
public class BuildPDFExtensionSchemaHelper {

	/**
	 * According to check-style, Utility classes should not have a public or
	 * default constructor.
	 */
	protected BuildPDFExtensionSchemaHelper() {
	};

	/**
	 * Build automatically Schema description with annotations and data present
	 * in schema class and include it in the PDF/A Extension Schema NOTE : If
	 * metadata given not contains a PDF/A Extension Schema, this function add
	 * one automatically.
	 * 
	 * @param metadata
	 *            The concerned metadata in which include Schema Description
	 *            inside the PDF/A Extension Schema
	 * @param schema
	 *            The specific schema concerned by the description building
	 * @throws BuildPDFAExtensionSchemaDescriptionException
	 *             When failed to load or treat data from XML Descriptions files
	 *             or in Schema Class
	 */
	public static void includePDFAExtensionDefinition(XMPMetadata metadata,
			XMPSchema schema)
			throws BuildPDFAExtensionSchemaDescriptionException {
		PDFAExtensionSchema ext = metadata.getPDFExtensionSchema();
		Class<? extends XMPSchema> classSchem = schema.getClass();
		if (ext == null) {
			ext = metadata.createAndAddPDFAExtensionSchemaWithDefaultNS();
		}
		Field[] fields;
		fields = classSchem.getFields();
		SchemaExtensionDefinition schemDefAnnot;
		PropertyExtensionDefinition propExtDefAnnot;
		PropertyType propTypeAnnot;
		String propName = null;
		String propDesc;
		List<PropertyDescription> xmlPropDesc = null;
		if (classSchem.isAnnotationPresent(SchemaExtensionDefinition.class)) {
			schemDefAnnot = classSchem
					.getAnnotation(SchemaExtensionDefinition.class);
			// Try to find and load XML Properties Descriptions file path
			if (!schemDefAnnot.property_descriptions().equals("")) {
				XMLPropertiesDescriptionManager propManag = new XMLPropertiesDescriptionManager();
				propManag.loadListFromXML(classSchem, schemDefAnnot
						.property_descriptions());
				xmlPropDesc = propManag.getPropertiesDescriptionList();
			}
			SchemaDescription desc = ext.createSchemaDescription();
			desc.setSchemaValue(schemDefAnnot.schema());
			desc.setNameSpaceURIValue(schema.getNamespaceValue());
			desc.setPrefixValue(schema.getPrefix());
			ext.addSchemaDescription(desc);
			// Try to find and load XML ValueType Description file path
			if (!schemDefAnnot.valueType_description().equals("")) {
				XMLValueTypeDescriptionManager valTypesManag = new XMLValueTypeDescriptionManager();
				valTypesManag.loadListFromXML(classSchem, schemDefAnnot
						.valueType_description());
				addValueTypesToSchem(metadata, desc, valTypesManag
						.getValueTypesDescriptionList());
			}
			for (Field field : fields) {
				if (field
						.isAnnotationPresent(PropertyExtensionDefinition.class)
						&& field.isAnnotationPresent(PropertyType.class)) {
					try {
						propName = (String) field.get(propName);
					} catch (Exception e) {
						throw propertyDescriptionError(
								classSchem.getName(),
								field.getName(),
								"Couldn't read content, please check accessibility and declaration of field associated",
								e.getCause());
					}
					propExtDefAnnot = field
							.getAnnotation(PropertyExtensionDefinition.class);
					propTypeAnnot = field.getAnnotation(PropertyType.class);
					try {
						if (xmlPropDesc != null) {
							Iterator<PropertyDescription> it = xmlPropDesc
									.iterator();
							PropertyDescription tmp;
							propDesc = null;
							while (it.hasNext() && (propDesc == null)) {
								tmp = it.next();
								if (tmp.getPropertyName().equals(propName)) {
									propDesc = tmp.getDescription();
								}
							}

						} else {
							propDesc = propExtDefAnnot.propertyDescription();
						}
						if ((propDesc == null) || propDesc.equals("")) {
							propDesc = "Not documented description";
						}
						desc.addProperty(propName,
								propTypeAnnot.propertyType(), propExtDefAnnot
										.propertyCategory(), propDesc);
					} catch (BadFieldValueException e) {
						throw propertyDescriptionError(classSchem.getName(),
								propName, "Wrong value for property Category",
								e.getCause());
					}
				}
			}
		} else {
			throw schemaDescriptionError(classSchem.getName(),
					"Couldn't find SchemaExtensionDefinition annotation.");
		}
		/*
		 * try { SaveMetadataHelper.serialize(ext, System.out); } catch
		 * (TransformException e) { e.printStackTrace(); }
		 */
	}

	/**
	 * Add Value Types description which must be associated to the Schema
	 * Description
	 * 
	 * @param metadata
	 *            Metadata concerned by this Schema Declaration
	 * @param desc
	 *            The Description Schema under construction
	 * @param vTypes
	 *            The Value Types list which must be added
	 */
	protected static void addValueTypesToSchem(XMPMetadata metadata,
			SchemaDescription desc, List<ValueTypeDescription> vTypes) {
		for (ValueTypeDescription valueTypeDescription : vTypes) {
			desc.addValueType(valueTypeDescription.getType(),
					valueTypeDescription.getNamespaceURI(),
					valueTypeDescription.getPrefix(), valueTypeDescription
							.getDescription(), valueTypeDescription
							.getPDFAFieldsAssocied(metadata));

		}
	}

	/**
	 * An helper to build a formated error message for schema Description errors
	 * 
	 * @param classSchem
	 *            Name of schema where errors are occured
	 * @param details
	 *            Error details
	 * @return Exception with formated error message
	 */
	protected static BuildPDFAExtensionSchemaDescriptionException schemaDescriptionError(
			String classSchem, String details) {
		StringBuilder sb = new StringBuilder(80);
		sb
				.append(
						"Error while building PDF/A Extension Schema description for '")
				.append(classSchem).append("' schema : ").append(details);
		return new BuildPDFAExtensionSchemaDescriptionException(sb.toString());
	}

	/**
	 * An helper to build a formated error message for properties Description
	 * errors
	 * 
	 * @param classSchem
	 *            Name of schema which include the bad property description
	 * @param propName
	 *            Name of property where a problem has occured
	 * @param details
	 *            Error details
	 * @param e
	 *            Original thrown Exception
	 * @return Exception with formated error message
	 */
	protected static BuildPDFAExtensionSchemaDescriptionException propertyDescriptionError(
			String classSchem, String propName, String details, Throwable e) {
		StringBuilder sb = new StringBuilder(80);
		sb
				.append(
						"Error while building PDF/A Extension Schema description for '")
				.append(classSchem).append("' schema, Failed to treat '")
				.append(propName).append("' property : ").append(details);
		return new BuildPDFAExtensionSchemaDescriptionException(sb.toString(),
				e);
	}

}
