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
public class User extends Model {
	private int id;
	private String name;
	private int access;
	private String password;

	public User() {}

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
