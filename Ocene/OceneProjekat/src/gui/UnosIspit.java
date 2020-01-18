package gui;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import alerts.Alerts;
import controller.Controller;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Ispit;
import model.Predispitnaobaveza;
import model.Predmet;
import slikeKlasa.Images;

public class UnosIspit extends Application {
	
	private Label lbl1 = new Label("Изаберите предмет");
	private Label lbl2 = new Label("Унесите број бодова са испита");
	private Label lbl3 = new Label("Унесите оцену");
	private Label lbl4 = new Label("Датум полагања предмета");
	
	private ComboBox<Predmet> comboP = new ComboBox<>();
	
	private TextField brBod = new TextField();
	private TextField ocena = new TextField();
	
	private DatePicker datum = new DatePicker();
	
	private Button potvrdi = new Button("Унеси испит", Images.getImageUnesi());
	private Button zatvori = new Button("Затвори", Images.getImageZatvori());
	
	private Stage pozornica = new Stage();
	
	@Override
	public void start(Stage stage) throws Exception {
		
		this.pozornica = stage;
		
		postaviVelicinu();
		
		BorderPane bp = new BorderPane();
		bp.setBackground(Images.getBackgroundKLKIspit());
		
		Alerts.showInformationAlert("Тренутак", "Тренутно добављамо податке из базе", false);
		bp.setCenter(initGui());
		Alerts.closeDialog("info");
		
		zatvori.setOnAction(this::zatvaranje);
		potvrdi.setOnAction(this::upis);
		
		Scene s = new Scene(bp, 1192, stage.getHeight());
		stage.setResizable(false);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setScene(s);
		stage.getIcons().add(Images.getImagePozornica());
		stage.setTitle("Унос испита");
		stage.show();
	}
	
	private GridPane initGui() {
		
		GridPane gp = new GridPane();
		gp.setHgap(15); 	gp.setVgap(15);
		gp.setPadding(new Insets(20));
		
		gp.add(lbl1, 0, 0);
		
		getPredmets();
		comboP.getSelectionModel().select(0);
		
		gp.add(comboP, 1, 0);
		
		gp.add(lbl2, 0, 1);
		gp.add(brBod, 1, 1);
		
		gp.add(lbl3, 0, 2);
		gp.add(ocena, 1, 2);
		
		gp.add(lbl4, 0, 3);
		gp.add(datum, 1, 3);
		
		gp.add(new Label("|"), 2, 0);
		gp.add(new Label("|"), 2, 1);
		gp.add(new Label("|"), 2, 2);
		gp.add(new Label("|"), 2, 3);
		
		gp.add(potvrdi, 3, 0);
		gp.add(zatvori, 3, 3);
		
		return gp;
	}
	
	private void getPredmets() {
		
		List<Predmet> pred = Controller.getSubjects();
		
		ObservableList<Predmet> predmeti = comboP.getItems();
		
		pred.forEach(e -> {
			predmeti.add(e);
		});
	
	}
	
	private void upis(ActionEvent event) {
		
		if(provera()) {
			
			boolean ok = Alerts.showConfrimationAlert("Уписивање", "Унети подаци биће уписани у базу. Потврђујем?");
			
			if(ok) upisi();
			else return;
			
			
		} else {
			
			Alerts.showErrorAlert("Нисте унели тражене податке");
			oboj();
			return;
		}
		
	}
	
	private void oboj() {
		
		if("".equals(brBod.getText())) {
			 
			brBod.setStyle("-fx-background-color: #d63c3c;"
					   + "-fx-text-fill: white;");
			
			brBod.setPromptText("Обавезно поље!");
		} 
		
		if("".equals(ocena.getText())) {
			
			ocena.setStyle("-fx-background-color: #d63c3c;"
					   + "-fx-text-fill: white;");
			
			ocena.setPromptText("Обавезно поље!");
			
		}
		
		brBod.setOnKeyTyped(e -> {
			
			brBod.setStyle("-fx-background-color: white;"
					   + "-fx-text-fill: black;");
			
			if("".equals(brBod.getText())) {
				
				brBod.setStyle("-fx-background-color: #d63c3c;"
						   + "-fx-text-fill: white;");
				
				brBod.setPromptText("Обавезно поље!");
			}
		});
		
		ocena.setOnKeyTyped(e -> {
			
			ocena.setStyle("-fx-background-color: white;"
					   + "-fx-text-fill: black;");
			
			if("".equals(ocena.getText())) {
				
				ocena.setStyle("-fx-background-color: #d63c3c;"
						   + "-fx-text-fill: white;");
				
				ocena.setPromptText("Обавезно поље!");
			}
		});
		
	}
	
