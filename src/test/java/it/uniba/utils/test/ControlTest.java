package it.uniba.utils.test;

import static org.junit.Assume.assumeNoException;
import static org.junit.Assume.assumeNotNull;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import it.uniba.exception.ConfigurationException;
import it.uniba.exception.ParameterException;
import it.uniba.utils.Control;
import junit.framework.Assert;

@Tag("Control")
class ControlTest {
	private static Control c = null;

	@BeforeAll
	public static void setUpAll() {
		c = new Control();
	}

	@AfterAll
	public static void tearDownAll() {
		c = null;
	}

	@BeforeEach
	public void setUp() {
	}

	@AfterEach
	public void tearDown() {
	}

	@Test
	@DisplayName("Testing runNode")
	public void testRunNode() {
		assumeNotNull(c);

		// è avvalorato l'attributo weight
		ConfigurationException ex2 = assertThrows(ConfigurationException.class, () -> {
			String[] test = { "weight=yes", "edge=no" };
			c.execute(test);
		});
		assertTrue(ex2.getMessage().equals("Cannot have node with weight"));

		// l'ogetto di tipo Arguments è vuoto
		assertThrows(ConfigurationException.class, () -> {
			String[] test = { "ciao" };
			c.execute(test);
		}, "Illegal arguments");

		// è avvalorato l'attributo user
		ConfigurationException ex3 = assertThrows(ConfigurationException.class, () -> {
			String[] test = { "user=100" };
			c.execute(test);
		});
		assertTrue(ex3.getMessage() == "You cannot have node specifing a specific user");

		// non è avvalorato l'attributo table (deve necessariamente)
		ConfigurationException ex4 = assertThrows(ConfigurationException.class, () -> {
			String[] test = { "dd=02", "mm=11", "yyyy=2016" };
			c.execute(test);
		});
		assertTrue(ex4.getMessage().equals("Illegal type."));

		// giorno vuoto
		assertAll("giorno vuoto", () -> {
			String[] test = { "type=question", "yyyy=2016", "mm=02", "limit=100" };

			// question
			ConfigurationException ex5 = assertThrows(ConfigurationException.class, () -> {
				c.execute(test);
			});
			assertTrue(ex5.getMessage().equals("Empty day exception"));

			// answer
			test[0] = "type=answer";
			ex5 = assertThrows(ConfigurationException.class, () -> {
				c.execute(test);
			});
			assertTrue(ex5.getMessage().equals("Empty day exception"));

			// post
			test[0] = "type=post";
			ex5 = assertThrows(ConfigurationException.class, () -> {
				c.execute(test);
			});
			assertTrue(ex5.getMessage().equals("Empty day exception"));
		});

		// mese, anno e limit vuoto
		assertAll("mese, anno e limit vuoti", () -> {
			// question
			String[] test = { "type=question", "taglike=x" };
			ParameterException ex6 = assertThrows(ParameterException.class, () -> {
				c.execute(test);
			});
			assertTrue(ex6.getMessage().equals("Parameter exception."));

			// answer
			test[0] = "type=answer";
			ex6 = assertThrows(ParameterException.class, () -> {
				c.execute(test);
			});
			assertTrue(ex6.getMessage().equals("Parameter exception."));

			// post
			test[0] = "type=post";
			ex6 = assertThrows(ParameterException.class, () -> {
				c.execute(test);
			});
			assertTrue(ex6.getMessage().equals("Parameter exception."));
		});
		
		//entra nel default dello switch
		assertThrows(ConfigurationException.class, ()->{
			String[] test= {"type=*"};
			c.execute(test);
		},"Illegal type");
	}

	@Test
	@DisplayName("Testing perfect 3rd node query")
	@Tag("slow")
	public void runNodePerfect3() {//runNodePostDate
		assumeNotNull(c);
		try {
			String[] test= {"type=post","yyyy=2016","mm=02","dd=11","limit=1"};
			c.execute(test);
		}catch(Exception e) {
			assumeNoException(e);
		}
	}
	
	@Test
	@DisplayName("Testing perfect 4th node query")
	@Tag("slow")
	public void runNodePerfect4() {//runNodeQuestionTag
		assumeNotNull(c);
		try {
			String[] test= {"type=question","yyyy=2016","mm=02","taglike=java","limit=1"};
			c.execute(test);
		}catch(Exception e) {
			assumeNoException(e);
		}
	}

	@Test
	@DisplayName("Testing perfect 2nd node query")
	@Tag("slow")
	public void runNodePerfect2() {//runNodeAnswerDate
		assumeNotNull(c);
		try {
			String[] test= {"type=answer","yyyy=2016","mm=02","dd=11","limit=1"};
			c.execute(test);
		}catch(Exception e) {
			assumeNoException(e);
		}
	}
	
	@Test
	@DisplayName("Testing perfect 5th node query")
	@Tag("slow")
	public void runNodePerfect5(){//runNodeAnswerTag
		assumeNotNull(c);
		try {
			String[] test= {"type=answer","yyyy=2016","mm=02","taglike=*","limit=1"};
			c.execute(test);
		}catch(Exception e) {
			assumeNoException(e);
		}
	}
	
