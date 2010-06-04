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

import net.padaf.xmpbox.SaveMetadataHelper;
import net.padaf.xmpbox.TransformException;
import net.padaf.xmpbox.XMPMetadata;
import net.padaf.xmpbox.schema.AdobePDFSchema;
import net.padaf.xmpbox.schema.DublinCoreSchema;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.junit.Assert;
import org.junit.Test;

public class SaveMetadataHelperTest {

	
	
	
	
	@Test
	public void testSchemaParsing() throws IOException, TransformException{
		DublinCoreSchema dc= new DublinCoreSchema(new XMPMetadata());
		dc.setCoverageValue("coverage");
		dc.addToContributorValue("contributor1");
		dc.addToContributorValue("contributor2");
		dc.addToDescriptionValue("x-default", "Description");
		ByteArrayOutputStream bos=new ByteArrayOutputStream();
		SaveMetadataHelper.serialize(dc, bos);
		byte[] tmp=SaveMetadataHelper.serialize(dc);
		Assert.assertArrayEquals(bos.toByteArray(), tmp);
	}
	
	@Test
	public void testMetadataParsing() throws IOException, TransformException{
		XMPMetadata meta=new XMPMetadata();
		DublinCoreSchema dc=meta.createAndAddDublinCoreSchema();
		dc.setCoverageValue("coverage");
		dc.addToContributorValue("contributor1");
		dc.addToContributorValue("contributor2");
		dc.addToDescriptionValue("x-default", "Description");
		
		AdobePDFSchema pdf=meta.createAndAddAdobePDFSchema();
		pdf.setProducerValue("Producer");
		pdf.setPDFVersionValue("1.4");
		
		ByteArrayOutputStream bos=new ByteArrayOutputStream();
		byte[] tmp=SaveMetadataHelper.serialize(meta);
		SaveMetadataHelper.serialize(meta, bos);
		
		
		Assert.assertArrayEquals(bos.toByteArray(), tmp);
		
		ByteArrayOutputStream bosWithoutPI=new ByteArrayOutputStream();
		SaveMetadataHelper.serialize(meta, false, bosWithoutPI);
		byte[] tmpWithoutPI=SaveMetadataHelper.serialize(meta, false );
		Assert.assertArrayEquals(bosWithoutPI.toByteArray(), tmpWithoutPI);
	}
	
	
	
	
}