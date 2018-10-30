package controller;

import java.time.LocalDate;
import java.util.ArrayList;

import model.ProduktLinje;
import model.Salg;
import storage.Storage;

public class StatisticsController {

	private static ArrayList<Salg> salgIPeriode;
	private static int klipSolgtIPeriode, klipBrugtIPeriode;

	public static void calcStatistics(LocalDate start, LocalDate end) {
		ArrayList<Salg> salg = new ArrayList<>();
		int klipSolgt = 0, klipBrugt = 0;
		for (Salg s : Storage.getSalg()) {
			if (s instanceof Salg && s.getDato().compareTo(start) >= 0 && s.getDato().compareTo(end) <= 0) {
				salg.add(s);
				
				if (s.getBetalingsMetode().isBrugerKlip()) {
					klipBrugt += s.getTotalKlipPris();
				}
				
				for (ProduktLinje pl : s.getProduktLinjer()) {
					klipSolgt += pl.getProdukt().getUdstedteKlip();
				}
			}
		}
		
		salgIPeriode = salg;
		klipSolgtIPeriode = klipSolgt;
		klipBrugtIPeriode = klipBrugt;
	}
	
	public static ArrayList<Salg> getSalgIPeriode() {
		return new ArrayList<Salg>(salgIPeriode);
	}

	public static int getKlipSolgtIPeriode() {
		return klipSolgtIPeriode;
	}

	public static int getKlipBrugtIPeriode() {
		return klipBrugtIPeriode;
	}
	
}
