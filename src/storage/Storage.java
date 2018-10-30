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

		Produkt p0 = Controller.createProdukt(flaskeølProduktKategori, "Klosterbryg", "Fyldig", 1, 0);
		Produkt p1 = Controller.createProdukt(flaskeølProduktKategori, "IPA", "Indian Pale Ale\nFrugtig.", 0, 0);
		Produkt p2 = Controller.createProdukt(flaskeølProduktKategori, "Blonde", "Frisk", 2, 0);

		Produkt p3 = Controller.createProdukt(fadøl, "Pilsner", "Klassiker, fra fad.", 1, 0);
		Produkt p4 = Controller.createProdukt(fadøl, "IPA", "Indian Pale Ale\nFrugtig, fra fad.", 0, 0);
		Produkt p5 = Controller.createProdukt(fadøl, "Blonde", "Frisk, fra fad.", 3, 0);
		
		/*Produkt p6 = */Controller.createUdlejningsProdukt(fustager, "IPA", "25L\nCrisp og frugtig.", 250d, 100d);
		/*Produkt p7 = */Controller.createUdlejningsProdukt(fustager, "Pilsner", "20L\nStandard, god til pizza.", 200d, 100d);
		
		/*Produkt p8 = */Controller.createUdlejningsProdukt(anlæg, "2-hane Anlæg", "Fadølsanlæg med 2 haner.", 500d, 0d);
		/*Produkt p9 = */Controller.createUdlejningsProdukt(anlæg, "4-hane Anlæg", "Fadølsanlæg med 4 haner.", 800d, 0d);
		
		Produkt p10 = Controller.createProdukt(klippekort, "5x Klippekort", "Klippekort med 5 klip.", 0, 5);
		
		PrisKategori pk1 = Controller.createPrisKategori("Bar");
		
		Controller.addPrisToProdukt(p0, butikPrisKategori, 1);
		Controller.addPrisToProdukt(p0, pk1, 2);
		Controller.addPrisToProdukt(p1, butikPrisKategori, 3);
		Controller.addPrisToProdukt(p1, pk1, 4);
		Controller.addPrisToProdukt(p2, butikPrisKategori, 5);
		Controller.addPrisToProdukt(p2, pk1, 6);
		Controller.addPrisToProdukt(p3, butikPrisKategori, 7);
		Controller.addPrisToProdukt(p3, pk1, 8);
		Controller.addPrisToProdukt(p4, butikPrisKategori, 9);
		Controller.addPrisToProdukt(p4, pk1, 10);
		Controller.addPrisToProdukt(p5, butikPrisKategori, 11);
		Controller.addPrisToProdukt(p5, pk1, 12);
		Controller.addPrisToProdukt(p10, butikPrisKategori, 150);
		Controller.addPrisToProdukt(p10, pk1, 150);
		
		Controller.createBetalingsMetode("Kreditkort", false);
		Controller.createBetalingsMetode("Kontant", false);
		Controller.createBetalingsMetode("MobilePay", false);
		Controller.createBetalingsMetode("Klippekort", true);
		
		Controller.createKunde("Jonas Præstegaard", "Inger Christensens Gade 24, 8220 Brabrand", "50523263");
		Controller.createKunde("Frederik Stræde", "Tordenkjoldsgade 21, 8200 Aarhus N", "25465501");
		Controller.createKunde("Morten Faber", "Pottemagertoften 115, 8270 Højbjerg", "91554511");
		
		Salg s1 = Controller.createSalg();
		Controller.setSalgBetalingsMetode(s1, Storage.getBetalingsMetoder().get(1));
		s1.opretProduktLinje(p2, butikPrisKategori, 2, 0.05d);
		Controller.saveSalg(s1);
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
