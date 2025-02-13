package br.com.superdia.controllers;

import java.io.Serializable;
import java.util.List;

import br.com.superdia.interfaces.IUsuario;
import br.com.superdia.model.ApiResponse;
import br.com.superdia.model.CrudOperations;
import br.com.superdia.model.LoginRequest;
import br.com.superdia.modelo.Usuario;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Named
@SessionScoped
@Path("/api/usuarios")
public class UsuarioController implements CrudOperations<Usuario>, Serializable {
	private static final long serialVersionUID = 1L;
	
	@EJB
	private IUsuario usuarioBean;
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(LoginRequest loginRequest) {
		
		Usuario usuario = usuarioBean.logar(loginRequest.getLogin(), loginRequest.getSenha());
		if(usuario == null) {
			return createResponse(Status.BAD_REQUEST, new ApiResponse<>(Status.BAD_REQUEST.getStatusCode(), "Falha ao realizar login"));
		}
		return createResponse(Status.OK, new ApiResponse<>(Status.OK.getStatusCode(), usuario, "OK"));
	}
	
	@GET
	@Path("/obter")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getByIdentification(@QueryParam("identification") String identification) {
		Usuario usuario = usuarioBean.getByIdentification(identification);
		if(usuario == null) {
			return createResponse(Status.BAD_REQUEST, new ApiResponse<>(Status.BAD_REQUEST.getStatusCode(), "Falha ao obter usuário com essa identificação"));
		}
		return createResponse(Status.OK, new ApiResponse<>(Status.OK.getStatusCode(), usuario, "OK"));
	}
	
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
			System.out.println(usuario);
			usuarioBean.adiciona(usuario);
		} catch (ConstraintViolationException e) {
			if(e.getMessage().contains("email")) {
				return createResponse(Status.BAD_REQUEST, new ApiResponse<>(Status.BAD_REQUEST.getStatusCode(), "Esse email já está em uso"));
			} else if(e.getMessage().contains("CPF")) {
				return createResponse(Status.BAD_REQUEST, new ApiResponse<>(Status.BAD_REQUEST.getStatusCode(), "Esse cpf já está em uso")); 
			} else {
				return createResponse(Status.BAD_REQUEST, new ApiResponse<>(Status.BAD_REQUEST.getStatusCode(), "Houve um erro")); 
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
		return this.delete(usuario);
	}
	
	private Response createResponse(Status status, Object entity) {
		if(entity == null) {
			return Response.status(status).build();
		}
		return Response.status(status).entity(entity).build();
	}
	
}
