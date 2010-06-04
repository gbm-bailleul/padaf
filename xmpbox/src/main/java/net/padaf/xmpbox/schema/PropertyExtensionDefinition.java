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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)


@Target(ElementType.FIELD)

/**
 * Annotation to specify type schema associated to a property.
 * This annotation help to make automatically PDF/A Extension schema description
 * for a schema.
 * 
 * In default case, property description will be read 
 * from xml file specified in ExtensionSchemaAnnotation which define
 * all properties description. If this file is not specified, description will be read here.
 * 
 * Note: if file and this propertyDescription are not specified, a default 'not documented description"
 * is written
 */
public @interface PropertyExtensionDefinition {
	String propertyCategory();
	String propertyDescription() default "";
	
}
	
	

