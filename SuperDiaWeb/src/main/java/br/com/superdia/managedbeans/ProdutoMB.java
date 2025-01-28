package br.com.superdia.managedbeans;

import java.io.Serializable;
import java.util.List;

import br.com.superdia.interfaces.IProduto;
import br.com.superdia.modelo.Produto;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

@Named
@SessionScoped
public class ProdutoMB implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@EJB
	private IProduto iProduto;
	private Produto produto = new Produto();
	
	private List<Produto> produtos;

	public Produto getProduto() {
		return produto;
	}//getProduto()

	public void setProduto(Produto produto) {
		this.produto = produto;
	}//setProduto()
	
	public void grava() {
		if(produto.getId() == null) {
			this.iProduto.adiciona(produto);
		} else {
			this.iProduto.altera(produto);
		}
		this.produto = new Produto();
		this.produtos = this.iProduto.getProdutos();
	}//grava()
	
	public List<Produto> getProdutos() {
		if(produtos == null) {
			produtos = this.iProduto.getProdutos();
		}
		return produtos;
	}//getProdutos()
	
	public void remover(Produto produto) {
		this.iProduto.remove(produto);
		this.produtos = this.iProduto.getProdutos();
	}//remover()
	
	public void cancelarEdicao() {
		this.produto = new Produto();
	}//cancelarEdicao()

}//ProdutoMB