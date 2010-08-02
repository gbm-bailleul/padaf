package net.padaf.preflight.font;

import java.util.ArrayList;
import java.util.List;

import net.padaf.preflight.ValidationConstants;
import net.padaf.preflight.font.type1.Type1;

import org.apache.pdfbox.cos.COSInteger;
import org.apache.pdfbox.pdmodel.font.PDFont;

public class Type1FontContainer extends AbstractFontContainer {

  private List<?> widthsArray = new ArrayList(0);
  private int firstCharInWidthsArray = 0;

  /**
   * Represent the missingWidth value of the FontDescriptor dictionary.
   * According to the PDF Reference, if this value is missing, the default 
   * one is 0.
   */
  private float defaultGlyphWidth = 0;
  /**
   * Object which contains the Type1Font data extracted by the
   * Type1Parser object
   */
  private Type1 fontObject = null;
  
  public Type1FontContainer(PDFont fd) {
    super(fd);
  }

  void setWidthsArray(List<?> widthsArray) {
	  this.widthsArray = widthsArray;
  }

  void setFirstCharInWidthsArray(int firstCharInWidthsArray) {
	  this.firstCharInWidthsArray = firstCharInWidthsArray;
  }

  void setDefaultGlyphWidth(float defaultGlyphWidth) {
	  this.defaultGlyphWidth = defaultGlyphWidth;
  }

  void setFontObject(Type1 fontObject) {
	  this.fontObject = fontObject;
  }
  
  @Override
  public void checkCID(int cid) throws GlyphException {
	  if (isAlreadyComputedCid(cid)) {
		  return;
	  }

	  int indexOfWidth = (cid - firstCharInWidthsArray);
	  float widthProvidedByPdfDictionary = this.defaultGlyphWidth;
	  int widthInFontProgram ;
	  try {
		  widthInFontProgram = this.fontObject.getWidthOfCID(cid);
	  } catch (GlyphException e) {
	  	addKnownCidElement(new GlyphDetail(cid, e));
		  throw e;
	  }

	  if (indexOfWidth >= 0 && indexOfWidth < this.widthsArray.size()) {
		  COSInteger w = (COSInteger)this.widthsArray.get(indexOfWidth);
		  widthProvidedByPdfDictionary = w.intValue(); 
	  }

	  checkWidthsConsistency(cid, widthProvidedByPdfDictionary, widthInFontProgram);
	  addKnownCidElement(new GlyphDetail(cid));
  }

}