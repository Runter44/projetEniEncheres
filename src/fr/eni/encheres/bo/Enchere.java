package fr.eni.encheres.bo;

import java.util.Date;

public class Enchere implements Comparable{
	
	private Utilisateur user;
	
	private Article vente;
	
	private Date dateEnchere;
	
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

	public Date getDateEnchere() {
		return dateEnchere;
	}

	public void setDateEnchere(Date dateEnchere) {
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
