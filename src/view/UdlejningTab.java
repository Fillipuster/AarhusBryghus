package view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import model.Produkt;
import model.ProduktKategori;

public class UdlejningTab extends GridPane implements ReloadableTab {
	private ListView<ProduktKategori> lvwKunder;
	private ListView<Produkt> lvwUdlejligeProdukter;
	private ListView lvwValgteProdukter;
	private TextField txfKategoriNavn;
	private Button btnAdd, btnDelete, btnOpretKategori;
	private ComboBox<ProduktKategori> cboxProduktKategori;

	public void reload() {
		// updateLvwKategorier();
	}

	private void setUpPane() {
		this.setPadding(new Insets(20));
		this.setHgap(20);
		this.setVgap(10);
		this.setGridLinesVisible(false);
	}

	public UdlejningTab() {
		setUpPane();

		// Column 0
		ViewHelper.label(this, 0, 1, "Kunder");
		lvwKunder = new ListView<ProduktKategori>();
		// lvwKategorier.setOnMouseClicked(e -> lvwKategorierAction());
		this.add(lvwKunder, 0, 2, 1, 5);

		// Column 1
		ViewHelper.label(this, 1, 0, "Produkt kategori:");
		cboxProduktKategori = new ComboBox<>();
		this.add(cboxProduktKategori, 1, 1);

		ViewHelper.label(this, 2, 0, "Produkter");
		lvwUdlejligeProdukter = new ListView<Produkt>();
		this.add(lvwUdlejligeProdukter, 1, 2, 1, 5);

		// Column 2
		btnAdd = new Button("→");
		// btnAdd.setOnAction(e -> btnAdd());
		btnAdd.setPrefWidth(200d);
		this.add(btnAdd, 2, 5);

		btnDelete = new Button("←");
		// btnSletKategori.setOnAction(e -> btnSletKategoriAction());
		btnDelete.setPrefWidth(200d);
		this.add(btnDelete, 2, 6);

		// Column 3
		ViewHelper.label(this, 3, 0, "Valgte Produkter");
		lvwValgteProdukter = new ListView<>();
		this.add(lvwValgteProdukter, 3, 2, 1, 5);

		// updateLvwKategorier();
	}
}