	private boolean provera() {
		
		if("".equals(brBod.getText()) || "".equals(ocena.getText())) {
			return false;
		}
		
		return true;
	}
	
	private void upisi() {
		
		double bod;
		int ocenaf;
		double ukupnoBod = 0;
		
		Predmet p = comboP.getSelectionModel().getSelectedItem();
		List<Predispitnaobaveza> lstpo = Controller.getColloquiums(p);
		
		ukupnoBod = lstpo.stream()
						 .collect(Collectors.summingDouble(Predispitnaobaveza::getBrBodova));
		
		try {
			
			bod = Double.parseDouble(brBod.getText());
			ocenaf = Integer.parseInt(ocena.getText());
			
		} catch(NumberFormatException nfe) {
			
			Alerts.showErrorAlert("Бодови и / или оцена морају бити нумерички податак");
			return;
		}
		
		ukupnoBod += bod;
		
		LocalDate ld = datum.getValue();
		
		if(ld == null) {
	
			boolean ok = Alerts.showWarningAlert("Нисте унели датум испита. Ипак наставити?");
			
			if(ok) zavrsiUpisivanje(p, bod, ocenaf, ukupnoBod, ld);
			else return;
			
		} else {
			
			zavrsiUpisivanje(p, bod, ocenaf, ukupnoBod, ld);
			return;
		}
	}
	
	private void zavrsiUpisivanje(Predmet p, double bod, int ocenaf, double ukupno, LocalDate ld) {
		
		Ispit i = new Ispit();
		
		if(ld == null) {
		
			i.setBrBodova(bod);
			i.setOcena(ocenaf);
			i.setUkupnoBodova(ukupno);
			i.setPredmet(p);
			i.setDatum(null);
			
		} else {
			
			Date date = Date.from(ld.atStartOfDay(ZoneId.systemDefault()).toInstant());
			i.setBrBodova(bod);
			i.setOcena(ocenaf);
			i.setUkupnoBodova(ukupno);
			i.setPredmet(p);
			i.setDatum(date);
			
		}
		
		if(Controller.insertExam(i) && Controller.connectSubjectExam(p, i)) {
			
			Alerts.showInformationAlert("Успешно сте додали испит за изабрани предмет!", true);
			pozornica.close();
			return;
		} else {
			Alerts.showErrorAlert("Грешка приликом додавања испита!");
			pozornica.close();
			return;
		}
	}
	
	private void postaviVelicinu() {
		
		potvrdi.setPrefSize(200, 30);
		zatvori.setPrefSize(200, 30);
		
		potvrdi.setStyle("-fx-background-color: #4a53ff; "
					   + "-fx-cursor: hand;"
				       + "-fx-border-style: solid;"
				       + "-fx-color: blue;"
				       + "-fx-border-color: yellow;");
		
		zatvori.setStyle("-fx-cursor: hand;"
					   + "-fx-background-color: lightblue;"
					   + "-fx-border-style: solid;");
		
		comboP.setStyle("-fx-cursor: hand;");
		datum.setStyle("-fx-cursor: hand;");
		
		lbl1.setStyle("-fx-text-fill: black;"
				    + "-fx-font-weight: bold;");
	
		lbl2.setStyle("-fx-text-fill: black;"
			        + "-fx-font-weight: bold;");
	
		lbl3.setStyle("-fx-text-fill: black;"
			        + "-fx-font-weight: bold;");
	
		lbl4.setStyle("-fx-text-fill: black;"
			        + "-fx-font-weight: bold;");
	}
	
	private void zatvaranje(ActionEvent event) {
		
		Alerts.showExitAlert();
	}

}

