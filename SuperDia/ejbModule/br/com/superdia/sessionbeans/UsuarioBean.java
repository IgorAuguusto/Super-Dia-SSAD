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
		return em.createQuery(query).getResultList();
	}//getUsuarios()
	
	

	@Override
	public Usuario getById(Long id) {
		try {
	        CriteriaBuilder cb = em.getCriteriaBuilder();
	        CriteriaQuery<Usuario> query = cb.createQuery(Usuario.class);
	        Root<Usuario> produto = query.from(Usuario.class);

	        Predicate condicaoId = cb.equal(produto.get("id"), id);
	        query.where(condicaoId);

	        return em.createQuery(query).getSingleResult();
	    } catch (NoResultException e) {
	        // Tratar o caso em que nenhum produto é encontrado
	        return null;
	    } catch (NonUniqueResultException e) {
	        // Tratar o caso em que mais de um produto é encontrado (improvável para ID)
	        throw new RuntimeException("Mais de um usuário encontrado com o mesmo ID", e);
	    }
	}

	@Override
	public boolean logar(String identificacao, String senha) {
		// Consulta para encontrar a Pessoa com base na identificação (nome, CPF ou e-mail)
	    String jpql = "SELECT p FROM Pessoa p WHERE p.cpf = :identificacao OR p.email = :identificacao";
	    List<Pessoa> pessoas = em.createQuery(jpql, Pessoa.class)
	                             .setParameter("identificacao", identificacao)
	                             .getResultList();

	    // Se não encontrar nenhuma Pessoa, retorna false
	    if (pessoas.isEmpty()) {
	        return false;
	    }

	    // Assume que a identificação é única, então pega a primeira Pessoa da lista
	    Pessoa pessoa = pessoas.get(0);

	    // Consulta para encontrar o Usuario associado à Pessoa
	    String jpqlUsuario = "SELECT u FROM Usuario u WHERE u.pessoa = :pessoa";
	    List<Usuario> usuarios = em.createQuery(jpqlUsuario, Usuario.class)
	                               .setParameter("pessoa", pessoa)
	                               .getResultList();

	    // Se não encontrar nenhum Usuario, retorna false
	    if (usuarios.isEmpty()) {
	        return false;
	    }

	    // Assume que cada Pessoa tem apenas um Usuario associado, então pega o primeiro
	    Usuario usuario = usuarios.get(0);
	    
	    return BCrypt.checkpw(senha, usuario.getSenha());
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
