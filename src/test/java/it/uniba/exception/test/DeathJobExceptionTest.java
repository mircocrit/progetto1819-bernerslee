package it.uniba.exception.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.uniba.exception.DeathJobException;

class DeathJobExceptionTest {

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
	public void constructorDeathJobExceptionTest() {
		DeathJobException actual = new DeathJobException();
	}
	
	@Test
	public void constructorArgumentDeathJobExceptionTest() {
		DeathJobException actual = new DeathJobException("");
	}

}
