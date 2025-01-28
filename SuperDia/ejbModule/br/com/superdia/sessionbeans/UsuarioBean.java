package br.com.superdia.sessionbeans;

import java.util.List;

import br.com.superdia.interfaces.IUsuario;
import br.com.superdia.modelo.Usuario;
import jakarta.ejb.Remote;
import jakarta.ejb.Stateful;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaQuery;

@Stateful
@Remote(IUsuario.class)
public class UsuarioBean implements IUsuario {
	
	@PersistenceContext(unitName = "SuperDia")
	EntityManager em;
	
	@Override
	public void aidciona(Usuario usuario) {
		em.persist(usuario);
	}//aidciona()

	@Override
	public void remove(Usuario usuario) {
		em.remove(em.merge(usuario));
	}//remove()

	@Override
	public void altera(Usuario usuario) {
		em.merge(usuario);
	}//altera()

	@Override
	public List<Usuario> getUsuarios() {
		CriteriaQuery<Usuario> query = em.getCriteriaBuilder().createQuery(Usuario.class);
		query.select(query.from(Usuario.class));
		return em.createQuery(query).getResultList();
	}//getUsuarios()

}//UsuarioBean
