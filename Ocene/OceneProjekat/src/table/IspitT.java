package table;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class IspitT {
	
	private StringProperty datum;
	private DoubleProperty brBodova;
	private DoubleProperty ukupnoBodova;
	private IntegerProperty ocena;
	
	public IspitT(String datum, double brBodova, double ukupnoBodova, int ocena) {
		
		super();
		this.datum = new SimpleStringProperty(datum);
		this.brBodova = new SimpleDoubleProperty(brBodova);
		this.ukupnoBodova = new SimpleDoubleProperty(ukupnoBodova);
		this.ocena = new SimpleIntegerProperty(ocena);
	}

	public StringProperty getDatum() {
		return datum;
	}

	public DoubleProperty getBrBodova() {
		return brBodova;
	}

	public DoubleProperty getUkupnoBodova() {
		return ukupnoBodova;
	}

	public IntegerProperty getOcena() {
		return ocena;
	}
	
	
	

}
