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
import net.padaf.xmpbox.type.TextType;


/**
 * Representation of Adobe PDF Schema
 * @author Germain Costenobel
 *
 */
public class AdobePDFSchema extends XMPSchema{

	
	
	public static final String PREFERRED_PDF_PREFIX="pdf";
	
	public static final String PDFURI="http://ns.adobe.com/pdf/1.3/";
	
	@PropertyType(propertyType = "Text")
	public static final String KEYWORDS="Keywords";
	
	@PropertyType(propertyType = "Text")
	public static final String PDF_VERSION="PDFVersion";
	
	@PropertyType(propertyType = "Text")
	public static final String PRODUCER="Producer";
		
	/**
	 * Constructor of an Adobe PDF schema
	 */
	public AdobePDFSchema(XMPMetadata metadata) {
		super(metadata, PREFERRED_PDF_PREFIX, PDFURI);
	}
	
	public AdobePDFSchema(XMPMetadata metadata, String ownPrefix) {
		super(metadata, ownPrefix, PDFURI);
	}
		
	/**
	 * Set the PDF keywords	
	 * @param value
	 */
	public void setKeywordsValue(String value){
		TextType keywords;
			keywords = new TextType(metadata, localPrefix, KEYWORDS, value);
			addProperty(keywords);
	}

    /**
     * Set the PDF keywords 
     * @param value
     */
    public void setKeywords(TextType keywords){
         addProperty(keywords);
    }
    
    
	
	/**
	 * Set the PDFVersion 	
	 * @param value
	 */
	public void setPDFVersionValue(String value){
		TextType version;
			version = new TextType(metadata, localPrefix, PDF_VERSION, value);
			addProperty(version);
		
	}

    /**
     * Set the PDFVersion   
     * @param value
     */
    public void setPDFVersion(TextType version){
           addProperty(version);
    }

	
	/**
	 * Set the PDFProducer	
	 * @param value
	 */
	public void setProducerValue(String value){
		TextType producer;
			producer = new TextType(metadata, localPrefix, PRODUCER, value);
			addProperty(producer);
	}
	
    /**
     * Set the PDFProducer  
     * @param producer
     */
    public void setProducer(TextType producer){
           addProperty(producer);
    }
	
	
	/**
	 * Give the PDF Keywords property
	 * @return
	 */
	public TextType getKeywords(){
		AbstractField tmp=getProperty(localPrefixSep+KEYWORDS);
		if(tmp!=null){
			if(tmp instanceof TextType){
				return (TextType) tmp;
			}
		}
		return null;
	}
	
	/**
	 * Give the PDF Keywords property value (string)
	 * @return
	 */
	public String getKeywordsValue(){
		AbstractField tmp=getProperty(localPrefixSep+KEYWORDS);
		if(tmp!=null){
			if(tmp instanceof TextType){
				return ((TextType) tmp).getStringValue();
			}
		}
		return null;
	}
	
	/**
	 * Give the PDFVersion property
	 * @return
	 */
	public TextType getPDFVersion(){
		AbstractField tmp=getProperty(localPrefixSep+PDF_VERSION);
		if(tmp!=null){
			if(tmp instanceof TextType){
				return (TextType) tmp;
			}
		}
		return null;
	}
	
	/**
	 * Give the PDFVersion property value (string)
	 * @return
	 */
	public String getPDFVersionValue(){
		AbstractField tmp=getProperty(localPrefixSep+PDF_VERSION);
		if(tmp!=null){
			if(tmp instanceof TextType){
				return ((TextType) tmp).getStringValue();
			}
		}
		return null;
	}

	/**
	 * Give the producer property
	 * @return
	 */
	public TextType getProducer(){
		AbstractField tmp=getProperty(localPrefixSep+PRODUCER);
		if(tmp!=null){
			if(tmp instanceof TextType){
				return (TextType) tmp;
			}
		}
		return null;
	}
	
	/**
	 * Give the producer property value (string)
	 * @return
	 */
	public String getProducerValue(){
		AbstractField tmp=getProperty(localPrefixSep+PRODUCER);
		if(tmp!=null){
			if(tmp instanceof TextType){
				return ((TextType) tmp).getStringValue();
			}
		}
		return null;
	}
	
	
}
