package gui;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

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
import model.Predispitnaobaveza;
import model.Predmet;
import slikeKlasa.Images;

public class UnosPredObav extends Application {
	
	private Label lbl1 = new Label("Изаберите предмет");
	private Label lbl2 = new Label("Унесите назив");
	private Label lbl3 = new Label("Унесите бодове");
	private Label lbl4 = new Label("Датум одржавања колоквијума");
	
	private ComboBox<Predmet> comboP = new ComboBox<>();
	
	private TextField nazPO = new TextField();
	private TextField bodovi = new TextField();
	
	private DatePicker datum = new DatePicker();
	
	private Button potvrdi = new Button("Унеси", Images.getImageUnesi());
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
		stage.setTitle("Унос предиспитне обавезе");
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
		gp.add(nazPO, 1, 1);
		
		gp.add(lbl3, 0, 2);
		gp.add(bodovi, 1, 2);
		
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
		
		if("".equals(nazPO.getText())) {
			 
			nazPO.setStyle("-fx-background-color: #d63c3c;"
					   + "-fx-text-fill: white;");
			
			nazPO.setPromptText("Обавезно поље!");
		} 
		
		if("".equals(bodovi.getText())) {
			
			bodovi.setStyle("-fx-background-color: #d63c3c;"
					   + "-fx-text-fill: white;");
			
			bodovi.setPromptText("Обавезно поље!");
			
		}
		
		nazPO.setOnKeyTyped(e -> {
			
			nazPO.setStyle("-fx-background-color: white;"
					   + "-fx-text-fill: black;");
			
			if("".equals(nazPO.getText())) {
				
				nazPO.setStyle("-fx-background-color: #d63c3c;"
						   + "-fx-text-fill: white;");
				
				nazPO.setPromptText("Обавезно поље!");
			}
		});
		
		bodovi.setOnKeyTyped(e -> {
			
			bodovi.setStyle("-fx-background-color: white;"
					   + "-fx-text-fill: black;");
			
			if("".equals(bodovi.getText())) {
				
				bodovi.setStyle("-fx-background-color: #d63c3c;"
						   + "-fx-text-fill: white;");
				
				bodovi.setPromptText("Обавезно поље!");
			}
		});
		
	}
	
	private boolean provera() {
		
		if("".equals(bodovi.getText()) || "".equals(nazPO.getText())) {
			return false;
		}
		
		return true;
	}
	
	private void upisi() {
		
		double bod;
		
		Predmet p = comboP.getSelectionModel().getSelectedItem();
		
		String nazP = nazPO.getText();
		
		try {
			
			bod = Double.parseDouble(bodovi.getText());
			
		} catch(NumberFormatException nfe) {
			Alerts.showErrorAlert("Бодови и / или оцена морају бити нумерички податак");
			return;
		}
		
		LocalDate ld = datum.getValue();
		
		if(ld == null) {
			
			boolean ok = Alerts.showWarningAlert("Нисте унели датум предиспитне обавезе. Ипак наставити?");
			
			if(ok) zavrsiUpisivanje(p, nazP, bod, ld);
			else return;
			
		} else {
			
			zavrsiUpisivanje(p, nazP, bod, ld);
			return;
		}
	}
	
	private void zavrsiUpisivanje(Predmet p, String nazP, double bod, LocalDate ld) {
		
		Predispitnaobaveza po = new Predispitnaobaveza();
		
		if(ld == null) {
		
			po.setNazPredObav(nazP);
			po.setBrBodova(bod);
			po.setPredmet(p);
			po.setDatum(null);
			
		} else {
			
			Date date = Date.from(ld.atStartOfDay(ZoneId.systemDefault()).toInstant());
			po.setNazPredObav(nazP);
			po.setBrBodova(bod);
			po.setPredmet(p);
			po.setDatum(date);
			
		}
		
		if(Controller.insertColloquim(po) && Controller.connectSubjectColloquim(p, po)) {
			
			Alerts.showInformationAlert("Успешно сте додали предиспитну обавезу за изабрани предмет!", true);
			pozornica.close();
			return;
			
		} else {
			Alerts.showErrorAlert("Грешка приликом додавања предиспитне обавезе!");
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
