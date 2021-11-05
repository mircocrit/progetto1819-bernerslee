package it.uniba.sotorrent;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.BigQueryOptions;
import com.google.cloud.bigquery.FieldValueList;
import com.google.cloud.bigquery.Job;
import com.google.cloud.bigquery.JobException;
import com.google.cloud.bigquery.JobId;
import com.google.cloud.bigquery.JobInfo;
import com.google.cloud.bigquery.QueryJobConfiguration;
import com.google.cloud.bigquery.TableResult;
import it.uniba.exception.DeathJobException;
import it.uniba.exception.ParameterException;
import it.uniba.utils.Edge;
import it.uniba.utils.MathUtils;

/**SOQuery.java ||Boundary||
 *  This class contains Node and Edge queries, required to get the results from google Big Query service.
 *  Each method returns a Job, from which we can extract the result by the column labels,
 *  using getter methods included in the class.
 *
 * @author bernerslee1819
 */

public final class SOQuery implements ISOQuery {
	/**
	 * Instance of BigQuery service.
	 */
	private BigQuery bigquery;
	/**
	 * URL of credentials JSON file.
	 */
	private static final String URL = "http://neo.di.uniba.it/credentials/project-bernerslee-vvb56d.json";

	/**
	 * Default constructor, instantiates BigQuery API service.
	 * 
	 * @throws FileNotFoundException The remote JSON file with credential is 404.
	 * @throws IOException           Malformed JSON file.
	 */
	public SOQuery() throws FileNotFoundException, IOException {
		bigquery = BigQueryOptions.newBuilder().setProjectId("perfect-chalice-237409")
				.setCredentials(ServiceAccountCredentials.fromStream(
						new URL(URL).openStream())).build().getService();
	}

	@Override
	public Job runQuery() throws InterruptedException {

			// Use standard SQL syntax for queries.
			// See: https://cloud.google.com/bigquery/sql-reference/
			QueryJobConfiguration queryConfig = QueryJobConfiguration
					.newBuilder("SELECT " + "CONCAT('https://stackoverflow.com/questions/', "
							+ "CAST(id as STRING)) as url, " + "view_count "
							+ "FROM `bigquery-public-data.stackoverflow.posts_questions` "
							+ "WHERE tags like '%google-bigquery%' "
							+ "ORDER BY favorite_count DESC LIMIT 10")
					.setUseLegacySql(false).build();

			// Create a job ID so that we can safely retry.
			JobId jobId = JobId.of(UUID.randomUUID().toString());
			Job queryJob = bigquery.create(JobInfo.newBuilder(queryConfig).setJobId(jobId).build());

			// Wait for the query to complete.
			queryJob = queryJob.waitFor();

			// Check for errors
			if (queryJob == null) {
				throw new DeathJobException("Job no longer exists (runQuery)");
			} else if (queryJob.getStatus().getError() != null) {
				// You can also look at queryJob.getStatus().getExecutionErrors() for all
				// errors, not just the latest one.
				throw new RuntimeException(queryJob.getStatus().getError().toString());
			}
			return queryJob;
	}

	@Override
	public Map<String, Long> getResults(final Job queryJob) throws JobException, InterruptedException {
		Map<String, Long> results = new HashMap<String, Long>();

		if (queryJob != null) {
			TableResult result = queryJob.getQueryResults();
			// Print all pages of the results.
			for (FieldValueList row : result.iterateAll()) {
				String keyUrl = row.get("url").getStringValue();
				long viewCount = row.get("view_count").getLongValue();
				System.out.printf("url: %s views: %d%n", keyUrl, viewCount);
				results.put(keyUrl, viewCount);
			}
		}
		return results;
	}

