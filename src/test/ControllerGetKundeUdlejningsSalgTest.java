package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import controller.Controller;
import model.Kunde;
import model.PrisKategori;
import model.ProduktKategori;
import model.UdlejningsProdukt;
import model.UdlejningsSalg;

public class ControllerGetKundeUdlejningsSalgTest {
	
	private static Kunde k0, k1;
	private static UdlejningsSalg us0;
	
	@BeforeClass
	public static void setUp() {
		k0 = Controller.createKunde("Peter Justesen", "Aarhus", "112");
		k1 = Controller.createKunde("Hanne Sommer", "Viby", "911");
		
		ProduktKategori prodk0 = Controller.createProduktKategori("fustager");
		PrisKategori prisk0 = Controller.createPrisKategori("butik");
		UdlejningsProdukt p0 = Controller.createUdlejningsProdukt(prodk0, "IPA", "frugtig", 500d, 100d);
		
		us0 = Controller.createUdlejningsSalg();
		us0.opretProduktLinje(p0, prisk0, 2, 0d);
		Controller.setUdlejningsSalgKunde(us0, k0);
		Controller.saveSalg(us0);
	}
	
	@Test
	public void testGetKundeUdlejningsSalgTC1() {
		ArrayList<UdlejningsSalg> expected = new ArrayList<>();
		expected.add(us0);
		
		assertEquals(expected, Controller.getKundeAktiveUdlejningsSalg(k0));
	}
	
	@Test
	public void testGetKundeUdlejningsSalgTC2() {
		assertTrue(Controller.getKundeAktiveUdlejningsSalg(k1).isEmpty());
	}
	
	@Test
	public void testGetKundeUdlejningsSalgTC3() {
		try {
			Controller.getKundeAktiveUdlejningsSalg(null);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Kunde må ikke være null.", e.getMessage());
		}
	}
	
	

}
