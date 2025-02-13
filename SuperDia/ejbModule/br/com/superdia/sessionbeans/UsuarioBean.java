package br.com.superdia.sessionbeans;

import java.util.List;

import org.mindrot.jbcrypt.BCrypt;

import br.com.superdia.interfaces.IUsuario;
import br.com.superdia.modelo.Pessoa;
import br.com.superdia.modelo.Usuario;
import jakarta.ejb.Remote;
import jakarta.ejb.Stateful;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.validation.ConstraintViolationException;

@Stateful
@Remote(IUsuario.class)
public class UsuarioBean implements IUsuario {
	
	@PersistenceContext(unitName = "SuperDia")
	EntityManager em;
	
	@Override
	public void adiciona(Usuario usuario) {
		if(isEmailAlreadyInUse(usuario.getPessoa().getEmail())) {
			throw new ConstraintViolationException("O email já está em uso", null);
		}
		
		if(isCpfAlreadyInUse(usuario.getPessoa().getCpf())) {
			throw new ConstraintViolationException("O CPF já está em uso", null);
		}
		
		String userPassword = usuario.getSenha();
		String hashedUserPassword = BCrypt.hashpw(userPassword, BCrypt.gensalt());
		usuario.setSenha(hashedUserPassword);
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
		List<Usuario> usuarios = em.createQuery(query).getResultList();
		return usuarios;
	}//getUsuarios()
	

	@Override
	public Usuario getById(Long id) {
		try {
	        CriteriaBuilder cb = em.getCriteriaBuilder();
	        CriteriaQuery<Usuario> query = cb.createQuery(Usuario.class);
	        Root<Usuario> produto = query.from(Usuario.class);

	        Predicate condicaoId = cb.equal(produto.get("id"), id);
	        query.where(condicaoId);

	        Usuario usuario = em.createQuery(query).getSingleResult();
	        return usuario;
	    } catch (NoResultException e) {
	        // Tratar o caso em que nenhum usuário é encontrado
	        return null;
	    } catch (NonUniqueResultException e) {
	        throw new RuntimeException("Mais de um usuário encontrado com o mesmo ID", e);
	    }
	}

	@Override
	public Usuario logar(String identificacao, String senha) {
		Pessoa pessoa = null;
		try {
			String jpql = "SELECT p FROM Pessoa p WHERE p.cpf = :identificacao OR p.email = :identificacao";
		    pessoa = em.createQuery(jpql, Pessoa.class)
		                             .setParameter("identificacao", identificacao)
		                             .getSingleResult();
		} catch (NoResultException e) {
			System.out.println("Não encontrou nenhuma pessoa com login");
	        return null;
		}

	    Usuario usuario = null;
	    try {
	    	String jpqlUsuario = "SELECT u FROM Usuario u WHERE u.pessoa = :pessoa";
	    	usuario = em.createQuery(jpqlUsuario, Usuario.class)
                    .setParameter("pessoa", pessoa)
                    .getSingleResult();
		} catch (Exception e) {
			System.out.println("Não encontrou nenhum usuário com pessoa");
	        return null;
		}
	    
	    boolean isPasswordCorrect = BCrypt.checkpw(senha, usuario.getSenha());
	    
	    return isPasswordCorrect ? usuario : null;
	}
	
	private boolean isEmailAlreadyInUse(String email) {
		Long emailCount = em.createQuery("SELECT COUNT(p) FROM Pessoa p WHERE p.email = :email", Long.class)
                .setParameter("email", email)
                .getSingleResult();

		 if (emailCount > 0) {
		     return true;
		 }
		 return false;
	}
	
	private boolean isCpfAlreadyInUse(String cpf) {
		Long cpfCount = em.createQuery("SELECT COUNT(p) FROM Pessoa p WHERE p.cpf = :cpf", Long.class)
                .setParameter("cpf", cpf)
                .getSingleResult();

		 if (cpfCount > 0) {
		     return true;
		 }
		 return false;
	}

}//UsuarioBean
