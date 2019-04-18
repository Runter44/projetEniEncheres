package fr.eni.encheres.bo;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



public class Vente<T> {
	
	private int noVente;
	private String nomArticle;
	private String description;
	private Timestamp datesDebutEncheres;
	private Timestamp datesFinEncheres;
	private int miseAPrix;
	private int prixVente;
	
	private Categorie cat;
	private List<Enchere> encheres;
	private Utilisateur vendeur;
	private Retrait retrait;
	
	
	public Vente() {
		
		encheres = new ArrayList<Enchere>();
		
	}
	
	public int getNoVente() {
		return noVente;
	}
	public void setNoVente(int noVente) {
		this.noVente = noVente;
	}
	
	public String getNomArticle() {
		return nomArticle;
	}
	public void setNomArticle(String nomArticle) {
		this.nomArticle = nomArticle;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * @return the datesDebutEncheres
	 */
	public Timestamp getDatesDebutEncheres() {
		return datesDebutEncheres;
	}

	/**
	 * @param datesDebutEncheres the datesDebutEncheres to set
	 */
	public void setDatesDebutEncheres(Timestamp datesDebutEncheres) {
		this.datesDebutEncheres = datesDebutEncheres;
	}

	/**
	 * @return the datesFinEncheres
	 */
	public Timestamp getDatesFinEncheres() {
		return datesFinEncheres;
	}

	/**
	 * @param datesFinEncheres the datesFinEncheres to set
	 */
	public void setDatesFinEncheres(Timestamp datesFinEncheres) {
		this.datesFinEncheres = datesFinEncheres;
	}

	public int getMiseAPrix() {
		return miseAPrix;
	}
	public void setMiseAPrix(int miseAPrix) {
		this.miseAPrix = miseAPrix;
	}
	
	public int getPrixVente() {
		return prixVente;
	}
	public void setPrixVente(int prixVente) {
		this.prixVente = prixVente;
	}
	
	public Categorie getCat() {
		return cat;
	}
	public void setCat(Categorie cat) {
		this.cat = cat;
	}
	
	public List<Enchere> getEncheres() {
		return encheres;
	}
	public void setEncheres(List<Enchere> encheres) {
		this.encheres = encheres;
	}
	public void addEncheres(Enchere enchere) {
		this.encheres.add(enchere);
	}
	public List<Enchere> getEncheresParValeur() {
		List<Enchere> listeHaute = new ArrayList<>();
		
		for (Enchere enchere:encheres) {
			listeHaute.add(enchere);
		}
		
		Collections.sort(listeHaute);
		
		return listeHaute;
	}
	
	public Utilisateur getVendeur() {
		return vendeur;
	}
	public void setVendeur(Utilisateur vendeur) {
		this.vendeur = vendeur;
	}

	
	public Retrait getRetrait() {
		return retrait;
	}

	public void setRetrait(Retrait retrait) {
		this.retrait = retrait;
	}
	
	
	
}
