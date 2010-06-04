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
import java.util.Calendar;
import java.util.List;

import net.padaf.xmpbox.TransformException;
import net.padaf.xmpbox.XMPMetadata;
import net.padaf.xmpbox.type.AbstractField;
import net.padaf.xmpbox.type.BadFieldValueException;
import net.padaf.xmpbox.type.ComplexProperty;
import net.padaf.xmpbox.type.DateType;
import net.padaf.xmpbox.type.IntegerType;
import net.padaf.xmpbox.type.TextType;
import net.padaf.xmpbox.type.ThumbnailType;



/**
 * Representation of XMPBasic Schema
 * @author Germain Costenobel
 *
 */
public class XMPBasicSchema extends XMPSchema{

  public static final String PREFERRED_XMP_PREFIX="xmp";

  public static final String XMPBASICURI="http://ns.adobe.com/xap/1.0/";

  @PropertyType(propertyType = "bag Xpath")
  public static String ADVISORY="Advisory";

  @PropertyType(propertyType = "URL")
  public static String BASEURL="BaseURL";

  @PropertyType(propertyType = "Date")
  public static String CREATEDATE="CreateDate";

  @PropertyType(propertyType = "Text")
  public static String CREATORTOOL="CreatorTool";

  @PropertyType(propertyType = "bag Text")
  public static String IDENTIFIER="Identifier";

  @PropertyType(propertyType = "Text")
  public static String LABEL="Label";

  @PropertyType(propertyType = "Date")
  public static String METADATADATE="MetadataDate";

  @PropertyType(propertyType = "Date")
  public static String MODIFYDATE="ModifyDate";

  @PropertyType(propertyType = "Text")
  public static String NICKNAME="Nickname";

  @PropertyType(propertyType = "Integer")
  public static String RATING="Rating";

  @PropertyType(propertyType = "Alt Thumbnail")
  public static String THUMBNAILS="Thumbnails";

  protected ComplexProperty altThumbs=null;
  
  public XMPBasicSchema(XMPMetadata metadata) {
    super(metadata, PREFERRED_XMP_PREFIX, XMPBASICURI);
    
  }
  
  public XMPBasicSchema(XMPMetadata metadata, String ownPrefix) {
    super(metadata, ownPrefix, XMPBASICURI);

  }

  /**
   * Add thumbnail to thumbnails list
 * @throws TransformException 
   */
  public void addThumbnails(Integer height, Integer width, String format, String img) throws TransformException{
	  if(altThumbs==null){
		  altThumbs= new ComplexProperty(metadata, localPrefix, THUMBNAILS, ComplexProperty.ALTERNATIVE_ARRAY);
		  addProperty(altThumbs);
	  }
	  ThumbnailType thumb=new ThumbnailType(metadata, "rdf", "li");
	  /*     <xapGImg:height>162</xapGImg:height>
      <xapGImg:width>216</xapGImg:width>
      <xapGImg:format>JPEG</xapGImg:format>
      <xapGImg:image>/9j/4AAQSkZJRgABAgEASABIAAD</xapGImg:image>*/
	  thumb.setHeight("xapGImg", "height", height);
	  thumb.setWidth("xapGImg", "width", width);
	  thumb.setFormat("xapGImg", "format", format);
	  thumb.setImg("xapGImg", "image", img);
	  altThumbs.getContainer().addProperty(thumb);
	//SaveMetadataHelper.serialize(metadata, System.out);
  }
  
  /**
   * Add a property specification that were edited outside the authoring application
   * @param xpath
   */
  public void addToAdvisoryValue(String xpath){
    addBagValue(localPrefixSep+ADVISORY, xpath);
  }

  /**
   * Set the base URL for relative URLs in the document content
   * @param url
   */
  public void setBaseURLValue(String url){
    addProperty(new TextType(metadata, localPrefix, BASEURL, url));
  }

