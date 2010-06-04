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
import net.padaf.xmpbox.type.ComplexProperty;
import net.padaf.xmpbox.type.TextType;



/**
 * Representation of XMPMediaManagement Schema
 * @author Germain Costenobel
 *
 */
public class XMPMediaManagementSchema extends XMPSchema{

  public static final String PREFERRED_XMPMM_PREFIX="xmpMM";

  public static final String XMPMMURI="http://ns.adobe.com/xap/1.0/mm/";

  public XMPMediaManagementSchema(XMPMetadata metadata) {
    super(metadata, PREFERRED_XMPMM_PREFIX, XMPMMURI);

  }

  public XMPMediaManagementSchema(XMPMetadata metadata, String ownPrefix) {
    super(metadata, ownPrefix, XMPMMURI);

  }


  // -------------------------------- ResourceRef --------------------

  @PropertyType(propertyType = "Text")
  public static final String RESOURCEREF = "ResourceRef";

  public void setResourceRefValue (String url)  {
    setResourceRef(new TextType(metadata,localPrefix,RESOURCEREF,url));
  }

  public void setResourceRef (TextType tt) {
    addProperty(tt);
  }

  public String getResourceRefValue () {
    TextType tt = getResourceRef();
    return tt!=null?tt.getStringValue():null;
  }

  public TextType getResourceRef () {
    return (TextType)getProperty(localPrefixSep+RESOURCEREF);
  }

  // --------------------------------------- DocumentID ----------------------------

  @PropertyType(propertyType = "Text")
  public static final String DOCUMENTID="DocumentID";

  public void setDocumentIDValue(String url)  {
    setDocumentID (new TextType(metadata, localPrefix, DOCUMENTID, url));
  }

  public void setDocumentID (TextType tt) {
    addProperty(tt);
  }

  public TextType getDocumentID(){
    return (TextType)getProperty(localPrefixSep+DOCUMENTID);
  }

  public String getDocumentIDValue(){
    TextType tt = getDocumentID();
    return tt!=null?tt.getStringValue():null;
  }

  // --------------------------------------- Manager ----------------------------

  @PropertyType(propertyType = "Text")
  public static final String MANAGER="Manager";

  public void setManagerValue(String url) {
    setManager (new TextType(metadata, localPrefix, MANAGER, url));
  }

  public void setManager (TextType tt) {
    addProperty(tt);
  }

  public TextType getManager(){
    return (TextType)getProperty(localPrefixSep+MANAGER);
  }

  public String getManagerValue(){
    TextType tt = getManager();
    return tt!=null?tt.getStringValue():null;
  }

  // --------------------------------------- ManageTo ----------------------------

  @PropertyType(propertyType = "Text")
  public static final String MANAGETO="ManageTo";

  public void setManageToValue(String url)  {
    setManageTo (new TextType(metadata, localPrefix, MANAGETO, url));
  }

  public void setManageTo (TextType tt) {
    addProperty(tt);
  }

  public TextType getManageTo(){
    return (TextType)getProperty(localPrefixSep+MANAGETO);
  }

  public String getManageToValue(){
    TextType tt = getManageTo();
    return tt!=null?tt.getStringValue():null;
  }

  // --------------------------------------- ManageUI ----------------------------

  @PropertyType(propertyType = "Text")
  public static final String MANAGEUI="ManageUI";

  public void setManageUIValue(String url)  {
    setManageUI (new TextType(metadata, localPrefix, MANAGEUI, url));
  }

  public void setManageUI (TextType tt) {
    addProperty(tt);
  }

  public TextType getManageUI(){
    return (TextType)getProperty(localPrefixSep+MANAGEUI);
  }

  public String getManageUIValue(){
    TextType tt = getManageUI();
    return tt!=null?tt.getStringValue():null;
  }

  // --------------------------------------- ManagerVariant ----------------------------

  @PropertyType(propertyType = "Text")
  public static final String MANAGERVARIANT="ManagerVariant";

  public void setManagerVariantValue(String url) {
    setManagerVariant(new TextType(metadata, localPrefix, MANAGERVARIANT, url));
  }

  public void setManagerVariant (TextType tt) {
    addProperty(tt);
  }

  public TextType getManagerVariant(){
    return (TextType)getProperty(localPrefixSep+MANAGERVARIANT);
  }

  public String getManagerVariantValue(){
    TextType tt = getManagerVariant();
    return tt!=null?tt.getStringValue():null;
  }

  // --------------------------------------- InstanceID ----------------------------

  @PropertyType(propertyType = "Text")
  public static final String INSTANCEID="InstanceID";

  public void setInstanceIDValue(String url)  {
    setInstanceID(new TextType(metadata, localPrefix, INSTANCEID, url));
  }

  public void setInstanceID (TextType tt) {
    addProperty(tt);
  }

  public TextType getInstanceID(){
    return (TextType)getProperty(localPrefixSep+INSTANCEID);
  }

