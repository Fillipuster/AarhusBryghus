package controller;

import java.util.ArrayList;

import model.PrisKategori;
import model.Produkt;
import model.ProduktKategori;
import storage.Storage;

public class Controller {

	// Produkt
	public static Produkt createProdukt(ProduktKategori kategori, String navn, String beskrivelse) {
		Produkt p = new Produkt(kategori, navn, beskrivelse);
		Storage.addProdukt(p);
		
		return p;
	}
	
	public static void updateProdukt(Produkt produkt, ProduktKategori produktKategori, String navn, String beskrivelse) {
		produkt.setProduktKategori(produktKategori);
		produkt.setNavn(navn);
		produkt.setBeskrivelse(beskrivelse);
	}
	
	public static void addPrisToProdukt(Produkt produkt, PrisKategori kategori, double pris) {
		produkt.setPris(kategori, pris);
	}
	
	public static ArrayList<Produkt> getProdukterIKategori(ProduktKategori kategori) {
		ArrayList<Produkt> result = new ArrayList<>();
		
		for (Produkt p : Storage.getProdukter()) {
			if (p.getProduktKategori() == kategori) {
				result.add(p);
			}
		}
		
		return result;
	}
	
	// ProduktKategori
	public static ProduktKategori createProduktKategori(String navn) {
		ProduktKategori pk = new ProduktKategori(navn);
		Storage.addProduktKategori(pk);
		
		return pk;
	}
	
	public static void updateProduktKategori(ProduktKategori kategori, String navn) {
		kategori.setNavn(navn);
	}

	
	// PrisKategori
	public static PrisKategori createPrisKategori(String navn) {
		PrisKategori pk = new PrisKategori(navn);
		Storage.addPrisKategori(pk);
		
		return pk;
	}
	
	public static void updatePrisKategori(PrisKategori kategori, String navn) {
		kategori.setNavn(navn);
	}
	
}
