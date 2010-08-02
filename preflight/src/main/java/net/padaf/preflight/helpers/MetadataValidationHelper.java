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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import net.padaf.preflight.DocumentHandler;
import net.padaf.preflight.ValidationConstants;
import net.padaf.preflight.ValidationException;
import net.padaf.preflight.ValidatorConfig;
import net.padaf.preflight.ValidationResult.ValidationError;
import net.padaf.preflight.utils.COSUtils;
import net.padaf.preflight.xmp.PDFAIdentificationValidation;
import net.padaf.preflight.xmp.RDFAboutAttributeConcordanceValidation;
import net.padaf.preflight.xmp.SynchronizedMetaDataValidation;
import net.padaf.preflight.xmp.XpacketParsingException;
import net.padaf.preflight.xmp.RDFAboutAttributeConcordanceValidation.DifferentRDFAboutException;
import net.padaf.xmpbox.XMPMetadata;
import net.padaf.xmpbox.parser.XMPDocumentBuilder;
import net.padaf.xmpbox.parser.XmpExpectedRdfAboutAttribute;
import net.padaf.xmpbox.parser.XmpParsingException;
import net.padaf.xmpbox.parser.XmpPropertyFormatException;
import net.padaf.xmpbox.parser.XmpRequiredPropertyException;
import net.padaf.xmpbox.parser.XmpSchemaException;
import net.padaf.xmpbox.parser.XmpUnexpectedNamespacePrefixException;
import net.padaf.xmpbox.parser.XmpUnexpectedNamespaceURIException;
import net.padaf.xmpbox.parser.XmpUnknownPropertyException;
import net.padaf.xmpbox.parser.XmpUnknownSchemaException;
import net.padaf.xmpbox.parser.XmpUnknownValueTypeException;
import net.padaf.xmpbox.parser.XmpXpacketEndException;
import net.padaf.xmpbox.type.BadFieldValueException;

import org.apache.commons.io.IOUtils;
import org.apache.pdfbox.cos.COSBase;
import org.apache.pdfbox.cos.COSDictionary;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.cos.COSObject;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.common.PDStream;

public class MetadataValidationHelper extends AbstractValidationHelper {

  public MetadataValidationHelper(ValidatorConfig cfg)
  throws ValidationException {
	super(cfg);
  }

  /**
   * Return the xpacket from the dictionary's stream
   */
  public static byte[] getXpacket(COSDocument cdocument) throws IOException,
      XpacketParsingException {
    COSObject catalog = cdocument.getCatalog();
    COSBase cb = catalog.getDictionaryObject(COSName.METADATA);
    if (cb == null) {
      // missing Metadata Key in catalog
      ValidationError error = new ValidationError(
          ValidationConstants.ERROR_METADATA_FORMAT,
          "Missing Metadata Key in catalog");
      throw new XpacketParsingException("Failed while retrieving xpacket",
          error);
    }
    // no filter key
    COSDictionary metadataDictionnary = COSUtils.getAsDictionary(cb, cdocument);
    if (metadataDictionnary.getItem(COSName.FILTER) != null) {
      // should not be defined
      ValidationError error = new ValidationError(
          ValidationConstants.ERROR_SYNTAX_STREAM_INVALID_FILTER,
          "Filter specified in metadata dictionnary");
      throw new XpacketParsingException("Failed while retrieving xpacket",
          error);
    }

    PDStream stream = PDStream.createFromCOS(metadataDictionnary);
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    InputStream is = stream.createInputStream();
    IOUtils.copy(is, bos);
    is.close();
    bos.close();
    return bos.toByteArray();
  }

