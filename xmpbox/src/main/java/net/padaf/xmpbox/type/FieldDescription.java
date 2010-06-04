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

import net.padaf.xmpbox.XMPMetadata;
import net.padaf.xmpbox.schema.PDFAFieldDescription;

/**
 * Represents one Property Description described in xml file
 * @author Germain Costenobel
 *
 */
public class FieldDescription {
	protected String name;
	protected String valueType;
	protected String description;
	
	public FieldDescription(String name, String valueType, String description){
		this.name=name;
		this.valueType=valueType;
		this.description=description;
	}
	
	public String getValueType(){
		return valueType;
	}
	
	public String getDescription(){
		return description;
	}
	
	public String getName(){
		return name;
	}
	
	public PDFAFieldDescription createPDFAFieldDescription(XMPMetadata metadata){
		PDFAFieldDescription fieldDesc=new PDFAFieldDescription(metadata);
		fieldDesc.setNameValue(name);
		fieldDesc.setValueTypeValue(valueType);
		fieldDesc.setDescriptionValue(description);
		return fieldDesc;
	}
}
