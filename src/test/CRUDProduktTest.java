package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import model.PrisKategori;
import model.Produkt;
import model.ProduktKategori;
import storage.Storage;
import controller.Controller;

public class CRUDProduktTest {
	
	private static ProduktKategori pk0;
	private static ProduktKategori pk1; 
	private static PrisKategori PrisK0;
	private static PrisKategori PrisK1; 
	private static Produkt p0; 
	private static Produkt p1; 
	private static Produkt p2;
	private static Produkt p3;
	
	@BeforeClass
	public static void setUp() {
		pk0 = Controller.createProduktKategori("fadøl");
		pk1 = Controller.createProduktKategori("flaske øl");
		PrisK0 = Controller.createPrisKategori("bar");
		PrisK1 = Controller.createPrisKategori("butik");
		p0 = Controller.createProdukt(pk0, "pilsner", "dejlig fra fad", 1, 0);
		p1 = Controller.createProdukt(pk0, "IPA", "god øl", 1, 0);
		p2 = Controller.createProdukt(pk0, "blondie", "lys øl", 1, 0);

	}
	// -------------------------------------------------------------------------------------------------------------------------
	// Test cases for addPrisToProdukt
	
	@Test
	public void addPrisToProduktTC1() {
		Produkt expected = new Produkt(pk0, "pilsner", "dejlig fra fad", 1, 0);
		expected.setPris(PrisK0, 50);
		expected.setPris(PrisK1, 70);
		
		Controller.addPrisToProdukt(p0, PrisK0, 50);
		Controller.addPrisToProdukt(p0, PrisK1, 70);
		
		boolean pris0Matches = expected.getPris(PrisK0) == p0.getPris(PrisK0);
		boolean pris1Matches = expected.getPris(PrisK1) == p0.getPris(PrisK1);
		
		assertTrue(pris1Matches && pris0Matches);
	}
	
	@Test
	public void addPrisToProduktTC2() {
		Produkt expected = new Produkt(pk0, "pilsner", "dejlig fra fad", 1, 0);
		expected.setPris(PrisK0, 40);
		
		Controller.addPrisToProdukt(p0, PrisK0, 40);
		
		assertEquals(expected, p0);
	}
	
	@Test
	public void addPrisToProduktTC3() {
		try {
			Controller.addPrisToProdukt(p0, PrisK0, -1);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(e.getMessage(), "Pris skal være > 0.");
		}
	}
	
	@Test
	public void addPrisToProduktTC4() {
		try {
			Controller.addPrisToProdukt(p0, null, 1);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(e.getMessage(), "PrisKategori må ikke være null.");
		}
	}
	
	@Test
	public void addPrisToProduktTC5() {
		try {
			Controller.addPrisToProdukt(null, PrisK0, 1);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(e.getMessage(), "Produkt må ikke være null.");
		}
	}
	// -------------------------------------------------------------------------------------------------------------------------
	// Test cases for getProdukter
	
	@Test
	public void getProdukterTC1() {
		ArrayList<Produkt> expected = new ArrayList<>();
		
		expected.add(p0);
		expected.add(p1);
		expected.add(p2);
		
		assertEquals(expected, Storage.getProdukter());
	}
	// -------------------------------------------------------------------------------------------------------------------------
	// Test cases for removeProdukt
	
	@Test
	public void removeProduktTC1() {
		ArrayList<Produkt> expected = new ArrayList<>();
		
		Produkt pt = new Produkt(pk0, "", "", 1, 0);
		
		expected.add(p0);
		expected.add(p1);
		expected.add(p2);
		
		Storage.addProdukt(pt);
		
		if (!Storage.getProdukter().contains(pt)) {
			fail();
		}
		
		Storage.removeProdukt(pt);
		
		assertEquals(expected, Storage.getProdukter());
	}
	
	// -------------------------------------------------------------------------------------------------------------------------
	// Test cases for addPrisToProdukt
	
	@Test
	public void addProduktTC1() {
		p3 = Controller.createProdukt(pk0, "brownie", "mærk øl", 1, 0);
		
		ArrayList<Produkt> expected = new ArrayList<>();
		
		expected.add(p0);
		expected.add(p1);
		expected.add(p2);
		expected.add(p3);
		
		assertEquals(expected, Storage.getProdukter());
	}
	// -------------------------------------------------------------------------------------------------------------------------
	// Test cases for setPris
	
	@Test
	public void setPrisTC1(){
		Produkt expected = new Produkt(pk0, "pilsner", "dejlig fra fad", 1, 0);
		Controller.addPrisToProdukt(expected, PrisK0, 2);
		
		p0.setPris(PrisK0, 2);
		
		assertEquals(expected, 5);
	}
	// -------------------------------------------------------------------------------------------------------------------------
	// Test cases for setNavn
	
	@Test
	public void setNavnTC1() {
		Produkt p4 = new Produkt(pk0, "pilsner", "dejlig fra fad", 1, 0);
		p4.setNavn("julebryg");
		
		assertEquals("julebryg", p4.getNavn());
	}
	// -------------------------------------------------------------------------------------------------------------------------
	// Test cases for setProduktKategori
	
	@Test 
	public void setProduktKategoriTC1() {
		Produkt p4 = new Produkt(pk0, "pilsner", "dejlig fra fad", 1, 0);
		p4.setProduktKategori(pk1);
		
		assertEquals(pk1, p4.getProduktKategori());
	}
	// -------------------------------------------------------------------------------------------------------------------------
	// Test cases for setBeskrivelse
	
	@Test
	public void setBeskrivelseTC1() {
		Produkt p4 = new Produkt(pk0, "pilsner", "dejlig fra fad", 1, 0);
		p4.setBeskrivelse("test");
		
		assertEquals("test", p4.getBeskrivelse());
	}
	// -------------------------------------------------------------------------------------------------------------------------
	// Test cases for setKlipPris
	
	@Test
	public void setKlipPrisTC1() {
		Produkt p4 = new Produkt(pk0, "pilsner", "dejlig fra fad", 1, 0);
		p4.setKlipPris(2);
		
		assertEquals(2, p4.getKlipPris());
	}
	// -------------------------------------------------------------------------------------------------------------------------
	// Test cases for setKlipPris
	
	@Test
	public void setUdstedteKlipTC1() {
		Produkt p4 = new Produkt(pk0, "pilsner", "dejlig fra fad", 0, 1);
		p4.setUdstedteKlip(2);
		
		assertEquals(2, p4.getUdstedteKlip());
	}

	
	
	
	

	// -------------------------------------------------------------------------------------------------------------------------
	// Test cases for createProdukt

	/* For at se test case for createProdukt henvises der til klassen ControllerCreateProduktTest*/
	
	// -------------------------------------------------------------------------------------------------------------------------
	// Test cases for updateProdukt
	
	/* For at se test case for updateProdukt henvises der til klassen ControllerUpdateProduktTest*/
	
}
