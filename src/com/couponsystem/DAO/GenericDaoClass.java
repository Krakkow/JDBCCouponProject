package com.couponsystem.DAO;

import java.util.Collection;

import com.couponsystem.exceptions.DAOException;

public interface GenericDaoClass<T> 
{
	public abstract void create(T t) throws DAOException;
	public abstract T read(long t) throws DAOException;
	public abstract boolean update(T t) throws DAOException;
	public abstract void delete(long t) throws DAOException;
	public abstract Collection <T> getAll() throws DAOException;

	
	
}
