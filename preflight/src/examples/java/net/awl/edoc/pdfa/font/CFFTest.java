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
package net.awl.edoc.pdfa.font;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.fontbox.cff.CFFFont;
import org.apache.fontbox.cff.CFFFontROS;
import org.apache.fontbox.cff.CFFParser;
import org.apache.fontbox.cff.Type1CharStringParser;
import org.apache.fontbox.cff.Type2CharStringParser;
import org.apache.fontbox.cff.CFFFont.Mapping;

public class CFFTest {

  /**
   * @param args
   */
  public static void main(String[] args) throws Exception {
    FileInputStream fis = new FileInputStream("/home/eric/UneCFFType0.cff0");
    // FileInputStream fis = new FileInputStream("/home/eric/UneCFFType2.cff2");
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    IOUtils.copyLarge(fis, bos);

    CFFParser cp = new CFFParser();
    List<CFFFont> lf = cp.parse(bos.toByteArray());

    CFFFontROS font = (CFFFontROS) lf.get(0);
    System.out
        .println("CharstringType : " + font.getProperty("CharstringType"));

    int CID = 85;
    int fdAIndex = font.getFdSelect().getFd(CID);
    Map<String, Object> fd = font.getFontDict().get(fdAIndex);
    Map<String, Object> pd = font.getPrivDict().get(fdAIndex);
    for (Mapping m : font.getMappings()) {
      if (m.getSID() == CID) {
        // List<Object> l1 = new
        // Type1CharStringParser().parse(font.getCharStringsDict().get(m.getName()));
        List<Object> l2 = new Type2CharStringParser().parse(m.getBytes());
        System.err.println("");
      }
    }
    System.out.println("");
  }
}
