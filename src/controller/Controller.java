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
import model.DataFindesAlleredeException;
import model.ProduktLinje;
import model.Salg;
import model.UdlejningsProdukt;
import model.UdlejningsSalg;
import storage.Storage;

public class Controller {

	// Argument validation methods;
	private static void validateArgNull(Object arg, String argName) {
		if (arg == null) {
			throw new IllegalArgumentException(argName + " må ikke være null.");
		}
	}

	private static void validateArgPositiveInt(int arg, String argName) {
		if (arg <= 0) {
			throw new IllegalArgumentException(argName + " skal være > 0.");
		}
	}

	private static void validateArgPositiveZeroInt(int arg, String argName) {
		if (arg < 0) {
			throw new IllegalArgumentException(argName + " skal være >= 0.");
		}
	}

	private static void validateArgPositiveDouble(double arg, String argName) {
		if (arg <= 0) {
			throw new IllegalArgumentException(argName + " skal være > 0.");
		}
	}

	private static void validateArgPositiveZeroDouble(double arg, String argName) {
		if (arg < 0) {
			throw new IllegalArgumentException(argName + " skal være >= 0.");
		}
	}

	// Produkt
	public static Produkt createProdukt(ProduktKategori kategori, String navn, String beskrivelse, int klipPris,
			int udstedteKlip) throws DataFindesAlleredeException {
		if (udstedteKlip > 0 && klipPris > 0) {
			throw new IllegalArgumentException("Klippris for klippekort skal være 0.");
		}
		validateArgNull(kategori, "ProduktKategori");
		validateArgNull(navn, "Navn");
		validateArgNull(beskrivelse, "Beskrivelse");
		validateArgPositiveZeroInt(udstedteKlip, "UdstedteKlip");
		validateArgPositiveZeroInt(klipPris, "KlipPris");

		// Implementation;
		for (Produkt p : Storage.getProdukter()) {
			if (p.getProduktKategori().equals(kategori) && p.getNavn().equals(navn)) {
				throw new DataFindesAlleredeException("Produkt findes allerede");
			}
		}

		Produkt p = new Produkt(kategori, navn, beskrivelse, klipPris, udstedteKlip);
		Storage.addProdukt(p);

		return p;
	}

	public static void updateProdukt(Produkt produkt, ProduktKategori produktKategori, String navn, String beskrivelse,
			int klipPris, int udstedteKlip) {
		if (klipPris > 0 && udstedteKlip > 0) {
			throw new IllegalArgumentException("Klippris for klippekort skal være 0.");
		}
		validateArgNull(produkt, "Produkt");
		validateArgNull(produktKategori, "ProduktKategori");
		validateArgNull(navn, "Navn");
		validateArgNull(beskrivelse, "Beskrivelse");
		validateArgPositiveZeroInt(klipPris, "KlipPris");
		validateArgPositiveZeroInt(udstedteKlip, "UdstedteKlip");

		// Implementation;
		produkt.setProduktKategori(produktKategori);
		produkt.setNavn(navn);
		produkt.setBeskrivelse(beskrivelse);
		produkt.setKlipPris(klipPris);
		produkt.setUdstedteKlip(udstedteKlip);
	}

	public static void addPrisToProdukt(Produkt produkt, PrisKategori prisKategori, double pris) {
		validateArgNull(produkt, "Produkt");
		validateArgNull(prisKategori, "PrisKategori");
		validateArgPositiveDouble(pris, "Pris");

		// Implementation;
		produkt.setPris(prisKategori, pris);
	}

	public static ArrayList<Produkt> getProdukterIKategori(ProduktKategori kategori) {
		validateArgNull(kategori, "ProduktKategori");
		
		// Implementation;
		ArrayList<Produkt> result = new ArrayList<>();
		for (Produkt p : Storage.getProdukter()) {
			if (p.getProduktKategori() == kategori) {
				result.add(p);
			}
		}

		return result;
	}

	public static ArrayList<Produkt> getProdukterIPrisKategori(PrisKategori prisKategori) {
		validateArgNull(prisKategori, "PrisKategori");

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
		validateArgNull(kategori, "ProduktKategori");

		// Implementation;
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
		validateArgNull(produktKategori, "ProudktKategori");
		validateArgNull(navn, "Navn");
		validateArgNull(beskrivelse, "Beskrivelse");

		// Implementation;
		UdlejningsProdukt up = new UdlejningsProdukt(produktKategori, navn, beskrivelse, pris, pant);
		Storage.addProdukt(up);
		return up;
	}

	public static void updateUdlejningsProdukt(UdlejningsProdukt udlejningsProdukt, ProduktKategori produktKategori,
			String navn, String beskrivelse, double pris, double pant) {
		validateArgNull(udlejningsProdukt, "UdlejningsProdukt");
		validateArgNull(produktKategori, "ProduktKategori");
		validateArgNull(navn, "Navn");
		validateArgNull(beskrivelse, "Beskrivelse");

		// Implementation;
		udlejningsProdukt.setProduktKategori(produktKategori);
		udlejningsProdukt.setNavn(navn);
		udlejningsProdukt.setBeskrivelse(beskrivelse);
		udlejningsProdukt.setPris(pris);
		udlejningsProdukt.setPant(pant);
	}

