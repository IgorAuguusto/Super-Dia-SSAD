package br.com.superdia.sessionbeans;

import java.util.List;

import br.com.superdia.interfaces.IProduto;
import br.com.superdia.modelo.Produto;
import jakarta.ejb.Remote;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

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

	@Override
	public Produto getById(Long id) {
		try {
	        CriteriaBuilder cb = em.getCriteriaBuilder();
	        CriteriaQuery<Produto> query = cb.createQuery(Produto.class);
	        Root<Produto> produto = query.from(Produto.class);

	        Predicate condicaoId = cb.equal(produto.get("id"), id);
	        query.where(condicaoId);

	        return em.createQuery(query).getSingleResult();
	    } catch (NoResultException e) {
	        // Tratar o caso em que nenhum produto é encontrado
	        return null;
	    } catch (NonUniqueResultException e) {
	        // Tratar o caso em que mais de um produto é encontrado (improvável para ID)
	        throw new RuntimeException("Mais de um produto encontrado com o mesmo ID", e);
	    }
	}
	
}//ProdutoBean
