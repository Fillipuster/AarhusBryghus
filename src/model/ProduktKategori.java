package model;

public class ProduktKategori {
	
	private String navn;
	
	public ProduktKategori(String navn) {
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
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ProduktKategori) {
			if (navn.equals(((ProduktKategori) obj).getNavn())) {
				return true;
			}
		}
		
		return false;
	}
	
}
