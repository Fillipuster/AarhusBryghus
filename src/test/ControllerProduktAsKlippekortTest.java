package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import controller.Controller;
import model.Produkt;
import model.ProduktKategori;

public class ControllerProduktAsKlippekortTest {
	
	ProduktKategori pk; 
	Produkt p; 
	
	@Before 
	public void setUp() {
		pk = new ProduktKategori("Klippekort");
		p = new Produkt(pk, "Øl klippekort", "Klippekort til øl", 0, 10);
	}
	
	// -------------------------------------------------------------------------------------------------------------------------
	// Test cases for createProdukt
	
	@Test 
	public void testCreateProduktTC1() {
		Produkt actual = p;
		Produkt p = Controller.createProdukt(pk, "Øl klippekort", "Klippekort til øl", 0, 10);
		assertEquals(p, actual);
	}
	
	@Test 
	public void testCreateProduktTC2() {
		try {
			Controller.createProdukt(null, "Øl klippekort", "Klippekort til øl", 0, 10);
			fail();
		} catch (IllegalArgumentException iea) {
			assertEquals(iea.getMessage(), "Kategori kan ikke være null");
		}
	}
	
	@Test 
	public void testCreateProduktTC3() {
		try {
			Controller.createProdukt(pk, null, "Klippekort til øl", 0, 10);
			fail();
		} catch (IllegalArgumentException iea) {
			assertEquals(iea.getMessage(), "Navn kan ikke være null");
		}	
	}
	
	@Test 
	public void testCreateProduktTC4() {
		try {
			Controller.createProdukt(pk, "Øl klippekort", null, 0, 10);
			fail();
		} catch (IllegalArgumentException iea) {
			assertEquals(iea.getMessage(), "Beskrivelse kan ikke være null");
		}
	}
	
	@Test 
	public void testCreateProduktTC5() {
		try {
			Controller.createProdukt(pk, "Øl klippekort", "Klippekort til øl", 1, 10);
			fail();
		} catch (IllegalArgumentException iea) {
			assertEquals(iea.getMessage(), "Klippris for klippekort skal være 0");
		}
	}
	
	@Test 
	public void testCreateProduktTC6() {
		try {
			Controller.createProdukt(pk, "Øl klippekort", "Klippekort til øl", 0, -1);
			fail();
		} catch (IllegalArgumentException iea) {
			assertEquals(iea.getMessage(), "Udstedteklip skal være større eller ligmed 0");
		}
	}
	
	@Test 
	public void testCreateProduktTC7() {
		try {
			Controller.createProdukt(pk, "Øl klippekort", "Klippekort til øl", -1, 10);
			fail();
		} catch (IllegalArgumentException iea) {
			assertEquals(iea.getMessage(), "Klippris skal være større eller ligmed 0");
		}
	}
	
	
	

}
