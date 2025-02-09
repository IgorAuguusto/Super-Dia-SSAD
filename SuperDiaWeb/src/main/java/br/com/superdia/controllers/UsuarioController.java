package br.com.superdia.controllers;

import java.io.Serializable;
import java.util.List;

import br.com.superdia.interfaces.IUsuario;
import br.com.superdia.model.ApiResponse;
import br.com.superdia.model.CrudOperations;
import br.com.superdia.modelo.Usuario;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Named
@SessionScoped
@Path("/api/usuarios")
public class UsuarioController implements CrudOperations<Usuario>, Serializable {
	private static final long serialVersionUID = 1L;
	
	@EJB
	private IUsuario usuarioBean;
	
	@Override
	public Response getAll() {
		List<Usuario> usuarios = usuarioBean.getUsuarios();
		return createResponse(Status.OK, new ApiResponse<>(Status.OK.getStatusCode(), usuarios, "OK"));
	}
	
	@Override
	public Response getById(@PathParam("id") Long id) {
		Usuario usuario = usuarioBean.getById(id);
		if(usuario == null) {
			return createResponse(Status.NOT_FOUND, new ApiResponse<>(Status.NOT_FOUND.getStatusCode(), "Usuário não encontrado"));
		}
		return createResponse(Status.OK, new ApiResponse<>(Status.OK.getStatusCode(), usuario, "OK"));
	}
	
	@Override
	public Response update(Usuario usuario) {
		usuarioBean.altera(usuario);
		return createResponse(Status.OK, new ApiResponse<>(Status.OK.getStatusCode(), usuario, "OK"));
	}
	
	@Override
	public Response create(Usuario usuario) {
		try {
			usuarioBean.adiciona(usuario);
		} catch (ConstraintViolationException e) {
			if(e.getMessage().contains("email")) {
				return createResponse(Status.BAD_REQUEST, new ApiResponse<>(Status.BAD_REQUEST.getStatusCode(), "email já está em uso"));
			} else if(e.getMessage().contains("CPF")) {
				return createResponse(Status.BAD_REQUEST, new ApiResponse<>(Status.BAD_REQUEST.getStatusCode(), "cpf já cadastrado")); 
			} else {
				return createResponse(Status.BAD_REQUEST, new ApiResponse<>(Status.BAD_REQUEST.getStatusCode(), "houve um erro")); 
			}
		}
		return createResponse(Status.CREATED, new ApiResponse<>(Status.CREATED.getStatusCode(), usuario, "Usuário criado com sucesso"));
		
	}
	
	@Override
	public Response delete(Usuario usuario) {
		usuarioBean.remove(usuario);
		return createResponse(Status.OK, new ApiResponse<>(Status.OK.getStatusCode(), usuario, "Usuário deletado com sucesso"));
	}
	
	@Override
	public Response deleteById(Long id) {
		Usuario usuario = usuarioBean.getById(id);
		if(usuario == null) {
			return createResponse(Status.NOT_FOUND, new ApiResponse<>(Status.NOT_FOUND.getStatusCode(), "Usuário não encontrado"));
		}
		usuarioBean.remove(usuario);
		return createResponse(Status.OK, new ApiResponse<>(Status.OK.getStatusCode(), usuario, "Usuário deletado com sucesso"));
	}
	
	private Response createResponse(Status status, Object entity) {
		if(entity == null) {
			return Response.status(status).build();
		}
		return Response.status(status).entity(entity).build();
	}
	
}
