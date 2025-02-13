package br.com.superdia.interfaces;

import java.util.List;

import br.com.superdia.modelo.Venda;

public interface IVenda {
	void adiciona(Venda venda);
	void remove(Venda produto);
	void altera(Venda produto);
	List<Venda> getVendas();
	List<Venda> getVendasByUserId(Long id);
	Venda getById(Long id);
}
