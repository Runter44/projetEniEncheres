package fr.eni.encheres.bo;

import java.util.ArrayList;
import java.util.List;

public class Utilisateur {

	private int noUtilisateur;
	private String pseudo,nom,prenom,email,telephone,rue,codePostal,ville,motDePasse;
	private int credit;
	private boolean administrateur;

	private List<Vente> ventes;//liste des ventes de cet utilisateur
	private List<Enchere> encheres;//liste des encheres de cet utilisateur

	public Utilisateur() {
		
		noUtilisateur = -1;
		ventes = new ArrayList<Vente>();
		encheres = new ArrayList<Enchere>();

	}

	public Utilisateur(String nomUser, String mdp) {
		setPseudo(nomUser);
		setMotDePasse(mdp);
	}
	public int getNoUtilisateur() {
		return noUtilisateur;
	}
	public void setNoUtilisateur(int noUtilisateur) {
		this.noUtilisateur = noUtilisateur;
	}

	public String getPseudo() {
		return pseudo;
	}
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
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

	public String getMotDePasse() {
		return motDePasse;
	}
	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}

	public int getCredit() {
		return credit;
	}
	public void setCredit(int credit) {
		this.credit = credit;
	}

	public List<Vente> getVentes() {
		return ventes;
	}
	public void setVentes(List<Vente> ventes) {
		this.ventes = ventes;
	}

	public void addVentes(Vente vente) {
		this.ventes.add(vente);
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

	public boolean isAdministrateur() {
		return administrateur;
	}
	public void setAdministrateur(boolean administrateur) {
		this.administrateur = administrateur;
	}


}
