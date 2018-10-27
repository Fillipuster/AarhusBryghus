package model;

public class ProduktKategoriFindesException extends IllegalArgumentException {
	
	public ProduktKategoriFindesException() {
		super("Kategori findes allerede");
	}
}
