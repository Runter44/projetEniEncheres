package fr.eni.encheres.criteres;

import java.util.Date;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Utilisateur;

public class CritEnchere implements Comparable{
	
	private Utilisateur user;
	
	private Article vente;
	
	private Date dateEnchere;
	
	private int valeur;
	
	private boolean enCours;

	private boolean nonDebute;

	public CritEnchere () {
		
		
		
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

	
	/**
	 * @return the enCours
	 */
	public boolean isEnCours() {
		return enCours;
	}

	/**
	 * @param enCours the enCours to set
	 */
	public void setEnCours(boolean enCours) {
		this.enCours = enCours;
	}

	/**
	 * @return the nonDebute
	 */
	public boolean isNonDebute() {
		return nonDebute;
	}

	/**
	 * @param nonDebute the nonDebute to set
	 */
	public void setNonDebute(boolean nonDebute) {
		this.nonDebute = nonDebute;
	}

	@Override
	public int compareTo(Object enchere1) {
		
		CritEnchere enchereCompar = (CritEnchere)enchere1;
		
		return this.getValeur() - enchereCompar.getValeur();
	}
	
	
}
