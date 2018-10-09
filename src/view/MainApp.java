package view;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.sun.prism.paint.Color;

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
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
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
		stage.setTitle("Fredags Bar");
		BorderPane pane = new BorderPane();
		
		Scene scene = new Scene(pane);
		stage.setScene(scene);
		stage.setHeight(500);
		stage.setWidth(800);
		stage.show();
	
	}
	
	private void initTabPane(TabPane tabPane) {
		tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);

		Tab tabProdukt = new Tab("Produkter");
		Tab tabKunde = new Tab("Kunder");
		Tab tabSalg = new Tab("Salg");
		Tab tabStatestik = new Tab("Statestik");
		
		tabProdukt.setContent(new ProduktTab());
		tabKunde.setContent(new KundeTab());
		tabSalg.setContent(new SalgTab());
		tabStatestik.setContent(new StatestikTab());
		
		tabPane.getTabs().add(tabProdukt);
		tabPane.getTabs().add(tabKunde);
		tabPane.getTabs().add(tabSalg);
		tabPane.getTabs().add(tabStatestik);
		
	}
}
