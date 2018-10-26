package model;

public class ProduktLinje {
	
	private Produkt produkt;
	private PrisKategori prisKategori;

	private int antal, antalUbrugt;
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

	public int getAntalUbrugt() {
		return antalUbrugt;
	}

	public void setAntalUbrugt(int antalUbrugt) {
		this.antalUbrugt = antalUbrugt;
	}

	public Produkt getProdukt() {
		return produkt;
	}

	public void setProdukt(Produkt produkt) {
		this.produkt = produkt;
	}
	
	public double getPris() {
		if (produkt instanceof UdlejningsProdukt) {
			return (antal - antalUbrugt) * ((UdlejningsProdukt) produkt).getPris() * (1 - rabat);
		} else {
			if (prisKategori != null) {
				return antal * produkt.getPris(prisKategori) * (1 - rabat);				
			}
		}
		
		return Double.NaN;
	}

	public double getPant() {
		if (produkt instanceof UdlejningsProdukt) {
			return ((UdlejningsProdukt) produkt).getPant() * antal;
		} else {
			return Double.NaN;
		}
	}

	public int getKlipPris() {
		if (produkt instanceof UdlejningsProdukt) {
			return -1;
		} else {
			return getProdukt().getKlipPris() * getAntal();
		}
	}
	
	
	
	@Override
	public String toString() {
		String total = String.format("%.2f", getPris());
		
		String rabatStr = "";
		if (rabat > 0d) {
			rabatStr = "(" + String.format("%.2f", rabat * 100d) + "%)";
		}
		
		String prisStr = "";
		if (produkt instanceof UdlejningsProdukt) {
			prisStr = Double.toString(((UdlejningsProdukt)produkt).getPris());
		} else {
			prisStr = Double.toString(produkt.getPris(prisKategori));
		}
		
		return String.format("%s x %d af %s kr.%n = %s kr. %s", produkt.getNavn(), antal, prisStr, total, rabatStr);
	}
	
	
}
