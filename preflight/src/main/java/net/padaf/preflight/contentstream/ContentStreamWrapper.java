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
package net.padaf.preflight.contentstream;

import static net.padaf.preflight.ValidationConstants.DICTIONARY_KEY_RESOURCES;
import static net.padaf.preflight.ValidationConstants.ERROR_FONTS_ENCODING_ERROR;
import static net.padaf.preflight.ValidationConstants.ERROR_FONTS_FONT_FILEX_INVALID;
import static net.padaf.preflight.ValidationConstants.ERROR_FONTS_GLYPH_MISSING;
import static net.padaf.preflight.ValidationConstants.ERROR_FONTS_METRICS;
import static net.padaf.preflight.ValidationConstants.ERROR_FONTS_UNKNOWN_FONT_REF;
import static net.padaf.preflight.ValidationConstants.ERROR_SYNTAX_CONTENT_STREAM_INVALID_ARGUMENT;
import static net.padaf.preflight.ValidationConstants.ERROR_SYNTAX_CONTENT_STREAM_UNSUPPORTED_OP;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.padaf.preflight.DocumentHandler;
import net.padaf.preflight.ValidationException;
import net.padaf.preflight.ValidationResult.ValidationError;
import net.padaf.preflight.font.FontContainer;
import net.padaf.preflight.font.FontContainer.State;
import net.padaf.preflight.utils.ContentStreamEngine;

import org.apache.pdfbox.cos.COSArray;
import org.apache.pdfbox.cos.COSDictionary;
import org.apache.pdfbox.cos.COSInteger;
import org.apache.pdfbox.cos.COSStream;
import org.apache.pdfbox.cos.COSString;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectForm;
import org.apache.pdfbox.pdmodel.text.PDTextState;
import org.apache.pdfbox.util.PDFOperator;
import org.apache.pdfbox.util.operator.OperatorProcessor;

public class ContentStreamWrapper extends ContentStreamEngine {

  public ContentStreamWrapper(DocumentHandler handler, PDPage page) {
    super(handler);
    this.page = page;
  }

  /**
   * Process the validation of a PageContent (The page is initialized by the
   * constructor)
   * 
   * @return A list of validation error. This list is empty if the validation
   *         succeed.
   * @throws ValidationException.
   */
  public List<ValidationError> validPageContentStream()
      throws ValidationException {
    List<ValidationError> errors = new ArrayList<ValidationError>();

    try {
      processStream(page, page.findResources(), page.getContents().getStream());
    } catch (ContentStreamException e) {
      errors.add(new ValidationError(e.getValidationError(), e.getMessage()));
    } catch (IOException e) {
      throw new ValidationException("Unable to check the ContentStream : "
          + e.getMessage(), e);
    }

    return errors;
  }

  /**
   * Process the validation of a XObject Form
   * 
   * @param xobj
   * @return A list of validation error. This list is empty if the validation
   *         succeed.
   * @throws ValidationException
   */
  public List<ValidationError> validXObjContentStream(PDXObjectForm xobj)
      throws ValidationException {
    List<ValidationError> errors = new ArrayList<ValidationError>();

    try {
      processStream(null, xobj.getResources(), xobj.getCOSStream());
    } catch (ContentStreamException e) {
      errors.add(new ValidationError(e.getValidationError(), e.getMessage()));
    } catch (IOException e) {
      throw new ValidationException("Unable to check the ContentStream : "
          + e.getMessage(), e);
    }

    return errors;
  }

  /**
   * Process the validation of a Tiling Pattern
   * 
   * @param pattern
   * @return A list of validation error. This list is empty if the validation
   *         succeed.
   * @throws ValidationException
   */
  public List<ValidationError> validPatternContentStream(COSStream pattern)
      throws ValidationException {
    List<ValidationError> errors = new ArrayList<ValidationError>();

    try {
      COSDictionary res = (COSDictionary) pattern
          .getDictionaryObject(DICTIONARY_KEY_RESOURCES);
      processStream(null, new PDResources(res), pattern);
    } catch (ContentStreamException e) {
      errors.add(new ValidationError(e.getValidationError(), e.getMessage()));
    } catch (IOException e) {
      throw new ValidationException("Unable to check the ContentStream : "
          + e.getMessage(), e);
    }

    return errors;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.apache.pdfbox.util.PDFStreamEngine#processOperator(org.apache.pdfbox
   * .util.PDFOperator, java.util.List)
   */
  protected void processOperator(PDFOperator operator, List arguments)
      throws IOException {

    // ---- Here is a copy of the super method because the else block is
    // different. (If the operator is unknown, throw an exception)
    String operation = operator.getOperation();
    OperatorProcessor processor = (OperatorProcessor) operators.get(operation);
    if (processor != null) {
      processor.setContext(this);
      processor.process(operator, arguments);
    } else {
      if (!unsupportedOperators.contains(operation)) {
        throwContentStreamException("The operator \"" + operation
            + "\" isn't supported.", ERROR_SYNTAX_CONTENT_STREAM_UNSUPPORTED_OP);
      }
    }

    // --- Process Specific Validation
    // --- The Generic Processing is useless for PDFA validation
    if ("BI".equals(operation)) {
      validImageFilter(operator);
      validImageColorSpace(operator);
    }

    checkShowTextOperators(operator, arguments);
    checkColorOperators(operation);
    validRenderingIntent(operator, arguments);
    checkSetColorSpaceOperators(operator, arguments);
  }

