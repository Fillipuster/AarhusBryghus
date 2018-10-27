package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import controller.Controller;
import model.Produkt;
import model.ProduktKategori;
import model.PrisKategori;

public class ProduktTest {

//	Produkt p;
	
	ProduktKategori pk1;
	Produkt p1;
	PrisKategori prisK1;
	
	@Before
	public void setUp() {
		pk1 = new ProduktKategori("Fadøl");
		p1 = new Produkt(pk1, "Pilsner", "God øl", 1);
		prisK1 = new PrisKategori("Bar");
	}
	
	// -------------------------------------------------------------------------------------------------------------------------
	// Test cases for getProdukt
	
	@Test
	public void testGetProduktTC1() {
		assertEquals(p1.getProduktKategori(), pk1);
	}
	
	// -------------------------------------------------------------------------------------------------------------------------
	// Test cases for setPris
	
	@Test
	public void testSetPrisTC1() {
		try {
		p1.setPris(null, 1);
		fail();
		} catch (IllegalArgumentException iea) {
			assertEquals(iea.getMessage(), "Priskategori må ikke være null");
		}
		
	}
	
	@Test 
	public void testSetPrisTC2() {
		p1.setPris(prisK1, 50d);
		assertEquals(50d, p1.getPris(prisK1), 0.0);
	}
	
	
}