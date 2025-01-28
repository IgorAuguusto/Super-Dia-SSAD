package br.com.superdia.enums;

public enum Perfil {
	
	ADMINISTRADOR("Administrador"),
	CAIXA("Caixa"),
	CLIENTE("Cliente");
	
	private String perfil;
	
	private Perfil(String perfil) {
		this.perfil = perfil;
	}//Perfil

	public String getPerfil() {
		return perfil;
	}//getPerfil()

	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}//setPerfil()
	
	public static Perfil converterStringParaPerfil(String string) {
		for(Perfil perfil : Perfil.values()) {
			if (perfil.toString().equals(string)) {
				return perfil;
			}
		}
		return null;
	}//converterStringParaPerfil()
}//Perfil
