package it.poste.patrimonio.rs.api.exception;

public class ErrorCodes {

	private ErrorCodes() {

	}

	public static final String GENERIC_ERROR = "GENERIC_ERROR";
	public static final String INVALID_ARGUMENT = "INVALID_ARGUMENT";
	public static final String MISSING_ARGUMENT = "MISSING_ARGUMENT";
	public static final String INVALID_ARGUMENT_DESC = "Invalid argument in request";
	public static final String MISSING_ARGUMENT_DESC = "Missing argument in request";
	public static final String RESOURCE_NOT_FOUND = "RESOURCE_NOT_FOUND";
	public static final String RESOURCE_NOT_FOUND_DESC = "Requested NDG not found";
	public static final String FUNCTIONAL_ERROR = "FUNCTIONAL_ERROR";
	public static final String UNAUTHORIZED = "UNAUTHORIZED";
	public static final String UNAUTHORIZED_DESC = "Unauthorized";
}
