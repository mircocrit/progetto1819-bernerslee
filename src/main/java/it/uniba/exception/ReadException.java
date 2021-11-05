package it.uniba.exception;
/**
 * Gestisce le eccezioni rilevabili durante la lettura dell'input
 * @author bernerslee1819
 *
 */
public class ReadException extends ParameterException {
	public ReadException() {
		super();
	}
	public ReadException(final String msg) {
		super(msg);
	}
}
