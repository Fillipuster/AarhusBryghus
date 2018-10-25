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
	public void setSalgBetalingsMetodeTC1() {
		Controller.setSalgBetalingsMetode(s1, bm1);
		BetalingsMetode actual = bm1;
		assertEquals(s1.getBetalingsMetode(), actual);
	}
	
	@Test
	public void setSalgBetalingsMetodeTC2() {
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
	public void createProduktLinjeTC1() { 
		ProduktLinje pl1 = Controller.createProduktLinje(s1, p1, PrK1, 5, 21);
		Boolean found = false; 
		for (ProduktLinje pl : s1.getProduktLinjer()) {
			if(pl == pl1) {
				found = true; 
			}
		}
		assertTrue(found);
	}
	
	@Test 
	public void createProduktLinjeTC2() {
//		ProduktLinje pl1 = Controller.createProduktLinje(s1, p1, PrK1, null, 21);
		
	}
	

}
