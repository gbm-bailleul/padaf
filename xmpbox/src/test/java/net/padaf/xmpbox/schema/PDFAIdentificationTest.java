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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.padaf.xmpbox.XMPMetadata;
import net.padaf.xmpbox.schema.PDFAIdentificationSchema;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class PDFAIdentificationTest extends AbstractXMPSchemaTest {

	
	  @Before
	  public void initTempMetaData() throws Exception {
	    metadata=new XMPMetadata();
	    schema = metadata.createAndAddPFAIdentificationSchema();
	    schemaClass = PDFAIdentificationSchema.class;
	  }

	  @Parameters
	  public static Collection<Object[]> initializeParameters() throws Exception {
	    List<Object[]> data = new ArrayList<Object[]>();
	    data.add(wrapProperty("part","Integer",1));
        data.add(wrapProperty("amd","Text","2005"));
        data.add(wrapProperty("conformance","Text","B"));
 	    return data;
	  }

	  
	  public PDFAIdentificationTest(String property,String type,Object value) {
	    super(property,type, value);
	  }


}