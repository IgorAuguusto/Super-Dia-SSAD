package br.com.superdia.controllers;

import java.io.Serializable;
import java.util.List;

import br.com.superdia.interfaces.IVenda;
import br.com.superdia.model.ApiResponse;
import br.com.superdia.model.CrudOperations;
import br.com.superdia.modelo.Venda;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Named
@SessionScoped
@Path("/api/vendas")
public class VendaController implements CrudOperations<Venda>, Serializable {
	private static final long serialVersionUID = 1L;
	
	@EJB
	private IVenda vendaBean;

	@Override
	public Response getAll() {
		List<Venda> vendas = vendaBean.getVendas();
		return createResponse(Status.OK, new ApiResponse<>(Status.OK.getStatusCode(), vendas, "OK"));
	}
	
	@GET
	@Path("/usuario/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getVendasByUserId(@PathParam("id") Long id) {
		List<Venda> vendasUsuario = vendaBean.getVendasByUserId(id);
		if(vendasUsuario == null) {
			return createResponse(Status.NOT_FOUND, new ApiResponse<>(Status.NOT_FOUND.getStatusCode(), "Nenhum usuário encontrado"));
		}
		return createResponse(Status.OK, new ApiResponse<>(Status.OK.getStatusCode(), vendasUsuario, "OK"));
	}

	@Override
	public Response getById(Long id) {
		Venda venda = vendaBean.getById(id);
		if(venda == null) {
			return createResponse(Status.NOT_FOUND, new ApiResponse<>(Status.NOT_FOUND.getStatusCode(), "Venda não encontrada"));
		}
		return createResponse(Status.OK, new ApiResponse<>(Status.OK.getStatusCode(), venda, "OK"));
	}

	@Override
	public Response update(Venda venda) {
		vendaBean.altera(venda);
		return createResponse(Status.OK, new ApiResponse<>(Status.OK.getStatusCode(), venda, "OK"));
	}

	@Override
	public Response create(Venda venda) {
		vendaBean.adiciona(venda);
		return createResponse(Status.CREATED, new ApiResponse<>(Status.CREATED.getStatusCode(), venda, "Venda criada com sucesso"));
	}

	@Override
	public Response delete(Venda venda) {
		vendaBean.remove(venda);
		return createResponse(Status.OK, new ApiResponse<>(Status.OK.getStatusCode(), venda, "Venda deletada com sucesso"));
	}

	@Override
	public Response deleteById(Long id) {
		Venda venda = vendaBean.getById(id);
		if(venda == null) {
			return createResponse(Status.NOT_FOUND, new ApiResponse<>(Status.NOT_FOUND.getStatusCode(), "Venda não encontrada"));
		}
		return this.delete(venda);
	}
	
	private Response createResponse(Status status, Object entity) {
		if(entity == null) {
			return Response.status(status).build();
		}
		return Response.status(status).entity(entity).build();
	}
}