	/*
	 * Visualizzare la lista dei primi 100 id utente (User) che hanno fatto almeno
	 * una domanda (Question) in un dato anno, mese e giorno
	 */
	public Job runNodeQuestionDate(final int year, final int month, final int day, final int limit)
			throws InterruptedException {
		try {
			MathUtils help = new MathUtils();
			if (help.isData(year, month, day) && limit > 0) { // date is made in a good way
				QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder("SELECT DISTINCT "
						+ "owner_user_id "
						+ "FROM `bigquery-public-data.stackoverflow.posts_questions` " + "WHERE"
						+ " extract(year from creation_date)=" + year
						+ " and " + "extract(month from creation_date)="
						+ month + " and " + "extract(day from creation_date)=" + day + " and "
						+ "owner_user_id IS NOT NULL" + " ORDER BY owner_user_id"
						+ " LIMIT " + limit)
						.setUseLegacySql(false).build();

				JobId jobId = JobId.of(UUID.randomUUID().toString());
				Job queryJob = bigquery.create(JobInfo.newBuilder(queryConfig).setJobId(jobId).build());
				queryJob = queryJob.waitFor();
				if (queryJob == null) {
					throw new DeathJobException("Job no longer exists (runNodeQuestionDate)");
				} else if (queryJob.getStatus().getError() != null) {
					// You can also look at queryJob.getStatus().getExecutionErrors() for all
					// errors, not just the latest one.
					throw new RuntimeException(queryJob.getStatus().getError().toString());
				}
				return queryJob;
			} else {
				throw new ParameterException("Parameters exception (runNodeQuestionDate)");
			}
		} catch (ParameterException e) {
			System.err.println(e.getMessage());
			// Runtime.getRuntime().exit(0);
		}
		return null;
	}

	/*
	 * Visualizzare la lista dei primi 100 id utente (User) che hanno dato almeno
	 * una risposta (Answer) in un dato anno, mese e giorno.
	 */

	public Job runNodeAnswerDate(final int year, final int month, final int day, final int limit)
			throws InterruptedException {
		try {
			MathUtils help = new MathUtils();
			if (help.isData(year, month, day)) { // date is made in a good way
				QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder("SELECT DISTINCT "
						+ "owner_user_id "
						+ "FROM `bigquery-public-data.stackoverflow.posts_answers` " + "WHERE "
						+ "extract(year from creation_date)=" + year
						+ " and " + "extract(month from creation_date)="
						+ month + " and " + "extract(day from creation_date)=" + day + " and "
						+ "owner_user_id IS NOT NULL"
						+ " ORDER BY owner_user_id" + " LIMIT " + limit)
						.setUseLegacySql(false).build();

				JobId jobId = JobId.of(UUID.randomUUID().toString());
				Job queryJob = bigquery.create(JobInfo.newBuilder(queryConfig).setJobId(jobId).build());

				queryJob = queryJob.waitFor();

				if (queryJob == null) {
					throw new DeathJobException("Job no longer exists (runNodeAnswerDate)");
				} else if (queryJob.getStatus().getError() != null) {
					// You can also look at queryJob.getStatus().getExecutionErrors() for all
					// errors, not just the latest one.
					throw new RuntimeException(queryJob.getStatus().getError().toString());

				}
				return queryJob;
			} else {
				throw new ParameterException("Parameters exception (runNodeAnswerDate)");
			}
		} catch (ParameterException e) {
			System.err.println(e.getMessage());
			}
		return null;
	}

	/*
	 * Visualizzare la lista dei primi 100 id utente (User) che hanno fatto almeno
	 * un Post in un dato mese, anno, giorno (un Post può essere una domanda o una
	 * risposta).
	 */

	public Job runNodePostDate(final int year, final int month, final int day, final int limit)
			throws InterruptedException {
		try {
			MathUtils help = new MathUtils();
			if (help.isData(year, month, day)) { // date is made in a good way
				QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder("SELECT DISTINCT "
						+ "owner_user_id "
						+ "FROM `bigquery-public-data.stackoverflow.posts_*` " + "WHERE "
						+ "extract(year from creation_date)=" + year + " and "
						+ "extract(month from creation_date)="
						+ month + " and " + "extract(day from creation_date)=" + day + " and "
						+ "owner_user_id IS NOT NULL and owner_user_id >= 0 "
						+ " ORDER BY owner_user_id" + " LIMIT "
						+ limit).setUseLegacySql(false).build();

				JobId jobId = JobId.of(UUID.randomUUID().toString());
				Job queryJob = bigquery.create(JobInfo.newBuilder(queryConfig).setJobId(jobId).build());

				queryJob = queryJob.waitFor();

				if (queryJob == null) {
					throw new DeathJobException("Job no longer exists (runNodePostDate)");
				} else if (queryJob.getStatus().getError() != null) {
					// You can also look at queryJob.getStatus().getExecutionErrors() for all
					// errors, not just the latest one.
					throw new RuntimeException(queryJob.getStatus().getError().toString());

				}
				return queryJob;
			} else {
				throw new ParameterException("Parameters exception (runNodePostDate)");
			}
		} catch (ParameterException e) {
			System.err.println(e.getMessage());
			}
		return null;
	}

