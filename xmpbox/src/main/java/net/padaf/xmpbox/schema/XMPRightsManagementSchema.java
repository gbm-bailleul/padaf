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

import java.util.List;

import net.padaf.xmpbox.XMPMetadata;
import net.padaf.xmpbox.type.BooleanType;
import net.padaf.xmpbox.type.ComplexProperty;
import net.padaf.xmpbox.type.TextType;

public class XMPRightsManagementSchema extends XMPSchema {
  public static final String PREFERRED_XMPRIGHTS_PREFIX = "xmpRights";

  public static final String XMPRIGHTSURI = "http://ns.adobe.com/xap/1.0/rights/";

  @PropertyType(propertyType = "URL")
  public static final String CERTIFICATE="Certificate";

  @PropertyType(propertyType = "Boolean")
  public static final String MARKED="Marked";

  @PropertyType(propertyType = "bag ProperName")
  public static final String OWNER="Owner";

  @PropertyType(propertyType = "Lang Alt")
  public static final String USAGETERMS="UsageTerms";

  @PropertyType(propertyType = "URL")
  public static final String WEBSTATEMENT="WebStatement";

  public XMPRightsManagementSchema(XMPMetadata metadata) {
    super(metadata, PREFERRED_XMPRIGHTS_PREFIX, XMPRIGHTSURI);
  }

  public XMPRightsManagementSchema(XMPMetadata metadata, String ownPrefix) {
    super(metadata, ownPrefix, XMPRIGHTSURI);
  }

  /**
   * Set the legal owner of the described resource.
   * @param text
   */
 public void addToOwnerValue(String value){
	  addBagValue(localPrefixSep+OWNER,  value);
  }

  /**
   * Return the Bag of owner(s)
   * @return
   */
  public ComplexProperty getOwner(){
    return (ComplexProperty)getProperty(localPrefixSep+OWNER);
  }

  /**
   * Return a String list of owner(s)
   * @return
   */
  public List<String> getOwnerValue(){
    return getBagValueList(localPrefixSep+OWNER);
  }


  public void setMarkedValue(Boolean marked){
    addProperty(new BooleanType(metadata, localPrefix, MARKED, marked));
  }

  public void setMarked(BooleanType marked){
	  addProperty(marked);
  }
  
  public BooleanType getMarked(){
    return (BooleanType)getProperty(localPrefixSep+MARKED);
  }

  public Boolean getMarkedValue(){
    BooleanType bt = ((BooleanType)getProperty(localPrefixSep+MARKED));
    return bt==null?null:bt.getValue();
  }

  public void addToUsageTermsValue(String lang, String value){
    setLanguagePropertyValue(localPrefixSep+USAGETERMS, lang, value);
  }

  /**
   * Return the Lang alt UsageTerms
   * @return
   */
  public ComplexProperty getUsageTerms(){
    return (ComplexProperty)getProperty(localPrefixSep+USAGETERMS);
  }

  /**
   * Return a list of languages defined in description property
   * @return
   */
  public List<String> getUsageTermsLanguages(){
    return getLanguagePropertyLanguagesValue(localPrefixSep+USAGETERMS);
  }

  /**
   * Return a language value for description property
   * @param lang
   * @return
   */
  public String getUsageTermsValue(String lang){
    return getLanguagePropertyValue(localPrefixSep+USAGETERMS, lang);
  }

  /**
   * Return the WebStatement URL as TextType.
   * @return
   */
  public TextType getWebStatement(){
    return ((TextType)getProperty(localPrefixSep+WEBSTATEMENT));
  }

  /**
   * Return the WebStatement URL as String.
   * @return
   */
  public String getWebStatementValue(){
    TextType tt = ((TextType)getProperty(localPrefixSep+WEBSTATEMENT));
    return tt==null?null:tt.getStringValue();
  }

  /**
   * Set the WebStatement url 
   * @param url
   */
  public void setWebStatementValue(String url){
    addProperty(new TextType(metadata, localPrefix, WEBSTATEMENT, url));
  }

  /**
   * Set the WebStatement url 
   * @param url
   */
  public void setWebStatement(TextType url){
    addProperty(url);
  }
 
  /**
   * Return the Certificate URL as TextType.
   * @return
   */
  public TextType getCertificate(){
    return ((TextType)getProperty(localPrefixSep+CERTIFICATE));
  }

  /**
   * Return the Certificate URL as String.
   * @return
   */
  public String getCertificateValue(){
    TextType tt = ((TextType)getProperty(localPrefixSep+CERTIFICATE));
    return tt==null?null:tt.getStringValue();
  }

  /**
   * Set the Certificate URL. 
   * @param url
   */
  public void setCertificateValue(String url){
    addProperty(new TextType(metadata, localPrefix, CERTIFICATE, url));
  }
 
  /**
   * Set the Certificate URL. 
   * @param url
   */
  public void setCertificate(TextType url){
    addProperty(url);
  }
}
