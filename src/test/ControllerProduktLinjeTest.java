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
import model.ProduktLinje;
import model.Salg;



public class ControllerProduktLinjeTest {
	
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
		p1 = new Produkt(pk1, "Øl", "En god øl", 1,0);
		PrK1 = new PrisKategori("Bar");
	}
	// -------------------------------------------------------------------------------------------------------------------------
		// Test cases for createProduktLinje

		@Test
		public void testCreateProduktLinjeTC1() {
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
			fail();
			} catch (IllegalArgumentException iea) {
				assertEquals(iea.getMessage(), "Rabat må ikke være negativ");
			}
		}
		
		@Test
		public void testUpdateProduktLinjeTC3() {
			try {
			ProduktLinje pl1 = Controller.createProduktLinje(s1, p1, PrK1, 1, 0);
			Controller.updateProduktLinje(pl1, -1, 21);
			fail();
			} catch (IllegalArgumentException iea) {
				assertEquals(iea.getMessage(), "Antal må ikke være negativ");
			}
		}

}
