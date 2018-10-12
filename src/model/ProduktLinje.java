package model;

public class ProduktLinje {
	
	private Produkt produkt;
	private PrisKategori prisKategori;
	
	private int antal;
	private double rabat;

	public ProduktLinje(Produkt produkt, PrisKategori prisKategori, int antal, double rabat) {
		this.produkt = produkt;
		this.prisKategori = prisKategori;
		this.antal = antal;
		this.rabat = rabat;
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

	public Produkt getProdukt() {
		return produkt;
	}

	public void setProdukt(Produkt produkt) {
		this.produkt = produkt;
	}
	
	public double getPris() {
		return antal * produkt.getPris(prisKategori) * (1 - rabat);
	}

	@Override
	public String toString() {
		String total = String.format("%.2f", getPris());
		
		String rabatStr = "";
		if (rabat > 0d) {
			rabatStr = "(" + String.format("%.2f", rabat * 100d) + "%)";
		}
		
		return produkt.getNavn() + " x " + antal + " af " + produkt.getPris(prisKategori) +  " kr.\n = " + total + " kr. " + rabatStr ;
	}
	
}
