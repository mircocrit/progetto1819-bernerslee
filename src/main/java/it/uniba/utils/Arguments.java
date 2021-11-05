package it.uniba.utils;

import it.uniba.exception.ReadException;

/** Arguments.java ||Entity||
 *  This class uses the string taken by an external class and stores the data into
 *  local variables, ready to be read with included getter methods.
 *  It includes data fields(yy-mm-dd), an user id, a limit field, a weight field,
 *  a tag field and an edge field.
 *  The constructor includes different patterns to distinguish the type of the string value
 *  and setting the relative value of the attribute.
 */
public class Arguments {

	// attributi
	private String table; // the table from which data must be taken
	private String tag;
	private int year = -1;
	private int month = -1;
	private int day = -1;
	private int limit = -1;
	private boolean weight = false;
	private boolean edge = false;
	private int user = -1;
	private static final int NUM3 = 3;
	/**
	 * return user
	 * @return the user
	 */
	public int getUser() {
		return user;
	}
	/**
	 *  return weight
	 *  @return weight
	*/
	public boolean getWeight() {
		return weight;
	}
	/**
	 * return edge
	 * @return edge
	 */
	public boolean getEdge() {
		return edge;
	}
	/**
	 * return year
	 * @return year
	 */
	public int getYear() {
		return year;
	}
	/**
	 * return month
	 * @return month
	 */
	public int getMonth() {
		return month;
	}
	/**
	 * return day
	 * @return day
	 */
	public int getDay() {
		return day;
	}
	/**
	 * return table of parameters
	 * @return table
	 */
	public String getTable() {
		return table;
	}
	/**
	 * return limit of rows
	 * @return limit
	*/
	public int getLimit() {
		return limit;
	}
	/**
	 * return tag
	 * @return tag
	*/
	public String getTag() {
		return tag;
	}
	/**
	 * return true if table is empty
	 * @return true else false
	*/
	public boolean empty() {
	return table == null && tag == null && year == -1
			&& month == -1 && day == -1 && limit == -1 && user == -1 && !weight;
	}
	/**
	 * @return true if year is empty
	 */
	public boolean yearEmpty() {
		return (year == -1);
	}
	/**
	 * @return true if moth is empty
	*/
	public boolean monthEmpty() {
		return (month == -1);
	}
	/**
	 * @return true if day is empty
	*/
	public boolean dayEmpty() {
		return (day == -1);
	}
	/**
	 * @return true if limit is empty
	*/
	public boolean limitEmpty() {
		return (limit == -1);
	}
	/**
	 * @return true if table is empty
	*/
	public boolean tableEmpty() {
		return (table == null);
	}
	/**
	 * @return true if tag is empty
	*/
	public boolean tagEmpty() {
		return (tag == null);
	}
	/**
	 * @return true if user is empty
	*/
	public boolean userEmpty() {
		return (user == -1);
	}

	private Arguments() { // rende impossibile dall'esterno la possibilità di istanziare
	// un oggetto di tipo arguments senza aver avvalorato gli attributi in modo corretto

	}

	public Arguments(final String[] input) {
		try {
			String patternType = "type=";
			String patternYear = "yyyy=";
			String patternMonth = "mm=";
			String patternDay = "dd=";
			String patternLimit = "limit=";
			String patternTag = "taglike=";
			String patternUser = "user=";
			String patternEdge = "edge=";
			String patternWeight = "weight=";

			// for each attribute find it in the array and evaluate the object
	for (int i = 0; i < input.length; i++) {
	if (input[i].regionMatches(0, patternType, 0, patternType.length())) {
		if (!tableEmpty()) {
		 throw new ReadException("Multiple type mentionated");
		}
		table = input[i].substring(patternType.length(), input[i].length());
		} else if (input[i].regionMatches(0, patternYear, 0, patternYear.length())) {
		if (!yearEmpty()) {
		 throw new ReadException("Multiple day mentionated");
		}
		 year = Integer.parseInt(input[i].substring(patternYear.length(), input[i].length()));
		} else if (input[i].regionMatches(0, patternMonth, 0, patternMonth.length())) {
		if (!monthEmpty()) {
		 throw new ReadException("Multiple month mentionated");
		}
		 month = Integer.parseInt(input[i].substring(patternMonth.length(), input[i].length()));
		} else if (input[i].regionMatches(0, patternDay, 0, patternDay.length())) {
		if (!dayEmpty()) {
		 throw new ReadException("Multiple day mentionated");
		}
		 day = Integer.parseInt(input[i].substring(patternDay.length(), input[i].length()));
		} else if (input[i].regionMatches(0, patternLimit, 0, patternDay.length())) {
		if (!limitEmpty()) {
		 throw new ReadException("Multiple limit mentionated");
		}
		 limit = Integer.parseInt(input[i].substring(patternLimit.length(), input[i].length()));
		} else if (input[i].regionMatches(0, patternTag, 0, patternTag.length())) {
		if (!tagEmpty()) {
		 throw new ReadException("Multiple tag mentionated");
		}
		 tag = input[i].substring(patternTag.length(), input[i].length());
		} else if (input[i].regionMatches(0, patternUser, 0, patternUser.length())) {
		if (!userEmpty()) {
		 throw new ReadException("Multiple user mentionated");
		}
		   user = Integer.parseInt(input[i].substring(patternUser.length(), input[i].length()));
		} else if (input[i].regionMatches(0, patternEdge, 0, patternEdge.length())) {
		if (input[i].substring(patternEdge.length(), input[i].length()).equals("yes")) {
		  edge = true;
		} else if (!input[i].substring(patternEdge.length(), input[i].length()).equals("no")) {
		  throw new ReadException("Invalid parameter for edge");
		}
		} else if (input[i].regionMatches(0, patternWeight, 0, patternWeight.length())) {
		if (input[i].substring(patternWeight.length(), input[i].length()).equals("yes")) {
			weight = true;
		} else if (!input[i].substring(patternWeight.length(), input[i].length()).equals("no")) {
						throw new ReadException("Invalid parameter for weight");
					}
				}
			}

		} catch (ReadException e) {
			System.err.println(e.getMessage());
			System.err.println("Check your command.");
			// Runtime.getRuntime().exit(0);
			//Percorso Fantasma (morirà in runEdge)
			//input = new String[4];
			input[0] = "type=answer";
			input[1] = "user=1";
			input[2] = "edge=yes";
			input[NUM3] = "limit=0";
		}
	}
}
