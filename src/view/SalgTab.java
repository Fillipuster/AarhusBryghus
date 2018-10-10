package view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import model.PrisKategori;
import model.Produkt;
import model.ProduktKategori;
import storage.Storage;

public class SalgTab extends GridPane implements ReloadableTab {

	@Override
	public void reload() {
		// TODO Auto-generated method stub
		
	}

	private TableView twSolgteØl;
	private ListView lvwProdukter;

	private TextField txfProduktNavn, txfPris;
	private TextArea txaProduktBeskrivelse;
	private Button btnOpdaterProdukt, btnSletProdukt, btnOpretProdukt, btnTilføjPris;
	
	private void setUpPane() {
		this.setPadding(new Insets(20));
		this.setHgap(20);
		this.setVgap(10);
		this.setGridLinesVisible(false);
	}

	public SalgTab() {
		setUpPane();

		// Column 0
		ListView lvwProdukter = new ListView<>();
		pane
		
		
		
		// Column 1
		
		// Column 2
		
		// Column 3
		
	
}
