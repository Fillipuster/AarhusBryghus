package view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import model.Produkt;
import storage.Storage;

public class GaveaeskeWindow extends GridPane {

	private ListView<Produkt> lvwProdukter;
	private ComboBox<Produkt> cboxProdukter;
	private Button btnAccepter, btnAnnuller;
	
	private void setUpPane() {
		this.setPadding(new Insets(20));
		this.setHgap(20);
		this.setVgap(10);
		this.setGridLinesVisible(false);

	}
	
	public GaveaeskeWindow() {
		setUpPane();
		this.setGridLinesVisible(false);
		
		btnAccepter = new Button("Accepter");
		btnAccepter.setOnAction(e -> btnAccepterAction());
		this.add(btnAccepter, 0, 1);
		
		btnAnnuller = new Button("Annuller");
		btnAnnuller.setOnAction(e -> btnAnnullerAction());
		this.add(btnAnnuller, 0, 2);
		
		ViewHelper.label(this, 0, 3, "Vælg øl");
		cboxProdukter = new ComboBox<>();
		this.add(cboxProdukter, 0, 0);
		
	}
	
	private void btnAccepterAction() {
		
	}
	
	private void btnAnnullerAction() {
		
	}
	
	private void updateCboxProdukterMetode() {
		cboxProdukter.getItems().removeAll(cboxProdukter.getItems());
		cboxProdukter.getItems().addAll(Storage.getProdukter());
	}
	
}
