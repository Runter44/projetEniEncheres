package fr.eni.encheres.bo;

import java.util.Date;

public class Article {

	private int noVente;
	private String nomArticle;
	private String description;
	private Date datesDebutEncheres;
	private Date datesFinEncheres;
	private int miseAPrix;
	private int prixVente;

	private Categorie cat;
	private Utilisateur vendeur;
	private Retrait retrait;

	public int getId() {
		return noVente;
	}

	public void setId(int noVente) {
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

	public Date getDatesDebutEncheres() {
		return datesDebutEncheres;
	}

	public void setDatesDebutEncheres(Date datesDebutEncheres) {
		this.datesDebutEncheres = datesDebutEncheres;
	}

	public Date getDatesFinEncheres() {
		return datesFinEncheres;
	}

	public void setDatesFinEncheres(Date datesFinEncheres) {
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
