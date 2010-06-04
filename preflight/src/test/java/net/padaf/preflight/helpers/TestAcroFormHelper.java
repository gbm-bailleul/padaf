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
package net.padaf.preflight.helpers;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.activation.FileDataSource;

import net.padaf.preflight.DocumentHandler;
import net.padaf.preflight.PdfAValidatorFactory;
import net.padaf.preflight.ValidationException;
import net.padaf.preflight.ValidatorConfig;
import net.padaf.preflight.ValidationResult.ValidationError;
import net.padaf.preflight.helpers.AcroFormValidationHelper;
import net.padaf.preflight.util.DocumentHandlerStub;
import net.padaf.preflight.util.IsartorPdfProvider;
import net.padaf.preflight.util.NOCatalogDocument;
import net.padaf.preflight.utils.COSUtils;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.junit.Test;

public class TestAcroFormHelper {

  public static final String PDF_ACROFORM = "isartor-6-9-t02-fail-b.pdf";

  @Test(expected = ValidationException.class)
  public void noCatalogEntry() throws ValidationException {
    PDDocument pddoc = null;
    try {
      AcroFormValidationHelper helper = new AcroFormValidationHelper(PdfAValidatorFactory.pdfa1bStandardConfig);
      DocumentHandlerStub hdl = new DocumentHandlerStub(null);
      pddoc = new NOCatalogDocument();
      hdl.setDocument(pddoc);
      helper.innerValidate(hdl);
    } catch (IOException e) {
      fail(e.getMessage());
    } finally {
      COSUtils.closeDocumentQuietly(pddoc);
    }
  }

  @Test(expected = ValidationException.class)
  public void exploreFieldsFails() throws ValidationException {
    File idoc = IsartorPdfProvider.getIsartorDocument(PDF_ACROFORM);
    if (idoc != null) {
      PDDocument pdDoc = null;
      try {
        pdDoc = PDDocument.load(idoc);
        DocumentHandlerStub handler = new DocumentHandlerStub(
            new FileDataSource(idoc));
        handler.setDocument(pdDoc);
        DummyAcroFormHelper helper = new DummyAcroFormHelper(PdfAValidatorFactory.pdfa1bStandardConfig);
        helper.innerValidate(handler);
      } catch (IOException e) {
        fail(e.getMessage());
      } finally {
        COSUtils.closeDocumentQuietly(pdDoc);
      }
    }
  }

  private class DummyAcroFormHelper extends AcroFormValidationHelper {
	  
    public DummyAcroFormHelper(ValidatorConfig cfg) throws ValidationException {
		super(cfg);
	}

	protected boolean exploreFields(DocumentHandler handler, List<?> lFields,
        List<ValidationError> error) throws IOException {
      handler.close();
      throw new IOException("Exception for code coverage");
    }

  }

}
