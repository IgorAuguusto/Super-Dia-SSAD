package br.com.superdia.modelo;

import java.io.Serializable;

import br.com.superdia.enums.Perfil;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;

    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "A pessoa vinculada ao usuário não pode ser nula")
    @OneToOne(cascade = CascadeType.ALL)
    private Pessoa pessoa;
    
    @NotNull(message = "A senha não pode ser null")
    @Size(min = 3, message = "Senha deve ter mais de 3 caracteres")
    private String senha;
    
    @NotNull(message = "O perfil do usuário não pode ser nulo")
    private Perfil perfil;
    
    public Long getId() {
        return id;
    } // getId()

    public void setId(Long id) {
        this.id = id;
    } // setId()

    public Pessoa getPessoa() {
        return pessoa;
    } // getPessoa()

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    } // setPessoa()
    
    
    public String getSenha() {
		return senha;
	}//getSenha()

	public void setSenha(String senha) {
		this.senha = senha;
	}//setSenha()

	public String getPerfilStr() {
        return perfil.toString();
    } // getPerfilString()
    
    public Perfil getPerfil() {
    	return perfil;
    }//getPerfil()

    public void setPerfilStr(String perfil) {
        this.perfil = Perfil.converterStringParaPerfil(perfil);
    } // setPerfil()

    public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}//setPerfil()
    
	@Override
    public String toString() {
        return String.format("id=%s, pessoa=%s, perfil=%s", id, pessoa, perfil.getPerfil().toString());
    } // toString()

} // Usuario
