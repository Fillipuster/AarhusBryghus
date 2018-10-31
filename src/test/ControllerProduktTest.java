package test;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import controller.Controller;
import model.PrisKategori;
import model.Produkt;
import model.ProduktKategori;
import storage.Storage;

public class ControllerProduktTest {

	PrisKategori priskat0;
	ProduktKategori produktkat0;
	Produkt p1;
	Produkt p2;
	Produkt p3;

	@Before
	public void setUp() throws Exception {
		priskat0 = new PrisKategori("Bar");
		produktkat0 = new ProduktKategori("Fadøl");
		p1 = new Produkt(produktkat0, "IPA", "Bedste øl", 1, 0);
		p3 = new Produkt(produktkat0, "Pils", "Frugtig", 2, 0);
	}

	// -------------------------------------------------------------------------------------------------------------------------
	// Test cases for createProdukt

	@Test(expected = IllegalArgumentException.class)
	public void testCreateProduktTC1() {
		Produkt actual = p1;
		Produkt p = Controller.createProdukt(null, "IPA", "Bedste øl", 1, 0 );
		assertEquals(p, actual);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateProduktTC2() {
		Produkt actual = p1;
		Produkt p = Controller.createProdukt(produktkat0, null, "Bedste øl", 1, 0);
		assertEquals(p, actual);
	}

	@Test
	public void testCreateProduktTC3() {
		Produkt actual = p1;
		Produkt p2 = Controller.createProdukt(produktkat0, "IPA", "Bedste øl", 1, 0);
		assertEquals(p2, actual);
	}
	
	@Test
	public void TestCreateProduktTC4() {
		
	}

	// -------------------------------------------------------------------------------------------------------------------------
	// Test cases for updateProdukt

	@Test
	public void testUpdateProduktTC1() {
		try {
			Controller.updateProdukt(null, produktkat0, "IPA", "Bedre end bedste", 1);
			fail();
		} catch (IllegalArgumentException iae) {
			assertEquals(iae.getMessage(), "Produkt må ikke være null.");
		}
	}

	@Test
	public void testUpdateProduktTC2() {
		try {
			Controller.updateProdukt(p1, null, "IPA", "Bedre end bedste", 1);
			fail();
		} catch (IllegalArgumentException iae) {
			assertEquals(iae.getMessage(), "ProduktKategori må ikke være null.");
		}
	}

	@Test
	public void testUpdateProduktTC3() {
		Produkt actual = p1;
		Controller.updateProdukt(p1, produktkat0, "IPA", "Bedre end bedste", 1);
		Produkt p = new Produkt(produktkat0, "IPA", "Bedre end bedste", 1, 0);
		assertEquals(p, actual);
	}

	// -------------------------------------------------------------------------------------------------------------------------
	// Test cases for addPrisToProdukt

	@Test
	public void testAddPrisToProduktTC1() {
		try {
			Controller.addPrisToProdukt(p1, null, 21);
			fail();
		} catch (IllegalArgumentException iae) {
			assertEquals(iae.getMessage(), "PrisKategori må ikke være null.");
		}
	}

	@Test
	public void testAddPrisToProduktTC2() {
		try {
			Controller.addPrisToProdukt(null, priskat0, 21);
			fail();
		} catch (IllegalArgumentException iae) {
			assertEquals(iae.getMessage(), "Produkt må ikke være null.");
		}
	}

	@Test
	public void testAddPrisToProduktTC3() {
		Produkt actual = p1;
		Produkt p = new Produkt(produktkat0, "IPA", "Bedste øl", 1, 0);
		p.setPris(priskat0, 21);
		Controller.addPrisToProdukt(p1, priskat0, 21);

		assertEquals(p, actual);
	}

	@Test
	public void testAddPrisToProduktTC4() {
		try {
			Controller.addPrisToProdukt(p1, priskat0, -21);
			fail();
		} catch (IllegalArgumentException iae) {
			assertEquals(iae.getMessage(), "Pris skal være > 0.");
		}
	}

	@Test
	public void testAddPrisToProduktTC5() {
		try {
			Controller.addPrisToProdukt(p1, priskat0, 0);
			fail();
		} catch (IllegalArgumentException iae) {
			assertEquals(iae.getMessage(), "Pris skal være > 0.");
		}
	}
	// -------------------------------------------------------------------------------------------------------------------------
	// Test cases for getProdukterIKategori

	@Test
	public void testGetProduktIKategoriTC1() {
		try {
			Controller.getProdukterIKategori(null);
			fail();
		} catch (IllegalArgumentException iae) {
			assertEquals(iae.getMessage(), "ProduktKategori må ikke være null.");
		}
	}

	@Test
	public void testGetProdukterIKategoriTC3() {
		Storage.addPrisKategori(priskat0);
		Storage.addProdukt(p1);
		Storage.addProdukt(p3);
		Storage.addProduktKategori(produktkat0);

		ArrayList<Produkt> produkter = new ArrayList<>();
		produkter.add(p1);
		produkter.add(p3);
		assertEquals(produkter, Controller.getProdukterIKategori(produktkat0));
	}

	// -------------------------------------------------------------------------------------------------------------------------
	// Test cases for getProdukterIPrisKategori

	@Test
	public void getProdukterIPrisKategoriTC1() {
		try {
			Controller.getProdukterIPrisKategori(null);
			fail();
		} catch (IllegalArgumentException iae) {
			assertEquals(iae.getMessage(), "PrisKategori må ikke være null.");
		}
	}

	@Test
	public void getProdukterIPrisKategoriTC2() {
		Storage.addPrisKategori(priskat0);
		Storage.addProdukt(p1);
		Storage.addProdukt(p3);
		Storage.addProduktKategori(produktkat0);

		ArrayList<Produkt> produkter = new ArrayList<>();
		produkter.add(p1);
		produkter.add(p3);

		assertEquals(produkter, Controller.getProdukterIKategori(produktkat0));
	}

}
