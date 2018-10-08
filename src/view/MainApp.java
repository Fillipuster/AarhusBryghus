package view;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javafx.application.*;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Produkt;
import model.ProduktKategori;
import storage.Storage;

public class MainApp extends Application {

	public static void main(String[] args) {
		Storage.createTestData();
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		TabPane pane = new TabPane();
		
		pane.setPrefSize(1000, 600);
		buildTabPane(pane);
		
		Scene scene = new Scene(pane);
		stage.setScene(scene);
		stage.show();
	}
	
	private GridPane createGridPane() {
		GridPane pane = new GridPane();
        pane.setPadding(new Insets(20));
        pane.setHgap(10);
        pane.setVgap(10);
        
        return pane;
	}
	
	private void buildTabPane(TabPane pane) {
		Tab produktTab = new Tab("Produkter");
		produktTab.setContent(buildProduktPane(createGridPane()));
		produktTab.setClosable(false);
		pane.getTabs().add(produktTab);
	}
	
	ListView<Produkt> lvwProdukter = new ListView<Produkt>();
	ComboBox<ProduktKategori> cboxProduktKategorier = new ComboBox<>();
	TextField txfProduktNavn = new TextField("Produktnavn");
	TextField txfProduktBeskrivelse = new TextField("Beskrivelse");
	private GridPane buildProduktPane(GridPane pane) {
		label(pane, 0, 0, "Produkter");		
		
		lvwProdukter.getItems().addAll(Storage.getProdukter());
		lvwProdukter.setCellFactory(new Callback<ListView<Produkt>, ListCell<Produkt>>() {
			@Override
			public ListCell<Produkt> call(ListView<Produkt> param) {
				ListCell<Produkt> cell = new ListCell<Produkt>() {
					@Override
					protected void updateItem(Produkt item, boolean empty) {
						super.updateItem(item, empty);
						if (item =! null) {
							setText();
						}
					}
				};
				
				return cell;
			}
		});
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
		pane.add(lvwProdukter, 0, 1, 1, 4);
		
		label(pane, 1, 0, "Produktinformation");
		
		cboxProduktKategorier.getItems().addAll(Storage.getProduktKategorier());
		pane.add(cboxProduktKategorier, 1, 1);
		
		pane.add(txfProduktNavn, 1, 2);
		pane.add(txfProduktBeskrivelse, 1, 3);
		
//		Button btn = new Button("Opret Produkt");
//		pane.add(btn, 0, 0);
		
		return pane;
	}
	
	public static Label label(GridPane pane, int x, int y, String text) {
		Label label = new Label(text);
		pane.add(label, x, y);
		
		return label;
	}

}
