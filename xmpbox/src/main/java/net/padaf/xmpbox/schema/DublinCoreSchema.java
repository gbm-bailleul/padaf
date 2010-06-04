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

import java.util.Calendar;
import java.util.List;

import net.padaf.xmpbox.XMPMetadata;
import net.padaf.xmpbox.type.ComplexProperty;
import net.padaf.xmpbox.type.TextType;


/**
 * Representation of a DublinCore Schema
 * @author Germain Costenobel
 *
 */
public class DublinCoreSchema extends XMPSchema{

  public static final String PREFERRED_DC_PREFIX="dc";

  public static final String DCURI="http://purl.org/dc/elements/1.1/";

  @PropertyType(propertyType="bag Text")
  public static final String CONTRIBUTOR="contributor";
  
  @PropertyType(propertyType="Text")
  public static final String COVERAGE="coverage" ;
  
  @PropertyType(propertyType="seq Text")
  public static final String CREATOR="creator" ;
  
  @PropertyType(propertyType="seq Date")
  public static final String DATE="date" ;

  @PropertyType(propertyType="Lang Alt")
  public static final String DESCRIPTION="description" ;
  
  @PropertyType(propertyType="Text")
  public static final String FORMAT="format" ;
  
  @PropertyType(propertyType="Text")
  public static final String IDENTIFIER="identifier" ;
  
  @PropertyType(propertyType="bag Text")
  public static final String LANGUAGE="language" ;
  
  @PropertyType(propertyType="bag Text")
  public static final String PUBLISHER="publisher" ;
  
  @PropertyType(propertyType="bag Text")
  public static final String RELATION="relation" ;
  
  @PropertyType(propertyType="Lang Alt")
  public static final String RIGHTS="rights" ;
  
  @PropertyType(propertyType="Text")
  public static final String SOURCE="source" ;
  
  @PropertyType(propertyType="bag Text")
  public static final String SUBJECT="subject" ;
 
  @PropertyType(propertyType="Lang Alt")
  public static final String TITLE="title" ;

  @PropertyType(propertyType="Text")
  public static final String TYPE="type" ;

  /**
   * Constructor of a Dublin Core schema
   * @param metadata
   */
  public DublinCoreSchema(XMPMetadata metadata) {
    super(metadata, PREFERRED_DC_PREFIX, DCURI);
  }

  /**
   * Constructor of a Dublin Core schema
   * @param metadata
   */
  public DublinCoreSchema(XMPMetadata metadata, String ownPrefix) {
    super(metadata, ownPrefix, DCURI);
  }



  /**
   * set contributor(s) to the resource (other than the authors)
   * @param properName
   */
  public void addToContributorValue(String properName){
      addBagValue(localPrefixSep+CONTRIBUTOR, properName);
  }

  /**
   * set the extent or scope of the resource
   * @param text
   */
  public void setCoverageValue(String text){
      addProperty(new TextType(metadata, localPrefix, COVERAGE, text));
  }

  /**
   * set the extent or scope of the resource
   * @param text
   */
  public void setCoverage (TextType text) {
    addProperty(text);
  }
  
  /**
   * set the autor(s) of the resource
   * @param properName
   * @throws InappropriateTypeException 
   */
  public void addToCreatorValue(String properName) {
    addSequenceValue(localPrefixSep+CREATOR, properName);
  }

  /**
   * Set date(s) that something interesting happened to the resource
   * @param date
   */
  public void addToDateValue(Calendar date){
    addSequenceDateValue(localPrefixSep+DATE, date);
  }

  /**
   * add a textual description of the content of the resource
   * (multiple values may be present for different languages)
   * @param lang
   * @param value
   */
  public void addToDescriptionValue(String lang, String value){
    setLanguagePropertyValue(localPrefixSep+DESCRIPTION, lang, value);
  }

  /**
   * set the file format used when saving the resource.
   * @param mimeType
   */
  public void setFormatValue(String mimeType){
      addProperty(new TextType(metadata, localPrefix, FORMAT, mimeType));
  }

  /**
   * Set the unique identifier of the resource
   * @param text
   */
  public void setIdentifierValue(String text){
      addProperty(new TextType(metadata, localPrefix, IDENTIFIER, text));
  }

