package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the ispit database table.
 * 
 */
@Entity
@Table(name="ispit")
@NamedQuery(name="Ispit.findAll", query="SELECT i FROM Ispit i")
public class Ispit implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idIspit;

	@Column(name="br_bodova")
	private double brBodova;

	@Temporal(TemporalType.DATE)
	private Date datum;

	private int ocena;

	@Column(name="ukupno_bodova")
	private double ukupnoBodova;

	//bi-directional many-to-one association to Predmet
	@ManyToOne
	@JoinColumn(name="Predmet_idPredmet")
	private Predmet predmet;

	public Ispit() {
	}

	public int getIdIspit() {
		return this.idIspit;
	}

	public void setIdIspit(int idIspit) {
		this.idIspit = idIspit;
	}

	public double getBrBodova() {
		return this.brBodova;
	}

	public void setBrBodova(double brBodova) {
		this.brBodova = brBodova;
	}

	public Date getDatum() {
		return this.datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

	public int getOcena() {
		return this.ocena;
	}

	public void setOcena(int ocena) {
		this.ocena = ocena;
	}

	public double getUkupnoBodova() {
		return this.ukupnoBodova;
	}

	public void setUkupnoBodova(double ukupnoBodova) {
		this.ukupnoBodova = ukupnoBodova;
	}

	public Predmet getPredmet() {
		return this.predmet;
	}

	public void setPredmet(Predmet predmet) {
		this.predmet = predmet;
	}
	
	@Override
	public String toString() {
		
		return "Број бодова -> " + brBodova + ", оцена -> " + ocena + ", датум -> " + datum;
	}

}