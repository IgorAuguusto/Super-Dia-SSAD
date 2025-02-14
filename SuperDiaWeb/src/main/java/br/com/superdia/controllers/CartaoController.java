package br.com.superdia.controllers;

import br.com.superdia.model.CartaoRequest;
import br.com.superdia.model.CartaoResponse;
import br.com.superdia.valida.ValidaCartao;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api")
public class CartaoController {
    
    @POST
    @Path("/valida-cartao")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response validarCartao(CartaoRequest request) {
        int resultCode = ValidaCartao.validateCreditCard(
            request.getNumeroCartao(), 
            request.getDataExpiracao()
        );
        
        boolean isValid = (resultCode == 0);
        CartaoResponse response = new CartaoResponse(isValid, resultCode);
        return Response.ok(response).build();
    }
}