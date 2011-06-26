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

public class TrueTypeInfoExample {

  /**
   * @param args
   */
  public static void main(String[] args) throws Exception {
    // TTFReader reader = new TTFReader();
    // TTFFile ttf = reader.loadTTF("resources/good_true_type.ttf", "myfont");
    //		
    // System.err.println(ttf.getPostScriptName());

    // FontFileReader ffr = new
    // FontFileReader("/usr/share/fonts/liberation/LiberationMono-Bold.ttf");
    // FontFileReader ffr = new FontFileReader("resources/bad_true_type.ttf");
    // FontFileReader ffr = new FontFileReader("resources/good_true_type.ttf");
    // TTFFile ttf = new TTFFile();
    // ttf.readFont(ffr);
    // System.out.println(ttf.getFullName());
    // System.out.println(ttf.getPostScriptName());
    // System.out.println(ttf.isEmbeddable());

    TrueTypeFont ttf = new TTFParser().parseTTF(new FileInputStream(
        "/usr/share/fonts/truetype/kochi/kochi-mincho.ttf"));
    ttf.getCMAP();
  }

}
