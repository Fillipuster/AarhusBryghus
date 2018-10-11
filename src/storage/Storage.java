package storage;

import java.util.ArrayList;

import controller.Controller;
import model.PrisKategori;
import model.Produkt;
import model.ProduktKategori;
import model.Salg;

public class Storage {

	private static ArrayList<Produkt> produkter = new ArrayList<>();
	private static ArrayList<ProduktKategori> produktKategorier = new ArrayList<>();
	private static ArrayList<PrisKategori> prisKategorier = new ArrayList<>();
	private static ArrayList<Salg> salg = new ArrayList<>();

	public static void createTestData() {
		ProduktKategori flaskeøl = new ProduktKategori("Flaske Øl");
		ProduktKategori fadøl = new ProduktKategori("Fadøl");
		produktKategorier.add(flaskeøl);
		produktKategorier.add(fadøl);

		Produkt p0 = new Produkt(flaskeøl, "Klosterbryg", "Fyldig");
		produkter.add(p0);
		Produkt p1 = new Produkt(flaskeøl, "IPA", "Indian Pale Ale\nFrugtig");
		produkter.add(p1);
		Produkt p2 = new Produkt(flaskeøl, "Blonde", "Frisk");
		produkter.add(p2);

		Produkt p3 = new Produkt(fadøl, "Pilsner", "Klassiker, fra fad.");
		produkter.add(p3);
		Produkt p4 = new Produkt(fadøl, "IPA", "Indian Pale Ale\nFrugtig, fra fad.");
		produkter.add(p4);
		Produkt p5 = new Produkt(fadøl, "Blonde", "Frisk, fra fad.");
		produkter.add(p5);
		
		PrisKategori pk0 = new PrisKategori("Bar");
		PrisKategori pk1 = new PrisKategori("Butik");
		prisKategorier.add(pk0);
		prisKategorier.add(pk1);
		
		Controller.addPrisToProdukt(p0, pk0, 1);
		Controller.addPrisToProdukt(p0, pk1, 2);
		Controller.addPrisToProdukt(p1, pk0, 3);
		Controller.addPrisToProdukt(p1, pk1, 4);
		Controller.addPrisToProdukt(p2, pk0, 5);
		Controller.addPrisToProdukt(p2, pk1, 6);
		Controller.addPrisToProdukt(p3, pk0, 7);
		Controller.addPrisToProdukt(p3, pk1, 8);
		Controller.addPrisToProdukt(p4, pk0, 9);
		Controller.addPrisToProdukt(p4, pk1, 10);
		Controller.addPrisToProdukt(p5, pk0, 11);
		Controller.addPrisToProdukt(p5, pk1, 12);
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

}
