package model;

public class Produkt {
	
	private ProduktKategori produktKategori;
	private String navn;
	private String beskrivelse;
	
	public Produkt(ProduktKategori produktKategori, String navn, String beskrivelse) {
		this.setProduktKategori(produktKategori);
		this.setNavn(navn);
		this.setBeskrivelse(beskrivelse);
	}

	public ProduktKategori getProduktKategori() {
		return produktKategori;
	}

	public void setProduktKategori(ProduktKategori produktKategori) {
		this.produktKategori = produktKategori;
	}

	public String getNavn() {
		return navn;
	}

	public void setNavn(String navn) {
		this.navn = navn;
	}
	
	@Override
	public String toString() {
		return navn;
	}

	public String getBeskrivelse() {
		return beskrivelse;
	}

	public void setBeskrivelse(String beskrivelse) {
		this.beskrivelse = beskrivelse;
	}
	
}