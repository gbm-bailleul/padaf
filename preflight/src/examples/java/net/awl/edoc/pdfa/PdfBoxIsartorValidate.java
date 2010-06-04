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
package net.awl.edoc.pdfa;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import net.padaf.preflight.javacc.PDFParser;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdmodel.PDDocument;

public class PdfBoxIsartorValidate {

  public static int nbFile = 0;

  public static int nbOk = 0;

  public static int nbBad = 0;

  /**
   * @param args
   */
  public static void main(String[] args) throws Exception {
    // String root = "/home/gbailleul/Isartor testsuite/PDFA-1b";
    // Collection<?> col = FileUtils.listFiles(new File(root), new
    // String[]{"pdf"}, true);
    // System.err.println(col.size());
    // for (Object object : col) {
    // coin((File)object);
    // System.out.println(nbFile+":"+nbOk+"/"+nbBad);
    // }

    // String root =
    // "/home/gbailleul/Isartor testsuite/PDFA-1b/6.7 Metadata/6.7.8 Extension schemas/isartor-6-7-8-t02-fail-j.pdf";
    // coin(new File(root));

    String root = "resources/model-pdfa.pdf";
    coin(new File(root));

    System.out.println(nbFile + ":" + nbOk + "/" + nbBad);
  }

  public static void coin(File f) {
    nbFile++;
    // PDFBox
    try {
      PDDocument document = PDDocument.load(f);
      COSDocument cDocument = document.getDocument();

      boolean result = PDFParser.parse(new FileInputStream(f));
      if (result) {
        nbOk++;
      } else {
        nbBad++;
      }
      ;

      document.close();
    } catch (IOException e) {
      System.err.println("Failed for : " + f.getAbsolutePath());
      // } catch (ParseException e) {
      // nbBad++;
    } catch (Throwable e) {
      nbBad++;
    }

  }
}
