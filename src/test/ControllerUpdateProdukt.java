package test;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import controller.Controller;
import model.Produkt;
import model.ProduktKategori;

public class ControllerUpdateProdukt {
	
	private static ProduktKategori pk0;
	private static ProduktKategori pk1;
	private static Produkt p0;
	
	@BeforeClass
	public static void setUp() {
		pk0 = Controller.createProduktKategori("øl");
		pk1 = Controller.createProduktKategori("oel");
		p0 = Controller.createProdukt(pk0, "IPA", "Frugtig", 1, 0);
	}
	
	@Test
	public static void testUpdateProduktTC1() {
		Produkt p0Test = new Produkt(pk1, "EEPA", "Saftig", 0, 1);
		
		assertEquals(p0Test, );
	}
}
