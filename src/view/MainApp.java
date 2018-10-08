package view;

import javafx.application.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class MainApp extends Application {

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		TabPane pane = new TabPane();
		
		pane.setPrefSize(500, 500);
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
	
	private GridPane buildProduktPane(GridPane pane) {
		Button btn = new Button("Opret Produkt");
		pane.add(btn, 0, 0);
		
		return pane;
	}

}
