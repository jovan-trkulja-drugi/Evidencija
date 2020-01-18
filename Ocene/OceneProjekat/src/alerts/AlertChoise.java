package alerts;

import controller.Controller;

import gui.Statistike;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Predmet;
import slikeKlasa.Images;

public class AlertChoise extends Application {

	private Stage stage;

	private TextField txt = new TextField();

	private Button btn = new Button("Прикажи колоквијуме", Images.getImageUnesi());
	private Button btn2 = new Button("Прикажи испите", Images.getImageUnesi());
	
	private String akcija = "";

	@Override
	public void start(Stage stage) throws Exception {

		this.stage = stage;
		
		oboj();
		
		BorderPane bp = new BorderPane();
		
		postaviVelicinu();
		
		if("КОЛОКВИЈУМ".equals(akcija)) {
			bp.setCenter(initGui());
		} else {
			bp.setCenter(initGuiIspit());
		}
		
		btn.setOnAction(this::akcija);
		btn2.setOnAction(this::akcija);
		
		Scene s = new Scene(bp, 300, 100);

		txt.setOnKeyPressed(e -> {

			if (e.getCode() == KeyCode.ENTER) {
				akcija(e);
			}

		});
		
		s.setOnKeyPressed(d -> {
			if(d.getCode() == KeyCode.ESCAPE) {
				this.stage.close();
			}
		});

		stage.setResizable(false);
		stage.setScene(s);
		stage.getIcons().add(Images.getImagePozornica());
		stage.setTitle("Избор");
		stage.show();

	}
	
	public void start(Stage stage, String akcija) throws Exception {
		
		this.akcija = akcija;
		start(stage);
	}

	private VBox initGui() {

		VBox v = new VBox();
		v.setSpacing(10);
		v.setPadding(new Insets(10));

		v.getChildren().add(txt);
		v.getChildren().add(btn);

		return v;
	}
	
	private VBox initGuiIspit() {

		VBox v = new VBox();
		v.setSpacing(10);
		v.setPadding(new Insets(10));

		v.getChildren().add(txt);
		v.getChildren().add(btn2);

		return v;
	}

	private void akcija(ActionEvent event) {

		if(!provera()) {
			Alerts.showErrorAlert("Нисте унели назив предмета");
			oboj();
			return;
		}
		
		Alerts.showInformationAlert("Тренутак", "Тренутно добављамо податке из базе", false);
		
		Predmet p = Controller.findPredmet(txt.getText());

		if (p == null) {

			Alerts.closeDialog("info");
			Alerts.showErrorAlert("Унети предмет не постоји у бази");
			
		}
		
		if("KOLOKVIJUM".equals(akcija)) {
			
			Statistike s = new Statistike();
			s.prikaziKLK(p);
		
		} else if("ИСПИТ".equals(akcija)) {

			Statistike s = new Statistike();
			s.prikaziIspit(p);
		}
		
		stage.close();

	}
	
	private boolean provera() {
		
		if ("".equals(txt.getText())) {
			return false;
		}
		
		return true;
	}

	private void akcija(KeyEvent event) {

		if(!provera()) {
			Alerts.showErrorAlert("Нисте унели назив предмета");
			oboj();
			return;
		}
		
		Alerts.showInformationAlert("Тренутак", "Тренутно добављамо податке из базе", false);
		
		Predmet p = Controller.findPredmet(txt.getText());

		if (p == null) {

			Alerts.closeDialog("info");
			Alerts.showErrorAlert("Унети предмет не постоји у бази");
			
		}
	
		if("КОЛОКВИЈУМ".equals(akcija)) {
			
			Statistike s = new Statistike();
			s.prikaziKLK(p);
		
		} else if("ИСПИТ".equals(akcija)) {
			
			Statistike s = new Statistike();
			s.prikaziIspit(p);
		}
		
		stage.close();

	}

	private void oboj() {

		if ("".equals(txt.getText())) {

			txt.setStyle("-fx-background-color: #d63c3c;" + "-fx-text-fill: white;");

			txt.setPromptText("Обавезно поље1");
		}

		txt.setOnKeyTyped(e -> {

			txt.setStyle("-fx-background-color: white;" + "-fx-text-fill: black;");

			if ("".equals(txt.getText())) {

				txt.setStyle("-fx-background-color: #d63c3c;" + "-fx-text-fill: white;");

				txt.setPromptText("Обавезно поље!");
			}
		});
	}

	private void postaviVelicinu() {

		btn.setPrefSize(170, 25);
		btn2.setPrefSize(170, 25);
		txt.setPromptText("Унесите предмет");

		btn.setCursor(Cursor.HAND);
		btn2.setCursor(Cursor.HAND);

		btn.setStyle("-fx-background-color: lightblue");
		btn2.setStyle("-fx-background-color: lightblue");

		String css = "-fx-background-color: lightblue";

		btn.setOnMouseEntered(
				e -> btn.setStyle("-fx-background-color: #eb4034; " + "-fx-cursor: hand;" + "-fx-text-fill: white;"));
		btn.setOnMouseExited(e -> btn.setStyle(css));
		
		btn2.setOnMouseEntered(
				e -> btn2.setStyle("-fx-background-color: #eb4034; " + "-fx-cursor: hand;" + "-fx-text-fill: white;"));
		btn2.setOnMouseExited(e -> btn2.setStyle(css));
	}

}
