package model;

public class BetalingsMetode {

	private String navn;
	
	public BetalingsMetode(String navn) {
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
		return navn;
	}
	
}