  /**
   * Set the unique identifier of the resource
   * @param text
   */
  public void setIdentifier(TextType text){
      addProperty(text);
  }

  
  /**
   * Add language(s) used in this resource
   * @param locale
   */
  public void addToLanguageValue(String locale){
      addBagValue(localPrefixSep+LANGUAGE, locale);
  }

  /**
   * add publisher(s)
   * @param properName
   */
  public void addToPublisherValue(String properName){
      addBagValue(localPrefixSep+PUBLISHER, properName);
  }

  /**
   * Add relationships to other documents
   * @param text
   */
  public void addToRelationValue(String text){
      addBagValue(localPrefixSep+RELATION, text);
  }

  /**
   * add informal rights statement, by language.
   * @param lang
   * @param value
   */
  public void addToRightsValue(String lang, String value){
    setLanguagePropertyValue(localPrefixSep+RIGHTS, lang, value);
  }

  /**
   * Set the unique identifer of the work from which this resource was derived
   * @param text
   */
  public void setSourceValue(String text){
      addProperty(new TextType(metadata, localPrefix, SOURCE, text));
  }

  /**
   * Set the unique identifer of the work from which this resource was derived
   * @param text
   */
  public void setSource(TextType text){
      addProperty(text);
  }
 
  /**
   * Set the unique identifer of the work from which this resource was derived
   * @param text
   */
  public void setFormat(TextType text){
      addProperty(text);
  }
  

  
  /**
   * add descriptive phrases or keywords that specify the topic of the content of the resource
   * @param text
   */
  public void addToSubjectValue(String text){
      addBagValue(localPrefixSep+SUBJECT, text);
  }

  /**
   * set the title of the document, or the name given to the resource (by language)
   * @param lang
   * @param value
   */
  public void addToTitleValue(String lang, String value){
    setLanguagePropertyValue(localPrefixSep+TITLE, lang, value);
  }

  /**
   * set the document type (novel, poem, ...)
   * @param type
   */
  public void addToTypeValue(String type){
      addBagValue(localPrefixSep+TYPE, type);
  }



  /**
   * Return the Bag of contributor(s)
   * @return
   */
  public ComplexProperty getContributor(){
    return (ComplexProperty)getProperty(localPrefixSep+CONTRIBUTOR);
  }

  /**
   * Return a String list of contributor(s)
   * @return
   */
  public List<String> getContributorValue(){
    return getBagValueList(localPrefixSep+CONTRIBUTOR);

  }

  /**
   * Return the Coverage TextType Property
   * @return
   */
  public TextType getCoverage(){
    return (TextType)getProperty(localPrefixSep+COVERAGE);
  }

  /**
   * Return the value of the coverage
   * @return
   */
  public String getCoverageValue(){
    TextType tt = (TextType)getProperty(localPrefixSep+COVERAGE);
    return tt==null?null:tt.getStringValue();
  }

  /**
   * Return the Sequence of contributor(s)
   * @return
   */
  public ComplexProperty getCreator(){
    return (ComplexProperty)getProperty(localPrefixSep+CREATOR);
  }

  /**
   * Return the creator(s) string value
   * @return
   */
  public List<String> getCreatorValue(){
    return getSequenceValueList(localPrefixSep+CREATOR);
  }

  /**
   * Return the sequence of date(s)
   * @return
   */
  public ComplexProperty getDate(){
    return (ComplexProperty)getProperty(localPrefixSep+DATE);
  }

  /**
   * Return a calendar list of date
   * @return
   */
  public List<Calendar> getDateValue() {
    return getSequenceDateValueList(localPrefixSep+DATE);
  }

  /**
   * Return the Lang alt Description
   * @return
   */
  public ComplexProperty getDescription(){
    return (ComplexProperty)getProperty(localPrefixSep+DESCRIPTION);
  }

  /**
   * Return a list of languages defined in description property
   * @return
   */
  public List<String> getDescriptionLanguages(){
    return getLanguagePropertyLanguagesValue(localPrefixSep+DESCRIPTION);
  }

  /**
   * Return a language value for description property
   * @param lang
   * @return
   */
  public String getDescriptionValue(String lang){
    return getLanguagePropertyValue(localPrefixSep+DESCRIPTION, lang);
  }


