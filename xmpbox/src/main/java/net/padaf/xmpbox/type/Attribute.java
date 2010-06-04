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


/**
 * Simple representation of an attribute
 * @author Germain Costenobel
 *
 */
public class Attribute {

	private String nsURI, prefix, localName, value;
		
	public Attribute(String nsURI, String prefix, String localName, String value) {
		this.nsURI=nsURI;
		this.prefix=prefix;
		this.localName=localName;
		this.value=value;
	}
	
	public String getPrefix(){
		if(prefix !=null){
			if(prefix.equals("")){
				return null;
			}
			return prefix;
		}
		return null;
	}

	public void setPrefix(String prefix){
		this.prefix=prefix;
	}
	
	public String getLocalName(){
		return localName;
	}
	
	public void setLocalName(String lname){
		localName=lname;
	}
	
	public String getNamespace() {
		return nsURI;
	}

	public void setNsURI(String nsURI) {
		this.nsURI = nsURI;
	}

	public String getQualifiedName() {
		if(prefix==null){
			return localName;
		}
		if(prefix.equals("")){
			return localName;
		}
		return prefix+":"+localName;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	
	
	
}
