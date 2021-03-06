package view;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import model.BetalingsMetode;
import model.Gaveaeske;
import model.PrisKategori;
import model.Produkt;
import model.ProduktLinje;
import model.Salg;
import storage.Storage;

import controller.Controller;

public class SalgTab extends GridPane implements ReloadableTab {
	
	private Salg salg;
	private ListView<ProduktMedKategoriFormatter> lvwProdukter;
	private ListView<ProduktLinje> lvwProduktLinjer;
	private Button btnAdd, btnDelete, btnAnuller, btnKøb, btnOpretGaveæske;
	private ComboBox<PrisKategori> cboxPrisKategorier;
	private TextField txfAntal, txfRabat;
	private Label lblTotal, lblError;
	private ComboBox<BetalingsMetode> cboxBetalingsMetoder;

	@Override
	public void reload() {
		updateCboxPrisKategrorier();
		updateCboxBetalingsMetoder();
		setErrorText("");
	}
	
	private void setUpPane() {
		this.setPadding(new Insets(20));
		this.setHgap(20);
		this.setVgap(10);
		this.setAlignment(Pos.CENTER);

		// Clear error label on mouse event;
		this.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				clearErrorText();
			}
		});
	}

	public SalgTab() {
		salg = Controller.createSalg();
		
		setUpPane();
		this.setGridLinesVisible(false);

		// Column 0
		ViewHelper.label(this, 0, 0, "Vælg priskategori (salgssituation):");
		cboxPrisKategorier = new ComboBox<>();
		cboxPrisKategorier.setOnAction(e -> cboxPrisKategorierAction());
		cboxPrisKategorier.setPromptText("Priskategori...");
		this.add(cboxPrisKategorier, 0, 1);

		lvwProdukter = new ListView<>();
		lvwProdukter.setStyle("-fx-font-family: Consolas;\n-fx-font-size: 14;");
		lvwProdukter.setOnMouseClicked((MouseEvent) -> lvwProdukterAction(MouseEvent));
		this.add(lvwProdukter, 0, 2, 1, 10);

		lblError = new Label("");
		lblError.setTextFill(Color.RED);
		this.add(lblError, 0, 13);

		btnOpretGaveæske = new Button("Opret Gaveæske");
		btnOpretGaveæske.setOnAction(e -> btnOpretGaveæskeAction());
		this.add(btnOpretGaveæske, 0, 12);

		// Column 1
		btnAdd = new Button("→");
		btnAdd.setOnAction(e -> btnAddAction());
		btnAdd.setStyle("-fx-font-size: 16;\n-fx-font-weight: bold;");
		this.add(btnAdd, 1, 5);

		btnDelete = new Button("←");
		btnDelete.setOnAction(e -> btnDeleteAction());
		btnDelete.setStyle("-fx-font-size: 16;");
		this.add(btnDelete, 1, 10);

		// Column 2
		ViewHelper.label(this, 2, 1, "Produkter i indkøbskurv:");
		lvwProduktLinjer = new ListView<>();
		lvwProduktLinjer.setOnMouseClicked(e -> lvwProduktLinjerAction());
		lvwProduktLinjer.setStyle("-fx-font-family: monospace;");
		this.add(lvwProduktLinjer, 2, 2, 4, 10);

		lblTotal = ViewHelper.label(this, 2, 12, "TOTAL: 00,00 kr.\n(0 klip)");
		lblTotal.setStyle("-fx-font-size: 16;\n-fx-font-family: monospace;");

		// Column 6
		ViewHelper.label(this, 6, 2, "Antal produkter:");
		txfAntal = new TextField();
		txfAntal.setPromptText("Produktmængde");
		txfAntal.setOnAction(e -> txfAntalAction());
		ViewHelper.textFieldRestrictInt(txfAntal);
		this.add(txfAntal, 6, 3, 2, 1);

		ViewHelper.label(this, 6, 4, "Rabat (%):");
		txfRabat = new TextField();
		txfRabat.setPromptText("Rabat (%)");
		txfRabat.setOnAction(e -> txfRabatAction());
		ViewHelper.textFieldRestrictDouble(txfRabat);
		this.add(txfRabat, 6, 5, 2, 1);

		ViewHelper.label(this, 6, 8, "Vælg betalingsmetode:");
		cboxBetalingsMetoder = new ComboBox<>();
		cboxBetalingsMetoder.setPromptText("Betalingsmetode...");
		this.add(cboxBetalingsMetoder, 6, 10);

		btnAnuller = new Button("Anuller");
		btnAnuller.setOnAction(e -> btnAnullerAction());
		this.add(btnAnuller, 6, 12);

		// Column 7
		btnKøb = new Button("Gennemfør Salg");
		btnKøb.setOnAction(e -> btnKøbAction());
		this.add(btnKøb, 7, 12);
	}
	
	private void resetSalg() {
		salg = Controller.createSalg();
		updateLvwProduktLinjer(null);
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
		lvwProduktLinjer.getItems().setAll(salg.getProduktLinjer());

		if (newSelection != null && lvwProduktLinjer.getItems().contains(newSelection)) {
			lvwProduktLinjer.getSelectionModel().select(newSelection);
		}

		String klipString = (salg.getTotalKlipPris() >= 0) ? String.format("(%d klip)", salg.getTotalKlipPris())
				: "(kun penge >:D)";
		lblTotal.setText(String.format("TOTAL: %.2f kr.\n%s", salg.getTotalPris(), klipString));
	}
	

	private void updateCboxBetalingsMetoder() {
		cboxBetalingsMetoder.getItems().removeAll(cboxBetalingsMetoder.getItems());
		cboxBetalingsMetoder.getItems().addAll(Storage.getBetalingsMetoder());
	}
	
	private void cboxPrisKategorierAction() {
		resetSalg();
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
				Controller.createProduktLinje(salg, selected.produkt,
						cboxPrisKategorier.getSelectionModel().getSelectedItem(), 1, 0d);
			} else {
				Controller.updateProduktLinje(match, match.getAntal() + 1, 0d);
			}
			updateLvwProduktLinjer(null);
		} else {
			setErrorText("Produkt skal være valgt.");
		}

	}

	public void btnOpretGaveæskeAction() {
		if (ViewHelper.comboBoxHasSelected(cboxPrisKategorier)) {
			GaveaeskeWindow window = new GaveaeskeWindow("Gaveæske", MainApp.getMainStage());
			window.showAndWait();
			Gaveaeske gaveæske = window.getGaveæske();
			if (gaveæske != null) {			
				Controller.createProduktLinje(salg, window.getGaveæske(),
						cboxPrisKategorier.getSelectionModel().getSelectedItem(), 1, 0d);
				updateLvwProduktLinjer(null);
			}			
		} else {
			setErrorText("Priskategori skal være valgt.");
		}
	}

	public void btnDeleteAction() {
		ProduktLinje selected = lvwProduktLinjer.getSelectionModel().getSelectedItem();
		if (selected != null) {
			salg.sletProduktLinje(selected);
			updateLvwProduktLinjer(null);
		} else {
			setErrorText("Produktlinje skal være valgt.");
		}
	}

	public void txfAntalAction() {
		ProduktLinje selected = lvwProduktLinjer.getSelectionModel().getSelectedItem();
		if (selected != null) {
			try {
				int antal = Integer.parseInt(txfAntal.getText());
				if (antal < 1) {
					setErrorText("Produktmængde må ikke være 0.");
					return;
				}
				Controller.updateProduktLinje(selected, antal, selected.getRabat());
			} catch (NumberFormatException e) {
				setErrorText("Produktmængde skal være et heltal.");
			}
		}
		updateLvwProduktLinjer(selected);
	}

	public void txfRabatAction() {
		ProduktLinje selected = lvwProduktLinjer.getSelectionModel().getSelectedItem();
		if (selected != null) {
			try {
				double rabat = Double.parseDouble(txfRabat.getText()) / 100d;
				if (rabat > 1d) {
					setErrorText("Rabat må ikke overstige 100%.");
					return;
				}
				Controller.updateProduktLinje(selected, selected.getAntal(),
						rabat);
			} catch (NumberFormatException e) {
				setErrorText("Rabat skal være et tal.");
			}
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
		BetalingsMetode betalingsMetode = cboxBetalingsMetoder.getValue();
		if (betalingsMetode != null) {
			if (salg.getTotalKlipPris() <= 0 && betalingsMetode.isBrugerKlip()) {
				setErrorText("Produkt kan ikke betales med klippekort.");
				return;
			}
			Controller.setSalgBetalingsMetode(salg, betalingsMetode);
			Controller.saveSalg(salg);
			resetSalg();
		} else {
			setErrorText("Betalingsmetode skal være valgt.");
		}

	}

	private void lvwProdukterAction(MouseEvent e) {
		if (e.getButton().equals(MouseButton.PRIMARY)) {
			if (e.getClickCount() >= 2) {
				btnAddAction();
			}
		}
	}

	// Error Label;
	private void setErrorText(String text) {
		lblError.setText(text);
	}

	private void clearErrorText() {
		lblError.setText("");
	}

	// ListView formatting classes;
	private class ProduktMedKategoriFormatter {
		public Produkt produkt;

		public ProduktMedKategoriFormatter(Produkt produkt) {
			this.produkt = produkt;
		}

		@Override
		public String toString() {
			return String.format("%-15s(%s)", produkt.getNavn(), produkt.getProduktKategori().getNavn());
		}
	}

}
