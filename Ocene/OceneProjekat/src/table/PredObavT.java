package table;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PredObavT {
	
	private StringProperty naz;
	private DoubleProperty bodovi;
	private StringProperty datum;
	
	public PredObavT(String naz, double bodovi, String datum) {
		
		this.naz = new SimpleStringProperty(naz);
		this.bodovi = new SimpleDoubleProperty(bodovi);
		this.datum = new SimpleStringProperty(datum);
	}
	
	public StringProperty getNaziv() {
		return naz;
	}
	
	public DoubleProperty getBodovi() {
		return bodovi;
	}
	
	public StringProperty getDatum() {
		return datum;
	}

}
