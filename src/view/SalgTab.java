package view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.PrisKategori;
import model.Produkt;
import model.ProduktKategori;
import model.ProduktLinje;
import model.Salg;
import storage.Storage;

import java.time.LocalDate;

import controller.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class SalgTab extends GridPane implements ReloadableTab {
	private ListView<Produkt> lvwProdukter;
	private ListView<ProduktLinje> lvwProduktLinjer;
	private Button btnGennemførSalg, btnAdd, btnDelete, btnAntal, btnRabat, btnAnuller, btnKøb;
	private ComboBox<PrisKategori> cboxPrisKategorier;
	private Salg salg;

	private void setUpPane() {
		this.setPadding(new Insets(20));
		this.setHgap(20);
		this.setVgap(10);
		this.setGridLinesVisible(false);

	}

	public SalgTab() {
		salg = Controller.createSalg();
		
		setUpPane();
		this.setGridLinesVisible(false);

		// Column 0
		cboxPrisKategorier = new ComboBox<>();
		cboxPrisKategorier.setOnAction(e -> cboxPrisKategorierAction());
		this.add(cboxPrisKategorier, 0, 0);

		lvwProdukter = new ListView<>();
		this.add(lvwProdukter, 0, 1, 1, 5);
		lvwProdukter.getItems().addAll(Storage.getProdukter());

		// Column 1
		btnAdd = new Button("→");
		btnAdd.setOnAction(e -> btnAddAction());
		this.add(btnAdd, 1, 3);

		btnDelete = new Button("←");
		this.add(btnDelete, 1, 4);

		// Column 2
		ViewHelper.label(this, 2, 0, "Produkt");
		ViewHelper.label(this, 3, 0, "Antal");
		ViewHelper.label(this, 4, 0, "Pris");
		ViewHelper.label(this, 5, 0, "Rabat");

		lvwProduktLinjer = new ListView<>();
		this.add(lvwProduktLinjer, 2, 1, 4, 5);
		Salg s = new Salg(LocalDate.now());
		s.opretProduktLinje(Storage.getProdukter().get(0), Storage.getPrisKategorier().get(0), 2, 0.1);
		lvwProduktLinjer.getItems().addAll(s.getProduktLinjer());

		btnGennemførSalg = new Button("Gennemfør salg");
		this.add(btnGennemførSalg, 2, 6);

		// Column 5
		btnAntal = new Button("Antal");
		this.add(btnAntal, 6, 2);

		btnRabat = new Button("Rabat");
		this.add(btnRabat, 6, 3);

		btnAnuller = new Button("Anuller");
		this.add(btnAnuller, 6, 5);

		// Column 6
		btnKøb = new Button("Køb");
		this.add(btnKøb, 7, 5);

	}

	// Node updater methods
	private void updateCboxPrisKategrorier() {
		cboxPrisKategorier.getItems().removeAll(cboxPrisKategorier.getItems());
		cboxPrisKategorier.getItems().addAll(Storage.getPrisKategorier());
	}

	private void updateLvwProdukter() {
		lvwProdukter.getItems().removeAll(lvwProdukter.getItems());
		PrisKategori selected = cboxPrisKategorier.getSelectionModel().getSelectedItem();
		if (selected != null) {
			lvwProdukter.getItems().addAll(Controller.getProdukterIPrisKategori(selected));
		}
	}

	private void updateLvwProduktLinjer() {
		lvwProduktLinjer.getItems().removeAll(lvwProduktLinjer.getItems());
		lvwProduktLinjer.getItems().addAll(salg.getProduktLinjer());
	}
	
	// Node Action methods
	public void cboxPrisKategorierAction() {
		updateLvwProdukter();
	}
	
	public void btnAddAction() {
		Produkt selected = lvwProdukter.getSelectionModel().getSelectedItem();
		if (selected != null) {
			ProduktLinje match = null;
			for (ProduktLinje pl : lvwProduktLinjer.getItems()) {
				if(pl.getProdukt() == selected && pl.getRabat() == 0d) {
					match = pl;
				}
			}
			if(match == null) {
				salg.opretProduktLinje(selected, cboxPrisKategorier.getSelectionModel().getSelectedItem(), 1, 0d);				
			} else {
				match.setAntal(match.getAntal() + 1);
			}
			updateLvwProduktLinjer();
		}
		
		
	}

	@Override
	public void reload() {
		updateCboxPrisKategrorier();
	}

}
