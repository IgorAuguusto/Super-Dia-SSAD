package br.com.superdia.model;

public class CartaoResponse {
    private boolean valido;
    private String mensagem;
    private int codigoRetorno;
    
    public CartaoResponse(boolean valido, int codigoRetorno) {
        this.valido = valido;
        this.codigoRetorno = codigoRetorno;
        this.mensagem = valido ? "Cartão válido" : "Cartão inválido";
    }
    
    public boolean isValido() {
        return valido;
    }
    
    public void setValido(boolean valido) {
        this.valido = valido;
    }
    
    public String getMensagem() {
        return mensagem;
    }
    
    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
    
    public int getCodigoRetorno() {
        return codigoRetorno;
    }
    
    public void setCodigoRetorno(int codigoRetorno) {
        this.codigoRetorno = codigoRetorno;
    }
}