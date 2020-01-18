package gui;

import alerts.Alerts;
import controller.Controller;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Predmet;
import slikeKlasa.Images;

public class UnosPredmeta extends Application {
	
	private Label lbl1 = new Label("Унесите назив предмета");
	private Label lbl2 = new Label("Одаберите статус предмета");
	private Label lbl3 = new Label("Унесите професора / асистента");
	private Label lbl4 = new Label("Да ли сте положили предмет?");
	private Label lbl5 = new Label("Унесите број ESPB бодова");
	
	private TextField naz = new TextField();
	private ComboBox<Integer> god = new ComboBox<>();
	private ComboBox<String> status = new ComboBox<>();
	private TextField prof = new TextField();
	private ComboBox<String> polozen = new ComboBox<>();
	private TextField espb = new TextField();
	
	private Button potvrdi = new Button("Унесите предмет у базу", Images.getImageUnesi());
	private Button obrisi = new Button("Обриши унете податке", Images.getImageObrisi());
	private Button zatvori = new Button("Затвори", Images.getImageZatvori());
	
	private Stage pozornica = new Stage();
	
	
	@Override
	public void start(Stage stage) throws Exception {
		
		this.pozornica = stage;
		
		postaviVelicinu();
		
		BorderPane bp = new BorderPane();
		bp.setBackground(Images.getBackgroundPredmet());
		
		bp.setLeft(initGui());
		
		zatvori.setOnAction(this::zatvaranje);
		obrisi.setOnAction(this::brisanje);
		potvrdi.setOnAction(this::upis);
		
		Scene s = new Scene(bp);
		stage.getIcons().add(Images.getImagePozornica());
		stage.setResizable(false);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setScene(s);
		stage.setTitle("Унос предмета");
		stage.show();
	}
	
	private GridPane initGui() {
		GridPane gp = new GridPane();
		gp.setHgap(20);   gp.setVgap(20);
		gp.setPadding(new Insets(25));
		
		gp.add(lbl1, 0, 0);
		gp.add(lbl2, 0, 1);
		gp.add(lbl3, 0, 2);
		gp.add(lbl4, 0, 3);
		gp.add(lbl5, 0, 4);
		
		gp.add(naz, 1, 0);
		
		ObservableList<Integer> comboGod = god.getItems();
		comboGod.add(1);
		comboGod.add(2);
		comboGod.add(3);
		comboGod.add(4);
		god.getSelectionModel().select(0);
		
		gp.add(god, 2, 0);
		
		ObservableList<String> comboStatus = status.getItems();
		comboStatus.add("Обавезни");
		comboStatus.add("Изборни");
		status.getSelectionModel().select(0);
		
		gp.add(status, 1, 1);
		gp.add(prof, 1, 2);
		
		ObservableList<String> comboPolozen = polozen.getItems();
		comboPolozen.add("Да");
		comboPolozen.add("Не");
		polozen.getSelectionModel().select(0);
		
		gp.add(polozen, 1, 3);
		gp.add(espb, 1, 4);
		
		gp.add(potvrdi, 4, 0);
		gp.add(obrisi, 4, 2);
		
		gp.add(zatvori, 4, 4);
		
		gp.add(new Label("|"), 3, 0);
		gp.add(new Label("|"), 3, 1);
		gp.add(new Label("|"), 3, 2);
		gp.add(new Label("|"), 3, 3);
		gp.add(new Label("|"), 3, 4);
		
		return gp;
	}
	
