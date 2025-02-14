package br.com.superdia.desktop.model;

public class Usuario  {
    
    private Long id;

    private Pessoa pessoa;
    
    private String senha;
    
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
