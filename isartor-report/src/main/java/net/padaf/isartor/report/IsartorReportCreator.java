package net.padaf.isartor.report;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import net.padaf.isartor.report.IsartorTestReader.IsartorResultContainer;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

/**
 * Goal which generates the Isartor test report.
 *
 * @goal isartor
 * @requiresDependencyResolution test
 * @phase site
 */
public class IsartorReportCreator
extends AbstractMojo
{
	/**
	 * Location of the file.
	 * @parameter expression="${project.reporting.outputDirectory}"
	 * @required
	 */
	private File outputDirectory;

	/**
	 * Path to the file which contains errors found for isartor pdf files. 
	 * @parameter expression="${isartor.result.path}"
	 * @required 
	 */
	private String isartorResult;

	public void execute()
	throws MojoExecutionException
	{
		File f = outputDirectory;
		if ( !f.exists() )
		{
			f.mkdirs();
		}

		// --- Test if isartor result file exists
		File testResult = new File(this.isartorResult);
		if (!testResult.exists() || !testResult.isFile()) {
			throw new MojoExecutionException( "isartor.result.path value is invalid : " + isartorResult);
		}

		// Parse Isartor result
		IsartorTestReader itr = new IsartorTestReader(testResult);
		try {
			itr.parseResult();
		} catch (IOException e) {
			throw new MojoExecutionException( "Can't read isartor result file ", e );
		}

		BufferedReader expected = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("isartor_expected_errors.txt")));
		File report = new File( f, "isartor.html" );
		FileWriter w = null;
		try
		{
			w = new FileWriter( report );
			createHeader(w);
			while ( expected.ready() ) {
				String line = expected.readLine();
				addResult(line.substring(0, line.indexOf(" ")).trim(), line.substring(line.indexOf(" "), line.length()), itr, w);
			}
			endReport(w);
		}
		catch ( IOException e )
		{
			throw new MojoExecutionException( "Error creating file " + report, e );
		}
		finally
		{
			if ( w != null )
			{
				try
				{
					w.close();
				}
				catch ( IOException e )
				{
					// ignore
				}
			}
			
			if (expected != null) {
				try
				{
					expected.close();
				}
				catch ( IOException e )
				{
					// ignore
				}
			}
		}
	}

	public void addResult(String test, String expected, IsartorTestReader isartorReader, FileWriter writer) throws IOException {
		List lContainer = isartorReader.getIartorResults(test);
		writer.write("<p><table>\n");
		
		writer.write("<th colspan=\"3\"> Test : " + test + " </th>" );
		writer.write("<tr><td class=\"expected\"> Expected Error : </td><td class=\"expected\" colspan=\"2\">" + expected + " </td></tr>" );
		
		for (int i = 0; i < lContainer.size(); ++i) {
			IsartorResultContainer container = (IsartorResultContainer)lContainer.get(i);
			writer.write("<tr><td class=\"title\"> Test Result : </td><td class=\"errorCode\">" + container.getErrorCode() + " </td><td>" + container.getDetails() +"</td></tr>" );
		}

		writer.write("</table></p>\n");
		writer.flush();
	}

	public void createHeader(FileWriter writer) throws IOException {
		writer.write("<html>\n");
		writer.write("<header>");
		writer.write("<style type=\"text/css\">");
		
		InputStream is = this.getClass().getResourceAsStream("isartor.css");
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		while (br.ready()) {
			writer.write(br.readLine());
		}
		br.close();
		writer.write("</style>");
		writer.write("<title>Isartor test suite report</title></header>");
		writer.write("<body><H1>Isartor Test Suite report</H1>\n");
	}

	public void endReport(FileWriter writer) throws IOException {
		writer.write("</body>\n");
		writer.write("</html>");
		writer.flush();
	}
}