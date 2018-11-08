package storage;

import java.util.ArrayList;

import controller.Controller;
import model.BetalingsMetode;
import model.GaveaeskeEmballage;
import model.GaveaeskePreset;
import model.Kunde;
import model.PrisKategori;
import model.Produkt;
import model.ProduktKategori;
import model.Salg;
import model.UdlejningsSalg;

public class Storage {
	
	// Kategorier der er nødvendige for at gaveæsker kan fungere;
	private static PrisKategori butikPrisKategori;
	private static ProduktKategori glasProduktKategori;
	private static ProduktKategori flaskeølProduktKategori;
	
	// Lister;
	private static ArrayList<Produkt> 				produkter 			= new ArrayList<>();
	private static ArrayList<ProduktKategori> 		produktKategorier 	= new ArrayList<>();
	private static ArrayList<PrisKategori> 			prisKategorier 		= new ArrayList<>();
	private static ArrayList<Salg> 					salg 				= new ArrayList<>();
	private static ArrayList<BetalingsMetode> 		betalingsMetoder 	= new ArrayList<>();
	private static ArrayList<GaveaeskePreset> 		gaveaeskePresets 	= new ArrayList<>();
	private static ArrayList<Kunde> 				kunder 				= new ArrayList<>();
	private static ArrayList<GaveaeskeEmballage> 	gaveaeskeEmballager = new ArrayList<>();

