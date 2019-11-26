package fr.insee.bootcampjs.telephoneback.api;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import fr.insee.bootcampjs.telephoneback.api.RestDesignation;

public class RestBasiqueTest {

	@Test
	public void testHelloWorld() {
		RestDesignation api = new RestDesignation();

		// GIVEN
		assertEquals("Hello World", api.helloWorld());
		// WHEN

		// THEN
	}

}
