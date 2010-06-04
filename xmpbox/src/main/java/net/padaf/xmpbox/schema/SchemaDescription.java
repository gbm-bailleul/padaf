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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import net.padaf.xmpbox.XMPMetadata;
import net.padaf.xmpbox.type.AbstractField;
import net.padaf.xmpbox.type.Attribute;
import net.padaf.xmpbox.type.BadFieldValueException;
import net.padaf.xmpbox.type.ComplexPropertyContainer;
import net.padaf.xmpbox.type.Elementable;
import net.padaf.xmpbox.type.TextType;

import org.w3c.dom.Element;


/**
 * Representation of one schema description (used in PDFAExtension Schema)
 * @author Germain Costenobel
 *
 */
public class SchemaDescription implements Elementable{

  protected XMPMetadata metadata;
  protected ValueTypesDescriptionContainer valueTypes;
  protected PropertyDescriptionContainer properties;
  protected ComplexPropertyContainer content;




  public SchemaDescription(XMPMetadata metadata) {
    this.metadata=metadata;
    content=new ComplexPropertyContainer(metadata, "rdf", "li");
    content.setAttribute(new Attribute(null, "rdf", "parseType", "Resource"));

    // <pdfaSchema:property><seq>
    properties = new PropertyDescriptionContainer();
    content.getElement().appendChild(properties.getElement());
    // <pdfaSchema:valueType><seq>
    valueTypes = new ValueTypesDescriptionContainer();
    content.getElement().appendChild(valueTypes.getElement());



  }

  /**
   * Add a property to the current structure
   * @param obj the property to add
   */
  public void addProperty(AbstractField obj){
    content.addProperty(obj);
  }


  private String getPdfaTextValue(String qualifiedName) {
    Iterator<AbstractField> it = content.getAllProperties().iterator();
    AbstractField tmp;
    while (it.hasNext()) {
      tmp = it.next();
      if (tmp.getQualifiedName().equals(qualifiedName)) {
        return ((TextType) tmp).getStringValue();
      }
    }
    return null;
  }


  /**
   * Set Description of this schema
   * 
   * @param description
   */
  public void setSchemaValue(String description) {
    // <pdfaSchema:schema>
    content.addProperty(new TextType(metadata, PDFAExtensionSchema.PDFASCHEMA, PDFAExtensionSchema.SCHEMA,
        description));
  }

  /**
   * Return the schema description value
   * 
   * @return
   */
  public String getSchema() {
    return getPdfaTextValue(PDFAExtensionSchema.PDFASCHEMASEP+PDFAExtensionSchema.SCHEMA);
  }

  /**
   * Set the Schema Namespace URI
   * 
   * @param uri
   */
  public void setNameSpaceURIValue(String uri) {
      content.addProperty(new TextType(metadata, PDFAExtensionSchema.PDFASCHEMA,
          PDFAExtensionSchema.NS_URI, uri));
  }

  /**
   * Return the Schema nameSpaceURI value
   * 
   * @return
   */
  public String getNameSpaceURI() {
    return getPdfaTextValue(PDFAExtensionSchema.PDFASCHEMASEP+PDFAExtensionSchema.NS_URI);
  }

  /**
   * Set the preferred schema namespace prefix
   * 
   * @param prefix
   */
  public void setPrefixValue(String prefix) {
      content.addProperty(new TextType(metadata, PDFAExtensionSchema.PDFASCHEMA, PDFAExtensionSchema.PREFIX,
          prefix));
  }

  /**
   * Return the preferred schema namespace prefix value
   * 
   * @return
   */
  public String getPrefix() {
    return getPdfaTextValue(PDFAExtensionSchema.PDFASCHEMASEP+PDFAExtensionSchema.PREFIX);
  }

  /**
   * Give all PDFAProperties description embedded in this schema
   * 
   * @return
   */
  public List<PDFAPropertyDescription> getProperties() {
    return Collections.unmodifiableList(properties.properties);
  }

  /**
   * Add a property description to this schema
   * @param name 
   * @param type
   * @param category
   * @param desc
   * @return the created PDFAPropertyDescription
   * @throws BadFieldValueException 
   */
  public PDFAPropertyDescription addProperty(String name, String type, String category, String desc) throws BadFieldValueException {

    PDFAPropertyDescription prop= new PDFAPropertyDescription(metadata);
    prop.setNameValue(name);
    prop.setValueTypeValue(type);
    prop.setCategoryValue(category);
    prop.setDescriptionValue(desc);
    properties.addPropertyDescription(prop);

    return prop;
  }

  /**
   * Give all ValueTypes description embedded in this schema
   * 
   * @return
   */
  public List<PDFAValueTypeDescription> getValueTypes() {
    return Collections.unmodifiableList(valueTypes.valueTypes);
  }



  /**
   * Add a valueType description to this schema
   * @param name 
   * @param type
   * @param category
   * @param desc
   * @return the created PDFAPropertyDescription
   */
  public PDFAValueTypeDescription addValueType(String type, String namespaceURI, String prefix, String description, List<PDFAFieldDescription> fields) {
    PDFAValueTypeDescription valueType= new PDFAValueTypeDescription(metadata);
    valueType.setTypeNameValue(type);
    valueType.setNamespaceURIValue(namespaceURI);
    valueType.setPrefixValue(prefix);
    valueType.setDescriptionValue(description);
    //Field is optional
    if(fields!=null){
      int size=fields.size();
      for(int i=0; i<size; i++){
        valueType.addField(fields.get(i));
      }

    }
    valueTypes.addValueTypeDescription(valueType);

    return valueType;
  }




  public class ValueTypesDescriptionContainer implements Elementable {

