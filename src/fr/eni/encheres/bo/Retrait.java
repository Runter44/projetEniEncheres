package fr.eni.encheres.bo;

public class Retrait {
	
	private String rue,codePostal,ville;
	
	private Article vente;

	public Retrait() {
		
		
		
	}
	
	public String getRue() {
		return rue;
	}

	public void setRue(String rue) {
		this.rue = rue;
	}

	public String getCodePostal() {
		return codePostal;
	}

	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public Article getVente() {
		return vente;
	}

	public void setVente(Article vente) {
		this.vente = vente;
	}
	
	
	
}
