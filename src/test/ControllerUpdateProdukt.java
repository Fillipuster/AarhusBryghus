package test;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;


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
	public void testUpdateProduktTC1() {
		Produkt actual = p0;
		Produkt expected = new Produkt(pk1, "EEPA", "Saftig", 0, 1);
		Controller.updateProdukt(actual, pk1, "EEPA", "Saftig", 0, 1);

		assertEquals(expected, actual);
	}

	@Test
	public void testUpdateProduktTC2() {
		try {
			Controller.updateProdukt(null, pk1, "EEPA", "saftig", 0, 1);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(e.getMessage(), "Produkt må ikke være null.");
		}
	}

	 @Test
	 public void testUpdateProduktTC3() {
		 try {
			 Controller.updateProdukt(p0, null, "EEPA", "saftig", 0, 1);
			 fail();
		 } catch (IllegalArgumentException e) {
			 assertEquals(e.getMessage(), "ProduktKategori må ikke være null.");
		 }
	 }
	 
	 @Test
	 public void testUpdateProduktTC4() {
		 try {
			 Controller.updateProdukt(p0, pk1, null, "saftig", 0, 1);
			 fail();
		 } catch (IllegalArgumentException e) {
			 assertEquals(e.getMessage(), "Navn må ikke være null.");
		 }
	 }
	 
	 @Test
	 public void testUpdateProduktTC5() {
		 try {
			 Controller.updateProdukt(p0, pk1, "EEPA", null, 0, 1);
		 fail();
		 } catch (IllegalArgumentException e) {
			 assertEquals(e.getMessage(), "Beskrivelse må ikke være null.");
		 }
	 }
	 
	 @Test
	 public void testUpdateProduktTC6() {
		 try {
			 Controller.updateProdukt(p0, pk1, "EEPA", "Saftig", -1, 1);
			 fail();
		 } catch (IllegalArgumentException e) {
			 assertEquals(e.getMessage(), "KlipPris skal være >= 0.");
		 }
	 }
	 
	 @Test
	 public void testUpdateProduktTC7() {
		 try {
			 Controller.updateProdukt(p0, pk1, "EEPA", "saftig", 0, -1);
			 fail();
		 } catch (IllegalArgumentException e) {
			 assertEquals(e.getMessage(), "UdstedteKlip skal være >= 0.");
		 }
	 }
	 
	 @Test
	 public void testUpdateProduktTC8() {
		 try {
			 Controller.updateProdukt(p0, pk1, "EEPA", "saftig", 1, 1);
			 fail();
		 } catch (IllegalArgumentException e) {
			 assertEquals(e.getMessage(), "Klippris for klippekort skal være 0.");
		 }
	 }



}
