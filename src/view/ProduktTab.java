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

public class ProduktTab extends GridPane {

	private ListView<Produkt> lvwProdukter;
	private ListView<ProduktPrisKategoriFormat> lvwPrisKategorier;
	private ComboBox<ProduktKategori> cboxProduktKategorier;
	private ComboBox<PrisKategori> cboxPrisKategorier;

	private TextField txfProduktNavn, txfPris;
	private TextArea txaProduktBeskrivelse;
	private Button btnOpdaterProdukt, btnSletProdukt, btnOpretProdukt, btnTilføjPris;

	private void setUpPane() {
		this.setPadding(new Insets(20));
		this.setHgap(20);
		this.setVgap(10);
		this.setGridLinesVisible(false);
	}

	public ProduktTab() {
		setUpPane();

		// Column 0
		cboxProduktKategorier = new ComboBox<>();
		cboxProduktKategorier.getItems().addAll(Storage.getProduktKategorier());
		cboxProduktKategorier.setOnAction(e -> cboxProduktKategoriAction());
		this.add(cboxProduktKategorier, 0, 0);

		lvwProdukter = new ListView<Produkt>();
		lvwProdukter.setOnMouseClicked(e -> lvwProdukterAction());
		this.add(lvwProdukter, 0, 1, 1, 9);

		// Column 1
		txfProduktNavn = new TextField("PRODUKT NAVN");
		txfProduktNavn.setPrefWidth(200);
		txfProduktNavn.setDisable(true);
		this.add(txfProduktNavn, 1, 1);

		txaProduktBeskrivelse = new TextArea("BESKRIVELSE");
		txaProduktBeskrivelse.setPrefWidth(200);
		txaProduktBeskrivelse.setDisable(true);
		this.add(txaProduktBeskrivelse, 1, 2, 1, 3);

		btnOpretProdukt = new Button("Opret");
		btnOpretProdukt.setOnAction(e -> btnOpretProduktAction());
		btnOpretProdukt.setDisable(true);
		this.add(btnOpretProdukt, 1, 6);

		btnOpdaterProdukt = new Button("Opdater");
		btnOpdaterProdukt.setOnAction(e -> btnOpdaterProduktAction());
		btnOpdaterProdukt.setDisable(true);
		this.add(btnOpdaterProdukt, 1, 7);

		btnSletProdukt = new Button("Slet");
		btnSletProdukt.setOnAction(e -> btnSletProduktAction());
		btnSletProdukt.setDisable(true);
		this.add(btnSletProdukt, 1, 8);

		// Column 2
		lvwPrisKategorier = new ListView<ProduktTab.ProduktPrisKategoriFormat>();
		lvwPrisKategorier.setDisable(true);
		this.add(lvwPrisKategorier, 2, 1, 1, 7);
		
		// Column 3
		cboxPrisKategorier = new ComboBox<>();
		cboxPrisKategorier.getItems().addAll(Storage.getPrisKategorier());
		cboxPrisKategorier.setDisable(true);
		this.add(cboxPrisKategorier, 3, 1);
		
		txfPris = new TextField("PRIS");
		txfPris.setDisable(true);
		this.add(txfPris, 3, 2);
		
		btnTilføjPris = new Button("Tilføj");
		btnTilføjPris.setOnAction(e -> btnTilføjPrisAction());
		btnTilføjPris.setDisable(true);
		this.add(btnTilføjPris, 3, 3);
	}

	// ListView updater methods;
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
			return String.format("%10s : %10.2f", prisKategori.getNavn(), produkt.getPris(prisKategori));
		}
	}
	
}
