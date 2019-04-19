package fr.eni.encheres.bo;

public class Utilisateur {

	private Integer id;
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

	public Utilisateur(int id, String pseudo, String password, String nom, String prenom, String email,
			String telephone, String rue, String codePostal, String ville, int credit, boolean isAdmin) {
		super();
		this.id = id;
		this.pseudo = pseudo;
		this.password = password;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.telephone = telephone;
		this.rue = rue;
		this.codePostal = codePostal;
		this.ville = ville;
		this.credit = credit;
		this.isAdmin = isAdmin;
	}

	public Utilisateur(String nomUser, String mdp) {
		setPseudo(nomUser);
		setPassword(mdp);
	}
	
	public Integer getId() {
		return id;
	}
	public Utilisateur setId(Integer noUtilisateur) {
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
	public boolean equalsUser(Utilisateur monUtilisateur){

		boolean bool;
		bool = true;

		if (this.getId() != monUtilisateur.getId()){bool = false;}
		if (!this.getPseudo().equals(monUtilisateur.getPseudo())){bool = false;}
		if (!this.getNom().equals(monUtilisateur.getNom())){bool = false;}
		if (!this.getPrenom().equals(monUtilisateur.getPrenom())){bool = false;}
		if (!this.getEmail().equals(monUtilisateur.getEmail())){bool = false;}
		if (!this.getTelephone().equals(monUtilisateur.getTelephone())){bool = false;}
		if (!this.getRue().equals(monUtilisateur.getRue())){bool = false;}
		if (!this.getCodePostal().equals(monUtilisateur.getCodePostal())){bool = false;}
		if (!this.getVille().equals(monUtilisateur.getVille())){bool = false;}
		if (this.getCredit() != monUtilisateur.getCredit()){bool = false;}
		if (this.isAdmin() != monUtilisateur.isAdmin()){bool = false;}
		
		return bool;		
	}
}
