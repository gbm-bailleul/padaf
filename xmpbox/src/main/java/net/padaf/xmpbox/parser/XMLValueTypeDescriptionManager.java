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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.padaf.xmpbox.BuildPDFAExtensionSchemaDescriptionException;
import net.padaf.xmpbox.schema.XMPSchema;
import net.padaf.xmpbox.type.FieldDescription;
import net.padaf.xmpbox.type.ValueTypeDescription;

import org.apache.commons.io.IOUtils;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * Manage XML valuetype description file 
 * Allow user to create XML valuetype description file
 * or retrieve List of ValueTypeDescription from an XML File
 * 
 * @author Germain Costenobel
 *
 */
public class XMLValueTypeDescriptionManager {


  protected List<ValueTypeDescription> vTypes;
  protected XStream xstream;

  public XMLValueTypeDescriptionManager(){
    vTypes=new ArrayList<ValueTypeDescription>();
    xstream = new XStream(new DomDriver());
    xstream.alias("ValueTypesDescriptions", List.class);
    xstream.alias("ValueTypeDescription", ValueTypeDescription.class);
    xstream.alias("FieldDescription", FieldDescription.class);
  }

  public void addValueTypeDescription(String type, String nsURI, String prefix, String description){
    vTypes.add(new ValueTypeDescription(type, nsURI, prefix, description));
  }

  public void addValueTypeDescription(String type, String nsURI, String prefix, String description, List<FieldDescription> fields){
    vTypes.add(new ValueTypeDescription(type, nsURI, prefix, description, fields));
  }

  public void toXML(OutputStream os){
    xstream.toXML(vTypes, os);
  }

  public void loadListFromXML(Class<? extends XMPSchema> classSchem,String path) throws BuildPDFAExtensionSchemaDescriptionException{
    InputStream fis = classSchem.getResourceAsStream(path);
    if (fis==null) {
      throw new BuildPDFAExtensionSchemaDescriptionException("Failed to find specified XML valuetypes descriptions File");
    }
    loadListFromXML(fis);
  }

  public List<ValueTypeDescription> getValueTypesDescriptionList(){
    return vTypes;
  }


  public void loadListFromXML(InputStream is) throws BuildPDFAExtensionSchemaDescriptionException{
    try{
      Object o= xstream.fromXML(is);
      if(o instanceof List<?>){
        if(((List<?>) o).get(0)!=null){
          if(((List<?>) o).get(0) instanceof ValueTypeDescription){
            vTypes= (List<ValueTypeDescription>) o;
          }else{
            throw new BuildPDFAExtensionSchemaDescriptionException("Failed to get correct valuetypes descriptions from specified XML stream");
          }
        }else{
          throw new BuildPDFAExtensionSchemaDescriptionException("Failed to find a valuetype description into the specified XML stream");
        }
      }

    }
    catch(Exception e){
      e.printStackTrace();
      throw new BuildPDFAExtensionSchemaDescriptionException("Failed to get correct valuetypes descriptions from specified XML stream", e.getCause());
    } finally {
      IOUtils.closeQuietly(is);
    }
  }


  public static void main(String[] args) throws BuildPDFAExtensionSchemaDescriptionException{
    XMLValueTypeDescriptionManager vtMaker=new XMLValueTypeDescriptionManager();

    //add Descriptions
    for(int i=0; i<3; i++){
      vtMaker.addValueTypeDescription("testType"+i, "nsURI"+i, "prefix"+i, "description"+i);

    }
    List<FieldDescription> fieldSample=new ArrayList<FieldDescription>();
    for(int i=0; i<2; i++){
      fieldSample.add(new FieldDescription("fieldName"+i, "valueType"+i, "description"+i));
    }
    vtMaker.addValueTypeDescription("testTypeField", "http://test.withfield.com/vt/", "prefTest", " value type description", fieldSample);

    // Display XML conversion
    System.out.println("Display XML Result:");
    vtMaker.toXML(System.out);


    //Sample to show how to build object from XML file
    ByteArrayOutputStream bos=new ByteArrayOutputStream();
    vtMaker.toXML(bos);
    IOUtils.closeQuietly(bos);

    //emulate a new reading
    InputStream is=new ByteArrayInputStream(bos.toByteArray());
    vtMaker=new XMLValueTypeDescriptionManager();
    vtMaker.loadListFromXML(is);
    List<ValueTypeDescription> result= vtMaker.getValueTypesDescriptionList();
    System.out.println();
    System.out.println();
    System.out.println("Result of XML Loading :");
    for (ValueTypeDescription propertyDescription : result) {
      System.out.println(propertyDescription.getType()+" :"+propertyDescription.getDescription());
      if(propertyDescription.getFields()!=null){
        Iterator<FieldDescription> fit=propertyDescription.getFields().iterator();
        FieldDescription field;
        while(fit.hasNext()){
          field=fit.next();
          System.out.println("Field "+field.getName()+" :"+field.getValueType());
        }
      }
    }



  }

}
