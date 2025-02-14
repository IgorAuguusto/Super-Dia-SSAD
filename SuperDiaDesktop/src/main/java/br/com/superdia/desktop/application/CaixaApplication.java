package br.com.superdia.desktop.application;

import java.io.IOException;
import java.net.URL;

import br.com.superdia.desktop.auth.AuthenticationManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CaixaApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        if (!AuthenticationManager.getInstance().isAuthenticated()) {
            throw new SecurityException("Acesso não autorizado! Faça login primeiro.");
        }

        String fxmlPath = "/br/com/superdia/desktop/CaixaView.fxml";
 	    URL resource = LoginApplication.class.getResource(fxmlPath);
 	    if (resource == null) {
 	        System.err.println("FXML file not found: " + fxmlPath);
 	        return;
 	    }
 	    FXMLLoader fxmlLoader = new FXMLLoader(resource);
        Parent root = fxmlLoader.load();
        primaryStage.setTitle("SuperDia - Caixa");
        primaryStage.setScene(new Scene(root, 600, 400));

        // Add handler for when the window is closed
        primaryStage.setOnCloseRequest(event -> {
            AuthenticationManager.getInstance().logout();
        });

        primaryStage.show();
    }

}


