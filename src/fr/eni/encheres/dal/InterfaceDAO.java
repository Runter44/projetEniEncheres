package fr.eni.encheres.dal;

import java.util.List;
import java.util.Map;

public interface InterfaceDAO<T> {
	
	public Map<String,String> CONSTANTE = null; 
	
	public T select(T t, String requete);
	public List<T> selectAll();
	public List<T> selectAll(T t, String requete);
	public int insert(T t);
	public boolean update(T t);
	public boolean remove(int id);	
	public boolean remove(T t, String requete);	
}
