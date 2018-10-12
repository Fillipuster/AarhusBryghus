package model;

public class BetalingsMetode {

	private String navn;
	private boolean brugerKlip;
	
	public BetalingsMetode(String navn, boolean brugerKlip) {
		setNavn(navn);
		setBrugerKlip(brugerKlip);
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

	public boolean isBrugerKlip() {
		return brugerKlip;
	}

	public void setBrugerKlip(boolean brugerKlip) {
		this.brugerKlip = brugerKlip;
	}
	
}
