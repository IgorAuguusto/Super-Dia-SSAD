package br.com.superdia.sessionbeans;

import java.util.List;

import br.com.superdia.interfaces.IVenda;
import br.com.superdia.modelo.Usuario;
import br.com.superdia.modelo.Venda;
import jakarta.ejb.Remote;
import jakarta.ejb.Stateful;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaQuery;

@Stateful
@Remote(IVenda.class)
public class VendasBean implements IVenda {
	
	@PersistenceContext(unitName = "SuperDia")
	EntityManager em;
	
	@Override
	public void adiciona(Venda venda) {
		em.persist(venda);
	}

	@Override
	public void remove(Venda venda) {
		em.remove(em.merge(venda));
	}

	@Override
	public void altera(Venda venda) {
		em.merge(venda);
	}

	@Override
	public List<Venda> getVendas() {
		CriteriaQuery<Venda> query = em.getCriteriaBuilder().createQuery(Venda.class);
		query.select(query.from(Venda.class));
		List<Venda> vendas = em.createQuery(query).getResultList();
		return vendas;
	}

	@Override
	public List<Venda> getVendasByUserId(Long id) {
		Usuario usuario = null;
		try {
			String jpqlUser = "SELECT u FROM Usuario u WHERE u.id = :userId";
			usuario = em.createQuery(jpqlUser, Usuario.class)
									.setParameter("userId", id)
									.getSingleResult();
			
		} catch (NoResultException e) {
	        System.out.println("Nenhum usuário foi encontrado");
	        return null;
	    } catch (NonUniqueResultException e) {
	    	System.out.println("Mais de um usuário encontrado com o mesmo ID " + e.getMessage());
	        return null;
	    } catch (Exception e) {
	    	e.printStackTrace();
	    	return null;
		}
		
		
		try {
			
			String jpql = "SELECT v FROM Venda v WHERE v.usuario = :user";
			List<Venda> vendas = em.createQuery(jpql, Venda.class)
					.setParameter("user", usuario)
					.getResultList();
			
			return vendas;
		} catch (NoResultException e) {
	        System.out.println("Nenhuma venda foi encontrada");
	        return null;
	    } catch (NonUniqueResultException e) {
	    	System.out.println("Mais de uma venda encontrada com o mesmo ID " + e.getMessage());
	        return null;
	    } catch (Exception e) {
	    	e.printStackTrace();
	    	return null;
		}
	}

	@Override
	public Venda getById(Long id) {
		try {
	        String jpql = "SELECT v FROM Venda v WHERE v.id = :id";

	        Venda venda = em.createQuery(jpql, Venda.class)
	        							.setParameter("id", id)
	        							.getSingleResult();
	        return venda;
	    } catch (NoResultException e) {
	        System.out.println("Nenhuma venda foi encontrada");
	        return null;
	    } catch (NonUniqueResultException e) {
	        throw new RuntimeException("Mais de uma venda encontrada com o mesmo ID", e);
	    }
	}

}
