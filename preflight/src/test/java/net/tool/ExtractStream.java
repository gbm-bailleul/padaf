package net.tool;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.pdfbox.cos.COSObject;
import org.apache.pdfbox.cos.COSStream;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.persistence.util.COSObjectKey;

public class ExtractStream {
	public static void main(String[] args) throws Exception {
		if (args.length != 3) {
			System.err.println("usage : ExtractStream file objNum objGen");
		}
		PDDocument document = PDDocument.load(new FileInputStream(args[0]));
		COSObject obj = document.getDocument().getObjectFromPool(new COSObjectKey(Integer.parseInt(args[1]),Integer.parseInt(args[2])));
		if (obj.getObject() instanceof COSStream) {
			COSStream stream = (COSStream)obj.getObject();
			InputStream is = stream.getUnfilteredStream();
			FileOutputStream out = new FileOutputStream("stream.out");
			IOUtils.copyLarge(is, out);
			IOUtils.closeQuietly(out);
		}
	}
}
