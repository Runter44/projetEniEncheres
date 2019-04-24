package fr.eni.encheres.bo;

import java.util.Date;

public class Article {
	
	private Integer noVente;
	private String nomArticle;
	private String description;
	private Date datesDebutEncheres;
	private Date datesFinEncheres;
	private int miseAPrix;
	private int prixVente;
	private String rue;
	private String codePostal;
	private String ville;

	private Categorie cat;
	private Utilisateur vendeur;
	
	
	public Article() {
	}
	
	public Integer getNoVente() {
		return noVente;
	}
	public Article setNoVente(Integer noVente) {
		this.noVente = noVente;
		return this;
	}

	public String getNomArticle() {
		return nomArticle;
	}

	public Article setNomArticle(String nomArticle) {
		this.nomArticle = nomArticle;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public Article setDescription(String description) {
		this.description = description;
		return this;
	}

	public Date getDatesDebutEncheres() {
		return datesDebutEncheres;
	}

	public Article setDatesDebutEncheres(Date datesDebutEncheres) {
		this.datesDebutEncheres = datesDebutEncheres;
		return this;
	}

	public Date getDatesFinEncheres() {
		return datesFinEncheres;
	}

	public Article setDatesFinEncheres(Date datesFinEncheres) {
		this.datesFinEncheres = datesFinEncheres;
		return this;
	}

	public int getMiseAPrix() {
		return miseAPrix;
	}

	public Article setMiseAPrix(int miseAPrix) {
		this.miseAPrix = miseAPrix;
		return this;
	}

	public int getPrixVente() {
		return prixVente;
	}

	public Article setPrixVente(int prixVente) {
		this.prixVente = prixVente;
		return this;
	}

	public Categorie getCat() {
		return cat;
	}

	public Article setCat(Categorie cat) {
		this.cat = cat;
		return this;
	}

	public Utilisateur getVendeur() {
		return vendeur;
	}

	public Article setVendeur(Utilisateur vendeur) {
		this.vendeur = vendeur;
		return this;
	}

	public String getRue() {
		return rue;
	}

	public Article setRue(String rue) {
		this.rue = rue;
		return this;
	}

	public String getCodePostal() {
		return codePostal;
	}

	public Article setCodePostal(String codePostal) {
		this.codePostal = codePostal;
		return this;
	}

	public String getVille() {
		return ville;
	}

	public Article setVille(String ville) {
		this.ville = ville;
		return this;
	}
}
