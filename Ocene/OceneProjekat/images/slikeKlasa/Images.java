package slikeKlasa;

import java.io.File;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;

public class Images {
	
	public static ImageView getImageZatvori() {
		
		File f = new File("/home/jovan/Desktop/EclipseWorkspace/Ocene/OceneProjekat/images/close-icon.png");
		Image img = new Image(f.toURI().toString());
		ImageView imgView = new ImageView(img);
		imgView.setFitHeight(25);
		imgView.setFitWidth(25);
		
		return imgView;
	}
	
	public static ImageView getImagePregled() {
		
		File f = new File("/home/jovan/Desktop/EclipseWorkspace/Ocene/OceneProjekat/images/database.png");
		Image img = new Image(f.toURI().toString());
		ImageView imgView = new ImageView(img);
		imgView.setFitHeight(25);
		imgView.setFitWidth(25);
		
		return imgView;
	}
	
	public static ImageView getImageBodovi() {
		
		File f = new File("/home/jovan/Desktop/EclipseWorkspace/Ocene/OceneProjekat/images/exam.png");
		Image img = new Image(f.toURI().toString());
		ImageView imgView = new ImageView(img);
		imgView.setFitHeight(25);
		imgView.setFitWidth(25);
		
		return imgView;
	}
	
	public static ImageView getImagePredmet() {
		
		File f = new File("/home/jovan/Desktop/EclipseWorkspace/Ocene/OceneProjekat/images/subject.png");
		Image img = new Image(f.toURI().toString());
		ImageView imgView = new ImageView(img);
		imgView.setFitHeight(25);
		imgView.setFitWidth(25);
		
		return imgView;
	}
	
	public static ImageView getImagePrint() {
		
		File f = new File("/home/jovan/Desktop/EclipseWorkspace/Ocene/OceneProjekat/images/print.png");
		Image img = new Image(f.toURI().toString());
		ImageView imgView = new ImageView(img);
		imgView.setFitHeight(25);
		imgView.setFitWidth(30);
		
		return imgView;
		
	}
	
	public static ImageView getImageConnect() {
		
		File f = new File("/home/jovan/Desktop/EclipseWorkspace/Ocene/OceneProjekat/images/connect.jpg");
		Image img = new Image(f.toURI().toString());
		ImageView imgView = new ImageView(img);
		imgView.setFitHeight(25);
		imgView.setFitWidth(30);
		
		return imgView;
		
	}
	
	public static ImageView getImageUpdate() {
		
		File f = new File("/home/jovan/Desktop/EclipseWorkspace/Ocene/OceneProjekat/images/update.png");
		Image img = new Image(f.toURI().toString());
		ImageView imgView = new ImageView(img);
		imgView.setFitHeight(25);
		imgView.setFitWidth(30);
		
		return imgView;
	}
	
	public static Background getBackground() {
		
		//File f = new File("background.jpg");
		File f = new File("/home/jovan/Desktop/EclipseWorkspace/Ocene/OceneProjekat/images/background.jpg");
		
		Image img = new Image(f.toURI().toString());
		
		BackgroundImage bi = new BackgroundImage(img,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
		
		Background bcg = new Background(bi);
		
		return bcg;
	}
	
	public static Background getBackgroundPredmet() {
		
		//File f = new File("background.jpg");
		File f = new File("/home/jovan/Desktop/EclipseWorkspace/Ocene/OceneProjekat/images/background2.jpg");
		
		Image img = new Image(f.toURI().toString());
		
		BackgroundImage bi = new BackgroundImage(img,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
		
		Background bcg = new Background(bi);
		
		return bcg;
	}
	
	public static Image getImagePozornica() {
		
		File f = new File("/home/jovan/Desktop/EclipseWorkspace/Ocene/OceneProjekat/images/app-icon.png");
		Image img = new Image(f.toURI().toString());
		
		return img;
	}
	
	public static Background getBackgroundStatistika() {
		
		File f = new File("/home/jovan/Desktop/EclipseWorkspace/Ocene/OceneProjekat/images/background3.jpg");
		Image img = new Image(f.toURI().toString());
		
		BackgroundImage bi = new BackgroundImage(img,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
		
		Background bcg = new Background(bi);
		
		return bcg;
	}
	
	public static Background getBackgroundKLKIspit() {
		
		//File f = new File("background.jpg");
		File f = new File("/home/jovan/Desktop/EclipseWorkspace/Ocene/OceneProjekat/images/pozadinaPO.png");
		
		Image img = new Image(f.toURI().toString());
		
		BackgroundImage bi = new BackgroundImage(img,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
		
		Background bcg = new Background(bi);
		
		return bcg;
	}
	
	public static ImageView getImageUnesi() {
		
		File f = new File("/home/jovan/Desktop/EclipseWorkspace/Ocene/OceneProjekat/images/checkmark.png");
		Image img = new Image(f.toURI().toString());
		ImageView imgView = new ImageView(img);
		
		return imgView;
	}
	
	public static ImageView getImageObrisi() {
		
		File f = new File("/home/jovan/Desktop/EclipseWorkspace/Ocene/OceneProjekat/images/delete.png");
		Image img = new Image(f.toURI().toString());
		ImageView imgView = new ImageView(img);
		imgView.setFitHeight(25);
		imgView.setFitWidth(25);
		
		return imgView;
	}

}
