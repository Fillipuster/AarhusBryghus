package view;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import model.DataFindesAlleredeException;
import model.PrisKategori;
import model.Produkt;
import model.ProduktKategori;
import model.UdlejningsProdukt;
import storage.Storage;
import controller.Controller;
import javafx.event.EventHandler;
import javafx.geometry.Insets;

public class ProduktTab extends GridPane implements ReloadableTab {

	private ListView<Produkt> lvwProdukter;
	private ListView<ProduktPrisKategoriFormat> lvwPriser;
	private ComboBox<ProduktKategori> cboxProduktKategorier;
	private Label lblError;
	private TextField txfProduktNavn, txfPris, txfKlipPris, txfUdlejligPris, txfUdlejligPant, txfUdstedteKlip;
	private TextArea txaProduktBeskrivelse;
	private Button btnOpdaterProdukt, btnSletProdukt, btnOpretProdukt, btnSætPris;
	private CheckBox cbUdlejlig, cbKanKøbesMedKlippekort, cbIsKlippekort;

	public void reload() {
		updateCboxProduktKategorier();
	}

	private void setUpPane() {
		this.setPadding(new Insets(20));
		this.setHgap(20);
		this.setVgap(10);
		this.setGridLinesVisible(false);

		// Clear error label on mouse event;
		this.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				clearErrorText();
			}
		});
	}

	public ProduktTab() {
		setUpPane();

		// Column 0
		ViewHelper.label(this, 0, 0, "Vælg produktkategori for at se produkter:");
		cboxProduktKategorier = new ComboBox<>();
		cboxProduktKategorier.getItems().addAll(Storage.getProduktKategorier());
		cboxProduktKategorier.setOnAction(e -> cboxProduktKategoriAction());
		cboxProduktKategorier.setPrefWidth(250);
		this.add(cboxProduktKategorier, 0, 1);

		lvwProdukter = new ListView<Produkt>();
		lvwProdukter.setOnMouseClicked(e -> lvwProdukterAction());
		this.add(lvwProdukter, 0, 2, 1, 14);

		lblError = new Label();
		lblError.setTextFill(Color.RED);
		this.add(lblError, 0, 16);

		// Column 1
		ViewHelper.label(this, 1, 1, "Produktnavn:");
		txfProduktNavn = new TextField();
		txfProduktNavn.setPromptText("Produkt Navn");
		txfProduktNavn.setPrefWidth(200);
		this.add(txfProduktNavn, 1, 2);

		ViewHelper.label(this, 1, 3, "Produktbeskrivelse:");
		txaProduktBeskrivelse = new TextArea();
		txaProduktBeskrivelse.setPromptText("Produkt\nBeskrivelse");
		txaProduktBeskrivelse.setPrefWidth(200);
		this.add(txaProduktBeskrivelse, 1, 4, 1, 3);

		ViewHelper.label(this, 1, 7, "Pris i klippekort klip:");
		cbKanKøbesMedKlippekort = new CheckBox("Kan købes med klippekort.");
		cbKanKøbesMedKlippekort.selectedProperty().addListener((o, oldValue, newValue) -> cbKanKøbesMedKlippekortListener(o, oldValue, newValue));
		this.add(cbKanKøbesMedKlippekort, 1, 8);
		txfKlipPris = new TextField();
		txfKlipPris.setPromptText("Klip Pris");
		txfKlipPris.setPrefWidth(200);
		txfKlipPris.setDisable(true);
		ViewHelper.textFieldRestrictInt(txfKlipPris);
		this.add(txfKlipPris, 1, 9);

		ViewHelper.label(this, 1, 10, "Mængde udstedte klip (til klippekort):");
		cbIsKlippekort = new CheckBox("Produkt er klippekort.");
		cbIsKlippekort.selectedProperty().addListener((o, oldValue, newValue) -> cbIsKlippekortListener(o, oldValue, newValue));
		this.add(cbIsKlippekort, 1, 11);
		txfUdstedteKlip = new TextField();
		txfUdstedteKlip.setPromptText("Udstedte Klip");
		txfUdstedteKlip.setPrefWidth(200d);
		txfUdstedteKlip.setDisable(true);
		ViewHelper.textFieldRestrictInt(txfUdstedteKlip);
		this.add(txfUdstedteKlip, 1, 12);
		
		btnOpretProdukt = new Button("Opret");
		btnOpretProdukt.setOnAction(e -> btnOpretProduktAction());
		btnOpretProdukt.setPrefWidth(250);
		this.add(btnOpretProdukt, 1, 13);

		btnOpdaterProdukt = new Button("Opdater");
		btnOpdaterProdukt.setOnAction(e -> btnOpdaterProduktAction());
		btnOpdaterProdukt.setPrefWidth(250);
		this.add(btnOpdaterProdukt, 1, 14);

		btnSletProdukt = new Button("Slet");
		btnSletProdukt.setOnAction(e -> btnSletProduktAction());
		btnSletProdukt.setPrefWidth(250);
		this.add(btnSletProdukt, 1, 15);

		// Column 2
		ViewHelper.label(this, 2, 1, "Produktpriser:");
		lvwPriser = new ListView<ProduktTab.ProduktPrisKategoriFormat>();
		lvwPriser.setStyle("-fx-font-family: monospace;");
		lvwPriser.setOnMouseClicked(e -> lvwPriserAction());
		this.add(lvwPriser, 2, 2, 2, 14);

		txfPris = new TextField();
		txfPris.setPromptText("Pris");
		ViewHelper.textFieldRestrictDouble(txfPris);
		this.add(txfPris, 2, 17);

		// Column 3
		btnSætPris = new Button("Sæt Pris");
		btnSætPris.setOnAction(e -> btnSætPris());
		this.add(btnSætPris, 3, 17);

		// Column 4
		ViewHelper.label(this, 4, 1, "For udlejningsprodukter:");
		cbUdlejlig = new CheckBox("Udlejligt Produkt");
		cbUdlejlig.setOnAction(e -> cbUdlejligAction());
		this.add(cbUdlejlig, 4, 2);

		ViewHelper.label(this, 4, 3, "Udlejlig pris:");
		txfUdlejligPris = new TextField();
		txfUdlejligPris.setPromptText("Pris");
		txfUdlejligPris.setDisable(true);
		ViewHelper.textFieldRestrictDouble(txfUdlejligPris);
		this.add(txfUdlejligPris, 4, 4);

		ViewHelper.label(this, 4, 5, "Udlejlig pant:");
		txfUdlejligPant = new TextField();
		txfUdlejligPant.setPromptText("Pant");
		txfUdlejligPant.setDisable(true);
		ViewHelper.textFieldRestrictDouble(txfUdlejligPant);
		this.add(txfUdlejligPant, 4, 6);
	}

	// Node updater methods;
	private void updateLvwProdukter() {
		lvwProdukter.getItems().removeAll(lvwProdukter.getItems());

		ProduktKategori selected = cboxProduktKategorier.getSelectionModel().getSelectedItem();
		if (selected != null) {
			lvwProdukter.getItems().addAll(Controller.getProdukterIKategori(selected));
		}
	}

	private void updateLvwPriser() {
		lvwPriser.getItems().removeAll(lvwPriser.getItems());

		Produkt selected = lvwProdukter.getSelectionModel().getSelectedItem();
		if (selected != null) {
			for (PrisKategori pk : Storage.getPrisKategorier()) {
				lvwPriser.getItems().add(new ProduktPrisKategoriFormat(selected, pk));
			}
		}
	}

	private void updateCboxProduktKategorier() {
		cboxProduktKategorier.getItems().removeAll(cboxProduktKategorier.getItems());
		cboxProduktKategorier.getItems().addAll(Storage.getProduktKategorier());
	}

	// Node action methods;
	private void lvwProdukterAction() {
		Produkt selected = lvwProdukter.getSelectionModel().getSelectedItem();
		if (selected != null) {
			if (selected instanceof UdlejningsProdukt) {
				cbUdlejlig.setSelected(true);
				txfUdlejligPris.setText(Double.toString(((UdlejningsProdukt) selected).getPris()));
				txfUdlejligPant.setText(Double.toString(((UdlejningsProdukt) selected).getPant()));
			} else {
				cbUdlejlig.setSelected(false);
				if (selected.getKlipPris() > 0) {
					txfKlipPris.setText(Integer.toString(selected.getKlipPris()));
					txfKlipPris.setDisable(false);
					cbKanKøbesMedKlippekort.setSelected(true);
				} else {
					txfKlipPris.clear();
					txfKlipPris.setDisable(true);
					cbKanKøbesMedKlippekort.setSelected(false);
				}
				if (selected.getUdstedteKlip() > 0) {
					txfUdstedteKlip.setText(Integer.toString(selected.getUdstedteKlip()));
					txfUdstedteKlip.setDisable(false);
					cbIsKlippekort.setSelected(true);
				} else {
					txfUdstedteKlip.clear();
					txfUdstedteKlip.setDisable(true);
					cbIsKlippekort.setSelected(false);
				}
				updateLvwPriser();
			}
			txfProduktNavn.setText(selected.getNavn());
			txaProduktBeskrivelse.setText(selected.getBeskrivelse());
			cbUdlejligAction();
		}
	}

	private void lvwPriserAction() {
		ProduktPrisKategoriFormat selected = lvwPriser.getSelectionModel().getSelectedItem();
		if (selected != null) {
			txfPris.setText(Double.toString(selected.produkt.getPris(selected.prisKategori)));
		}
	}

	private void cboxProduktKategoriAction() {
		updateLvwProdukter();
	}

	private void btnOpretProduktAction() {
		ProduktKategori selected = cboxProduktKategorier.getSelectionModel().getSelectedItem();
		if (selected != null) {
			if (cbUdlejlig.isSelected()) {
				createUdlejligtProdukt(selected);
			} else {	
				createProdukt(selected);
			}
		} else {
			setErrorText("Kategori skal vælges.");
		}
	}

	private void btnOpdaterProduktAction() {
		Produkt selected = lvwProdukter.getSelectionModel().getSelectedItem();
		if (selected != null) {
			if (cbUdlejlig.isSelected()
					&& lvwProdukter.getSelectionModel().getSelectedItem() instanceof UdlejningsProdukt) {

				Controller.updateUdlejningsProdukt((UdlejningsProdukt) selected, cboxProduktKategorier.getValue(),
						txfProduktNavn.getText(), txaProduktBeskrivelse.getText(),
						Double.parseDouble(txfUdlejligPris.getText()), Double.parseDouble(txfUdlejligPant.getText()));

			} else {

				Controller.updateProdukt(selected, cboxProduktKategorier.getValue(), txfProduktNavn.getText(),
						txaProduktBeskrivelse.getText(), Integer.parseInt(txfKlipPris.getText()), Integer.parseInt(txfUdstedteKlip.getText()));
				updateLvwProdukter();

			}
		} else {
			setErrorText("Produkt skal være valgt.");
		}
	}

	private void btnSletProduktAction() {
		Produkt selected = lvwProdukter.getSelectionModel().getSelectedItem();
		if (selected == null) {
			setErrorText("Produkt skal være valgt");
			return;
		}

		Storage.removeProdukt(lvwProdukter.getSelectionModel().getSelectedItem());
		updateLvwProdukter();
	}

	private void btnSætPris() {
		if (!ViewHelper.listViewHasSelected(lvwProdukter)) {
			lblError.setText("Vælg venlist et produkt");
			return;
		}
		if (!ViewHelper.listViewHasSelected(lvwPriser)) {
			lblError.setText("Vælg venligst en priskategori");
			return;
		}

		Controller.addPrisToProdukt(lvwProdukter.getSelectionModel().getSelectedItem(),
				lvwPriser.getSelectionModel().getSelectedItem().prisKategori, Double.parseDouble(txfPris.getText()));
		updateLvwPriser();
	}

	private void cbUdlejligAction() {
		boolean checked = cbUdlejlig.isSelected();
		lvwPriser.setDisable(checked);
		txfPris.setDisable(checked);
		txfKlipPris.setDisable(checked);
		txfUdstedteKlip.setDisable(checked);
		btnSætPris.setDisable(checked);
		cbKanKøbesMedKlippekort.setDisable(checked);
		cbIsKlippekort.setDisable(checked);

		txfUdlejligPris.setDisable(!checked);
		txfUdlejligPant.setDisable(!checked);
	}
	
	// Node Listeners;
	private void cbKanKøbesMedKlippekortListener(Object o, boolean oldValue, boolean newValue) {
		txfKlipPris.setDisable(!newValue);
		if (txfKlipPris.isDisable()) {
			txfKlipPris.clear();
		}
	}
	
	private void cbIsKlippekortListener(Object o, boolean oldValue, boolean newValue) {
		txfUdstedteKlip.setDisable(!newValue);
		if (txfUdstedteKlip.isDisable()) {
			txfUdstedteKlip.clear();
		}
	}

	// Error Label;
	private void setErrorText(String text) {
		lblError.setText(text);
	}

	private void clearErrorText() {
		lblError.setText("");
	}
	
	// helper methods
	private void createUdlejligtProdukt(ProduktKategori selected) {
		try {
			Controller.createUdlejningsProdukt(selected, txfProduktNavn.getText(),
					txaProduktBeskrivelse.getText(), Double.parseDouble(txfUdlejligPris.getText()),
					Double.parseDouble(txfUdlejligPant.getText()));
			updateLvwProdukter();
		} catch (DataFindesAlleredeException e) {
			setErrorText("Produkt findes allerede.");
		}
	}
	
	private void createProdukt(ProduktKategori selected) {
		try {
			int klipPris = (cbKanKøbesMedKlippekort.isSelected()) ? Integer.parseInt(txfKlipPris.getText()) : 0;
			int klipUdstedt = (cbIsKlippekort.isSelected()) ? Integer.parseInt(txfUdstedteKlip.getText()) : 0;

			Controller.createProdukt(selected, txfProduktNavn.getText(), txaProduktBeskrivelse.getText(), klipPris, klipUdstedt);
			updateLvwProdukter();
		} catch (NumberFormatException e) {
			setErrorText("Kun tal er accepteret.");
		} catch (DataFindesAlleredeException e) {
			setErrorText("Produkt findes allerede.");
		}
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