  public String getInstanceIDValue(){
    TextType tt = getInstanceID();
    return tt!=null?tt.getStringValue():null;
  }


  // --------------------------------------- ManageFrom ----------------------------

  @PropertyType(propertyType = "Text")
  public static final String MANAGEFROM="ManageFrom";

  public void setManageFromValue(String url)  {
    setManageFrom (new TextType(metadata, localPrefix, MANAGEFROM, url));
  }

  public void setManageFrom (TextType tt) {
    addProperty(tt);
  }

  public TextType getManageFrom(){
    return (TextType)getProperty(localPrefixSep+MANAGEFROM);
  }

  public String getManageFromValue(){
    TextType tt = getManageFrom();
    return tt!=null?tt.getStringValue():null;
  }


  // --------------------------------------- OriginalDocumentID ----------------------------

  @PropertyType(propertyType = "Text")
  public static final String ORIGINALDOCUMENTID="OriginalDocumentID";

  public void setOriginalDocumentIDValue(String url)  {
    setOriginalDocumentID (new TextType(metadata, localPrefix, ORIGINALDOCUMENTID, url));
  }

  public void setOriginalDocumentID (TextType tt) {
    addProperty(tt);
  }

  public TextType getOriginalDocumentID(){
    return (TextType)getProperty(localPrefixSep+ORIGINALDOCUMENTID);
  }

  public String getOriginalDocumentIDValue(){
    TextType tt = getOriginalDocumentID();
    return tt!=null?tt.getStringValue():null;
  }

  // --------------------------------------- RenditionClass ----------------------------

  @PropertyType(propertyType = "Text")
  public static final String RENDITIONCLASS="RenditionClass";

  public void setRenditionClassValue(String url)  {
    setRenditionClass (new TextType(metadata, localPrefix, RENDITIONCLASS, url));
  }

  public void setRenditionClass (TextType tt) {
    addProperty(tt);
  }

  public TextType getRenditionClass(){
    return (TextType)getProperty(localPrefixSep+RENDITIONCLASS);
  }

  public String getRenditionClassValue(){
    TextType tt = getRenditionClass();
    return tt!=null?tt.getStringValue():null;
  }

  // --------------------------------------- RenditionParams ----------------------------

  @PropertyType(propertyType = "Text")
  public static final String RENDITIONPARAMS="RenditionParams";

  public void setRenditionParamsValue(String url)  {
    setRenditionParams (new TextType(metadata, localPrefix, RENDITIONPARAMS, url));
  }

  public void setRenditionParams (TextType tt) {
    addProperty(tt);
  }

  public TextType getRenditionParams(){
    return (TextType)getProperty(localPrefixSep+RENDITIONPARAMS);
  }

  public String getRenditionParamsValue(){
    TextType tt = getRenditionParams();
    return tt!=null?tt.getStringValue():null;
  }

  // --------------------------------------- VersionID ----------------------------

  @PropertyType(propertyType = "Text")
  public static final String VERSIONID="VersionID";

  public void setVersionIDValue(String url)  {
    setVersionID (new TextType(metadata, localPrefix, VERSIONID, url));
  }

  public void setVersionID (TextType tt) {
    addProperty(tt);
  }

  public TextType getVersionID(){
    return (TextType)getProperty(localPrefixSep+VERSIONID);
  }

  public String getVersionIDValue(){
    TextType tt = getVersionID();
    return tt!=null?tt.getStringValue():null;
  }

  // --------------------------------------- Versions ----------------------------

  @PropertyType(propertyType = "seq(Text)")
  public static final String VERSIONS="Versions";

  public void addToVersionsValue (String version)  {
    addSequenceValue(localPrefixSep+VERSIONS,version);
  }

  public ComplexProperty getVersions () {
    return (ComplexProperty)getProperty(localPrefixSep+VERSIONS);
  }

  public List<String> getVersionsValue(){
    return getSequenceValueList(localPrefixSep+VERSIONS);
  }

  // --------------------------------------- History ----------------------------

  @PropertyType(propertyType = "seq(Text)")
  public static final String HISTORY="History";

  public void addToHistoryValue (String history)  {
    addSequenceValue(localPrefixSep+HISTORY,history);
  }

  public ComplexProperty getHistory() {
    return (ComplexProperty)getProperty(localPrefixSep+HISTORY);
  }

  public List<String> getHistoryValue(){
    return getSequenceValueList(localPrefixSep+HISTORY);
  }

  // --------------------------------------- Ingredients ----------------------------

  @PropertyType(propertyType = "bag(Text)")
  public static final String INGREDIENTS="Ingredients";

  public void addToIngredientsValue (String history)  {
    addBagValue(localPrefixSep+INGREDIENTS,history);
  }

  public ComplexProperty getIngredients() {
    return (ComplexProperty)getProperty(localPrefixSep+INGREDIENTS);
  }

  public List<String> getIngredientsValue(){
    return getBagValueList(localPrefixSep+INGREDIENTS);
  }

}
