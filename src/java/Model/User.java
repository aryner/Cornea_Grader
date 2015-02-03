/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

/**
 *
 * @author aryner
 */
public class User {
	private int id;
	private String name;
	private int access;
	private int password;

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
	public int getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(int password) {
		this.password = password;
	}
	
}
