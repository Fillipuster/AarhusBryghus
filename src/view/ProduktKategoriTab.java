package view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import model.Produkt;
import model.ProduktKategori;
import model.DataFindesAlleredeException;
import storage.Storage;
import controller.Controller;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class ProduktKategoriTab extends GridPane implements ReloadableTab {

	private ListView<ProduktKategori> lvwKategorier;
	private ListView<Produkt> lvwProdukter;
	private TextField txfKategoriNavn;
	private Button btnOpdaterKategori, btnSletKategori, btnOpretKategori;
	private Label lblError;

	public void reload() {
		updateLvwKategorier();
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

	public ProduktKategoriTab() {
		setUpPane();

		// Column 0
		ViewHelper.label(this, 0, 0, "Produktkategorier");
		lvwKategorier = new ListView<ProduktKategori>();
		lvwKategorier.setOnMouseClicked(e -> lvwKategorierAction());
		this.add(lvwKategorier, 0, 1, 1, 5);
		
		lblError = new Label();
		lblError.setTextFill(Color.RED);
		this.add(lblError, 0, 6);
		
		// Column 1
		ViewHelper.label(this, 1, 0, "Kategori navn:");
		txfKategoriNavn = new TextField();
		txfKategoriNavn.setPromptText("Produktkategori Navn");
		this.add(txfKategoriNavn, 1, 1);
		
		btnOpdaterKategori = new Button("Opdater");
		btnOpdaterKategori.setOnAction(e -> btnOpdaterKategoriAction());
		btnOpdaterKategori.setPrefWidth(200d);
		this.add(btnOpdaterKategori, 1, 2);
		
		btnSletKategori = new Button("Slet");
		btnSletKategori.setOnAction(e -> btnSletKategoriAction());
		btnSletKategori.setPrefWidth(200d);
		this.add(btnSletKategori, 1, 3);
		
		btnOpretKategori = new Button("Opret");
		btnOpretKategori.setOnAction(e -> btnOpretKategoriAction());
		btnOpretKategori.setPrefWidth(200d);
		this.add(btnOpretKategori, 1, 4);
		
		// Column 2
		ViewHelper.label(this, 2, 0, "Produkter");
		lvwProdukter = new ListView<Produkt>();
		this.add(lvwProdukter, 2, 1, 1, 5);
		
		updateLvwKategorier();
	}
	
	// Node updater methods;
	private void updateLvwKategorier() {
		lvwKategorier.getItems().removeAll(lvwKategorier.getItems());
		lvwKategorier.getItems().addAll(Storage.getProduktKategorier());
	}
	
	private void updateLvwProdukter() {
		ProduktKategori selected = lvwKategorier.getSelectionModel().getSelectedItem();
		if (selected != null) {
			lvwProdukter.getItems().removeAll(lvwProdukter.getItems());
			lvwProdukter.getItems().addAll(Controller.getProdukterIKategori(selected));
		}
	}
	
	// Node action methods;
	private void lvwKategorierAction() {
		ProduktKategori selected = lvwKategorier.getSelectionModel().getSelectedItem();
		if (selected != null) {
			updateLvwProdukter();
			txfKategoriNavn.setText(selected.getNavn());
		}
	}
	
	private void btnOpdaterKategoriAction() {
		if (txfKategoriNavn.getText().trim().isEmpty()) {
			setErrorText("Produktkategori skal have et navn.");
			return;
		}
		
		ProduktKategori selected = lvwKategorier.getSelectionModel().getSelectedItem();
		if (selected != null) {
			Controller.updateProduktKategori(selected, txfKategoriNavn.getText());
			updateLvwKategorier();			
		} else {
			setErrorText("Kategori skal vælges.");
		}
	}
	
	private void btnSletKategoriAction() {
		ProduktKategori selected = lvwKategorier.getSelectionModel().getSelectedItem();
		if (selected != null) {
			if (selected.equals(Storage.getFlaskeølProduktKategori())) {
				setErrorText("Flaskeøl produktkategori må ikke slettes.");
				return;
			}
			if (selected.equals(Storage.getGlasProduktKategori())) {
				setErrorText("Glas produktkategori må ikke slettes.");
				return;
			}
			Storage.removeProduktKategori(selected);
			updateLvwKategorier();			
		} else {
			setErrorText("Kategori skal vælges.");
		}
	}
	
	private void btnOpretKategoriAction() {
		if (txfKategoriNavn.getText().trim().isEmpty()) {
			setErrorText("Produktkategori skal have et navn.");
			return;
		}
		
		try {
			Controller.createProduktKategori(txfKategoriNavn.getText());
		} catch (DataFindesAlleredeException e) {
			lblError.setText("Produkt findes allered");
		}
		updateLvwKategorier();
	}
	
	// Error Label;
	private void setErrorText(String text) {
		lblError.setText(text);
	}
	
	private void clearErrorText() {
		lblError.setText("");
	}
	
}
