package net.padaf.isartor.report;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IsartorTestReader {

	private File path;
	
	private Map mapOfRes = new HashMap();

	public IsartorTestReader(File isartorRes) {
		path = isartorRes;
	}

	public void parseResult() throws IOException {
		FileInputStream fis = new FileInputStream(path);
		BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

		while (reader.ready()) {
			String line = reader.readLine();
			String[] split = line.split("#");
			if ( split.length == 3 ) {
			    String s = new File(split[0]).getName();
				addResult(s, split[1], split[2]);
			}
		}
		
		reader.close();
	}

	private void addResult(String test, String code, String details) {
		IsartorResultContainer res = new IsartorResultContainer(code, details);
		List lst = new ArrayList();
		if (this.mapOfRes.containsKey(test)) {
			lst = (List)this.mapOfRes.get(test); 
		}
		lst.add(res);
		this.mapOfRes.put(test, lst);
	}
	
	public List getIartorResults(String isoartorName) {
	  System.err.println("### "+isoartorName);
		List lRes = (List)this.mapOfRes.get(isoartorName);
		if (lRes == null) {
			lRes = new ArrayList(0);
		}
		System.err.println("  #"+lRes.size());
		return lRes;
	}
	
	public static class IsartorResultContainer {
		private String errorCode;
		private String details;
		
		public IsartorResultContainer(String errorCode, String details) {
			super();
			this.errorCode = errorCode;
			this.details = details;
		}
		/**
		 * @return the errorCode
		 */
		public String getErrorCode() {
			return errorCode;
		}
		/**
		 * @return the details
		 */
		public String getDetails() {
			return details;
		}
		
	}
}
