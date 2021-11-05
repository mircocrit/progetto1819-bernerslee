package it.uniba.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;

import com.google.cloud.bigquery.Job;

import it.uniba.exception.ConfigurationException;
import it.uniba.exception.ParameterException;
import it.uniba.sotorrent.GoogleDocsUtils;
import it.uniba.sotorrent.ISOQuery;
import it.uniba.sotorrent.SOQuery;
//import it.uniba.utils.Arguments;

/**Control.java ||Control||
 * This class controls the values stored into the table and select the correct query to run.
 * It also runs the query (with runEdge/runNode) and adds its result into an ArrayList variable.
 * Finally this class calls the GoogleDocuUtils.java class which writes the spreadsheet with the selected data.
 */
public class Control {
	/**
	 * selector to choose the right feature to run
	 * @param a		Input args String of the app
	 * @return void		No return needed
	 * @throws 	FileNotFoundException
	 * @throws	IOException		General I/O Exception
	 * @throws	InterruptedException		General Interruption Exception
	 * @throws	GeneralSecurityException 	General Security Exception
	 * @throws	URISyntaxException			URI Syntax Exception check your URI
	 */
	private void runNode(final Arguments args) throws FileNotFoundException, IOException, InterruptedException,
			GeneralSecurityException, URISyntaxException {
		ISOQuery soq = new SOQuery(); // preparing for executing
		ArrayList<String> res = new ArrayList<String>();
		if (args.empty()) {
			throw new ConfigurationException("Illegal arguments");
		}
		if (args.getWeight()) {
			throw new ConfigurationException("Cannot have node with weight");
		}
		if (!args.userEmpty()) {
			throw new ConfigurationException("You cannot have node specifing a specific user");
		}
		// control the value of table
		if (args.tableEmpty()) {
			throw new ConfigurationException("Illegal type.");
		}
		switch (args.getTable()) {
		case "question":
			if (!args.yearEmpty() && !args.monthEmpty() && !args.limitEmpty()) {
				if (args.tagEmpty()) { // is 1st
					if (!args.dayEmpty()) {
						Job job = soq.runNodeQuestionDate(args.getYear(),
								args.getMonth(), args.getDay(), args.getLimit());
						res = soq.getNode(job);
					} else {
						throw new ConfigurationException("Empty day exception");
					}

				} else {
					Job job = soq.runNodeQuestionTag(args.getYear(),
							args.getMonth(), args.getTag(), args.getLimit());
					res = soq.getNode(job);
				}
			} else {
				throw new ParameterException("Parameter exception.");
			}
			break;

		case "post":
			if (!args.yearEmpty() && !args.monthEmpty() && !args.limitEmpty()) {
				if (args.tagEmpty()) { // is 3rd
					if (!args.dayEmpty()) {
						Job job = soq.runNodePostDate(args.getYear(),
								args.getMonth(), args.getDay(), args.getLimit());
						res = soq.getNode(job);
					} else {
						throw new ConfigurationException("Empty day exception");
					}
				} else {
					Job job = soq.runNodePostTag(args.getYear(),
							args.getMonth(), args.getTag(), args.getLimit());
					res = soq.getNode(job);
				}
			} else {
				throw new ParameterException("Parameter exception.");
			}
			break;

		case "answer":
			if (!args.yearEmpty() && !args.monthEmpty() && !args.limitEmpty()) {
				if (args.tagEmpty()) { // is 2nd
					if (!args.dayEmpty()) {
						Job job = soq.runNodeAnswerDate(args.getYear(),
								args.getMonth(), args.getDay(), args.getLimit());
						res = soq.getNode(job);
					} else {
						throw new ConfigurationException("Empty day exception");
					}
				} else {
					Job job = soq.runNodeAnswerTag(args.getYear(),
							args.getMonth(), args.getTag(), args.getLimit());
					res = soq.getNode(job);
				}
			} else {
				throw new ParameterException("Parameter exception.");
			}
			break;
		default:
			throw new ConfigurationException("Illegal type");

		}
		if (!res.isEmpty()) {
			GoogleDocsUtils ut = new GoogleDocsUtils();
			String spid = ut.createSheet("Prova sna4so");
			ut.shareSheet(spid);
			ut.getSheetByTitle(spid);
			ut.writeNode(spid, res);
		}
	}

