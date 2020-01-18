package guiUpdate;

import alerts.Alerts;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import slikeKlasa.Images;

public class PozornicaUpdate extends Application {
	
	private Button updatePredOb = new Button("Ажурирај податке о колоквијуму", Images.getImageUpdate());
	private Button updatePredmet = new Button("Aжурирај податке о предмету", Images.getImageUpdate());
	private Button deletePredOb = new Button("Обриши предиспитну обавезу", Images.getImageUpdate());
	private Button close = new Button("Затвори", Images.getImageZatvori());
	
	@Override
	public void start(Stage stage) throws Exception {
		
		BorderPane bp = new BorderPane();
		bp.setBackground(Images.getBackground());
		
		postaviVelicinu();
		
		bp.setCenter(initGui());
		
		close.setOnAction(this::zatvaranje);
		
		updatePredmet.setOnAction(e -> {
			try {
				
				Stage pozornica = new Stage();
				UpdatePredmet up = new UpdatePredmet();
				up.start(pozornica);
				
			} catch(Exception ex) {
				ex.printStackTrace();
				return;
			}
		});
		
		updatePredOb.setOnAction(e -> {
			try {
				
				Stage pozornica = new Stage();
				UpdatePredOb up = new UpdatePredOb();
				up.start(pozornica);
				
			} catch(Exception ex) {
				ex.printStackTrace();
				return;
			}
		});
		
		deletePredOb.setOnAction(e -> {
			
			Alerts.showErrorAlert("Изабрана функција тренутно није доступна!");
			return;
			
		});
		
		
		Scene s = new Scene(bp);
		
		stage.setResizable(false);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setScene(s);
		stage.getIcons().add(Images.getImagePozornica());
		stage.setTitle("Ажурирање података");
		stage.show();
		
	}
	
	private GridPane initGui() {
		
		GridPane gp = new GridPane();
		
		gp.setHgap(15);   gp.setVgap(15);
		gp.setPadding(new Insets(20));
		gp.setAlignment(Pos.CENTER);
		
		gp.add(updatePredmet, 0, 0);
		gp.add(updatePredOb, 0, 1);
		gp.add(deletePredOb, 0, 2);
		gp.add(close, 0, 3);
		
		return gp;
	}
	
	private void zatvaranje(ActionEvent event) {
		
		Alerts.showExitAlert();
		
	}
	
	private void postaviVelicinu() {
		
		updatePredOb.setPrefSize(300, 50);
		updatePredmet.setPrefSize(300, 50);
		deletePredOb.setPrefSize(300, 50);
		close.setPrefSize(300, 50);
		
		updatePredOb.setStyle("-fx-background-color: lightblue; -fx-cursor: hand");
		updatePredmet.setStyle("-fx-background-color: lightblue; -fx-cursor: hand");
		deletePredOb.setStyle("-fx-background-color: lightblue; -fx-cursor: hand");
		close.setStyle("-fx-background-color: lightblue; -fx-cursor: hand");
		
		String css = "-fx-background-color: lightblue; -fx-cursor: hand";
		
		updatePredmet.setOnMouseEntered(e -> updatePredmet.setStyle("-fx-background-color: #eb4034; "
																  + "-fx-cursor: hand;"
		                                                          + "-fx-text-fill: white;"));

		updatePredmet.setOnMouseExited(e -> updatePredmet.setStyle(css));
		
		updatePredOb.setOnMouseEntered(e -> updatePredOb.setStyle("-fx-background-color: #eb4034; "
															   + "-fx-cursor: hand;"
															   + "-fx-text-fill: white;"));
		updatePredOb.setOnMouseExited(e -> updatePredOb.setStyle(css));
		
		deletePredOb.setOnMouseEntered(e -> deletePredOb.setStyle("-fx-background-color: #eb4034; "
																+ "-fx-cursor: hand;"
																+ "-fx-text-fill: white;"));
		deletePredOb.setOnMouseExited(e -> deletePredOb.setStyle(css));
		
		close.setOnMouseEntered(e -> close.setStyle("-fx-background-color: #eb4034; "
																  + "-fx-cursor: hand;"
																  + "-fx-text-fill: white;"));
		close.setOnMouseExited(e -> close.setStyle(css));

	}
	
}
