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
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.padaf.xmpbox.XMPMetadata;
import net.padaf.xmpbox.schema.XMPRightsManagementSchema;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class XmpRightsSchemaTest extends AbstractXMPSchemaTest {

	public XmpRightsSchemaTest(String property, String type, Object value) {
		super(property, type, value);
	}

	@Before
	public void initTempMetaData() throws Exception {
		metadata=new XMPMetadata();
		schema = metadata.createAndAddXMPRightsManagementSchema();
		schemaClass = XMPRightsManagementSchema.class;
	}

	  @Parameters
	  public static Collection<Object[]> initializeParameters() throws Exception {
	    List<Object[]> data = new ArrayList<Object[]>();
	    data.add(wrapProperty("Certificate", "URL", "http://une.url.vers.un.certificat/moncert.cer"));
	    data.add(wrapProperty("Marked", "Boolean", true));
	    data.add(wrapProperty("Owner", "bag ProperName", new String[]{"OwnerName"}));

        Map<String, String> desc = new HashMap<String, String>(2);
        desc.put("fr", "Termes d'utilisation");
        desc.put("en", "Usage Terms");
        data.add(wrapProperty("UsageTerms","Lang Alt", desc));
	    data.add(wrapProperty("WebStatement", "URL", "http://une.url.vers.une.page.fr/"));
	    return data;
	  }
	
//	@Test
//	public void testXmpRights() throws Exception{
//		XMPRightsManagementSchema schem = metadata.createAndAddXMPRightsManagementSchema();
//		/*prefix.preferred=xmpRights
//		property.Certificate=URL
//		property.Marked=Boolean
//		property.Owner=bag(ProperName)
//		property.UsageTerms=langAlt
//		property.WebStatement=URL*/
//
//		String Certificate = "http://une.url.vers.un.certificat/moncert.cer";
//		boolean marked = true;
//		String owner = "OwnerName";
//		String usage1 = "Usage Terms";
//		String usage2 = "Termes d'utilisation";
//		String webstmt = "http://une.url.vers.une.page.fr/";
//		String lang1 = "x-default";
//		String lang2 = "fr-fr";
//
//		schem.setCertificateValue(Certificate);
//		schem.setWebStatementValue(webstmt);
//		schem.setMarkedValue(marked);
//		schem.setOwnerValue(owner);
//		schem.addUsageTermsValue(lang1, usage1);
//		schem.addUsageTermsValue(lang2, usage2);
//
//		//check retrieve this schema in metadata
//		Assert.assertEquals(schem, metadata.getXMPRightsManagementSchema());
//		
//		// check info embedded
//		Assert.assertEquals(XMPRightsManagementSchema.PREFERRED_XMPRIGHTS_PREFIX+":Owner", schem.getOwner().getQualifiedName());
//		Assert.assertEquals(owner, schem.getOwnerValue());
//
//		Assert.assertEquals(XMPRightsManagementSchema.PREFERRED_XMPRIGHTS_PREFIX+":Certificate", schem.getCertificate().getQualifiedName());
//		Assert.assertEquals(Certificate, schem.getCertificateValue());
//
//		Assert.assertEquals(XMPRightsManagementSchema.PREFERRED_XMPRIGHTS_PREFIX+":WebStatement", schem.getWebStatement().getQualifiedName());
//		Assert.assertEquals(webstmt, schem.getWebStatementValue());
//		
//		Assert.assertEquals(XMPRightsManagementSchema.PREFERRED_XMPRIGHTS_PREFIX+":Marked", schem.getMarked().getQualifiedName());
//		Assert.assertTrue(schem.getMarkedValue());
//
//		Assert.assertEquals(XMPRightsManagementSchema.PREFERRED_XMPRIGHTS_PREFIX+":UsageTerms", schem.getUsageTerms().getQualifiedName());
//		Assert.assertEquals(usage1, schem.getUsageTermsLangValue(lang1));
//		Assert.assertEquals(usage2, schem.getUsageTermsLangValue(lang2));
//		Assert.assertEquals(2, schem.getUsageTermsLanguages().size());
//	}	
}
