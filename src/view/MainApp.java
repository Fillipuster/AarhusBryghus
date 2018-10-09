package view;

import javafx.application.*;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import storage.Storage;

public class MainApp extends Application {

	public static void main(String[] args) {
		Storage.createTestData();
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("Fredags Bar");
		TabPane pane = new TabPane();

		initTabPane(pane);
		
		Scene scene = new Scene(pane);
		stage.setScene(scene);
		stage.setHeight(600);
		stage.setWidth(1000);
		stage.show();

	}

	private void initTabPane(TabPane tabPane) {
		tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);

		Tab tabProdukt = new Tab("Produkter");
		Tab tabKunde = new Tab("Kunder");
		Tab tabSalg = new Tab("Salg");
		Tab tabStatestik = new Tab("Statestik");

		tabProdukt.setContent(new ProduktTab());
//		tabKunde.setContent(new KundeTab());
//		tabSalg.setContent(new SalgTab());
//		tabStatestik.setContent(new StatestikTab());

		tabPane.getTabs().add(tabProdukt);
		tabPane.getTabs().add(tabKunde);
		tabPane.getTabs().add(tabSalg);
		tabPane.getTabs().add(tabStatestik);

	}

	public static Label label(GridPane pane, int x, int y, String text) {
		Label label = new Label(text);
		pane.add(label, x, y);

		return label;
	}
}
