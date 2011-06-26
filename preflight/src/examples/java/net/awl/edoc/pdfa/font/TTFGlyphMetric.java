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

package net.awl.edoc.pdfa.font;

import java.io.FileInputStream;

import org.apache.fontbox.ttf.TTFParser;
import org.apache.fontbox.ttf.TrueTypeFont;

public class TTFGlyphMetric {

  /**
   * Extract Widths values from a TrueType font program
   */
  public static void main(String[] args) throws Exception {
    TrueTypeFont ttf = new TTFParser().parseTTF(new FileInputStream(
        "/media/WEBS/PDFA_TEST/6.3 Fonts/6.3.5 Font subsets/hello-font.ttf"));
    System.out.println("LowestRecPPEM : " + ttf.getHeader().getLowestRecPPEM());
    int upe = ttf.getHeader().getUnitsPerEm();
    System.out.println("UnitsPPEM : " + upe);
    int[] gds = ttf.getHorizontalMetrics().getAdvanceWidth();

    for (int w : gds) {
      System.out.println((int) (w * 1000 / upe));
    }
  }
}
