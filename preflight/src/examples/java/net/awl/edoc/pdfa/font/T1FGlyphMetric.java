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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.util.List;

import org.apache.fontbox.cff.Type1CharStringParser;
import org.apache.fontbox.cff.Type1FontUtil;

public class T1FGlyphMetric {
  public static void main(String[] args) throws Exception {
    String filePath = "/media/WEBS/fonts/subsetType1.font";
    int length1 = 926;
    int length2 = 12270;
    int length3 = 532;

    FileInputStream fis = new FileInputStream(filePath);
    // ---- skip the clear-text
    long skipped = fis.skip(length1);
    while (length1 - skipped > 0) {
      skipped += fis.skip(length1 - skipped);
    }

    // ---- extract eexec part which contains glyph data
    int BUFFER_SIZE = 1024;
    byte[] buffer = new byte[BUFFER_SIZE];
    ByteArrayOutputStream eexecPart = new ByteArrayOutputStream();
    int lr = 0;
    int total = 0;
    do {
      lr = fis.read(buffer, 0, BUFFER_SIZE);
      if (lr == BUFFER_SIZE && (total + BUFFER_SIZE < length2)) {
        eexecPart.write(buffer, 0, BUFFER_SIZE);
        total += BUFFER_SIZE;
      } else if (lr > 0 && (total + lr < length2)) {
        eexecPart.write(buffer, 0, lr);
        total += lr;
      } else if (lr > 0 && (total + lr >= length2)) {
        eexecPart.write(buffer, 0, length2 - total);
        total += (length2 - total);
      }

    } while (length2 > total);

    eexecPart.close();
    fis.close();

    // --- decode eexec :
    byte[] eexecDecoded = Type1FontUtil.eexecDecrypt(eexecPart.toByteArray());
    String font = new String(eexecDecoded, "US-ASCII");
    System.out.println(font + "\n\n\n");

    byte[] tmp = new byte[7];
    ByteArrayInputStream bis = new ByteArrayInputStream(eexecDecoded);
    do {
      int cr = bis.read();
      if (cr == '/' && bis.read() == 'o' && bis.read() == 'n'
          && bis.read() == 'e') {
        bis.read(tmp, 0, 7);
        break;
      }
    } while (bis.available() > 0);

    byte[] charF = new byte[45];
    for (int i = 0; i < 45; i++) {
      charF[i] = (byte) bis.read();
    }
    byte[] dcs = Type1FontUtil.charstringDecrypt(charF, 0); // --- 0 is the
                                                            // value of lenIV,
                                                            // or 4 as default

    System.out.println("##### " + dcs.length);
    Type1CharStringParser t1p = new Type1CharStringParser();
    List<Object> lSequence = t1p.parse(dcs);
    for (Object object : lSequence) {
      System.out.println(object.toString());
    }

  }
}
