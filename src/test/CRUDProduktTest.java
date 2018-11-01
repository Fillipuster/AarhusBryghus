package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.BeforeClass;
import org.junit.Test;

import model.PrisKategori;
import model.Produkt;
import model.ProduktKategori;
import controller.Controller;

public class CRUDProduktTest {
	
	private static ProduktKategori pk0; 
	private static PrisKategori PrisK0;
	private static PrisKategori PrisK1; 
	private static Produkt p0; 
	
	@BeforeClass
	public static void setUp() {
		pk0 = Controller.createProduktKategori("fadøl");
		PrisK0 = Controller.createPrisKategori("bar");
		PrisK1 = Controller.createPrisKategori("butik");
		p0 = Controller.createProdukt(pk0, "pilsner", "dejlig fra fad", 1, 0);
	}
	
	@Test
	public void addPrisToProduktTC1() {
		Produkt expected = new Produkt(pk0, "pilsner", "dejlig fra fad", 1, 0);
		expected.setPris(PrisK0, 50);
		expected.setPris(PrisK1, 70);
		
		Controller.addPrisToProdukt(p0, PrisK0, 50);
		Controller.addPrisToProdukt(p0, PrisK1, 70);
		
		boolean pris0Matches = expected.getPris(PrisK0) == p0.getPris(PrisK0);
		boolean pris1Matches = expected.getPris(PrisK1) == p0.getPris(PrisK1);
		
		assertTrue(pris1Matches && pris0Matches);
	}
	
	@Test
	public void addPrisToProduktTC2() {
		Produkt expected = new Produkt(pk0, "pilsner", "dejlig fra fad", 1, 0);
		expected.setPris(PrisK0, 40);
		
		Controller.addPrisToProdukt(p0, PrisK0, 40);
		
		assertEquals(expected, p0);
	}
	
	@Test
	public void addPrisToProduktTC3() {
		try {
			Controller.addPrisToProdukt(p0, PrisK0, -1);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(e.getMessage(), "Pris skal være > 0.");
		}
	}
	
	@Test
	public void addPrisToProduktTC4() {
		try {
			Controller.addPrisToProdukt(p0, null, 1);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(e.getMessage(), "PrisKategori må ikke være null.");
		}
	}
	
	@Test
	public void addPrisToProduktTC5() {
		try {
			Controller.addPrisToProdukt(null, PrisK0, 1);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(e.getMessage(), "Produkt må ikke være null.");
		}
	}
	
	
	
	

	// -------------------------------------------------------------------------------------------------------------------------
	// Test cases for createProdukt

	/* For at se test case for createProdukt henvises der til klassen ControllerCreateProduktTest*/
	
	// -------------------------------------------------------------------------------------------------------------------------
	// Test cases for updateProdukt
	
	/* For at se test case for updateProdukt henvises der til klassen ControllerUpdateProduktTest*/
	
}
