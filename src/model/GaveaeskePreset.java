package model;

public class GaveaeskePreset {

	private int antalØl, antalGlas;
	private double pris;
	private GaveaeskeEmballage emballage;

	public GaveaeskePreset(int antalØl, int antalGlas, double pris, GaveaeskeEmballage emballage) {
		setAntalØl(antalØl);
		setAntalGlas(antalGlas);
		setPris(pris);
		setEmballage(emballage);
	}

	public int getAntalØl() {
		return antalØl;
	}

	public void setAntalØl(int antalØl) {
		this.antalØl = antalØl;
	}

	public int getAntalGlas() {
		return antalGlas;
	}

	public void setAntalGlas(int antalGlas) {
		this.antalGlas = antalGlas;
	}

	public double getPris() {
		return pris;
	}

	public void setPris(double pris) {
		this.pris = pris;
	}

	public GaveaeskeEmballage getEmballage() {
		return emballage;
	}

	public void setEmballage(GaveaeskeEmballage emballage) {
		this.emballage = emballage;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof GaveaeskePreset) {
			GaveaeskePreset comp = (GaveaeskePreset) obj;
			if (getAntalØl() == comp.getAntalØl() && getAntalGlas() == comp.getAntalGlas() && getEmballage() == comp.getEmballage()) {
				return true;
			}
		}

		return false;
	}

	@Override
	public String toString() {
		return getEmballage() + " (" + antalØl + "/" + antalGlas + ")";
	}

}
