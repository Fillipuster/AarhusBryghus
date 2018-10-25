package model;

import java.time.LocalDate;

public class UdlejningsSalg {
	private Kunde kunde;
	private LocalDate bestillingsDag;
	private LocalDate retuneringsDag;
	
	public UdlejningsSalg(Kunde kunde, LocalDate bestillingsDag, LocalDate retuneringsDag) {
		this.kunde = kunde;
		this.bestillingsDag = LocalDate.now();
	}
	
}
