package view;

import java.util.ArrayList;

import com.sun.org.apache.bcel.internal.generic.Select;

import controller.Controller;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Gaveaeske;
import model.Produkt;
import storage.Storage;

public class GaveaeskeWindow extends Stage {

	private Gaveaeske gaveæske;
	
	private ListView<Produkt> lvwProdukter, lvwTilføjedeProdukter;
	private ComboBox<Produkt> cboxProdukter;
	private Button btnTilføj, btnFjern, btnAccepter, btnAnnuller;

	public GaveaeskeWindow(String title, Stage owner) {
		this.initOwner(owner);
		this.initStyle(StageStyle.UTILITY);
		this.initModality(Modality.APPLICATION_MODAL);
		this.setMinHeight(100);
		this.setMinWidth(200);
		this.setResizable(false);

		this.setTitle(title);
		GridPane pane = new GridPane();
		this.initContent(pane);

		Scene scene = new Scene(pane);
		this.setScene(scene);
	}

	private void setUpPane(GridPane pane) {
		pane.setPadding(new Insets(20));
		pane.setHgap(20);
		pane.setVgap(10);
	}

	public void initContent(GridPane pane) {
		setUpPane(pane);

		// Column 0
		lvwProdukter = new ListView<>();
		lvwProdukter.setStyle("-fx-font-family: monospace;");
		pane.add(lvwProdukter, 0, 1, 1, 5);

		// Column 1
		btnTilføj = new Button("→");
		btnTilføj.setOnAction(e -> btnTilføjAction());
		pane.add(btnTilføj, 1, 2);

		btnFjern = new Button("←");
		btnFjern.setOnAction(e -> btnFjernAction());
		pane.add(btnFjern, 1, 4);

		// Column 2
		lvwTilføjedeProdukter = new ListView<>();
		pane.add(lvwTilføjedeProdukter, 2, 1, 1, 5);

		Label lblPris = ViewHelper.label(pane, 2, 6, "PRIS: 0.0 kr.");
		
		// Column 3
		btnAccepter = new Button("Accepter");
		btnAccepter.setOnAction(e -> btnAccepterAction());
		pane.add(btnAccepter, 3, 2);

		btnAnnuller = new Button("Annuller");
		btnAnnuller.setOnAction(e -> btnAnnullerAction());
		pane.add(btnAnnuller, 3, 3);
		
		updateLvwProdukter();
	}

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
	
	private void btnTilføjAction() {
		if (ViewHelper.listViewHasSelected(lvwProdukter)) {
			Produkt selected = lvwProdukter.getSelectionModel().getSelectedItem();
			lvwTilføjedeProdukter.getItems().add(selected);
		}
	}

	private void btnFjernAction() {
		if (ViewHelper.listViewHasSelected(lvwTilføjedeProdukter)) {
			Produkt selected = lvwTilføjedeProdukter.getSelectionModel().getSelectedItem();
			lvwTilføjedeProdukter.getItems().remove(selected);
		}
	}
	
	private void btnAccepterAction() {
		gaveæske = Controller.createGaveæske(new ArrayList<Produkt>(lvwTilføjedeProdukter.getItems()));
		this.close();
	}
	
	private void btnAnnullerAction() {
		this.close();
	}
	
	public Gaveaeske getGaveæske() {
		return gaveæske;
	}
}
