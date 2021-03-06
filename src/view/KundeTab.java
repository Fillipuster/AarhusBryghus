package view;

import controller.Controller;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import model.Kunde;
import model.DataFindesAlleredeException;
import storage.Storage;

public class KundeTab extends GridPane implements ReloadableTab {
	
	private ListView<Kunde> lvwKunder;
	private TextField txfNavn, txfTlf;
	private TextArea txaAdresse;
	private Button btnOpdater, btnOpret, btnSlet;
	private Label lblError;
	
	@Override
	public void reload() {
		updateLvwKunder();
	}

	private void setUpPane() {
		this.setPadding(new Insets(20));
		this.setHgap(20);
		this.setVgap(10);
		this.setAlignment(Pos.CENTER);
		
		this.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				clearErrorText();
			}
		});
		
	}
	
	public KundeTab() {
		setUpPane();
		
		// Column 0
		ViewHelper.label(this, 0, 0, "Kunder:");
		lvwKunder = new ListView<Kunde>();
		lvwKunder.setOnMouseClicked(e -> lvwKunderAction());
		this.add(lvwKunder, 0, 1, 1, 10);
		
		lblError = new Label();
		lblError.setTextFill(Color.RED);
		this.add(lblError, 0, 12);
		
		// Column 1
		ViewHelper.label(this, 1, 0, "Navn:");
		txfNavn = new TextField();
		txfNavn.setPromptText("Kunde Navn");
		this.add(txfNavn, 1, 1);
		
		ViewHelper.label(this, 1, 2, "Adresse");
		txaAdresse = new TextArea();
		txaAdresse.setPromptText("Kunde Adresse");
		txaAdresse.setPrefWidth(250d);
		txaAdresse.setPrefHeight(75d);
		this.add(txaAdresse, 1, 3, 1, 2);
		
		ViewHelper.label(this, 1, 5, "Telefon Nr:");
		txfTlf = new TextField();
		txfTlf.setPromptText("Kunde Telefon Nr.");
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
			txaAdresse.setText(selected.getAddresse());
			txfTlf.setText(selected.getTelefonNr());
		}
	}

	private void btnSletAction() {
		Kunde selected = lvwKunder.getSelectionModel().getSelectedItem();
		if (selected != null) {
			Storage.removeKunde(selected);
			updateLvwKunder();
		} else {
			setErrorText("Kunde skal vælges.");
		}
	}

	private void btnOpretAction() {
		if (txfNavn.getText().isEmpty() || txaAdresse.getText().isEmpty() || txfTlf.getText().isEmpty()) {
			setErrorText("Alle felter skal udfyldes.");
			return;
		}
		try {
			Controller.createKunde(txfNavn.getText(), txaAdresse.getText(), txfTlf.getText());
		} catch (DataFindesAlleredeException e) {
			setErrorText("Kunde findes allerede");
		}
		updateLvwKunder();
	}

	private void btnOpdaterAction() {
		Kunde selected = lvwKunder.getSelectionModel().getSelectedItem();
		if (selected != null) {
			Controller.updateKunde(selected, txfNavn.getText(), txaAdresse.getText(), txfTlf.getText());
			updateLvwKunder();
		} else {
			setErrorText("Kunde skal vælges.");
		}
	}
	
	// Error Label;
	private void setErrorText(String text) {
		lblError.setText(text);
	}
	
	private void clearErrorText() {
		lblError.setText("");
	}
}