	// ProduktKategori
	public static ArrayList<ProduktKategori> getUdlejligeProduktKategorier() {
		ArrayList<ProduktKategori> result = new ArrayList<>();
		for (ProduktKategori pk : Storage.getProduktKategorier()) {
			for (Produkt p : getProdukterIKategori(pk)) {
				if (p instanceof UdlejningsProdukt) {
					if (!result.contains(pk)) {
						result.add(pk);
					}
				}
			}
		}
		return result;
	}

	public static ProduktKategori createProduktKategori(String navn) throws DataFindesAlleredeException {
		for (ProduktKategori pk : Storage.getProduktKategorier()) {
			if (navn.equals(pk.getNavn())) {
				throw new DataFindesAlleredeException("Produkt kategori findes allerede");
			}
		}
		validateArgNull(navn, "Navn");

		// Implementation;
		ProduktKategori pk = new ProduktKategori(navn);
		Storage.addProduktKategori(pk);

		return pk;
	}

	public static void updateProduktKategori(ProduktKategori kategori, String navn) {
		validateArgNull(kategori, "ProduktKategori");
		validateArgNull(navn, "Navn");

		// Implementation,
		kategori.setNavn(navn);
	}

	// PrisKategori
	public static PrisKategori createPrisKategori(String navn) throws DataFindesAlleredeException {
		for (PrisKategori pk : Storage.getPrisKategorier()) {
			if (navn.equals(pk.getNavn())) {
				throw new DataFindesAlleredeException("Pris kategorien findes allerede");
			}
		}
		validateArgNull(navn, "Navn");

		// Implementation;
		PrisKategori pk = new PrisKategori(navn);
		Storage.addPrisKategori(pk);

		return pk;
	}

	public static void updatePrisKategori(PrisKategori kategori, String navn) {
		validateArgNull(kategori, "PrisKategori");
		validateArgNull(navn, "Navn");

		// Implementation;
		kategori.setNavn(navn);
	}

	// Salg
	public static Salg createSalg() {
		return new Salg();
	}

	public static void saveSalg(Salg salg) {
		validateArgNull(salg, "Salg");

		// Implementation;
		salg.setDato(LocalDate.now());
		Storage.addSalg(salg);
	}

	public static void setSalgBetalingsMetode(Salg salg, BetalingsMetode betalingsMetode) {
		validateArgNull(salg, "Salg");
		validateArgNull(betalingsMetode, "BetalingsMetode");

		// Implementation;
		salg.setBetalingsMetode(betalingsMetode);
	}

	// UdlejningsSalg
	public static UdlejningsSalg createUdlejningsSalg() {
		return new UdlejningsSalg();
	}

	public static void saveUdlejningsSalg(UdlejningsSalg salg) {
		validateArgNull(salg, "UdlejningsSalg");

		// Implementation;
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
		validateArgNull(kunde, "Kunde");

		// Implementation;
		ArrayList<UdlejningsSalg> result = new ArrayList<>();
		for (UdlejningsSalg us : getUdlejningsSalg()) {
			if (us.getKunde() == kunde) {
				result.add(us);
			}
		}

		return result;
	}

	public static ArrayList<UdlejningsSalg> getKundeAktiveUdlejningsSalg(Kunde kunde) {
		validateArgNull(kunde, "Kunde");

		// Implementation;
		ArrayList<UdlejningsSalg> result = new ArrayList<>();
		for (UdlejningsSalg us : getKundeUdlejningsSalg(kunde)) {
			if (us.getRetuneringsDato() == null) {
				result.add(us);
			}
		}

		return result;
	}

	public static void setUdlejningsSalgKunde(UdlejningsSalg udlejningsSalg, Kunde kunde) {
		validateArgNull(udlejningsSalg, "UdlejningsSalg");
		validateArgNull(kunde, "Kunde");

		// Implementation;
		udlejningsSalg.setKunde(kunde);
	}

	public static void tilbageleverUdlejningsSalg(UdlejningsSalg salg) {
		validateArgNull(salg, "UdlejningsSalg");

		// Implementation;
		salg.setRetuneringsDato(LocalDate.now());
	}

	public static void sletUdlejligProduktLinje(UdlejningsSalg udlejningsSalg, ProduktLinje produktLinje) {
		validateArgNull(udlejningsSalg, "UdlejningsSalg");
		validateArgNull(produktLinje, "ProduktLinje");

		// Implementation;
		udlejningsSalg.sletProduktLinje(produktLinje);
	}

	// ProduktLinje
	public static ProduktLinje createProduktLinje(Salg salg, Produkt produkt, PrisKategori prisKategori, int antal,
			double rabat) {
		validateArgNull(produkt, "Produkt");
		validateArgPositiveInt(antal, "Antal");
		validateArgPositiveZeroDouble(rabat, "Rabat");

		// Implementation;
		return salg.opretProduktLinje(produkt, prisKategori, antal, rabat);
	}

