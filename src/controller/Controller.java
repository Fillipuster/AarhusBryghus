package controller;

import java.time.LocalDate;
import java.util.ArrayList;

import model.BetalingsMetode;
import model.Gaveaeske;
import model.GaveaeskePakning;
import model.GaveaeskePreset;
import model.PrisKategori;
import model.Produkt;
import model.ProduktKategori;
import model.ProduktLinje;
import model.Salg;
import storage.Storage;

public class Controller {

	// Produkt
	public static Produkt createProdukt(ProduktKategori kategori, String navn, String beskrivelse, int klipPris) {
		if (kategori == null) {
			throw new IllegalArgumentException("Kategori kan ikke være null");
		}
		if (navn == null) {
			throw new IllegalArgumentException("Navn kan ikke være null");
		}
		if (beskrivelse == null) {
			throw new IllegalArgumentException("Beskrivelse kan ikke være null");
		}
		
		Produkt p = new Produkt(kategori, navn, beskrivelse, klipPris);
		Storage.addProdukt(p);

		return p;
	}

	public static void updateProdukt(Produkt produkt, ProduktKategori produktKategori, String navn, String beskrivelse,
			int klipPris) {
		if (produkt == null) {
			throw new IllegalArgumentException("Produkt kan ikke være null");
		}
		if (produktKategori == null) {
			throw new IllegalArgumentException("ProduktKategori kan ikke være null");
		}
		if (navn == null) {
			throw new IllegalArgumentException("Navn kan ikke være null");
		}
		if (beskrivelse == null) {
			throw new IllegalArgumentException("Beskrivelse kan ikke være null");
		}
		//TODO LAV FUCKING KLIPPRISEN SÅ DEN FUCKING IKKE KAN VÆRE FUCKING NEGATIV (jonas :D)
		
		produkt.setProduktKategori(produktKategori);
		produkt.setNavn(navn);
		produkt.setBeskrivelse(beskrivelse);
		produkt.setKlipPris(klipPris);
	}

	public static void addPrisToProdukt(Produkt produkt, PrisKategori prisKategori, double pris) {
		if (produkt == null) {
			throw new IllegalArgumentException("Produkt kan ikke være null");
		}
		if (prisKategori == null) {
			throw new IllegalArgumentException("Priskategori kan ikke være null");
		}
		if (pris < 0) {
			throw new IllegalArgumentException("pris kan ikke være negativt");
		}
		if (pris == 0) {
			throw new IllegalArgumentException("Prisen kan ikke være 0");
		}
		
		produkt.setPris(prisKategori, pris);
	}

	public static ArrayList<Produkt> getProdukterIKategori(ProduktKategori kategori) {
		if (kategori == null) {
			throw new IllegalArgumentException("Produktkategori kan ikke være null");
		}
		
		ArrayList<Produkt> result = new ArrayList<>();

		for (Produkt p : Storage.getProdukter()) {
			if (p.getProduktKategori() == kategori) {
				result.add(p);
			}
		}

		return result;
	}

	public static ArrayList<Produkt> getProdukterIPrisKategori(PrisKategori prisKategori) {
		if (prisKategori == null) {
			throw new IllegalArgumentException("Produktkategori kan ikke være null");
		}
		
		ArrayList<Produkt> result = new ArrayList<>();

		for (Produkt p : Storage.getProdukter()) {
			if (!Double.isNaN(p.getPris(prisKategori))) {
				result.add(p);
			}
		}
		
		return result;
	}

	// ProduktKategori
	public static ProduktKategori createProduktKategori(String navn) {
		if (navn == null) {
			throw new IllegalArgumentException("Navn må ikke være null");
		}
		
		
		ProduktKategori pk = new ProduktKategori(navn);
		Storage.addProduktKategori(pk);

		return pk;
	}

	public static void updateProduktKategori(ProduktKategori kategori, String navn) {
		if (kategori == null) {
			throw new IllegalArgumentException("Kategori må ikke være null");
		}
		if (navn == null) {
			throw new IllegalArgumentException("Navn må ikke være null");
		}
		
		kategori.setNavn(navn);
	}

	// PrisKategori
	public static PrisKategori createPrisKategori(String navn) {
		PrisKategori pk = new PrisKategori(navn);
		Storage.addPrisKategori(pk);

		return pk;
	}

	public static void updatePrisKategori(PrisKategori kategori, String navn) {
		if (kategori == null) {
			throw new IllegalArgumentException("Kategori må ikke være null");
		}
		if (navn == null) {
			throw new IllegalArgumentException("Navn må ikke være null");
		}
		
		kategori.setNavn(navn);
	}

	// Salg
	public static Salg createSalg() {
		return new Salg(LocalDate.of(1, 1, 1));
	}

	public static void saveSalg(Salg salg) {
		Storage.addSalg(salg);
	}
	
	public static void setSalgBetalingsMetode(Salg salg, BetalingsMetode betalingsMetode) {
		salg.setBetalingsMetode(betalingsMetode);
	}

	// ProduktLinje
	public static ProduktLinje createProduktLinje(Salg salg, Produkt produkt, PrisKategori prisKategori, int antal,
			double rabat) {
		return salg.opretProduktLinje(produkt, prisKategori, antal, rabat);
	}

	public static void updateProduktLinje(ProduktLinje produktLinje, int antal, double rabat) {
		produktLinje.setAntal(antal);
		produktLinje.setRabat(rabat);
	}
	
	// BetalingsMetode
	public static BetalingsMetode createBetalingsMetode(String navn, boolean brugerKlip) {
		BetalingsMetode bm = new BetalingsMetode(navn, brugerKlip);
		Storage.addBetalingsMetode(bm);
		
		return bm;
	}
	
	public static void updateBetalingsMetode(BetalingsMetode bm, String navn) {
		bm.setNavn(navn);
	}
	
	// Gaveæsker
	public static Gaveaeske createGaveaeske() {
		Gaveaeske g = new Gaveaeske();
		
		return g;
	}
	
	public static void setGaveaeskePakning(Gaveaeske gaveaeske, GaveaeskePakning pakning) {
		gaveaeske.setPakning(pakning);
	}
	
	// GaveæskePreset
	public static GaveaeskePreset createGaveaeskePreset(int øl, int glas, double pris, GaveaeskePakning pakning) {
		GaveaeskePreset gp = new GaveaeskePreset(øl, glas, pris, pakning);
		Storage.addGaveaeskePreset(gp);
		return gp;
	}
	
	public static void updateGaveaeskePreset(GaveaeskePreset preset, int øl, int glas, double pris, GaveaeskePakning pakning) {
		preset.setØl(øl);
		preset.setGlas(glas);
		preset.setPris(pris);
		preset.setPakning(pakning);
	}

}
