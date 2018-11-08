package view;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class ViewHelper {
	
	public static Label label(GridPane pane, int x, int y, String text) {
		Label label = new Label(text);
		pane.add(label, x, y);

		return label;
	}
	
	public static boolean listViewHasSelected(ListView<?> lvw) {
		return (lvw.getSelectionModel().getSelectedItem() != null);
	}
	
	public static boolean comboBoxHasSelected(ComboBox<?> cbox) {
		return (cbox.getSelectionModel().getSelectedItem() != null);
	}
	
	public static void textFieldRestrictInt(TextField txf) {
		txf.textProperty().addListener((o, oldValue, newValue) -> textFieldIntListener(txf, oldValue, newValue));
	}
	
	public static void textFieldRestrictDouble(TextField txf) {
		txf.textProperty().addListener((o, oldValue, newValue) -> textFieldDoubleListener(txf, oldValue, newValue));
	}
	
	private static void textFieldIntListener(TextField txf, String oldValue, String newValue) {		
		if (!newValue.isEmpty()) {
			try {
				int num = Integer.parseInt(newValue);
				if (num < 0) {
					txf.setText(oldValue);
				}
			} catch (NumberFormatException e) {
				txf.setText(oldValue);
			}
		}
	}
	
	private static void textFieldDoubleListener(TextField txf, String oldValue, String newValue) {		
		if (!newValue.isEmpty()) {
			try {
				double num = Double.parseDouble(newValue);
				if (num < 0) {
					txf.setText(oldValue);
				}
			} catch (NumberFormatException e) {
				txf.setText(oldValue);
			}
		}
	}
	
}
