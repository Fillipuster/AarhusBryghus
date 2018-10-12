package model;

import java.util.ArrayList;

public class Kunde {
	private String navn, addresse;
	private int telefonNr;
	private ArrayList<UdlejningsSalg> udlejningsSalg = new ArrayList<>();
	
	public Kunde(String navn, String addresse) {
		this.navn = navn;
		this.addresse = addresse;
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

	public int getTelefonNr() {
		return telefonNr;
	}

	public void setTelefonNr(int telefonNr) {
		this.telefonNr = telefonNr;
	}

	public ArrayList<UdlejningsSalg> getUdlejningssalg() {
		return udlejningsSalg;
	}

	public void setUdlejningssalg(ArrayList<UdlejningsSalg> udlejningssalg) {
		this.udlejningsSalg = udlejningssalg;
	}
	
}
