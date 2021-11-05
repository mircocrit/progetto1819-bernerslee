package it.uniba.exception.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.uniba.exception.ConfigurationException;

class ConfigurationExceptionTest {

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
	public void constructorConfigurationExceptionTest() {
		ConfigurationException actual = new ConfigurationException();
	}
	
	@Test
	public void constructorArgumentConfigurationException() {
		ConfigurationException actual = new ConfigurationException(" ");
	}

}
