package guiUpdate;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import controller.Controller;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Ispit;
import model.Predmet;

import slikeKlasa.Images;

public class UpdateIspit extends Application {
	
	private Label lbl1 = new Label("Изаберите предмет");
	private Label lbl2 = new Label("Изаберите испит");
	private Label lbl3 = new Label("Унесите оцену");
	private Label lbl4 = new Label("Datum polaganja predmeta");
	private Label lbl5 = new Label("Unesite nove podatke za izabrani ispit");
	private Label lbl6 = new Label("Unesite bodove sa ispita");
	
	private ComboBox<Predmet> comboP = new ComboBox<>();
	private ComboBox<Ispit> comboI = new ComboBox<>();
	
	private TextField brBod = new TextField();
	private TextField ocena = new TextField();
	
	private DatePicker datum = new DatePicker();
	
	private Button potvrdi = new Button("A�uriraj ispit", Images.getImageUnesi());
	private Button azz = new Button("A�uriraj ispite za predmet", Images.getImageUpdate());
	private Button zatvori = new Button("Zatvori", Images.getImageZatvori());
	
	private Stage pozornica = new Stage();
	
	@Override
	public void start(Stage stage) throws Exception {
		
		this.pozornica = stage;
		
		postaviVelicinu();
		
		BorderPane bp = new BorderPane();
		bp.setBackground(Images.getBackgroundKLKIspit());
		
		bp.setCenter(initGui());
		
		zatvori.setOnAction(this::zatvaranje);
		potvrdi.setOnAction(this::upis);
		
		azz.setOnAction(e -> {
			getIspit();
		});
		
		Scene s = new Scene(bp);
		stage.setResizable(false);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setScene(s);
		stage.getIcons().add(Images.getImagePozornica());
		stage.setTitle("Unos ispita");
		stage.show();
	}
	
	private GridPane initGui() {
		
		GridPane gp = new GridPane();
		gp.setHgap(15); 	gp.setVgap(15);
		gp.setPadding(new Insets(20));
		
		gp.add(lbl1, 0, 0);
		
		getPredmets();
		comboP.getSelectionModel().select(0);
		
		getIspit();
		comboI.getSelectionModel().select(0);
		comboI.setPromptText("Izaberite ispit");
		
		gp.add(comboP, 1, 0);
		
		gp.add(lbl2, 0, 1);
		gp.add(comboI, 1, 1);
		
		gp.add(lbl5, 0, 2);
		
		gp.add(lbl6, 0, 3);
		gp.add(brBod, 1, 3);
		
		gp.add(lbl3, 0, 4);
		gp.add(ocena, 1, 4);
		
		gp.add(lbl4, 0, 5);
		gp.add(datum, 1, 5);
		
		gp.add(new Label("|"), 2, 0);
		gp.add(new Label("|"), 2, 1);
		gp.add(new Label("|"), 2, 2);
		gp.add(new Label("|"), 2, 3);
		gp.add(new Label("|"), 2, 4);
		gp.add(new Label("|"), 2, 5);
		
		gp.add(potvrdi, 3, 0);
		gp.add(azz, 3, 3);
		gp.add(zatvori, 3, 5);
		
		return gp;
	}
	
	private void getPredmets() {
		
		List<Predmet> pred = Controller.getSubjects();
		
		ObservableList<Predmet> predmeti = comboP.getItems();
		
		pred.forEach(e -> {
			predmeti.add(e);
		});
	
	}
	
	private void getIspit() {
		
		Predmet p = comboP.getSelectionModel().getSelectedItem();
		
		List<Ispit> lista = Controller.getExams(p);
		
		ObservableList<Ispit> obaveze = comboI.getItems();
		obaveze.clear();
		
		lista.forEach(e -> {
			obaveze.add(e);
		});
	
	}
	
	private void upis(ActionEvent event) {
		
		if(provera()) {
			
			Alert a = new Alert(Alert.AlertType.CONFIRMATION);
			a.setTitle("A�uriranje");
			a.setHeaderText("Uneti podaci bi�e a�urirani. Potvr�ujem?");
			a.initModality(Modality.APPLICATION_MODAL);
		
			Optional<ButtonType> btn = a.showAndWait();
		
			if(btn.isPresent() && btn.get() == ButtonType.OK) {
				
				upisi();
			}
			
		}
		
	}
	
	private boolean provera() {
		
		if("".equals(brBod.getText()) && "".equals(ocena.getText())) {
			return false;
		}
		
		return true;
	}
	
