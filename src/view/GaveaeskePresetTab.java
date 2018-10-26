package view;

import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import model.GaveaeskeEmballage;
import model.GaveaeskePreset;
import storage.Storage;
import controller.Controller;
import javafx.geometry.Insets;

public class GaveaeskePresetTab extends GridPane implements ReloadableTab {

	private ListView<GaveaeskePreset> lvwGaveaeskePresets;
	private ListView<GaveaeskeEmballage> lvwEmballage;
	private TextField txfØl, txfGlas, txfPris, txfEmballageNavn;
	private Button btnOpdater, btnSlet, btnOpret, btnOpretEmballage, btnSletEmballage;

	public void reload() {
		updateLvwGaveaeskePresets();
		updateLvwEmballage();
	}
	
	private void setUpPane() {
		this.setPadding(new Insets(20));
		this.setHgap(20);
		this.setVgap(10);
		this.setGridLinesVisible(false);
	}

	public GaveaeskePresetTab() {
		setUpPane();

		// Column 0
		ViewHelper.label(this, 0, 0, "Sammenpakninger:");
		
		lvwGaveaeskePresets = new ListView<GaveaeskePreset>();
		lvwGaveaeskePresets.setOnMouseClicked(e -> lvwGaveaeskePresetsAction());
		this.add(lvwGaveaeskePresets, 0, 1, 1, 11);
		
		// Column 1
		ViewHelper.label(this, 1, 0, "Ølflasker i æske:");
		txfØl = new TextField("4");
		ViewHelper.textFieldRestrictInt(txfØl);
		this.add(txfØl, 1, 1);
		
		ViewHelper.label(this, 1, 2, "Glas i æske:");
		txfGlas = new TextField("2");
		ViewHelper.textFieldRestrictInt(txfGlas);
		this.add(txfGlas, 1, 3);

		ViewHelper.label(this, 1, 4, "Pris på æske:");
		txfPris = new TextField("299.95");
		ViewHelper.textFieldRestrictDouble(txfPris);
		this.add(txfPris, 1, 5);
		
		ViewHelper.label(this, 1, 6, "Emballage:");
		lvwEmballage = new ListView<GaveaeskeEmballage>();
		this.add(lvwEmballage, 1, 7);
		
		// Column 2
		btnOpdater = new Button("Opdater");
		btnOpdater.setOnAction(e -> btnOpdaterAction());
		this.add(btnOpdater, 2, 1);
		
		btnOpret = new Button("Opret");
		btnOpret.setOnAction(e -> btnOpretAction());
		this.add(btnOpret, 2, 2);
		
		btnSlet = new Button("Slet");
		btnSlet.setOnAction(e -> btnSletAction());
		this.add(btnSlet, 2, 3);
		
		txfEmballageNavn = new TextField("EMBALLAGE NAVN");
		this.add(txfEmballageNavn, 2, 4, 2, 1);
		
		btnOpretEmballage = new Button("Opret Emballage");
		btnOpretEmballage.setOnAction(e -> btnOpretEmballageAction());
		this.add(btnOpretEmballage, 2, 5);
		
		btnSletEmballage = new Button("Slet Emballage");
		btnSletEmballage.setOnAction(e -> btnSletEmballageAction());
		this.add(btnSletEmballage, 3, 5);
	}

	// Node updater methods;
	private void updateLvwGaveaeskePresets() {
		lvwGaveaeskePresets.getItems().removeAll(lvwGaveaeskePresets.getItems());
		lvwGaveaeskePresets.getItems().addAll(Storage.getGaveaeskePresets());
	}
	
	private void updateTextFields() {
		GaveaeskePreset selected = lvwGaveaeskePresets.getSelectionModel().getSelectedItem();
		if (selected != null) {
			txfØl.setText(Integer.toString(selected.getØl()));
			txfGlas.setText(Integer.toString(selected.getGlas()));
			txfPris.setText(Double.toString(selected.getPris()));
			
			lvwEmballage.getSelectionModel().select(selected.getEmballage());
		}
	}
	
	private void updateLvwEmballage() {
		lvwEmballage.getItems().setAll(Storage.getGaveaeskeEmballager());
	}
	
	// Node action methods;
	private void lvwGaveaeskePresetsAction() {
		updateTextFields();
	}
	
	private void btnOpdaterAction() {
		if (!ViewHelper.listViewHasSelected(lvwEmballage)) { return; }
		
		GaveaeskePreset selected = lvwGaveaeskePresets.getSelectionModel().getSelectedItem();
		if (selected != null) {
			try {
				Controller.updateGaveaeskePreset(selected, Integer.parseInt(txfØl.getText()),
						Integer.parseInt(txfGlas.getText()), Double.parseDouble(txfPris.getText()), lvwEmballage.getSelectionModel().getSelectedItem());
			} catch (NumberFormatException e) {
				// TODO: Set error text.
			}
			updateLvwGaveaeskePresets();
		}
	}
	
	private void btnOpretAction() {
		GaveaeskeEmballage selected = lvwEmballage.getSelectionModel().getSelectedItem();
		if (selected != null) {
			try {
				Controller.createGaveaeskePreset(Integer.parseInt(txfØl.getText()), Integer.parseInt(txfGlas.getText()),
						Double.parseDouble(txfPris.getText()), selected);			
			} catch (NumberFormatException e) {
				// TODO: Set error text.
			}
			updateLvwGaveaeskePresets();			
		} else {
			// TODO: Set error text.
		}
	}
	
	private void btnSletAction() {
		GaveaeskePreset selected = lvwGaveaeskePresets.getSelectionModel().getSelectedItem();
		if (selected != null) {
			Storage.removeGaveaeskePreset(selected);
		}
		updateLvwGaveaeskePresets();
	}
	
	private void btnOpretEmballageAction() {
		Controller.createGaveaeskeEmballage(txfEmballageNavn.getText());
		updateLvwEmballage();
	}
	
	private void btnSletEmballageAction() {
		GaveaeskeEmballage selected = lvwEmballage.getSelectionModel().getSelectedItem();
		if (selected != null) {
			Storage.removeGaveaeskeEmballage(selected);
		}
	}
	
}
