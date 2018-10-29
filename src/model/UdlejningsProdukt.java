package model;

public class UdlejningsProdukt extends Produkt {
	
	private double pris, pant;

	public UdlejningsProdukt(ProduktKategori produktKategori, String navn, String beskrivelse, double pris, double pant) {
		super(produktKategori, navn, beskrivelse, 0, 0);
		setPris(pris);
		setPant(pant);
	}

	public double getPris() {
		return pris;
	}

	public void setPris(double pris) {
		this.pris = pris;
	}

	public double getPant() {
		return pant;
	}

	public void setPant(double pant) {
		this.pant = pant;
	}

}