	private void upisi() {
		
		double bod = 0;
		int ocenaf = 0;
		
		Ispit i = comboI.getSelectionModel().getSelectedItem();
		
		double ukupnoBod = i.getUkupnoBodova();
		
		if(!"".equals(brBod.getText())) {
			
			try {
				
				bod = Double.parseDouble(brBod.getText());
				
			} catch(NumberFormatException nfe) {
				Alert a = new Alert(Alert.AlertType.ERROR);
				a.setTitle("Gre�ka");
				a.setHeaderText("Bodovi moraju biti numeri�ki podatak");
				a.initModality(Modality.APPLICATION_MODAL);
				a.showAndWait();
				return;
			}
		} 
		
		if(!"".equals(ocena.getText())) {
			
			try {
				
				ocenaf = Integer.parseInt(ocena.getText());
				
			} catch(NumberFormatException nfe) {
				Alert a = new Alert(Alert.AlertType.ERROR);
				a.setTitle("Gre�ka");
				a.setHeaderText("Ocena mora biti numeri�ki podatak");
				a.initModality(Modality.APPLICATION_MODAL);
				a.showAndWait();
				return;
			}
		}
		
		if(bod != 0) {
			
			double temp = i.getBrBodova();
			double noviB = (ukupnoBod - temp) + bod;
			int ocenaS = i.getOcena();
			
			if((ocenaS != ocenaf) && ocenaf != 0)
				ocenaS = ocenaf;
			
			LocalDate ld = datum.getValue();
			
			if(ld == null) {
				
				if(Controller.updateExam(i, bod, noviB, ocenaS)) {
					
					Alert ale = new Alert(Alert.AlertType.INFORMATION);
					ale.setTitle("A�uriranje");
					ale.setHeaderText("Uspe�no ste a�urirali ispit!");
					ale.initModality(Modality.APPLICATION_MODAL);
					ale.showAndWait();
					pozornica.close();
					return;
					
				} else {
					Alert al = new Alert(Alert.AlertType.ERROR);
					al.setTitle("Gre�ka");
					al.setHeaderText("Do�lo je do gre�ke prilikom a�uriranja!");
					al.initModality(Modality.APPLICATION_MODAL);
					al.showAndWait();
					return;
				}
			} else {
				
				Date date = Date.from(ld.atStartOfDay(ZoneId.systemDefault()).toInstant());
				
				if(Controller.updateExam(i, bod, noviB, ocenaS, date)) {
					
					Alert ale = new Alert(Alert.AlertType.INFORMATION);
					ale.setTitle("A�uriranje");
					ale.setHeaderText("Uspe�no ste a�urirali ispit!");
					ale.initModality(Modality.APPLICATION_MODAL);
					ale.showAndWait();
					pozornica.close();
					return;
					
				} else {
					Alert al = new Alert(Alert.AlertType.ERROR);
					al.setTitle("Gre�ka");
					al.setHeaderText("Do�lo je do gre�ke prilikom a�uriranja!");
					al.initModality(Modality.APPLICATION_MODAL);
					al.showAndWait();
					return;
				}
			}
			
		} 
	}
	
	
	private void postaviVelicinu() {
		
		potvrdi.setPrefSize(200, 30);
		zatvori.setPrefSize(200, 30);
		azz.setPrefSize(230, 30);
		
		potvrdi.setStyle("-fx-background-color: #4a53ff; "
					   + "-fx-cursor: hand;"
				       + "-fx-border-style: solid;"
				       + "-fx-color: blue;"
				       + "-fx-border-color: yellow;");
		
		azz.setStyle("-fx-background-color: #4a53ff; "
				   + "-fx-cursor: hand;"
			       + "-fx-border-style: solid;"
			       + "-fx-color: blue;"
			       + "-fx-border-color: yellow;");
		
		zatvori.setStyle("-fx-cursor: hand;"
					   + "-fx-background-color: lightblue;"
					   + "-fx-border-style: solid;");
		
		comboP.setStyle("-fx-cursor: hand;");
		comboI.setStyle("-fx-cursor: hand;");
		datum.setStyle("-fx-cursor: hand;");
		
		lbl1.setStyle("-fx-text-fill: black;"
				    + "-fx-font-weight: bold;");
	
		lbl2.setStyle("-fx-text-fill: black;"
			        + "-fx-font-weight: bold;");
	
		lbl3.setStyle("-fx-text-fill: black;"
			        + "-fx-font-weight: bold;");
	
		lbl4.setStyle("-fx-text-fill: black;"
			        + "-fx-font-weight: bold;");
		
		lbl6.setStyle("-fx-text-fill: black;"
		        + "-fx-font-weight: bold;");
		
		lbl5.setStyle("-fx-text-fill: red;"
		        + "-fx-font-weight: bold;");
	}
	
	private void zatvaranje(ActionEvent event) {
		
		Alert a = new Alert(Alert.AlertType.CONFIRMATION);
		a.setTitle("Napu�tate prozor");
		a.setHeaderText("Sigurno napu�tate aplikaciju?");
		a.initModality(Modality.APPLICATION_MODAL);
		a.initStyle(StageStyle.UNDECORATED);
		Optional<ButtonType> btn = a.showAndWait();
		
		if(btn.isPresent() && btn.get() == ButtonType.OK) {
			a.close();
			pozornica.close();
		} else {
			a.close();
			return;
		}
	}
	
}
