package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import controller.Controller;
import model.Produkt;
import model.ProduktKategori;
import model.UdlejningsProdukt;
import storage.Storage;

public class ControllerUdlejeligeProdukterTest {

	ProduktKategori pk1;
	ProduktKategori pk2;
	UdlejningsProdukt up;

	@Before
	public void setUp() {
		pk1 = new ProduktKategori("Fustage");
		pk2 = new ProduktKategori("Anlæg");
		up = new UdlejningsProdukt(pk1, "IPA", "IPA fustage 25 L", 750, 200);
	}
	// -------------------------------------------------------------------------------------------------------------------------
	// Test cases for createUdlejningsProdukt

	@Test
	public void createUdlejningsProduktTC1() {
		UdlejningsProdukt up = Controller.createUdlejningsProdukt(pk1, "IPA", "IPA fustage 25 L", 750, 200);
		Boolean found = false;
		for (Produkt p : Storage.getProdukter()) {
			if (p == up) {
				found = true;
			}
		}
		assertTrue(found);
	}

	@Test
	public void createUdlejningsProduktTC2() {
		try {
			UdlejningsProdukt up = Controller.createUdlejningsProdukt(null, "IPA", "IPA fustage 25L", 750, 200);
			fail();
		} catch (IllegalArgumentException iea) {
			assertEquals(iea.getMessage(), "Produkt kategori må ikke være null");
		}
	}

	@Test
	public void createUdlejningsProduktTC3() {
		try {
			UdlejningsProdukt up = Controller.createUdlejningsProdukt(pk1, null, "IPA fustage 25 L", 750, 200);
			fail();
		} catch (IllegalArgumentException iea) {
			assertEquals(iea.getMessage(), "Navn må ikke være null");
		}
	}

	@Test
	public void createUdlejningsProduktTC4() {
		try {
			UdlejningsProdukt up = Controller.createUdlejningsProdukt(pk1, "IPA", null, 750, 200);
			fail();
		} catch (IllegalArgumentException iea) {
			assertEquals(iea.getMessage(), "Beskrivelse må ikke være null");
		}
	}
	// -------------------------------------------------------------------------------------------------------------------------
	// Test cases for updateUdlejningsProdukt

	@Test
	public void updateUdlejningsProduktTC1() {
		Controller.updateUdlejningsProdukt(up, pk1, "IPA", "IPA fustage 25 L", 750, 200);
		assertEquals(pk1, up.getProduktKategori());
	}
	
	@Test
	public void updateUdlejningsProduktTC2() {
		Controller.updateUdlejningsProdukt(up, pk1, "Øl", "IPA fustage 25 L", 750, 200);
		assertEquals("Øl", up.getNavn());
	}
	
	@Test
	public void updateUdljeningsProduktTC3() {
		Controller.updateUdlejningsProdukt(up, pk1, "IPA", "Fustage", 750, 200);
		assertEquals("Fustage", up.getBeskrivelse());
	}
	
	@Test
	public void updateUdlejningsProduktTC4() {
		Controller.updateUdlejningsProdukt(up, pk1, "IPA", "IPA fustage 25 L", 1.0, 200);
		assertEquals(1, up.getPris(), 0.0);
	}
	
	@Test
	public void updateUdlejningsProduktTC5() {
		Controller.updateUdlejningsProdukt(up, pk1, "IPA", "IPA fustage 25 L", 750, 0);
		assertEquals(0, up.getPant(), 0.0);
	}
	
	@Test
	public void updateUdlejningsProduktTC6() {
		try {
		Controller.updateUdlejningsProdukt(null, pk1, "IPA", "IPA fustage 25 L", 750, 200);
		fail();
		} catch (IllegalArgumentException iea) {
			assertEquals(iea.getMessage(), "Udlejningsprodukt må ikke være null");
		}
	}
	
	@Test
	public void updateUdlejningsProduktTC7() {
		try {
			Controller.updateUdlejningsProdukt(up, null, "IPA", "IPA fustage 25 L", 750, 200);
		fail();
		} catch(IllegalArgumentException iea) {
			assertEquals(iea.getMessage(), "Produkt kategori må ikke være null");
		}
	}
	
	@Test
	public void updateUdlejningsProduktTC8() {
		try {
			Controller.updateUdlejningsProdukt(up, pk1, null, "IPA fustage 25 L", 750, 200);
			fail();
		}catch (IllegalArgumentException iea) {
			assertEquals(iea.getMessage(), "Navn må ikke være null");
		}
	}
	
	@Test
	public void updateUdlejningsProduktTC9() {
		try {
			Controller.updateUdlejningsProdukt(up, pk1, "IPA", null, 750, 200);
			fail();
		}catch (IllegalArgumentException iea) {
			assertEquals(iea.getMessage(), "Beskrivelse må ikke være null");
		}
	}
	// -------------------------------------------------------------------------------------------------------------------------
	// Test cases for getUdlejningsProdukter
	
	@Test
	public void testGetUdlejningsProdukterTC1() {
		ArrayList<UdlejningsProdukt> expected = new ArrayList<>();
		for (Produkt p : Storage.getProdukter()) {
			if (p instanceof UdlejningsProdukt) {
				expected.add((UdlejningsProdukt) p);
			}
		}
		assertEquals(expected, Controller.getUdlejningsProdukter());
	}
	// -------------------------------------------------------------------------------------------------------------------------
	// Test cases for getUdlejningsProdukterIProduktKategori

	@Test 
	public void testGetUdlejningsProdukterIProduktKategoriTC1() {
		ArrayList<UdlejningsProdukt> expected = new ArrayList<>();
		for (UdlejningsProdukt up : Controller.getUdlejningsProdukter()) {
			if (up.getProduktKategori() == pk1) {
				expected.add(up);
			}
		}
		assertEquals(expected, Controller.getUdlejningsProdukterIProduktKategori(pk1));
	}
	
	@Test
	public void testGetUdlejningsProdukterIProduktKategoriTC2() {
		try {
			Controller.getUdlejningsProdukterIProduktKategori(null);
			fail();
		} catch (IllegalArgumentException iea){
			assertEquals(iea.getMessage(), "Kategori må ikke være null");
		}
	}
	
	
}
