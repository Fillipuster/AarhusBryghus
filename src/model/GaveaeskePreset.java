package model;

public class GaveaeskePreset {
	
	private int øl, glas;
	private double pris;
	private GaveaeskeEmballage emballage;
	
	public GaveaeskePreset(int øl, int glas, double pris, GaveaeskeEmballage emballage) {
		setØl(øl);
		setGlas(glas);
		setPris(pris);
		setEmballage(emballage);
	}

	public int getØl() {
		return øl;
	}

	public void setØl(int øl) {
		this.øl = øl;
	}

	public int getGlas() {
		return glas;
	}

	public void setGlas(int glas) {
		this.glas = glas;
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
			if (getØl() == comp.getØl() && getGlas() == comp.getGlas() && getEmballage() == comp.getEmballage()) {
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public String toString() {
		return getEmballage() + " (" + øl + "/" + glas + ")";
	}

}
