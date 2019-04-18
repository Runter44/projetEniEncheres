package fr.eni.encheres.bo;

public class Utilisateur {

	private int id;
	private String pseudo;
	private String password;
	private String nom;
	private String prenom;
	private String email;
	private String telephone;
	private String rue;
	private String codePostal;
	private String ville;
	private int credit;
	private boolean isAdmin;

	public Utilisateur() {
		
	}

	public Utilisateur(String nomUser, String mdp) {
		setPseudo(nomUser);
		setPassword(mdp);
	}
	
	public int getId() {
		return id;
	}
	public Utilisateur setId(int noUtilisateur) {
		this.id = noUtilisateur;
		return this;
	}

	public String getPseudo() {
		return pseudo;
	}
	public Utilisateur setPseudo(String pseudo) {
		this.pseudo = pseudo;
		return this;
	}
	
	public String getPassword() {
		return password;
	}
	public Utilisateur setPassword(String password) {
		this.password = password;
		return this;
	}

	public String getNom() {
		return nom;
	}
	public Utilisateur setNom(String nom) {
		this.nom = nom;
		return this;
	}

	public String getPrenom() {
		return prenom;
	}
	public Utilisateur setPrenom(String prenom) {
		this.prenom = prenom;
		return this;
	}

	public String getEmail() {
		return email;
	}
	public Utilisateur setEmail(String email) {
		this.email = email;
		return this;
	}

	public String getTelephone() {
		return telephone;
	}
	public Utilisateur setTelephone(String telephone) {
		this.telephone = telephone;
		return this;
	}

	public String getRue() {
		return rue;
	}
	public Utilisateur setRue(String rue) {
		this.rue = rue;
		return this;
	}

	public String getCodePostal() {
		return codePostal;
	}
	public Utilisateur setCodePostal(String codePostal) {
		this.codePostal = codePostal;
		return this;
	}

	public String getVille() {
		return ville;
	}
	public Utilisateur setVille(String ville) {
		this.ville = ville;
		return this;
	}

	public int getCredit() {
		return credit;
	}
	public Utilisateur setCredit(int credit) {
		this.credit = credit;
		return this;
	}

	public boolean isAdmin() {
		return isAdmin;
	}
	public Utilisateur setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
		return this;
	}

	@Override
	public String toString() {
		return "Utilisateur [id=" + id + ", pseudo=" + pseudo + ", nom=" + nom + ", prenom="
				+ prenom + ", email=" + email + ", telephone=" + telephone + ", rue=" + rue + ", codePostal="
				+ codePostal + ", ville=" + ville + ", credit=" + credit + ", isAdmin=" + isAdmin + "]";
	}
}