  /**
   * Set the base URL for relative URLs in the document content
   * @param url
   */
  public void setBaseURL(TextType url){
    addProperty(url);
  }
  
  /**
   * Set the date and time the resource was originally created
   * @param date
   */
  public void setCreateDateValue(Calendar date){
    addProperty(new DateType(metadata, localPrefix, CREATEDATE, date));
  }

  /**
   * Set the date and time the resource was originally created
   * @param date
   */
  public void setCreateDate(DateType date){
    addProperty(date);
  }
  
  /**
   * set the name of the first known tool used to create this resource
   * @param creatorTool
   */
  public void setCreatorToolValue(String creatorTool){
    addProperty(new TextType(metadata, localPrefix, CREATORTOOL, creatorTool));
  }

  /**
   * set the name of the first known tool used to create this resource
   * @param creatorTool
   */
  public void setCreatorTool(TextType creatorTool){
    addProperty(creatorTool);
  }
  
  public void addThumbnail(ThumbnailType thumb){
	  
  }
  
  /**
   * Add a text string which unambiguously identify the resource within a given context
   * @param text
   */
  public void addToIdentifierValue(String text){
    addBagValue(localPrefixSep+IDENTIFIER, text);
  }

  /**
   * set a word or a short phrase which identifies a document as a member of a user-defined collection
   * @param text
   */
  public void setLabelValue(String text){
    addProperty(new TextType(metadata, localPrefix, LABEL, text));
  }

  /**
   * set a word or a short phrase which identifies a document as a member of a user-defined collection
   * @param text
   */
  public void setLabel(TextType text){
    addProperty(text);
  }
  
  /**
   * Set the date and time that any metadata for this resource was last changed.
   * (should be equals or more recent than the createDate)
   * @param date
   */
  public void setMetadataDateValue(Calendar date){
    addProperty(new DateType(metadata, localPrefix, METADATADATE, date));
  }

  public void setMetadataDate(DateType date){
    addProperty(date);
  }
  
  /**
   * Set the date and time the resource was last modified
   * @param date
   */
  public void setModifyDateValue(Calendar date){
    addProperty(new DateType(metadata, localPrefix, MODIFYDATE, date));
  }

  public void setModifyDate(DateType date){
    addProperty(date);
  }

  /**
   * Set a short informal name for the resource
   * @param text
   */
  public void setNicknameValue(String text){
    addProperty(new TextType(metadata, localPrefix, NICKNAME, text));
  }

  public void setNickname(TextType text){
	addProperty(text);
  }
  
  /**
   * Set a number that indicates a document's status relative to other documents, used to organize documents in a file browser
   * (values are user-defined within an application-defined range)
   * @param rate
   */
  public void setRatingValue(Integer rate){
    addProperty(new IntegerType(metadata, localPrefix, RATING, rate));
  }

  public void setRating(IntegerType rate){
    addProperty(rate);
  }
  
  /**
   * Get the Advisory property 
   * @return
   */
  public ComplexProperty getAdvisory(){
    return (ComplexProperty)getProperty(localPrefixSep+ADVISORY);
  }

  /**
   * Get the Advisory property values
   * @return
   */
  public List<String> getAdvisoryValue(){
    return getBagValueList(localPrefixSep+ADVISORY);
  }

  /**
   * Get the BaseURL property
   * @return
   */
  public TextType getBaseURL(){
    return (TextType)getProperty(localPrefixSep+BASEURL);
  }

  /**
   * Get the BaseURL property value
   * @return
   */
  public String getBaseURLValue() {
	  TextType tt = ((TextType)getProperty(localPrefixSep+BASEURL));
	  return tt==null?null:tt.getStringValue();
  }

  /**
   * Get the CreateDate property
   * @return
   */
  public DateType getCreateDate(){
    return (DateType)getProperty(localPrefixSep+CREATEDATE);
  }

