package br.com.superdia.desktop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class CaixaApplication extends Application {

	@Override
	public void start(Stage primaryStage) throws IOException {
	    String fxmlPath = "/br/com/superdia/desktop/CaixaView.fxml";
	    URL resource = CaixaApplication.class.getResource(fxmlPath);
	    if (resource == null) {
	        System.err.println("FXML file not found: " + fxmlPath);
	        return;
	    }
	    FXMLLoader fxmlLoader = new FXMLLoader(resource);
	    Parent root = fxmlLoader.load();
	    primaryStage.setTitle("SuperDia - Caixa");
	    primaryStage.setScene(new Scene(root, 600, 400));
	    primaryStage.show();
	}

    public static void main(String[] args) {
        launch(args);
    }
}

