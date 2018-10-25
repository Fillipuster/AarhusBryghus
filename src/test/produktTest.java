package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import controller.Controller;
import model.Produkt;
import model.ProduktKategori;
import sun.net.www.content.text.plain;
import model.PrisKategori;

public class produktTest {

//	Produkt p;
	ProduktKategori pk1 = Controller.createProduktKategori("Fadøl");
	Produkt p1 = Controller.createProdukt(pk1, "Pilsner", "God øl", 1);
	

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void TC1_getProduktKategori() {
		ProduktKategori actual;
		actual = p1.getProduktKategori();
		assertEquals("Fadøl", actual);
	}

	@Test(expected = NullPointerException.class)	
	public void TC1_setPris() {
		p1.setPris(null, 50);
		
	}
//
//	@Test
//	public void TC3_setPris() {
//		PrisKategori PK = new PrisKategori("Pilsner");
//		p.setPris(PK, 50);
//		assertEquals(PK, p.getPris(PK));
//	}

}