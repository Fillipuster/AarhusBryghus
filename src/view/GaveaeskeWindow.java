package view;

import java.util.ArrayList;
import controller.Controller;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Gaveaeske;
import model.GaveaeskeEmballage;
import model.Produkt;
import storage.Storage;

public class GaveaeskeWindow extends Stage {

	private Gaveaeske gaveæske;
	
	private ListView<Produkt> lvwProdukter, lvwTilføjedeProdukter;
	private Button btnTilføj, btnFjern, btnAccepter, btnAnnuller;
	private Label lblPris, lblError;
	private ListView<GaveaeskeEmballage> lvwEmballage;

	public GaveaeskeWindow(String title, Stage owner) {
		this.initOwner(owner);
		this.initStyle(StageStyle.DECORATED);
		this.initModality(Modality.APPLICATION_MODAL);
		this.setOnCloseRequest(e -> btnAnnullerAction());
		this.setMinHeight(100);
		this.setMinWidth(200);
		this.setResizable(false);
		
		this.setTitle(title);
		GridPane pane = new GridPane();
		this.initContent(pane);

		Scene scene = new Scene(pane);
		this.setScene(scene);
		scene.getStylesheets().add("style.css");
	}

	private void setUpPane(GridPane pane) {
		pane.setPadding(new Insets(20));
		pane.setHgap(20);
		pane.setVgap(10);
		
		// Clear error label on mouse event;
		this.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				clearErrorText();
			}
		});
	}

	public void initContent(GridPane pane) {
		setUpPane(pane);

		// Column 0
		ViewHelper.label(pane, 0, 0, "Tilgængelige produkter:");
		
		lvwProdukter = new ListView<>();
		pane.add(lvwProdukter, 0, 1, 1, 8);
		
		lblError = new Label();
		lblError.setTextFill(Color.RED);
		pane.add(lblError, 0, 9);

		// Column 1
		btnTilføj = new Button("→");
		btnTilføj.setOnAction(e -> btnTilføjAction());
		pane.add(btnTilføj, 1, 1);

		btnFjern = new Button("←");
		btnFjern.setOnAction(e -> btnFjernAction());
		pane.add(btnFjern, 1, 2);

		// Column 2
		ViewHelper.label(pane, 2, 0, "Produkter i gaveæske:");

		lvwTilføjedeProdukter = new ListView<>();
		pane.add(lvwTilføjedeProdukter, 2, 1, 1, 8);
		
		lblPris = ViewHelper.label(pane, 2, 9, "PRIS: 0.0 kr.");
		lblPris.setStyle("-fx-font-size: 16;\n-fx-font-family: monospace;");
		
		// Column 3
		ViewHelper.label(pane, 3, 0, "Gaveæske pakning/emballage:");
		lvwEmballage = new ListView<GaveaeskeEmballage>();
		lvwEmballage.setOnMouseClicked(e -> lvwEmballageAction());
		pane.add(lvwEmballage, 3, 1);
		
		btnAccepter = new Button("Accepter");
		btnAccepter.setOnAction(e -> btnAccepterAction());
		pane.add(btnAccepter, 3, 5);

		btnAnnuller = new Button("Annuller");
		btnAnnuller.setOnAction(e -> btnAnnullerAction());
		pane.add(btnAnnuller, 3, 6);
		
		updateLvwProdukter();
		updateLvwEmballage();
		
		gaveæske = Controller.createGaveaeske();
	}

	// Node updater methods;
	private void updateLvwProdukter() {
		lvwProdukter.getItems().removeAll(lvwProdukter.getItems());
		ArrayList<Produkt> produkter = new ArrayList<>();
		for (Produkt p : Controller.getProdukterIPrisKategori(Storage.getButikPrisKategori())) {
			if (p.getProduktKategori() == Storage.getFlaskeølProduktKategori() || p.getProduktKategori() == Storage.getGlasProduktKategori()) {
				produkter.add(p);
			}
		}
		lvwProdukter.getItems().addAll(produkter);
	}
	
	private void updateLvwEmballage() {
		lvwEmballage.getItems().setAll(Storage.getGaveaeskeEmballager());
	}
	
	// Node action methods;
	private void btnTilføjAction() {
		if (ViewHelper.listViewHasSelected(lvwProdukter)) {
			Produkt selected = lvwProdukter.getSelectionModel().getSelectedItem();
			lvwTilføjedeProdukter.getItems().add(selected);
			gaveæske.addProdukt(selected);
			updateTotalPris();
		} else {
			setErrorText("Produkt skal vælges.");
		}
	}

	private void btnFjernAction() {
		if (ViewHelper.listViewHasSelected(lvwTilføjedeProdukter)) {
			Produkt selected = lvwTilføjedeProdukter.getSelectionModel().getSelectedItem();
			lvwTilføjedeProdukter.getItems().remove(selected);
			gaveæske.removeProdukt(selected);
			updateTotalPris();
		} else {
			setErrorText("Produkt skal vælges.");
		}
	}
	
	private void lvwEmballageAction() {
		GaveaeskeEmballage selected = lvwEmballage.getSelectionModel().getSelectedItem();
		if (selected != null) {
			Controller.setGaveaeskeEmballage(gaveæske, selected);
			updateTotalPris();			
		}
	}
	
	private void btnAccepterAction() {
		if (ViewHelper.listViewHasSelected(lvwEmballage)) {
			this.close();			
		} else {
			setErrorText("Emballage skal vælges.");
		}
	}
	
	private void updateTotalPris() {
		lblPris.setText(String.format("TOTAL: %.2f kr.", getGaveæske().getPris()));
	}
	
	private void btnAnnullerAction() {
		gaveæske = null;
		this.close();
	}
	
	// Error Label;
	private void setErrorText(String text) {
		lblError.setText(text);
	}
	
	private void clearErrorText() {
		lblError.setText("");
	}
	
	public Gaveaeske getGaveæske() {
		return gaveæske;
	}
}
