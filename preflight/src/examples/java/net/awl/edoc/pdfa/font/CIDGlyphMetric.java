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

import java.io.FileInputStream;

public class CIDGlyphMetric {

  /**
   * Extract Widths values from a TrueType font program
   */
  public static void main(String[] args) throws Exception {
    FileInputStream fis = new FileInputStream("/home/eric/CIDFontType2.font");
    fis.read();
    fis.read();
    fis.read();
    fis.read();
    fis.read();
    fis.read();
    fis.read();
    fis.read();

    int a = fis.read();
    int b = fis.read();
    int c = fis.read();
    int d = fis.read();

    System.out.println("MAJOR : " + (a & 0xFF));
    System.out.println("MINOR : " + (b & 0xFF));
    System.out.println("HSize : " + (c & 0xFF));
    System.out.println("Offset : " + (d & 0xFF));
  }
}
