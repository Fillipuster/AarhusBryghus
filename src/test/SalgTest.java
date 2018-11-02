package test;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import controller.Controller;
import model.BetalingsMetode;
import model.PrisKategori;
import model.Produkt;
import model.ProduktKategori;
import model.ProduktLinje;
import model.Salg;

public class SalgTest {

	private static Salg s0, s1, s2, s3, s4, s5;
	private static Produkt p0, p1;
	private static ProduktKategori prodK;
	private static PrisKategori prisK0, prisK1;
	private static BetalingsMetode bm0, bm1;
	private static ProduktLinje pl0, pl1;

	@BeforeClass
	public static void setUp() {
		s0 = new Salg();
		s1 = new Salg();
		s2 = new Salg();
		s4 = new Salg();
		s5 = new Salg();
		s3 = Controller.createSalg();
		bm0 = new BetalingsMetode("Dankort", false);
		bm1 = new BetalingsMetode("Klippekort", true);
		prisK0 = new PrisKategori("Bar");
		prisK1 = new PrisKategori("Butik");
		prodK = new ProduktKategori("øl");
		p0 = new Produkt(prodK, "IPA", "Indian Pale Ale.", 1, 0);
		p1 = new Produkt(prodK, "Blonde", "Frisk", 2, 0);

	}
	// --------------------------------------------------------------------
	// Test til setDato()

	@Test
	public void testSetSalgTC1() {
		s0.setDato(LocalDate.now());
		assertEquals(LocalDate.now(), s0.getDato());
	}

	@Test
	public void testSetSalgTC2() {
		s0.setDato(null);
		assertEquals(null, s0.getDato());
	}

	// --------------------------------------------------------------------
	// Test til getDato()

	@Test
	public void testGetSalgTC1() {
		s0.setDato(LocalDate.now());
		assertEquals(LocalDate.now(), s0.getDato());
	}

	// --------------------------------------------------------------------
	// Test til getBetalingsMetode()

	@Test
	public void testGetBetalingsMetodeTC1() {
		s0.setBetalingsMetode(bm0);
		assertEquals(bm0, s0.getBetalingsMetode());
	}
	// --------------------------------------------------------------------
	// Test til setBetalingsMetode()

	@Test
	public void testSetBetalingsMetodeTC1() {
		s0.setBetalingsMetode(bm0);
		assertEquals(bm0, s0.getBetalingsMetode());
	}

	@Test
	public void testSetBetalingsMetodeTC2() {
		try {
			s0.setBetalingsMetode(null);
		} catch (IllegalArgumentException e) {
			assertEquals(e.getMessage(), null);
		}
	}

	// --------------------------------------------------------------------
	// Test til getProduktLinjer()

	@Test
	public void testGetProduktLinjerTC1() {
		ArrayList<ProduktLinje> produktLinjer = new ArrayList<>();
		pl0 = s0.opretProduktLinje(p0, prisK0, 4, 0);
		pl1 = s0.opretProduktLinje(p1, prisK0, 1, 0);

		produktLinjer.add(pl0);
		produktLinjer.add(pl1);

		assertEquals(produktLinjer, s0.getProduktLinjer());
	}

	// --------------------------------------------------------------------
	// Test til opretProduktLinjer()

	@Test
	public void testOpretProduktLinjerTC1() {
		ProduktLinje expected = new ProduktLinje(p0, prisK0, 2, 0);

		pl0 = s0.opretProduktLinje(p0, prisK0, 2, 0);

		assertEquals(expected, pl0);
	}

	// --------------------------------------------------------------------
	// Test til sletProduktLinjer()

	@Test
	public void testSletProduktLinjeTC1() {
		pl0 = s1.opretProduktLinje(p0, prisK0, 2, 0);
		if (!s1.getProduktLinjer().contains(pl0)) {
			fail();
		}
		s1.sletProduktLinje(pl0);
		assertTrue(s1.getProduktLinjer().isEmpty());
	}

	// --------------------------------------------------------------------
	// Test til testGetTotalPris

	@Test
	public void testGetTotalPrisTC1() {

		pl0 = s2.opretProduktLinje(p0, prisK0, 1, 0);
		Controller.addPrisToProdukt(p0, prisK0, 100);

		assertEquals(100d, s2.getTotalPris(), 0.001);
	}

	@Test
	public void testGetTotalPrisTC2() {
		pl0 = s1.opretProduktLinje(p0, prisK0, 1, 0);
		pl1 = s1.opretProduktLinje(p1, prisK0, 1, 0);
		Controller.addPrisToProdukt(p0, prisK0, 100);
		Controller.addPrisToProdukt(p1, prisK0, 120);

		assertEquals(220d, s1.getTotalPris(), 0.001);
	}

	// --------------------------------------------------------------------
	// Test til testGetTotalKlipPris

	@Test
	public void testGetTotalKlipPrisTC1() {
		s3.setBetalingsMetode(bm1);
		pl0 = s3.opretProduktLinje(p0, prisK0, 1, 0);

		assertEquals(1d, s3.getTotalKlipPris(), 0.001);

	}

	@Test
	public void testGetTotalKlipPrisTC2() {
		s4.setBetalingsMetode(bm1);
		pl0 = s4.opretProduktLinje(p0, prisK0, 1, 0);
		pl1 = s4.opretProduktLinje(p1, prisK0, 1, 0);

		assertEquals(3, s4.getTotalKlipPris());
	}

	// --------------------------------------------------------------------
	// Test til testToString

	@Test
	public void testToStringTC1() {
		p0.setPris(prisK1, 100d);
		s5.opretProduktLinje(p0, prisK1, 2, 0);
		s5.setDato(LocalDate.now());
		assertEquals(LocalDate.now() + " (2) 200,00 kr.", s5.toString());
	}

}