	/*
	 * Visualizzare la lista dei primi 100 id utente (User) che hanno fatto almeno
	 * una domanda (Question) su un dato argomento (Tag) in un dato mese e anno.
	 */

	public Job runNodeQuestionTag(final int year, final int month, final String tag, final int limit)
			throws InterruptedException {
		try {
			MathUtils help = new MathUtils();
			if (help.isDataNoDay(year, month)) { // date is made in a good way
				QueryJobConfiguration queryConfig = QueryJobConfiguration
						.newBuilder("SELECT DISTINCT " + "owner_user_id FROM "
								+ "`bigquery-public-data.stackoverflow.posts_questions`"
								+ " WHERE "
								+ "extract(year from creation_date)=" + year + " and "
								+ "extract(month from creation_date)=" + month
								+ " and " + " tags like '%" + tag + "%' "
								+ " and " + "owner_user_id IS NOT NULL"
								+ " ORDER BY owner_user_id" + " LIMIT " + limit)
						.setUseLegacySql(false).build();

				JobId jobId = JobId.of(UUID.randomUUID().toString());
				Job queryJob = bigquery.create(JobInfo.newBuilder(queryConfig).setJobId(jobId).build());

				queryJob = queryJob.waitFor();

				if (queryJob == null) {
					throw new DeathJobException("Job no longer exists (runNodeQuestionTag)");
				} else if (queryJob.getStatus().getError() != null) {
					// You can also look at queryJob.getStatus().getExecutionErrors() for all
					// errors, not just the latest one.
					throw new RuntimeException(queryJob.getStatus().getError().toString());
				}
				return queryJob;
			} else {
				throw new ParameterException("Parameters exception (runNodeQuestionTag)");
			}
		} catch (ParameterException e) {
			System.err.println(e.getMessage());
			}
		return null;
	}

	/*
	 * Visualizzare la lista dei primi 100 id utente (User) che hanno dato almeno
	 * una risposta (Answer) su un dato argomento (Tag) in un dato mese e anno
	 */
	public Job runNodeAnswerTag(final int year, final int month, final String tag, final int limit)
			throws InterruptedException {
		try {
			MathUtils help = new MathUtils();
			if (help.isDataNoDay(year, month)) { // date is made in a good way
				QueryJobConfiguration queryConfig = QueryJobConfiguration
						.newBuilder("SELECT DISTINCT " + "a.owner_user_id FROM "
								+ "`bigquery-public-data.stackoverflow.posts_answers`"
								+ " a " + "join "
								+ "`bigquery-public-data.stackoverflow.posts_questions`"
								+ " q on a.parent_id = q.id "
								+ "WHERE q.tags like '%" + tag + "%' " + "and "
								+ "extract(year from a.creation_date)=" + year + " and "
								+ "extract(month from a.creation_date)=" + month
								+ " and a.owner_user_id IS NOT NULL"
								+ " ORDER BY a.owner_user_id " + " LIMIT " + limit)
						.setUseLegacySql(false).build();

				JobId jobId = JobId.of(UUID.randomUUID().toString());
				Job queryJob = bigquery.create(JobInfo.newBuilder(queryConfig).setJobId(jobId).build());

				queryJob = queryJob.waitFor();

				if (queryJob == null) {
					throw new DeathJobException("Job no longer exists (runNodeAnswerTag)");
				} else if (queryJob.getStatus().getError() != null) {
					// You can also look at queryJob.getStatus().getExecutionErrors() for all
					// errors, not just the latest one.
					throw new RuntimeException(queryJob.getStatus().getError().toString());
				}
				return queryJob;
			} else {
				throw new ParameterException("Parameters exception (runNodeAnswerTag)");
			}
		}  catch (ParameterException e) {
			System.err.println(e.getMessage());
			}
		return null;
	}

	/*
	 * Visualizzare la lista dei primi 100 id utente (User) che hanno fatto almeno
	 * un Post su un dato argomento (Tag) in un dato mese e anno (un Post può
	 * essere una domanda o una risposta)
	 */

