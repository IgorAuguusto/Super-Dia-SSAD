package br.com.superdia.modelo;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
public class Pessoa implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "O nome não pode ser nulo")
    @Size(min = 3, max = 50, message = "O nome deve ter entre 3 e 50 caracteres")
    private String nome;

    @NotNull(message = "O endereço não pode ser nulo")
    @Size(min = 5, max = 100, message = "O endereço deve ter entre 5 e 100 caracteres")
    private String endereco;

    @NotNull(message = "O CPF não pode ser nulo")
    @Pattern(regexp = "^\\d{11}$" , message = "O CPF deve conter exatamente 11 dígitos numéricos")
    private String cpf;

    @NotNull(message = "O e-mail não pode ser nulo")
    @Email(message = "O e-mail deve ser válido")
    private String email;

    @NotNull(message = "O telefone não pode ser nulo")
    @Pattern(regexp = "\\d{10,11}", message = "O telefone deve conter entre 10 e 11 dígitos numéricos")
    private String telefone;

    @NotNull(message = "A data de nascimento não pode ser nula")
    private LocalDate dataNascimento;

    public Long getId() {
        return id;
    } // getId()

    public void setId(Long id) {
        this.id = id;
    } // setId()

    public String getNome() {
        return nome;
    } // getNome()

    public void setNome(String nome) {
        this.nome = nome;
    } // setNome()

    public String getEndereco() {
        return endereco;
    } // getEndereco()

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    } // setEndereco()

    public String getCpf() {
        return cpf;
    } // getCpf()

    public void setCpf(String cpf) {
        this.cpf = cpf;
    } // setCpf()

    public String getEmail() {
        return email;
    } // getEmail()

    public void setEmail(String email) {
        this.email = email;
    } // setEmail()

    public String getTelefone() {
        return telefone;
    } // getTelefone()

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    } // setTelefone()

    public LocalDate getDataNascimento() {
        return dataNascimento;
    } // getDataNascimento()
    
    public void setDataNascimento(String dataNascimento) {
        if (dataNascimento != null && !dataNascimento.isEmpty()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            try {
                this.dataNascimento = LocalDate.parse(dataNascimento, formatter);
            } catch (DateTimeParseException e) {
                e.printStackTrace();
            }
        } else {
            this.dataNascimento = null;
        }
    }//setDataNascimento()

    private String getDataNascimento(String formato) {
        if (dataNascimento == null) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formato);
        return dataNascimento.format(formatter);
    } // getDataNascimento()

    public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}//setDataNascimento()

	public String getDataNascimentoFormatted() {
        return getDataNascimento("dd/MM/yyyy");
    } // getDataNascimentoFormatted()

    @Override
    public String toString() {
        return String.format("id = %s, nome = %s, endereco = %s, cpf = %s, email = %s, telefone = %s, dataNascimento = %s",
                id, nome, endereco, cpf, email, telefone, getDataNascimento("dd/MM/yyyy"));
    } // toString()

} // Pessoa
