package model;

public class GaveaeskePreset {
	
	private int øl, glas;
	private double pris;
	private GaveaeskePakning pakning;
	
	public GaveaeskePreset(int øl, int glas, double pris, GaveaeskePakning pakning) {
		setØl(øl);
		setGlas(glas);
		setPris(pris);
		setPakning(pakning);
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
	
	public GaveaeskePakning getPakning() {
		return pakning;
	}
	
	public void setPakning(GaveaeskePakning pakning) {
		this.pakning = pakning;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof GaveaeskePreset) {
			GaveaeskePreset comp = (GaveaeskePreset) obj;
			if (getØl() == comp.getØl() && getGlas() == comp.getGlas() && getPakning() == comp.getPakning()) {
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public String toString() {
		return getPakning().toString() + " (" + øl + "/" + glas + ")";
	}

}
