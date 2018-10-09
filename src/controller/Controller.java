package controller;

import java.util.ArrayList;

import model.Produkt;
import model.ProduktKategori;
import storage.Storage;

public class Controller {

	public static ArrayList<Produkt> getProdukterIKategori(ProduktKategori kategori) {
		ArrayList<Produkt> result = new ArrayList<>();
		
		for (Produkt p : Storage.getProdukter()) {
			if (p.getProduktKategori() == kategori) {
				result.add(p);
			}
		}
		
		return result;
	}
	
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
	
}
