package br.com.superdia.desktop.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.superdia.desktop.model.Produto;

public class CaixaService {
	private static final String BASE_URL = "http://localhost:8080/SuperDiaWeb/api/produtos";
	private final HttpClient httpClient;
	private final ObjectMapper objectMapper;

	public CaixaService() {
		this.httpClient = HttpClient.newHttpClient();
		this.objectMapper = new ObjectMapper();
	}
	
	public Produto obterProduto(String codigoProduto, Integer quantidade) {
		try {
			HttpRequest request = HttpRequest.newBuilder()
	                .uri(URI.create(BASE_URL + "/" + Long.valueOf(codigoProduto)))
	                .header("Accept", "application/json") 
	                .GET()
	                .build();
			
			HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
			
			if (response.statusCode() == 200) {
				JsonNode data = objectMapper.readTree(response.body()).path("data");
				
	            return new Produto(data.path("nome").asText(), quantidade, data.path("preco").asDouble());
			}
			
			return null;
		
		} catch (Exception e) {
			System.out.println("Erro ao tentar fazer requisição");
			return null;
		}
	}
}
