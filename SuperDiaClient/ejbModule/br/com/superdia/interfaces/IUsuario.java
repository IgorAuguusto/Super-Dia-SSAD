package br.com.superdia.interfaces;

import java.util.List;

import br.com.superdia.modelo.Usuario;

public interface IUsuario {
	void adiciona(Usuario usuario);
	void remove(Usuario usuario);
	void altera(Usuario usuario);
	Usuario logar(String identificacao, String senha);
	List<Usuario> getUsuarios();
	Usuario getById(Long id);
}//IUsuario