  /**
   * Get the CreateDate property value
   * @return
   */
  public Calendar getCreateDateValue(){
    DateType createDate=  (DateType)getProperty(localPrefixSep+CREATEDATE);
    if(createDate!=null){
      return createDate.getValue();
    }
    return null;
  }

  /**
   * Get the CreationTool property
   * @return
   */
  public TextType getCreatorTool(){
    return (TextType)getProperty(localPrefixSep+CREATORTOOL);
  }

  /**
   * Get the CreationTool property value
   * @return
   */
  public String getCreatorToolValue(){
	  TextType tt =((TextType)getProperty(localPrefixSep+CREATORTOOL));
	  return tt==null?null:tt.getStringValue();
  }

  /**
   * Get the Identifier property 
   * @return
   */
  public ComplexProperty getIdentifier(){
    return (ComplexProperty)getProperty(localPrefixSep+IDENTIFIER);
  }

  /**
   * Get the Identifier property values
   * @return
   */
  public List<String> getIdentifierValue(){
    return getBagValueList(localPrefixSep+IDENTIFIER);
  }

  /**
   * Get the label property 
   * @return
   */
  public TextType getLabel(){
    return (TextType)getProperty(localPrefixSep+LABEL);
  }

  /**
   * Get the label property value
   * @return
   */
  public String getLabelValue(){
	  TextType tt = ((TextType)getProperty(localPrefixSep+LABEL));
	  return tt==null?null:tt.getStringValue();
  }

  /**
   * Get the MetadataDate property 
   * @return
   */
  public DateType getMetadataDate(){
    return (DateType)getProperty(localPrefixSep+METADATADATE);
  }

  /**
   * Get the MetadataDate property value
   * @return
   */
  public Calendar getMetadataDateValue(){
    DateType dt = ((DateType)getProperty(localPrefixSep+METADATADATE));
    return dt==null?null:dt.getValue();
  }

  /**
   * Get the ModifyDate property
   * @return
   */
  public DateType getModifyDate(){
    return (DateType)getProperty(localPrefixSep+MODIFYDATE);
  }

  /**
   * Get the ModifyDate property value
   * @return
   */
  public Calendar getModifyDateValue(){
    DateType modifyDate=  (DateType)getProperty(localPrefixSep+MODIFYDATE);
    if(modifyDate!=null){
      return modifyDate.getValue();
    }
    return null;

  }

  /**
   * Get the Nickname property
   * @return
   */
  public TextType getNickname(){
    return (TextType)getProperty(localPrefixSep+NICKNAME);
  }

  /**
   * Get the Nickname property value
   * @return
   */
  public String getNicknameValue(){
    TextType tt = ((TextType)getProperty(localPrefixSep+NICKNAME));
    return tt==null?null:tt.getStringValue();
  }

  /**
   * Get the Rating property
   * @return
   */
  public IntegerType getRating(){
    return ((IntegerType)getProperty(localPrefixSep+RATING));
  }

  /**
   * Get the Rating property value
   * @return
   */
  public Integer getRatingValue(){
    IntegerType it = ((IntegerType)getProperty(localPrefixSep+RATING));
    return it==null?null:it.getValue();
  }
  
  /**
   * Get list of Thumbnails
   * @return
   * @throws BadFieldValueException
   */
  public List<ThumbnailType> getThumbnails() throws BadFieldValueException{
	  List<AbstractField> tmp= getArrayList(localPrefixSep+THUMBNAILS);
	  if(tmp!=null){
		  List<ThumbnailType> thumbs=new ArrayList<ThumbnailType>();
		  for (AbstractField abstractField : tmp) {
			if(abstractField instanceof ThumbnailType){
				thumbs.add((ThumbnailType)abstractField);
			}else{
				throw new BadFieldValueException("Thumbnail expected and "+abstractField.getClass().getName()+" found.");
			}
		}
		return thumbs;
	  }
	  return null;
	  
  }
  
  
  
  

}
