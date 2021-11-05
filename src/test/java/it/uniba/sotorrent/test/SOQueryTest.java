package it.uniba.sotorrent.test;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.uniba.sotorrent.SOQuery;
import it.uniba.utils.Edge;
import it.uniba.sotorrent.ISOQuery;
import it.uniba.sotorrent.GoogleDocsUtils;

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
import it.uniba.utils.MathUtils;
import it.uniba.exception.DeathJobException;
import it.uniba.exception.ParameterException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

class SOQueryTest {


	/**
	@throws IOException 
	 * @throws FileNotFoundException 
	 * @BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

 
  
   @BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	*/
	
	@Test
	public void runQueryTest() throws JobException, InterruptedException, FileNotFoundException, IOException{
		ISOQuery soq = new SOQuery();
		Map<String, Long> res = new HashMap<String, Long>();
		Job job =soq.runQuery();
		res = soq.getResults(job);		
		assertFalse(res.isEmpty(),() -> "The actual has an empty return");	
	}
	
	@Test
	public void runNodeQuestionDateTest() throws JobException, InterruptedException, FileNotFoundException, IOException {
		ISOQuery soq = new SOQuery();
		Job job =soq.runNodeQuestionDate(2016,2,11,10);	
		ArrayList<String> res = soq.getNode(job);		
		assertFalse(res.isEmpty(),() -> "The actual has an empty return");
	}
	
	
	@Test
	public void runNodeQuestionDateParameterExceptionTest() throws JobException, InterruptedException, FileNotFoundException, IOException {
		ISOQuery soq = new SOQuery();
		Job job =soq.runNodeQuestionDate(2016,2,11,0);	
		ArrayList<String> res = soq.getNode(job);		
		assertTrue(res.isEmpty(),() -> "The actual has an empty return");
	}
	
	@Test
	public void runNodeQuestionDateDeathJobExceptionTest() throws JobException, InterruptedException, FileNotFoundException, IOException {
		ISOQuery soq = new SOQuery();
		Job job =soq.runNodeQuestionDate(1980,2,25,1);	
		ArrayList<String> res = soq.getNode(job);		
		assertTrue(res.isEmpty(),() -> "The actual has an empty return");
	}
	
	
	@Test
	public void runNodeQuestionTagTest() throws JobException, InterruptedException, FileNotFoundException, IOException{
		ISOQuery soq = new SOQuery();
		Job job =soq.runNodeQuestionTag(2016,2,"question",10);	
		ArrayList<String> res = soq.getNode(job);		
		assertFalse(res.isEmpty(),() -> "The actual has an empty return");
	}
	
	@Test
	public void runNodeQuestionTagParameterExceptionTest() throws JobException, InterruptedException, FileNotFoundException, IOException{
		ISOQuery soq = new SOQuery();
		Job job =soq.runNodeQuestionTag(1,22,"question",0);	
		ArrayList<String> res = soq.getNode(job);		
		assertTrue(res.isEmpty(),() -> "The actual has an empty return");
	}
	
	@Test
	public void runNodePostDateTest() throws JobException, InterruptedException, FileNotFoundException, IOException{
		ISOQuery soq = new SOQuery();
		Job job =soq.runNodePostDate(2016,2,11,10);
		ArrayList<String> res = soq.getNode(job);		
		assertFalse(res.isEmpty(),() -> "The actual has an empty return");
	}
	
	@Test
	public void runNodePostDateParameterExceptionTest() throws JobException, InterruptedException, FileNotFoundException, IOException{
		ISOQuery soq = new SOQuery();
		Job job =soq.runNodePostDate(1,22,11,0);
		ArrayList<String> res = soq.getNode(job);		
		assertTrue(res.isEmpty(),() -> "The actual has an empty return");
	}
	
	@Test
	public void runNodePostTagTest() throws JobException, InterruptedException, FileNotFoundException, IOException{
		ISOQuery soq = new SOQuery();
		Job job =soq.runNodePostTag(2016,2,"post",10);
		ArrayList<String> res = soq.getNode(job);		
		assertFalse(res.isEmpty(),() -> "The actual has an empty return");
	}
	
	@Test
	public void runNodePostTagParameterExceptionTest() throws JobException, InterruptedException, FileNotFoundException, IOException{
		ISOQuery soq = new SOQuery();
		Job job =soq.runNodePostTag(1,22,"post",0);
		ArrayList<String> res = soq.getNode(job);		
		assertTrue(res.isEmpty(),() -> "The actual has an empty return");
	}
	
	@Test
	public void runNodeAnswerDateTest() throws JobException, InterruptedException, FileNotFoundException, IOException{
		ISOQuery soq = new SOQuery();
		Job job =soq.runNodeAnswerDate(2016,2,11,10);
		ArrayList<String> res = soq.getNode(job);		
		assertFalse(res.isEmpty(),() -> "The actual has an empty return");
	}
	
	@Test
	public void runNodeAnswerDateParameterExceptionTest() throws JobException, InterruptedException, FileNotFoundException, IOException{
		ISOQuery soq = new SOQuery();
		Job job =soq.runNodeAnswerDate(1,22,11,0);
		ArrayList<String> res = soq.getNode(job);		
		assertTrue(res.isEmpty(),() -> "The actual has an empty return");
	}
	
	@Test
	public void runNodeAnswerTagTest() throws JobException, InterruptedException, FileNotFoundException, IOException{
		ISOQuery soq = new SOQuery();
		Job job =soq.runNodeAnswerTag(2016,2,"answer",10);
		ArrayList<String> res = soq.getNode(job);		
		assertFalse(res.isEmpty(),() -> "The actual has an empty return");	
	}
	
