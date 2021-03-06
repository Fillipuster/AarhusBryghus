	package view;

import javafx.application.*;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.stage.Stage;
import storage.Storage;

public class MainApp extends Application {
	
	public static void main(String[] args) {
		Storage.initializeStorage();
		Application.launch(args);
	}
	
	private static Stage mainStage;

	@Override
	public void start(Stage stage) throws Exception {
		mainStage = stage;
		
		stage.setTitle("Aarhus Bryghus Salgssystem");
		TabPane pane = new TabPane();

		initTabPane(pane);
		
		Scene scene = new Scene(pane);
		scene.getStylesheets().add("style.css");
		stage.setScene(scene);
		stage.setHeight(720);
		stage.setWidth(1080);
		stage.show();
	}
	
	public static Stage getMainStage() { return mainStage; }

	private void initTabPane(TabPane tabPane) {
		tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		
		tabPane.getSelectionModel().selectedItemProperty().addListener((o, oldValue, newValue) -> tabChangeListener(oldValue, newValue));
		
		Tab tabSalg = new Tab("Salg");
		tabSalg.setContent(new SalgTab());
		tabPane.getTabs().add(tabSalg);

		Tab tabProdukt = new Tab("Produkter");
		tabProdukt.setContent(new ProduktTab());
		tabPane.getTabs().add(tabProdukt);
		
		Tab tabProduktKategori = new Tab("Produkt Kategorier");
		tabProduktKategori.setContent(new ProduktKategoriTab());
		tabPane.getTabs().add(tabProduktKategori);
		
		Tab tabPrisKategori = new Tab("Pris Kategorier");
		tabPrisKategori.setContent(new PrisKategoriTab());
		tabPane.getTabs().add(tabPrisKategori);
		
		Tab tabGaveæskePresets = new Tab("Sammenpakninger");
		tabGaveæskePresets.setContent(new GaveaeskePresetTab());
		tabPane.getTabs().add(tabGaveæskePresets);
		
		Tab tabUdlejning = new Tab("Udlejning");
		tabUdlejning.setContent(new UdlejningTab());
		tabPane.getTabs().add(tabUdlejning);

		Tab tabUdlejninger = new Tab("Udlejninger");
		tabUdlejninger.setContent(new UdlejningerTab());
		tabPane.getTabs().add(tabUdlejninger);
		
		Tab tabKunder = new Tab("Kunder");
		tabKunder.setContent(new KundeTab());
		tabPane.getTabs().add(tabKunder);
		
		Tab tabStatestik = new Tab("Statistik");
		tabStatestik.setContent(new StatestikTab());
		tabPane.getTabs().add(tabStatestik);
	}
	
	private void tabChangeListener(Tab oldValue, Tab newValue) {
		((ReloadableTab) newValue.getContent()).reload();
	}
	
}
