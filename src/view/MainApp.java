package view;

import javafx.application.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.stage.Stage;
import storage.Storage;

public class MainApp extends Application {
	
	public static void main(String[] args) {
		Storage.createTestData();
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("Aarhus Bryghus Salgssystem");
		TabPane pane = new TabPane();

		initTabPane(pane);
		
		Scene scene = new Scene(pane);
		stage.setScene(scene);
		stage.setHeight(600);
		stage.setWidth(1000);
		stage.show();

		pane.setTabMinWidth((stage.getWidth() / pane.getTabs().size()) - 20d);
	}

	private void initTabPane(TabPane tabPane) {
		tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		
		tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
			@Override
			public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
				((ReloadableTab) newValue.getContent()).reload();
			}
		});
		
		Tab tabProdukt = new Tab("Produkter");
		tabProdukt.setContent(new ProduktTab());
		
		Tab tabProduktKategori = new Tab("Produkt Kategorier");
		tabProduktKategori.setContent(new ProduktKategoriTab());
		
		Tab tabSalg = new Tab("Salg");
		tabSalg.setContent(new SalgTab());
		
		Tab tabPrisKategori = new Tab("Pris Kategorier");
		tabPrisKategori.setContent(new PrisKategoriTab());

		tabPane.getTabs().add(tabSalg);
		tabPane.getTabs().add(tabProdukt);
		tabPane.getTabs().add(tabProduktKategori);
		tabPane.getTabs().add(tabPrisKategori);
	}
	
}
