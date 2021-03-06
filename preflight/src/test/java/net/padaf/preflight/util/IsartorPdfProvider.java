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
package net.padaf.preflight.util;

import java.io.File;
import java.io.InputStream;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;

public class IsartorPdfProvider {
//  public static File path;
//  static {
//    String ip = System.getProperty("isartor.path", null);
//    if (ip != null) {
//      path = new File(ip);
//      if (!path.exists() || !path.isDirectory()) {
//        path = null;
//      }
//    }
//  }

  public static InputStream getIsartorDocument(String name) {
    return IsartorPdfProvider.class.getResourceAsStream(name);
//    
//    if (path == null) {
//      return null;
//    }
//
//    String[] ext = { "pdf" };
//    Iterator<?> iter = FileUtils.iterateFiles(path, ext, true);
//    while (iter.hasNext()) {
//      Object o = iter.next();
//      if (o instanceof File) {
//        File isartorFile = (File) o;
//        if (isartorFile.isFile() && name.equals(isartorFile.getName())) {
//          return isartorFile;
//        }
//      }
//    }
//    return null;
//  }
  }
}
