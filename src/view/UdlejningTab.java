package view;

import controller.Controller;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import model.Kunde;
import model.ProduktKategori;
import model.ProduktLinje;
import model.UdlejningsProdukt;
import model.UdlejningsSalg;
import storage.Storage;

public class UdlejningTab extends GridPane implements ReloadableTab {
	
	private UdlejningsSalg salg;
	private ListView<Kunde> lvwKunder;
	private ListView<UdlejningsProdukt> lvwUdlejligeProdukter;
	private ListView<ProduktLinje> lvwProduktLinjer;
	private TextField txfAntal;
	private Label lblTotal, lblPant, lblError;
	private Button btnAdd, btnDelete, btnAnnuler, btnGennemførSalg;
	private ComboBox<ProduktKategori> cboxProduktKategori;

	public void reload() {
		updateLvwKunder();
		updateCboxProduktKategori();
	}

	private void setUpPane() {
		this.setPadding(new Insets(20));
		this.setHgap(20);
		this.setVgap(10);
	}

	public UdlejningTab() {
		setUpPane();
		
		salg = Controller.createUdlejningsSalg();

		// Column 0
		ViewHelper.label(this, 0, 1, "Kunder");
		lvwKunder = new ListView<Kunde>();
		this.add(lvwKunder, 0, 2, 1, 5);

		// Column 1
		ViewHelper.label(this, 1, 0, "Produkt kategori:");
		cboxProduktKategori = new ComboBox<>();
		cboxProduktKategori.setOnAction(e -> cboxProduktKategoriAction());
		this.add(cboxProduktKategori, 1, 1);

		ViewHelper.label(this, 2, 0, "Produkter");
		lvwUdlejligeProdukter = new ListView<UdlejningsProdukt>();
		this.add(lvwUdlejligeProdukter, 1, 2, 1, 5);

		// Column 2
		btnAdd = new Button("→");
		btnAdd.setOnAction(e -> btnAddAction());
		this.add(btnAdd, 2, 5);

		btnDelete = new Button("←");
		btnDelete.setOnAction(e -> btnDeleteAction());
		this.add(btnDelete, 2, 6);

		lblError = new Label("");
		lblError.setTextFill(Color.RED);
		this.add(lblError, 2, 7);

		// Column 3
		ViewHelper.label(this, 3, 0, "Valgte Produkter");
		lvwProduktLinjer = new ListView<>();
		this.add(lvwProduktLinjer, 3, 2, 1, 5);

		lblTotal = ViewHelper.label(this, 3, 7, "TOTAL: 00.00 kr.\nderaf pant: 00.00 kr.");
		lblTotal.setStyle("-fx-font-size: 16;\n-fx-font-family: monospace;");

		// Column 4
		txfAntal = new TextField("Antal");
		txfAntal.setOnAction(e -> txfAntalAction());
		this.add(txfAntal, 4, 5);

		btnAnnuler = new Button("Annuller");
		// btnAnnuler.setOnAction(e -> btnAnnulerAction);
		this.add(btnAnnuler, 4, 7);

		btnGennemførSalg = new Button("Gennemfør Salg");
		this.add(btnGennemførSalg, 4, 8);
	}

	// Node updater methods;
	private void updateLblTotal() {
		lblTotal.setText(String.format("TOTAL: %.2f kr.%nderaf pant: %.2f kr.", salg.getTotalPris() + salg.getTotalPant(), salg.getTotalPant()));
	}
	
	private void updateLvwKunder() {
		lvwKunder.getItems().setAll(Storage.getKunder());
	}

	private void updateCboxProduktKategori() {
		cboxProduktKategori.getItems().setAll(Storage.getProduktKategorier());
	}

	private void updateLvwProduktLinjer() {
		lvwProduktLinjer.getItems().setAll(salg.getProduktLinjer());
		updateLblTotal();
	}

	private void updateLvwUdlejligeProdukter() {
		ProduktKategori selected = cboxProduktKategori.getSelectionModel().getSelectedItem();
		if (selected != null) {
			lvwUdlejligeProdukter.getItems().setAll(Controller.getUdlejningsProdukterIProduktKategori(selected));

		}
	}

	// Node action methods;
	private void cboxProduktKategoriAction() {
		updateLvwUdlejligeProdukter();
	}

	private void btnAddAction() {
		UdlejningsProdukt selected = lvwUdlejligeProdukter.getSelectionModel().getSelectedItem();
		if (selected != null) {
			Controller.createProduktLinje(salg, selected, null, 1, 0d);
			updateLvwProduktLinjer();
		}
	}

	private void btnDeleteAction() {
		ProduktLinje selected = lvwProduktLinjer.getSelectionModel().getSelectedItem();
		if (selected != null) {
			Controller.sletUdlejligProduktLinje(salg, selected);
			updateLvwProduktLinjer();
		} else {
			setErrorText("Produktlinje skal være valgt.");
		}
	}

	public void txfAntalAction() {
		ProduktLinje selected = lvwProduktLinjer.getSelectionModel().getSelectedItem();
		if (selected != null) {
			try {
				Controller.updateProduktLinje(selected, Integer.parseInt(txfAntal.getText()), 0d);
			} catch (NumberFormatException nfe) {
				setErrorText("Produktmængden skal være et heltal");
			}
		}
		updateLvwProduktLinjer();
	}

	// Error label related;
	private void setErrorText(String text) {
		lblError.setText(text);
	}

}
