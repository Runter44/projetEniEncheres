package fr.eni.encheres.criteres;

import java.util.Date;

import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Utilisateur;

public class CritArticle {
	
	private Integer noArticle;
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
	
	
	public CritArticle() {
	}
	
	public Integer getNoArticle() {
		return noArticle;
	}
	public void setNoArticle(Integer noArticle) {
		this.noArticle = noArticle;
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

	public CritArticle setPrixVente(int prixVente) {
		this.prixVente = prixVente;
		return this;
	}

	public Categorie getCat() {
		return cat;
	}

	public CritArticle setCat(Categorie cat) {
		this.cat = cat;
		return this;
	}

	public Utilisateur getVendeur() {
		return vendeur;
	}

	public CritArticle setVendeur(Utilisateur vendeur) {
		this.vendeur = vendeur;
		return this;
	}

	public String getRue() {
		return rue;
	}

	public CritArticle setRue(String rue) {
		this.rue = rue;
		return this;
	}

	public String getCodePostal() {
		return codePostal;
	}

	public CritArticle setCodePostal(String codePostal) {
		this.codePostal = codePostal;
		return this;
	}

	public String getVille() {
		return ville;
	}

	public CritArticle setVille(String ville) {
		this.ville = ville;
		return this;
	}
}
