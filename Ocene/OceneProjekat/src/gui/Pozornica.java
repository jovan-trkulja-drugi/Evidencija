package gui;

import alerts.AlertChoise;
import alerts.Alerts;
import controller.Controller;
import guiUpdate.PozornicaUpdate;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import slikeKlasa.Images;

public class Pozornica extends Application {

	private Button spisakPredmeta = new Button("Списак предмета", Images.getImagePredmet());
	private Button unesiPredmet = new Button("Унеси предмет", Images.getImagePredmet());
	private Button unesiBodove = new Button("Унеси колоквијум", Images.getImageBodovi());
	private Button unesiIspit = new Button("Унеси испит", Images.getImageBodovi());
	private Button pregledEv = new Button("Табеларни прикази", Images.getImagePregled());
	private Button update = new Button("Ажурирај податке", Images.getImageUpdate());
	private Button zatvori = new Button("Затвори", Images.getImageZatvori());
	private Button statistika = new Button("Статистика о предмету", Images.getImagePregled());

	private MenuBar menuBar = new MenuBar();

	@Override
	public void start(Stage stage) throws Exception {

		BorderPane bp = new BorderPane();
		bp.setBackground(Images.getBackground());

		postaviVelicinu();

		bp.setCenter(initGui());
		bp.setTop(makeMenuBar());

		zatvori.setOnAction(this::zatvaranje);

		spisakPredmeta.setOnAction(e -> {
			try {

				Stage pozornica2 = new Stage();
				PrikazPredmeta sp = new PrikazPredmeta();
				sp.start(pozornica2);

			} catch (Exception ex) {
				ex.printStackTrace();
				return;
			}
		});

		unesiPredmet.setOnAction(e -> {

			try {

				Stage pozornica = new Stage();
				UnosPredmeta up = new UnosPredmeta();
				up.start(pozornica);

			} catch (Exception ex) {
				ex.printStackTrace();
				return;
			}
		});

		unesiIspit.setOnAction(e -> {

			try {

				Stage pozornica = new Stage();
				UnosIspit up = new UnosIspit();
				up.start(pozornica);

			} catch (Exception ex) {
				ex.printStackTrace();
				return;
			}
		});

		unesiBodove.setOnAction(e -> {
			

			try {

				Stage pozornica = new Stage();
				UnosPredObav up = new UnosPredObav();
				up.start(pozornica);

			} catch (Exception ex) {
				ex.printStackTrace();
				return;
			}
		});

		pregledEv.setOnAction(e -> {

			try {

				Stage pozornica = new Stage();
				Statistike up = new Statistike();
				up.start(pozornica);

			} catch (Exception ex) {
				ex.printStackTrace();
				return;
			}
		});
		
		statistika.setOnAction(e -> {
			try {

				Stage pozornica = new Stage();
				StatistikaPredmeta up = new StatistikaPredmeta();
				up.start(pozornica);

			} catch (Exception ex) {
				ex.printStackTrace();
				return;
			}
		});

		update.setOnAction(e -> {
			try {

				Stage pozornica = new Stage();
				PozornicaUpdate up = new PozornicaUpdate();
				up.start(pozornica);

			} catch (Exception ex) {
				ex.printStackTrace();
				return;
			}
		});

		Scene s = new Scene(bp, 700, 350);
		
		s.setOnKeyPressed(key -> {
			if(key.getCode() == KeyCode.ESCAPE) {
				Controller.closeConnection();
				System.exit(0);
				
			}
		});
		
		stage.setResizable(false);
		stage.setScene(s);
		stage.getIcons().add(Images.getImagePozornica());
		stage.setTitle("Евиденција");
		stage.show();

	}

	private void zatvaranje(ActionEvent event) {

		Alerts.showExitAlert();

	}

