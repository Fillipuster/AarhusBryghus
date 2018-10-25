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
		updateLvwKategorier();
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
		ViewHelper.label(this, 0, 0, "Priskategorier");
		lvwKategorier = new ListView<PrisKategori>();
		lvwKategorier.setOnMouseClicked(e -> lvwKategorierAction());
		this.add(lvwKategorier, 0, 1, 1, 5);
		
		// Column 1
		ViewHelper.label(this, 1, 0, "Kategori navn:");
		txfKategoriNavn = new TextField("KATEGORI NAVN");
		this.add(txfKategoriNavn, 1, 1);
		
		btnOpdaterKategori = new Button("Opdater");
		btnOpdaterKategori.setOnAction(e -> btnOpdaterKategoriAction());
		btnOpdaterKategori.setPrefWidth(200d);
		this.add(btnOpdaterKategori, 1, 2);
		
		btnSletKategori = new Button("Slet");
		btnSletKategori.setOnAction(e -> btnSletKategoriAction());
		btnSletKategori.setPrefWidth(200d);
		this.add(btnSletKategori, 1, 3);
		
		btnOpretKategori = new Button("Opret");
		btnOpretKategori.setOnAction(e -> btnOpretKategoriAction());
		btnOpretKategori.setPrefWidth(200d);
		this.add(btnOpretKategori, 1, 4);
		
		updateLvwKategorier();
	}
	
	// Node updater methods;
	private void updateLvwKategorier() {
		lvwKategorier.getItems().removeAll(lvwKategorier.getItems());
		lvwKategorier.getItems().addAll(Storage.getPrisKategorier());
	}
	
	// Node action methods;
	private void lvwKategorierAction() {
		PrisKategori selected = lvwKategorier.getSelectionModel().getSelectedItem();
		if (selected != null) {
			txfKategoriNavn.setText(selected.getNavn());
		} else {
			// TODO: err label?
		}
	}
	
	private void btnOpdaterKategoriAction() {
		if (!ViewHelper.listViewHasSelected(lvwKategorier)) {
			return;
		}
		
		Controller.updatePrisKategori(lvwKategorier.getSelectionModel().getSelectedItem(), txfKategoriNavn.getText());
		updateLvwKategorier();
	}
	
	private void btnSletKategoriAction() {
		if (!ViewHelper.listViewHasSelected(lvwKategorier)) {
			return;
		}
		
		Storage.removePrisKategori(lvwKategorier.getSelectionModel().getSelectedItem());
		updateLvwKategorier();
	}
	
	private void btnOpretKategoriAction() {
		Controller.createPrisKategori(txfKategoriNavn.getText());
		updateLvwKategorier();
	}
	
}
