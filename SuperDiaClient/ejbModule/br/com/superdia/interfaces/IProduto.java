package br.com.superdia.interfaces;

import java.util.List;

import br.com.superdia.modelo.Produto;

public interface IProduto {
	void adiciona(Produto produto);
	void remove(Produto produto);
	void altera(Produto produto);
	List<Produto> getProdutos();
	Produto getById(Long id);
	boolean importarProdutos(String url);
}//IProduto
