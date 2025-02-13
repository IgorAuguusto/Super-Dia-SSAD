package br.com.superdia.desktop.auth;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AuthenticationService {
    private static final String BASE_URL = "http://localhost:8080/SuperDiaWeb/api";
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public AuthenticationService() {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    public boolean authenticate(String login, String senha) {
        try {
            LoginRequest loginRequest = new LoginRequest(login, senha);
            String requestBody = objectMapper.writeValueAsString(loginRequest);

            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/usuarios/login"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            JsonNode data = objectMapper.readTree(response.body()).path("data");
            String perfil = data.path("perfil").asText();
            
            if (perfil.equalsIgnoreCase("CAIXA"))
            	return true;

            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static class LoginRequest {
        public String login;
        public String senha;

        public LoginRequest(String login, String senha) {
            this.login = login;
            this.senha = senha;
        }
    }
}