	@Test
	public void runNodeAnswerTagParameterExceptionTest() throws JobException, InterruptedException, FileNotFoundException, IOException{
		ISOQuery soq = new SOQuery();
		Job job =soq.runNodeAnswerTag(1,22,"answer",0);
		ArrayList<String> res = soq.getNode(job);		
		assertTrue(res.isEmpty(),() -> "The actual has an empty return");	
	}
	
	/////
	
	@Test
	public void runEdgeQuestionDateTest() throws JobException, InterruptedException, FileNotFoundException, IOException{
		ISOQuery soq = new SOQuery();
		ArrayList<Edge> res = new ArrayList<Edge>();
		Job job =soq.runEdgeQuestionDate(2016,2,11,10);
		res = soq.getEdge(job);		
		assertFalse(res.isEmpty(),() -> "The actual has an empty return");	
	}
	
	@Test
	public void runEdgeQuestionDateParameterExceptionTest() throws JobException, InterruptedException, FileNotFoundException, IOException{
		ISOQuery soq = new SOQuery();
		ArrayList<Edge> res = new ArrayList<Edge>();
		Job job =soq.runEdgeQuestionDate(1,22,11,0);
		res = soq.getEdge(job);		
		assertTrue(res.isEmpty(),() -> "The actual has an empty return");	
	}
	
	@Test
	public void runEdgeQuestionDateWeightTest() throws JobException, InterruptedException, FileNotFoundException, IOException{
		ISOQuery soq = new SOQuery();
		ArrayList<Edge> res = new ArrayList<Edge>();
		Job job =soq.runEdgeQuestionDateWeight(2016,2,11,10);
		res = soq.getEdge(job);		
		assertFalse(res.isEmpty(),() -> "The actual has an empty return");	
	}
	
	@Test
	public void runEdgeQuestionDateWeightParameterExceptionTest() throws JobException, InterruptedException, FileNotFoundException, IOException{
		ISOQuery soq = new SOQuery();
		ArrayList<Edge> res = new ArrayList<Edge>();
		Job job =soq.runEdgeQuestionDateWeight(1,22,11,0);
		res = soq.getEdge(job);		
		assertTrue(res.isEmpty(),() -> "The actual has an empty return");	
	}
	
	@Test
	public void runEdgeQuestionTest() throws JobException, InterruptedException, FileNotFoundException, IOException{
		ISOQuery soq = new SOQuery();
		ArrayList<Edge> res = new ArrayList<Edge>();
		Job job =soq.runEdgeQuestion(1109,10);
		res = soq.getEdge(job);		
		assertFalse(res.isEmpty(),() -> "The actual has an empty return");	
	}
	
	@Test
	public void runEdgeQuestionParameterExceptionTest() throws JobException, InterruptedException, FileNotFoundException, IOException{
		ISOQuery soq = new SOQuery();
		ArrayList<Edge> res = new ArrayList<Edge>();
		Job job =soq.runEdgeQuestion(1109,0);
		res = soq.getEdge(job);		
		assertTrue(res.isEmpty(),() -> "The actual has an empty return");	
	}
	
	@Test
	public void runEdgeQuestionWeightTest() throws JobException, InterruptedException, FileNotFoundException, IOException{
		ISOQuery soq = new SOQuery();
		ArrayList<Edge> res = new ArrayList<Edge>();
		Job job =soq.runEdgeQuestionWeight(1109,10);
		res = soq.getEdge(job);		
		assertFalse(res.isEmpty(),() -> "The actual has an empty return");	
	}
	
	@Test
	public void runEdgeQuestionWeightParameterExceptionTest() throws JobException, InterruptedException, FileNotFoundException, IOException{
		ISOQuery soq = new SOQuery();
		ArrayList<Edge> res = new ArrayList<Edge>();
		Job job =soq.runEdgeQuestionWeight(1109,0);
		res = soq.getEdge(job);		
		assertTrue(res.isEmpty(),() -> "The actual has an empty return");	
	}
	
	@Test
	public void runEdgeAnswerWeightTest() throws JobException, InterruptedException, FileNotFoundException, IOException{
		ISOQuery soq = new SOQuery();
		ArrayList<Edge> res = new ArrayList<Edge>();
		Job job =soq.runEdgeAnswerWeight(86,10);
		res = soq.getEdge(job);		
		assertFalse(res.isEmpty(),() -> "The actual has an empty return");	
	}
	
	@Test
	public void runEdgeAnswerWeightParameterExceptionTest() throws JobException, InterruptedException, FileNotFoundException, IOException{
		ISOQuery soq = new SOQuery();
		ArrayList<Edge> res = new ArrayList<Edge>();
		Job job =soq.runEdgeAnswerWeight(86,0);
		res = soq.getEdge(job);		
		assertTrue(res.isEmpty(),() -> "The actual has an empty return");	
	}
	
	@Test
	public void runEdgeAnswerTest() throws JobException, InterruptedException, FileNotFoundException, IOException{
		ISOQuery soq = new SOQuery();
		ArrayList<Edge> res = new ArrayList<Edge>();
		Job job =soq.runEdgeAnswer(86,10);
		res = soq.getEdge(job);		
		assertFalse(res.isEmpty(),() -> "The actual has an empty return");	
	}
	
	@Test
	public void runEdgeAnswerParameterExceptionTest() throws JobException, InterruptedException, FileNotFoundException, IOException{
		ISOQuery soq = new SOQuery();
		ArrayList<Edge> res = new ArrayList<Edge>();
		Job job =soq.runEdgeAnswer(86,0);
		res = soq.getEdge(job);		
		assertTrue(res.isEmpty(),() -> "The actual has an empty return");	
	}
	
}
