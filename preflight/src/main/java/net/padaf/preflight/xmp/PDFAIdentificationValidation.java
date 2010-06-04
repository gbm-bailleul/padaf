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
package net.padaf.preflight.xmp;

import java.util.ArrayList;
import java.util.List;

import net.padaf.preflight.ValidationConstants;
import net.padaf.preflight.ValidationException;
import net.padaf.preflight.ValidationResult.ValidationError;
import net.padaf.xmpbox.XMPMetadata;
import net.padaf.xmpbox.schema.PDFAIdentificationSchema;
import net.padaf.xmpbox.schema.XMPBasicSchema;

/**
 * Class which check if PDF/A Identification Schema contains good information
 * 
 * @author Germain Costenobel
 * 
 */
public class PDFAIdentificationValidation {

  /**
   * Check if PDFAIdentification is valid
   * 
   * @param document
   *          the PDF Document
   * @param metadata
   *          the XMP MetaData
   * @return List of validation errors
   * @throws ValidationException
   */
  public List<ValidationError> validatePDFAIdentifer(XMPMetadata metadata)
      throws ValidationException {
    List<ValidationError> ve = new ArrayList<ValidationError>();
    PDFAIdentificationSchema id = metadata.getPDFIdentificationSchema();
    if (id == null) {
      ve.add(new ValidationError(
          ValidationConstants.ERROR_METADATA_PDFA_ID_MISSING));
      return ve;
    }
    if (!id.getPrefix().equals(PDFAIdentificationSchema.IDPREFIX)) {
      if (metadata.getSchema(PDFAIdentificationSchema.IDPREFIX,
          XMPBasicSchema.XMPBASICURI) == null) {
        ve.add(UnexpectedPrefixFoundError(id.getPrefix(),
            PDFAIdentificationSchema.IDPREFIX, PDFAIdentificationSchema.class
                .getName()));
      } else {
        id = (PDFAIdentificationSchema) metadata.getSchema(
            PDFAIdentificationSchema.IDPREFIX, PDFAIdentificationSchema.IDURI);
      }
    }
    checkConformanceLevel(ve, id.getConformanceValue());
    checkPartNumber(ve, id.getPartValue());
    return ve;
  }

  /**
   * Return a validationError formatted when a schema has not the expected
   * prefix
   * 
   * @param prefFound
   * @param prefExpected
   * @param schema
   * @return
   */
  protected ValidationError UnexpectedPrefixFoundError(String prefFound,
      String prefExpected, String schema) {
    StringBuilder sb = new StringBuilder(80);
    sb.append(schema).append(" found but prefix used is '").append(prefFound)
        .append("', prefix '").append(prefExpected).append("' is expected.");

    return new ValidationError(
        ValidationConstants.ERROR_METADATA_WRONG_NS_PREFIX, sb.toString());
  }

  protected void checkConformanceLevel(List<ValidationError> ve, String value) {
    if (!(value.equals("A") || value.equals("B"))) {
      ve.add(new ValidationError(
          ValidationConstants.ERROR_METADATA_INVALID_PDFA_CONFORMANCE));
    }
  }

  protected void checkPartNumber(List<ValidationError> ve, int value) {
    if (value != 1) {
      ve.add(new ValidationError(
          ValidationConstants.ERROR_METADATA_INVALID_PDFA_VERSION_ID));
    }
  }
}
