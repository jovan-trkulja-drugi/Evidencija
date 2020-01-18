package alerts;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import slikeKlasa.Images;

public class Alerts {
	
	private static Alert err;
	private static Alert info;
	private static Alert conf;
	private static Alert warning;
	
	private static ButtonType da;
	private static ButtonType ne;
	
	public static void showExitAlert() {
		
		conf = new Alert(Alert.AlertType.CONFIRMATION);
		conf.initModality(Modality.APPLICATION_MODAL);
		
		conf.setTitle("Излаз");
		conf.setHeaderText("Сигурно затварате апликацију");
		
		((Stage) conf.getDialogPane()
				     .getScene()
				     .getWindow())
		             .getIcons()
		             .add(Images.getImagePozornica());
		
		Optional<ButtonType> btn = conf.showAndWait();

		if (btn.isPresent() && btn.get() == ButtonType.OK) {
			conf.close();
			System.exit(0);
		} else {
			conf.close();
			return;
		}
		
	}
	
	public static void showErrorAlert(String msg) {
		
		err = new Alert(Alert.AlertType.ERROR);
		err.initModality(Modality.APPLICATION_MODAL);
		
		err.setTitle("Грешка");
		err.setHeaderText(msg);
		
		
		((Stage) err.getDialogPane()
				    .getScene()
                    .getWindow())
		            .getIcons()
		            .add(Images.getImagePozornica());
		
		err.showAndWait();
		
		
	}
	
	public static void showInformationAlert(String msg, boolean wait) {
		
		info =  new Alert(Alert.AlertType.INFORMATION);
		info.initModality(Modality.APPLICATION_MODAL);
		
		info.setTitle("Обавештење");
		info.setHeaderText(msg);
		
		((Stage) info.getDialogPane()
				     .getScene()
				     .getWindow())
		             .getIcons()
		             .add(Images.getImagePozornica());
		
		if(wait)
			info.showAndWait();
		else {
			info.getDialogPane().lookupButton(ButtonType.OK).setVisible(false);
			info.show();
		}
		
		
	}
	
	public static void showInformationAlert(String header, String msg, boolean wait) {
		
		info =  new Alert(Alert.AlertType.INFORMATION);
		info.initModality(Modality.APPLICATION_MODAL);
		
		info.setTitle("Обавештење");
		info.setHeaderText(header);
		info.setContentText(msg);
		
		((Stage) info.getDialogPane()
				     .getScene()
				     .getWindow())
		             .getIcons()
		             .add(Images.getImagePozornica());
		
		if(wait) 
			info.showAndWait();
		
		else {
			info.getDialogPane().lookupButton(ButtonType.OK).setVisible(false);
			info.show();
		}
		
		
		
	}
	
	public static boolean showConfrimationAlert(String header, String msg) {
		
		conf = new Alert(Alert.AlertType.CONFIRMATION);
		conf.initModality(Modality.APPLICATION_MODAL);
		
		conf.setTitle("Обавештење");
		conf.setHeaderText(header);
		conf.setContentText(msg);
		
		((Stage) conf.getDialogPane()
				     .getScene()
				     .getWindow())
					 .getIcons()
					 .add(Images.getImagePozornica());
	
		Optional<ButtonType> btn = conf.showAndWait();
	
		if(btn.isPresent() && btn.get() == ButtonType.OK) {
			
			return true;
			
		} else {
			
			return false;
		}
		
	}
	
	public static boolean showWarningAlert(String msg) {
		
		warning = new Alert(Alert.AlertType.WARNING);
		warning.initModality(Modality.APPLICATION_MODAL);
		
		da = new ButtonType("Да", ButtonBar.ButtonData.OK_DONE);
		ne = new ButtonType("Не", ButtonBar.ButtonData.CANCEL_CLOSE);
		
		warning.setTitle("Упозорење");
		warning.setHeaderText(msg);
		
		warning.getDialogPane().lookupButton(ButtonType.OK).setVisible(false);
		
		((Stage) warning.getDialogPane()
				        .getScene()
				        .getWindow())
		                .getIcons()
		                .add(Images.getImagePozornica());
		
		warning.getButtonTypes().add(da);
		warning.getButtonTypes().add(ne);

		Optional<ButtonType> btn = warning.showAndWait();
		
		if(btn.isPresent() && btn.get() == da) {
			warning = null;
			return true;
		} else {
			Alerts.closeDialog("warning");
			
			return false;
		}
	}
	
	public static void closeDialog(String type) {
		
		switch(type) {
		
			case "err":
				err.close();
				break;
				
			case "info":
				info.close();
				break;
			
			case "conf":
				conf.close();
				break;
			
			case "warning":
				warning.close();
				break;
				
			default:
				throw new RuntimeException("Грешка упит");
		}
	}
	
	

}
