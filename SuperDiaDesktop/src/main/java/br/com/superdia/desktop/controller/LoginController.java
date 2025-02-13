package br.com.superdia.desktop.controller;

import br.com.superdia.desktop.application.CaixaApplication;
import br.com.superdia.desktop.auth.AuthenticationManager;
import br.com.superdia.desktop.auth.AuthenticationService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {
    @FXML private TextField cpfField;
    @FXML private PasswordField passwordField;
    @FXML private Label messageLabel;

    private final AuthenticationService authService = new AuthenticationService();
    private final AuthenticationManager authManager = AuthenticationManager.getInstance();

    @FXML
    private void handleLogin() {
        String username = cpfField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            messageLabel.setText("Por favor, preencha todos os campos");
            return;
        }

        setInputsDisabled(true);
        messageLabel.setText("Autenticando...");

        new Thread(() -> {
            boolean isAuthenticated = authService.authenticate(username, password);
            
            Platform.runLater(() -> {
                if (isAuthenticated) {
                    authManager.setAuthenticated(true);
                    openCaixaApplication();
                } else {
                    messageLabel.setText("Credenciais inválidas!");
                    setInputsDisabled(false);
                }
            });
        }).start();
    }

    private void setInputsDisabled(boolean disabled) {
        cpfField.setDisable(disabled);
        passwordField.setDisable(disabled);
    }

    private void openCaixaApplication() {
        try {
            Stage caixaStage = new Stage();
            new CaixaApplication().start(caixaStage);
            ((Stage) cpfField.getScene().getWindow()).close();
        } catch (Exception e) {
            e.printStackTrace();
            messageLabel.setText("Erro ao abrir a aplicação do caixa.");
            setInputsDisabled(false);
        }
    }
}