	public Job runNodePostTag(final int year, final int month, final String tag, final int limit)
			throws InterruptedException {
		try {
			MathUtils help = new MathUtils();
			if (help.isDataNoDay(year, month)) { // date is made in a good way
				QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder("SELECT DISTINCT "
						+ "owner_user_id "
						+ "FROM `bigquery-public-data.stackoverflow.posts_questions` " + "WHERE"
						+ " extract(year from creation_date)=" + year + " and "
						+ "extract(month from creation_date)="
						+ month + " and " + " tags like '%" + tag + "%' " + " and "
						+ "owner_user_id IS NOT NULL "
						+ "UNION DISTINCT " + "(SELECT DISTINCT " + "a.owner_user_id "
						+ "FROM `bigquery-public-data.stackoverflow.posts_answers` a " + "join "
						+ "`bigquery-public-data.stackoverflow.posts_questions` q "
						+ "on a.parent_id = q.id "
						+ "WHERE q.tags like '%" + tag + "%' " + "and "
						+ "extract(year from a.creation_date)=" + year
						+ " and " + "extract(month from a.creation_date)=" + month + " and "
						+ "a.owner_user_id IS NOT NULL)"

						+ " ORDER BY owner_user_id " + " LIMIT "
						+ limit).setUseLegacySql(false).build();

				JobId jobId = JobId.of(UUID.randomUUID().toString());
				Job queryJob = bigquery.create(JobInfo.newBuilder(queryConfig).setJobId(jobId).build());

				queryJob = queryJob.waitFor();

				if (queryJob == null) {
					throw new DeathJobException("Job no longer exists (runNodePostTag)");
				} else if (queryJob.getStatus().getError() != null) {
					// You can also look at queryJob.getStatus().getExecutionErrors() for all
					// errors, not just the latest one.
					throw new RuntimeException(queryJob.getStatus().getError().toString());
				}
				return queryJob;
			} else {
				throw new ParameterException("Parameters exception (runNodePostTag)");
			}
		} catch (ParameterException e) {
			System.err.println(e.getMessage());
			}
		return null;
	}

	/*
	 * Restituisce il risultato della query calcolata dal metodo in un oggetto di
	 * tipo Node
	 */
	public ArrayList<String> getNode(final Job query) throws JobException, InterruptedException {
		ArrayList<String> out = new ArrayList<String>();

		if (query != null) {
			TableResult res = query.getQueryResults();
			for (FieldValueList row : res.iterateAll()) {
				String in = row.get("owner_user_id").getStringValue();
				out.add(in);
			}
		}
		return out;
	}

	/*
	 * Visualizzare la lista delle prime 100 triple (from, to, weight) relative a
	 * domande (Question) poste da un determinato utente.
	 */
	public Job runEdgeQuestionWeight(final int user, final int limit) throws InterruptedException {

			QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder(
					"SELECT DISTINCT a.owner_user_id as us_from, q.owner_user_id as us_to, "
							+ "COUNT(*) as weight "
							+ "FROM `bigquery-public-data.stackoverflow.posts_answers` a"
							+ "  join `bigquery-public-data.stackoverflow.posts_questions` "
							+ "q on a.parent_id = q.id "
							+ "where q.owner_user_id=" + user + " and "
							+ "q.owner_user_id IS NOT NULL and a.owner_user_id IS NOT NULL "
							+ "GROUP BY a.owner_user_id, q.owner_user_id "
							+ "ORDER BY us_from, us_to " + "LIMIT "
							+ limit)
					.setUseLegacySql(false).build();

			JobId jobId = JobId.of(UUID.randomUUID().toString());
			Job queryJob = bigquery.create(JobInfo.newBuilder(queryConfig).setJobId(jobId).build());

			queryJob = queryJob.waitFor();

			if (queryJob == null) {
				throw new DeathJobException("Job no longer exists (runEdgeQuestionWeight)");
			} else if (queryJob.getStatus().getError() != null) {
				// You can also look at queryJob.getStatus().getExecutionErrors() for all
				// errors, not just the latest one.
				throw new RuntimeException(queryJob.getStatus().getError().toString());
			}
			return queryJob;
	}

