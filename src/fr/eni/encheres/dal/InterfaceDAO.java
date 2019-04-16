package fr.eni.encheres.dal;

import java.util.List;

public interface InterfaceDAO<T> {

	public T find(int id);
	public List<T> findAll();
	public T insert(T t);
	public boolean update(T t);
	public boolean remove(T t);

}
