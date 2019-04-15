package fr.eni.encheres.bll;

import java.util.ArrayList;
import java.util.List;

import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Retrait;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.bo.Vente;
import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.dal.InterfaceDAO;

public class EnchereManager {

	private static EnchereManager instance;
	
	static {
		ScheduleManager.getInstance();
		System.out.println("Demarrage du Schedule Manager");
	}
	//private InterfaceDAO dal;


	private List<Utilisateur> utilisateurs;
	private List<Categorie> categories;
	private List<Enchere> encheres;
	private List<Vente> ventes;


	private EnchereManager() {

		utilisateurs = new ArrayList<Utilisateur>();
		categories = new ArrayList<Categorie>();
		encheres = new ArrayList<Enchere>();
		ventes = new ArrayList<Vente>();

	}

	public static EnchereManager getInstance() {

		if (instance == null)  {

			instance = new EnchereManager();

		}

		return instance;

	}

	public List<Utilisateur> getUtilisateurs() {

		if (utilisateurs == null || utilisateurs.size() == 0) {

			InterfaceDAO dal = DAOFactory.getUtilisateursDAO();

			utilisateurs = dal.selectAll();

		}

		return utilisateurs;

	}
	public List<Utilisateur> getUtilisateurs(Utilisateur utilisateur,String sql) {	//recuperer des utilisateurs par criteres

		if (utilisateurs == null || utilisateurs.size() == 0) {

			InterfaceDAO dal = DAOFactory.getUtilisateursDAO();

			utilisateurs = dal.selectAll(utilisateur,sql);

		}

		return utilisateurs;

	}
	public Utilisateur getUtilisateur(int noUtilisateur) {

		Utilisateur retour = null;
		
		InterfaceDAO dal = DAOFactory.getUtilisateursDAO();
		
		//si la mémoire cache n'est pas vide
		if (utilisateurs != null && utilisateurs.size() <=0) {
			//pour chaque utilisateur
			for (Utilisateur user:utilisateurs) {
				
				//si c'eset l'utilisateur qu'on cherche
				if (user.getNoUtilisateur() == noUtilisateur) {

					retour = user;
					break;

				}

			}
		}

		//si on a pas trouvé l'utilisateur dans la mémoire cache
		if (retour == null) {
			
			Utilisateur userTmp = new Utilisateur();
			userTmp.setNoUtilisateur(noUtilisateur);
			
			retour = (Utilisateur) dal.select(userTmp, "SELECTBYID");

		}
		
		return retour;

	}
	public Utilisateur createUtilisateur(Utilisateur utilisateur) {
		InterfaceDAO dal = DAOFactory.getUtilisateursDAO();
		utilisateur.setNoUtilisateur(dal.insert(utilisateur));
		return utilisateur;
	}
	public boolean setUtilisateur(Utilisateur user) {
		
		InterfaceDAO<Utilisateur> dal = DAOFactory.getUtilisateursDAO(); 
		return dal.update(user);
		
		//return false;
		
	}
	public boolean remUtilisateur(Utilisateur user) {

		InterfaceDAO dal = DAOFactory.getUtilisateursDAO(); 
		
		return dal.remove(user.getNoUtilisateur());
		
	}
	
	public List<Vente> getVentes() {
		
		if (ventes == null || ventes.size() == 0) {

			InterfaceDAO dal = DAOFactory.getVenteDAO();

			ventes = dal.selectAll();

		}

		return ventes;

	}
	public List<Vente> getVentes(Vente vente, String requete) {
		
		InterfaceDAO dal = DAOFactory.getVenteDAO();

		ventes = dal.selectAll(vente,requete);

		return ventes;

	}
	public Vente getVente(int noVente) {
		

		Vente retour = null;
		
		InterfaceDAO<Vente> dal = DAOFactory.getVenteDAO();
		/*
		if (utilisateurs == null || utilisateurs.size() == 0) {

			

			utilisateurs = dal.selectAll();

		}
		 */
		if (ventes != null && ventes.size() <=0) {
			for (Vente vente : ventes) {

				if (vente.getNoVente() == noVente) {

					retour = vente;
					break;

				}

			}
		}

		if (retour == null) {
			
			Vente v = new Vente();	// On créé un nouvel objet vente pour pouvoir envoyer un objet Vente
			v.setNoVente(noVente);	// Dans cet objet Vente, On met juste le numéro de vente
			retour = (Vente) dal.select(v, "SELECT");
			retour.setNoVente(noVente);			
		}


		return retour;
		
	}
	public Vente createVente(Vente vente) {
		InterfaceDAO<Vente> dal = DAOFactory.getVenteDAO();
		vente.setNoVente(dal.insert(vente));
		return vente;
	}
	public boolean setVente(Vente vente) {
		
		InterfaceDAO<Vente> dal = DAOFactory.getVenteDAO();		
		return dal.update(vente);
		
	}
	public boolean remVente(Vente vente) {

		InterfaceDAO dal = DAOFactory.getVenteDAO(); 
		
		return dal.remove(vente.getNoVente());
		
	}
	public boolean remVente(Vente vente, String requete) {

		InterfaceDAO dal = DAOFactory.getVenteDAO(); 
		
		return dal.remove(vente,requete);
		
	}

