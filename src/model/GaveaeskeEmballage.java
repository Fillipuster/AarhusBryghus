package model;

public class GaveaeskeEmballage {

	private String navn;
	
	public GaveaeskeEmballage(String navn) {
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
