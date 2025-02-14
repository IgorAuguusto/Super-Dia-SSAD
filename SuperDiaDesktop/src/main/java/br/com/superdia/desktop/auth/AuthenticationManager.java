package br.com.superdia.desktop.auth;

public class AuthenticationManager {
    private static AuthenticationManager instance;
    private boolean isAuthenticated = false;

    private AuthenticationManager() {}

    public static AuthenticationManager getInstance() {
        if (instance == null) {
            instance = new AuthenticationManager();
        }
        return instance;
    }

    public void setAuthenticated(boolean authenticated) {
        this.isAuthenticated = authenticated;
    }

    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    public void logout() {
        isAuthenticated = false;
    }
}