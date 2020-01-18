package gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import slikeKlasa.Images;

public class Prikazi extends Application {
	
	private TableView<?> tabela = new TableView<>();
	private Label labela = new Label();
	
	@Override
	public void start(Stage stage) throws Exception {

		
		BorderPane bp = new BorderPane();
		bp.setTop(labela);
		bp.setCenter(tabela);
		BorderPane.setAlignment(labela, Pos.TOP_CENTER);
		
		Scene s = new Scene(bp);
		
		s.setOnKeyPressed(action -> {
			
			if(action.getCode() == KeyCode.ESCAPE) {
				stage.close();
			}
			
		});
		
		tabela.setOnKeyPressed(e -> {
			if(e.getCode() == KeyCode.ESCAPE) {
				stage.close();
			}
		});
		
		stage.getIcons().add(Images.getImagePozornica());
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setResizable(false);
		stage.setScene(s);
		stage.setTitle("Табела");
		stage.show();
		
	}
	
	public void setTabela(TableView<?> tabela) {
		
		tabela.autosize();
		this.tabela = tabela;
	}
	
	public void setText(String text) {
		
		this.labela.setText(text);
		this.labela.setStyle("-fx-font-weight: bold;"
					       + "-fx-text-fill: red;"
					       + "-fx-font-size: 16;");
		
		this.labela.setPadding(new Insets(10));

	}

}
