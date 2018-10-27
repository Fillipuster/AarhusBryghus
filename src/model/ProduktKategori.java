package model;

public class ProduktKategori {
	
	private ProduktKategoriType type;
	private String navn;
	
	public ProduktKategori(String navn, ProduktKategoriType type) {
		setNavn(navn);
		setType(type);
	}

	public String getNavn() {
		return navn;
	}

	public void setNavn(String navn) {
		this.navn = navn;
	}
	
	public ProduktKategoriType getType() {
		return type;
	}
	
	public void setType(ProduktKategoriType type) {
		this.type = type;
	}
	
	@Override
	public String toString() {
		return navn;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ProduktKategori) {
			ProduktKategori comp = (ProduktKategori) obj;
			if (type.equals(comp.getType()) && navn.equals(comp.getNavn())) {
				return true;
			}
		}
		
		return false;
	}
	
}
