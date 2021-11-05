package it.uniba.utils.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import it.uniba.utils.Edge;

class EdgeTest {

	@BeforeAll
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

	@Test
	public void isWeightEdge() {

	}

	@Test
	public void isWeightEdgeTest() {
		Edge.setHasWeight(false);
		boolean actual = Edge.isWeightEdge();
		assertFalse(actual, () -> "The actual is not false!");
	}

	@Test
	public void EdgeWithNullArgumentTest() {
		String param1 = null;
		String param2 = null;
		Edge actual = new Edge(param1, param2);
		assertNotNull(actual, () -> "The actual has not an empty pointer");
	}

	@Test
	public void EdgeWithNullArgumentWeightTest() {
		String param1 = null;
		String param2 = null;
		String param3 = null;
		Edge actual = new Edge(param1, param2, param3);
		assertNotNull(actual, () -> "The actual has not an empty pointer");
	}

	@Test
	public void getToEditArgumentTest() {
		String param1 = " ";
		String param2 = " ";
		Edge actual = new Edge(param1, param2);
		assertNotNull(actual.getTo(), () -> "The actual has not an empty to");

	}

	@Test
	public void getFromEditArgumentTest() {
		String param1 = " ";
		String param2 = " ";
		Edge actual = new Edge(param1, param2);
		assertNotNull(actual.getFrom(), () -> "The actual has not an empty from");
	}

	@Test
	public void getFromAssertEqualTest() {
		String param1 = "12345";
		String param2 = "";
		Edge actual = new Edge(param1, param2);
		assertEquals(actual.getFrom(), param1, () -> "The actual has not an expected from");
	}

	@Test
	public void getToAssertEqualTest() {
		String param1 = "";
		String param2 = "23456";
		Edge actual = new Edge(param1, param2);
		assertEquals(actual.getTo(), param2, () -> "The actual has not an expected to");
	}

	@Test
	public void setFromAssertEqualTest() {
		String param1 = "";
		String param2 = "";
		String param3 = "12345";
		Edge actual = new Edge(param1, param2);
		actual.setFrom(param3);
		assertEquals(actual.getFrom(), param3, () -> "The actual has not an expected from");
	}

	@Test
	public void setToAssertEqualTest() {
		String param1 = "";
		String param2 = "";
		String param3 = "12345";
		Edge actual = new Edge(param1, param2);
		actual.setTo(param3);
		assertEquals(actual.getTo(), param3, () -> "The actual has not an expected to");
	}

	@Test
	public void getWeightEqualTest() {
		String param1 = null;
		String param2 = null;
		Edge actual = new Edge(param1, param2);
		actual.setWeight("2");
		assertNotNull(actual.getWeight(), () -> "The actual has not a null weight");
	}

	@Test
	public void setWeightEqualTest() {
		String param1 = null;
		String param2 = null;
		String param3 = "2";
		Edge actual = new Edge(param1, param2);
		actual.setWeight(param3);
		assertEquals(actual.getWeight(), param3, () -> "The actual has not a null weight");
	}

	@Test
	public void emptyWeightTest() {
		String param1 = null;
		String param2 = null;
		String param3 = "";
		Edge actual = new Edge(param1, param2);
		actual.setWeight(param3);
		assertTrue(actual.emptyWeight(), () -> "The actual has not an empty weight");
	}

}
