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
import model.ProduktKategoriType;
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
	private Label lblTotal, lblError;
	private Button btnAdd, btnDelete, btnAnnuller, btnGennemførSalg;
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
		ViewHelper.label(this, 0, 1, "Vælg kunde:");
		lvwKunder = new ListView<Kunde>();
		this.add(lvwKunder, 0, 2, 1, 10);

		lblError = new Label();
		lblError.setTextFill(Color.RED);
		this.add(lblError, 0, 13);
		
		// Column 1
		ViewHelper.label(this, 1, 0, "Vælg produktkategori:");
		cboxProduktKategori = new ComboBox<>();
		cboxProduktKategori.setOnAction(e -> cboxProduktKategoriAction());
		this.add(cboxProduktKategori, 1, 1);

		lvwUdlejligeProdukter = new ListView<UdlejningsProdukt>();
		this.add(lvwUdlejligeProdukter, 1, 2, 1, 10);

		// Column 2
		btnAdd = new Button("→");
		btnAdd.setOnAction(e -> btnAddAction());
		this.add(btnAdd, 2, 5);

		btnDelete = new Button("←");
		btnDelete.setOnAction(e -> btnDeleteAction());
		this.add(btnDelete, 2, 6);

		// Column 3
		ViewHelper.label(this, 3, 1, "Produkter i udlejning:");
		lvwProduktLinjer = new ListView<>();
		this.add(lvwProduktLinjer, 3, 2, 1, 10);

		lblTotal = ViewHelper.label(this, 3, 13, "TOTAL: 00.00 kr.\nderaf pant: 00.00 kr.");
		lblTotal.setStyle("-fx-font-size: 16;\n-fx-font-family: monospace;");

		// Column 4
		ViewHelper.label(this, 4, 1, "Produktmængde:");
		txfAntal = new TextField("1");
		txfAntal.setOnAction(e -> txfAntalAction());
		ViewHelper.textFieldRestrictInt(txfAntal);
		this.add(txfAntal, 4, 2);

		btnAnnuller = new Button("Annuller");
		btnAnnuller.setOnAction(e -> btnAnnullerAction());
		this.add(btnAnnuller, 4, 7);

		btnGennemførSalg = new Button("Gennemfør Salg");
		btnGennemførSalg.setOnAction(e -> btnGennemførSalgAction());
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
		cboxProduktKategori.getItems().setAll(Controller.getProduktKategorierAfType(ProduktKategoriType.UDLEJNING));
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
		if (cboxProduktKategori.getSelectionModel().getSelectedItem() != null) {
			updateLvwUdlejligeProdukter();
		}
	}

	private void btnAddAction() {
		UdlejningsProdukt selected = lvwUdlejligeProdukter.getSelectionModel().getSelectedItem();
		if (selected != null) {
			ProduktLinje match = null;
			for (ProduktLinje pl : salg.getProduktLinjer()) {
				if (pl.getProdukt() == selected && pl.getRabat() == 0d) {
					match = pl;
				}
			}
			
			if (match != null) {
				Controller.updateProduktLinje(match, match.getAntal() + 1, 0d);
			} else {
				Controller.createProduktLinje(salg, selected, null, 1, 0d);
			}
			updateLvwProduktLinjer();
		} else {
			setErrorText("Produkt skal vælges.");
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
				setErrorText("Produktmængden skal være et tal.");
			}
		}
		updateLvwProduktLinjer();
	}
	
	private void btnAnnullerAction() {
		salg = Controller.createUdlejningsSalg();
		updateLvwProduktLinjer();
	}
	
	private void btnGennemførSalgAction() {
		Kunde selected = lvwKunder.getSelectionModel().getSelectedItem();
		
		if (selected != null && !lvwProduktLinjer.getItems().isEmpty()) {
			Controller.saveSalg(salg);
			Controller.setUdlejningsSalgKunde(salg, selected);
			btnAnnullerAction();
			lblError.setText("");
		} else {
			lblError.setText("Vælg venligst en kunde og et eller flere \nprodukter for at gennemføre salget");
		}
	}

	// Error label related;
	private void setErrorText(String text) {
		lblError.setText(text);
	}

}
