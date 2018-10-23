package model;

import java.util.ArrayList;

import storage.Storage;

public class Gaveaeske extends Produkt {
	
	private static GaveæskePreset[] presets = new GaveæskePreset[] { new GaveæskePreset(2, 2, 100d),
			new GaveæskePreset(4, 0, 130d), new GaveæskePreset(6, 0, 240d), new GaveæskePreset(6, 2, 250d),
			new GaveæskePreset(6, 6, 290d), new GaveæskePreset(12, 0, 390d) };
	
	private ArrayList<Produkt> produkter;
	
	public Gaveaeske(ArrayList<Produkt> produkter) {
		super(null, "", "", -1);
		this.produkter = new ArrayList<>(produkter);
		updateNavnBeskrivelse();
	}
	
	private void updateNavnBeskrivelse() {
		super.setNavn("Gaveæske (" + produkter.size() + ")");
		super.setBeskrivelse("Gaveæske med " + produkter.size() + " produkter.");
	}
	
	public ArrayList<Produkt> getProdukter() {
		return new ArrayList<>(produkter);
	}
	
	public void addProdukt(Produkt produkt) {
		produkter.add(produkt);
		updateNavnBeskrivelse();
	}
	
	public void removeProdukt(Produkt produkt) {
		produkter.remove(produkt);
		updateNavnBeskrivelse();
	}
	
	private GaveæskePreset getPreset() {
		int flasker = 0, glas = 0;
		for (Produkt p : produkter) {
			if (p.getProduktKategori() == Storage.getFlaskeølProduktKategori()) {
				flasker++;
			} else if(p.getProduktKategori() == Storage.getGlasProduktKategori()) {
				glas++;
			}
		}
		
		return new GaveæskePreset(flasker, glas, -1d);		
	}
	
	public double getPris() {
		GaveæskePreset preset = getPreset();
		for (int i = 0; i < presets.length; i++) {
			if (presets[i].equals(preset)) {
				return presets[i].pris;
			}
		}
		
		double sum = 0d;
		
		for (Produkt p : produkter) {
			sum += p.getPris(Storage.getButikPrisKategori());
		}
		
		return sum;
	}
	
	public boolean isEmpty() {
		return produkter.isEmpty();
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