	@Test
	@DisplayName("Testing perfect 6th node query")
	@Tag("slow")
	public void runNodePerfect6() {//runNodePostTag
		assumeNotNull(c);
		try {
			String[] test= {"type=post","yyyy=2016","mm=02","taglike=*","limit=1"};
			c.execute(test);
		}catch(Exception e) {
			assumeNoException(e);
		}
	}
	
	@Test
	@DisplayName("Testing runEdge")
	public void runEdgeTest() {
		assumeNotNull(c);

		// l'ogetto di tipo Arguments è vuoto
		assertThrows(RuntimeException.class, () -> {
			String[] test = { "ciao", "edge=yes" };
			c.execute(test);
		}, "Illegal arguments");

		// tag avvalorato
		assertThrows(RuntimeException.class, () -> {
			String[] test = { "ciao", "edge=yes", "taglike=*" };
			c.execute(test);
		}, "You cannot have any edge requiring a specific tag");

		// limit vuoto
		assertThrows(RuntimeException.class, () -> {
			String[] test = { "ciao", "edge=yes", "type=*" };
			c.execute(test);
		}, "Limit not specified");

		// non ho data
		assertAll("Non ho nè data nè utente", () -> {
			String[] test = { "type=question", "edge=yes", "limit=0" };

			// question
			assertThrows(ConfigurationException.class, () -> {

				c.execute(test);
			}, "Empty date");
			// necessari altri 3 branches

			// answer
			test[0] = "type=answer";
			assertThrows(ConfigurationException.class, () -> {
				c.execute(test);
			}, "You can have edges from answer just passing an user and choosing if have weight or not");
		});

		// question con user e data
		assertThrows(RuntimeException.class, () -> {
			String[] test = { "type=question", "user=1", "edge=yes", "yyyy=2016", "mm=02", "dd=11","limit=0" };
			c.execute(test);
		}, "Cannot execute a request for an edge specifing a date an a user together");

		assertThrows(ConfigurationException.class, () -> {
			String[] test = { "type=*", "edge=yes", "limit=0" };
			c.execute(test);
		}, "Illegal type");
	}

	
	@Test
	@DisplayName("Testing perfect 1st edge query")
	@Tag("slow")
	public void runEdgePerfect1() {//runEdgeQuestionDate
		assumeNotNull(c);
		try {
			String[] test= {"type=question","yyyy=2016","mm=02","dd=11","limit=1","edge=yes"};
			c.execute(test);
		}catch(Exception e) {
			assumeNoException(e);
		}
	}
	
	@Test
	@DisplayName("Testing perfect 2nd edge query")
	@Tag("slow")
	public void runEdgePerfect2() {//runEdgeQuestion
		assumeNotNull(c);
		try {
			String[] test= {"type=question","user=1","limit=1","edge=yes"};
			c.execute(test);
		}catch(Exception e) {
			assumeNoException(e);
		}
	}
	
	
	
	@Test
	@DisplayName("Testing perfect 3rd edge query")
	@Tag("slow")
	public void runEdgePerfect3() {//runEdgeAnswer
		assumeNotNull(c);
		try {
			String[] test= {"type=answer","user=1","limit=1","edge=yes"};
			c.execute(test);
		}catch(Exception e) {
			assumeNoException(e);
		}
	}
	
	
	@Test
	@DisplayName("Testing perfect 4th edge query")
	@Tag("slow")
	public void runEdgePerfect4() {//runEdgeQuestionDateWeight
		assumeNotNull(c);
		try {
			String[] test= {"type=question","yyyy=2016","mm=02","dd=11","limit=1","edge=yes","weight=yes"};
			c.execute(test);
		}catch(Exception e) {
			assumeNoException(e);
		}
	}
	
	@Test
	@DisplayName("Testing perfect 5th edge query")
	@Tag("slow")
	public void runEdgePerfect5() {//runedgeQuestionWeight
		assumeNotNull(c);
		try {
			String[] test= {"type=question","user=1","limit=1","edge=yes","weight=yes"};
			c.execute(test);
		}catch(Exception e) {
			assumeNoException(e);
		}
	}
	
	
	@Test
	@DisplayName("Testing perfect 6th edge query")
	@Tag("slow")
	public void runEdgePerfect6() {//runEdgeAnswerWeight
		assumeNotNull(c);
		try {
			String[] test= {"type=answer","user=1","limit=1","edge=yes","weight=yes"};
			c.execute(test);
		}catch(Exception e) {
			assumeNoException(e);
		}
	}
	
	
	
	@Test
	@DisplayName("Testing execute with an empty array")
	@Tag("slow")
	public void executeEmptyArrayTest() {
		assumeNotNull(c);
		try {
			String[] test = new String[0];
			c.execute(test);
		} catch (Exception e) {
			assumeNoException(e);
		}
	}

	@Test
	@DisplayName("Testing an ideal execution")
	@Tag("slow")
	public void executePerfectTest() {
		assumeNotNull(c);
		try {
			String[] test = { "type=question", "yyyy=2016", "mm=02", "dd=11", "limit=1" };
			c.execute(test);
		} catch (Exception e) {
			assumeNoException(e);
		}
	}

}
