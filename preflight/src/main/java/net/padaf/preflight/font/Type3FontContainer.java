package net.padaf.preflight.font;

import net.padaf.preflight.ValidationConstants;

import org.apache.pdfbox.pdmodel.font.PDFont;

/**
 * Because Type3 font program is an inner type of the PDF file, 
 * this font container is quite different from the other because
 * all character/glyph are already checked.
 * 
 */
public class Type3FontContainer extends AbstractFontContainer {

  public Type3FontContainer(PDFont fd) {
    super(fd);
  }

  @Override
  public void checkCID(int cid) throws GlyphException {
  	if (!isAlreadyComputedCid(cid)) {
  		// missing glyph
  		GlyphException e = new GlyphException(ValidationConstants.ERROR_FONTS_GLYPH_MISSING, cid, 
  																					"There are no glyph in the Type 3 font for the character \"" + cid + "\"");
	  	addKnownCidElement(new GlyphDetail(cid, e));
	  	throw e;
  	}  	
  } 
}