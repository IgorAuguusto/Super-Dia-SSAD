package br.com.superdia.desktop.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class LoginApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
    	String fxmlPath = "/br/com/superdia/desktop/LoginView.fxml";
 	    URL resource = LoginApplication.class.getResource(fxmlPath);
 	    if (resource == null) {
 	        System.err.println("FXML file not found: " + fxmlPath);
 	        return;
 	    }
 	    FXMLLoader fxmlLoader = new FXMLLoader(resource);
        Parent root = fxmlLoader.load();
        primaryStage.setTitle("SuperDia - Login");
        primaryStage.setScene(new Scene(root, 300, 200));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

