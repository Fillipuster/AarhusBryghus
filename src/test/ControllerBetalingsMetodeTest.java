package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import controller.Controller;
import model.BetalingsMetode;
import model.PrisKategori;
import model.Produkt;
import model.ProduktKategori;
import model.Salg;
import storage.Storage;

public class ControllerBetalingsMetodeTest {

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
		p1 = new Produkt(pk1, "Øl", "En god øl", 1, 0);
		PrK1 = new PrisKategori("Bar");
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
		fail();
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
			fail();
		} catch (IllegalArgumentException iea) {
			assertEquals(iea.getMessage(), "Navn må ikke være null");
		}
	}
	
	@Test 
	public void testUpdateBetalingsMetodeTC3() {
		try {
			Controller.updateBetalingsMetode(null, "MobilePay");
			fail();
		} catch (IllegalArgumentException iea) {
			assertEquals(iea.getMessage(), "Betalingsmetode må ikke være null");
		}
	}
}