	public static void updateProduktLinje(ProduktLinje produktLinje, int antal, double rabat) {
		validateArgPositiveInt(antal, "Antal");
		validateArgPositiveZeroDouble(rabat, "Rabat");

		// Implementation;
		produktLinje.setAntal(antal);
		produktLinje.setRabat(rabat);
	}

	public static void setProduktLinjeAntalUbrugt(ProduktLinje produktLinje, int antalUbrugt) {
		validateArgNull(produktLinje, "ProduktLinje");
		validateArgPositiveZeroInt(antalUbrugt, "AntalUbrugt");

		// Implementation;
		produktLinje.setAntalUbrugt(antalUbrugt);
	}

	// BetalingsMetode
	public static BetalingsMetode createBetalingsMetode(String navn, boolean brugerKlip) {
		validateArgNull(navn, "Navn");

		// Implementation;
		BetalingsMetode bm = new BetalingsMetode(navn, brugerKlip);
		Storage.addBetalingsMetode(bm);

		return bm;
	}

	public static void updateBetalingsMetode(BetalingsMetode bm, String navn) {
		validateArgNull(bm, "BetalingsMetode");
		validateArgNull(navn, "Navn");

		// Implementation;
		bm.setNavn(navn);
	}

	// Gaveæsker
	public static Gaveaeske createGaveaeske() {
		Gaveaeske g = new Gaveaeske();

		return g;
	}

	public static void setGaveaeskeEmballage(Gaveaeske gaveaeske, GaveaeskeEmballage emballage) {
		validateArgNull(gaveaeske, "Gaveaeske");
		validateArgNull(emballage, "Emballage");

		// Implementation;
		gaveaeske.setEmballage(emballage);
	}

	// GaveæskePreset
	public static GaveaeskePreset createGaveaeskePreset(int øl, int glas, double pris, GaveaeskeEmballage emballage)
			throws IllegalArgumentException {
		if (glas == 0 && øl == 0) {
			throw new IllegalArgumentException("Der skal være et produkt i gaveæske");
		}
		for (GaveaeskePreset g : Storage.getGaveaeskePresets()) {
			if (g.getEmballage().equals(emballage) && g.getAntalØl() == øl && g.getAntalGlas() == glas) {
				throw new DataFindesAlleredeException("Produkt Findes Allerede");
			}
		}
		validateArgPositiveZeroInt(øl, "Øl");
		validateArgPositiveZeroInt(glas, "Glas");
		validateArgNull(emballage, "Emballage");

		// Implementation;
		GaveaeskePreset gp = new GaveaeskePreset(øl, glas, pris, emballage);
		Storage.addGaveaeskePreset(gp);

		return gp;
	}

	public static void updateGaveaeskePreset(GaveaeskePreset preset, int antalØl, int antalGlas, double pris,
			GaveaeskeEmballage emballage) {
		if (antalGlas <= 0 && antalØl <= 0) {
			throw new IllegalArgumentException("Der skal være et produkt i gaveæske");
		}
		validateArgPositiveZeroInt(antalØl, "Øl");
		validateArgPositiveZeroInt(antalGlas, "Glas");
		validateArgNull(preset, "GaveaeskePreset");
		validateArgNull(emballage, "Emballage");

		// Implementation;
		preset.setAntalØl(antalØl);
		preset.setAntalGlas(antalGlas);
		preset.setPris(pris);
		preset.setEmballage(emballage);
	}

	// GaveaeskeEmballage
	public static GaveaeskeEmballage createGaveaeskeEmballage(String navn) {
		for (GaveaeskeEmballage ge : Storage.getGaveaeskeEmballager()) {
			if (ge.getNavn().equals(navn)) {
				throw new DataFindesAlleredeException("GaveaeskeEmballage findes allerede.");
			}
		}
		validateArgNull(navn, "Navn");

		// Implementation;
		GaveaeskeEmballage ge = new GaveaeskeEmballage(navn);
		Storage.addGaveaeskeEmballage(ge);
		return ge;
	}

	// Kunde
	public static Kunde createKunde(String navn, String addresse, String tlf) {
		validateArgNull(navn, "Navn");
		validateArgNull(addresse, "Addresse");
		validateArgNull(tlf, "Tlf");
		for (Kunde k : Storage.getKunder()) {
			if (k.getAddresse().equals(addresse) || k.getTelefonNr().equals(tlf)) {
				throw new DataFindesAlleredeException("Kunde findes allerede.");
			}
		}

		// Implementation;
		Kunde k = new Kunde(navn, addresse, tlf);
		Storage.addKunde(k);

		return k;
	}

	public static void updateKunde(Kunde kunde, String navn, String addresse, String tlf) {
		validateArgNull(kunde, "Kunde");
		validateArgNull(navn, "Navn");
		validateArgNull(addresse, "Addresse");
		validateArgNull(tlf, "Tlf");

		// Implementation;
		kunde.setNavn(navn);
		kunde.setAddresse(addresse);
		kunde.setTelefonNr(tlf);
	}

}
