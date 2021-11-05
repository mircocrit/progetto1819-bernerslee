package it.uniba.exception;

/**
 * Gestisce le eccezioni che riguardano le configurazioni di input errate
 * @author bernerslee1819 */
public class ConfigurationException extends ParameterException {
	public  ConfigurationException() {
		super();
	//
	}
	public ConfigurationException(final String msg) {
		super(msg);
	}
}
