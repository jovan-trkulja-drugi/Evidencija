package gui;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import alerts.Alerts;
import controller.Controller;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Ispit;
import model.Predispitnaobaveza;
import model.Predmet;
import slikeKlasa.Images;

public class StatistikaPredmeta extends Application {
	
	private ComboBox<Predmet> comboP = new ComboBox<>();
	
	private Button potvrdi = new Button("Прикажи", Images.getImageUnesi());
	private Button zatvori = new Button("Затвори", Images.getImageZatvori());
	
	private TextArea podaci = new TextArea();
	
	private Label lbl1 = new Label("Изаберите предмет");
	
	private String oPredmetu = "";
	private String predispitne = "";
	private String ispit = "";
	
	@Override
	public void start(Stage stage) throws Exception {
		
		BorderPane bp = new BorderPane();
		bp.setBackground(Images.getBackgroundStatistika());
		
		postaviVelicinu();
		
		Alerts.showInformationAlert("Тренутак", "Тренутно добављамо податке из базе", false);
		bp.setCenter(initGui());
		Alerts.closeDialog("info");
		
		zatvori.setOnAction(this::zatvaranje);
		potvrdi.setOnAction(this::obrada);
		
		Scene s = new Scene(bp);
		
		stage.setResizable(false);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setScene(s);
		stage.getIcons().add(Images.getImagePozornica());
		stage.setTitle("Статистички подаци");
		stage.show();
	}
	
	private GridPane initGui() {
		
		GridPane gp = new GridPane();
		gp.setHgap(15); 	gp.setVgap(15);
		gp.setPadding(new Insets(25));
		gp.setAlignment(Pos.BASELINE_CENTER);
		
		getPredmets();
		comboP.getSelectionModel().select(0);
		
		gp.add(lbl1, 0, 0);
		gp.add(comboP, 0, 1);
		gp.add(potvrdi, 0, 2);
		gp.add(podaci, 0, 3);
		
		return gp;
	}
	
	private void getPredmets() {
		
		List<Predmet> pred = Controller.getSubjects();
		
		ObservableList<Predmet> predmeti = comboP.getItems();
		
		pred.forEach(e -> {
			predmeti.add(e);
		});
	
	}
	
	private void zatvaranje(ActionEvent event) {
		
		Alerts.showExitAlert();
	}
	
	private void obrada(ActionEvent event) {
		
		Predmet p = comboP.getSelectionModel().getSelectedItem();
		
		oPredmetu = "Назив предмета -> " + p.getNazPred() + "\n" + 
					"Година изучавања -> " + p.getGodina() + "\n" + 
					"Професор / Асистент -> " + p.getNazProf() + "\n" + 
					"Обавезни / изборни -> " + p.getStatus() + "\n" + 
					"ESPB бодови -> " + p.getBrEspb() + "\n" +
					"--------------------------------------";
		
		List<Predispitnaobaveza> lista = Controller.getColloquiums(p);
		
		if(lista == null || lista.size() == 0) {
			
			predispitne = "За изабрани предмет нису унете или не постоје предиспитне обавезе! \n" + 
			      	  	  "--------------------------------------";
			
		} else {	
			
			double bodovi = lista.stream()
					             .collect(Collectors.summingDouble(Predispitnaobaveza::getBrBodova));

			Predispitnaobaveza najbolja = lista.stream()
								   			   .max(Comparator.comparing(Predispitnaobaveza::getBrBodova))
								               .orElse(null);
			
			predispitne = "Укупно предиспитних обавеза -> " + lista.size() + "\n" + 
						  "Укупно бодова остварених на предиспитним обавезама -> " + bodovi + "\n" + 
		                  "Најбоље урађена предиспитна обавеза -> " + najbolja.toString() + "\n" + 
		                  "--------------------------------------";
		}
			
		List<Ispit> isp = Controller.getExams(p);
		
		if(isp == null || isp.size() == 0) {
			
			ispit = "За изабрани предмет не постоје или нису унети испити! \n" + 
			        "-------------------------------------- \n" +  
					"Предмет положен -> " + p.getPolozen() + "\n" + 
					"Оцена на предмету -> " + "5" + "\n";
			
		} else {
			
			Ispit i = isp.stream()
					     .max(Comparator.comparing(Ispit::getOcena))
					     .orElse(null);
		
			
			ispit = "Предмет положен -> " + p.getPolozen() + "\n" + 
					"Предмет полаган укупно -> " + isp.size() + ". пута" + "\n" + 
					"Оцена на предмету -> " + i.getOcena() + "\n" + 
					"Остварен број бодова на предмету -> " + i.getUkupnoBodova();
			
		}
			
		podaci.setText(oPredmetu + "\n" + predispitne + "\n" + ispit); 
				      
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
		
		lbl1.setStyle("-fx-text-fill: black;"
			        + "-fx-font-weight: bold;"
			        + "-fx-font-size: 15;");

		podaci.setEditable(false);
		podaci.setStyle("-fx-background-color: lightblue;"
					  + "-fx-text-fill: blue;"
					  + "-fx-font-size: 13;");
		
	}

}