	private MenuBar makeMenuBar() {
		
		Menu menu = new Menu("Програм");
		
		Menu item1 = new Menu("База података", Images.getImagePregled());
		MenuItem item1CB = new MenuItem("Повежи се на базу података", Images.getImageConnect());
		
		item1CB.setOnAction(e -> {
			
			Alerts.showInformationAlert("Тренутак", "Покушавамо да се повежемо на базу података", false);
			boolean ok = Controller.createEntityManager();
			Alerts.closeDialog("info");
			
			if(ok) {
				Alerts.showInformationAlert("Успешно сте повезани на базу података", true);
			} else {
				Alerts.showErrorAlert("Грешка приликом повезивања на базу");
			}
		});
		
		MenuItem item1RefB = new MenuItem("Refresh конекције", Images.getImageUpdate());
		
		item1RefB.setOnAction(e -> {
			
			Alerts.showInformationAlert("Тренутак", "Покушавамо да освежимо конекцију са базом!", false);
			
			boolean ok1 = Controller.closeConnection();
			boolean ok2 = Controller.createEntityManager();
			
			Alerts.closeDialog("info");
			
			if(ok1 && ok2) {
				Alerts.showInformationAlert("Поново је успостављена конекција на базу података", true);
			} else {
				Alerts.showErrorAlert("Грешка приликом успостављања конекције");
			}
		});
		
		item1.getItems().add(item1CB);
		item1.getItems().add(item1RefB);
		
		Menu item2 = new Menu("Брзи приступ", Images.getImagePredmet());
		
		MenuItem item2Quick1 = new MenuItem("Прегледај колоквијуме за одређени предмет", Images.getImageBodovi());
		MenuItem item2Quick2 = new MenuItem("Прегледај испите за одређени предмет", Images.getImageBodovi());
		
		item2Quick1.setOnAction(action -> {
			
			Stage s = new Stage();
			AlertChoise ac = new AlertChoise();
			try {
				ac.start(s, "КОЛОКВИЈУМ");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
		
		item2Quick2.setOnAction(action -> {
			Stage s = new Stage();
			AlertChoise ac = new AlertChoise();
			try {
				ac.start(s, "ИСПИТ");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
		
		item2.getItems().add(item2Quick1);
		item2.getItems().add(item2Quick2);
		
		MenuItem item3 = new MenuItem("--------------");
		item3.setDisable(true);
		
		MenuItem item4 = new MenuItem("Одштампај извештај", Images.getImagePrint());
		MenuItem item5 = new MenuItem("Затвори апликацију", Images.getImageZatvori());
		
		item5.setOnAction(this::zatvaranje);
		
		menu.getItems().add(item1);
		menu.getItems().add(item2);
		menu.getItems().add(item3);
		menu.getItems().add(item4);
		menu.getItems().add(item5);
		
		menuBar.getMenus().add(menu);
		
		menuBar.setCursor(Cursor.HAND);
		
		return menuBar;
	}

	private GridPane initGui() {

		GridPane gp = new GridPane();
		gp.setHgap(20);
		gp.setVgap(20);
		gp.setAlignment(Pos.CENTER);
		gp.setPadding(new Insets(20));

		gp.add(spisakPredmeta, 0, 0);
		gp.add(unesiPredmet, 1, 0);
		gp.add(unesiBodove, 0, 1);
		gp.add(unesiIspit, 1, 1);
		gp.add(pregledEv, 0, 2);
		gp.add(statistika, 1, 2);
		gp.add(update, 0, 3);
		gp.add(zatvori, 1, 3);

		return gp;
	}

	private void postaviVelicinu() {

		spisakPredmeta.setPrefSize(200, 50);
		unesiBodove.setPrefSize(200, 50);
		unesiIspit.setPrefSize(200, 50);
		unesiPredmet.setPrefSize(200, 50);
		pregledEv.setPrefSize(200, 50);
		zatvori.setPrefSize(200, 50);
		update.setPrefSize(200, 50);
		statistika.setPrefSize(200, 50);

		zatvori.setStyle("-fx-background-color: lightblue; -fx-cursor: hand");
		spisakPredmeta.setStyle("-fx-background-color: lightblue; -fx-cursor: hand");
		unesiBodove.setStyle("-fx-background-color: lightblue; -fx-cursor: hand");
		unesiIspit.setStyle("-fx-background-color: lightblue; -fx-cursor: hand");
		unesiPredmet.setStyle("-fx-background-color: lightblue; -fx-cursor: hand");
		pregledEv.setStyle("-fx-background-color: lightblue; -fx-cursor: hand");
		update.setStyle("-fx-background-color: lightblue; -fx-cursor: hand");
		statistika.setStyle("-fx-background-color: lightblue; -fx-cursor: hand");

		String css = "-fx-background-color: lightblue; -fx-cursor: hand";

		spisakPredmeta.setOnMouseEntered(e -> spisakPredmeta
				.setStyle("-fx-background-color: #eb4034; " + "-fx-cursor: hand;" + "-fx-text-fill: white;"));
		spisakPredmeta.setOnMouseExited(e -> spisakPredmeta.setStyle(css));

		update.setOnMouseEntered(e -> update
				.setStyle("-fx-background-color: #eb4034; " + "-fx-cursor: hand;" + "-fx-text-fill: white;"));
		update.setOnMouseExited(e -> update.setStyle(css));

		pregledEv.setOnMouseEntered(e -> pregledEv
				.setStyle("-fx-background-color: #eb4034; " + "-fx-cursor: hand;" + "-fx-text-fill: white;"));
		pregledEv.setOnMouseExited(e -> pregledEv.setStyle(css));

		unesiPredmet.setOnMouseEntered(e -> unesiPredmet
				.setStyle("-fx-background-color: #eb4034; " + "-fx-cursor: hand;" + "-fx-text-fill: white;"));
		unesiPredmet.setOnMouseExited(e -> unesiPredmet.setStyle(css));

		unesiIspit.setOnMouseEntered(e -> unesiIspit
				.setStyle("-fx-background-color: #eb4034; " + "-fx-cursor: hand;" + "-fx-text-fill: white;"));
		unesiIspit.setOnMouseExited(e -> unesiIspit.setStyle(css));

		unesiBodove.setOnMouseEntered(e -> unesiBodove
				.setStyle("-fx-background-color: #eb4034; " + "-fx-cursor: hand;" + "-fx-text-fill: white;"));
		unesiBodove.setOnMouseExited(e -> unesiBodove.setStyle(css));

		statistika.setOnMouseEntered(e -> statistika
				.setStyle("-fx-background-color: #eb4034; " + "-fx-cursor: hand;" + "-fx-text-fill: white;"));
		statistika.setOnMouseExited(e -> statistika.setStyle(css));

		zatvori.setOnMouseEntered(e -> zatvori
				.setStyle("-fx-background-color: #eb4034; " + "-fx-cursor: hand;" + "-fx-text-fill: white;"));
		zatvori.setOnMouseExited(e -> zatvori.setStyle(css));

	}

	public static void main(String[] args) {
		launch(args);
	}
}