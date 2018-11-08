package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controller.Controller;
import model.PrisKategori;
import model.Produkt;
import model.ProduktKategori;
import storage.Storage;

public class ControllerCreateProduktTest {
	PrisKategori priskat0;
	ProduktKategori k1;
	Produkt p1;
	Produkt p2;
	Produkt p3;

	@Before
	public void setUp() throws Exception {
		priskat0 = new PrisKategori("Bar");
		k1 = new ProduktKategori("Øl");
		p1 = new Produkt(k1, "IPA", "frugtig", 0, 0);
		p3 = new Produkt(k1, "Pils", "Frugtig", 2, 0);
	}
	
	@After
	public void cleanUp() {
		for(Produkt p : Storage.getProdukter()) {
			Storage.removeProdukt(p);
		}
	}
	

	// -------------------------------------------------------------------------------------------------------------------------
	// Test cases for createProdukt

	@Test
	public void testCreateProduktTC1() {
		Produkt actual = p1;
		Produkt p = Controller.createProdukt(k1, "IPA", "frugtig", 0, 0);
		assertEquals(p, actual);
	}

	@Test
	public void testCreateProduktTC2() {
		Produkt actual = new Produkt(k1, "IPA", "frugtig", 1, 0);
		Produkt p = Controller.createProdukt(k1, "IPA", "frugtig", 1, 0);
		assertEquals(p, actual);
	}
	
	@Test
	public void testCreateProduktTC3() {
		Produkt actual = new Produkt(k1, "IPA", "frugtig", 0, 1);
		Produkt p = Controller.createProdukt(k1, "IPA", "frugtig", 0, 1);
		assertEquals(p, actual);
	}
	
	@Test
	public void testCreateProduktTC4() {
		try {
			Controller.createProdukt(k1, "IPA", "frugtig", 1, 1);
			fail();
		}catch (IllegalArgumentException iae) {
			assertEquals(iae.getMessage(), "Klippris for klippekort skal være 0.");
		}
	}
	
	@Test
	public void testCreateProduktTC5() {
		try {
			Controller.createProdukt(null, "IPA", "frugtig", 0, 0);
			fail();
		}catch (IllegalArgumentException iae) {
			assertEquals(iae.getMessage(), "ProduktKategori må ikke være null.");
		}
	}
	
	@Test
	public void testCreateProduktTC6() {
		try {
			Controller.createProdukt(k1, null, "frugtig", 0, 0);
			fail();
		}catch (IllegalArgumentException iae) {
			assertEquals(iae.getMessage(), "Navn må ikke være null.");
		}
	}
	
	@Test
	public void testCreateProduktTC7() {
		try {
			Controller.createProdukt(k1, "IPA", null, 0, 0);
			fail();
		}catch (IllegalArgumentException iae) {
			assertEquals(iae.getMessage(), "Beskrivelse må ikke være null.");
		}
	}
	
	@Test
	public void testCreateProduktTC8() {
		try {
			Controller.createProdukt(k1, "IPA", "frugtig", -1, 0);
			fail();
		}catch (IllegalArgumentException iae) {
			assertEquals(iae.getMessage(), "KlipPris skal være >= 0.");
		}
	}
	
	@Test
	public void testCreateProduktTC9() {
		try {
			Controller.createProdukt(k1, "IPA", "frugtig", 0, -1);
			fail();
		}catch (IllegalArgumentException iae) {
			assertEquals(iae.getMessage(), "UdstedteKlip skal være >= 0.");
		}
	}
}
