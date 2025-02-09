package br.com.superdia.controllers;

import java.io.Serializable;
import java.util.List;

import br.com.superdia.interfaces.IPessoa;
import br.com.superdia.model.ApiResponse;
import br.com.superdia.model.CrudOperations;
import br.com.superdia.modelo.Pessoa;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Named
@SessionScoped
@Path("/api/pessoas")
public class PessoaController implements CrudOperations<Pessoa>, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@EJB
	private IPessoa pessoaBean;
	
	@Override
	public Response getAll() {
		List<Pessoa> pessoas = pessoaBean.getPessoas();
		return createResponse(Status.OK, new ApiResponse<>(Status.OK.getStatusCode(), pessoas, "OK"));
	}

	@Override
	public Response getById(@PathParam("id") Long id) {
		Pessoa pessoa = pessoaBean.getById(id);
		if(pessoa == null) {
			return createResponse(Status.NOT_FOUND, new ApiResponse<>(Status.NOT_FOUND.getStatusCode(), "Pessoa não encontrada"));
		}
		return createResponse(Status.OK, new ApiResponse<>(Status.OK.getStatusCode(), pessoa, "OK"));
	}

	@Override
	public Response update(Pessoa pessoa) {
		pessoaBean.altera(pessoa);
		return createResponse(Status.OK, new ApiResponse<>(Status.OK.getStatusCode(), pessoa, "OK"));
	}

	@Override
	public Response create(Pessoa pessoa) {
		pessoaBean.adiciona(pessoa);
		return createResponse(Status.CREATED, new ApiResponse<>(Status.CREATED.getStatusCode(), pessoa, "Pessoa criada com sucesso"));
	}

	@Override
	public Response delete(Pessoa pessoa) {
		pessoaBean.remove(pessoa);
		return createResponse(Status.OK, new ApiResponse<>(Status.OK.getStatusCode(), pessoa, "Pessoa deletada com sucesso"));
	}

	@Override
	public Response deleteById(Long id) {
		Pessoa pessoa = pessoaBean.getById(id);
		if(pessoa == null) {
			return createResponse(Status.NOT_FOUND, new ApiResponse<>(Status.NOT_FOUND.getStatusCode(), "Pessoa não encontrada"));
		}
		pessoaBean.remove(pessoa);
		return createResponse(Status.OK, new ApiResponse<>(Status.OK.getStatusCode(), pessoa, "Pessoa deletada com sucesso"));
	}
	
	private Response createResponse(Status status, Object entity) {
		if(entity == null) {
			return Response.status(status).build();
		}
		return Response.status(status).entity(entity).build();
	}
}
