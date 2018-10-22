package storage;

import java.util.ArrayList;

import controller.Controller;
import model.BetalingsMetode;
import model.PrisKategori;
import model.Produkt;
import model.ProduktKategori;
import model.Salg;

public class Storage {
	
	// Kategorier der er nødvendige for at gaveæsker kan fungere;
	private static PrisKategori butikPrisKategori;
	private static ProduktKategori glasProduktKategori;
	private static ProduktKategori flaskeølProduktKategori;
	
	// Lister;
	private static ArrayList<Produkt> produkter = new ArrayList<>();
	private static ArrayList<ProduktKategori> produktKategorier = new ArrayList<>();
	private static ArrayList<PrisKategori> prisKategorier = new ArrayList<>();
	private static ArrayList<Salg> salg = new ArrayList<>();
	private static ArrayList<BetalingsMetode> betalingsMetoder = new ArrayList<>();

	public static void initializeStorage() {
		butikPrisKategori = Controller.createPrisKategori("Butik");
		glasProduktKategori = Controller.createProduktKategori("Glas");
		flaskeølProduktKategori = Controller.createProduktKategori("Flaske Øl");

		ProduktKategori flaskeøl = new ProduktKategori("Flaske Øl");
		ProduktKategori fadøl = new ProduktKategori("Fadøl");
		produktKategorier.add(flaskeøl);
		produktKategorier.add(fadøl);

		Produkt p0 = new Produkt(flaskeøl, "Klosterbryg", "Fyldig", 1);
		produkter.add(p0);
		Produkt p1 = new Produkt(flaskeøl, "IPA", "Indian Pale Ale\nFrugtig", -1);
		produkter.add(p1);
		Produkt p2 = new Produkt(flaskeøl, "Blonde", "Frisk", 2);
		produkter.add(p2);

		Produkt p3 = new Produkt(fadøl, "Pilsner", "Klassiker, fra fad.", 1);
		produkter.add(p3);
		Produkt p4 = new Produkt(fadøl, "IPA", "Indian Pale Ale\nFrugtig, fra fad.", -1);
		produkter.add(p4);
		Produkt p5 = new Produkt(fadøl, "Blonde", "Frisk, fra fad.", 3);
		produkter.add(p5);
		
		PrisKategori pk1 = new PrisKategori("Bar");
		prisKategorier.add(pk1);
		
		Controller.addPrisToProdukt(p0, butikPrisKategori, 1);
		Controller.addPrisToProdukt(p0, pk1, 2);
		Controller.addPrisToProdukt(p1, butikPrisKategori, 3);
		Controller.addPrisToProdukt(p1, pk1, 4);
		Controller.addPrisToProdukt(p2, butikPrisKategori, 5);
		Controller.addPrisToProdukt(p2, pk1, 6);
		Controller.addPrisToProdukt(p3, butikPrisKategori, 7);
		Controller.addPrisToProdukt(p3, pk1, 8);
		Controller.addPrisToProdukt(p4, butikPrisKategori, 9);
		Controller.addPrisToProdukt(p4, pk1, 10);
		Controller.addPrisToProdukt(p5, butikPrisKategori, 11);
		Controller.addPrisToProdukt(p5, pk1, 12);
		
		Controller.createBetalingsMetode("Kreditkort", false);
		Controller.createBetalingsMetode("Kontant", false);
		Controller.createBetalingsMetode("MobilePay", false);
		Controller.createBetalingsMetode("Klippekort", true);
	}
	
	// Getters for essential categories;
	public static PrisKategori getButikPrisKategori() {
		return butikPrisKategori;
	}
	
	public static ProduktKategori getFlaskeølProduktKategori() {
		return flaskeølProduktKategori;
	}
	
	public static ProduktKategori getGlasProduktKategori() {
		return glasProduktKategori;
	}
	
	// Produkt
	public static ArrayList<Produkt> getProdukter() {
		return new ArrayList<>(produkter);
	}
	
	public static void addProdukt(Produkt produkt) {
		produkter.add(produkt);
	}

	public static void removeProdukt(Produkt produkt) {
		produkter.remove(produkt);
	}
	
	// ProduktKategori
	public static ArrayList<ProduktKategori> getProduktKategorier() {
		return new ArrayList<>(produktKategorier);
	}

	public static void addProduktKategori(ProduktKategori pk) {
		produktKategorier.add(pk);
	}
	
	public static void removeProduktKategori(ProduktKategori pk) {
		produktKategorier.remove(pk);
	}
	
	// PrisKategori
	public static ArrayList<PrisKategori> getPrisKategorier() {
		return new ArrayList<>(prisKategorier);
	}

	public static void addPrisKategori(PrisKategori pk) {
		prisKategorier.add(pk);
	}
	
	public static void removePrisKategori(PrisKategori pk) {
		prisKategorier.remove(pk);
	}
	
	// Salg
	public static ArrayList<Salg> getSalg() {
		return new ArrayList<>(salg);
	}
	
	public static void addSalg(Salg s) {
		salg.add(s);
	}
	
	public static void removeSalg(Salg s) {
		salg.remove(s);
	}
	
	// BetalingsMetode
	public static ArrayList<BetalingsMetode> getBetalingsMetoder() {
		return new ArrayList<>(betalingsMetoder);
	}
	
	public static void addBetalingsMetode(BetalingsMetode bm) {
		betalingsMetoder.add(bm);
	}
	
	public static void removeBetalingsMetode(BetalingsMetode bm) {
		betalingsMetoder.remove(bm);
	}

}
