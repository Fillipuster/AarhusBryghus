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
	private TextField txfProduktNavn, txfPris, txfKlipPris, txfUdlejligPris, txfUdlejligPant; 
	private TextArea txaProduktBeskrivelse;
	private Button btnOpdaterProdukt, btnSletProdukt, btnOpretProdukt, btnSætPris;
	private CheckBox cbUdlejlig;
	
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
		this.add(lvwProdukter, 0, 2, 1, 11);

		// Column 1
		ViewHelper.label(this, 1, 1, "Produktnavn:");
		txfProduktNavn = new TextField("PRODUKT NAVN");
		txfProduktNavn.setPrefWidth(200);
		this.add(txfProduktNavn, 1, 2);

		ViewHelper.label(this, 1, 3, "Produktbeskrivelse:");
		txaProduktBeskrivelse = new TextArea("BESKRIVELSE");
		txaProduktBeskrivelse.setPrefWidth(200);
		this.add(txaProduktBeskrivelse, 1, 4, 1, 3);
		
		ViewHelper.label(this, 1, 7, "Pris i klippekort klip:");
		txfKlipPris = new TextField("1");
		txfKlipPris .setPrefWidth(200);
		ViewHelper.textFieldRestrictInt(txfKlipPris);
		this.add(txfKlipPris, 1, 8);

		btnOpretProdukt = new Button("Opret");
		btnOpretProdukt.setOnAction(e -> btnOpretProduktAction());
		btnOpretProdukt.setPrefWidth(250);
		this.add(btnOpretProdukt, 1, 9);

		btnOpdaterProdukt = new Button("Opdater");
		btnOpdaterProdukt.setOnAction(e -> btnOpdaterProduktAction());
		btnOpdaterProdukt.setPrefWidth(250);
		this.add(btnOpdaterProdukt, 1, 10);

		btnSletProdukt = new Button("Slet");
		btnSletProdukt.setOnAction(e -> btnSletProduktAction());
		btnSletProdukt.setPrefWidth(250);
		this.add(btnSletProdukt, 1, 11);

		// Column 2
		ViewHelper.label(this, 2, 1, "Produktpriser:");
		lvwPriser = new ListView<ProduktTab.ProduktPrisKategoriFormat>();
		lvwPriser.setStyle("-fx-font-family: monospace;");
		lvwPriser.setOnMouseClicked(e -> lvwPriserAction());
		this.add(lvwPriser, 2, 2, 2, 11);
		
		txfPris = new TextField("59.95");
		ViewHelper.textFieldRestrictDouble(txfPris);
		this.add(txfPris, 2, 14);

		// Column 3
		btnSætPris = new Button("Sæt Pris");
		btnSætPris.setOnAction(e -> btnSætPris());
		this.add(btnSætPris, 3, 14);
		
		lblError = new Label();
		lblError.setTextFill(Color.RED);
		this.add(lblError, 0, 13);
		
		// Column 4
		ViewHelper.label(this, 4, 1, "For udlejningsprodukter:");
		cbUdlejlig = new CheckBox("Udlejligt Produkt");
		cbUdlejlig.setOnAction(e -> cbUdlejligAction());
		this.add(cbUdlejlig, 4, 2);
		
		ViewHelper.label(this, 4, 3, "Udlejlig pris:");
		txfUdlejligPris = new TextField("295.85");
		txfUdlejligPris.setDisable(true);
		ViewHelper.textFieldRestrictDouble(txfUdlejligPris);
		this.add(txfUdlejligPris, 4, 4);
		
		ViewHelper.label(this, 4, 5, "Udlejlig pant:");
		txfUdlejligPant = new TextField("109.50");
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
				txfUdlejligPris.setText(Double.toString(((UdlejningsProdukt)selected).getPris()));
				txfUdlejligPant.setText(Double.toString(((UdlejningsProdukt)selected).getPant()));
			} else {
				cbUdlejlig.setSelected(false);
				txfKlipPris.setText(Integer.toString(selected.getKlipPris()));
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
		if (cbUdlejlig.isSelected()) {
			try {
				Controller.createUdlejningsProdukt(cboxProduktKategorier.getValue(), txfProduktNavn.getText(),
						txaProduktBeskrivelse.getText(), Double.parseDouble(txfUdlejligPris.getText()),
						Double.parseDouble(txfUdlejligPant.getText()));
				updateLvwProdukter();
			} catch (NumberFormatException e) {
				setErrorText("Pris og pant skal være tal.");
			}
		} else {
			try {
			Controller.createProdukt(cboxProduktKategorier.getValue(), txfProduktNavn.getText(),
					txaProduktBeskrivelse.getText(), Integer.parseInt(txfKlipPris.getText()));
			updateLvwProdukter();
			}
			catch(NumberFormatException e) {
				setErrorText("Klippris skal være et tal.");
			}
		}
	}

	private void btnOpdaterProduktAction() {
		if (!ViewHelper.listViewHasSelected(lvwProdukter)) {return;}
		
		if (cbUdlejlig.isSelected() && lvwProdukter.getSelectionModel().getSelectedItem() instanceof UdlejningsProdukt) {
			try {
				Controller.updateUdlejningsProdukt((UdlejningsProdukt)lvwProdukter.getSelectionModel().getSelectedItem(), cboxProduktKategorier.getValue(), txfProduktNavn.getText(), txaProduktBeskrivelse.getText(), Double.parseDouble(txfUdlejligPris.getText()), Double.parseDouble(txfUdlejligPant.getText()));
			} catch (NumberFormatException e) {
				setErrorText("Pris og pant skal være tal.");
			}
		} else {
			try {
				Controller.updateProdukt(lvwProdukter.getSelectionModel().getSelectedItem(), cboxProduktKategorier.getValue(),
						txfProduktNavn.getText(), txaProduktBeskrivelse.getText(), Integer.parseInt(txfKlipPris.getText()));
				updateLvwProdukter();			
			} catch(NumberFormatException e) {
				setErrorText("Klippris skal være et tal.");
			}			
		}
	}

	private void btnSletProduktAction() {
		if (!ViewHelper.listViewHasSelected(lvwProdukter)) {return;}
		
		Storage.removeProdukt(lvwProdukter.getSelectionModel().getSelectedItem());
		updateLvwProdukter();
	}
	
	private void btnSætPris() {
		if (!ViewHelper.listViewHasSelected(lvwProdukter)) {return;}
		if (!ViewHelper.listViewHasSelected(lvwPriser)) {return;}
		
		Controller.addPrisToProdukt(lvwProdukter.getSelectionModel().getSelectedItem(),
				lvwPriser.getSelectionModel().getSelectedItem().prisKategori, Double.parseDouble(txfPris.getText()));
		updateLvwPriser();
	}
	
	private void cbUdlejligAction() {
		boolean checked = cbUdlejlig.isSelected();
		lvwPriser.setDisable(checked);
		txfPris.setDisable(checked);
		txfKlipPris.setDisable(checked);
		btnSætPris.setDisable(checked);
		
		txfUdlejligPris.setDisable(!checked);
		txfUdlejligPant.setDisable(!checked);
	}
	
	// Error Label;
	private void setErrorText(String text) {
		lblError.setText(text);
	}
	
	private void clearErrorText() {
		lblError.setText("");
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
