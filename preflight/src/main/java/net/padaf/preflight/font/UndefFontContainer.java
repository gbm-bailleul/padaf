package net.padaf.preflight.font;

import net.padaf.preflight.ValidationConstants;

import org.apache.pdfbox.pdmodel.font.PDFont;

public class UndefFontContainer extends AbstractFontContainer {

  public UndefFontContainer(PDFont fd) {
    super(fd);
  }

  @Override
  public void checkCID(int cid) throws GlyphException {
  	throw new GlyphException(ValidationConstants.ERROR_FONTS_UNKNOWN_FONT_REF, 0, "A text content is using a undefined font type.");
  }
}