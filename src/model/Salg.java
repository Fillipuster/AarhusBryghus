package model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Salg {
	
	private BetalingsMetode betalingsMetode;
	private ArrayList<ProduktLinje> produktLinjer = new ArrayList<>();
	private LocalDate dato;

	public void setDato(LocalDate dato) {
		this.dato = dato;
	}

	public LocalDate getDato() {
		return dato;
	}

	public BetalingsMetode getBetalingsMetode() {
		return betalingsMetode;
	}

	public void setBetalingsMetode(BetalingsMetode betalingsMetode) {
		this.betalingsMetode = betalingsMetode;
	}

	public ArrayList<ProduktLinje> getProduktLinjer() {
		return new ArrayList<ProduktLinje>(produktLinjer);
	}

	public ProduktLinje opretProduktLinje(Produkt produkt, PrisKategori prisKategori, int antal, double rabat) {
		ProduktLinje pl = new ProduktLinje(produkt, prisKategori, antal, rabat);
		produktLinjer.add(pl);
		return pl;
	}
	
	public void sletProduktLinje(ProduktLinje produktLinje) {
		produktLinjer.remove(produktLinje);
	}
	
	public double getTotalPris() {
		double total = 0d;
		for (ProduktLinje pl : produktLinjer) {
			total += pl.getPris();
		}
		
		return total;
	}
	
	public int getTotalKlipPris() {
		int total = 0;
		for (ProduktLinje pl : produktLinjer) {
			if (pl.getKlipPris() <= 0) {
				return 0;
			}
			
			total += pl.getKlipPris();
		}
		
		return total;
	}
	
	@Override
	public String toString() {
		int produkter = 0;
		for (ProduktLinje pl : getProduktLinjer()) {
			produkter += pl.getAntal();
		}
		
		return String.format("%t (%d) %f kr.", getDato(), produkter, getTotalPris());
	}

}
