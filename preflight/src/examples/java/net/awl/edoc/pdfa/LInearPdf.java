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

package net.awl.edoc.pdfa;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.pdfbox.cos.COSDictionary;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessBuffer;
import org.apache.pdfbox.pdfparser.BaseParser;
import org.apache.pdfbox.pdfparser.PDFStreamParser;

public class LInearPdf {
  public static void main(String[] args) throws Exception {
    InputStream is = new FileInputStream("/home/eric/dictionnaire.txt");
    // PDFStreamParser parser = new PDFStreamParser(is,new
    // RandomAccessBuffer());
    // COSDocument doc = new COSDocument();
    // parser.setDocument(doc);
    // parser.parse();
    //
    // System.out.println(((COSDictionary)doc.getObjects().get(0)).getInt("Size"));

    CustomParser parser = new CustomParser(is);
    COSDocument doc = new COSDocument();
    parser.setDocument(doc);
    COSDictionary dic = parser.getCOSDictionary();
    System.out.println(dic.toString());
  }

  public static class CustomParser extends BaseParser {

    public CustomParser(byte[] input) throws IOException {
      super(input);
    }

    public CustomParser(InputStream input) throws IOException {
      super(input);
    }

    public COSDictionary getCOSDictionary() throws IOException {
      return super.parseCOSDictionary();
    }
  }
}
