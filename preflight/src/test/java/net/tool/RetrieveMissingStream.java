package net.tool;

import java.io.FileInputStream;
import java.util.HashSet;
import java.util.List;

import org.apache.pdfbox.cos.COSBase;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.cos.COSObject;
import org.apache.pdfbox.cos.COSStream;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDStream;
import org.apache.pdfbox.persistence.util.COSObjectKey;

public class RetrieveMissingStream {
	public static void main(String[] args) throws Exception {
		if (args.length != 1) {
			System.err.println("usage : RetrieveMissingStream file");
			System.exit(233);
		}

		HashSet<COSObjectKey> listOfKeys = new HashSet<COSObjectKey>();
		
		PDDocument document = PDDocument.load(new FileInputStream(args[0]));
		List<COSObject> lCosObj = document.getDocument().getObjects();
		for (COSObject cosObject : lCosObj) {

			if (cosObject.getObject() instanceof COSStream) {
				listOfKeys.add(new COSObjectKey(cosObject.getObjectNumber().intValue(),
																				cosObject.getGenerationNumber().intValue()));
			}

		}
		
    PDDocumentCatalog catalog = document.getDocumentCatalog();
    List<?> pages = catalog.getAllPages();
    for (int i = 0; i < pages.size(); ++i) {
    	PDPage pdp = (PDPage) pages.get(i);
    	PDStream pdStream = pdp.getContents();
 
    	COSBase b = pdp.getCOSDictionary().getItem(COSName.getPDFName("Contents"));
    	System.out.println();
    }
	}
}
