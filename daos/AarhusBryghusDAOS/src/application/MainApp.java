package application;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import javafx.application.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

// Søg efter "Opgave" i kildekoden for at finde relevante opgave-metoder;
public class MainApp extends Application {

	private static Connection connection;
	private static Statement stmt;
	
	public static void main(String[] args) {
		// JDBC
		try {
			// Generel opsætning via native driver;
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			connection = DriverManager.getConnection("jdbc:sqlserver://localhost\\SQLEXPRESS;databaseName=AarhusBryghus;user=sa;password=1116;");
			stmt = connection.createStatement();
		}
		catch (Exception e) {
			System.out.println("Database connection error: " + e.getMessage());
			e.printStackTrace();
		}
		
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {		
		// JavaFX
		GridPane pane = new GridPane();
    	pane.setPadding(new Insets(10d));
    	pane.setHgap(10d);
    	pane.setVgap(10d);
        pane.setPrefSize(350, 250);
        buildWindow(pane);
        
        stage.setTitle("Aarhus Bryghus Database");
        stage.setScene(new Scene(pane));
		stage.show();
	}
	
	// Nodes
	private Label lblStatus, lblStatistik;
	private ComboBox<String> cboxProduktKategorier, cboxProdukter;
	private TextField txfProduktNavn, txfProduktBeskrivelse;
	private Button btnOpretProdukt, btnHentStatistik;
	private DatePicker dpSalg;
	
	private void buildWindow(GridPane pane) {
		// Column #0
		cboxProduktKategorier = new ComboBox<String>();
		cboxProduktKategorier.setPromptText("Vælg produktkategori...");
		pane.add(cboxProduktKategorier, 0, 0);
		
		txfProduktNavn = new TextField();
		txfProduktNavn.setPromptText("Produkt Navn");
		pane.add(txfProduktNavn, 0, 1);
		
		txfProduktBeskrivelse = new TextField();
		txfProduktBeskrivelse.setPromptText("Produkt Beskrivelse");
		pane.add(txfProduktBeskrivelse, 0, 2);
		
		btnOpretProdukt = new Button("Opret Produkt");
		btnOpretProdukt.setOnAction(e -> btnOpretProduktAction());
		pane.add(btnOpretProdukt, 0, 3);
		
		lblStatus = new Label();
		pane.add(lblStatus, 0, 4);
		
		// Column #1
		cboxProdukter = new ComboBox<String>();
		cboxProdukter.setPromptText("Vælg produkt...");
		pane.add(cboxProdukter, 1, 0);
		
		dpSalg = new DatePicker();
		dpSalg.setPromptText("Vælg dato for salgstatistik...");
		pane.add(dpSalg, 1, 1);
		
		btnHentStatistik = new Button("Hent Statistik");
		btnHentStatistik.setOnAction(e -> btnHentStatistikAction());
		pane.add(btnHentStatistik, 1, 2);
		
		lblStatistik = new Label("Total salg for produkt på dato:\nNaN");
		pane.add(lblStatistik, 1, 3);
		
		// Initialization
		updateCboxProduktKategorier();
		updateCboxProdukter();
	}
	
	
	// Opgave 6.a
	private boolean sqlCreateProdukt(String navn, String beskrivelse, String produktKategori) {
		String sql = "INSERT INTO Produkter VALUES (?, ?, ?)";
		
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.clearParameters();
			ps.setString(1, navn);
			ps.setString(2, beskrivelse);
			ps.setString(3, produktKategori);
			ps.execute();
			lblStatus.setText("Produkt oprettet.");
			
			return true;
		} catch (SQLException e) {
			System.out.println("SQL Error: " + e.getMessage());
			e.printStackTrace();
			lblStatus.setText("SQL Fejl: " + e.getMessage());
			
			return false;
		}
	}
	
	// Opgave 6.b
	private double sqlGetSalgProduktDato(String produktNavn, LocalDate dato) {
		String sql = "select sum(isnull(aftaltPris, (pris * (1 - rabat)) * antal)) as total from Salg s\r\n" + 
				"join ProduktLinjer pl on pl.salg = s.id\r\n" + 
				"join ProduktPriser pp on pl.produktPris = pp.id\r\n" + 
				"join Produkter p on p.id = pp.produkt\r\n" + 
				"where s.dato = ? and p.navn = ?";
		
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.clearParameters();
			ps.setDate(1, Date.valueOf(dato));
			ps.setString(2, produktNavn);
			ResultSet queryResult = ps.executeQuery();
			queryResult.next();
			lblStatus.setText("Salgsstatisik hentet.");
			return queryResult.getDouble("total");
		} catch (SQLException e) {
			System.out.println("SQL Error: " + e.getMessage());
			e.printStackTrace();
			lblStatus.setText("SQL Fejl: " + e.getMessage());
			
			return Double.NaN;
		}
	}

	// Node updaters;
	private void updateCboxProduktKategorier() {
		cboxProduktKategorier.getItems().clear();
		String sql = "SELECT navn FROM ProduktKategorier";
		
		try {
			ResultSet queryResult = stmt.executeQuery(sql);
			while (queryResult.next()) {
				cboxProduktKategorier.getItems().add(queryResult.getString("navn"));
			}
		} catch (SQLException e) {
			System.out.println("SQL Error: " + e.getMessage());
			e.printStackTrace();
			lblStatus.setText("SQL Fejl: " + e.getMessage());
		}
	}
	
	private void updateCboxProdukter() {
		cboxProdukter.getItems().clear();
		String sql = "SELECT navn FROM Produkter";
		
		try {
			ResultSet queryResult = stmt.executeQuery(sql);
			while (queryResult.next()) {
				cboxProdukter.getItems().add(queryResult.getString("navn"));
			}
		} catch (SQLException e) {
			System.out.println("SQL Error: " + e.getMessage());
			e.printStackTrace();
			lblStatus.setText("SQL Fejl: " + e.getMessage());
		}
	}
	
	// Node actions;
	private void btnOpretProduktAction() {
		sqlCreateProdukt(txfProduktNavn.getText(), txfProduktBeskrivelse.getText(), cboxProduktKategorier.getSelectionModel().getSelectedItem());
		updateCboxProdukter();
	}
	
	private void btnHentStatistikAction() {
		lblStatistik.setText("Total salg for produkt på dato:\n" + sqlGetSalgProduktDato(cboxProdukter.getSelectionModel().getSelectedItem(), dpSalg.getValue()));
	}

}
