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

import net.padaf.xmpbox.XMPMetadata;
import net.padaf.xmpbox.type.AbstractField;
import net.padaf.xmpbox.type.BadFieldValueException;
import net.padaf.xmpbox.type.IntegerType;
import net.padaf.xmpbox.type.TextType;


/**
 * Representation of PDF/A Identification Schema
 * @author Germain Costenobel
 *
 */
public class PDFAIdentificationSchema extends XMPSchema{

  public static final String IDPREFIX="pdfaid";
  public static final String IDPREFIXSEP="pdfaid:";
  public static final String IDURI="http://www.aiim.org/pdfa/ns/id/";

  @PropertyType(propertyType = "Integer")
  public static final String PART="part";

  @PropertyType(propertyType = "Text")
  public static final String AMD="amd";

  @PropertyType(propertyType = "Text")
  public static final String CONFORMANCE="conformance";

  /*
   * <rdf:Description rdf:about="" xmlns:pdfaid="http://www.aiim.org/pdfa/ns/id/">
   * 		<pdfaid:conformance>B</pdfaid:conformance>
   * 		<pdfaid:part>1</pdfaid:part>
   * </rdf:Description>
   */

  /**
   * Constructor of a PDF/A Identification schema
   */
  public PDFAIdentificationSchema(XMPMetadata metadata) {
    super(metadata, IDPREFIX, IDURI);
  }

  /**
   * Set the PDFA Version identifier (with string)
   * @param value
   * @throws InappropriateTypeException
   */
  public void setPartValueWithString(String value) {
    IntegerType part=new IntegerType(metadata, IDPREFIX, PART, value);
    addProperty(part);
  }

  /**
   * Set the PDFA Version identifier (with an int)
   * @param value
   * @throws InappropriateTypeException
   */
  public void setPartValueWithInt(int value) {
    IntegerType part=new IntegerType(metadata, IDPREFIX, PART, value);
    addProperty(part);
  }
  
  /**
   * Set the PDFA Version identifier (with an int)
   * @param value
   * @throws InappropriateTypeException
   */
  public void setPartValue(Integer value) {
    IntegerType part=new IntegerType(metadata, IDPREFIX, PART, value);
    addProperty(part);
  }
  
  public void setPart(IntegerType part) {
    addProperty(part);
  }

  /**
   * Set the PDF/A amendment identifier	
   * @param value
   */
  public void setAmdValue(String value){
    TextType amd = new TextType(metadata, IDPREFIX, AMD, value);
    addProperty(amd);
  }

  /**
   * Set the PDF/A amendment identifier 
   * @param amd
   */
  public void setAmd(TextType amd) {
    addProperty(amd);
  }

  
  
  /**
   * Set the PDF/A conformance level	
   * @param value
   * @throws BadFieldValueException
   */
  public void setConformanceValue(String value) throws BadFieldValueException{
    if(value.equals("A") || value.equals("B")){
      TextType conf= new TextType(metadata, IDPREFIX, CONFORMANCE, value);
      addProperty(conf);

    }else{
      throw new BadFieldValueException("The property given not seems to be a PDF/A conformance level (must be A or B)");
    }
  }

  /**
   * Set the PDF/A conformance level    
   * @param value
   * @throws BadFieldValueException
   */
  public void setConformance(TextType conf) throws BadFieldValueException{
    String value = conf.getStringValue();
    if(value.equals("A") || value.equals("B")){
      addProperty(conf);
    }else{
      throw new BadFieldValueException("The property given not seems to be a PDF/A conformance level (must be A or B)");
    }
  }



  /**
   * Give the PDFAVersionId (as an integer)
   * @return
   */
  public Integer getPartValue(){
    AbstractField tmp=getProperty(IDPREFIXSEP+PART);
    if(tmp!=null){
      if(tmp instanceof IntegerType){
        return ((IntegerType) tmp).getValue();
      }
    }
    return null;
  }

  /**
   * Give the property corresponding to the PDFA Version id
   * @return
   */
  public IntegerType getPart(){
    AbstractField tmp=getProperty(IDPREFIXSEP+PART);
    if(tmp!=null){
      if(tmp instanceof IntegerType){
        return (IntegerType) tmp;
      }
    }
    return null;
  }

  /**
   * Give the PDFAAmendmentId (as an String)
   * @return
   */
  public String getAmendmentValue(){
    AbstractField tmp=getProperty(IDPREFIXSEP+AMD);
    if(tmp!=null){
      if(tmp instanceof TextType){
        return ((TextType) tmp).getStringValue();
      }
    }
    return null;
  }

  /**
   * Give the property corresponding to the PDFA Amendment id
   * @return
   */
  public TextType getAmd(){
    AbstractField tmp=getProperty(IDPREFIXSEP+AMD);
    if(tmp!=null){
      if(tmp instanceof TextType){
        return (TextType) tmp;
      }
    }
    return null;
  }

  /**
   * Give the PDFA Conformance Id (as an String)
   * @return
   */
  public String getAmdValue(){
    AbstractField tmp=getProperty(IDPREFIXSEP+AMD);
    if(tmp!=null){
      if(tmp instanceof TextType){
        return ((TextType) tmp).getStringValue();
      }
    }
    return null;
  }

  /**
   * Give the property corresponding to the PDFA Conformance id
   * @return
   */
  public TextType getConformance(){
    AbstractField tmp=getProperty(IDPREFIXSEP+CONFORMANCE);
    if(tmp!=null){
      if(tmp instanceof TextType){
        return (TextType) tmp;
      }
    }
    return null;
  }

  public String getConformanceValue() {
    TextType tt = getConformance();
    return (tt==null)?null:tt.getStringValue();
  }




}