	public List<Categorie> getCategories() {

		if (categories == null || categories.size() == 0) {

			InterfaceDAO dal = DAOFactory.getCategoriesDAO();

			categories = dal.selectAll();

		}

		return categories;


	}
	public Categorie getCategorie(int noCat) {

		Categorie retour = null;
		
		InterfaceDAO dal = DAOFactory.getCategoriesDAO();
		/*
		if (utilisateurs == null || utilisateurs.size() == 0) {

			

			utilisateurs = dal.selectAll();

		}
		 */
		if (categories != null && categories.size() <=0) {
			for (Categorie cat:categories) {

				if (cat.getNoCategorie() == noCat) {

					retour = cat;
					break;

				}

			}
		}

		if (retour == null) {
			
			Categorie cat = new Categorie();
			cat.setNoCategorie(noCat);
			
			retour = (Categorie) dal.select(cat, "SELECT");

		}


		return retour;

	}
	public boolean setCategorie(Categorie cat) {
		
		return false;
		
	}
	public boolean remCategorie(Categorie cat) {
		
		return false;
		
	}
	
	public List<Enchere> getEncheres(Enchere e , String sql) {

		if (encheres == null || encheres.size() == 0) {

			InterfaceDAO dal = DAOFactory.getEncheresDAO();
			
			
			encheres = dal.selectAll(e,sql);

		}

		return encheres;

	}
	public Enchere getHauteEnchere(int noVente, String requete) {
		Enchere enchere = new Enchere();
		Enchere retour = null;
		List<Enchere> listeEnchere = new ArrayList<>();
		InterfaceDAO<Enchere> dal = DAOFactory.getEncheresDAO();
		
		enchere.setVente(this.getVente(noVente));
		enchere.setValeur(0);
		
		listeEnchere = dal.selectAll(enchere, requete);
		
		//on cherche l'enchére avec la valeur la plus élevé
		for(Enchere ench : listeEnchere) {
			if(ench.getValeur() > enchere.getValeur()) {
				enchere = ench;
			}
		}		
		
		if(enchere.getValeur() != 0) {
			retour = enchere;
		}
		
		return retour;
		
	}
	public boolean getUtilisateurEnchere(Enchere enchere) {
			InterfaceDAO<Enchere> dal = DAOFactory.getEncheresDAO();
			ArrayList<Enchere> listeEnchere = new ArrayList<>();
			boolean enchereExiste = false;
			
			listeEnchere = (ArrayList<Enchere>) dal.selectAll(enchere, "SELECTALLBYUTILISATEUR");
			
			for(Enchere ench : listeEnchere) {
				if(ench.getVente().getNoVente() == enchere.getVente().getNoVente() &&
	    		   ench.getUser().getNoUtilisateur() == enchere.getUser().getNoUtilisateur()) {
					enchereExiste = true;
				}
			}
			
			return enchereExiste;
	}
	public Enchere createEnchere(Enchere enchere) {
			
			InterfaceDAO<Enchere> dal = DAOFactory.getEncheresDAO();
			dal.insert(enchere);
			return enchere;
			
	}
	public boolean setEnchere(Enchere enchere) {
			InterfaceDAO<Enchere> dal = DAOFactory.getEncheresDAO();
			return dal.update(enchere);
			
	}
	public boolean remEnchere(Enchere enchere,String requete) {
		
		InterfaceDAO<Enchere> dal = DAOFactory.getEncheresDAO();
		return dal.remove(enchere,requete);
		
	}
	
	public Retrait createRetrait(Retrait retrait) {
		
		InterfaceDAO<Retrait> dal = DAOFactory.getRetraitsDAO();
		//retrait.setNoVente(
		dal.insert(retrait);
		return retrait;
		
	}
	public List<Retrait> getRetraits() {	//Obtenir tout les retraits
		
		InterfaceDAO<Retrait> dal = DAOFactory.getRetraitsDAO();
		return dal.selectAll();
		
	}
	public List<Retrait> getRetraits(Retrait retrait, String sql) {	//Obtenir tout les retraits qui correspondent à un critère
		
		InterfaceDAO<Retrait> dal = DAOFactory.getRetraitsDAO();
		return dal.selectAll(retrait,sql);
		
	}
	public Retrait getRetrait(Retrait retrait,String sql) {
		
		InterfaceDAO<Retrait> dal = DAOFactory.getRetraitsDAO();
		return dal.select(retrait,sql);
		
	}
	public boolean remRetrait(Retrait retrait,String requete) {
		
		InterfaceDAO<Retrait> dal = DAOFactory.getRetraitsDAO();
		return dal.remove(retrait,requete);
		
	}


	public Utilisateur connectUser(String login, String mdp) {
		
		Utilisateur retour = null;
		
		InterfaceDAO<Utilisateur> dal = DAOFactory.getUtilisateursDAO();

		Utilisateur userVide = new Utilisateur(login, mdp);
		
		retour = (Utilisateur) dal.select(userVide, "SELECT");
		
		return retour;
		
	}
	
}
