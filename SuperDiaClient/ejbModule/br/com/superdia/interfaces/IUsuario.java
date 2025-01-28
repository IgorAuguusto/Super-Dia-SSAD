package br.com.superdia.interfaces;

import java.util.List;

import br.com.superdia.modelo.Usuario;

public interface IUsuario {
	void aidciona(Usuario usuario);
	void remove(Usuario usuario);
	void altera(Usuario usuario);
	List<Usuario> getUsuarios();
}//IUsuario
