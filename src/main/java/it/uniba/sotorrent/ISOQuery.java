package it.uniba.sotorrent;

import com.google.cloud.bigquery.JobException;

import it.uniba.utils.Edge;

import java.util.ArrayList;
import java.util.Map;

import com.google.cloud.bigquery.Job;
/** ISOQuery.java ||Boundary||
 * Interface for running a query on Stack Overflow via Google's BigQuery service.
 * It needs specific query execute methods and implementations, and getter methods.
 * Returns must be Job types.
 */
public interface ISOQuery {
	/**
	 * Starts the query.
	 * @return The job for the query.
	 * @throws InterruptedException Raised on timeouts.
	 */
	Job runQuery() throws InterruptedException;
	/**	//Faiail
	 * finds out the first 100 users which has made a question in a specific date, ordered cresc.
	 * @param year of post
	 * @param month of post
	 * @param day of post
	 * @param limit of users
	 * @return The job for the query.
	 * @throws InterruptedException exception
	 */
	Job runNodeQuestionDate(int year,  int month,  int day, int limit)throws InterruptedException;
	/**	//mircocrit
	 * finds out the first 100 users who answered a question in a specific date, in asc. order.
	 * @param year of post
	 * @param month of post
	 * @param day of post
	 * @param limit of users
	 * @return The job for the query.
	 * @throws InterruptedException exception
	 */
	Job runNodeAnswerDate(int year,  int month,  int day, int limit)throws InterruptedException;
	/**	//mpia3
	 * finds out the first 100 users who has made a post(answers or questions) in a specific date, in asc. order.
	 * @param year of post
	 * @param month of post
	 * @param day of post
	 * @param limit of users
	 * @return The job for the query.
	 * @throws InterruptedException exception
	 */
	Job runNodePostDate(int year,  int month,  int day, int limit)throws InterruptedException;
	/**	//Chrismic
	 * finds out the first 100 users (User) which has made a question (Question) in a specific date using an
	 *  argument (Tag), ordered cresc.
	 * @param year of post
	 * @param month of post
	 * @param tag of post
	 * @param limit of users
	 * @return The job for the query.
	 * @throws InterruptedException exception
	 */
	Job runNodeQuestionTag(int year,  int month,  String tag, int limit)throws InterruptedException;
	/**
	 * finds out the first 100 users (User) which has made an answer (Answer) in
	 * a specific date using an argument (Tag), ordered cresc.
	 * @param year of post
	 * @param month of post
	 * @param tag of post
	 * @param limit of users
	 * @return The job for the query.
	 * @throws InterruptedException exception
	 */
	Job runNodeAnswerTag(int year, int month, String tag, int limit) throws InterruptedException;
	/**
	 * finds out the first 100 users (User) which has made a post (a post could be a question or an answer)
	 * in a specific date using an argument (Tag), ordered cresc.
	 * @param year of post
	 * @param month of post
	 * @param tag of post
	 * @param limit of post
	 * @return The job for the query.
	 * @throws InterruptedException exception
	 */
	Job runNodePostTag(int year, int month, String tag, int limit) throws InterruptedException;
	/**	//prrid, RuggieroPedico
	 * View the list of the first 100 couples (from, to) related to
	 * questions (Question) asked in a specific date, ordered cresc.
	 * @param year of post
	 * @param month of post
	 * @param day of post
	 * @param limit of users
	 * @return The job for the query
	 * @throws InterruptedException exception
	 */
	Job runEdgeQuestionDate(int year, int month, int day, int limit)
			throws InterruptedException;
	/**	//Chrism1c
	 * View the list of the first 100 couples (from, to) related to questions (Question) asked by a specific user.
	 * @param user of post
	 * @param limit of users
	 * @return The job for the query
	 * @throws InterruptedException exception
	 */
	Job runEdgeQuestion(int user, int limit) throws InterruptedException; /**
	 * View the list of the first 100 couples (from, to) related to answers (Answer) written by a specific user.
	 * @param user of post
	 * @param limit of users
	 * @return The job for the query
	 * @throws InterruptedException exception
	 */
	Job runEdgeAnswer(int user, int limit) throws InterruptedException; /**
	 * View the list of the first 100 triples (from, to, weight) related to questions
	 * (Question) asked by a specific user.
	 * @param user of post
	 * @param limit of users
	 * @return The job for the query
	 * @throws InterruptedException exception
	 */
	Job runEdgeQuestionWeight(int user, int limit) throws InterruptedException; /**
	 * View the list of the first 100 triples (from, to, weight) related to answers
	 * (Answer) written by a specific user.
	 * @param user of post
	 * @param limit of users
	 * @return The job for the query
	 * @throws InterruptedException exception
	 */
	Job runEdgeAnswerWeight(int user, int limit) throws InterruptedException; /**
	 * View the list of the first 100 triples (from, to, weight) related to
	 * questions (Question) asked in a specific date, ordered cresc.
	 * @param year of post
	 * @param month of post
	 * @param day of post
	 * @param limit of users
	 * @return The job for the query
	 * @throws InterruptedException exception
	 */
	Job runEdgeQuestionDateWeight(int year, int month, int day, int limit) throws InterruptedException; /**
	 * Returns the results from the query job.
	 * @param job The job associated to the query.
	 * @return Results as a hash map, with URL as key and view count as value.
	 * @throws JobException Generic error occurred.
	 * @throws InterruptedException Raised on timeouts.
	 */
	Map<String, Long> getResults(Job job) throws JobException, InterruptedException; /**
	 * Returns the results from the query job (Node).
	 * @param query The job associated to the query.
	 * @return Results as a ArrayList of Strings.
	 * @throws JobException Generic error occurred.
	 * @throws InterruptedException Raised on timeouts.
	 */
	ArrayList<String> getNode(Job query) throws JobException, InterruptedException; /**
	 * Returns the results from the query job (Edge).
	 * @param query The job associated to the query.
	 * @return Results as a ArrayList of Edges.
	 * @throws JobException Generic error occurred.
	 * @throws InterruptedException Raised on timeouts.
	 */
	ArrayList<Edge> getEdge(Job query) throws JobException, InterruptedException;
	}
