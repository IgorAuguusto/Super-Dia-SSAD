package br.com.superdia.controllers;

import java.io.Serializable;
import java.util.List;

import br.com.superdia.interfaces.IProduto;
import br.com.superdia.model.ApiResponse;
import br.com.superdia.model.CrudOperations;
import br.com.superdia.modelo.Produto;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Named
@SessionScoped
@Path("/api/produtos")
public class ProdutoController implements CrudOperations<Produto>, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@EJB
	private IProduto produtoBean;
	
	@Override
	public Response getAll() {
		List<Produto> produtos = produtoBean.getProdutos();
		return createResponse(Status.OK, new ApiResponse<List<Produto>>(Status.OK.getStatusCode(), produtos, "OK"));
	}
	
	@Override
	public Response getById(@PathParam("id") Long id) {
		Produto produto = produtoBean.getById(id);
		if(produto == null) {
			return createResponse(Status.NOT_FOUND, new ApiResponse<>(Status.NOT_FOUND.getStatusCode(), "Produto não encontrado"));
		}
		return createResponse(Status.OK, new ApiResponse<>(Status.OK.getStatusCode(), produto, "OK"));
	}
	
	@Override
	public Response update(Produto produto) {
		produtoBean.altera(produto);
		return createResponse(Status.OK, new ApiResponse<>(Status.OK.getStatusCode(), produto, "OK"));
	}
	
	@Override
	public Response create(Produto produto) {
		produtoBean.adiciona(produto);
		return createResponse(Status.CREATED, new ApiResponse<>(Status.CREATED.getStatusCode(), produto, "Produto criado com sucesso"));
	}
	
	@Override
	public Response delete(Produto produto) {
		produtoBean.remove(produto);
		return createResponse(Status.OK, new ApiResponse<>(Status.OK.getStatusCode(), produto, "Produto deletado com sucesso"));
	}
	
	@Override
	public Response deleteById(@PathParam("id") Long id) {
		Produto produto = produtoBean.getById(id);
		if(produto == null) {
			return createResponse(Status.NOT_FOUND, new ApiResponse<>(Status.NOT_FOUND.getStatusCode(), "Produto não encontrado"));
		}
		produtoBean.remove(produto);
		return createResponse(Status.OK, new ApiResponse<>(Status.OK.getStatusCode(), produto, "Produto deletado com sucesso"));
	}
	
	@GET
	@Path("/importar-produtos")
	@Produces(MediaType.APPLICATION_JSON)
	public Response importProducts(@QueryParam("url") String url) {
		if(url == null) {
			return createResponse(Status.BAD_REQUEST, new ApiResponse<>(Status.BAD_REQUEST.getStatusCode(), "Forneça a URL para obter os produtos"));
		}
		if(url.isEmpty()) {
			return createResponse(Status.BAD_REQUEST, new ApiResponse<>(Status.BAD_REQUEST.getStatusCode(), "Forneça a URL para obter os produtos"));
		}
		boolean isImportSuccessful = produtoBean.importarProdutos(url);
		
		if(isImportSuccessful) {
			return createResponse(Status.OK, new ApiResponse<>(Status.OK.getStatusCode(), "Importação de produtos foi bem sucedida"));
		}
		return createResponse(Status.BAD_REQUEST, new ApiResponse<>(Status.BAD_REQUEST.getStatusCode(), "Houve um erro ao importar os produtos"));
	}
	
	private Response createResponse(Status status, Object entity) {
		if(entity == null) {
			return Response.status(status).build();
		}
		return Response.status(status).entity(entity).build();
	}
	
	
	
}
