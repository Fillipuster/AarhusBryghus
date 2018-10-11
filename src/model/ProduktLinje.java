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

	@Override
	public String toString() {
		return produkt.getNavn() + "  " + antal + "  " + produkt.getPris(prisKategori) +  "  " + rabat * 100d + "%";
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof ProduktLinje) {
			if( this.getProdukt() == ((ProduktLinje) obj).getProdukt()) {
				return (((ProduktLinje) obj).getRabat() == this.getRabat());
			}
			
		}
		return false;
	}
	
}
