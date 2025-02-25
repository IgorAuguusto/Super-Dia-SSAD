package br.com.superdia.cors;
import java.io.IOException;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;

@Provider
public class CorsFilter implements ContainerResponseFilter {

	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext cres) throws IOException {
		cres.getHeaders().add("Access-Control-Allow-Origin", "*");
	      cres.getHeaders().add("Access-Control-Allow-Headers", "origin, content-type, accept, authorization, user-id, user-role");
	      cres.getHeaders().add("Access-Control-Allow-Credentials", "true");
	      cres.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
	      cres.getHeaders().add("Access-Control-Max-Age", "1209600");

	}

}
