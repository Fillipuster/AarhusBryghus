package model;

public class Kunde {
	
	private String navn, addresse, telefonNr;
	
	public Kunde(String navn, String addresse, String tlf) {
		setNavn(navn);
		setAddresse(addresse);
		setTelefonNr(tlf);
	}

	public String getNavn() {
		return navn;
	}

	public void setNavn(String navn) {
		this.navn = navn;
	}

	public String getAddresse() {
		return addresse;
	}

	public void setAddresse(String addresse) {
		this.addresse = addresse;
	}

	public String getTelefonNr() {
		return telefonNr;
	}

	public void setTelefonNr(String telefonNr) {
		this.telefonNr = telefonNr;
	}
	
	@Override
	public String toString() {
		return String.format("%s (%s)", getNavn(), getTelefonNr());
	}
	
}
