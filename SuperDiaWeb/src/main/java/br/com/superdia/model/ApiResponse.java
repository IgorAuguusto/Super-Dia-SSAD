package br.com.superdia.model;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

public class ApiResponse<T> {
    private int status; // Código HTTP do status
    private T data;     // Dados retornados (pode ser um objeto, uma lista, etc.)
    private String message; // Mensagem descritiva 
    
    public ApiResponse(int status, T data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }
    
    public ApiResponse(int status, String message) {
        this.status = status;
        this.data = null;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    public static Response createResponse(Status status, Object entity) {
		if(entity == null) {
			return Response.status(status).build();
		}
		return Response.status(status).entity(entity).build();
	}
}