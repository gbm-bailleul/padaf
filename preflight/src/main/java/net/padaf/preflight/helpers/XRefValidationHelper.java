/**
 * 
 */
package net.padaf.preflight.helpers;

import java.util.ArrayList;
import java.util.List;

import net.padaf.preflight.DocumentHandler;
import net.padaf.preflight.ValidationConstants;
import net.padaf.preflight.ValidationException;
import net.padaf.preflight.ValidatorConfig;
import net.padaf.preflight.ValidationResult.ValidationError;

import org.apache.pdfbox.cos.COSDocument;

/**
 * Check if the number of inderect objects is less great than ValidationConstant.MAX_INDIRECT_OBJ.
 */
public class XRefValidationHelper extends AbstractValidationHelper {

	/**
	 * @param cfg
	 * @throws ValidationException
	 */
	public XRefValidationHelper(ValidatorConfig cfg) throws ValidationException {
		super(cfg);
	}

	/* (non-Javadoc)
	 * @see net.padaf.preflight.helpers.AbstractValidationHelper#innerValidate(net.padaf.preflight.DocumentHandler)
	 */
	@Override
	public List<ValidationError> innerValidate(DocumentHandler handler)
	throws ValidationException {
		List<ValidationError> errors = new ArrayList<ValidationError>();
		COSDocument document = handler.getDocument().getDocument();
		if ( document.getObjects().size() > ValidationConstants.MAX_INDIRECT_OBJ ) {
			errors.add(new ValidationError(ERROR_SYNTAX_INDIRECT_OBJ_RANGE, "Too many indirect objects"));
		}
		return errors;
	}

}
 