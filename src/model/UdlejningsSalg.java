package model;

import java.time.LocalDate;

public class UdlejningsSalg extends Salg {
	
	private LocalDate retuneringsDato;
	private Kunde kunde;
	
	public UdlejningsSalg(Kunde kunde) {
		super();
		setKunde(kunde);
	}
	
	public Kunde getKunde() {
		return kunde;
	}

	public void setKunde(Kunde kunde) {
		this.kunde = kunde;
	}

	public LocalDate getRetuneringsDato() {
		return retuneringsDato;
	}

	public void setRetuneringsDato(LocalDate retuneringsDato) {
		this.retuneringsDato = retuneringsDato;
	}
	
	public double getTotalPant() {
		double sum = 0d;
		for (ProduktLinje pl : super.getProduktLinjer()) {
			sum += pl.getPant();
		}
		
		return sum;
	}
	
}
