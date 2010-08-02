package net.padaf.preflight.font;

import org.apache.pdfbox.pdmodel.font.PDFont;

public class CompositeFontContainer extends AbstractFontContainer {
	private AbstractFontContainer delegatedContainer = null;

  public CompositeFontContainer(PDFont fd) {
    super(fd);
  }

  @Override
  public void checkCID(int cid) throws GlyphException {
    this.delegatedContainer.checkCID(cid);
  }

  CFFType0FontContainer getCFFType0() {
  	if (delegatedContainer == null) {
  		delegatedContainer = new CFFType0FontContainer(this);
  	}
  	return (CFFType0FontContainer)this.delegatedContainer;
  }

  CFFType2FontContainer getCFFType2() {
  	if (delegatedContainer == null) {
  		delegatedContainer = new CFFType2FontContainer(this);
  	}
  	return (CFFType2FontContainer)this.delegatedContainer;
  }
}
