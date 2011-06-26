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

import java.util.List;

import org.apache.fontbox.ttf.TTFParser;
import org.apache.fontbox.ttf.TrueTypeFont;

public class FontBoxUsage {

  /**
   * @param args
   */
  public static void main(String[] args) throws Exception {
    TTFParser parser = new TTFParser();

    // TrueTypeFont ttf =
    // parser.parseTTF("/usr/share/fonts/liberation/LiberationMono-Bold.ttf");
    TrueTypeFont ttf = parser.parseTTF("resources/bad_true_type.ttf");
    // TrueTypeFont ttf = parser.parseTTF("resources/good_true_type.ttf");

    List<?> l = ttf.getNaming().getNameRecords();

    for (Object object : l) {
      System.out.println(object);
    }
    System.err.println(l.size());

  }

}
