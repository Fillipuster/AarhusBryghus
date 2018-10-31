package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import controller.Controller;
import model.Produkt;
import model.ProduktKategori;
import model.UdlejningsProdukt;
import storage.Storage;

public class ControllerGetUdlejningsProdukterIProduktKategoriTest {

	private static ProduktKategori pk0;
	private static ProduktKategori pk1;
	private static ProduktKategori pk2;
	private static ProduktKategori pk3;

	@BeforeClass
	public static void setUp() {
		pk0 = new ProduktKategori("fustager");
		pk1 = new ProduktKategori("anlæg");
		pk2 = new ProduktKategori("kulsyre");
		pk3 = new ProduktKategori("øl");
		
		Controller.createUdlejningsProdukt(pk0, "Øl", "frugtig", 1, 2);
		
		Controller.createUdlejningsProdukt(pk1, "Sprit", "god", 2, 4);
		Controller.createUdlejningsProdukt(pk1, "Vodkas", "bedre", 3, 5);
		
		Controller.createProdukt(pk3, "adfasfd", "sdfadsfa", 8, 0);
	}


	@Test
	public void getUdlejningsProdukterIProduktKategoriTC1() {
		int counter = 0;
		for (Produkt p : Controller.getUdlejningsProdukterIProduktKategori(pk0)) {
			if (p instanceof UdlejningsProdukt) {
				counter++;
			} else {
				fail();
			}
		}
		assertEquals(1, counter, 0.0);
	}
	
	@Test
	public void getUdlejningsProdukterIProduktKategoriTC2() {
		int counter = 0;
		for (Produkt p : Controller.getUdlejningsProdukterIProduktKategori(pk1)) {
			if (p instanceof UdlejningsProdukt) {
				counter++;
			} else {
				fail();
			}
		}
		assertEquals(2, counter, 0.0);
	}
	
	@Test
	public void getUdlejningsProdukterIProduktKategoriTC3() {
		int counter = 0;
		for (Produkt p : Controller.getUdlejningsProdukterIProduktKategori(pk2)) {
			if (p instanceof UdlejningsProdukt) {
				counter++;
			} else {
				fail();
			}
		}
		assertEquals(0, counter, 0.0);
	}
	
	@Test
	public void getUdlejningsProdukterIProduktKategoriTC4() {
		int counter = 0;
		for (Produkt p : Controller.getUdlejningsProdukterIProduktKategori(pk3)) {
			if (p instanceof UdlejningsProdukt) {
				counter++;
			} else {
				fail();
			}
		}
		assertEquals(0, counter, 0.0);
	}
	
	@Test
	public void getUdlejningsProdukterIProduktKategoriTC5() {
		try {
			Controller.getProdukterIPrisKategori(null);
			fail();
		}catch (IllegalArgumentException iae) {
			assertEquals(iae.getMessage(), "PrisKategori må ikke være null.");
		}
	}
	
	
}
