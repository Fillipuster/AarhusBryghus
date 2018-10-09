package model;

public class PrisKategori {

	private String navn;
	
	public PrisKategori(String navn) {
		setNavn(navn);
	}

	public String getNavn() {
		return navn;
	}

	public void setNavn(String navn) {
		this.navn = navn;
	}
	
	@Override
	public String toString() {
		return getNavn();
	}
	
}