    protected Element element, content;
    protected List<PDFAValueTypeDescription> valueTypes;

    /**
     * 
     * SchemasDescription Container  constructor 
     */
    public ValueTypesDescriptionContainer() {
      element=metadata.getFuturOwner().createElement(PDFAExtensionSchema.PDFASCHEMASEP+PDFAExtensionSchema.VALUETYPE);
      content=metadata.getFuturOwner().createElement("rdf:Seq");
      element.appendChild(content);

      valueTypes=new ArrayList<PDFAValueTypeDescription>();
    }



    /**
     * Add a SchemaDescription to the current structure
     * @param obj the property to add
     */
    public void addValueTypeDescription(PDFAValueTypeDescription obj){
      if(containsValueTypeDescription(obj)){
        removeValueTypeDescription(getValueTypeDescription(obj.getTypeNameValue()));
      }
      valueTypes.add(obj);
      content.appendChild(obj.content.getElement());
    }


    /**
     * Return all SchemaDescription
     * @return
     */
    public Iterator<PDFAValueTypeDescription> getAllValueTypeDescription(){
      return valueTypes.iterator();
    }

    public PDFAValueTypeDescription getValueTypeDescription(String type){
      Iterator<PDFAValueTypeDescription> it=getAllValueTypeDescription();
      PDFAValueTypeDescription tmp;
      while(it.hasNext()){
        tmp=it.next();
        if(tmp.getTypeNameValue().equals(type) ){
          return tmp;
        }
      }
      return null;
    }

    /**
     * Check if two SchemaDescription are similar
     * @param prop1
     * @param prop2
     * @return
     */
    public boolean isSameValueTypeDescription(PDFAValueTypeDescription prop1, PDFAValueTypeDescription prop2){
      if(prop1.getClass().equals(prop2.getClass()) ){
        if(prop1.getTypeNameValue().equals(prop2.getTypeNameValue())){
          return true;
        }
      }
      return false;
    }

    /**
     * Check if a specified SchemaDescription is embedded
     * @param schema
     * @return
     */
    public boolean containsValueTypeDescription(PDFAValueTypeDescription schema){
      Iterator<PDFAValueTypeDescription> it=getAllValueTypeDescription();
      PDFAValueTypeDescription tmp;
      while(it.hasNext()){
        tmp=it.next();
        if(isSameValueTypeDescription(tmp, schema) ){
          return true;
        }
      }
      return false;
    }



    /**
     * Remove a schema
     * @param schema
     */
    public void removeValueTypeDescription(PDFAValueTypeDescription schema){
      if(containsValueTypeDescription(schema)){
        valueTypes.remove(schema);
        content.removeChild(schema.content.getElement());
      }
    }




    public Element getElement() {
      return element;
    }

  }


  public class PropertyDescriptionContainer implements Elementable {

    protected Element element, content;
    protected List<PDFAPropertyDescription> properties;

    /**
     * 
     * SchemasDescription Container  constructor 
     */
    public PropertyDescriptionContainer() {
      element=metadata.getFuturOwner().createElement(PDFAExtensionSchema.PDFASCHEMASEP+PDFAExtensionSchema.PROPERTY);
      content=metadata.getFuturOwner().createElement("rdf:Seq");
      element.appendChild(content);

      properties=new ArrayList<PDFAPropertyDescription>();
    }



    /**
     * Add a SchemaDescription to the current structure
     * @param obj the property to add
     */
    public void addPropertyDescription(PDFAPropertyDescription obj){
      if(containsPropertyDescription(obj)){
        removePropertyDescription(getPropertyDescription(obj.getNameValue()));
      }
      properties.add(obj);
      content.appendChild(obj.content.getElement());
    }


    /**
     * Return all SchemaDescription
     * @return
     */
    public Iterator<PDFAPropertyDescription> getAllPropertyDescription(){
      return properties.iterator();
    }

    /**
     * Check if two SchemaDescription are similar
     * @param prop1
     * @param prop2
     * @return
     */
    public boolean isSamePropertyDescription(PDFAPropertyDescription prop1, PDFAPropertyDescription prop2){
      if(prop1.getClass().equals(prop2.getClass()) ){
        //Assuming that 2 properties can't have the same name
        if(prop1.getNameValue()	.equals(prop2.getNameValue())){
          return true;
        }
        //					if(prop1.content.getElement().getTextContent().equals(prop2.content.getElement().getTextContent())){
        //							return true;
        //						}
      }
      return false;
    }

    /**
     * Check if a specified SchemaDescription is embedded
     * @param schema
     * @return
     */
    public boolean containsPropertyDescription(PDFAPropertyDescription schema){
      Iterator<PDFAPropertyDescription> it=getAllPropertyDescription();
      PDFAPropertyDescription tmp;
      while(it.hasNext()){
        tmp=it.next();
        if(isSamePropertyDescription(tmp, schema) ){
          return true;
        }
      }
      return false;
    }

    public PDFAPropertyDescription getPropertyDescription(String name){
      Iterator<PDFAPropertyDescription> it=getAllPropertyDescription();
      PDFAPropertyDescription tmp;
      while(it.hasNext()){
        tmp=it.next();
        if(tmp.getNameValue().equals(name)){
          return tmp;
        }
      }
      return null;
    }


    /**
     * Remove a schema
     * @param schema
     */
    public void removePropertyDescription(PDFAPropertyDescription schema){
      if(containsPropertyDescription(schema)){
        properties.remove(schema);
        content.removeChild(schema.content.getElement());
      }
    }




    public Element getElement() {
      return element;
    }

  }


  public Element getElement() {
    return content.getElement();
  }



}

