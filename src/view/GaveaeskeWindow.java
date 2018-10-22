package view;

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
import model.Produkt;
import storage.Storage;

public class GaveaeskeWindow extends Stage {

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
		pane.add(btnAccepter, 3, 2);

		btnAnnuller = new Button("Annuller");
		pane.add(btnAnnuller, 3, 3);
		
		
	}

	private void updateLvwProdukter() {
		lvwProdukter.getItems().removeAll(lvwProdukter.getItems());
		lvwProdukter.getItems().addAll(Controller.getProdukterIPrisKategori(Storage.getButikPrisKategori()));
	}
	
	private void btnTilføjAction() {
		
	}

	private void btnFjernAction() {
		
	}
}
