package br.com.superdia.desktop.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.superdia.desktop.model.Data;
import br.com.superdia.desktop.model.Perfil;
import br.com.superdia.desktop.model.Pessoa;
import br.com.superdia.desktop.model.Produto;
import br.com.superdia.desktop.model.Usuario;

public class CaixaService {
	private static final String BASE_URL = "http://localhost:8080/SuperDiaWeb/api";
	private final HttpClient httpClient;
	private final ObjectMapper objectMapper;

	public CaixaService() {
		this.httpClient = HttpClient.newHttpClient();
		this.objectMapper = new ObjectMapper();
	}
	
	public Produto obterProduto(String codigoProduto, Integer quantidade) {
		try {
			HttpRequest request = HttpRequest.newBuilder()
	                .uri(URI.create(BASE_URL + "/" + "produtos/" + Long.valueOf(codigoProduto)))
	                .header("Accept", "application/json") 
	                .GET()
	                .build();
			
			HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

			if (response.statusCode() == 200) {
				JsonNode data = objectMapper.readTree(response.body()).path("data");
				
				if (data.path("quantidade").asInt() > quantidade)
					return null;
				
				Produto produto = new Produto();
				produto.setNome(data.path("nome").asText());
				produto.setId(data.path("id").asLong());
				produto.setQuantidadeEstoque(quantidade);
				produto.setPreco(data.path("preco").asDouble());
				
	            return produto;
			}
			
			return null;
		
		} catch (Exception e) {
			System.out.println("Erro ao tentar fazer requisição");
			return null;
		}
	}
	
	public Boolean finalizarCompra(Double precoTotal, String cpf, String numeroCartao, List<Produto> listaProdutos) {
		try {
			List<Produto> produtosComprados = new ArrayList<>();
			
			for (Produto produto : listaProdutos) {
				HttpRequest request = HttpRequest.newBuilder()
		                .uri(URI.create(BASE_URL + "/" + "produtos/" + Long.valueOf(produto.getId())))
		                .header("Accept", "application/json") 
		                .GET()
		                .build();
				
				HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
				JsonNode data = objectMapper.readTree(response.body()).path("data");
				produtosComprados.add(objectMapper.treeToValue(data, Produto.class));
			}
			System.out.println(cpf);
			HttpRequest request = HttpRequest.newBuilder()
	                .uri(URI.create(BASE_URL + "/" + "usuarios/obter?identification=" + cpf.trim()))
	                .header("Accept", "application/json") 
	                .GET()
	                .build();
			
			HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
			JsonNode data = objectMapper.readTree(response.body()).path("data");
			JsonNode nodePessoa = data.path("pessoa");
			
			Pessoa pessoa = new Pessoa();
			pessoa.setCpf(nodePessoa.path("cpf").asText());
			pessoa.setDataNascimento(nodePessoa.path("dataNascimento").asText());
			pessoa.setEmail(nodePessoa.path("email").asText());
			pessoa.setEndereco(nodePessoa.path("endereco").asText());
			pessoa.setId(nodePessoa.path("id").asLong());
			pessoa.setNome(nodePessoa.path("nome").asText());
			pessoa.setTelefone(nodePessoa.path("telefone").asText());
			
			Usuario usuario = new Usuario();
			usuario.setPessoa(pessoa);
			usuario.setId(data.path("id").asLong());
			usuario.setPerfil(Perfil.converterStringParaPerfil(data.path("perfil").asText()));
			usuario.setPerfilStr(data.path("perfilStr").asText());
			usuario.setSenha(data.path("senha").asText());
			
			Data dataJson = new Data();
			dataJson.setCartao(numeroCartao);
			dataJson.setProdutos(produtosComprados);
			dataJson.setUsuario(usuario);
			
			String jsonString = objectMapper.writeValueAsString(data);
			
			HttpRequest requestVendas = HttpRequest.newBuilder()
	                .uri(URI.create(BASE_URL + "/" + "vendas/criar"))
	                .header("Accept", "application/json") 
	                .POST(HttpRequest.BodyPublishers.ofString(jsonString))
	                .build();
			
			return true;
		
		} catch (Exception e) {
			System.out.println("Erro ao tentar fazer requisição");
			return false;
		}
	}
}
