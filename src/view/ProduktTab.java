package view;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import model.PrisKategori;
import model.Produkt;
import model.ProduktKategori;
import storage.Storage;
import controller.Controller;
import javafx.geometry.Insets;

public class ProduktTab extends GridPane implements ReloadableTab {

	private ListView<Produkt> lvwProdukter;
	private ListView<ProduktPrisKategoriFormat> lvwPrisKategorier;
	private ComboBox<ProduktKategori> cboxProduktKategorier;
	private ComboBox<PrisKategori> cboxPrisKategorier;

	private TextField txfProduktNavn, txfPris;
	private TextArea txaProduktBeskrivelse;
	private Button btnOpdaterProdukt, btnSletProdukt, btnOpretProdukt, btnTilføjPris;
	
	public void reload() {
		cboxProduktKategorier.getItems().removeAll(cboxProduktKategorier.getItems());
		cboxProduktKategorier.getItems().addAll(Storage.getProduktKategorier());
	}
	
	private void setUpPane() {
		this.setPadding(new Insets(20));
		this.setHgap(20);
		this.setVgap(10);
		this.setGridLinesVisible(false);
	}

	public ProduktTab() {
		setUpPane();

		// Column 0
		ViewHelper.label(this, 0, 0, "Vælg produktkategori for at se produkter:");
		cboxProduktKategorier = new ComboBox<>();
		cboxProduktKategorier.getItems().addAll(Storage.getProduktKategorier());
		cboxProduktKategorier.setOnAction(e -> cboxProduktKategoriAction());
		this.add(cboxProduktKategorier, 0, 1);

		lvwProdukter = new ListView<Produkt>();
		lvwProdukter.setOnMouseClicked(e -> lvwProdukterAction());
		this.add(lvwProdukter, 0, 2, 1, 10);

		// Column 1
		ViewHelper.label(this, 1, 1, "Produktnavn:");
		txfProduktNavn = new TextField("PRODUKT NAVN");
		txfProduktNavn.setPrefWidth(200);
		txfProduktNavn.setDisable(true);
		this.add(txfProduktNavn, 1, 2);

		ViewHelper.label(this, 1, 3, "Produktbeskrivelse:");
		txaProduktBeskrivelse = new TextArea("BESKRIVELSE");
		txaProduktBeskrivelse.setPrefWidth(200);
		txaProduktBeskrivelse.setDisable(true);
		this.add(txaProduktBeskrivelse, 1, 4, 1, 3);

		btnOpretProdukt = new Button("Opret");
		btnOpretProdukt.setOnAction(e -> btnOpretProduktAction());
		btnOpretProdukt.setDisable(true);
		this.add(btnOpretProdukt, 1, 7);

		btnOpdaterProdukt = new Button("Opdater");
		btnOpdaterProdukt.setOnAction(e -> btnOpdaterProduktAction());
		btnOpdaterProdukt.setDisable(true);
		this.add(btnOpdaterProdukt, 1, 8);

		btnSletProdukt = new Button("Slet");
		btnSletProdukt.setOnAction(e -> btnSletProduktAction());
		btnSletProdukt.setDisable(true);
		this.add(btnSletProdukt, 1, 9);

		// Column 2
		ViewHelper.label(this, 2, 1, "Produktpriser:");
		lvwPrisKategorier = new ListView<ProduktTab.ProduktPrisKategoriFormat>();
		lvwPrisKategorier.setDisable(true);
		lvwPrisKategorier.setStyle("-fx-font-family: monospace;");
		this.add(lvwPrisKategorier, 2, 2, 1, 10);
		
		// Column 3
		ViewHelper.label(this, 3, 1, "Vælg priskategori:");
		cboxPrisKategorier = new ComboBox<>();
		cboxPrisKategorier.getItems().addAll(Storage.getPrisKategorier());
		cboxPrisKategorier.setDisable(true);
		this.add(cboxPrisKategorier, 3, 2);
		
		ViewHelper.label(this, 3, 3, "Pris i valgte priskategori:");
		txfPris = new TextField("PRIS");
		txfPris.setDisable(true);
		this.add(txfPris, 3, 4);
		
		btnTilføjPris = new Button("Tilføj");
		btnTilføjPris.setOnAction(e -> btnTilføjPrisAction());
		btnTilføjPris.setDisable(true);
		this.add(btnTilføjPris, 3, 5);
	}

