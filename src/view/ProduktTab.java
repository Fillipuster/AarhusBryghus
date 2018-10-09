package view;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import model.Produkt;
import model.ProduktKategori;
import storage.Storage;
import controller.Controller;
import javafx.geometry.Insets;

public class ProduktTab extends GridPane {

	ListView<Produkt> lvwProdukter;
	ComboBox<ProduktKategori> cboxProduktKategorier;

	private TextField txfProduktNavn;
	private TextArea txaProduktBeskrivelse;
	private Button btnOpdaterProdukt, btnSletProdukt, btnOpretProdukt;

	private void setUpPane() {
		this.setPadding(new Insets(20));
		this.setHgap(20);
		this.setVgap(10);
		this.setGridLinesVisible(false);
	}

	public ProduktTab() {
		setUpPane();

		// Column 0
//		MainApp.label(this, 0, 0, "Produkter");

		cboxProduktKategorier = new ComboBox<>();
		cboxProduktKategorier.getItems().addAll(Storage.getProduktKategorier());
		cboxProduktKategorier.setOnAction(e -> cboxProduktKategoriAction());
		this.add(cboxProduktKategorier, 0, 0);

		lvwProdukter = new ListView<Produkt>();
		lvwProdukter.setOnMouseClicked(e -> lvwProdukterAction());
		this.add(lvwProdukter, 0, 1, 1, 5);

		// Column 1
		txfProduktNavn = new TextField("PRODUKT NAVN");
		this.add(txfProduktNavn, 1, 1);

		txaProduktBeskrivelse = new TextArea("BESKRIVELSE");
		this.add(txaProduktBeskrivelse, 1, 2);

		btnOpretProdukt = new Button("Opret");
		btnOpretProdukt.setOnAction(e -> btnOpretProduktAction());
		btnOpretProdukt.setDisable(true);
		this.add(btnOpretProdukt, 1, 3);

		btnOpdaterProdukt = new Button("Opdater");
		btnOpdaterProdukt.setOnAction(e -> btnOpdaterProduktAction());
		btnOpdaterProdukt.setDisable(true);
		this.add(btnOpdaterProdukt, 1, 4);

		btnSletProdukt = new Button("Slet");
		btnSletProdukt.setOnAction(e -> btnSletProduktAction());
		btnSletProdukt.setDisable(true);
		this.add(btnSletProdukt, 1, 5);

		// Column 2
	}

	private void updateLvwProdukter() {
		lvwProdukter.getItems().removeAll(lvwProdukter.getItems());
		lvwProdukter.getItems().addAll(Controller.getProdukterIKategori(cboxProduktKategorier.getValue()));
		btnOpdaterProdukt.setDisable(true);
		btnSletProdukt.setDisable(true);
	}

	private void lvwProdukterAction() {
		Produkt selected = lvwProdukter.getSelectionModel().getSelectedItem();
		if (selected != null) {
			txfProduktNavn.setText(selected.getNavn());
			txaProduktBeskrivelse.setText(selected.getBeskrivelse());
			btnOpdaterProdukt.setDisable(false);
			btnSletProdukt.setDisable(false);
		} else {
			btnOpdaterProdukt.setDisable(true);
			btnSletProdukt.setDisable(true);
		}
	}

	private void cboxProduktKategoriAction() {
		updateLvwProdukter();
		btnOpretProdukt.setDisable(false);
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

}
