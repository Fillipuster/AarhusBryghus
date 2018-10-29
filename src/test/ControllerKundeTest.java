package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import controller.Controller;
import model.Kunde;
import storage.Storage;

public class ControllerKundeTest {
	
	Kunde k1; 
	
	@Before
	public void setUp() {
		Kunde k = new Kunde("Jens", "Vej", "12345678");
	}
	
	// -------------------------------------------------------------------------------------------------------------------------
	// Test cases for createKunde
	
	@Test
	public void createKundeTC1() {
		Kunde k1 = Controller.createKunde("Jens", "Vej 21", "12345678");
		boolean found = false;
		for (Kunde k : Storage.getKunder()) {
			if(k == k1) {
				found = true;
			}
		}
		assertTrue(found);
	}
	
	@Test
	public void createKundeTC2() {
		try {
		Kunde k1 = Controller.createKunde(null, "Vej 21", "12345678");
		fail();
		} catch(IllegalArgumentException iea) {
			assertEquals(iea.getMessage(), "Navn må ikke være null");
		}
	}
	
	@Test
	public void createKundeTC3() {
		try {
		Kunde k1 = Controller.createKunde("Jens", null, "12345678");
		fail();
		} catch(IllegalArgumentException iea) {
			assertEquals(iea.getMessage(), "Adresse må ikke være null");
		}
	}
	
	@Test
	public void createKundeTC4() {
		try {
		Kunde k1 = Controller.createKunde("Jens", "Vej 21", null);
		fail();
		} catch(IllegalArgumentException iea) {
			assertEquals(iea.getMessage(), "Telefonnummer må ikke være null");
		}
	}
	
	
}
