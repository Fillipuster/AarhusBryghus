package view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import model.BetalingsMetode;
import model.PrisKategori;
import model.Produkt;
import model.ProduktLinje;
import model.Salg;
import storage.Storage;

import controller.Controller;

public class SalgTab extends GridPane implements ReloadableTab {
	private ListView<ProduktMedKategoriFormatter> lvwProdukter;
	private ListView<ProduktLinje> lvwProduktLinjer;
	private Button btnAdd, btnDelete, btnAnuller, btnKøb;
	private ComboBox<PrisKategori> cboxPrisKategorier;
	private Salg salg;
	private TextField txfAntal, txfRabat;
	private Label lblTotal;
	private ComboBox<BetalingsMetode> cboxBetalingsMetoder;

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
		this.add(lvwProdukter, 0, 1, 1, 10);

		// Column 1
		btnAdd = new Button("→");
		btnAdd.setOnAction(e -> btnAddAction());
		btnAdd.setStyle("-fx-font-size: 16;");
		this.add(btnAdd, 1, 3);

		btnDelete = new Button("←");
		btnDelete.setOnAction(e -> btnDeleteAction());
		btnDelete.setStyle("-fx-font-size: 16;");
		this.add(btnDelete, 1, 4);

		// Column 2
		lvwProduktLinjer = new ListView<>();
		lvwProduktLinjer.setOnMouseClicked(e -> lvwProduktLinjerAction());
		lvwProduktLinjer.setStyle("-fx-font-family: monospace;");
		this.add(lvwProduktLinjer, 2, 1, 4, 10);
		
		lblTotal = ViewHelper.label(this, 2, 11, "TOTAL: 00,00 kr.");
		lblTotal.setStyle("-fx-font-size: 16;\n-fx-font-family: monospace;");

		// Column 6
		ViewHelper.label(this, 6, 1, "Produktmængde");
		txfAntal = new TextField("ANTAL");
		txfAntal.setOnAction(e -> txfAntalAction());
		this.add(txfAntal, 6, 2, 2, 1);

		ViewHelper.label(this, 6, 3, "Rabat (%)");
		txfRabat = new TextField("RABAT");
		txfRabat.setOnAction(e -> txfRabatAction());
		this.add(txfRabat, 6, 4, 2, 1);
		
		ViewHelper.label(this, 6, 8, "Vælg Betalingsmetode:");
		cboxBetalingsMetoder = new ComboBox<>();
		this.add(cboxBetalingsMetoder, 6, 9);

		btnAnuller = new Button("Anuller");
		btnAnuller.setOnAction(e -> btnAnullerAction());
		this.add(btnAnuller, 6, 11);

		// Column 7
		btnKøb = new Button("Gennemfør Salg");
		btnKøb.setOnAction(e -> btnKøbAction());
		this.add(btnKøb, 7, 11);
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
			for (Produkt p : Controller.getProdukterIPrisKategori(selected)) {
				lvwProdukter.getItems().add(new ProduktMedKategoriFormatter(p));
			}
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
	
	private void updateCboxBetalingsMetoder() {
		cboxBetalingsMetoder.getItems().removeAll(cboxBetalingsMetoder.getItems());
		cboxBetalingsMetoder.getItems().addAll(Storage.getBetalingsMetoder());
	}

	// Node action methods;
	public void cboxPrisKategorierAction() {
		updateLvwProdukter();
	}

	public void btnAddAction() {
		ProduktMedKategoriFormatter selected = lvwProdukter.getSelectionModel().getSelectedItem();
		if (selected != null) {
			ProduktLinje match = null;
			for (ProduktLinje pl : lvwProduktLinjer.getItems()) {
				if (pl.getProdukt() == selected.produkt && pl.getRabat() == 0d) {
					match = pl;
				}
			}
			if (match == null) {
				Controller.createProduktLinje(salg, selected.produkt, cboxPrisKategorier.getSelectionModel().getSelectedItem(),
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
		resetSalg();
	}
	
	private void btnKøbAction() {
		if (!ViewHelper.comboBoxHasSelected(cboxBetalingsMetoder)) {return;}
		
		Controller.setSalgBetalingsMetode(salg, cboxBetalingsMetoder.getValue());
		Controller.saveSalg(salg);
		resetSalg();
	}
	
	// Helper methods;
	private void resetSalg() {
		salg = Controller.createSalg();
		updateLvwProduktLinjer(null);
	}

	// Tab reloading;
	@Override
	public void reload() {
		updateCboxPrisKategrorier();
		updateCboxBetalingsMetoder();
	}
	
	// ListView formatting classes;
	private class ProduktMedKategoriFormatter {
		public Produkt produkt;
		
		public ProduktMedKategoriFormatter(Produkt produkt) {
			this.produkt = produkt;
		}
		
		@Override
		public String toString() {
			return String.format("%-20s\t(%s)", produkt.getNavn(), produkt.getProduktKategori().getNavn());
		}
	}

}
