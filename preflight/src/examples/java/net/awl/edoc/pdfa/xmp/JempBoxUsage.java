/*****************************************************************************
 * 
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * 
 ****************************************************************************/

package net.awl.edoc.pdfa.xmp;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.jempbox.xmp.pdfa.XMPMetadataPDFA;
import org.w3c.dom.Document;

public class JempBoxUsage {

  /**
   * @param args
   */
  public static void main(String[] args) throws Exception {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = factory.newDocumentBuilder();
    Document document = builder.parse(new File("resources/valid-xmp.xmp"));

    XMPMetadataPDFA xmp = new XMPMetadataPDFA(document);
    // System.out.println(xmp.getSchemas().toString());
    System.out.println(xmp.getEncoding());
    System.out.println(xmp.getPDFAIdSchema().getConformance());
    System.out.println(xmp.getPDFAIdSchema().getAmd());
    System.out.println(xmp.getPDFAIdSchema().getPart());
    System.out.println(xmp.getBasicSchema().getCreatorTool());
    System.out.println(xmp.getBasicSchema().getCreateDate());
    System.out.println(xmp.getPDFSchema().getProducer());
    System.out.println();

    System.out.println(xmp.getPDFAIdSchema().getTextProperty(
        "pdfaid:conformance"));
    System.out.println(xmp.getPDFAIdSchema().getTextProperty("pdfaid:part"));
    System.out.println(xmp.getPDFAIdSchema().getIntegerProperty("pdfaid:part"));

  }

}