  /**
   * Return the file format property 
   * @return
   */
  public TextType getFormat(){
    return (TextType)getProperty(localPrefixSep+FORMAT);
  }

  /**
   * return the file format value
   * @return
   */
  public String getFormatValue(){
    TextType tt = (TextType)getProperty(localPrefixSep+FORMAT);
    return tt==null?null:tt.getStringValue();
  }

  /**
   * Return the unique identifier property of this resource
   * @return
   */
  public TextType getIdentifier(){
    return (TextType)getProperty(localPrefixSep+IDENTIFIER);
  }

  /**
   * return the unique identifier value of this resource
   * @return
   */
  public String getIdentifierValue(){
    TextType tt = (TextType)getProperty(localPrefixSep+IDENTIFIER);
    return tt==null?null:tt.getStringValue();
  }


  /**
   * Return the bag DC language
   * @return
   */
  public ComplexProperty getLanguage(){
    return (ComplexProperty)getProperty(localPrefixSep+LANGUAGE);
  }

  /**
   * Return the list of values defined in the DC language
   * @return
   */
  public List<String> getLanguageValue(){
    return getBagValueList(localPrefixSep+LANGUAGE);
  }

  /**
   * Return the bag DC publisher
   * @return
   */
  public ComplexProperty getPublisher(){
    return (ComplexProperty)getProperty(localPrefixSep+PUBLISHER);
  }

  /**
   * Return the list of values defined in the DC publisher
   * @return
   */
  public List<String> getPublisherValue(){
    return getBagValueList(localPrefixSep+PUBLISHER);
  }


  /**
   * Return the bag DC relation
   * @return
   */
  public ComplexProperty getRelation(){
    return (ComplexProperty)getProperty(localPrefixSep+RELATION);
  }

  /**
   * Return the list of values defined in the DC relation
   * @return
   */
  public List<String> getRelationValue(){
    return getBagValueList(localPrefixSep+RELATION);
  }

  /**
   * Return the Lang alt Rights
   * @return
   */
  public ComplexProperty getRights(){
    return (ComplexProperty)getProperty(localPrefixSep+RIGHTS);
  }

  /**
   * Return a list of languages defined in Right property
   * @return
   */
  public List<String> getRightsLanguages(){
    return getLanguagePropertyLanguagesValue(localPrefixSep+RIGHTS);
  }

  /**
   * Return a language value for Right property
   * @param lang
   * @return
   */
  public String getRightsValue(String lang){
    return getLanguagePropertyValue(localPrefixSep+RIGHTS, lang);
  }

  /**
   * Return the source property of this resource
   * @return
   */
  public TextType getSource(){
    return (TextType)getProperty(localPrefixSep+SOURCE);
  }

  /**
   * return the source value of this resource
   * @return
   */
  public String getSourceValue(){
    TextType tt = (TextType)getProperty(localPrefixSep+SOURCE);
    return tt==null?null:tt.getStringValue();
  }

  /**
   * Return the bag DC Subject
   * @return
   */
  public ComplexProperty getSubject(){
    return (ComplexProperty)getProperty(localPrefixSep+SUBJECT);
  }

  /**
   * Return the list of values defined in the DC Subject
   * @return
   */
  public List<String> getSubjectValue(){
    return getBagValueList(localPrefixSep+SUBJECT);
  }

  /**
   * Return the Lang alt Title
   * @return
   */
  public ComplexProperty getTitle(){
    return (ComplexProperty)getProperty(localPrefixSep+TITLE);
  }

  /**
   * Return a list of languages defined in Title property
   * @return
   */
  public List<String> getTitleLanguages(){
    return getLanguagePropertyLanguagesValue(localPrefixSep+TITLE);
  }

  /**
   * Return a language value for Title property
   * @param lang
   * @return
   */
  public String getTitleValue(String lang){
    return getLanguagePropertyValue(localPrefixSep+TITLE, lang);
  }

  /**
   * Return the bag DC Type
   * @return
   */
  public ComplexProperty getType(){
    return (ComplexProperty)getProperty(localPrefixSep+TYPE);
  }

  /**
   * Return the list of values defined in the DC Type
   * @return
   */
  public List<String> getTypeValue(){
    return getBagValueList(localPrefixSep+TYPE);
  }

}
