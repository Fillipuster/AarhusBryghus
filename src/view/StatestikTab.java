package view;

import java.time.LocalDate;

import controller.StatisticsController;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
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
	private Label lblKlipBrugt, lblKlipSolgt, lblError, lblSalg, lblProduktLinje;
	private DatePicker dpStart, dpSlut;

	@Override
	public void reload() {
		// updateCboxPrisKategrorier();
		// updateCboxBetalingsMetoder();
		setErrorText("");
	}

	private void setUpPane() {
		this.setPadding(new Insets(40));
		this.setHgap(40);
		this.setVgap(10);

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
		this.setGridLinesVisible(false);

		// Column 0
		dpStart = new DatePicker();
		this.add(dpStart, 0, 0);

		dpSlut = new DatePicker();
		this.add(dpSlut, 0, 1);

		btnUdregnStatestik = new Button("Hent Statestik");
		btnUdregnStatestik.setOnAction(e -> btnUdregnStatestikAction());
		this.add(btnUdregnStatestik, 0, 2);

		lblKlipBrugt = ViewHelper.label(this, 0, 6, "Klip Brugt:");
		lblKlipBrugt.setStyle("-fx-font-size: 16;\n-fx-font-family: monospace;");
		//
		lblKlipSolgt = ViewHelper.label(this, 0, 7, "Klip Solgt:");
		lblKlipSolgt.setStyle("-fx-font-size: 16;\n-fx-font-family: monospace;");

		lblError = new Label("");
		lblError.setTextFill(Color.RED);
		this.add(lblError, 0, 9);

		// Column 1
		lblSalg = ViewHelper.label(this, 1, 0, "Salg i valgte periode:");

		lvwSalg = new ListView<>();
		lvwSalg.setOnMouseClicked(e -> lvwSalgAction());
		this.add(lvwSalg, 1, 1, 1, 10);

		// Column 2
		lblProduktLinje = ViewHelper.label(this, 3, 0, "Produktlinjer:");

		lvwProduktLinjer = new ListView<>();
		this.add(lvwProduktLinjer, 3, 1, 1, 10);

	}

	private void updateLvwSalg() {
		int klipBrugt = StatisticsController.getKlipBrugtIPeriode();
		int klipSolgt = StatisticsController.getKlipSolgtIPeriode();
		
		lblKlipBrugt.setText(String.format("Klip Brugt: %d", klipBrugt));
		lblKlipSolgt.setText(String.format("Klip Solgt: %d", klipSolgt));
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