//package view;
//
//import javafx.scene.control.Button;
//import javafx.scene.control.ComboBox;
//import javafx.scene.control.ListView;
//import javafx.scene.control.TextArea;
//import javafx.scene.control.TextField;
//import javafx.scene.layout.GridPane;
//import model.PrisKategori;
//import model.Produkt;
//import model.ProduktKategori;
//import storage.Storage;
//import controller.Controller;
//import javafx.geometry.Insets;
//
//public class GaveaeskeTab extends GridPane implements ReloadableTab {
//
//	private ListView<Produkt> lvwProdukter;
//	private TextField txfProduktNavn, txfPris, txfAntalØl;
//	private TextArea txaProduktBeskrivelse;
//	private Button btnOpdaterProdukt, btnSletProdukt, btnOpretProdukt;
//
//	private void setUpPane() {
//		this.setPadding(new Insets(20));
//		this.setHgap(20);
//		this.setVgap(10);
//		this.setGridLinesVisible(false);
//	}
//
//	public GaveaeskeTab() {
//		setUpPane();
//
//		// Column 0
//		ViewHelper.label(this, 0, 1, "Gaveæsker");
//		lvwProdukter = new ListView<Produkt>();
//		this.add(lvwProdukter, 0, 2, 1, 11);
//
//		// Column 1
//		ViewHelper.label(this, 1, 1, "Produktnavn:");
//		txfProduktNavn = new TextField("PRODUKT NAVN");
//		txfProduktNavn.setPrefWidth(200);
//		this.add(txfProduktNavn, 1, 2);
//
//		ViewHelper.label(this, 1, 3, "Produktbeskrivelse:");
//		txaProduktBeskrivelse = new TextArea("BESKRIVELSE");
//		txaProduktBeskrivelse.setPrefWidth(200);
//		this.add(txaProduktBeskrivelse, 1, 4, 1, 3);
//
//		ViewHelper.label(this, 1, 8, "Pris:");
//		txfPris = new TextField("PRIS");
//		txfPris.setPrefWidth(200);
//		this.add(txfPris, 1, 9);
//
//		ViewHelper.label(this, 1, 10, "Antal Øl");
//		txfAntalØl = new TextField("ANTAL ØL");
//		txfAntalØl.setPrefWidth(200);
//		this.add(txfAntalØl, 1, 11);
//
//		// Column 2
//		btnSletProdukt = new Button("Slet");
//		btnSletProdukt.setOnAction(e -> btnSletProduktAction());
//		btnSletProdukt.setPrefWidth(250);
//		this.add(btnSletProdukt, 2, 5);
//
//		btnOpretProdukt = new Button("Opret");
//		btnOpretProdukt.setOnAction(e -> btnOpretProduktAction());
//		btnOpretProdukt.setPrefWidth(250);
//		this.add(btnOpretProdukt, 2, 3);
//
//		btnOpdaterProdukt = new Button("Opdater");
//		btnOpdaterProdukt.setOnAction(e -> btnOpdaterProduktAction());
//		btnOpdaterProdukt.setPrefWidth(250);
//		this.add(btnOpdaterProdukt, 2, 4);
//
//	}
//
//	private void updatelvwProdukter() {
//		lvwProdukter.getItems().removeAll(lvwProdukter.getItems());
//	}
//
//	private void btnOpretProduktAction() {
//		Controller.createGaveæske(this.txfProduktNavn.getText(), this.txaProduktBeskrivelse.getText(),
//				Integer.parseInt(txfAntalØl.getText()), Integer.parseInt(txfPris.getText()));
//	}
//	
//	private void btnOpdaterProduktAction() {
//		if (!ViewHelper.listViewHasSelected(lvwProdukter)) {return;}
//		
//	}
//
//	private void btnSletProduktAction() {
//		if (!ViewHelper.listViewHasSelected(lvwProdukter)) {
//			return;
//		}
//
//		Storage.removeProdukt(lvwProdukter.getSelectionModel().getSelectedItem());
//	}
//
//	@Override
//	public void reload() {
//		updatelvwProdukter();
//
//	}
//
//}