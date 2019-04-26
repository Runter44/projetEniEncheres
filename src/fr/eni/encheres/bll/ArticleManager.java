package fr.eni.encheres.bll;

import java.util.List;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.criteres.CritArticle;
import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.dal.impl.DAOArticle;

public class ArticleManager {
	
	private static DAOArticle daoArticle;
	
	public ArticleManager() {
		daoArticle = DAOFactory.getDAOArticle();
	}
	
	public Article getArticleById(int id) {
		return daoArticle.find(id);
	}
	
	public Article getArticleByName(String name) {
		return daoArticle.findByName(name);
	}

	public List<Article> getAllArticles() {
		return daoArticle.findAll();
	}
	
	public Article addArticle(Article article) {
		return daoArticle.insert(article);
	}
	
	public boolean updateArticle(Article article) {
		return daoArticle.update(article);
	}
	
	public boolean deleteArticle(Article article) {
		return daoArticle.remove(article);
	}
	
	public List<Article> getListArticleByCrit(CritArticle critArticle){
		return daoArticle.findListCrit(critArticle);
	}
	
}
