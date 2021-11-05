package it.uniba.exception;

/**
 * Gestisce le eccezioni riguardanti gli input non validi
 * @author bernerslee1819
 * */
public class ParameterException extends RuntimeException {
	public ParameterException() {
		super();

	}
	public ParameterException(final String msg) {
		super(msg);
	}
}
