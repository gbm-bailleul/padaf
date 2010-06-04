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
package net.padaf.xmpbox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.padaf.xmpbox.parser.XmpSchemaException;
import net.padaf.xmpbox.schema.AdobePDFSchema;
import net.padaf.xmpbox.schema.DublinCoreSchema;
import net.padaf.xmpbox.schema.PDFAExtensionSchema;
import net.padaf.xmpbox.schema.PDFAIdentificationSchema;
import net.padaf.xmpbox.schema.XMPBasicSchema;
import net.padaf.xmpbox.schema.XMPMediaManagementSchema;
import net.padaf.xmpbox.schema.XMPRightsManagementSchema;
import net.padaf.xmpbox.schema.XMPSchema;
import net.padaf.xmpbox.type.Elementable;

import org.w3c.dom.Document;
import org.w3c.dom.Element;


/**
 * Object representation of XMPMetaData
 * Be CAREFUL: typically, metadata should contain only one schema for each type (each NSURI).
 * Retrieval of common schemas (like DublinCore) is based on this fact and take the first schema of this type encountered.
 * However, XmpBox allow you to place schemas of same type with different prefix.
 * If you do that, you must retrieve all schemas by yourself with getAllSchemas or with getSchema which use prefix parameter.
 * 
 * @author Germain Costenobel
 * 
 */
public class XMPMetadata {

	protected String xpacketId = "W5M0MpCehiHzreSzNTczkc9d";
	protected String xpacketBegin = "\uFEFF";
	
	//DEPRECATED (SHOULD STAY NULL)
	protected String xpacketBytes = null;
	protected String xpacketEncoding = null;
	
	protected String xpacketEndData= "end=\"w\"";
	
	
	
	protected SchemasContainer schemas;

	private Document xmpDocument;

	/**
	 * Contructor of an empty default XMPMetaData
	 * @throws IOException 
	 * 
	 */ 
	public XMPMetadata() throws IOException {
		xmpDocument = net.padaf.xmpbox.parser.XMLUtil.newDocument();
		schemas = new SchemasContainer();
		
	}
	
	/**
     * Default constructor, creates blank XMP doc.
	 * @throws CreateXMPMetadataException 
     */
    public XMPMetadata(String xpacketBegin, String xpacketId, String xpacketBytes,String xpacketEncoding) throws CreateXMPMetadataException
    {
    	this.xpacketBegin = xpacketBegin;
    	this.xpacketId = xpacketId;
    	this.xpacketBytes = xpacketBytes;
    	this.xpacketEncoding = xpacketEncoding;
    	try{
    		xmpDocument = net.padaf.xmpbox.parser.XMLUtil.newDocument();
    		schemas = new SchemasContainer();
		} catch (IOException e) {
			throw new CreateXMPMetadataException("Failed to create Dom Document");
		}
    }
    
    public String getXpacketBytes() {
		return xpacketBytes;
	}
    
    public String getXpacketEncoding() {
		return xpacketEncoding;
	}
    
    
    public String getXpacketBegin() {
		return xpacketBegin;
	}

	public String getXpacketId() {
		return xpacketId;
	}

	public void addSchema(XMPSchema schema) {
		schemas.addSchema(schema);
	}

	public List<XMPSchema> getAllSchemas() {
		ArrayList<XMPSchema> schem = new ArrayList<XMPSchema>();
		Iterator<XMPSchema> it = schemas.getAllSchemas();
		while (it.hasNext()) {
			schem.add((XMPSchema) it.next());
		}
		return schem;
	}

	
	/**
	 * Set special XPACKET END PI 
	 * @param target
	 * @param data
	 */
	public void setEndXPacket(String data){
		xpacketEndData=data;
	}
	
	/**
	 * get END XPACKET PI
	 * @return
	 */
	public String getEndXPacket(){
		return xpacketEndData;
	}
	

	/**
	 * Get element associated to all schemas contained in this Metadata
	 * @return
	 */
	public Element getContainerElement(){
		return schemas.getElement();
	}
	