	private void runEdge(final Arguments a) throws FileNotFoundException, IOException, InterruptedException,
			GeneralSecurityException, URISyntaxException {
		ISOQuery soq = new SOQuery(); // preparing for executing
		ArrayList<Edge> res = new ArrayList<Edge>();
		if (a.empty()) {
			throw new ConfigurationException("Illegal arguments");
		}

		// controlli sulla configurazione in generale (non dipende dalla specifica query
		// che deve essere eseguita dai nastri
		if (!a.tagEmpty()) {
			throw new RuntimeException("You cannot have any edge requiring a specific tag");
		}
		if (a.limitEmpty()) {
			throw new RuntimeException("Limit not specified");
		}

		switch (a.getTable()) {
		case "question":
			if (a.userEmpty()) {
				if (a.dayEmpty() || a.monthEmpty() || a.yearEmpty()) {
					throw new ConfigurationException("Empty date");
				}
				// se non ho l'utente devo necessariamente avere la data
				if (!a.getWeight()) { // non ho peso
					// sto eseguendo la prima
					Job job = soq.runEdgeQuestionDate(a.getYear(),
							a.getMonth(), a.getDay(), a.getLimit());
					res = soq.getEdge(job);
				} else {
					// sto eseguendo la quarta
					Job job = soq.runEdgeQuestionDateWeight(a.getYear(),
							a.getMonth(), a.getDay(), a.getLimit());
					res = soq.getEdge(job);
				}
			} else {
				if (!a.dayEmpty() && !a.monthEmpty() && !a.yearEmpty()) {
					throw new ConfigurationException(
							"Cannot execute a request for an edge "
							+ "specifing a date an a user together");
				}
				if (!a.getWeight()) {
					// sto eseguendo la seconda
					Job job = soq.runEdgeQuestion(a.getUser(), a.getLimit());
					res = soq.getEdge(job);
				} else {
					// sto eseguendo la quinta
					Job job = soq.runEdgeQuestionWeight(a.getUser(), a.getLimit());
					res = soq.getEdge(job);
				}
			}
			break;
		case "answer":
			if (a.userEmpty() || (!a.dayEmpty() && !a.monthEmpty() && !a.yearEmpty())) {
				throw new ConfigurationException(
						"You can have edges from answer just passing "
						+ "an user and choosing if have weight or not");
			}
			if (a.getWeight()) {
				// sto eseguendo la sesta
				Job job = soq.runEdgeAnswerWeight(a.getUser(), a.getLimit());
				res = soq.getEdge(job);
			} else {
				// sto eseguendo la terza
				Job job = soq.runEdgeAnswer(a.getUser(), a.getLimit());
				res = soq.getEdge(job);
			}
			break;
		default:
			throw new ConfigurationException("Illegal type");
		}
		if (!res.isEmpty()) {
			GoogleDocsUtils ut = new GoogleDocsUtils();
			String spid = ut.createSheet("Prova sna4so");
			ut.shareSheet(spid);
			ut.getSheetByTitle(spid);
			ut.writeEdge(spid, res);
		}
	}

	/**
	 * Verifica che la query sia da eseguire sui nodi o sugli archi
	 * 
	 * @param a 		 					Input args String of the app
	 * @throws 	FileNotFoundException		File NotFound General Exception
	 * @throws  IOException					General I/O Exception
	 * @throws	InterruptedException		General Interruption Exception
	 * @throws	GeneralSecurityException 	General Security Exception
	 * @throws	URISyntaxException			URI Syntax Exception check your URI
	 */
	public final void execute(final String[] a) throws FileNotFoundException, IOException, InterruptedException,
			GeneralSecurityException, URISyntaxException {
		boolean ghost = false;
		try {
			if (a.length == 0) {
				throw new RuntimeException("You must have data passing arguments!");
			}
		} catch (RuntimeException e) {
			ghost = true;
			System.err.println(e.getMessage());

			// Percorso Fantasma (morirà in runEdge)

		}
		Arguments args = null; // will be surely take a value in the following if then else
		if (ghost) {
			String[] arrayGhost = {"type=answer", "user=1", "edge=yes", "limit=0" };
			args = new Arguments(arrayGhost);
		} else {
			args = new Arguments(a);
		}

		if (args.getEdge()) {
			runEdge(args);
		} else {
			runNode(args);
		}
	}

}
