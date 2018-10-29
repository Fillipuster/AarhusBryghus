package test;

import static org.junit.Assert.assertTrue;

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
	
}
