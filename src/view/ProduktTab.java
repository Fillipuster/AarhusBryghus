package view;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import model.Produkt;
import model.ProduktKategori;
import storage.Storage;
import javafx.scene.control.Label;
import javafx.event.EventHandler;
import javafx.geometry.Insets;



public class ProduktTab {
	private TextField txfProduktNavn, txfProduktBeskrivelse;
	private Button btnOpdater, btnSlet, btnGem;
	private Label lblError;
	
	public produktTab() {
		this.
	}
	
	this.setPadding(new Insets(20));
	this.setHgap(20);
	this.setVgap(10);
	this.setGridLinesVisible(false);

	
	ListView<Produkt> lvwProdukter = new ListView<Produkt>();
	ComboBox<ProduktKategori> cboxProduktKategorier = new ComboBox<>();
	
	private GridPane buildProduktPane(GridPane pane) {
		label(pane, 0, 0, "Produkter");		
		
		lvwProdukter.getItems().addAll(Storage.getProdukter());
		
		lvwProdukter.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				Produkt selected = lvwProdukter.getSelectionModel().getSelectedItem();
				if (selected != null) {
					cboxProduktKategorier.getSelectionModel().select(selected.getProduktKategori());
					txfProduktNavn.setText(selected.getNavn());
					txfProduktBeskrivelse.setText(selected.getBeskrivelse());
				}
			}
		});
	
	
		public static Label label(GridPane pane, int x, int y, String text) {
			Label label = new Label(text);
			pane.add(label, x, y);
			
			return label;
		}
	
	
	
	
	
	public void actionOpdater() {
		lblError.setText("");
		lblError.setTextFill(Paint.);
		if(lvwProdukter.getSelectionModel().getSelectedItems() == null) {
			
		}
	}
}
