package model;

import com.sun.org.apache.xpath.internal.operations.Equals;

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
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof PrisKategori) {
			if (navn.equals(((PrisKategori) obj).getNavn())) {
				return true;
			}
		}
		
		
	return false;
	}
	
}
