package net.padaf.preflight.utils;

import java.util.List;

import net.padaf.preflight.ValidationResult.ValidationError;

import static net.padaf.preflight.ValidationConstants.*;

public class FilterHelper {

	/**
	 * This method checks if the filter is authorized for a PDF/A file.
	 * According to the PDF/A-1 specification, only the LZW filter is forbidden due to
	 * Copyright compatibility. Because of the PDF/A is based on the PDF1.4 specification, 
	 * all filters that aren't declared in the PDF Reference Third Edition are rejected. 
	 * 
	 * @param filter the filter to checks
	 * @param errors the list of validation errors
	 * @return true if the filter is authorized, false otherwise.
	 */
	public static boolean isAuthorizedFilter(String filter, List<ValidationError> errors) {
		String errorCode = isAuthorizedFilter(filter);
		if (errorCode != null) {
			// --- LZW is forbidden.
			if ( ERROR_SYNTAX_STREAM_INVALID_FILTER.equals(errorCode) ) {
				errors.add(new ValidationError(ERROR_SYNTAX_STREAM_INVALID_FILTER, "LZWDecode is forbidden"));
				return false;
			} else {
				errors.add(new ValidationError(ERROR_SYNTAX_STREAM_UNDEFINED_FILTER, "This filter isn't defined in the PDF Reference Third Edition."));
				return false;				
			}
		}
		return true;
	}

	/**
	 * This method checks if the filter is authorized for a PDF/A file.
	 * According to the PDF/A-1 specification, only the LZW filter is forbidden due to
	 * Copyright compatibility. Because of the PDF/A is based on the PDF1.4 specification, 
	 * all filters that aren't declared in the PDF Reference Third Edition are rejected. 
	 * 
	 * @param filter
	 * @return null if validation succeed, the errorCode if the validation failed
	 */
	public static String isAuthorizedFilter(String filter) {
		if (filter != null) {
			// --- LZW is forbidden.
			if (STREAM_DICTIONARY_VALUE_FILTER_LZW.equals(filter) || INLINE_DICTIONARY_VALUE_FILTER_LZW.equals(filter) ) {
				return ERROR_SYNTAX_STREAM_INVALID_FILTER;
			}

			// --- Filters declared in the PDF Reference for PDF 1.4
			// --- Other Filters are considered as invalid to avoid not consistent behaviour
			boolean definedFilter = STREAM_DICTIONARY_VALUE_FILTER_FLATE_DECODE.equals(filter);
			definedFilter = definedFilter || STREAM_DICTIONARY_VALUE_FILTER_ASCII_HEX.equals(filter);
			definedFilter = definedFilter || STREAM_DICTIONARY_VALUE_FILTER_ASCII_85.equals(filter);
			definedFilter = definedFilter || STREAM_DICTIONARY_VALUE_FILTER_CCITTFF.equals(filter);
			definedFilter = definedFilter || STREAM_DICTIONARY_VALUE_FILTER_DCT.equals(filter);
			definedFilter = definedFilter || STREAM_DICTIONARY_VALUE_FILTER_JBIG.equals(filter);
			definedFilter = definedFilter || STREAM_DICTIONARY_VALUE_FILTER_RUN.equals(filter);

			definedFilter = definedFilter || INLINE_DICTIONARY_VALUE_FILTER_FLATE_DECODE.equals(filter);
			definedFilter = definedFilter || INLINE_DICTIONARY_VALUE_FILTER_ASCII_HEX.equals(filter);
			definedFilter = definedFilter || INLINE_DICTIONARY_VALUE_FILTER_ASCII_85.equals(filter);
			definedFilter = definedFilter || INLINE_DICTIONARY_VALUE_FILTER_CCITTFF.equals(filter);
			definedFilter = definedFilter || INLINE_DICTIONARY_VALUE_FILTER_DCT.equals(filter);
			definedFilter = definedFilter || INLINE_DICTIONARY_VALUE_FILTER_RUN.equals(filter);
			
			if (!definedFilter) {
				return ERROR_SYNTAX_STREAM_UNDEFINED_FILTER;
			}
		}
		return null;
	}
}
