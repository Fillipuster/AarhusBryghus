package test;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;
import controller.Controller;
import model.Kunde;
import model.UdlejningsSalg;

public class ControllerGetKundeAktiveUdlejningsSalg {

	private static Kunde k0;
	private static Kunde k1;
	private static UdlejningsSalg us0;
	private static UdlejningsSalg us1;

	@BeforeClass
	public static void setUp() {
		k0 = Controller.createKunde("Peter Justesen", "Aarhus", "112");
		k1 = Controller.createKunde("Hanne Sommer", "Viby", "911");
		
		us0 = Controller.createUdlejningsSalg();
		us0.setRetuneringsDato(null);
		Controller.setUdlejningsSalgKunde(us0, k0);
		Controller.saveSalg(us0);

		us1 = Controller.createUdlejningsSalg();
		us1.setRetuneringsDato(LocalDate.now());
		Controller.setUdlejningsSalgKunde(us1, k1);
		Controller.saveSalg(us1);

	}

	@Test
	public void testGetKundeAktiveUdlejningsSalgTC1() {
		ArrayList<UdlejningsSalg> expected = new ArrayList<>();

		expected.add(us0);

		assertEquals(expected, Controller.getKundeAktiveUdlejningsSalg(k0));

	}

	@Test
	public void testGetKundeAktiveUdlejningsSalgTC2() {
		assertTrue(Controller.getKundeAktiveUdlejningsSalg(k1).isEmpty());
	}
	
	@Test
	public void testGetKundeAktiveUdlejningsSalgTC3() {
		try {
			Controller.getKundeAktiveUdlejningsSalg(null);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Kunde må ikke være null.", e.getMessage());
		}
	}

}
