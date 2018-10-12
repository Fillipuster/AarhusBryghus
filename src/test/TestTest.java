package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.Produkt;
import model.ProduktKategori;
import model.PrisKategori;

public class TestTest {

Produkt p;
	
	@Before
	public void setUp() throws Exception {
//		p = new Produkt(new ProduktKategori("Fadøl"),"Pilsner", "God øl");
	}

	@Test
	public void TC1_getProduktKategori() {
		assertEquals("Fadøl", p.getProduktKategori().getNavn());
	}
	

	@Test(expected = NullPointerException.class)
	public void TC1_setPris() {
		p.setPris(null, 50);
		assertEquals(null, p.getPris(null));
	}
	
	@Test
	public void TC3_setPris() {
		PrisKategori PK = new PrisKategori("Pilsner");
		p.setPris(PK, 50);
		assertEquals(PK, p.getPris(PK));
	}
	

}