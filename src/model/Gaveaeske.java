package model;

import java.util.ArrayList;

import storage.Storage;

public class Gaveaeske extends Produkt {
	
	private static GaveæskePreset[] presets = new GaveæskePreset[] {new GaveæskePreset(2, 2, 100d), new GaveæskePreset(4, 0, 130d), new GaveæskePreset(6, 0, 240d), new GaveæskePreset(6, 2, 250d), new GaveæskePreset(6, 6, 290d), new GaveæskePreset(12, 0, 390d)};
	
	private ArrayList<Produkt> produkter;
	private GaveæskePreset preset;
	
	public Gaveaeske(ArrayList<Produkt> produkter) {
		super(null, "Gaveæske (" + produkter.size() + ")", "Gaveæske med " + produkter.size() + " produkter i.", -1);
		this.produkter = new ArrayList<>(produkter);
	}
	
	public ArrayList<Produkt> getProdukter() {
		return new ArrayList<>(produkter);
	}
	
	private GaveæskePreset getPreset() {
		int flasker = 0, glas = 0;
		for (Produkt p : produkter) {
			if (p.getProduktKategori() == Storage.flaskeølProduktKategori) {
				flasker++;
			} else if(p.getProduktKategori() == Storage.glasProduktKategori) {
				glas++;
			}
		}
		
		return new GaveæskePreset(flasker, glas, -1d);
	}
	
	public double getPris() {
		if (preset == null) {
			preset = getPreset();
		}
		
		for (int i = 0; i < presets.length; i++) {
			if (presets[i].equals(preset)) {
				return presets[i].pris;
			}
		}
		
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
	
	private static class GaveæskePreset {
		public int ølAmount, glasAmount;
		public double pris;
		
		public GaveæskePreset(int ølAmount, int glasAmount, double pris) {
			this.ølAmount = ølAmount;
			this.glasAmount = glasAmount;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (obj instanceof GaveæskePreset) {
				GaveæskePreset comp = (GaveæskePreset) obj;
				return (this.ølAmount == comp.ølAmount && this.glasAmount == comp.glasAmount);
			}
			
			return false;
		}
	}
	
}
