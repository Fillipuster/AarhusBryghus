package model;

import java.util.ArrayList;

import storage.Storage;

public class Gaveaeske extends Produkt {

	private GaveaeskeEmballage emballage;
	private ArrayList<Produkt> produkter = new ArrayList<>();

	public Gaveaeske() {
		super(null, "", "", -1);
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

	public GaveaeskeEmballage getEmballage() {
		return emballage;
	}

	public void setEmballage(GaveaeskeEmballage emballage) {
		this.emballage = emballage;
	}

	private GaveaeskePreset getPreset() {
		int flasker = 0, glas = 0;
		for (Produkt p : produkter) {
			if (p.getProduktKategori() == Storage.getFlaskeølProduktKategori()) {
				flasker++;
			} else if (p.getProduktKategori() == Storage.getGlasProduktKategori()) {
				glas++;
			}
		}

		return new GaveaeskePreset(flasker, glas, -1d, getEmballage());
	}

	public double getPris() {
		GaveaeskePreset preset = getPreset();
		for (GaveaeskePreset gp : Storage.getGaveaeskePresets()) {
			if (gp.equals(preset)) {
				return gp.getPris();
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

	@Override
	public int getKlipPris() {
		return -1;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Gaveaeske) {
			Gaveaeske comp = (Gaveaeske) obj;
			if (this.getEmballage() == comp.getEmballage()) {
				if (this.getProdukter().equals(comp.getProdukter())) {
					return true;
				}
			}
		}
		return false;
	}

}
