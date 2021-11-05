package it.uniba.exception;

/**
 * Gestisce le eccezioni che riguardano il non funzionameto dei metodi
 * @author bernerslee1819
 * */
public class DeathJobException extends RuntimeException {

	public DeathJobException() {
		super();
	}
	public DeathJobException(final String msg) {
		super(msg);
	}
}
