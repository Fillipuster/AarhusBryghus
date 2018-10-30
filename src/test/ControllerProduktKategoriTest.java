package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import controller.Controller;
import model.Produkt;
import model.ProduktKategori;
import model.UdlejningsProdukt;
import storage.Storage;

public class ControllerProduktKategoriTest {
//	TODO: getUdlejligeProduktKategorier, 

	ProduktKategori pk1;

	@Before
	public void setUp() throws Exception {
		pk1 = new ProduktKategori("Fadøl");

	}

	// -------------------------------------------------------------------------------------------------------------------------
	// Test cases for createProduktKategori

	@Test
	public void testCreateProduktKategoriTC1() {
		try {
			Controller.createProduktKategori(null);
			fail();
		} catch (IllegalArgumentException iea) {
			assertEquals(iea.getMessage(), "Navn må ikke være null");
		}
	}

	@Test
	public void testCreateProduktKategoriTC2() {
		ProduktKategori actual = pk1;
		ProduktKategori pk2 = new ProduktKategori("Fadøl");

		assertEquals(pk2, actual);
	}

	// -------------------------------------------------------------------------------------------------------------------------
	// Test cases for updateProduktKategori

	@Test
	public void testUpdateProduktKategoriTC1() {
		ProduktKategori actual = pk1;
		Controller.updateProduktKategori(pk1, "Pils");
		System.out.println(pk1);
		ProduktKategori pk2 = new ProduktKategori("Pils");
		assertEquals(pk2, actual);

	}

	@Test
	public void testUpdateProduktKategoriTC2() {
		try {
			Controller.updateProduktKategori(null, "Flaske øl");
			fail();
		} catch (IllegalArgumentException iae) {
			assertEquals(iae.getMessage(), "Kategori må ikke være null");
		}
	}

	@Test
	public void testUpdateProduktKategoriTC3() {
		try {
			Controller.updateProduktKategori(pk1, null);
			fail();
		} catch (IllegalArgumentException iae) {
			assertEquals(iae.getMessage(), "Navn må ikke være null");
		}
	}

	// -------------------------------------------------------------------------------------------------------------------------
	// Test cases for getUdlejligeProduktKategorier
	//	TODO: Check at denner er rigtig
	@Test
	public void getUdlejligeProduktKategorier() {
		ArrayList<ProduktKategori> expected = new ArrayList<>();
		for (ProduktKategori pk : Storage.getProduktKategorier()) {
			for (Produkt p : Controller.getProdukterIKategori(pk)) {
				if (p instanceof UdlejningsProdukt) {
					if (!expected.contains(pk)) {
						expected.add(pk);
					}
				}
			}
		}
		assertEquals(expected, Controller.getUdlejligeProduktKategorier());
	}

}
