package table;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PredmetT {
	
	private IntegerProperty idPredmet;
	private IntegerProperty brEspb;
	private IntegerProperty godina;
	private StringProperty nazPred;
	private StringProperty nazProf;
	private StringProperty polozen;
	private StringProperty status;
	
	public PredmetT(int idPredmet, int brEspb, int godina, String nazPred,
			String nazProf, String polozen, String status) {
		
		super();
		this.idPredmet = new SimpleIntegerProperty(idPredmet);
		this.brEspb = new SimpleIntegerProperty(brEspb);
		this.godina = new SimpleIntegerProperty(godina);
		this.nazPred = new SimpleStringProperty(nazPred);
		this.nazProf = new SimpleStringProperty(nazProf);
		this.polozen = new SimpleStringProperty(polozen);
		this.status = new SimpleStringProperty(status);
		
	}

	public IntegerProperty getIdPredmet() {
		return idPredmet;
	}

	public IntegerProperty getBrEspb() {
		return brEspb;
	}

	public IntegerProperty getGodina() {
		return godina;
	}

	public StringProperty getNazPred() {
		return nazPred;
	}

	public StringProperty getNazProf() {
		return nazProf;
	}

	public StringProperty getPolozen() {
		return polozen;
	}

	public StringProperty getStatus() {
		return status;
	}
	
	public void setNazPred(String x) {
		this.nazPred = new SimpleStringProperty(x);
	}
	
	
	

}
