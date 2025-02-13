package br.com.superdia.modelo;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Produto implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public Produto() {}

    public Produto(
			@NotNull(message = "O nome do produto não pode ser nulo") @Size(min = 3, max = 50, message = "O nome do produto deve ter entre 3 e 50 caracteres") String nome,
			@Size(max = 200, message = "A descrição não pode exceder 200 caracteres") String descricao,
			@NotNull(message = "O preço do produto não pode ser nulo") @DecimalMin(value = "0.0", inclusive = false, message = "O preço deve ser maior que zero") Double preco,
			@NotNull(message = "O estoque mínimo não pode ser nulo") @Min(value = 0, message = "O estoque mínimo deve ser igual ou maior que zero") Integer estoqueMinimo,
			@NotNull(message = "A quantidade em estoque não pode ser nula") @Min(value = 0, message = "A quantidade em estoque deve ser igual ou maior que zero") Integer quantidadeEstoque,
			String imageUrl, String vendidoPor) {
		super();
		this.nome = nome;
		this.descricao = descricao;
		this.preco = preco;
		this.estoqueMinimo = estoqueMinimo;
		this.quantidadeEstoque = quantidadeEstoque;
		this.imageUrl = imageUrl;
		this.vendidoPor = vendidoPor;
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "O nome do produto não pode ser nulo")
    @Size(min = 3, message = "O nome do produto deve ter no mínimo 3 caracteres")
    private String nome;

    @Size(max = 200, message = "A descrição não pode exceder 200 caracteres")
    private String descricao;

    @NotNull(message = "O preço do produto não pode ser nulo")
    private Double preco;

    @NotNull(message = "O estoque mínimo não pode ser nulo")
    @Min(value = 0, message = "O estoque mínimo deve ser igual ou maior que zero")
    private Integer estoqueMinimo;

    @NotNull(message = "A quantidade em estoque não pode ser nula")
    @Min(value = 0, message = "A quantidade em estoque deve ser igual ou maior que zero")
    private Integer quantidadeEstoque;
    
    private String imageUrl;
    
    private String vendidoPor;

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

    public String getDescricao() {
        return descricao;
    } // getDescricao()

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    } // setDescricao()

    public Double getPreco() {
        return preco;
    } // getPreco()

    public void setPreco(Double preco) {
        this.preco = preco;
    } // setPreco()

    public Integer getEstoqueMinimo() {
        return estoqueMinimo;
    } // getEstoqueMinimo()

    public void setEstoqueMinimo(Integer estoqueMinimo) {
        this.estoqueMinimo = estoqueMinimo;
    } // setEstoqueMinimo()

    public Integer getQuantidadeEstoque() {
        return quantidadeEstoque;
    } // getQuantidadeEstoque()

    public void setQuantidadeEstoque(Integer quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    } // setQuantidadeEstoque()
    
    public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getVendidoPor() {
		return vendidoPor;
	}

	public void setVendidoPor(String vendidoPor) {
		this.vendidoPor = vendidoPor;
	}

	@Override
	public String toString() {
		return "Produto [id=" + id + ", nome=" + nome + ", descricao=" + descricao + ", preco=" + preco
				+ ", estoqueMinimo=" + estoqueMinimo + ", quantidadeEstoque=" + quantidadeEstoque + ", imageUrl="
				+ imageUrl + ", vendidoPor=" + vendidoPor + "]";
	}

	
	
} // Produto