	public static void initializeStorage() {
		// Kategorier nødvendige for at gaveæsker fungerer;
		butikPrisKategori = Controller.createPrisKategori("Butik");
		glasProduktKategori = Controller.createProduktKategori("Glas");
		flaskeølProduktKategori = Controller.createProduktKategori("Flaske Øl");
		
		// Almen test data (ikke nødvendig);
		GaveaeskeEmballage ge1 = Controller.createGaveaeskeEmballage("Gaveæske");
		GaveaeskeEmballage ge2 = Controller.createGaveaeskeEmballage("Trækasse");
		GaveaeskeEmballage ge3 = Controller.createGaveaeskeEmballage("Gavekurv");
		GaveaeskeEmballage ge4 = Controller.createGaveaeskeEmballage("Papkasse");
		
		Controller.createGaveaeskePreset(2, 2, 100d, ge1);
		Controller.createGaveaeskePreset(4, 0, 130d, ge1);
		Controller.createGaveaeskePreset(6, 0, 240d, ge2);
		Controller.createGaveaeskePreset(6, 2, 250d, ge3);
		Controller.createGaveaeskePreset(6, 6, 290d, ge2);
		Controller.createGaveaeskePreset(12, 0, 390d, ge2);
		Controller.createGaveaeskePreset(12, 0, 360d, ge4);
		
		ProduktKategori fadøl = Controller.createProduktKategori("Fadøl");
		ProduktKategori fustager = Controller.createProduktKategori("Fustager");
		ProduktKategori anlæg = Controller.createProduktKategori("Anlæg");
		ProduktKategori klippekort = Controller.createProduktKategori("Klippekort");
		ProduktKategori spiritus = Controller.createProduktKategori("Spiritus");
		ProduktKategori kulsyre = Controller.createProduktKategori("Kulsyre");
		ProduktKategori malt = Controller.createProduktKategori("Malt");
		ProduktKategori beklædning = Controller.createProduktKategori("Beklædning");
		
		PrisKategori bar = Controller.createPrisKategori("Bar");

		Produkt p0 = Controller.createProdukt(fadøl, "Klosterbryg", "Fyldig", 1, 0);
		Controller.addPrisToProdukt(p0, bar, 30d);
		Produkt p1 = Controller.createProdukt(fadøl, "IPA", "Indian Pale Ale\nFrugtig.", 0, 0);
		Controller.addPrisToProdukt(p1, bar, 30d);
		Produkt p2 = Controller.createProdukt(fadøl, "Blonde", "Frisk", 2, 0);
		Controller.addPrisToProdukt(p2, bar, 30d);
		Produkt p3 = Controller.createProdukt(fadøl, "Pilsner", "Klassiker, fra fad.", 1, 0);
		Controller.addPrisToProdukt(p3, bar, 30d);
//		Produkt p4 = Controller.createProdukt(fadøl, "IPA", "Indian Pale Ale\nFrugtig, fra fad.", 0, 0);
//		Controller.addPrisToProdukt(p4, bar, 30d);
//		Produkt p5 = Controller.createProdukt(fadøl, "Blonde", "Frisk, fra fad.", 3, 0);
//		Controller.addPrisToProdukt(p5, bar, 30d);
//		Produkt p6 = Controller.createProdukt(fadøl, "KlosterBryg", "Luftig", 1, 0);
//		Controller.addPrisToProdukt(p6, bar, 30d);
		Produkt p7 = Controller.createProdukt(fadøl, "Forårsbryg", "Som viske blade", 1, 0);
		Controller.addPrisToProdukt(p7, bar, 30d);
		Produkt p8 = Controller.createProdukt(fadøl, "Black Monster", "Uhyggelig", 1, 0);
		Controller.addPrisToProdukt(p8, bar, 30d);
		Produkt p9 = Controller.createProdukt(fadøl, "Tribute", "Mortens Dag", 1, 0);
		Controller.addPrisToProdukt(p9, bar, 30d);
		Produkt p10 = Controller.createProdukt(fadøl, "India Pale Ale", "CowBoys", 2, 0);
		Controller.addPrisToProdukt(p10, bar, 30d);
		Produkt p11 = Controller.createProdukt(fadøl, "Celebration", "Festlig", 1, 0);
		Controller.addPrisToProdukt(p11, bar, 30d);
		Produkt p12 = Controller.createProdukt(fadøl, "Juletønde", "Julet", 1, 0);
		Controller.addPrisToProdukt(p12, bar, 30d);
		Produkt p13 = Controller.createProdukt(fadøl, "Fregatten Jylland", "Jullansk Guld", 2, 0);
		Controller.addPrisToProdukt(p13, bar, 30d);
		Produkt p14 = Controller.createProdukt(fadøl, "Imperial stout", "Til de kongelige", 1, 0);
		Controller.addPrisToProdukt(p14, bar, 30d);
		Produkt p15 = Controller.createProdukt(fadøl, "Sweet Georgia Brown", "Brun", 1, 0);
		Controller.addPrisToProdukt(p15, bar, 30d);
		Produkt p16 = Controller.createProdukt(fadøl, "Nikoline", "Sodavand", 0, 0);
		Controller.addPrisToProdukt(p16, bar, 15d);
		Produkt p17 = Controller.createProdukt(fadøl, "Chips", "Snack", 0, 0);
		Controller.addPrisToProdukt(p17, bar, 10d);
		Produkt p18 = Controller.createProdukt(fadøl, "Peanuts", "Snack", 0, 0);
		Controller.addPrisToProdukt(p18, bar, 10d);
		Produkt p19 = Controller.createProdukt(fadøl, "ÆbleBrus", "med æblesmag", 0, 0);
		Controller.addPrisToProdukt(p19, bar, 15d);
		
		Produkt p20 = Controller.createProdukt(flaskeølProduktKategori, "IPA", "Bedste øl", 0, 0);
		Controller.addPrisToProdukt(p20, butikPrisKategori, 36);
		Controller.addPrisToProdukt(p20, bar, 50);
		Produkt p21 = Controller.createProdukt(flaskeølProduktKategori, "KlosterBryg", "Lys", 0, 0);
		Controller.addPrisToProdukt(p21, butikPrisKategori, 36);
		Controller.addPrisToProdukt(p21, bar, 50);
		Produkt p22 = Controller.createProdukt(flaskeølProduktKategori, "Ekstra pilsner", "Nu med pils smag", 0, 0);
		Controller.addPrisToProdukt(p22, butikPrisKategori, 36);
		Controller.addPrisToProdukt(p22, bar, 50);
		Produkt p23 = Controller.createProdukt(flaskeølProduktKategori, "Vand", "Vand", 0, 0);
		Controller.addPrisToProdukt(p23, butikPrisKategori, 36);
		Controller.addPrisToProdukt(p23, bar, 50);
		Produkt p24 = Controller.createProdukt(flaskeølProduktKategori, "Blondie", "Blød", 0, 0);
		Controller.addPrisToProdukt(p24, butikPrisKategori, 36);
		Controller.addPrisToProdukt(p24, bar, 50);
		Produkt p25 = Controller.createProdukt(flaskeølProduktKategori, "7-UP", "Sodavand", 0, 0);
		Controller.addPrisToProdukt(p25, butikPrisKategori, 36);
		Controller.addPrisToProdukt(p25, bar, 50);
		Produkt p26 = Controller.createProdukt(flaskeølProduktKategori, "Special", "Dagens øl", 0, 0);
		Controller.addPrisToProdukt(p26, butikPrisKategori, 36);
		Controller.addPrisToProdukt(p26, bar, 50);
		
		Produkt p27 = Controller.createProdukt(spiritus, "Spirit of Aarhus", "Stærk", 0, 0);
		Controller.addPrisToProdukt(p27, butikPrisKategori, 300);
		Controller.addPrisToProdukt(p27, bar, 30);
		Produkt p28 = Controller.createProdukt(spiritus, "SOA med ping", "Whiskey", 0, 0);
		Controller.addPrisToProdukt(p28, butikPrisKategori, 350);
		Controller.addPrisToProdukt(p28, bar, 350);
		Produkt p29 = Controller.createProdukt(spiritus, "Whisket", "Classic whiskey", 0, 0);
		Controller.addPrisToProdukt(p29, butikPrisKategori, 500);
		Controller.addPrisToProdukt(p29, bar, 500);
		Produkt p30 = Controller.createProdukt(spiritus, "Liquor of Aarhus", "Væske", 0, 0);
		Controller.addPrisToProdukt(p30, butikPrisKategori, 175);
		Controller.addPrisToProdukt(p30, bar, 175);

		Produkt p31 = Controller.createUdlejningsProdukt(fustager, "IPA", "25L\nCrisp og frugtig.", 685d, 200d);
		Controller.addPrisToProdukt(p31, butikPrisKategori, 150);
		Produkt p32 = Controller.createUdlejningsProdukt(fustager, "Pilsner", "20L\nStandard, god til pizza.", 775d, 200d);
		Controller.addPrisToProdukt(p32, butikPrisKategori, 150);
		Produkt p33 = Controller.createUdlejningsProdukt(fustager, "Jazz Classic", "25L\\nClassic og god", 625d, 200d);
		Controller.addPrisToProdukt(p33, butikPrisKategori, 150);
		Produkt p34 = Controller.createUdlejningsProdukt(fustager, "JuleBryg", "Med kanel", 775d, 200d);
		Controller.addPrisToProdukt(p34, butikPrisKategori, 150);
		Produkt p35 = Controller.createUdlejningsProdukt(fustager, "Celebration", "Til de festlige lejligheder", 775d, 200d);
		Controller.addPrisToProdukt(p35, butikPrisKategori, 150);
		Produkt p36 = Controller.createUdlejningsProdukt(fustager, "India Pale Ale", "20L\nBedste øl", 775d, 200d);
		Controller.addPrisToProdukt(p36, butikPrisKategori, 150);
		
		Produkt p37 = Controller.createUdlejningsProdukt(kulsyre, "6kg", "6kg kulsyre", 400d, 1000d);
		
		Produkt p38 = Controller.createProdukt(beklædning, "T-Shirt", "T-shirt", 0, 0);
		Controller.addPrisToProdukt(p38, butikPrisKategori, 70);
		Produkt p39 = Controller.createProdukt(beklædning, "Polo", "Hvid", 0, 01);
		Controller.addPrisToProdukt(p39, butikPrisKategori, 100);
		Produkt p40 = Controller.createProdukt(beklædning, "Cap", "Hat", 0, 0);
		Controller.addPrisToProdukt(p40, butikPrisKategori, 30);
		
		
		Produkt p41 = Controller.createUdlejningsProdukt(anlæg, "2-hane Anlæg", "Fadølsanlæg med 2 haner.", 500d, 100d);
		Controller.addPrisToProdukt(p41, butikPrisKategori, 150);
		Produkt p42 = Controller.createUdlejningsProdukt(anlæg, "4-hane Anlæg", "Fadølsanlæg med 4 haner.", 800d, 100d);
		Controller.addPrisToProdukt(p42, butikPrisKategori, 150);
		Produkt p43 = Controller.createUdlejningsProdukt(anlæg, "Bar med flere haner", "Til den store fest", 500d, 100d);
		Controller.addPrisToProdukt(p43, butikPrisKategori, 150);
		
		Produkt p44 = Controller.createProdukt(glasProduktKategori, "Gennemsigtig", "Alle glas 15 kr.", 0, 0);
		Controller.addPrisToProdukt(p43, butikPrisKategori, 150);
		
		Produkt p45 = Controller.createProdukt(klippekort, "5x Klippekort", "Klippekort med 5 klip.", 0, 5);
		Controller.addPrisToProdukt(p45, bar, 100);
		Controller.addPrisToProdukt(p45, butikPrisKategori, 100);

		Produkt p46 = Controller.createProdukt(malt, "Malt sæt", "25 kg malt", 0, 0);
		Controller.addPrisToProdukt(p46, butikPrisKategori, 300);
		
		BetalingsMetode b0 = Controller.createBetalingsMetode("Kreditkort", false);
		/*BetalingsMetode b1 = */Controller.createBetalingsMetode("Kontant", false);
		BetalingsMetode b2 = Controller.createBetalingsMetode("MobilePay", false);
		BetalingsMetode b3 = Controller.createBetalingsMetode("Klippekort", true);
		
		Kunde k0 = Controller.createKunde("Jonas Præstegaard", "Inger Christensens Gade 24, 8220 Brabrand", "50523263");
		Kunde k1 = Controller.createKunde("Frederik Stræde", "Tordenkjoldsgade 21, 8200 Aarhus N", "25465501");
		Kunde k2 = Controller.createKunde("Morten Faber", "Pottemagertoften 115, 8270 Højbjerg", "91554511");
		
		Salg s0 = Controller.createSalg();
		Controller.setSalgBetalingsMetode(s0, b0);
		s0.opretProduktLinje(p2, butikPrisKategori, 2, 0.05d);
		s0.opretProduktLinje(p37, butikPrisKategori, 1, 0);
		Controller.saveSalg(s0);

		Salg s1 = Controller.createSalg();
		Controller.setSalgBetalingsMetode(s1, b2);
		s1.opretProduktLinje(p10, butikPrisKategori, 4, 0.0d);
		Controller.saveSalg(s1);
	
		Salg s2 = Controller.createSalg();
		Controller.setSalgBetalingsMetode(s2, b3);
		s2.opretProduktLinje(p9, butikPrisKategori, 15, 0d);
		Controller.saveSalg(s2);
		
		Salg s3 = Controller.createSalg();
		Controller.setSalgBetalingsMetode(s3, b3);
		s3.opretProduktLinje(p0, bar, 5, 0d);
		s3.opretProduktLinje(p7, bar, 2, 0d);
		s3.opretProduktLinje(p10, bar, 3, 0.125d);
		Controller.saveSalg(s3);
		
		UdlejningsSalg s4 = Controller.createUdlejningsSalg();
		Controller.setSalgBetalingsMetode(s4, b2);
		s4.opretProduktLinje(p41, butikPrisKategori, 1, 0);
		s4.opretProduktLinje(p42, butikPrisKategori, 2, 0);
		s4.opretProduktLinje(p43, butikPrisKategori, 1, 0);
		Controller.setUdlejningsSalgKunde(s4, k2);
		Controller.saveSalg(s4);
		
		UdlejningsSalg s5 = Controller.createUdlejningsSalg();
		Controller.setSalgBetalingsMetode(s5, b2);
		s5.opretProduktLinje(p31, butikPrisKategori, 2, 0d);
		s5.opretProduktLinje(p35, butikPrisKategori, 11, 25d);
		s5.opretProduktLinje(p34, butikPrisKategori, 6, 0d);
		Controller.setUdlejningsSalgKunde(s5, k0);
		Controller.saveSalg(s5);
		
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
		if (pk.equals(flaskeølProduktKategori) || pk.equals(glasProduktKategori)) {
			return;
		}
		
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
		if (pk.equals(butikPrisKategori)) {
			return;
		}
		
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
	
	// GaveaeskePreset
	public static ArrayList<GaveaeskePreset> getGaveaeskePresets() {
		return new ArrayList<>(gaveaeskePresets);
	}
	
	public static void addGaveaeskePreset(GaveaeskePreset gp) {
		gaveaeskePresets.add(gp);
	}
	
	public static void removeGaveaeskePreset(GaveaeskePreset gp) {
		gaveaeskePresets.remove(gp);
	}
	
	// GaveaeskeEmballage
	public static ArrayList<GaveaeskeEmballage> getGaveaeskeEmballager() {
		return new ArrayList<>(gaveaeskeEmballager);
	}
	
	public static void addGaveaeskeEmballage(GaveaeskeEmballage ge) {
		gaveaeskeEmballager.add(ge);
	}
	
	public static void removeGaveaeskeEmballage(GaveaeskeEmballage ge) {
		gaveaeskeEmballager.remove(ge);
	}

	// Kunde
	public static ArrayList<Kunde> getKunder(){
		return new ArrayList<>(kunder);
	}
	
	public static void addKunde(Kunde k) {
		kunder.add(k);
	}
	
	public static void removeKunde(Kunde k) {
		kunder.remove(k);
	}

}
