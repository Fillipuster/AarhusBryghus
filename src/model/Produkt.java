package model;

import java.util.HashMap;

public class Produkt {
	
	private ProduktKategori produktKategori;
	private HashMap<PrisKategori, Double> priser = new HashMap<>();
	
	private String navn;
	private String beskrivelse;
	private int klipPris;
	
	public Produkt(ProduktKategori produktKategori, String navn, String beskrivelse, int klipPris) {
		this.setProduktKategori(produktKategori);
		this.setNavn(navn);
		this.setBeskrivelse(beskrivelse);
		this.setKlipPris(klipPris);
	}

	public ProduktKategori getProduktKategori() {
		return produktKategori;
	}

	public void setProduktKategori(ProduktKategori produktKategori) {
		this.produktKategori = produktKategori;
	}

	public String getNavn() {
		return navn;
	}

	public void setNavn(String navn) {
		this.navn = navn;
	}

	public String getBeskrivelse() {
		return beskrivelse;
	}

	public void setBeskrivelse(String beskrivelse) {
		this.beskrivelse = beskrivelse;
	}
	
	public int getKlipPris() {
		return klipPris;
	}
	
	public void setKlipPris(int klipPris) {
		this.klipPris = klipPris;
	}
	
	public void setPris(PrisKategori kategori, double pris) {
		priser.put(kategori, pris);
	}
	
	public double getPris(PrisKategori kategori) {
		return (priser.containsKey(kategori)) ? priser.get(kategori) : Double.NaN;
	}
	
	@Override
	public String toString() {
		return getNavn();
	}
	
}