	private void postaviVelicinu() {
		
		potvrdi.setPrefSize(200, 30);
		obrisi.setPrefSize(200, 30);
		zatvori.setPrefSize(200, 30);
		
		potvrdi.setStyle("-fx-background-color: #4a53ff; "
					   + "-fx-cursor: hand;"
				       + "-fx-border-style: solid;"
				       + "-fx-color: blue;"
				       + "-fx-border-color: yellow;");
		
		obrisi.setStyle("-fx-background-color: #bfbb34;"
					  + "-fx-cursor: hand;"
					  + "-fx-border-style: solid;"
					  + "-fx-text-fill: blue;");
		
		zatvori.setStyle("-fx-cursor: hand;"
					   + "-fx-background-color: lightblue;"
					   + "-fx-border-style: solid;");
		
		status.setStyle("-fx-cursor: hand;");
		polozen.setStyle("-fx-cursor: hand;");
		
		lbl1.setStyle("-fx-text-fill: black;"
					+ "-fx-font-weight: bold;");
		
		lbl2.setStyle("-fx-text-fill: black;"
				+ "-fx-font-weight: bold;");
		
		lbl3.setStyle("-fx-text-fill: black;"
				+ "-fx-font-weight: bold;");
		
		lbl4.setStyle("-fx-text-fill: black;"
				+ "-fx-font-weight: bold;");
		
		lbl5.setStyle("-fx-text-fill: black;"
				+ "-fx-font-weight: bold;");
	}
	
	private void zatvaranje(ActionEvent event) {
		
		Alerts.showExitAlert();
	}
	
	private void brisanje(ActionEvent event) {
		
		naz.setText("");
		prof.setText("");
		espb.setText("");
		status.getSelectionModel().select(0);
		polozen.getSelectionModel().select(0);
		god.getSelectionModel().select(0);
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
		
		if("".equals(naz.getText())) {
			 
			naz.setStyle("-fx-background-color: #d63c3c;"
					   + "-fx-text-fill: white;");
			
			naz.setPromptText("Обавезно поље!");
		} 
		
		if("".equals(espb.getText())) {
			
			espb.setStyle("-fx-background-color: #d63c3c;"
					   + "-fx-text-fill: white;");
			
			espb.setPromptText("Обавезно поље!");
			
		}
		
		if("".equals(prof.getText())) {
			
			prof.setStyle("-fx-background-color: #d63c3c;"
					   + "-fx-text-fill: white;");
			
			prof.setPromptText("Обавезно поље!");
			
		}
		
		naz.setOnKeyTyped(e -> {
			
			naz.setStyle("-fx-background-color: white;"
					   + "-fx-text-fill: black;");
			
			if("".equals(naz.getText())) {
				
				naz.setStyle("-fx-background-color: #d63c3c;"
						   + "-fx-text-fill: white;");
				
				naz.setPromptText("Обавезно поље!");
			}
		});
		
		prof.setOnKeyTyped(e -> {
			
			prof.setStyle("-fx-background-color: white;"
					   + "-fx-text-fill: black;");
			
			if("".equals(prof.getText())) {
				
				prof.setStyle("-fx-background-color: #d63c3c;"
						   + "-fx-text-fill: white;");
				
				prof.setPromptText("Обавезно поље!");
			}
		});
		
		espb.setOnKeyTyped(e -> {
			
			espb.setStyle("-fx-background-color: white;"
					   + "-fx-text-fill: black;");
			
			if("".equals(espb.getText())) {
				
				espb.setStyle("-fx-background-color: #d63c3c;"
						   + "-fx-text-fill: white;");
				
				espb.setPromptText("Обавезно поље!");
			}
		});
		
	}
	
	private void upisi() {
		
		String nazP = naz.getText();
		String godina = god.getSelectionModel().getSelectedItem().toString();
		
		int god = Integer.parseInt(godina);
		
		String statusP = status.getSelectionModel().getSelectedItem();
		String polozenP = polozen.getSelectionModel().getSelectedItem();
		
		String profesor = prof.getText();
		String bodovi = espb.getText();
		
		int ESPB = Integer.parseInt(bodovi);
		
		Predmet p = new Predmet();
	
		p.setNazPred(nazP);
		p.setGodina(god);
		p.setStatus(statusP);
		p.setBrEspb(ESPB);
		p.setNazProf(profesor);;
		p.setPolozen(polozenP);
		
		boolean vr = Controller.insertSubject(p);
		
		if(vr) {
			
			Alerts.showInformationAlert("Успешно сте уписали предмет у базу!", true);
			pozornica.close();
			return;
			
		} else {
			Alerts.showErrorAlert("Грешка приликом додавања предмета у базу!");
			return;
		}
	}
	
	private boolean provera() {
		
		if("".equals(naz.getText()) || "".equals(espb.getText()) || "".equals(prof.getText())) {
			return false;
		}
		
		return true;
	}

}
