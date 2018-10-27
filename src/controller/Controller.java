package controller;

import java.time.LocalDate;
import java.util.ArrayList;

import model.BetalingsMetode;
import model.Gaveaeske;
import model.GaveaeskeEmballage;
import model.GaveaeskePreset;
import model.Kunde;
import model.PrisKategori;
import model.Produkt;
import model.ProduktKategori;
import model.ProduktKategoriType;
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
	public static ArrayList<UdlejningsProdukt> getUdlejningsProdukter() {
		ArrayList<UdlejningsProdukt> result = new ArrayList<>();
		for (Produkt p : Storage.getProdukter()) {
			if (p instanceof UdlejningsProdukt) {
				result.add((UdlejningsProdukt) p);
			}
		}

		return result;
	}

	public static ArrayList<UdlejningsProdukt> getUdlejningsProdukterIProduktKategori(ProduktKategori kategori) {
		ArrayList<UdlejningsProdukt> result = new ArrayList<>();
		for (UdlejningsProdukt up : getUdlejningsProdukter()) {
			if (up.getProduktKategori() == kategori) {
				result.add(up);
			}
		}

		return result;
	}

	public static UdlejningsProdukt createUdlejningsProdukt(ProduktKategori produktKategori, String navn,
			String beskrivelse, double pris, double pant) {
		UdlejningsProdukt up = new UdlejningsProdukt(produktKategori, navn, beskrivelse, pris, pant);
		Storage.addProdukt(up);
		return up;
	}

	public static void updateUdlejningsProdukt(UdlejningsProdukt udlejningsProdukt, ProduktKategori produktKategori,
			String navn, String beskrivelse, double pris, double pant) {
		udlejningsProdukt.setProduktKategori(produktKategori);
		udlejningsProdukt.setNavn(navn);
		udlejningsProdukt.setBeskrivelse(beskrivelse);
		udlejningsProdukt.setPris(pris);
		udlejningsProdukt.setPant(pant);
	}

	// ProduktKategori
	public static ArrayList<ProduktKategori> getProduktKategorierAfType(ProduktKategoriType type) {
		ArrayList<ProduktKategori> result = new ArrayList<>();
		for (ProduktKategori pk : Storage.getProduktKategorier()) {
			if (pk.getType().equals(type)) {
				result.add(pk);
			}
		}
		
		return result;
	}

	public static ProduktKategori createProduktKategori(String navn, ProduktKategoriType type) {
		if (navn == null) {
			throw new IllegalArgumentException("Navn må ikke være null");
		}

		ProduktKategori pk = new ProduktKategori(navn, type);
		Storage.addProduktKategori(pk);

		return pk;
	}

	public static void updateProduktKategori(ProduktKategori kategori, String navn, ProduktKategoriType type) {
		if (kategori == null) {
			throw new IllegalArgumentException("Kategori må ikke være null");
		}
		if (navn == null) {
			throw new IllegalArgumentException("Navn må ikke være null");
		}

		kategori.setNavn(navn);
		kategori.setType(type);
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
		if (salg == null) {
			throw new IllegalArgumentException("Salg må ikke være null");
		}
		salg.setDato(LocalDate.now());
		Storage.addSalg(salg);
	}

	public static void setSalgBetalingsMetode(Salg salg, BetalingsMetode betalingsMetode) {
		if (betalingsMetode == null) {
			throw new IllegalArgumentException("Betalingsmetode må ikke være null");
		}
		salg.setBetalingsMetode(betalingsMetode);
	}

	// UdlejningsSalg
	public static UdlejningsSalg createUdlejningsSalg() {
		return new UdlejningsSalg();
	}
	
	public static void saveUdlejningsSalg(UdlejningsSalg salg) {
		salg.setRetuneringsDato(LocalDate.now());
	}

	public static ArrayList<UdlejningsSalg> getUdlejningsSalg() {
		ArrayList<UdlejningsSalg> result = new ArrayList<>();
		for (Salg s : Storage.getSalg()) {
			if (s instanceof UdlejningsSalg) {
				result.add((UdlejningsSalg) s);
			}
		}

		return result;
	}

	public static ArrayList<UdlejningsSalg> getKundeUdlejningsSalg(Kunde kunde) {
		ArrayList<UdlejningsSalg> result = new ArrayList<>();
		for (UdlejningsSalg us : getUdlejningsSalg()) {
			if (us.getKunde() == kunde) {
				result.add(us);
			}
		}

		return result;
	}
	
	public static ArrayList<UdlejningsSalg> getKundeAktiveUdlejningsSalg(Kunde kunde) {
		ArrayList<UdlejningsSalg> result = new ArrayList<>();
		for (UdlejningsSalg us : getKundeUdlejningsSalg(kunde)) {
			if (us.getRetuneringsDato() == null) {
				result.add(us);
			}
		}
		
		return result;
	}

	public static void setUdlejningsSalgKunde(UdlejningsSalg udlejningsSalg, Kunde kunde) {
		udlejningsSalg.setKunde(kunde);
	}
	
	public static void tilbageleverUdlejningsSalg(UdlejningsSalg salg) {
		salg.setRetuneringsDato(LocalDate.now());
	}
	
	public static void sletUdlejligProduktLinje(UdlejningsSalg udlejningsSalg, ProduktLinje produktLinje) {
		udlejningsSalg.sletProduktLinje(produktLinje);
	}

	// ProduktLinje
	public static ProduktLinje createProduktLinje(Salg salg, Produkt produkt, PrisKategori prisKategori, int antal,
			double rabat) {
		if (produkt == null) {
			throw new IllegalArgumentException("Produkt kan ikke være null");
		}
		if (antal < 0) {
			throw new IllegalArgumentException("Antal kan ikke være negativt");
		}
		if (antal == 0) {
			throw new IllegalArgumentException("Antal kan ikke være 0");
		}
		if (rabat < 0) {
			throw new IllegalArgumentException("Rabat kan ikke være negativt");
		}
		return salg.opretProduktLinje(produkt, prisKategori, antal, rabat);
	}

	public static void updateProduktLinje(ProduktLinje produktLinje, int antal, double rabat) {
		if(antal < 0) {
			throw new IllegalArgumentException("Antal må ikke være negativ");
		}
		if(rabat < 0) {
			throw new IllegalArgumentException("Rabat må ikke være negativ");
		}
		produktLinje.setAntal(antal);
		produktLinje.setRabat(rabat);
	}

	public static void setProduktLinjeAntalUbrugt(ProduktLinje produktLinje, int antalUbrugt) {
		produktLinje.setAntalUbrugt(antalUbrugt);
	}

	// BetalingsMetode
	public static BetalingsMetode createBetalingsMetode(String navn, boolean brugerKlip) {
		if (navn == null) {
			throw new IllegalArgumentException("Navn må ikke være null");
		}
		BetalingsMetode bm = new BetalingsMetode(navn, brugerKlip);
		Storage.addBetalingsMetode(bm);

		return bm;
	}

	public static void updateBetalingsMetode(BetalingsMetode bm, String navn) {
		if (navn == null) {
			throw new IllegalArgumentException("Navn må ikke være null");
		}
		
		if (bm == null) {
			throw new IllegalArgumentException("Betalingsmetode må ikke være null");
		}
		bm.setNavn(navn);
	}

	// Gaveæsker
	public static Gaveaeske createGaveaeske() {
		Gaveaeske g = new Gaveaeske();

		return g;
	}

	public static void setGaveaeskeEmballage(Gaveaeske gaveaeske, GaveaeskeEmballage emballage) {
		gaveaeske.setEmballage(emballage);
	}

	// GaveæskePreset
	public static GaveaeskePreset createGaveaeskePreset(int øl, int glas, double pris, GaveaeskeEmballage emballage) {
		GaveaeskePreset gp = new GaveaeskePreset(øl, glas, pris, emballage);
		Storage.addGaveaeskePreset(gp);
		return gp;
	}

	public static void updateGaveaeskePreset(GaveaeskePreset preset, int øl, int glas, double pris,
			GaveaeskeEmballage emballage) {
		preset.setØl(øl);
		preset.setGlas(glas);
		preset.setPris(pris);
		preset.setEmballage(emballage);
	}
	
	// GaveaeskeEmballage
	public static GaveaeskeEmballage createGaveaeskeEmballage(String navn) {
		GaveaeskeEmballage ge = new GaveaeskeEmballage(navn);
		Storage.addGaveaeskeEmballage(ge);
		return ge;
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
