package view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import model.NavnFindesAlleredeException;
import model.PrisKategori;
import storage.Storage;
import controller.Controller;
import javafx.event.EventHandler;
import javafx.geometry.Insets;

public class PrisKategoriTab extends GridPane implements ReloadableTab {

	private ListView<PrisKategori> lvwKategorier;
	private Label lblError;
	private TextField txfKategoriNavn;
	private Button btnOpdaterKategori, btnSletKategori, btnOpretKategori;

	public void reload() {
		updateLvwKategorier();
	}
	
	private void setUpPane() {
		this.setPadding(new Insets(20));
		this.setHgap(20);
		this.setVgap(10);
		
		// Clear error label on mouse event;
		this.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				clearErrorText();
			}
		});
	}

	public PrisKategoriTab() {
		setUpPane();

		// Column 0
		ViewHelper.label(this, 0, 0, "Priskategorier");
		lvwKategorier = new ListView<PrisKategori>();
		lvwKategorier.setOnMouseClicked(e -> lvwKategorierAction());
		this.add(lvwKategorier, 0, 1, 1, 5);
		
		lblError = new Label();
		lblError.setTextFill(Color.RED);
		this.add(lblError, 0, 6);
		
		// Column 1
		ViewHelper.label(this, 1, 0, "Kategori navn:");
		txfKategoriNavn = new TextField();
		txfKategoriNavn.setPromptText("Priskategori Navn");
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
		
		updateLvwKategorier();
	}
	
	// Node updater methods;
	private void updateLvwKategorier() {
		lvwKategorier.getItems().removeAll(lvwKategorier.getItems());
		lvwKategorier.getItems().addAll(Storage.getPrisKategorier());
	}
	
	// Node action methods;
	private void lvwKategorierAction() {
		PrisKategori selected = lvwKategorier.getSelectionModel().getSelectedItem();
		if (selected != null) {
			txfKategoriNavn.setText(selected.getNavn());
		}
	}
	
	private void btnOpdaterKategoriAction() {
		if (ViewHelper.listViewHasSelected(lvwKategorier)) {
			Controller.updatePrisKategori(lvwKategorier.getSelectionModel().getSelectedItem(), txfKategoriNavn.getText());
			updateLvwKategorier();			
		} else {
			setErrorText("Kategori skal vælges.");
		}
	}
	
	private void btnSletKategoriAction() {
		if (ViewHelper.listViewHasSelected(lvwKategorier)) {
			Storage.removePrisKategori(lvwKategorier.getSelectionModel().getSelectedItem());
			updateLvwKategorier();			
		} else {
			setErrorText("Kategori skal vælges.");
		}
	}
	
	private void btnOpretKategoriAction() {
		try {
			Controller.createPrisKategori(txfKategoriNavn.getText());
		} catch (NavnFindesAlleredeException e) {
			setErrorText("Pris kategori findes allerede");
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
