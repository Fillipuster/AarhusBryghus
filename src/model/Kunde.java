package model;

import java.util.ArrayList;

public class Kunde {
	
	private String navn, addresse, telefonNr;
	private ArrayList<UdlejningsSalg> udlejningsSalg = new ArrayList<>();
	
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

	public ArrayList<UdlejningsSalg> getUdlejningssalg() {
		return udlejningsSalg;
	}

	public void setUdlejningssalg(ArrayList<UdlejningsSalg> udlejningssalg) {
		this.udlejningsSalg = udlejningssalg;
	}
	
	@Override
	public String toString() {
		return String.format("%s (%s)", getNavn(), getTelefonNr());
	}
	
}
