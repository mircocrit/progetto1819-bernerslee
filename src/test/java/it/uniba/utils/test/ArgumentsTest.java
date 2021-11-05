package it.uniba.utils.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.uniba.utils.Arguments;

class ArgumentsTest {

	private static Arguments a = null;
	private static String arguments[] = {"yyyy=2016", "mm=02", "dd=11", "type=question", "user=1109", "edge=yes", "weight=yes", "taglike=java", "limit=100"};

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		a = new Arguments(arguments);
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		a = null;
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	
	@Test
	public void getUserTest() {
		int param = 1109;
		assertEquals(a.getUser(), param, () -> "Param is not equal to the expected id");

	}
	
	@Test
	public void getWeightTest() {
		assertTrue(a.getWeight(), () -> "Param has not an empty weight");
	}
	
	@Test 
	public void getEdgeTest() {
		assertTrue(a.getEdge(), () -> "Param has not an empty edge");
	}
	
	@Test
	public void getYearTest() {
		int param = 0;
		assertNotEquals(a.getYear(), param, () -> "Param is set to zero");
	}
	
	@Test
	public void getMonthTest() {
		int param = 13;
		assertNotEquals(a.getMonth(), param, () -> "Param is not a valid month");
	}
	
	@Test
	public void getDayTest() {
		int param = 32;
		assertNotEquals(a.getDay(), param, () -> "Param is not a valid day");
	}
	
	@Test
	public void getTableTest() {
		assertNotNull(a.getTable(), () -> "Param table is null");
	}
	 
	@Test
	public void getLimit() {
		assertTrue(a.getLimit() > 0, () -> "Limit is under 0");
		assertNotEquals(a.getLimit(), -1, () -> "Limit in under 1");
	}
	
	@Test
	public void getTagTest() {
		assertNotNull(a.getTag());
	}
	
	@Test
	public void EmptyTest() {
		assertFalse(a.empty());
		String []param = {""};
		Arguments actual = new Arguments(param);
		assertTrue(actual.empty());
		String []param1 = {"yyyy=2016","mm=02","dd=11"};
		Arguments actual1 = new Arguments(param1);
		assertFalse(actual1.empty());
	}
	
	@Test
	public void yearEmptyTest() {
		boolean actual = a.yearEmpty();
		assertFalse(actual);
		String param[] = {""};
		Arguments actual1 = new Arguments(param);
		assertTrue(actual1.yearEmpty());
	}
	
	@Test
	public void monthEmptyTest() {
		assertFalse(a.monthEmpty());
		String param[] = {""};
		Arguments actual = new Arguments(param);
		assertTrue(actual.monthEmpty());
	}
	
	@Test
	public void dayEmptyTest() {
		assertFalse(a.dayEmpty());
		String param[] = {""};
		Arguments actual = new Arguments(param);
		assertTrue(actual.dayEmpty());
	}
	
	@Test
	public void limitEmptyTest() {
		assertFalse(a.limitEmpty());
		String param[] = {""};
		Arguments actual = new Arguments(param);
		assertTrue(actual.limitEmpty());
	}
	
	@Test
	public void tableEmptyTest() {
		boolean actual = a.tableEmpty();
		assertFalse(actual);
		String actual1[] = {""};
		Arguments now = new Arguments(actual1);
		actual = now.tableEmpty();
		assertTrue(actual);
	}
	
	@Test
	public void tagEmptyTest() {
		assertFalse(a.tagEmpty());
		String param[] = {""};
		Arguments actual = new Arguments(param);
		assertTrue(actual.tagEmpty());
	}
	
	@Test
	public void userEmptytest() {
		assertFalse(a.userEmpty());
		String param[] = {""};
		Arguments actual = new Arguments(param);
		assertTrue(actual.userEmpty());
	}
	
	@Test
	public void constructorArgumentsEdgeTest() {
		String []param = {"edge=abc"};
		assertThrows(RuntimeException.class, () -> {Arguments actual = new Arguments(param);});
		String []param1 = {"edge=no"};
		Arguments actual1 = new Arguments(param1);
	}
	
	@Test
	public void constructorArgumentsWeightTest() {
		String []param = {"weight=abc"};
		assertThrows(RuntimeException.class, () -> {Arguments actual = new Arguments(param);});
		String []param1 = {"weight=no"};
		Arguments actual1 = new Arguments(param1);
	}
	
	@Test
	public void readExceptionYearTest() {
		String []param = {"yyyy=2016", "yyyy=2000"};
		assertThrows(RuntimeException.class, () -> {Arguments actual = new Arguments(param);});
	}
	
	@Test
	public void readExceptionMonthTest() {
		String []param = {"mm=02", "mm=01"};
		assertThrows(RuntimeException.class, () -> {Arguments actual = new Arguments(param);});
	}
	
	@Test
	public void readExceptionDayTest() {
		String []param = {"dd=11", "dd=01"};
		assertThrows(RuntimeException.class, () -> {Arguments actual = new Arguments(param);});
	}
	
	@Test
	public void readExceptionLimitTest() {
		String []param = {"limit=100", "limit=50"};
		assertThrows(RuntimeException.class, () -> {Arguments actual = new Arguments(param);});
	}
	
	@Test
	public void readExceptionTagTest() {
		String []param = {"taglike=java", "taglike=PHP"};
		assertThrows(RuntimeException.class, () -> {Arguments actual = new Arguments(param);});
	}
	
	@Test
	public void readExceptionUserTest() {
		String []param = {"user=2016", "user=2000"};
		assertThrows(RuntimeException.class, () -> {Arguments actual = new Arguments(param);});
	}
	
	@Test
	public void readExceptionTableEmptyTest() {
		String []param = {"yyyy=2016", "mm=02", "dd=11", "dd=01", "type=question", "user=1109", "edge=yes", "weight=yes", "taglike=java", "limit=100"};
		Arguments actual = new Arguments(param);
	}


}
