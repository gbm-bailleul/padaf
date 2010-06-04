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
/**
 * 
 */
package net.padaf.preflight.graphics;

import java.util.List;

import net.padaf.preflight.DocumentHandler;
import net.padaf.preflight.ValidationConstants;
import net.padaf.preflight.ValidationException;
import net.padaf.preflight.ValidationResult.ValidationError;
import net.padaf.preflight.graphics.color.ColorSpaceHelper;
import net.padaf.preflight.graphics.color.ColorSpaceHelperFactory;
import net.padaf.preflight.graphics.color.ColorSpaceHelperFactory.ColorSpaceRestriction;
import net.padaf.preflight.utils.RenderingIntents;

import org.apache.pdfbox.cos.COSBase;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.cos.COSStream;

/**
 * This class validates XObject with the Image subtype.
 */
public class XObjImageValidator extends AbstractXObjValidator implements
    ValidationConstants {

  public XObjImageValidator(DocumentHandler handler, COSStream xobj) {
    super(handler, xobj);
  }

  /*
   * (non-Javadoc)
   * 
   * @seenet.awl.edoc.pdfa.validation.graphics.AbstractXObjValidator#
   * checkMandatoryFields(java.util.List)
   */
  @Override
  protected boolean checkMandatoryFields(List<ValidationError> result) {
    boolean res = this.xobject.getItem(COSName.getPDFName("Width")) != null;
    res = res && this.xobject.getItem(COSName.getPDFName("Height")) != null;
    // type and subtype checked before to create the Validator.
    if (!res) {
      result.add(new ValidationError(ERROR_GRAPHIC_MISSING_FIELD));
    }
    return res;
  }

  /*
   * (non-Javadoc)
   * 
   * @see net.awl.edoc.pdfa.validation.graphics.AbstractXObjValidator#validate()
   */
  @Override
  public List<ValidationError> validate() throws ValidationException {
    List<ValidationError> result = super.validate();
    // 6.2.4 no Alternates
    if (this.xobject.getItem(COSName.getPDFName("Alternates")) != null) {
      result.add(new ValidationError(
          ValidationConstants.ERROR_GRAPHIC_UNEXPECTED_KEY,
          "Unexpected 'Alternates' Key"));
    }
    // 6.2.4 if interpolates, value = false
    if (this.xobject.getItem(COSName.getPDFName("Interpolate")) != null) {
      if (this.xobject.getBoolean(COSName.getPDFName("Interpolate"), true)) {
        result.add(new ValidationError(
            ValidationConstants.ERROR_GRAPHIC_UNEXPECTED_VALUE_FOR_KEY,
            "Unexpected 'true' value for 'Interpolate' Key"));
      }
    }
    // 6.2.4 Intent has specific values
    if (this.xobject.getItem(COSName.getPDFName("Intent")) != null) {
      String s = this.xobject.getNameAsString("Intent");
      if (!RenderingIntents.contains(s)) {
        result.add(new ValidationError(ERROR_GRAPHIC_UNEXPECTED_VALUE_FOR_KEY,
            "Unexpected value '" + s + "' for Intent key in image"));
      }
    }

    COSBase csImg = this.xobject.getItem(COSName
        .getPDFName(XOBJECT_DICTIONARY_KEY_COLOR_SPACE));
    ColorSpaceHelper csh = ColorSpaceHelperFactory.getColorSpaceHelper(csImg,
        handler, ColorSpaceRestriction.NO_PATTERN);
    csh.validate(result);
    return result;
  }
}
