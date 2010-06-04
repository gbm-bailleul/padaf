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
package net.padaf.preflight;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.activation.FileDataSource;

import junit.framework.Assert;
import net.padaf.preflight.ValidationResult.ValidationError;

import org.apache.commons.io.IOUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class TestIsatorValidation {

	protected static File isartorPath = null;
	protected static FileOutputStream isartorResultFile = null;

	protected static PdfAValidator validator = null;

	protected String expectedError;

	protected File file;

	public TestIsatorValidation(File file, String error) {
		this.file = file;
		this.expectedError = error;
	}

	@BeforeClass
	public static void beforeClass() throws Exception {
		validator = new PdfAValidatorFactory().createValidatorInstance(PdfAValidatorFactory.PDF_A_1_b); 
		String irp = System.getProperty("isartor.results.path");
		if (irp != null) {
			File f = new File (irp);
			if (f.exists() && f.isFile()) {
				f.delete();
				isartorResultFile = new FileOutputStream(f);    		
			} else if (!f.exists()) {
				isartorResultFile = new FileOutputStream(f);
			} else {
				throw new IllegalArgumentException("Invalid result file : " + irp);
			}
		}
	}

	@AfterClass
	public static void afterClass() throws Exception {
		if (isartorResultFile != null) {
			IOUtils.closeQuietly(isartorResultFile);
		}
	}
	
	@Test()
	public void validate() throws Exception {
		try {
			ValidationResult result = validator.validate(new FileDataSource(file));
				Assert.assertFalse(file.getName() + " : Isartor file should be invalid ("
					+ file.getName() + ")", result.isValid());
			Assert.assertTrue(file.getName() + " : Should find at least one error",
					result.getErrorsList().size() > 0);
			// could contain more than one error
			boolean found = false;
			for (ValidationError error : result.getErrorsList()) {
				if (error.getErrorCode().equals(this.expectedError)) {
					found = true;
				}
				if (isartorResultFile != null) {
					String log = file.getName().replace(".pdf", "") + "#" +error.getErrorCode()+"#"+error.getDetails()+"\n";
					isartorResultFile.write(log.getBytes());
				}
			}

			if (result.getErrorsList().size() > 1) {
				if (!found) {
					StringBuilder message = new StringBuilder(100);
					message.append(file.getName()).append(
							" : Invalid error code returned. Expected ");
					message.append(this.expectedError).append(", found ");
					for (ValidationError error : result.getErrorsList()) {
						message.append(error.getErrorCode()).append(" ");
					}
					Assert.fail(message.toString());
				}
			} else {
				Assert.assertEquals(file.getName() + " : Invalid error code returned.",
						this.expectedError, result.getErrorsList().get(0).getErrorCode());
			}
			result.closePdf();
		} catch (ValidationException e) {
			throw new Exception(file.getName() + " :" + e.getMessage(), e);
		}
	}

	@Parameters
	public static Collection<Object[]> initializeParameters() throws Exception {
		// same as before class
		String ip = System.getProperty("isartor.path", null);
		// ip = "/home/gbailleul/PDFA/Isartor testsuite/PDFA-1b";
		if (ip == null) {
			throw new Exception("Isartor path not defined (isartor.path)");
		} else {
			File path = new File(ip);
			if (!path.exists())
				throw new Exception("Isartor path does not exist");
			if (!path.isDirectory())
				throw new Exception("Isartor path is not a directory");
			// else ok
			isartorPath = path;

		}

		// isartorPath = new
		// File("/media/WEBS/PDFA_TEST/6.1 File structure/6.1.12 Implementation Limits/isartor-6-1-12-t01-fail-c.pdf");
		// tests can be done
		List<IsartorTargetFileInformation> col = IsartorTargetFileInformation
		.loadConfiguration(isartorPath);
		List<Object[]> data = new ArrayList<Object[]>(col.size());
		for (IsartorTargetFileInformation element : col) {
			// --- element can be null (if last line is empty)
			if (element != null) {
				Object[] tmp = new Object[] { element.getTargetFile(),
						element.getExpectedError() };
				data.add(tmp);
			}
		}
		return data;
	}
}
