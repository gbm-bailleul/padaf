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
package net.padaf.preflight.graphics.color;

import static net.padaf.preflight.ValidationConstants.ERROR_GRAPHIC_INVALID_PATTERN_COLOR_SPACE_FORBIDDEN;

import java.util.List;

import net.padaf.preflight.DocumentHandler;
import net.padaf.preflight.ValidationResult.ValidationError;

import org.apache.pdfbox.cos.COSBase;
import org.apache.pdfbox.pdmodel.graphics.color.PDColorSpace;

/**
 * This class defines restrictions on Pattern ColorSpace. It checks the
 * consistency of the Color space with the DestOutputIntent, if the color space
 * is a Pattern the validation will fail.
 */
public class NoPatternColorSpaceHelper extends StandardColorSpaceHelper {

  NoPatternColorSpaceHelper(COSBase _csObject, DocumentHandler _handler) {
    super(_csObject, _handler);
  }

  NoPatternColorSpaceHelper(PDColorSpace _csObject, DocumentHandler _handler) {
    super(_csObject, _handler);
  }

  /**
   * This method updates the given list with a ValidationError
   * (ERROR_GRAPHIC_INVALID_PATTERN_COLOR_SPACE_FORBIDDEN) and returns false.
   */
  protected boolean processPatternColorSpace(List<ValidationError> result) {
    result.add(new ValidationError(
        ERROR_GRAPHIC_INVALID_PATTERN_COLOR_SPACE_FORBIDDEN, "Pattern color space is forbidden"));
    return false;
  }
}
