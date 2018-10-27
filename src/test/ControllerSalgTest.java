package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import controller.Controller;
import model.BetalingsMetode;
import model.Gaveaeske;
import model.PrisKategori;
import model.Produkt;
import model.ProduktKategori;
import model.ProduktLinje;
import model.Salg;
import storage.Storage;

public class ControllerSalgTest {

	BetalingsMetode bm1;
	Salg s1;
	Produkt p1;
	ProduktKategori pk1;
	PrisKategori PrK1;

	@Before
	public void setUp() {
		s1 = new Salg();
		bm1 = new BetalingsMetode("MobilePay", false);
		pk1 = new ProduktKategori("Bar");
		p1 = new Produkt(pk1, "Øl", "En god øl", 1);
		PrK1 = new PrisKategori("Bar");
	}
	// -------------------------------------------------------------------------------------------------------------------------
	// Test cases for createSalg

	@Test
	public void testSalgTC1() {
		Salg s = Controller.createSalg();
		assertTrue(s instanceof Salg);
	}

	// -------------------------------------------------------------------------------------------------------------------------
	// Test cases for saveSalg

	@Test
	public void testSaveSalgTC1() {
		Controller.saveSalg(s1);
		boolean found = false;
		for (Salg s : Storage.getSalg()) {
			if (s == s1) {
				found = true;
			}
		}
		assertTrue(found);
	}

	@Test
	public void testSaveSalgTC2() {
		try {
			Controller.saveSalg(null);
			fail();

		} catch (IllegalArgumentException iea) {
			assertEquals(iea.getMessage(), "Salg må ikke være null");
		}
	}



	

}
