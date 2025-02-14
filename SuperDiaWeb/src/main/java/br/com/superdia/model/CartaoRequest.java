package br.com.superdia.model;

public class CartaoRequest {
    private String numeroCartao;
    private String dataExpiracao;
    
    public String getNumeroCartao() {
        return numeroCartao;
    }
    
    public void setNumeroCartao(String numeroCartao) {
        this.numeroCartao = numeroCartao;
    }
    
    public String getDataExpiracao() {
        return dataExpiracao;
    }
    
    public void setDataExpiracao(String dataExpiracao) {
        this.dataExpiracao = dataExpiracao;
    }
}