	/**
	 * Give the DOM Document to build metadata content
	 */
	public Document getFuturOwner() {
		return xmpDocument;
	}
	
		
	/**
	 * Return the schema corresponding to this nsURI
	 * BE CAREFUL: typically, Metadata should contains one schema for each type
	 * this method return the first schema encountered corresponding to this NSURI
	 * Return null if unknown
	 * @param nsURI
	 * @return
	 */
	public XMPSchema getSchema(String nsURI){
		Iterator<XMPSchema> it=getAllSchemas().iterator();
		XMPSchema tmp;
		while(it.hasNext()){
			tmp=it.next();
			if(tmp.getNamespaceValue().equals(nsURI)){
				return tmp;
			}
		}
		return null;
	}
	
	/**
	 * Return the schema corresponding to this nsURI and a prefix 
	 * This method is here to treat metadata which embed more than one time the same schema
	 * It permit to retrieve a specific schema with its prefix 
	 * @param prefix
	 * @param nsURI
	 * @return
	 */
	public XMPSchema getSchema(String prefix, String nsURI){
		Iterator<XMPSchema> it=getAllSchemas().iterator();
		XMPSchema tmp;
		while(it.hasNext()){
			tmp=it.next();
			if(tmp.getNamespaceValue().equals(nsURI) && tmp.getPrefix().equals(prefix)){
				return tmp;
			}
		}
		return null;
	}
	
	
	/**
	 * Set a unspecialized schema
	 * @param nsPrefix
	 * @param nsURI
	 * @return
	 */
	public XMPSchema createAndAddDefaultSchema(String nsPrefix, String nsURI){
		XMPSchema schem=new XMPSchema(this, nsPrefix, nsURI);
		schem.setAboutAsSimple("");
		addSchema(schem);
		return schem;
	}
	
	/**
	 * Create and add a PDFA Extension schema to this metadata
	 * This method return the created schema to enter information
	 * This PDFAExtension is created with all default namespaces used in PDFAExtensionSchema
	 */
	public PDFAExtensionSchema createAndAddPDFAExtensionSchemaWithDefaultNS(){
		PDFAExtensionSchema pdfAExt=new PDFAExtensionSchema(this);
		pdfAExt.setAboutAsSimple("");
		addSchema(pdfAExt);
		return pdfAExt;
	}

	/**
	 * Create and add a XMPRights schema to this metadata
	 * This method return the created schema to enter information
	 * @return
	 */
	public XMPRightsManagementSchema createAndAddXMPRightsManagementSchema(){
		XMPRightsManagementSchema  rights =new XMPRightsManagementSchema(this);
		rights.setAboutAsSimple("");
		addSchema(rights);
		return rights;
	}
	
	/**
	 * Create and add a PDFA Extension schema to this metadata
	 * This method return the created schema to enter information
	 * This PDFAExtension is created with specified list of namespaces
	 * @throws XmpSchemaException 
	 * @throws XmpSchemaException 
	 */
	public PDFAExtensionSchema createAndAddPDFAExtensionSchemaWithNS(HashMap<String, String> namespaces) throws XmpSchemaException{
		PDFAExtensionSchema pdfAExt=new PDFAExtensionSchema(this, namespaces);
		pdfAExt.setAboutAsSimple("");
		addSchema(pdfAExt);
		return pdfAExt;
	}
	
	/**
	 * Get the PDFA Extension schema
	 * This method return null if not found
	 */
	public PDFAExtensionSchema getPDFExtensionSchema(){
		return (PDFAExtensionSchema)getSchema(PDFAExtensionSchema.PDFAEXTENSIONURI);
	}
	
	/**
	 * Create and add a PDFA Identification schema to this metadata
	 * This method return the created schema to enter information
	 */
	public PDFAIdentificationSchema createAndAddPFAIdentificationSchema(){
		PDFAIdentificationSchema pdfAId=new PDFAIdentificationSchema(this);
		pdfAId.setAboutAsSimple("");
		addSchema(pdfAId);
		return pdfAId;
	}

	/**
	 * Get the PDFA Identification schema
	 * This method return null if not found
	 */
	public PDFAIdentificationSchema getPDFIdentificationSchema(){
		return (PDFAIdentificationSchema)getSchema(PDFAIdentificationSchema.IDURI);
	}
	
	/**
	 * Create and add a Dublin Core schema to this metadata
	 * This method return the created schema to enter information
	 */
	public DublinCoreSchema createAndAddDublinCoreSchema(){
		DublinCoreSchema dc=new DublinCoreSchema(this);
		dc.setAboutAsSimple("");
		addSchema(dc);
		return dc;
	}

