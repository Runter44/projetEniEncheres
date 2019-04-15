package fr.eni.encheres.bll;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Vente;
import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.dal.InterfaceDAO;


public class ScheduleManager {

	static ScheduleManager instance;

	private final ScheduledExecutorService scheduler =
			Executors.newScheduledThreadPool(1);


	private ScheduleManager() {

		ScheduleCheckVentes();
		System.out.println("Demarrage du compte a rebour");
	}

	public static ScheduleManager getInstance() {

		if (instance == null)  {

			instance = new ScheduleManager();

		}

		return instance;

	}

	public void ScheduleCheckVentes() {

		//Tache a répéter
		final Runnable beeper = new Runnable() {
			public void run() {
				ScheduleManager.this.validerVentes();
			}
		};

		//Combien de temps à attendre pour qu'il soit Minuit
		LocalDateTime todayMidnight = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT).plusDays(1);

		//l'attente pour lancer la validation des ventes pour la première fois
		long waitDelay =(Duration.between(LocalDateTime.now(),todayMidnight).toMinutes())*60;
		
		//l'attente pour relancer la validation après la première fois
		long restartDelay = 24*60*60;

		//			******	TEST	******
		//waitDelay=10;
		//restartDelay=10;
		//			******	/TEST	******

		//Appel de la tache à répéter à Minuit, toutes les 24 heures
		final ScheduledFuture<?> beeperHandle =
				scheduler.scheduleAtFixedRate(beeper, waitDelay, restartDelay, TimeUnit.SECONDS);

		//Arrêt de la répétition de la tâche (ici, jamais)
		/*
		scheduler.schedule(new Runnable() {
			public void run() { beeperHandle.cancel(true); }
		}, 1 * 60, TimeUnit.SECONDS);
		 */
	}

	//delai initial : time.now.at(23:59) MOINS time.now 
	//delai entre 2 : 24*60*60

	public void validerVentes() {

System.out.println("Debut de la tache repetable : Fin Vente"); 
		//sql : toutes les ventes dont la date de fin est hier
		LocalDateTime yesterday = LocalDateTime.now().minusDays(1);

		Vente venteVide = new Vente();
		venteVide.setDatesFinEncheres(yesterday);
//	***	TEST	***
//venteVide.setDatesFinEncheres(LocalDateTime.now());
//	***	TEST	***
		InterfaceDAO dalVente = DAOFactory.getVenteDAO();

		List<Vente> ventes = dalVente.selectAll(venteVide, "SELECTBYDATE");

		boolean validation = false;

		//pour chaque vente à cette date: 
		for (Vente venteToEnd : ventes) {
System.out.println("Vente : " + venteToEnd.getNoVente()+" "+venteToEnd.getNomArticle());
			//check si l'enchere la plus haute est valide (encherisseur a le nombre de points, date est avant la fin de la vente)
			validation = false;
			Enchere enchereSuperieure = EnchereManager.getInstance().getHauteEnchere(venteToEnd.getNoVente(), "SELECTALLBYVENTE");

			//tant que l'on a pas validé cette vente ou que l'on a plus d'enchères
			while (validation==false && enchereSuperieure != null) {
System.out.println("Enchere : "+ enchereSuperieure.getUser().getPseudo()+" => "+enchereSuperieure.getVente().getMiseAPrix());
				//si oui : enlever les points,créditer les points, "cloturer vente"
				if (true || enchereSuperieure.getDateEnchere().compareTo(LocalDateTime.now() ) >=0 ) {
System.out.println("Validation");
					//enchereSuperieure.getUser().setCredit(enchereSuperieure.getUser().getCredit() - enchereSuperieure.getValeur());
					venteToEnd.getVendeur().setCredit(venteToEnd.getVendeur().getCredit() + enchereSuperieure.getValeur());

					EnchereManager.getInstance().setUtilisateur(venteToEnd.getVendeur());
					
					venteToEnd.setPrixVente(enchereSuperieure.getValeur());

					EnchereManager.getInstance().setVente(venteToEnd);

					validation = true;

					//	??? effacer les autres encheres ???

				}

				//Si non : effacer enchere, chercher enchere suivante
				else {

					enchereSuperieure.getUser().setCredit(enchereSuperieure.getUser().getCredit() + enchereSuperieure.getValeur());

					EnchereManager.getInstance().setUtilisateur(enchereSuperieure.getUser());
					
					EnchereManager.getInstance().remEnchere(enchereSuperieure,"DELETE");

					enchereSuperieure = EnchereManager.getInstance().getHauteEnchere(venteToEnd.getNoVente(), "SELECTALLBYVENTE");

					enchereSuperieure.getUser().setCredit(enchereSuperieure.getUser().getCredit() - enchereSuperieure.getValeur());
					
					EnchereManager.getInstance().setUtilisateur(enchereSuperieure.getUser());

				}



			}



		}


	}

}
