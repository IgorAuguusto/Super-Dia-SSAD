package br.com.superdia.desktop.model;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import jakarta.persistence.Entity;

public class Pessoa  {
    private Long id;

    private String nome;

    private String endereco;

    private String cpf;

    private String email;

    private String telefone;

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
