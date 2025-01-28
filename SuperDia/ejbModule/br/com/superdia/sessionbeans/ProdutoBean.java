package br.com.superdia.sessionbeans;

import java.util.List;

import br.com.superdia.interfaces.IProduto;
import br.com.superdia.modelo.Produto;
import jakarta.ejb.Remote;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaQuery;

@Stateless
@Remote(IProduto.class)
public class ProdutoBean implements IProduto {
	
	@PersistenceContext(unitName = "SuperDia")
	EntityManager em;

	@Override
	public void adiciona(Produto produto) {
		em.persist(produto);
	}//adiciona()

	@Override
	public void remove(Produto produto) {
		em.remove(em.merge(produto));
	}//remove()

	@Override
	public void altera(Produto produto) {
		em.merge(produto);
	}//altera()

	@Override
	public List<Produto> getProdutos() {
		CriteriaQuery<Produto> query = em.getCriteriaBuilder().createQuery(Produto.class);
		query.select(query.from(Produto.class));
		return em.createQuery(query).getResultList();
	}//getProdutos()
	
}//ProdutoBean
