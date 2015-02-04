/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

import Utilities.*;
import java.util.*;

/**
 *
 * @author aryner
 */
public class User extends Model {
	private int id;
	private String name;
	private int access;
	private String password;

	public static final int GRADER = 1;
	public static final int UPLOADER = 2;
	public static final int GRADER_UPLOADER = 3;

	public User(int id, String name, int access, String password) {
		this.id = id;
		this.name = name;
		this.access = access;
		this.password = password;
	}

	public static User getModel(java.sql.ResultSet resultSet) 
		throws java.sql.SQLException, javax.naming.NamingException {
		return new User( resultSet.getInt("id"),resultSet.getString("name"),
				 resultSet.getInt("access"),resultSet.getString("password"));
	}

	public static User register(String name, int access, String password) {
		String getQuery = "SELECT * FROM user WHERE name='"+name+"'";
		ArrayList<Model> users = SQLCommands.queryModel(getQuery, Model.USER);

		if(!users.isEmpty()) return null;

		String insertQuery = "INSERT INTO user (name, access, password) VALUES ("+
			       "'"+name+"', '"+access+"', '"+password+"')";
		SQLCommands.update(insertQuery);
		
		return (User)SQLCommands.queryModel(getQuery, Model.USER).get(0);
	}

	public static User login(String name, String password) {
		String getQuery = "SELECT * FROM user WHERE name='"+name+"' AND password='"+password+"'";
		ArrayList<Model> users = SQLCommands.queryModel(getQuery, Model.USER);

		if(users.isEmpty()) return null;
		return (User)users.get(0);
	}

	public boolean canUpload() {
		return (this.access == UPLOADER) || (this.access == GRADER_UPLOADER);
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the access
	 */
	public int getAccess() {
		return access;
	}

	/**
	 * @param access the access to set
	 */
	public void setAccess(int access) {
		this.access = access;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
}
