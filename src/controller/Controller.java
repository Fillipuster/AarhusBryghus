package controller;

import java.time.LocalDate;
import java.util.ArrayList;

import model.PrisKategori;
import model.Produkt;
import model.ProduktKategori;
import model.ProduktLinje;
import model.Salg;
import storage.Storage;

public class Controller {

	// Produkt
	public static Produkt createProdukt(ProduktKategori kategori, String navn, String beskrivelse, int klipPris) {
		Produkt p = new Produkt(kategori, navn, beskrivelse, klipPris);
		Storage.addProdukt(p);
		
		return p;
	}
	
	public static void updateProdukt(Produkt produkt, ProduktKategori produktKategori, String navn, String beskrivelse, int klipPris) {
		produkt.setProduktKategori(produktKategori);
		produkt.setNavn(navn);
		produkt.setBeskrivelse(beskrivelse);
		produkt.setKlipPris(klipPris);
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
	
	public static ArrayList<Produkt> getProdukterIPrisKategori(PrisKategori prisKategori) {
		ArrayList<Produkt> result = new ArrayList<>();

		for (Produkt p : Storage.getProdukter()) {
			if (!Double.isNaN(p.getPris(prisKategori))) {
				result.add(p);
			}
		}
		return result;
	}
	
//	Salg
	public static Salg createSalg() {
		return new Salg(LocalDate.of(9002, 6, 14));
	}
	
	public static ProduktLinje createProduktLinje(Salg salg, Produkt produkt, PrisKategori prisKategori, int antal, double rabat) {
		return salg.opretProduktLinje(produkt, prisKategori, antal, rabat);
	}
	
	public static void updateProduktLinje(ProduktLinje produktLinje, int antal, double rabat) {
		produktLinje.setAntal(antal);
		produktLinje.setRabat(rabat);
	}
	
	public static void saveSalg(Salg salg) {
		Storage.addSalg(salg);
	}
	
 }
