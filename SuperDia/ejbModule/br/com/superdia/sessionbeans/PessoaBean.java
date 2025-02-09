package br.com.superdia.sessionbeans;

import java.util.List;

import br.com.superdia.interfaces.IPessoa;
import br.com.superdia.modelo.Pessoa;
import jakarta.ejb.Remote;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaQuery;



@Stateless
@Remote(IPessoa.class)
public class PessoaBean implements IPessoa {

	@PersistenceContext(unitName = "SuperDia")
	EntityManager em;

	@Override
	public void adiciona(Pessoa pessoa) {
		em.persist(pessoa);
	}//adiciona()

	@Override
	public void remove(Pessoa pessoa) {
		em.remove(em.merge(pessoa));
	}//remove()

	@Override
	public void altera(Pessoa pessoa) {
		em.merge(pessoa);
	}//altera()

	@Override
	public List<Pessoa> getPessoas() {
		CriteriaQuery<Pessoa> query = em.getCriteriaBuilder().createQuery(Pessoa.class);
		query.select(query.from(Pessoa.class));
		return em.createQuery(query).getResultList();
	}//getPessoas()

	@Override
	public Pessoa getById(Long id) {
		return em.find(Pessoa.class, id);
	}

}//PessoaBean
