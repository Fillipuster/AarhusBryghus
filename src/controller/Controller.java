package controller;

import java.time.LocalDate;
import java.util.ArrayList;

import model.BetalingsMetode;
import model.Gaveaeske;
import model.GaveaeskePakning;
import model.GaveaeskePreset;
import model.Kunde;
import model.PrisKategori;
import model.Produkt;
import model.ProduktKategori;
import model.ProduktLinje;
import model.Salg;
import model.UdlejningsProdukt;
import model.UdlejningsSalg;
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
	
	// Udlejligt Produkt
	public static ArrayList<UdlejningsProdukt> getUdlejningsProdukterIProduktKategori(ProduktKategori kategori){
		ArrayList<UdlejningsProdukt> result = new ArrayList<>();
		for (Produkt p : Storage.getProdukter()) {
			if (p instanceof UdlejningsProdukt) {
				result.add((UdlejningsProdukt)p);
			}
		}
		
		return result;
	}
	
	public static UdlejningsProdukt createUdlejningsProdukt(ProduktKategori produktKategori, String navn, String beskrivelse, double pris, double pant) {
		UdlejningsProdukt up = new UdlejningsProdukt(produktKategori, navn, beskrivelse, pris, pant);
		Storage.addProdukt(up);
		return up;
	}
	
	public static void updateUdlejningsProdukt(UdlejningsProdukt udlejningsProdukt, ProduktKategori produktKategori, String navn, String beskrivelse, double pris, double pant) {
		udlejningsProdukt.setProduktKategori(produktKategori);
		udlejningsProdukt.setNavn(navn);
		udlejningsProdukt.setBeskrivelse(beskrivelse);
		udlejningsProdukt.setPris(pris);
		udlejningsProdukt.setPant(pant);
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
		if (navn == null) {
			throw new IllegalArgumentException("Navn må ikke være null");
		}
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
		return new Salg();
	}

	public static void saveSalg(Salg salg) {
		salg.setDato(LocalDate.now());
		Storage.addSalg(salg);
	}
	
	public static void setSalgBetalingsMetode(Salg salg, BetalingsMetode betalingsMetode) {
		salg.setBetalingsMetode(betalingsMetode);
	}
	
	// UdlejningsSalg
	public static UdlejningsSalg createUdlejningsSalg() {
		return new UdlejningsSalg();
	}
	public static ArrayList<UdlejningsSalg> getUdlejningsSalg(){
		ArrayList<UdlejningsSalg> result = new ArrayList<>();
		for (Salg s : Storage.getSalg()) {
			if (s instanceof UdlejningsSalg) {
				result.add((UdlejningsSalg) s);
			}
		}
		
		return result;
	}
	
	public static ArrayList<UdlejningsSalg> getKundeUdlejningsSalg(Kunde kunde){
		ArrayList<UdlejningsSalg> result = new ArrayList<>();
		for (UdlejningsSalg us : getUdlejningsSalg()) {
			if (us.getKunde() == kunde) {
				result.add(us);
			}
		}
		
		return result;
	}
	
	public static void tilbageleverUdlejningsSalg(UdlejningsSalg salg) {
		salg.setRetuneringsDato(LocalDate.now());
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
	
	public static void setProduktLinjeAntalUbrugt(ProduktLinje produktLinje, int antalUbrugt) {
		produktLinje.setAntalUbrugt(antalUbrugt);
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
	
	// Kunde
	public static Kunde createKunde(String navn, String addresse, String tlf) {
		Kunde k = new Kunde(navn, addresse, tlf);
		Storage.addKunde(k);
		
		return k;
	}
	
	public static void updateKunde(Kunde k, String navn, String addresse, String tlf) {
		k.setNavn(navn);
		k.setAddresse(addresse);
		k.setTelefonNr(tlf);
	}

}
