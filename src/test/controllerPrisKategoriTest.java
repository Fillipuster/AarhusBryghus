package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import controller.Controller;
import model.PrisKategori;

public class controllerPrisKategoriTest {

	PrisKategori pk1;
	
	@Before
	public void setUp() throws Exception {
		pk1 = new PrisKategori("Bar");
		
	}
	// -------------------------------------------------------------------------------------------------------------------------
			// Test cases for CreatePrisKategori
		
		@Test
		public void testCreatePrisKategoriTC1() {
			PrisKategori actual = pk1;
			PrisKategori pk2 = Controller.createPrisKategori("Bar");
			assertEquals(pk2, actual);
		}
		

}
