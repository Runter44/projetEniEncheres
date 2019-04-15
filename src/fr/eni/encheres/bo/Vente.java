package fr.eni.encheres.bo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



public class Vente {
	
	private int noVente; //id
	private String nomArticle;//nom de l'objet vendu & de la vente
	private String description;//description de l'objet
	private LocalDateTime datesFinEncheres;
	private int miseAPrix;	//prix de départ
	private int prixVente;	//prix de la dernière enchère effectuée
	
	private Categorie cat;	//categorie de l'article
	private List<Enchere> encheres;
	private Utilisateur vendeur;	//le vendeur
	private Retrait retrait;
	
	
	public Vente() {
		
		noVente = -1;
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
	
	public LocalDateTime getDatesFinEncheres() {
		return datesFinEncheres;
	}
	public void setDatesFinEncheres(LocalDateTime datesFinEncheres) {
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
		//pour chaque encheres
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
