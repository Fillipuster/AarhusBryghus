package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import controller.Controller;
import model.BetalingsMetode;
import model.PrisKategori;
import model.Produkt;
import model.ProduktKategori;
import model.ProduktLinje;
import model.Salg;
import storage.Storage;

public class controllerSalgTest {

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

	// -------------------------------------------------------------------------------------------------------------------------
	// Test cases for setSalgBetalingsMetode

	@Test
	public void testSetSalgBetalingsMetodeTC1() {
		Controller.setSalgBetalingsMetode(s1, bm1);
		BetalingsMetode actual = bm1;
		assertEquals(s1.getBetalingsMetode(), actual);
	}

	@Test
	public void testSetSalgBetalingsMetodeTC2() {
		try {
			Controller.setSalgBetalingsMetode(s1, null);
			fail();
		} catch (IllegalArgumentException iea) {
			assertEquals(iea.getMessage(), "Betalingsmetode må ikke være null");
		}
	}

	// -------------------------------------------------------------------------------------------------------------------------
	// Test cases for createProduktLinje

	@Test
	public void testCestCreateProduktLinjeTC1() {
		ProduktLinje pl1 = Controller.createProduktLinje(s1, p1, PrK1, 5, 21);
		Boolean found = false;
		for (ProduktLinje pl : s1.getProduktLinjer()) {
			if (pl == pl1) {
				found = true;
			}
		}
		assertTrue(found);
	}

	@Test
	public void testCreateProduktLinjeTC2() {
		try {
			ProduktLinje pl1 = Controller.createProduktLinje(s1, p1, PrK1, -5, 5);
			fail();
		} catch (IllegalArgumentException iea) {
			assertEquals(iea.getMessage(), "Antal kan ikke være negativt");
		}
	}
	
	@Test
	public void testCreateProduktLinjeTC3() {
		try {
			ProduktLinje pl1 = Controller.createProduktLinje(s1, null, PrK1, -5, 5);
			fail();
		} catch (IllegalArgumentException iea) {
			assertEquals(iea.getMessage(), "Produkt kan ikke være null");
		}
	}
	
	@Test
	public void testCreateProduktLinjeTC4() {
		ProduktLinje pl1 = Controller.createProduktLinje(s1, p1, PrK1, 5, 0);
		Boolean found = false;
		for (ProduktLinje pl : s1.getProduktLinjer()) {
			if (pl == pl1) {
				found = true;
			}
		}
		assertTrue(found);
	}
	// -------------------------------------------------------------------------------------------------------------------------
	// Test cases for updateProduktLinje
	
	@Test
	public void testUpdateProduktLinjeTC1() {
		ProduktLinje pl1 = Controller.createProduktLinje(s1, p1, PrK1, 1, 0);
		Controller.updateProduktLinje(pl1, 5, 21);
		Boolean found = false;
		for (ProduktLinje pl : s1.getProduktLinjer()) {
			if (pl == pl1) {
				found = true;
			}
		}
		assertTrue(found);
	}
	
	@Test 
	public void testUpdateProduktLinjeTC2() {
		try {
		ProduktLinje pl1 = Controller.createProduktLinje(s1, p1, PrK1, 1, 0);
		Controller.updateProduktLinje(pl1, 1, -21);
		} catch (IllegalArgumentException iea) {
			assertEquals(iea.getMessage(), "Rabat kan ikke være negativt");
		}
	}
	
	@Test
	public void testUpdateProduktLinjeTC3() {
		try {
		ProduktLinje pl1 = Controller.createProduktLinje(s1, p1, PrK1, 1, 0);
		Controller.updateProduktLinje(pl1, -1, 21);
		} catch (IllegalArgumentException iea) {
			assertEquals(iea.getMessage(), "Antal kan ikke være negativt");
		}
	}
	// -------------------------------------------------------------------------------------------------------------------------
	// Test cases for createBetalingsMetode
	
	@Test
	public void testCreateBetalingsMetodeTC1() {
		BetalingsMetode bm1 = Controller.createBetalingsMetode("Dankort", false);
		Boolean found = false; 
		for (BetalingsMetode bm : Storage.getBetalingsMetoder()) {
			if(bm == bm1) {
				found = true; 
			}
		}
		assertTrue(found);
	}
	
	@Test 
	public void testCreateBetalingsMetodeTC2() {
		try {
		BetalingsMetode bm1 = Controller.createBetalingsMetode(null, false);
		} catch (IllegalArgumentException iea) {
			assertEquals(iea.getMessage(), "Navn må ikke være null");
		}
	}
	// -------------------------------------------------------------------------------------------------------------------------
	// Test cases for updateBetalingsMetode
	
	@Test
	public void testUpdateBetalingsMetodeTC1() {
		BetalingsMetode bm1 = Controller.createBetalingsMetode("Dankort", false);
		Controller.updateBetalingsMetode(bm1, "MobilePay");
		BetalingsMetode found = null;
		for (BetalingsMetode bm : Storage.getBetalingsMetoder()) {
			if (bm == bm1) {
				found = bm; 
			}
		}
		assertEquals("MobilePay", found.getNavn());
	}
	
	@Test
	public void testUpdateBetalingsMetodeTC2() {
		try {
			BetalingsMetode bm1 = Controller.createBetalingsMetode("Dankort", false);
			Controller.updateBetalingsMetode(bm1, null);
		} catch (IllegalArgumentException iea) {
			assertEquals(iea.getMessage(), "Navn må ikke være null");
		}
	}
	
	@Test 
	public void testUpdateBetalingsMetodeTC3() {
		try {
			BetalingsMetode bm1 = Controller.createBetalingsMetode("Dankort", false);
			Controller.updateBetalingsMetode(null, "MobilePay");
		} catch (IllegalArgumentException iea) {
			assertEquals(iea.getMessage(), "Betalingsmetode må ikke være null");
		}
	}

}
