package view;

import controller.Controller;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import model.Kunde;
import model.ProduktLinje;
import model.UdlejningsSalg;
import storage.Storage;

public class UdlejningerTab extends GridPane implements ReloadableTab {

	private ListView<Kunde> lvwKunder;
	private ListView<UdlejningsSalg> lvwUdlejningsSalg;
	private ListView<ProduktLinje> lvwProduktLinje;
	private TextField txfAntalUbrugt;
	private Button btnSætUbrugt;
	
	@Override
	public void reload() {
		updateLvwKunder();
	}
	
	private void setUpPane() {
		this.setPadding(new Insets(20));
		this.setHgap(20);
		this.setVgap(10);
	}
	
	public UdlejningerTab() {
		setUpPane();
		
		// Column 0
		ViewHelper.label(this, 0, 0, "Kunder:");
		lvwKunder = new ListView<Kunde>();
		lvwKunder.setOnMouseClicked(e -> lvwKunderAction());
		this.add(lvwKunder, 0, 1, 1, 10);
		
		// Column 1
		ViewHelper.label(this, 1, 0, "Kundens udlejninger:");
		lvwUdlejningsSalg = new ListView<UdlejningsSalg>();
		lvwUdlejningsSalg.setOnMouseClicked(e -> lvwUdlejningsSalgAction());
		this.add(lvwUdlejningsSalg, 1, 1, 1, 10);
		
		// Column 2
		ViewHelper.label(this, 2, 0, "Produkter i udlejning:");
		lvwProduktLinje = new ListView<ProduktLinje>();
		lvwProduktLinje.setOnMouseClicked(e -> lvwProduktLinjeAction());
		this.add(lvwProduktLinje, 2, 1, 1, 10);
		
		// Column 3
		ViewHelper.label(this, 3, 0, "Antal ubrugt produkter:");
		txfAntalUbrugt = new TextField("0");
		txfAntalUbrugt.setOnAction(e -> txfAntalUbrugtAction());
		ViewHelper.textFieldRestrictInt(txfAntalUbrugt);
		this.add(txfAntalUbrugt, 3, 1);
		
		btnSætUbrugt = new Button("Sæt Ubrugt");
		btnSætUbrugt.setOnAction(e -> txfAntalUbrugtAction());
		this.add(btnSætUbrugt, 3, 2);
	}
	
	// Node updater methods;
	private void updateLvwKunder() {
		lvwKunder.getItems().setAll(Storage.getKunder());
	}
	
	private void updateLvwUdlejningsSalg() {
		Kunde selected = lvwKunder.getSelectionModel().getSelectedItem();
		if (selected != null) {
			lvwUdlejningsSalg.getItems().setAll(Controller.getKundeUdlejningsSalg(selected));	
		}
	}
	
	private void updateLvwProduktLinjer() {
		UdlejningsSalg selected = lvwUdlejningsSalg.getSelectionModel().getSelectedItem();
		if (selected != null) {
			lvwProduktLinje.getItems().setAll(selected.getProduktLinjer());
		}
	}
	
	// Node action methods;
	private void lvwKunderAction() {
		updateLvwUdlejningsSalg();
	}
	
	private void lvwUdlejningsSalgAction() {
		updateLvwProduktLinjer();
	}
	
	private void lvwProduktLinjeAction() {
		ProduktLinje selected = lvwProduktLinje.getSelectionModel().getSelectedItem();
		if (selected != null) {
			txfAntalUbrugt.setText(Integer.toString(selected.getAntalUbrugt()));
		}
	}
	
	private void txfAntalUbrugtAction() {
		ProduktLinje selected = lvwProduktLinje.getSelectionModel().getSelectedItem();
		if (selected != null) {
			try {
				Controller.setProduktLinjeAntalUbrugt(selected, Integer.parseInt(txfAntalUbrugt.getText()));				
			} catch (NumberFormatException e) {
				return;
			}
		}
	}
	
}