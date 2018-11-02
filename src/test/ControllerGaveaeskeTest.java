package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import controller.Controller;
import model.Gaveaeske;
import model.GaveaeskeEmballage;
import model.GaveaeskePreset;
import storage.Storage;

public class ControllerGaveaeskeTest {
	
	GaveaeskeEmballage ge1;
	GaveaeskeEmballage ge2; 
	GaveaeskePreset g1;
	GaveaeskeEmballage ge3; 
	
	@Before
	public void setUp() {
		ge1 = new GaveaeskeEmballage("Trækasse");
		ge2 = new GaveaeskeEmballage("Papkasse");
		ge3 = new GaveaeskeEmballage("Kurv");
		g1 = new GaveaeskePreset(5, 2, 50, ge1);
	}

	// -------------------------------------------------------------------------------------------------------------------------
	// Test cases for createGaveæske
	
	@Test
	public void testCreateGaveæske() {
		assertEquals(new Gaveaeske(), Controller.createGaveaeske());
	}
	
	// -------------------------------------------------------------------------------------------------------------------------
	// Test cases for createGaveaeskePreset
	
	@Test 
	public void createGaveaeskePresetTC1() {
		GaveaeskePreset g1 = Controller.createGaveaeskePreset(5, 2, 50, ge1);
		boolean found = false; 
		for(GaveaeskePreset gp : Storage.getGaveaeskePresets()) {
			if(g1 == gp) {
				found = true; 
			}
		}
		assertTrue(found);
	}
	
	@Test
	public void createGaveaeskePresetTC2() {
		try {
			Controller.createGaveaeskePreset(-1, 2, 50, ge1);
			fail();
		} catch (IllegalArgumentException iea) {
			assertEquals(iea.getMessage(), "Øl kan ikke være mindre end 0");
		}
	}
	
	@Test
	public void createGaveaeskePresetTC3() {
		try {
			Controller.createGaveaeskePreset(5, -1, 50, ge1);
			fail();
		} catch (IllegalArgumentException iea) {
			assertEquals(iea.getMessage(), "Glas kan ikke være mindre end 0");
		}
	}
	
	@Test
	public void createGaveaeskePresetTC4() {
		try {
			Controller.createGaveaeskePreset(0, 0, 50, ge1);
			fail();
		} catch (IllegalArgumentException iea) {
			assertEquals(iea.getMessage(), "Der skal være et produkt i gaveæske");
		}
	}
	
	@Test
	public void createGaveaeskePresetTC5() {
		try {
			Controller.createGaveaeskePreset(5, 2, 50, null);
			fail();
		} catch (IllegalArgumentException iea) {
			assertEquals(iea.getMessage(), "Emballage må ikke være null");
		}
	}
	// -------------------------------------------------------------------------------------------------------------------------
	// Test cases for updateGaveaeskePreset
	
	@Test
	public void updateGaveaeskePresetTC1() {
		GaveaeskePreset g1 = Controller.createGaveaeskePreset(5, 2, 50, ge1);
		Controller.updateGaveaeskePreset(g1, 2, 2, 50, ge1);
		assertEquals(2, g1.getAntalØl(), 0.0);
	}
	
	@Test
	public void updateGaveaeskePresetTC2() {
		GaveaeskePreset g1 = Controller.createGaveaeskePreset(5, 2, 50, ge1);
		Controller.updateGaveaeskePreset(g1, 5, 10, 50, ge1);
		assertEquals(10, g1.getAntalGlas(), 0.0);
	}
	
	@Test
	public void updateGaveaeskePresetTC3() {
		GaveaeskePreset g1 = Controller.createGaveaeskePreset(5, 2, 50, ge1);
		Controller.updateGaveaeskePreset(g1, 5, 2, 100, ge1);
		assertEquals(100, g1.getPris(), 0.0);
	}
	
	@Test
	public void updateGaveaeskePresetTC4() {
		GaveaeskePreset g1 = Controller.createGaveaeskePreset(5, 2, 50, ge1);
		Controller.updateGaveaeskePreset(g1, 5, 2, 50, ge2);
		assertEquals(ge2, g1.getEmballage());
	}
	
	@Test
	public void updateGaveaeskePresetTC5() {
		try {
			GaveaeskePreset g1 = Controller.createGaveaeskePreset(5, 2, 50, ge1);
			Controller.updateGaveaeskePreset(g1, -1, 2, 50, ge1);
			fail();
		}catch (IllegalArgumentException iea) {
			assertEquals(iea.getMessage(), "Øl kan ikke være mindre end 0");
		}
	}
	
	@Test
	public void updateGaveaeskePresetTC6() {
		try {
			GaveaeskePreset g1 = Controller.createGaveaeskePreset(5, -1, 50, ge1);
			Controller.updateGaveaeskePreset(g1, 5, 2, 50, ge1);
			fail();
		}catch (IllegalArgumentException iea) {
			assertEquals(iea.getMessage(), "Glas kan ikke være mindre end 0");
		}
	}
	
	@Test
	public void updateGaveaeskePresetTC7() {
		try {
			GaveaeskePreset g1 = Controller.createGaveaeskePreset(5, 2, 50, ge1);
			Controller.updateGaveaeskePreset(g1, 0, 0, 50, ge1);
			fail();
		}catch (IllegalArgumentException iea) {
			assertEquals(iea.getMessage(), "Der skal være et produkt i gaveæske");
		}
	}
	
	@Test
	public void updateGaveaeskePresetTC8() {
		try {
			GaveaeskePreset g1 = Controller.createGaveaeskePreset(5, 2, 50, ge1);
			Controller.updateGaveaeskePreset(g1, 5, 2, 50, null);
			fail();
		}catch (IllegalArgumentException iea) {
			assertEquals(iea.getMessage(), "Emballage må ikke være null");
		}
	}
	
	@Test
	public void updateGaveaeskePresetTC9() {
		try {
			GaveaeskePreset g1 = Controller.createGaveaeskePreset(5, 2, 50, ge1);
			Controller.updateGaveaeskePreset(null, 5, 2, 50, ge1);
			fail();
		} catch (IllegalArgumentException iea) {
			assertEquals(iea.getMessage(), "Gaveæske skal være valgt");
		}
	}
	// -------------------------------------------------------------------------------------------------------------------------
	// Test cases for createGaveaeskeEmballage
	
	@Test
	public void testCreateGaveaeskeEmballageTC1() {
		GaveaeskeEmballage g3 = Controller.createGaveaeskeEmballage("Kurv");
		boolean found = false; 
		for(GaveaeskeEmballage ge : Storage.getGaveaeskeEmballager()) {
			if(ge == g3) {
				found = true; 
			}
		}
		assertTrue(found);
	}
	
}
