//package view;
//
//import javafx.geometry.Insets;
//import javafx.scene.control.Button;
//import javafx.scene.control.ComboBox;
//import javafx.scene.control.ListView;
//import javafx.scene.control.TableColumn;
//import javafx.scene.control.TableView;
//import javafx.scene.control.TextArea;
//import javafx.scene.control.TextField;
//import javafx.scene.control.cell.PropertyValueFactory;
//import javafx.scene.layout.GridPane;
//import javafx.scene.layout.HBox;
//import javafx.scene.layout.VBox;
//import model.PrisKategori;
//import model.Produkt;
//import model.ProduktKategori;
//import storage.Storage;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
//
//public class SalgTabTableView extends GridPane implements ReloadableTab {
//	private TableView table = new TableView();
//	private ListView lvwProdukter;
//	private TextField txfProdukt, txfAntal, txfPris, txfRabat;
//	private Button btnAdd;
//	
//	private ObservableList<ProduktKategori> data = FXCollections.observableArrayList(new ProduktKategori("Kage") = pk1, new Produkt(pk1, "HindbærTærte", "Smager godt") = p1, new ProduktKategoriModel(p1, 6, 500, 0);
//
//	private void setUpPane() {
//		this.setPadding(new Insets(20));
//		this.setHgap(20);
//		this.setVgap(10);
//		this.setGridLinesVisible(false);
//
//	}
//
//	public SalgTabTableView() {
//		setUpPane();
//
//		// Column 0
//		lvwProdukter = new ListView<>();
//		this.add(lvwProdukter, 0, 0);
//
//		// Column 1
//		table.setEditable(true);
//		TableColumn produktCol = new TableColumn("Produkt");
//		produktCol.setMinWidth(100);
//		// produktCol.setCellValueFactory(new PropertyValueFactory<Produkt,
//		// String>("Produkt"));
//
//		TableColumn antalCol = new TableColumn("Antal");
//		antalCol.setMinWidth(100);
//		// antalCol.setCellValueFactory(new PropertyValueFactory<Produkt,
//		// String>("Antal"));
//
//		TableColumn prisCol = new TableColumn("Pris");
//		prisCol.setMinWidth(100);
//		// antalCol.setCellValueFactory(new PropertyValueFactory<Produkt,
//		// String>("Pris"));
//
//		TableColumn rabatCol = new TableColumn("Rabat");
//		rabatCol.setMinWidth(100);
//		// antalCol.setCellValueFactory(new PropertyValueFactory<Produkt,
//		// String>("Rabat"));
//
//		table.getColumns().addAll(produktCol, antalCol, prisCol, rabatCol);
//
//		txfProdukt = new TextField();
//		txfProdukt.setPromptText("Produkt");
//		txfProdukt.setPrefWidth(80);
//
//		txfAntal = new TextField();
//		txfAntal.setPromptText("Antal");
//		txfAntal.setPrefWidth(80);
//
//		txfPris = new TextField();
//		txfPris.setPromptText("Pris");
//		txfPris.setPrefWidth(80);
//
//		txfRabat = new TextField();
//		txfRabat.setPromptText("Rabat");
//		txfRabat.setPrefWidth(80);
//
//		btnAdd = new Button("Add");
//
//		HBox hbox = new HBox();
//		hbox.setSpacing(5);
//		hbox.getChildren().addAll(txfProdukt, txfAntal, txfPris, txfRabat, btnAdd);
//		// this.add(hbox2, 1, 1);
//
//		final VBox vbox = new VBox();
//		vbox.setSpacing(5);
//		vbox.setPadding(new Insets(10, 0, 0, 10));
//		vbox.getChildren().addAll(table, hbox);
//		this.add(vbox, 1, 0);
//
//		// Column 2
//
//		// Column 3
//	}
//
//	@Override
//	public void reload() {
//		// TODO Auto-generated method stub
//	}
//	
//	public class ProduktKategoriModel {
//		private Produkt produkt;
//		private int antal;
//		private double pris;
//		private double rabat;
//		
//		public ProduktKategoriModel(Produkt produkt, int antal, double pris, double rabat) {
//			this.produkt = produkt;
//			this.antal = antal;
//			this.pris = pris;
//			this.rabat = rabat;
//		}
//	}
//
//}
