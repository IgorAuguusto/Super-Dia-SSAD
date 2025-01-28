package br.com.superdia.managedbeans;

import java.io.Serializable;
import java.util.List;

import br.com.superdia.interfaces.IPessoa;
import br.com.superdia.modelo.Pessoa;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

@Named
@SessionScoped
public class PessoaMB implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@EJB
	private IPessoa iPessoa;
	private Pessoa pessoa = new Pessoa();
	
	private List<Pessoa> pessoas;

	public Pessoa getPessoa() {
		return pessoa;
	}//getPessoa()

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}//setPessoa()
	
	public void grava() {
		if(pessoa.getId() == null) {
			this.iPessoa.adiciona(pessoa);
		} else {
			this.iPessoa.altera(pessoa);
		}
		this.pessoa = new Pessoa();
		this.pessoas = this.iPessoa.getPessoas();
	}//grava()
	
	public List<Pessoa> getPessoas() {
		if(pessoas == null) {
			this.pessoas = this.iPessoa.getPessoas();
		}
		return pessoas;
	}//getPessoas()
	
	public void remover(Pessoa pessoa) {
		this.iPessoa.remove(pessoa);
		this.pessoas = this.iPessoa.getPessoas();
	}//remover()
	
	public void cancelarEdicao() {
		this.pessoa = new Pessoa();
	}//cancelarEdicao()

	public IPessoa getiPessoa() {
		return iPessoa;
	}//getPessoa()
	
}//PessoaMB