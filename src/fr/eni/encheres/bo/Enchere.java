package fr.eni.encheres.bo;

import java.time.LocalDateTime;

public class Enchere implements Comparable{
	
	private Utilisateur user;
	
	private Article vente;
	
	private LocalDateTime dateEnchere;
	
	private int valeur;

	public Enchere () {
		
		
		
	}
	
	public Utilisateur getUser() {
		return user;
	}

	public void setUser(Utilisateur user) {
		this.user = user;
	}

	public Article getVente() {
		return vente;
	}

	public void setVente(Article vente) {
		this.vente = vente;
	}

	public LocalDateTime getDateEnchere() {
		return dateEnchere;
	}

	public void setDateEnchere(LocalDateTime dateEnchere) {
		this.dateEnchere = dateEnchere;
	}

	public int getValeur() {
		return valeur;
	}

	public void setValeur(int valeur) {
		this.valeur = valeur;
	}

	
	@Override
	public int compareTo(Object enchere1) {
		
		Enchere enchereCompar = (Enchere)enchere1;
		
		return this.getValeur() - enchereCompar.getValeur();
	}
	
	
}
