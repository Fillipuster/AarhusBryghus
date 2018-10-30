package view;

import controller.Controller;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import model.BetalingsMetode;
import model.PrisKategori;
import model.ProduktLinje;
import model.Salg;

public class StatestikTab extends GridPane implements ReloadableTab {
	private Salg salg;
	private ListView<ProduktLinje> lvwProduktLinjer;
	private Button btnAdd, btnDelete, btnAnuller, btnKøb, btnOpretGaveæske;
	private ComboBox<PrisKategori> cboxPrisKategorier;
	private TextField txfAntal, txfRabat;
	private Label lblTotal, lblError;
	private ComboBox<BetalingsMetode> cboxBetalingsMetoder;
	private DatePicker dpStart, dpSlut;

	@Override
	public void reload() {
		// updateCboxPrisKategrorier();
		// updateCboxBetalingsMetoder();
		setErrorText("");
	}

	private void setUpPane() {
		this.setPadding(new Insets(20));
		this.setHgap(20);
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
		salg = Controller.createSalg();
		
		setUpPane();
		this.setGridLinesVisible(false);

		// Column 0
		dpStart = new DatePicker();
		this.add(dpStart, 0, 0);
		
}

	private void setErrorText(String text) {
		lblError.setText(text);
	}
	
	private void clearErrorText() {
		lblError.setText("");
	}

}