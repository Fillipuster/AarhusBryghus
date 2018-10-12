package view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import model.PrisKategori;
import model.Produkt;
import model.ProduktLinje;
import model.Salg;
import storage.Storage;

import controller.Controller;

public class SalgTab extends GridPane implements ReloadableTab {
	private ListView<Produkt> lvwProdukter;
	private ListView<ProduktLinje> lvwProduktLinjer;
	private Button btnAdd, btnDelete, btnAnuller, btnKøb;
	private ComboBox<PrisKategori> cboxPrisKategorier;
	private Salg salg;
	private TextField txfAntal, txfRabat;
	private Label lblTotal;

	private void setUpPane() {
		this.setPadding(new Insets(20));
		this.setHgap(20);
		this.setVgap(10);
		this.setGridLinesVisible(false);

	}

	public SalgTab() {
		salg = Controller.createSalg();

		setUpPane();
		this.setGridLinesVisible(false);

		// Column 0
		cboxPrisKategorier = new ComboBox<>();
		cboxPrisKategorier.setOnAction(e -> cboxPrisKategorierAction());
		this.add(cboxPrisKategorier, 0, 0);

		lvwProdukter = new ListView<>();
		this.add(lvwProdukter, 0, 1, 1, 5);

		// Column 1
		btnAdd = new Button("→");
		btnAdd.setOnAction(e -> btnAddAction());
		this.add(btnAdd, 1, 3);

		btnDelete = new Button("←");
		btnDelete.setOnAction(e -> btnDeleteAction());
		this.add(btnDelete, 1, 4);

		// Column 2
		lvwProduktLinjer = new ListView<>();
		lvwProduktLinjer.setOnMouseClicked(e -> lvwProduktLinjerAction());
		lvwProduktLinjer.setStyle("-fx-font-family: monospace;");
		this.add(lvwProduktLinjer, 2, 1, 4, 5);
		
		lblTotal = ViewHelper.label(this, 2, 6, "TOTAL: 00,00 kr.");
		

		// Column 6
		txfAntal = new TextField("Antal");
		txfAntal.setOnAction(e -> txfAntalAction());
		this.add(txfAntal, 6, 2, 2, 1);

		txfRabat = new TextField("Rabat");
		txfRabat.setOnAction(e -> txfRabatAction());
		this.add(txfRabat, 6, 3, 2, 1);

		btnAnuller = new Button("Anuller");
		btnAnuller.setOnAction(e -> btnAnullerAction());
		this.add(btnAnuller, 6, 5);

		// Column 7
		btnKøb = new Button("Gennemfør Salgstransaktionativ");
		btnKøb.setOnAction(e -> btnKøbAction());
		this.add(btnKøb, 7, 5);

	}

	// Node updater methods;
	private void updateCboxPrisKategrorier() {
		cboxPrisKategorier.getItems().removeAll(cboxPrisKategorier.getItems());
		cboxPrisKategorier.getItems().addAll(Storage.getPrisKategorier());
	}

	private void updateLvwProdukter() {
		lvwProdukter.getItems().removeAll(lvwProdukter.getItems());
		PrisKategori selected = cboxPrisKategorier.getSelectionModel().getSelectedItem();
		if (selected != null) {
			lvwProdukter.getItems().addAll(Controller.getProdukterIPrisKategori(selected));
		}
	}

	private void updateLvwProduktLinjer(ProduktLinje newSelection) {
		lvwProduktLinjer.getItems().removeAll(lvwProduktLinjer.getItems());
		lvwProduktLinjer.getItems().addAll(salg.getProduktLinjer());
		
		if (newSelection != null && lvwProduktLinjer.getItems().contains(newSelection)) {
			lvwProduktLinjer.getSelectionModel().select(newSelection);
		}
		
		lblTotal.setText(String.format("TOTAL: %.2f kr.", salg.getTotalPris()));
	}

	// Node action methods;
	public void cboxPrisKategorierAction() {
		updateLvwProdukter();
	}

	public void btnAddAction() {
		Produkt selected = lvwProdukter.getSelectionModel().getSelectedItem();
		if (selected != null) {
			ProduktLinje match = null;
			for (ProduktLinje pl : lvwProduktLinjer.getItems()) {
				if (pl.getProdukt() == selected && pl.getRabat() == 0d) {
					match = pl;
				}
			}
			if (match == null) {
				Controller.createProduktLinje(salg, selected, cboxPrisKategorier.getSelectionModel().getSelectedItem(),
						1, 0d);
			} else {
				Controller.updateProduktLinje(match, match.getAntal() + 1, 0d);
			}
			updateLvwProduktLinjer(null);
		}

	}

	public void btnDeleteAction() {
		ProduktLinje selected = lvwProduktLinjer.getSelectionModel().getSelectedItem();
		if (selected != null) {
			salg.sletProduktLinje(selected);
			updateLvwProduktLinjer(null);
		}
	}

	public void txfAntalAction() {
		ProduktLinje selected = lvwProduktLinjer.getSelectionModel().getSelectedItem();
		if (selected != null) {
			Controller.updateProduktLinje(selected, Integer.parseInt(txfAntal.getText()), selected.getRabat());
		}
		updateLvwProduktLinjer(selected);
	}

	public void txfRabatAction() {
		ProduktLinje selected = lvwProduktLinjer.getSelectionModel().getSelectedItem();
		if (selected != null) {
			Controller.updateProduktLinje(selected, selected.getAntal(), Double.parseDouble(txfRabat.getText()) / 100d);
		}
		updateLvwProduktLinjer(selected);
	}

	private void lvwProduktLinjerAction() {
		ProduktLinje selected = lvwProduktLinjer.getSelectionModel().getSelectedItem();
		if (selected != null) {
			txfAntal.setText(Integer.toString(selected.getAntal()));
			txfRabat.setText(Double.toString(selected.getRabat() * 100d));
		}
	}
	
	private void btnAnullerAction() {
		salg = Controller.createSalg();
		updateLvwProduktLinjer(null);
	}
	
	private void btnKøbAction() {
		Controller.saveSalg(salg);
		salg = Controller.createSalg();
		updateLvwProduktLinjer(null);
	}

	// Tab reloading;
	@Override
	public void reload() {
		updateCboxPrisKategrorier();
	}

}
