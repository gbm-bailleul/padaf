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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.activation.DataSource;
import javax.activation.FileDataSource;

import net.padaf.preflight.PdfA1bValidator;
import net.padaf.preflight.ValidationException;
import net.padaf.preflight.ValidationResult;

import org.junit.Test;

public class TestPDFA1bValidator {

  @Test(expected = ValidationException.class)
  public void testIOExceptionOnJavaCCParser() throws ValidationException {
    DummyPdfaValidator totest = new DummyPdfaValidator() {
      public synchronized ValidationResult validate(DataSource source)
          throws ValidationException {
        return super.validate(new IODataSource());
      };
    };
    totest.validate(new FileDataSource(""));
  }

  private class IODataSource implements DataSource {

    /*
     * (non-Javadoc)
     * 
     * @see javax.activation.DataSource#getContentType()
     */
    public String getContentType() {
      return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.activation.DataSource#getInputStream()
     */
    public InputStream getInputStream() throws IOException {
      throw new IOException("Exception fo code coverage");
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.activation.DataSource#getName()
     */
    public String getName() {
      return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.activation.DataSource#getOutputStream()
     */
    public OutputStream getOutputStream() throws IOException {
      throw new IOException("Exception fo code coverage");
    }
  }

  private class DummyPdfaValidator extends PdfA1bValidator {
    public DummyPdfaValidator() throws ValidationException {
      super(PdfAValidatorFactory.getStandardPDFA1BConfiguration());
    }
  }
}
