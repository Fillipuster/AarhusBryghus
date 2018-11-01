package view;

import java.time.LocalDate;

import controller.StatisticsController;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import model.ProduktLinje;
import model.Salg;

public class StatestikTab extends GridPane implements ReloadableTab {
	private ListView<Salg> lvwSalg;
	private ListView<ProduktLinje> lvwProduktLinjer;
	private Button btnUdregnStatestik;
	private Label lblKlipBrugt, lblKlipSolgt, lblError;
	private DatePicker dpStart, dpSlut;

	@Override
	public void reload() {
		clearErrorText();
	}

	private void setUpPane() {
		this.setPadding(new Insets(40));
		this.setHgap(40);
		this.setVgap(10);
		this.setAlignment(Pos.CENTER);

		// Clear error label on mouse event;
		this.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				clearErrorText();
			}
		});
	}

	public StatestikTab() {
		setUpPane();

		// Column 0
		Label lblStart = new Label("Periode start:");
		this.add(lblStart, 0, 0, 2, 1);
		dpStart = new DatePicker();
		this.add(dpStart, 0, 1, 2, 1);

		Label lblSlut = new Label("Periode slut:");
		this.add(lblSlut, 0, 2, 2, 1);
		dpSlut = new DatePicker();
		this.add(dpSlut, 0, 3, 2, 1);

		btnUdregnStatestik = new Button("Hent Statistik");
		btnUdregnStatestik.setOnAction(e -> btnUdregnStatestikAction());
		this.add(btnUdregnStatestik, 0, 4, 2, 1);
	
		ViewHelper.label(this, 0, 5, "Klip solgt:");
		lblKlipSolgt = ViewHelper.label(this, 0, 6, "0");
		lblKlipSolgt.setStyle("-fx-font-size: 24;\n-fx-font-family: monospace;");
		
		lblError = new Label("");
		lblError.setTextFill(Color.RED);
		this.add(lblError, 0, 9);
		
		// Column 1
		ViewHelper.label(this, 1, 5, "Klip brugt:");
		lblKlipBrugt = ViewHelper.label(this, 1, 6, "0");
		lblKlipBrugt.setStyle("-fx-font-size: 24;\n-fx-font-family: monospace;");

		// Column 2
		ViewHelper.label(this, 2, 0, "Salg i valgte periode:");
		lvwSalg = new ListView<>();
		lvwSalg.setOnMouseClicked(e -> lvwSalgAction());
		this.add(lvwSalg, 2, 1, 1, 10);

		// Column 3
		ViewHelper.label(this, 3, 0, "Produkter i salg:");
		lvwProduktLinjer = new ListView<>();
		this.add(lvwProduktLinjer, 3, 1, 1, 10);
	}
	
	private void updateLblKlip() {
		lblKlipSolgt.setText(Integer.toString(StatisticsController.getKlipSolgtIPeriode()));
		lblKlipBrugt.setText(Integer.toString(StatisticsController.getKlipBrugtIPeriode()));
	}

	private void updateLvwSalg() {
		updateLblKlip();
		lvwSalg.getItems().setAll(StatisticsController.getSalgIPeriode());
	}

	private void updateLvwProduktLinje() {
		Salg selected = lvwSalg.getSelectionModel().getSelectedItem();
		if (selected != null) {
			lvwProduktLinjer.getItems().setAll(selected.getProduktLinjer());
		}
		
	}
	
	private void btnUdregnStatestikAction() {
		LocalDate start = dpStart.getValue();
		LocalDate slut = dpSlut.getValue();

		if (start != null && slut != null) {
			if (!start.isAfter(slut)) {
				StatisticsController.calcStatistics(start, slut);
				updateLvwSalg();
			} else {
				setErrorText("Slut dato skal være efter start.");
			}
		} else {
			setErrorText("Vælg venligst datoer.");
		}
	}

	private void lvwSalgAction() {
		updateLvwProduktLinje();
	}
	
	private void setErrorText(String text) {
		lblError.setText(text);
	}

	private void clearErrorText() {
		lblError.setText("");
	}

}