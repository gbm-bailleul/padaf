package net.padaf.preflight;

import org.junit.Test;

public class TestVersionning {
	
	
	@Test
	public void testGetVersion () throws Exception {
		PdfAValidatorFactory factory = new PdfAValidatorFactory();
		PdfAValidator validator = factory.createValidatorInstance(PdfAValidatorFactory.PDF_A_1_b);
		
		System.err.println(">> "+validator.getFullName());
	}

}
