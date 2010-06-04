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
import net.padaf.xmpbox.type.ComplexPropertyContainer;
import net.padaf.xmpbox.type.Elementable;
import net.padaf.xmpbox.type.TextType;

import org.w3c.dom.Element;


/**
 * Representation of a PDF/A Value type schema
 * @author Germain Costenobel
 *
 */
public class PDFAValueTypeDescription implements Elementable{

  public static final String PDFATYPEPREFIX="pdfaType";
  public static final String PDFATYPEPREFIXSEP="pdfaType:";

  public static final String TYPE="type";
  public static final String NS_URI="namespaceURI";
  public static final String PREFIX="prefix";
  public static final String DESCRIPTION="description";
  public static final String FIELD= "field";


  protected FieldDescriptionContainer fields;
  protected XMPMetadata metadata;
  protected ComplexPropertyContainer content;

  /**
   * Build a new valuetype description
   * @param metadata
   */
  public PDFAValueTypeDescription(XMPMetadata metadata) {
    this.metadata=metadata;
    content= new ComplexPropertyContainer(metadata, "rdf", "li");
    content.setAttribute(new Attribute(null, "rdf", "parseType", "Resource"));
    fields = new FieldDescriptionContainer();
    content.getElement().appendChild(fields.getElement());

  }

  /**
   * set the name of this valuetype
   * @param name
   */
  public void setTypeNameValue(String name){
    content.addProperty(new TextType(metadata, PDFATYPEPREFIX, TYPE, name));
  }

  /**
   *  set the namespaceURI of this valueType
   * @param type
   */
  public void setNamespaceURIValue(String nsURI){
    content.addProperty(new TextType(metadata, PDFATYPEPREFIX, NS_URI, nsURI));
  }

  /**
   *  set the prefix of this valuetype
   * @param type
   */
  public void setPrefixValue(String prefix){
    content.addProperty(new TextType(metadata, PDFATYPEPREFIX, PREFIX, prefix));
  }

  /**
   * set the description of this property
   * @param name
   */
  public void setDescriptionValue(String desc){
    content.addProperty(new TextType(metadata, PDFATYPEPREFIX, DESCRIPTION, desc));
  }


  private String getValueTypeProperty(String qualifiedName){
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
   * Return the current defined type name (in a string)
   * @return
   */
  public String getTypeNameValue(){
    return getValueTypeProperty(PDFATYPEPREFIXSEP+TYPE);
  }

  /**
   * Return the current nsURI (in a string)
   * @return
   */
  public String getNamespaceURIValue(){
    return getValueTypeProperty(PDFATYPEPREFIXSEP+NS_URI);
  }

  /**
   * Return the current prefix (in a string)
   * @return
   */
  public String getPrefixValue(){
    return getValueTypeProperty(PDFATYPEPREFIXSEP+PREFIX);
  }

  /**
   * Return the current description (in a string)
   * @return
   */
  public String getDescriptionValue(){
    return getValueTypeProperty(PDFATYPEPREFIXSEP+DESCRIPTION);
  }

  private TextType getTypeProperty(String qualifiedName){
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
   * Return the property corresponding to the type name definition
   * @return
   */
  public TextType getTypeName(){
    return getTypeProperty(PDFATYPEPREFIXSEP+TYPE);
  }

  /**
   * Return the property corresponding to the Type namespaceURI definition
   * @return
   */
  public TextType getNamespaceURI(){
    return getTypeProperty(PDFATYPEPREFIXSEP+NS_URI);
  }

  /**
   * Return the property corresponding to the type prefix definition
   * @return
   */
  public TextType getPrefix(){
    return getTypeProperty(PDFATYPEPREFIXSEP+PREFIX);
  }

  /**
   * Return the property corresponding to the type description definition
   * @return
   */
  public TextType getDescription(){
    return getTypeProperty(PDFATYPEPREFIXSEP+DESCRIPTION);
  }

  /**
   * Give all Fields description embedded in this schema
   * 
   * @return
   */
  public List<PDFAFieldDescription> getFields() {
    return Collections.unmodifiableList(fields.fields);
  }


  /**
   * Add a field description to this valuetype
   * @param name
   * @param valueType
   * @param description
   * @return
   */
  public PDFAFieldDescription addField(String name, String valueType, String description) {
    PDFAFieldDescription field= new PDFAFieldDescription(metadata);
    field.setNameValue(name);
    field.setValueTypeValue(valueType);
    field.setDescriptionValue(description);
    fields.addFieldDescription(field);
    return field;
  }

  /**
   * Add a Structured Field to this valueType
   * @param field
   */
  public void addField(PDFAFieldDescription field){
    fields.addFieldDescription(field);
  }


  public Element getElement() {
    return content.getElement();
  }


  public class FieldDescriptionContainer implements Elementable {

    protected Element element, content;
    protected List<PDFAFieldDescription> fields;

    /**
     * 
     * SchemasDescription Container  constructor 
     */
    public FieldDescriptionContainer() {
      element=metadata.getFuturOwner().createElement(PDFAExtensionSchema.PDFATYPESEP+FIELD);
      content=metadata.getFuturOwner().createElement("rdf:Seq");
      element.appendChild(content);

      fields=new ArrayList<PDFAFieldDescription>();
    }



    /**
     * Add a SchemaDescription to the current structure
     * @param obj the property to add
     */
    public void addFieldDescription(PDFAFieldDescription obj){
      if(containsFieldDescription(obj)){
        removeFieldDescription(obj);
      }
      fields.add(obj);
      content.appendChild(obj.content.getElement());
    }


    /**
     * Return all SchemaDescription
     * @return
     */
    public Iterator<PDFAFieldDescription> getAllFieldDescription(){
      return fields.iterator();
    }

    /**
     * Check if two SchemaDescription are similar
     * @param prop1
     * @param prop2
     * @return
     */
    public boolean isSameFieldDescription(PDFAFieldDescription prop1, PDFAFieldDescription prop2){
      if(prop1.getClass().equals(prop2.getClass()) ){
        if(prop1.content.getElement().getTextContent().equals(prop2.content.getElement().getTextContent())){
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
    public boolean containsFieldDescription(PDFAFieldDescription schema){
      Iterator<PDFAFieldDescription> it=getAllFieldDescription();
      PDFAFieldDescription tmp;
      while(it.hasNext()){
        tmp=it.next();
        if(isSameFieldDescription(tmp, schema) ){
          return true;
        }
      }
      return false;
    }



    /**
     * Remove a schema
     * @param schema
     */
    public void removeFieldDescription(PDFAFieldDescription schema){
      if(containsFieldDescription(schema)){
        fields.remove(schema);
        content.removeChild(schema.content.getElement());
      }
    }




    public Element getElement() {
      return element;
    }

  }










}
