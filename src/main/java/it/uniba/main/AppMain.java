/** AppMain.java <<Boundary>>
 * The main class for the project.
 * This class executes the command passed in String args variable by calling the Control.java class.
 * It has been customized to meet the project assignment specifications.
 * 
 * <b>DO NOT RENAME</b>
 */

package it.uniba.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;

import it.uniba.exception.DeathJobException;
import it.uniba.exception.ParameterException;
import it.uniba.utils.Control;

/**
 * This is the class where is written the application
 * 
 * @author bernerslee1819
 */
public final class AppMain {

	/**
	 * Private constructor. Change if needed.
	 */
	private AppMain() {

	}

	/**
	 * * This is the main entry of the application.
	 *
	 * @param args The command-line arguments.
	 * @throws FileNotFoundException    See stack trace for proper location.
	 * @throws IOException              See stack trace for proper location.
	 * @throws InterruptedException     See stack trace for proper location.
	 * @throws GeneralSecurityException See stack trace for proper location.
	 * @throws URISyntaxException       See stack trace for proper location.
	 */
	public static void main(final String[] args) throws FileNotFoundException, IOException, InterruptedException,
			GeneralSecurityException, URISyntaxException {
		System.out.println("Current working dir: " + System.getProperty("user.dir"));
		try {
			Control c = new Control();
			c.execute(args);
		} catch (GoogleJsonResponseException e) {
			System.err.println("Errore. Aspettare 100 sec. e rieseguire il comando");
		} catch (DeathJobException e2) {
			System.err.println(e2.getMessage());
		} catch (ParameterException e3) {
			System.err.println(e3.getMessage());
		} catch (RuntimeException e) {
			System.err.println(e.getMessage());
		}

	}

}
