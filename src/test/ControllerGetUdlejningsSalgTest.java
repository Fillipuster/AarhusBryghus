package test;

import model.BetalingsMetode;
import model.PrisKategori;
import model.Produkt;
import model.ProduktKategori;
import model.Salg;
import model.UdlejningsProdukt;
import model.UdlejningsSalg;

import org.junit.BeforeClass;
import org.junit.Test;

import controller.Controller;

public class ControllerGetUdlejningsSalgTest {
	
	private static ProduktKategori pk0;
	private static ProduktKategori pk1; 
	private static ProduktKategori pk2; 
	private static PrisKategori prisK0;
	
	private static BetalingsMetode bm0; 
	
	private static Produkt p0;
	private static Produkt p1; 
	private static Produkt p3;
	
	@BeforeClass
	public static void setUp() {
		pk0 = new ProduktKategori("fustager");
		pk1 = new ProduktKategori("anlæg");
		pk2 = new ProduktKategori("øl");
		
		p0 = new UdlejningsProdukt(pk0, "Fadøl", "33 ck", 50, 100);
		p1 = new UdlejningsProdukt(pk1, "Anlæg", "2 Hane", 2, 10);
		
		p1 = new Produkt(pk2, "IPA", "god øl", 10, 1);
		
		prisK0 = new PrisKategori("Mange");
		
		bm0 = new BetalingsMetode("MobilePay", false);
		
		UdlejningsSalg us0 = Controller.createUdlejningsSalg();
		us0.opretProduktLinje(p0, prisK0, 1, 0);
		Controller.setSalgBetalingsMetode(us0, bm0);
		Controller.saveSalg(us0);
		
		UdlejningsSalg us1 = Controller.createUdlejningsSalg();
		us1.opretProduktLinje(p1, prisK0, 2, 2);
		Controller.setSalgBetalingsMetode(us1, bm0);
		Controller.saveSalg(us1);
		
		Salg s1 = Controller.createUdlejningsSalg();
		s1.opretProduktLinje(p3, prisK0, 1, 0);
		Controller.setSalgBetalingsMetode(s1, bm0);
		Controller.saveSalg(s1);
	}
	
	@Test
	public void getUdlejnignsSalgTC1() {
		
	}
	

}