	/*
	 * Visualizzare la lista delle prime 100 triple (from, to, weight) relative a
	 * risposte (Answer) date da un determinato utente.
	 */
	public Job runEdgeAnswerWeight(final int userId, final int limit) throws InterruptedException {

			QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder("select distinct "
					+ "a.owner_user_id as us_from, " + "q.owner_user_id as us_to, "
					+ "count(*) as weight "
					+ "from `bigquery-public-data.stackoverflow.posts_answers` a " + "join "
					+ "`bigquery-public-data.stackoverflow.posts_questions` q "
					+ "on a.parent_id=q.id "
					+ "where a.owner_user_id=" + userId
					+ " and q.owner_user_id is not null and "
					+ "a.owner_user_id is not null "
					+ "group by a.owner_user_id, q.owner_user_id "
					+ "order by us_from, us_to " + "limit " + limit)
					.setUseLegacySql(false).build();
			JobId jobId = JobId.of(UUID.randomUUID().toString());
			Job queryJob = bigquery.create(JobInfo.newBuilder(queryConfig).setJobId(jobId).build());

			queryJob = queryJob.waitFor();

			if (queryJob == null) {
				throw new DeathJobException("Job no longer exists (runEdgeAnswerWeight)");
			} else if (queryJob.getStatus().getError() != null) {
				// You can also look at queryJob.getStatus().getExecutionErrors() for all
				// errors, not just the latest one.
				throw new RuntimeException(queryJob.getStatus().getError().toString());

			}
			return queryJob;
	}

	/*
	 * Visualizzare la lista delle prime 100 triple (from, to) relative a domande
	 * (Question) poste da un determinato utente.
	 */
	public Job runEdgeQuestion(final int userId, final int limit) throws InterruptedException {
			QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder("select distinct "
					+ "a.owner_user_id as us_from, " + "q.owner_user_id as us_to "
					+ "from `bigquery-public-data.stackoverflow.posts_answers` a " + "join "
					+ "`bigquery-public-data.stackoverflow.posts_questions` q "
					+ "on a.parent_id=q.id "
					+ "where q.owner_user_id=" + userId + " and a.owner_user_id is not null "
					+ "group by a.owner_user_id, q.owner_user_id "
					+ "order by us_from, us_to " + "limit " + limit)
					.setUseLegacySql(false).build();
			JobId jobId = JobId.of(UUID.randomUUID().toString());
			Job queryJob = bigquery.create(JobInfo.newBuilder(queryConfig).setJobId(jobId).build());

			queryJob = queryJob.waitFor();

			if (queryJob == null) {
				throw new DeathJobException("Job no longer exists (runEdgeQuestion)");
			} else if (queryJob.getStatus().getError() != null) {
				// You can also look at queryJob.getStatus().getExecutionErrors() for all
				// errors, not just the latest one.
				throw new RuntimeException(queryJob.getStatus().getError().toString());

			}
			return queryJob;
	}

	/*
	 * Visualizzare la lista delle prime 100 coppie (from, to) relative a domande
	 * (Question) poste in un dato anno, mese e giorno.
	 */
	public Job runEdgeQuestionDate(final int year, final int month, final int day, final int limit)
			throws InterruptedException {
		try {
			MathUtils help = new MathUtils();
			if (help.isData(year, month, day)) {
				QueryJobConfiguration queryConfig = QueryJobConfiguration
						.newBuilder("select distinct " + "a.owner_user_id as us_from, "
								+ "q.owner_user_id as us_to from "
								+ "`bigquery-public-data.stackoverflow.posts_answers`"
								+ " a join "
								+ "`bigquery-public-data.stackoverflow.posts_questions`"
								+ " q on a.parent_id=q.id "
								+ "where extract(year from q.creation_date)="
								+ year + " and "
								+ "extract(month from q.creation_date)=" + month
								+ " and "
								+ "extract(day from q.creation_date)=" + day
								+ " and a.owner_user_id is not null "
								+ " and q.owner_user_id is not null "
								+ "group by a.owner_user_id, q.owner_user_id "
								+ "order by us_from, us_to " + "limit " + limit)
						.setUseLegacySql(false).build();
				JobId jobId = JobId.of(UUID.randomUUID().toString());
				Job queryJob = bigquery.create(JobInfo.newBuilder(queryConfig).setJobId(jobId).build());

				queryJob = queryJob.waitFor();

				if (queryJob == null) {
					throw new DeathJobException("Job no longer exists (runEdgeQuestionDate)");
				} else if (queryJob.getStatus().getError() != null) {
					// You can also look at queryJob.getStatus().getExecutionErrors() for all
					// errors, not just the latest one.
					throw new RuntimeException(queryJob.getStatus().getError().toString());

				}
				return queryJob;
			} else {
				throw new ParameterException("Parameters exception (runEdgeQuestionDate)");
				}
		} catch (ParameterException e) {
			System.err.println(e.getMessage());
			}
		return null;
	}

