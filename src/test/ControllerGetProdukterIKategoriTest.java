package test;

import java.util.ArrayList;
import org.junit.BeforeClass;
import org.junit.Test;

import controller.Controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import model.Produkt;
import model.ProduktKategori;

public class ControllerGetProdukterIKategoriTest {

	private static ProduktKategori pk0;
	private static ProduktKategori pk1;
	private static ProduktKategori pk2;
	private static Produkt p0;
	private static Produkt p1;
	private static Produkt p2;
	
	
	@BeforeClass
	public static void setUp() {
		pk0 = Controller.createProduktKategori("øl");
		pk1 = Controller.createProduktKategori("glas");
		pk2 = Controller.createProduktKategori("klippekort");
		
		p0 = Controller.createProdukt(pk0, "IPA", "Bedste øl", 0, 0);
		p1 = Controller.createProdukt(pk1, "Glas", "Skrøblig", 0, 0);
		p2 = Controller.createProdukt(pk1, "Plastic", "Kraftig", 0, 0);
	}
	
	@Test
	public void testGetProdukterIKategoriTC1() {
		ArrayList<Produkt> expected = new ArrayList<>();
		
		expected.add(p0);
			
		assertEquals(expected, Controller.getProdukterIKategori(pk0));
	}
	
	@Test
	public void testGetProdukterIKategoriTC2() {
		ArrayList<Produkt> expected = new ArrayList<>();
		
		expected.add(p1);
		expected.add(p2);
			
		assertEquals(expected, Controller.getProdukterIKategori(pk1));
	}
	
	@Test
	public void testGetProdukterIKategoriTC3() {
		ArrayList<Produkt> expected = new ArrayList<>();
	
		assertEquals(expected, Controller.getProdukterIKategori(pk2));
	}
	
	@Test
	public void testGetProdukterIKategoriTC4() {
		try {
			Controller.getProdukterIKategori(null);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(e.getMessage(), "ProduktKategori må ikke være null.");
		}
	}
	
}
