package model;

import java.time.LocalDate;

public class UdlejningsSalg extends Salg {
	
	private Kunde kunde;
	private LocalDate retuneringsDato;
	
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
	
}