	/*
	 * Visualizzare la lista delle prime 100 triple (from, to, weight) relative a
	 * domande (Question) poste in un dato anno, mese e giorno
	 */
	public Job runEdgeQuestionDateWeight(final int year, final int month, final int day, final int limit)
			throws InterruptedException {
		try {
			MathUtils help = new MathUtils();
			if (help.isData(year, month, day)) {
				QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder("SELECT "
						+ "a.owner_user_id as `us_from`, q.owner_user_id as `us_to`,"
						+ " count(*) as weight " + "FROM "
						+ "`bigquery-public-data.stackoverflow.posts_questions` q " + "join "
						+ "`bigquery-public-data.stackoverflow.posts_answers` a "
						+ "on a.parent_id = q.id " + "WHERE "
						+ "extract(year from q.creation_date)=" + year + " and "
						+ "extract(month from q.creation_date)=" + month + " and "
						+ "extract(day from q.creation_date)=" + day + " and "
						+ "a.owner_user_id is not null " + "and "
						+ "q.owner_user_id is not null " + "group by "
						+ "a.owner_user_id, q.owner_user_id "
						+ "order by " + "a.owner_user_id, q.owner_user_id "
						+ "limit " + limit).setUseLegacySql(false)
						.build();

				JobId jobId = JobId.of(UUID.randomUUID().toString());
				Job queryJob = bigquery.create(JobInfo.newBuilder(queryConfig).setJobId(jobId).build());

				queryJob = queryJob.waitFor();

				if (queryJob == null) {
					throw new DeathJobException("Job no longer exists (runEdgeQuestionDateWeight)");
				} else if (queryJob.getStatus().getError() != null) {
					// You can also look at queryJob.getStatus().getExecutionErrors() for all
					// errors, not just the latest one.
					throw new RuntimeException(queryJob.getStatus().getError().toString());
				}
				return queryJob;
			} else {
				throw new ParameterException("Parameters exception (runEdgeQuestionDateWeight)");
			}
		} catch (ParameterException e) {
			System.err.println(e.getMessage());
			}

		return null;
	}

	/*
	 * Visualizzare la lista delle prime 100 coppie (from, to) relative a risposte
	 * (Answer) date da un determinato utente.
	 */
	public Job runEdgeAnswer(final int userId, final int limit) throws InterruptedException {

			QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder("select distinct "
					+ "a.owner_user_id as us_from, " + "q.owner_user_id as us_to "
					+ "from `bigquery-public-data.stackoverflow.posts_answers` a " + "join "
					+ "`bigquery-public-data.stackoverflow.posts_questions` q "
					+ "on a.parent_id=q.id "
					+ "where a.owner_user_id=" + userId + " and q.owner_user_id is not null "
					+ "group by a.owner_user_id, q.owner_user_id " + "order by us_from, us_to "
					+ "limit " + limit)
					.setUseLegacySql(false).build();
			JobId jobId = JobId.of(UUID.randomUUID().toString());
			Job queryJob = bigquery.create(JobInfo.newBuilder(queryConfig).setJobId(jobId).build());

			queryJob = queryJob.waitFor();

			if (queryJob == null) {
				throw new DeathJobException("Job no longer exists (runEdgeAnswer)");
			} else if (queryJob.getStatus().getError() != null) {
				// You can also look at queryJob.getStatus().getExecutionErrors() for all
				// errors, not just the latest one.
				throw new RuntimeException(queryJob.getStatus().getError().toString());

			}
			return queryJob;
	}

	/*
	 * Restituisce il risultato della query calcolata dal metodo in un oggetto di
	 * tipo Edge
	 */
	public ArrayList<Edge> getEdge(final Job query) throws JobException, InterruptedException {
		ArrayList<Edge> out = new ArrayList<Edge>();
		if (query != null) {
			TableResult res = query.getQueryResults();
			for (FieldValueList row : res.iterateAll()) {
				Edge in;
				if (row.size() == 2) {
					in = new Edge(row.get("us_from").getStringValue(),
							row.get("us_to").getStringValue());
				} else {
					in = new Edge(row.get("us_from").getStringValue(),
							row.get("us_to").getStringValue(),
							row.get("weight").getStringValue());
				}
				out.add(in);
			}
		}
		return out;
	}

}
