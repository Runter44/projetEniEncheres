package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.impl.DAOUtilisateur;

public class TestDAOUser {

	public static void main(String[] args) {
		Utilisateur u1 = new Utilisateur();
		u1.setPseudo("michou44");
		u1.setPrenom("Michel");
		u1.setNom("Dupont");
		u1.setEmail("mdupont@email.com");
		u1.setRue("44 rue du boulevard");
		u1.setCodePostal("44000");
		u1.setVille("Nantes");
		u1.setPassword("bonjour");
		u1.setCredit(500);
		u1.setAdmin(false);
		
		DAOUtilisateur dao = DAOFactory.getDAOUtilisateur();
		
		System.out.println(dao.insert(u1));
		System.out.println(dao.findAll());
		u1.setPassword("salut");
		System.out.println(dao.update(u1));
		System.out.println(dao.remove(u1));
	}

}
