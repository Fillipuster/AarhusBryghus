package view;

import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import model.PrisKategori;
import storage.Storage;
import controller.Controller;
import javafx.geometry.Insets;

public class PrisKategoriTab extends GridPane implements ReloadableTab {

	private ListView<PrisKategori> lvwKategorier;
	
	private TextField txfKategoriNavn;
	private Button btnOpdaterKategori, btnSletKategori, btnOpretKategori;

	public void reload() {
		
	}
	
	private void setUpPane() {
		this.setPadding(new Insets(20));
		this.setHgap(20);
		this.setVgap(10);
		this.setGridLinesVisible(false);
	}

	public PrisKategoriTab() {
		setUpPane();

		// Column 0
		lvwKategorier = new ListView<PrisKategori>();
		lvwKategorier.setOnMouseClicked(e -> lvwKategorierAction());
		this.add(lvwKategorier, 0, 0, 1, 5);
		
		// Column 1
		txfKategoriNavn = new TextField("KATEGORI NAVN");
		this.add(txfKategoriNavn, 1, 0);
		
		btnOpdaterKategori = new Button("Opdater");
		btnOpdaterKategori.setOnAction(e -> btnOpdaterKategoriAction());
		this.add(btnOpdaterKategori, 1, 1);
		
		btnSletKategori = new Button("Slet");
		btnSletKategori.setOnAction(e -> btnSletKategoriAction());
		this.add(btnSletKategori, 1, 2);
		
		btnOpretKategori = new Button("Opret");
		btnOpretKategori.setOnAction(e -> btnOpretKategoriAction());
		this.add(btnOpretKategori, 1, 3);
		
		updateLvwKategorier();
	}
	
	// ListView updater methods;
	private void updateLvwKategorier() {
		lvwKategorier.getItems().removeAll(lvwKategorier.getItems());
		lvwKategorier.getItems().addAll(Storage.getPrisKategorier());
		disableKategoriNodes(true);
	}
	
	// Node disabling methods;
	private void disableKategoriNodes(boolean disable) {
		btnOpdaterKategori.setDisable(disable);
		btnSletKategori.setDisable(disable);
	}
	
	// Node action methods;
	private void lvwKategorierAction() {
		PrisKategori selected = lvwKategorier.getSelectionModel().getSelectedItem();
		if (selected != null) {
			disableKategoriNodes(false);
			txfKategoriNavn.setText(selected.getNavn());
		} else {
			disableKategoriNodes(true);
		}
	}
	
	private void btnOpdaterKategoriAction() {
		Controller.updatePrisKategori(lvwKategorier.getSelectionModel().getSelectedItem(), txfKategoriNavn.getText());
		updateLvwKategorier();
	}
	
	private void btnSletKategoriAction() {
		Storage.removePrisKategori(lvwKategorier.getSelectionModel().getSelectedItem());
		updateLvwKategorier();
	}
	
	private void btnOpretKategoriAction() {
		Controller.createPrisKategori(txfKategoriNavn.getText());
		updateLvwKategorier();
	}
	
}
