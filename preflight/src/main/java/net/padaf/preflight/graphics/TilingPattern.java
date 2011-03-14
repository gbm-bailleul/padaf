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
package net.padaf.preflight.graphics;

import static net.padaf.preflight.ValidationConstants.DICTIONARY_KEY_RESOURCES;
import static net.padaf.preflight.ValidationConstants.ERROR_GRAPHIC_INVALID_PATTERN_DEFINITION;
import static net.padaf.preflight.ValidationConstants.PATTERN_KEY_BBOX;
import static net.padaf.preflight.ValidationConstants.PATTERN_KEY_PAINT_TYPE;
import static net.padaf.preflight.ValidationConstants.PATTERN_KEY_TILING_TYPE;
import static net.padaf.preflight.ValidationConstants.PATTERN_KEY_XSTEP;
import static net.padaf.preflight.ValidationConstants.PATTERN_KEY_YSTEP;

import java.util.ArrayList;
import java.util.List;

import net.padaf.preflight.DocumentHandler;
import net.padaf.preflight.ValidationException;
import net.padaf.preflight.ValidationResult.ValidationError;
import net.padaf.preflight.contentstream.ContentStreamWrapper;

import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.cos.COSStream;

public class TilingPattern implements XObjectValidator {
  /**
   * The COSStream which represents the TilingPattern.
   */
  private COSStream pattern = null;
  /**
   * The document handler which contains useful information to process the
   * validation.
   */
  private DocumentHandler documentHandler = null;

  public TilingPattern(DocumentHandler _handler, COSStream _pattern) {
    this.documentHandler = _handler;
    this.pattern = _pattern;
  }

  /**
   * Validate the Pattern content like Color and Show Text Operators using an
   * instance of ContentStreamWrapper.
   * 
   * @param errors
   *          the list of error to update if the validation fails.
   * @return
   * @throws ValidationException
   */
  protected boolean parsePatternContent(List<ValidationError> errors)
      throws ValidationException {
    ContentStreamWrapper csWrapper = new ContentStreamWrapper(documentHandler);
    List<ValidationError> csParseErrors = csWrapper
        .validPatternContentStream(pattern);
    if (csParseErrors == null
        || (csParseErrors != null && csParseErrors.isEmpty())) {
      return true;
    }
    errors.addAll(csParseErrors);
    return false;
  }

  /**
   * This method checks if required fields are present.
   * 
   * @param result
   *          the list of error to update if the validation fails.
   * @return true if all fields are present, false otherwise.
   */
  protected boolean checkMandatoryFields(List<ValidationError> errors) {
    boolean res = pattern.getItem(COSName.getPDFName(DICTIONARY_KEY_RESOURCES)) != null;
    res = res && pattern.getItem(COSName.getPDFName(PATTERN_KEY_BBOX)) != null;
    res = res
        && pattern.getItem(COSName.getPDFName(PATTERN_KEY_PAINT_TYPE)) != null;
    res = res
        && pattern.getItem(COSName.getPDFName(PATTERN_KEY_TILING_TYPE)) != null;
    res = res && pattern.getItem(COSName.getPDFName(PATTERN_KEY_XSTEP)) != null;
    res = res && pattern.getItem(COSName.getPDFName(PATTERN_KEY_YSTEP)) != null;
    if (!res) {
      errors.add(new ValidationError(ERROR_GRAPHIC_INVALID_PATTERN_DEFINITION));
    }
    return res;
  }

  /*
   * (non-Javadoc)
   * 
   * @see net.awl.edoc.pdfa.validation.graphics.XObjectValidator#validate()
   */
  public List<ValidationError> validate() throws ValidationException {
    List<ValidationError> result = new ArrayList<ValidationError>();
    boolean isValid = checkMandatoryFields(result);
    isValid = isValid && parsePatternContent(result);
    return result;
  }
}