  public List<ValidationError> innerValidate(DocumentHandler handler)
      throws ValidationException {
    try {
      PDDocument document = handler.getDocument();

      byte[] tmp = getXpacket(document.getDocument());
      XMPDocumentBuilder builder;
	  try {
		builder = new XMPDocumentBuilder();
	  } catch (XmpSchemaException e1) {
	    throw new ValidationException(e1.getMessage(), e1);
	  }
      XMPMetadata metadata;
      try {
        metadata = builder.parse(tmp);
        handler.setMetadata(metadata);
      } catch (XmpSchemaException e) {
        throw new ValidationException(
            "Parser: Internal Problem (failed to instanciate Schema object)", e);
      } catch (XmpXpacketEndException e) {
        throw new ValidationException("Unable to parse font metadata due to : "
            + e.getMessage(), e);
      }
      List<ValidationError> lve = new ArrayList<ValidationError>();

      // 6.7.5 no deprecated attribute in xpacket processing instruction
      if (metadata.getXpacketBytes() != null) {
        lve.add(new ValidationError(
            ValidationConstants.ERROR_METADATA_XPACKET_DEPRECATED,
            "bytes attribute is forbidden"));
      }
      if (metadata.getXpacketEncoding() != null) {
        lve.add(new ValidationError(
            ValidationConstants.ERROR_METADATA_XPACKET_DEPRECATED,
            "encoding attribute is forbidden"));
      }

      // Call metadata synchronization checking
      lve.addAll(new SynchronizedMetaDataValidation()
          .validateMetadataSynchronization(document, metadata));

      // Call PDF/A Identifier checking
      lve.addAll(new PDFAIdentificationValidation()
          .validatePDFAIdentifer(metadata));

      // Call rdf:about checking
      try {
        new RDFAboutAttributeConcordanceValidation()
            .validateRDFAboutAttributes(metadata);
      } catch (DifferentRDFAboutException e) {
        lve.add(new ValidationError(
            ValidationConstants.ERROR_METADATA_RDF_ABOUT_ATTRIBUTE_INEQUAL_VALUE, e
                .getMessage()));
      }

      return lve;
    } catch (XpacketParsingException e) {
      List<ValidationError> lve = new ArrayList<ValidationError>();
      if (e.getError() != null) {
        lve.add(e.getError());
      } else {
        lve.add(new ValidationError(ValidationConstants.ERROR_METADATA_MAIN,
            "Unexpected error"));
      }
      return lve;
    } catch (XmpPropertyFormatException e) {
      List<ValidationError> lve = new ArrayList<ValidationError>();
      lve.add(new ValidationError(
          ValidationConstants.ERROR_METADATA_PROPERTY_FORMAT, e.getMessage()));
      return lve;
    } catch (BadFieldValueException e) {
		List<ValidationError> lve = new ArrayList<ValidationError>();
		lve.add(new ValidationError(ValidationConstants.ERROR_METADATA_CATEGORY_PROPERTY_INVALID ,e.getMessage()));
		return lve;
	}
	catch (XmpExpectedRdfAboutAttribute e) {
		List<ValidationError> lve = new ArrayList<ValidationError>();
		lve.add(new ValidationError(ValidationConstants.ERROR_METADATA_RDF_ABOUT_ATTRIBUTE_MISSING ,e.getMessage()));
		return lve;
    } catch (XmpUnknownPropertyException e) {
      List<ValidationError> lve = new ArrayList<ValidationError>();
      lve.add(new ValidationError(
          ValidationConstants.ERROR_METADATA_PROPERTY_UNKNOWN, e.getMessage()));
      return lve;
    } catch (XmpUnknownSchemaException e) {
      List<ValidationError> lve = new ArrayList<ValidationError>();
      lve.add(new ValidationError(
          ValidationConstants.ERROR_METADATA_ABSENT_DESCRIPTION_SCHEMA, e
              .getMessage()));
      return lve;
    } catch (XmpUnexpectedNamespaceURIException e) {
      List<ValidationError> lve = new ArrayList<ValidationError>();
      lve.add(new ValidationError(
          ValidationConstants.ERROR_METADATA_WRONG_NS_URI, e.getMessage()));
      return lve;
    } catch (XmpUnexpectedNamespacePrefixException e) {
      List<ValidationError> lve = new ArrayList<ValidationError>();
      lve.add(new ValidationError(
          ValidationConstants.ERROR_METADATA_ABSENT_DESCRIPTION_SCHEMA, e
              .getMessage()));
      return lve;
    } catch (XmpRequiredPropertyException e) {
      List<ValidationError> lve = new ArrayList<ValidationError>();
      lve.add(new ValidationError(
          ValidationConstants.ERROR_METADATA_PROPERTY_MISSING, e.getMessage()));
      return lve;
    } catch (XmpUnknownValueTypeException e) {
      List<ValidationError> lve = new ArrayList<ValidationError>();
      lve
          .add(new ValidationError(
              ValidationConstants.ERROR_METADATA_UNKNOWN_VALUETYPE, e
                  .getMessage()));
      return lve;
    } catch (XmpParsingException e) {
      List<ValidationError> lve = new ArrayList<ValidationError>();
      lve.add(new ValidationError(ValidationConstants.ERROR_METADATA_FORMAT, e
          .getMessage()));
      return lve;
    }

    catch (IOException e) {
      throw new ValidationException("Failed while validating", e);
    }
  }

  /**
   * Check if metadata dictionary has no stream filter
   * 
   * @param doc
   * @return
   */
  protected List<ValidationError> checkStreamFilterUsage(PDDocument doc) {
    List<ValidationError> ve = new ArrayList<ValidationError>();
    List<?> filters = doc.getDocumentCatalog().getMetadata().getFilters();
    if (filters != null && !filters.isEmpty()) {
      ve.add(new ValidationError(ValidationConstants.ERROR_METADATA_MAIN,
          "Using stream filter on metadata dictionary is forbidden"));
    }
    return ve;
  }

}
