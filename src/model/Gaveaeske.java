package model;

import java.util.ArrayList;

import storage.Storage;

public class Gaveaeske extends Produkt {
	
	ArrayList<Produkt> produkter;
	
	public Gaveaeske(ArrayList<Produkt> produkter) {
		super(null, "Gaveæske (" + produkter.size() + ")", "Gaveæske med " + produkter.size() + " produkter i.", -1);
		this.produkter = new ArrayList<>(produkter);
	}
	
	public ArrayList<Produkt> getProdukter() {
		return new ArrayList<>(produkter);
	}
	
	public double getPris() {
		double sum = 0d;
		
		for (Produkt p : produkter) {
			sum += p.getPris(Storage.butikPrisKategori);
		}
		
		return sum;
	}
	
	@Override
	public double getPris(PrisKategori kategori) {
		return getPris();
	}
	
}
