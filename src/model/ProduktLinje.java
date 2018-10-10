package model;

public class ProduktLinje {
	private Produkt produkt;
	private int antal;
	private double rabat;
	
	public ProduktLinje(Produkt produkt, int antal, double rabat) {
		this.rabat = rabat;
		this.antal = antal;
	}

	public double getRabat() {
		return rabat;
	}

	public void setRabat(double rabat) {
		this.rabat = rabat;
	}

	public int getAntal() {
		return antal;
	}

	public void setAntal(int antal) {
		this.antal = antal;
	}
	
	
}
