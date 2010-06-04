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
package net.padaf.xmpbox.type;

import java.util.ArrayList;
import java.util.List;

import net.padaf.xmpbox.XMPMetadata;
import net.padaf.xmpbox.schema.PDFAFieldDescription;

/**
 * Represents one Property Description described in xml file
 * @author Germain Costenobel
 *
 */
public class ValueTypeDescription {
	protected String type;
	protected String namespaceURI;
	protected String prefix;
	protected String description;
	protected List<FieldDescription> fields;
	
	
	public ValueTypeDescription(String type, String namespaceURI, String prefix, String description, List<FieldDescription> fields){
		this.type=type;
		this.namespaceURI=namespaceURI;
		this.prefix=prefix;
		this.description=description;
		this.fields=fields;
		
	}
	
	public ValueTypeDescription(String type, String namespaceURI, String prefix, String description){
		this.type=type;
		this.namespaceURI=namespaceURI;
		this.prefix=prefix;
		this.description=description;
		this.fields=null;
		
	}

	public String getType() {
		return type;
	}

	public String getNamespaceURI() {
		return namespaceURI;
	}

	public String getPrefix() {
		return prefix;
	}

	public String getDescription() {
		return description;
	}

	public List<FieldDescription> getFields(){
		return fields;
	}
	
	public List<PDFAFieldDescription> getPDFAFieldsAssocied(XMPMetadata metadata) {
		if(fields!=null){
			List<PDFAFieldDescription> pdfaFields=new ArrayList<PDFAFieldDescription>();
			for (FieldDescription field : fields) {
				pdfaFields.add(field.createPDFAFieldDescription(metadata));
			}
			return pdfaFields;
		}
		
		return null;
	}
	
	
	
}