  /**
   * Process Text Validation. According to the operator one of the both method
   * will be called. (validStringDefinition(PDFOperator operator, List<?>
   * arguments) / validStringArray(PDFOperator operator, List<?> arguments))
   * 
   * @param operator
   * @param arguments
   * @throws ContentStreamException
   * @throws IOException
   */
  protected void checkShowTextOperators(PDFOperator operator, List<?> arguments)
      throws ContentStreamException, IOException {
	  String op = operator.getOperation();
    if ("Tj".equals(op)
        || "'".equals(op)
        || "\"".equals(op)) {
      validStringDefinition(operator, arguments);
    }

    if ("TJ".equals(op)) {
      validStringArray(operator, arguments);
    }
  }

  /**
   * Process Text Validation for the Operands of a Tj, "'" and "\"" operator.
   * 
   * If the validation fails for an unexpected reason, a IOException is thrown.
   * If the validation fails due to validation error, a ContentStreamException
   * is thrown. (Use the ValidationError attribute to know the cause)
   * 
   * @param operator
   * @param arguments
   * @throws ContentStreamException
   * @throws IOException
   */
  private void validStringDefinition(PDFOperator operator, List<?> arguments)
      throws ContentStreamException, IOException {
    // ---- For a Text operator, the arguments list should contain only one
    // COSString object
    Object objStr = arguments.get(0);
    if (objStr instanceof COSString) {
      validText(((COSString) objStr).getBytes());
    } else if (!(objStr instanceof COSInteger)) {
      throwContentStreamException("Invalid argument for the operator : "
          + operator.getOperation(),
          ERROR_SYNTAX_CONTENT_STREAM_INVALID_ARGUMENT);
    }
  }

  /**
   * Process Text Validation for the Operands of a TJ operator.
   * 
   * If the validation fails for an unexpected reason, a IOException is thrown.
   * If the validation fails due to validation error, a ContentStreamException
   * is thrown. (Use the ValidationError attribute to know the cause)
   * 
   * @param operator
   * @param arguments
   * @throws ContentStreamException
   * @throws IOException
   */
  private void validStringArray(PDFOperator operator, List<?> arguments)
      throws ContentStreamException, IOException {
    for (Object object : arguments) {
      if (object instanceof COSArray) {
        validStringArray(operator, ((COSArray) object).toList());
      } else if (object instanceof COSString) {
        validText(((COSString) object).getBytes());
      } else if (!(object instanceof COSInteger)) {
        throwContentStreamException("Invalid argument for the operator : "
            + operator.getOperation(),
            ERROR_SYNTAX_CONTENT_STREAM_INVALID_ARGUMENT);
      }
    }
  }

  /**
   * Process the validation of a Text operand contains in a ContentStream This
   * validation checks that :
   * <UL>
   * <li>The font isn't missing if the Rendering Mode isn't 3
   * <li>The font metrics are consistent
   * <li>All character used in the text are defined in the font program.
   * </UL>
   * 
   * @param string
   * @throws IOException
   */
  public void validText(byte[] string) throws IOException {
    // --- TextSize accessible through the TextState
	PDTextState textState = graphicsState.getTextState();
    final int renderingMode = textState.getRenderingMode();
    final PDFont font = textState.getFont();

    if (font == null) {
      // ---- Unable to decode the Text without Font
      throwContentStreamException("Text operator can't be process without Font", ERROR_FONTS_UNKNOWN_FONT_REF);
    }

    // FontContainer fontContainer = documentHandler.retrieveFontContainer(font);
    FontContainer fontContainer = documentHandler.getFont(font.getCOSObject());
    if (fontContainer == null && renderingMode != 3) {
      // ---- Font Must be embedded if the RenderingMode isn't 3
      throwContentStreamException(font.getBaseFont() + " is unknown wasn't found by the FontHelperValdiator", ERROR_FONTS_UNKNOWN_FONT_REF);
    } else if (renderingMode != 3 && !fontContainer.isFontProgramEmbedded()) {
      throwContentStreamException(font.getBaseFont() + " isn't embedded and the rendering mode isn't 3", ERROR_FONTS_FONT_FILEX_INVALID);
    } else if (renderingMode == 3 && fontContainer == null) {
    	return;
    } else if (fontContainer != null && !fontContainer.areWidthsConsistent()) {
      throwContentStreamException(font.getBaseFont() + " metrics are inconsistent with the /Widths", ERROR_FONTS_METRICS);
    } else if (fontContainer.areWidthsConsistent() && fontContainer.isFontProgramEmbedded() && fontContainer.isValid() != State.VALID ) {
    	return;
    }

    int codeLength = 1;
    for (int i = 0; i < string.length; i += codeLength) {
      // Decode the value to a Unicode character
      codeLength = 1;
      String c = null;
      try {
        c = font.encode(string, i, codeLength);
        if (c == null && i + 1 < string.length) {
          // maybe a multibyte encoding
          codeLength++;
          c = font.encode(string, i, codeLength);
        }
      } catch (IOException e) {
        throwContentStreamException("Encoding can't interpret the character code", ERROR_FONTS_ENCODING_ERROR);
      }

      // ---- According to the length of the character encoding,
      // convert the character to CID
      int cid = 0;
      for (int j = 0; j < codeLength; j++) {
        cid <<= 8;
        cid += ((string[i + j] + 256) % 256);
      }

      if (fontContainer != null && !fontContainer.isEmbeddedChar(cid)) {
        // ---- Unable to decode a character, throw an exception
        throwContentStreamException("The character code " + cid + " doesn't have glyph in the embedded font " + font.getBaseFont(), ERROR_FONTS_GLYPH_MISSING);
      }
    }
  }
}
