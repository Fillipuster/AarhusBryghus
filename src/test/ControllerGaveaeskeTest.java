package test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import controller.Controller;
import model.Gaveaeske;

public class ControllerGaveaeskeTest {

	// -------------------------------------------------------------------------------------------------------------------------
	// Test cases for createGaveæske
	
	@Test
	public void testCreateGaveæske() {
		assertEquals(new Gaveaeske(), Controller.createGaveaeske());
	}
	
}
