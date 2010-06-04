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
import net.padaf.xmpbox.type.BadFieldValueException;
import net.padaf.xmpbox.type.ComplexPropertyContainer;
import net.padaf.xmpbox.type.Elementable;
import net.padaf.xmpbox.type.TextType;

import org.w3c.dom.Element;


/**
 * Representation of a PDF/A property type schema
 * @author Germain Costenobel
 *
 */
public class PDFAPropertyDescription implements Elementable{

  public static final String PDFAPROPPREFIX="pdfaProperty";
  public static final String PDFAPROPPREFIXSEP="pdfaProperty:";
  protected XMPMetadata metadata;
  protected ComplexPropertyContainer content;

  public static final String NAME="name";
  public static final String VALUETYPE="valueType";
  public static final String CATEGORY="category";
  public static final String DESCRIPTION="description";

  /**
   * Build a new property description
   * @param metadata
   */
  public PDFAPropertyDescription(XMPMetadata metadata) {
    this.metadata=metadata;
    content=new ComplexPropertyContainer(metadata, "rdf", "li");
    content.setAttribute(new Attribute(null, "rdf", "parseType", "Resource"));
  }

  /**
   * set the name of this property
   * @param name
   */
  public void setNameValue(String name){
    content.addProperty(new TextType(metadata, PDFAPROPPREFIX, NAME, name));
  }

  /**
   *  set the value type of this property
   * @param type
   */
  public void setValueTypeValue(String type){
    content.addProperty(new TextType(metadata, PDFAPROPPREFIX, VALUETYPE, type));
  }

  /**
   *  set the category of this property
   * @param type
   * @throws BadFieldValueException 
   */
  public void setCategoryValue(String category) throws BadFieldValueException{
    if(category.equals("external") || category.equals("internal")){
      content.addProperty(new TextType(metadata, PDFAPROPPREFIX, CATEGORY, category));
    }
    else{
      throw new BadFieldValueException("Unexpected value '"+category+"' for property category (only values 'internal' or 'external' are allowed)");
    }
  }

  /**
   * set the description of this property
   * @param name
   */
  public void setDescriptionValue(String desc){
    content.addProperty(new TextType(metadata, PDFAPROPPREFIX, DESCRIPTION, desc));
  }


  private String getPropertyValue(String qualifiedName){
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
   * Return the current defined name (in a string)
   * @return
   */
  public String getNameValue(){
    return getPropertyValue(PDFAPROPPREFIXSEP+NAME);
  }

  /**
   * Return the current ValueType (in a string)
   * @return
   */
  public String getValueTypeValue(){
    return getPropertyValue(PDFAPROPPREFIXSEP+VALUETYPE);
  }

  /**
   * Return the current category (in a string)
   * @return
   */
  public String getCategoryValue(){
    return getPropertyValue(PDFAPROPPREFIXSEP+CATEGORY);
  }

  /**
   * Return the current description (in a string)
   * @return
   */
  public String getDescriptionValue(){
    return getPropertyValue(PDFAPROPPREFIXSEP+DESCRIPTION);
  }

  private TextType getProperty(String qualifiedName){
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
   * Return the property corresponding to the property name definition
   * @return
   */
  public TextType getName(){
    return getProperty(PDFAPROPPREFIXSEP+NAME);
  }

  /**
   * Return the property corresponding to the property valueType definition
   * @return
   */
  public TextType getValueType(){
    return getProperty(PDFAPROPPREFIXSEP+VALUETYPE);
  }

  /**
   * Return the property corresponding to the property category definition
   * @return
   */
  public TextType getCategory(){
    return getProperty(PDFAPROPPREFIXSEP+CATEGORY);
  }

  /**
   * Return the property corresponding to the property description definition
   * @return
   */
  public TextType getDescription(){
    return getProperty(PDFAPROPPREFIXSEP+DESCRIPTION);
  }


  public Element getElement() {
    return content.getElement();
  }
}
