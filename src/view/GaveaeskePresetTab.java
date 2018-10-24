package view;

import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import model.GaveaeskePakning;
import model.GaveaeskePreset;
import storage.Storage;
import controller.Controller;
import javafx.geometry.Insets;

public class GaveaeskePresetTab extends GridPane implements ReloadableTab {

	private ListView<GaveaeskePreset> lvwGaveaeskePresets;
	
	private TextField txfØl, txfGlas, txfPris;
	private Button btnOpdater, btnSlet, btnOpret;
	private ToggleGroup tggPakning = new ToggleGroup();
	private RadioButton rbPakningGaveæske, rbPakningTrækasse, rbPakningGavekurv, rbPakningPapkasse;

	public void reload() {
		updateLvwGaveaeskePresets();
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
		
		ViewHelper.label(this, 1, 6, "Pakning:");
		rbPakningGaveæske = new RadioButton("Gaveæske");
		rbPakningGaveæske.setUserData(GaveaeskePakning.GAVEÆSKE);
		rbPakningGaveæske.setToggleGroup(tggPakning);
		this.add(rbPakningGaveæske, 1, 7);
		
		rbPakningTrækasse = new RadioButton("Trækasse");
		rbPakningTrækasse.setUserData(GaveaeskePakning.TRÆKASSE);
		rbPakningTrækasse.setToggleGroup(tggPakning);
		this.add(rbPakningTrækasse, 1, 8);
		
		rbPakningGavekurv = new RadioButton("Gavekurv");
		rbPakningGavekurv.setUserData(GaveaeskePakning.GAVEKURV);
		rbPakningGavekurv.setToggleGroup(tggPakning);
		this.add(rbPakningGavekurv, 1, 9);
		
		rbPakningPapkasse = new RadioButton("Papkasse");
		rbPakningPapkasse.setUserData(GaveaeskePakning.PAPKASSE);
		rbPakningPapkasse.setToggleGroup(tggPakning);
		this.add(rbPakningPapkasse, 1, 10);
		
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
		
		tggPakning.selectToggle(rbPakningGaveæske);
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
			
			for (Toggle t : tggPakning.getToggles()) {
				if (t.getUserData().equals(selected.getPakning())) {
					tggPakning.selectToggle(t);
				}
			}
		}
	}
	
	// Node action methods;
	private void lvwGaveaeskePresetsAction() {
		updateTextFields();
	}
	
	private void btnOpdaterAction() {
		GaveaeskePreset selected = lvwGaveaeskePresets.getSelectionModel().getSelectedItem();
		if (selected != null) {
			try {
				Controller.updateGaveaeskePreset(selected, Integer.parseInt(txfØl.getText()),
						Integer.parseInt(txfGlas.getText()), Double.parseDouble(txfPris.getText()),
						(GaveaeskePakning) tggPakning.getSelectedToggle().getUserData());
			} catch (NumberFormatException e) {
				// TODO: Set error text.
			}
			updateLvwGaveaeskePresets();
		}
	}
	
	private void btnOpretAction() {
		try {
			Controller.createGaveaeskePreset(Integer.parseInt(txfØl.getText()), Integer.parseInt(txfGlas.getText()),
					Double.parseDouble(txfPris.getText()), (GaveaeskePakning) tggPakning.getSelectedToggle().getUserData());			
		} catch (NumberFormatException e) {
			// TODO: Set error text.
		}
		updateLvwGaveaeskePresets();
	}
	
	private void btnSletAction() {
		GaveaeskePreset selected = lvwGaveaeskePresets.getSelectionModel().getSelectedItem();
		if (selected != null) {
			Storage.removeGaveaeskePreset(selected);
		}
		updateLvwGaveaeskePresets();
	}
	
}
