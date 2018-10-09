package storage;

import java.util.ArrayList;

import model.PrisKategori;
import model.Produkt;
import model.ProduktKategori;

public class Storage {

	private static ArrayList<Produkt> produkter = new ArrayList<>();
	private static ArrayList<ProduktKategori> produktKategorier = new ArrayList<>();
	private static ArrayList<PrisKategori> prisKategorier = new ArrayList<>();

	public static void createTestData() {
		ProduktKategori flaskeøl = new ProduktKategori("Flaske Øl");
		ProduktKategori fadøl = new ProduktKategori("Fadøl");
		produktKategorier.add(flaskeøl);
		produktKategorier.add(fadøl);

		produkter.add(new Produkt(flaskeøl, "Klosterbryg", "Fyldig"));
		produkter.add(new Produkt(flaskeøl, "IPA", "Indian Pale Ale\nFrugtig"));
		produkter.add(new Produkt(flaskeøl, "Blonde", "Frisk"));

		produkter.add(new Produkt(fadøl, "Pilsner", "Klassiker, fra fad."));
		produkter.add(new Produkt(fadøl, "IPA", "Indian Pale Ale\nFrugtig, fra fad."));
		produkter.add(new Produkt(fadøl, "Blonde", "Frisk, fra fad."));
	}

	public static ArrayList<Produkt> getProdukter() {
		return new ArrayList<>(produkter);
	}
	
	public static void addProdukt(Produkt produkt) {
		produkter.add(produkt);
	}

	public static void removeProdukt(Produkt produkt) {
		produkter.remove(produkt);
	}
	
	public static ArrayList<ProduktKategori> getProduktKategorier() {
		return new ArrayList<>(produktKategorier);
	}
	
	public static ArrayList<PrisKategori> getPrisKategorier() {
		return new ArrayList<>(prisKategorier);
	}

}
