package view;

import controller.Controller;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import model.Kunde;
import storage.Storage;

public class KundeTab extends GridPane implements ReloadableTab {
	
	private ListView<Kunde> lvwKunder;
	private TextField txfNavn, txfTlf;
	private TextArea txaAddresse;
	private Button btnOpdater, btnOpret, btnSlet;
	
	@Override
	public void reload() {
		updateLvwKunder();
	}

	private void setUpPane() {
		this.setPadding(new Insets(20));
		this.setHgap(20);
		this.setVgap(10);
		this.setGridLinesVisible(false);		
	}
	
	public KundeTab() {
		setUpPane();
		
		// Column 0
		ViewHelper.label(this, 0, 0, "Kunder:");
		lvwKunder = new ListView<Kunde>();
		lvwKunder.setOnMouseClicked(e -> lvwKunderAction());
		this.add(lvwKunder, 0, 1, 1, 10);
		
		// Column 1
		ViewHelper.label(this, 1, 0, "Navn:");
		txfNavn = new TextField("NAVN");
		this.add(txfNavn, 1, 1);
		
		ViewHelper.label(this, 1, 2, "Addresse");
		txaAddresse = new TextArea("ADDRESSE");
		txaAddresse.setPrefWidth(250d);
		this.add(txaAddresse, 1, 3, 1, 2);
		
		ViewHelper.label(this, 1, 5, "Telefon Nr:");
		txfTlf = new TextField("00000000");
//		ViewHelper.textFieldRestrictInt(txfTlf);
		this.add(txfTlf, 1, 6);

		// Column 2
		btnOpdater = new Button("Opdater");
		btnOpdater.setOnAction(e -> btnOpdaterAction());
		btnOpdater.setPrefWidth(200d);
		this.add(btnOpdater, 2, 1);
		
		btnOpret = new Button("Opret");
		btnOpret.setOnAction(e -> btnOpretAction());
		btnOpret.setPrefWidth(200d);
		this.add(btnOpret, 2, 2);
		
		btnSlet = new Button("Slet");
		btnSlet.setOnAction(e -> btnSletAction());
		btnSlet.setPrefWidth(200d);
		this.add(btnSlet, 2, 3);
	}
	
	// Node updater methods;
	private void updateLvwKunder() {
		lvwKunder.getItems().removeAll(lvwKunder.getItems());
		lvwKunder.getItems().addAll(Storage.getKunder());
	}
	
	// Node action methods;
	private void lvwKunderAction() {
		Kunde selected = lvwKunder.getSelectionModel().getSelectedItem();
		if (selected != null) {
			txfNavn.setText(selected.getNavn());
			txaAddresse.setText(selected.getAddresse());
			txfTlf.setText(selected.getTelefonNr());
		}
	}

	private void btnSletAction() {
		Kunde selected = lvwKunder.getSelectionModel().getSelectedItem();
		if (selected != null) {
			Storage.removeKunde(selected);
			updateLvwKunder();
		}
	}

	private void btnOpretAction() {
		Controller.createKunde(txfNavn.getText(), txaAddresse.getText(), txfTlf.getText());
		updateLvwKunder();
	}

	private void btnOpdaterAction() {
		Kunde selected = lvwKunder.getSelectionModel().getSelectedItem();
		if (selected != null) {
			Controller.updateKunde(selected, txfNavn.getText(), txaAddresse.getText(), txfTlf.getText());
			updateLvwKunder();
		}
	}
	
}