	// Node updater methods;
	private void updateLvwProdukter() {
		if (cboxProduktKategorier.getSelectionModel().getSelectedItem() == null) {
			cboxProduktKategorier.getSelectionModel().select(0);
		}
		
		lvwProdukter.getItems().removeAll(lvwProdukter.getItems());
		lvwProdukter.getItems().addAll(Controller.getProdukterIKategori(cboxProduktKategorier.getValue()));
		btnOpdaterProdukt.setDisable(true);
		btnSletProdukt.setDisable(true);
	}
	
	private void updateLvwPrisKategorier() {
		if (cboxPrisKategorier.getSelectionModel().getSelectedItem() == null) {
			cboxPrisKategorier.getSelectionModel().select(0);
		}
		
		lvwPrisKategorier.getItems().removeAll(lvwPrisKategorier.getItems());
		for (PrisKategori pk : cboxPrisKategorier.getItems()) {
			lvwPrisKategorier.getItems()
					.add(new ProduktPrisKategoriFormat(lvwProdukter.getSelectionModel().getSelectedItem(), pk));
		}
	}

	// Node action methods;
	private void lvwProdukterAction() {
		Produkt selected = lvwProdukter.getSelectionModel().getSelectedItem();
		if (selected != null) {
			txfProduktNavn.setText(selected.getNavn());
			txaProduktBeskrivelse.setText(selected.getBeskrivelse());
			updateLvwPrisKategorier();
			
			disableProductNodes(false);
		} else {
			disableProductNodes(true);
		}
	}

	private void cboxProduktKategoriAction() {
		updateLvwProdukter();
		btnOpretProdukt.setDisable(false);
		txaProduktBeskrivelse.setDisable(false);
		txfProduktNavn.setDisable(false);
		disableProductNodes(true);
	}

	private void btnOpretProduktAction() {
		Controller.createProdukt(cboxProduktKategorier.getValue(), txfProduktNavn.getText(),
				txaProduktBeskrivelse.getText());
		updateLvwProdukter();
	}

	private void btnOpdaterProduktAction() {
		Controller.updateProdukt(lvwProdukter.getSelectionModel().getSelectedItem(), cboxProduktKategorier.getValue(),
				txfProduktNavn.getText(), txaProduktBeskrivelse.getText());
		updateLvwProdukter();
	}

	private void btnSletProduktAction() {
		Storage.removeProdukt(lvwProdukter.getSelectionModel().getSelectedItem());
		updateLvwProdukter();
	}
	
	private void btnTilføjPrisAction() {
		Controller.addPrisToProdukt(lvwProdukter.getSelectionModel().getSelectedItem(),
				cboxPrisKategorier.getSelectionModel().getSelectedItem(), Double.parseDouble(txfPris.getText()));
		updateLvwPrisKategorier();
	}
	
	// Node disabling methods;
	private void disableProductNodes(boolean disable) {
		btnOpdaterProdukt.setDisable(disable);
		btnSletProdukt.setDisable(disable);
		
		cboxPrisKategorier.setDisable(disable);
		lvwPrisKategorier.setDisable(disable);
		btnTilføjPris.setDisable(disable);
		txfPris.setDisable(disable);
	}

	// ListView formatting classes;
	private class ProduktPrisKategoriFormat {
		public Produkt produkt;
		public PrisKategori prisKategori;
		
		public ProduktPrisKategoriFormat(Produkt produkt, PrisKategori prisKategori) {
			this.produkt = produkt;
			this.prisKategori = prisKategori;
		}
		
		@Override
		public String toString() {
			return String.format("%-10s : %10.2f", prisKategori.getNavn(), produkt.getPris(prisKategori));
		}
	}
	
}
