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
package net.padaf.preflight.font;

import java.util.List;

import net.padaf.preflight.ValidationException;
import net.padaf.preflight.ValidationResult.ValidationError;
import net.padaf.preflight.font.AbstractFontContainer.State;

public interface FontValidator {

  /**
   * Call this method to validate the font wrapped by this interface
   * implementation. Return true if the validation succeed, false otherwise. If
   * the validation failed, the error is updated in the FontContainer with the
   * right error code.
   * 
   * @return
   */
  public abstract boolean validate() throws ValidationException;

  /**
   * Return the State of the Font Validation. Three values are possible :
   * <UL>
   * <li>VALID : there are no errors
   * <li>MAYBE : Metrics aren't consistent of the FontProgram isn't embedded,
   * but it can be valid.
   * <li>INVALID : the validation fails
   * </UL>
   * 
   * @return
   */
  public State getState();

  /**
   * Return all validation errors.
   * 
   * @return
   */
  public List<ValidationError> getValdiationErrors();
}
