package test;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import controller.Controller;
import model.ProduktKategori;
import model.UdlejningsProdukt;
import storage.Storage;

public class ControllerGetUdlejligeProduktKategorier {

	ProduktKategori pk0;
	UdlejningsProdukt up0;
	UdlejningsProdukt up1;

	@Before
	public void setUp() {
		pk0 = new ProduktKategori("Butik");
		up0 = new UdlejningsProdukt(pk0, "IPA", "Bedste øl", 499, 100);
		up1 = new UdlejningsProdukt(pk0, "Blondie", "Blid", 599, 100);
		
	}

	@Test
	public void testGetUdlejningsProduktKategorierTC1() {
		ArrayList<ProduktKategori> expected = new ArrayList<>();

		Storage.addProduktKategori(pk0);
		Storage.addProdukt(up0);
		Storage.addProdukt(up1);

		expected.add(pk0);

		assertEquals(expected, Controller.getUdlejligeProduktKategorier());
	}
}
