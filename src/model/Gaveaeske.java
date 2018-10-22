package model;

import storage.Storage;

public class Gaveaeske extends Produkt {

	private int antalØl;
	
	public Gaveaeske(String navn, String beskrivelse, int antalØl, double pris) {
		super(Storage.getGaveæskeProduktKategori(), navn, beskrivelse, -1);
		super.setPris(Storage.getButikPrisKategori(), pris);
		
		setAntalØl(antalØl);
	}

	public int getAntalØl() {
		return antalØl;
	}

	public void setAntalØl(int antalØl) {
		this.antalØl = antalØl;
	}
	
}
