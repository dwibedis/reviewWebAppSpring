package com.review.dbapi;

import java.sql.ResultSet;

/**
 * Interface for specifying actions implemented for interaction with DB.
 * 
 * @author satyad
 *
 */
public interface MovieDB {

	/**
	 * 
	 * @param hashCode
	 * @return
	 */
	public ResultSet read(int hashCode);

	/**
	 * 
	 * @param userName
	 * @param hashCode
	 * @return
	 */
	public ResultSet readCount(String userName, int hashCode);

	/**
	 * 
	 * @param id
	 * @param userName
	 * @param rating
	 * @param movieName
	 */
	public void update(int id, String userName, int rating, String movieName);

	/**
	 * 
	 * @param id
	 * @param userName
	 * @param rating
	 * @param movieName
	 */
	public void insert(int id, String userName, int rating, String reviewStatement);

}
