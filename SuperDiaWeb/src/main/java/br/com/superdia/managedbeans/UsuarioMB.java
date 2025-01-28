package br.com.superdia.managedbeans;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import br.com.superdia.enums.Perfil;
import br.com.superdia.interfaces.IUsuario;
import br.com.superdia.modelo.Pessoa;
import br.com.superdia.modelo.Usuario;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@SessionScoped
public class UsuarioMB implements Serializable {
    private static final long serialVersionUID = 1L;

    @EJB
    private IUsuario IUsuario;
    
    @Inject
    private PessoaMB pessoaMB;
    
    private Usuario usuario = new Usuario();
    private List<Usuario> usuarios;
    private List<Pessoa> pessoas; 
    private List<Perfil> perfis; 
    
    @PostConstruct
    public void init() {
        usuario = new Usuario();
        usuario.setPessoa(new Pessoa());
    }//init()

    public Usuario getUsuario() {
        return usuario;
    } // getUsuario()

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    } // setUsuario()

    public List<Usuario> getUsuarios() {
        if (usuarios == null) {
            usuarios = this.IUsuario.getUsuarios();
        }
        return usuarios;
    } // getUsuarios()

    public List<Pessoa> getPessoas() {
        if (pessoas == null) {
            pessoas = this.pessoaMB.getPessoas();
        }
        return pessoas;
    } // getPessoas()

    public List<Perfil> getPerfis() {
        if (perfis == null) {
            perfis = Arrays.asList(Perfil.values());
        }
        return perfis;
    } // getPerfis()

    public void grava() {
        if (usuario.getPessoa() == null || usuario.getPessoa().getId() == null) {
            throw new IllegalArgumentException("Pessoa deve ser selecionada");
        }
        if (usuario.getPerfil() == null) {
            throw new IllegalArgumentException("Perfil deve ser selecionado");
        }

        // Busca a pessoa pelo ID selecionado
        Pessoa pessoaSelecionada = pessoaMB.getiPessoa().buscarPessoa(usuario.getPessoa().getId());
        usuario.setPessoa(pessoaSelecionada);

        if (usuario.getId() == null) {
            this.IUsuario.aidciona(usuario);
        } else {
            this.IUsuario.altera(usuario);
        }

        this.usuario = new Usuario();
        this.usuarios = this.IUsuario.getUsuarios();
    }//grava


    public void remover(Usuario usuario) {
        this.IUsuario.remove(usuario);
        this.usuarios = this.IUsuario.getUsuarios();
    } // remover()

    public void cancelarEdicao() {
        this.usuario = new Usuario();
    } // cancelarEdicao()
}
