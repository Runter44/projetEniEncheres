package fr.eni.encheres.ihm;

import fr.eni.encheres.bll.*;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.bo.Vente;
import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.dal.InterfaceDAO;
import fr.eni.encheres.dal.impl.*;

import java.awt.List;
import java.util.ArrayList;

public class test {
	public static void main(String[] args) {
		ArrayList<Enchere> listeEnchere = new ArrayList<>();
		ArrayList<Utilisateur> listeUtilisateur = new ArrayList<>();
		Enchere e = new Enchere();
		Vente v = new Vente();
		
		
		
		
		InterfaceDAO dal = DAOFactory.getEncheresDAO();
		InterfaceDAO dalU = DAOFactory.getUtilisateursDAO();
		
		
		listeEnchere = (ArrayList<Enchere>) dal.selectAll();
		listeUtilisateur = (ArrayList<Utilisateur>) dalU.selectAll();
		System.out.println(listeEnchere);
		System.out.println(listeUtilisateur);
	}
}
