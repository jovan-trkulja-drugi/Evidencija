package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the predmet database table.
 * 
 */
@Entity
@Table(name="predmet")
@NamedQuery(name="Predmet.findAll", query="SELECT p FROM Predmet p")
public class Predmet implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idPredmet;

	@Column(name="br_espb")
	private int brEspb;

	private int godina;

	@Column(name="naz_pred")
	private String nazPred;

	@Column(name="naz_prof")
	private String nazProf;

	private String polozen;

	private String status;

	//bi-directional many-to-one association to Ispit
	@OneToMany(mappedBy="predmet")
	private List<Ispit> ispits;

	//bi-directional many-to-one association to Predispitnaobaveza
	@OneToMany(mappedBy="predmet")
	private List<Predispitnaobaveza> predispitnaobavezas;

	public Predmet() {
	}

	public int getIdPredmet() {
		return this.idPredmet;
	}

	public void setIdPredmet(int idPredmet) {
		this.idPredmet = idPredmet;
	}

	public int getBrEspb() {
		return this.brEspb;
	}

	public void setBrEspb(int brEspb) {
		this.brEspb = brEspb;
	}

	public int getGodina() {
		return this.godina;
	}

	public void setGodina(int godina) {
		this.godina = godina;
	}

	public String getNazPred() {
		return this.nazPred;
	}

	public void setNazPred(String nazPred) {
		this.nazPred = nazPred;
	}

	public String getNazProf() {
		return this.nazProf;
	}

	public void setNazProf(String nazProf) {
		this.nazProf = nazProf;
	}

	public String getPolozen() {
		return this.polozen;
	}

	public void setPolozen(String polozen) {
		this.polozen = polozen;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<Ispit> getIspits() {
		return this.ispits;
	}

	public void setIspits(List<Ispit> ispits) {
		this.ispits = ispits;
	}

	public Ispit addIspit(Ispit ispit) {
		getIspits().add(ispit);
		ispit.setPredmet(this);

		return ispit;
	}

	public Ispit removeIspit(Ispit ispit) {
		getIspits().remove(ispit);
		ispit.setPredmet(null);

		return ispit;
	}

	public List<Predispitnaobaveza> getPredispitnaobavezas() {
		return this.predispitnaobavezas;
	}

	public void setPredispitnaobavezas(List<Predispitnaobaveza> predispitnaobavezas) {
		this.predispitnaobavezas = predispitnaobavezas;
	}

	public Predispitnaobaveza addPredispitnaobaveza(Predispitnaobaveza predispitnaobaveza) {
		getPredispitnaobavezas().add(predispitnaobaveza);
		predispitnaobaveza.setPredmet(this);

		return predispitnaobaveza;
	}

	public Predispitnaobaveza removePredispitnaobaveza(Predispitnaobaveza predispitnaobaveza) {
		getPredispitnaobavezas().remove(predispitnaobaveza);
		predispitnaobaveza.setPredmet(null);

		return predispitnaobaveza;
	}
	

	@Override
	public String toString() {
		return "Предмет -> " + nazPred + " | " + nazProf + " | Година изучавања: " + godina;
	}

}