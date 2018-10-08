package model;

public class Produkt {
	
	private ProduktKategori produktKategori;
	private String navn;
	
	public Produkt(ProduktKategori produktKategori, String navn) {
		this.setProduktKategori(produktKategori);
		this.setNavn(navn);
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
	
}