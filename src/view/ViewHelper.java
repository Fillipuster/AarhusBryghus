package view;

import javafx.scene.control.Label;
import javafx.scene.control.ListView;
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
	
}
