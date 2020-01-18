package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the predispitnaobaveza database table.
 * 
 */
@Entity
@Table(name="predispitnaobaveza")
@NamedQuery(name="Predispitnaobaveza.findAll", query="SELECT p FROM Predispitnaobaveza p")
public class Predispitnaobaveza implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idPredispitnaOb;

	@Column(name="br_bodova")
	private double brBodova;

	@Temporal(TemporalType.DATE)
	private Date datum;

	@Column(name="naz_pred_obav")
	private String nazPredObav;

	//bi-directional many-to-one association to Predmet
	@ManyToOne
	@JoinColumn(name="Predmet_idPredmet")
	private Predmet predmet;

	public Predispitnaobaveza() {
	}

	public int getIdPredispitnaOb() {
		return this.idPredispitnaOb;
	}

	public void setIdPredispitnaOb(int idPredispitnaOb) {
		this.idPredispitnaOb = idPredispitnaOb;
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

	public String getNazPredObav() {
		return this.nazPredObav;
	}

	public void setNazPredObav(String nazPredObav) {
		this.nazPredObav = nazPredObav;
	}

	public Predmet getPredmet() {
		return this.predmet;
	}

	public void setPredmet(Predmet predmet) {
		this.predmet = predmet;
	}
	
	@Override
	public String toString() {
		
		return nazPredObav + ", број бодова -> " + brBodova;
	}

}