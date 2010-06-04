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
package net.padaf.preflight.font;

import static net.padaf.preflight.ValidationConstants.FONT_DICTIONARY_VALUE_MMTYPE;
import static net.padaf.preflight.ValidationConstants.FONT_DICTIONARY_VALUE_TRUETYPE;
import static net.padaf.preflight.ValidationConstants.FONT_DICTIONARY_VALUE_TYPE1;
import static net.padaf.preflight.ValidationConstants.FONT_DICTIONARY_VALUE_TYPE3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.padaf.preflight.ValidationResult.ValidationError;

import org.apache.pdfbox.pdmodel.font.PDFont;

public class FontContainer {
  /**
   * The Type of the font in this container.
   */
  private FONTTYPE type = FONTTYPE.UNDEF;
  /**
   * PDFBox object which contains the Font Dictionary
   */
  private PDFont font = null;
  /**
   * This map contains all character identifiers known by the Font. If the
   * character is known the value linked with it is true, otherwise false.
   */
  private Map<Integer, Boolean> cidKnownByFont = new HashMap<Integer, Boolean>();

  /**
   * Boolean used to know if the Font Program is embedded.
   */
  private boolean isFontProgramEmbedded = true;
  /**
   * Boolean used to know if Widths are consistent.
   */
  private boolean areWidthsConsistent = true;
  /**
   * Errors which occurs during the Font Validation
   */
  private List<ValidationError> errors = new ArrayList<ValidationError>(0);

  /**
   * The FontContainer Constructor. The type attribute is initialized according
   * to the given PDFont object.
   * 
   * @param fd
   *          Font object of the PDFBox API. (Mandatory)
   * @throws NullPointerException
   *           If the fd is null.
   */
  public FontContainer(PDFont fd) {
    this.font = fd;
    String subtype = fd.getSubType();
    if (FONT_DICTIONARY_VALUE_TRUETYPE.equals(subtype)) {
      this.type = FONTTYPE.TRUETYPE;
    } else if (FONT_DICTIONARY_VALUE_MMTYPE.equals(subtype)) {
      this.type = FONTTYPE.TYPE1;
    } else if (FONT_DICTIONARY_VALUE_TYPE1.equals(subtype)) {
      this.type = FONTTYPE.TYPE1;
    } else if (FONT_DICTIONARY_VALUE_TYPE3.equals(subtype)) {
      this.type = FONTTYPE.TYPE3;
    } else {
      this.type = FONTTYPE.COMPOSITE;
    }
  }

  /**
   * Return the PDFont object
   * 
   * @return
   */
  public PDFont getFont() {
    return font;
  }

  /**
   * Return the type of the PDFont
   * 
   * @return
   */
  public FONTTYPE getType() {
    return type;
  }

  public void addCID(Integer cid, Boolean isPresent) {
    this.cidKnownByFont.put(cid, isPresent);
  }

  /**
   * Return true if the given CID is present in the Font Program, false
   * otherwise.
   * 
   * @param cid
   * @return
   */
  public boolean isEmbeddedChar(Integer cid) {
	Boolean iec = this.cidKnownByFont.get(cid);
    return iec == null ? false : iec;
  }

  /**
   * @return the isFontProgramEmbedded
   */
  public boolean isFontProgramEmbedded() {
    return isFontProgramEmbedded;
  }

  /**
   * @param isFontProgramEmbedded
   *          the isFontProgramEmbedded to set
   */
  public void setFontProgramEmbedded(boolean isFontProgramEmbedded) {
    this.isFontProgramEmbedded = isFontProgramEmbedded;
  }

  /**
   * @return the areWidthsConsistent
   */
  public boolean areWidthsConsistent() {
    return areWidthsConsistent;
  }

  /**
   * @param areWidthsConsistent
   *          the areWidthsConsistent to set
   */
  public void setAreWidthsConsistent(boolean areWidthsConsistent) {
    this.areWidthsConsistent = areWidthsConsistent;
  }

  /**
   * Addition of a validation error.
   * 
   * @param error
   */
  public void addError(ValidationError error) {
    this.errors.add(error);
  }

  /**
   * This method returns the validation state of the font.
   * 
   * If the list of errors is empty, the validation is successful (State :
   * VALID). If the size of the list is 1 and if the error is about EmbeddedFont
   * or WidthsConsistency, the state is "MAYBE" because the font can be valid if
   * it isn't used (for Width error) or if the rendering mode is 3 (For not
   * embedded font). Otherwise, the validation failed (State : INVALID)
   * 
   * @return
   */
  public State isValid() {
    if (this.errors.isEmpty()) {
      return State.VALID;
    }

    if ((this.errors.size() == 1)
        && (!this.isFontProgramEmbedded || !this.areWidthsConsistent)) {
      return State.MAYBE;
    }

    // else more than one error, the validation failed
    return State.INVALID;
  }

  /**
   * @return the errors
   */
  public List<ValidationError> getErrors() {
    return errors;
  }

  public static enum State {
    VALID, MAYBE, INVALID;
  }

  public static enum FONTTYPE {
    UNDEF, TYPE1, TRUETYPE, TYPE3, COMPOSITE;
  }
}
