package br.com.superdia.interfaces;

import java.util.List;

import br.com.superdia.modelo.Pessoa;

public interface IPessoa {
	void adiciona(Pessoa pessoa);
	void remove(Pessoa pessoa);
	void altera(Pessoa pessoa);
	List<Pessoa> getPessoas();
	Pessoa getById(Long id);
}//IPessoa
