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

/**
 * This exception is thrown when a property is found in xmp block and is not existing
 * in predefined schema.
 * 
 * @author gbailleul
 *
 */
public class XmpPropertyFormatException extends XmpParsingException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public XmpPropertyFormatException(String message, Throwable cause) {
		super(message, cause);
	}

	public XmpPropertyFormatException(String message) {
		super(message);
	}

}
