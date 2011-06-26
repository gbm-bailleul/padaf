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

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.FileInputStream;

public class ValidateTrueType {

  public static void main(String[] args) throws Exception {
    try {
      // Font.createFont(Font.TRUETYPE_FONT, new
      // FileInputStream("resources/bad_true_type.ttf"));
      Font font = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(
          "resources/good_true_type.ttf"));
      System.out.println(font.getFamily());
      System.out.println(font.getFontName());
      System.out.println(font.getPSName());
      System.out.println(font.getNumGlyphs());
      System.out.println(font.getStyle() + "/" + Font.ITALIC + "/" + Font.BOLD
          + "/" + Font.PLAIN);
    } catch (FontFormatException e) {
      // throw exception if invalid true type
      e.printStackTrace();
    }
  }
}
