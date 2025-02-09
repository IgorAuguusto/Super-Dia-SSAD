package br.com.superdia.model;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public interface CrudOperations<T> {
	@GET
	@Path("/todos")
	@Produces(MediaType.APPLICATION_JSON)
	Response getAll();
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	Response getById(@PathParam("id") Long id);
	
	@PUT
	@Path("/alterar")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response update(T item);
	
	@POST
	@Path("/criar")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response create(T item);
	
	@DELETE
	@Path("/deletar")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response delete(T item);
	
	@DELETE
	@Path("/deletar/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	Response deleteById(@PathParam("id") Long id);
}