	/**
	 * Get the Dublin Core schema 
	 * This method return null if not found
	 */
	public DublinCoreSchema getDublinCoreSchema(){
		return (DublinCoreSchema)getSchema(DublinCoreSchema.DCURI);
	}

	/**
	 * Get the XMPRights schema 
	 * This method return null if not found
	 */
	public XMPRightsManagementSchema getXMPRightsManagementSchema(){
		return (XMPRightsManagementSchema)getSchema(XMPRightsManagementSchema.XMPRIGHTSURI);
	}
	
	
	/**
	 * Create and add a XMP Basic schema to this metadata
	 * This method return the created schema to enter information
	 */
	public XMPBasicSchema createAndAddXMPBasicSchema(){
		XMPBasicSchema xmpB=new XMPBasicSchema(this);
		xmpB.setAboutAsSimple("");
		addSchema(xmpB);
		return xmpB;
	}
	
	/**
	 * Get the XMP Basic schema 
	 * This method return null if not found
	 */
	public XMPBasicSchema getXMPBasicSchema(){
		return (XMPBasicSchema)getSchema(XMPBasicSchema.XMPBASICURI);
	}
	
	/**
	 * Create and add a XMP Media Management schema to this metadata
	 * This method return the created schema to enter information
	 */
	public XMPMediaManagementSchema createAndAddXMPMediaManagementSchema(){
		XMPMediaManagementSchema xmpMM=new XMPMediaManagementSchema(this);
		xmpMM.setAboutAsSimple("");
		addSchema(xmpMM);
		return xmpMM;
	}
	
	/**
	 * Get the XMP Media Management schema 
	 * This method return null if not found
	 */
	public XMPMediaManagementSchema getXMPMediaManagementSchema(){
		return (XMPMediaManagementSchema)getSchema(XMPMediaManagementSchema.XMPMMURI);
	}
	
	/**
	 * Create and add an Adobe PDF schema to this metadata
	 * This method return the created schema to enter information
	 */
	public AdobePDFSchema createAndAddAdobePDFSchema(){
		AdobePDFSchema pdf=new AdobePDFSchema(this);
		pdf.setAboutAsSimple("");
		addSchema(pdf);
		return pdf;
	}
	
	/**
	 * Get the Adobe PDF schema 
	 * This method return null if not found
	 */
	public AdobePDFSchema getAdobePDFSchema(){
		return (AdobePDFSchema)getSchema(AdobePDFSchema.PDFURI);
	}
	
	
	
	public class SchemasContainer implements Elementable {

		protected Element element;
		protected List<XMPSchema> schemas;
			
			/**
			 * 
			 * Schemas Container  constructor 
			 */
			public SchemasContainer() {
				element=xmpDocument.createElement("rdf:RDF");
				element.setAttributeNS(XMPSchema.NS_NAMESPACE, "xmlns:rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#");
				schemas=new ArrayList<XMPSchema>();
			}

			
			
			/**
			 * Add a schema to the current structure
			 * @param obj the property to add
			 */
			public void addSchema(XMPSchema obj){
				if(containsSchema(obj)){
					removeSchema(obj);
				}
				schemas.add(obj);
				element.appendChild(obj.getElement());
			}

			
			/**
			 * Return all schemas
			 * @return
			 */
			public Iterator<XMPSchema> getAllSchemas(){
				return schemas.iterator();
			}
			
			/**
			 * Check if two schemas are similar
			 * @param prop1
			 * @param prop2
			 * @return
			 */
			public boolean isSameSchema(XMPSchema prop1, XMPSchema prop2){
				if(prop1.getClass().equals(prop2.getClass()) ){
					if(prop1.getPrefix().equals(prop2.getPrefix())) return true;
				}		
					
				return false;
			}
			
			/**
			 * Check if a specified schema is embedded
			 * @param schema
			 * @return
			 */
			public boolean containsSchema(XMPSchema schema){
				Iterator<XMPSchema> it=getAllSchemas();
				XMPSchema tmp;
				while(it.hasNext()){
					tmp=it.next();
					if(isSameSchema(tmp, schema) ){
						return true;
					}
				}
				return false;
			}
			
			
			
			/**
			 * Remove a schema
			 * @param schema
			 */
			public void removeSchema(XMPSchema schema){
				if(containsSchema(schema)){
					schemas.remove(schema);
					element.removeChild(schema.getElement());
				}
			}



			
			public Element getElement() {
				return element;
			}

		}
	
	
}
