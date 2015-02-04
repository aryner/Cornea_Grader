/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;


import java.sql.*;

/**
 *
 * @author aryner
 */
public abstract class Model {
	public static final int USER = 1;
	public static final int PICTURE = 2;
	public static final int GRADE = 3;
	
	public static Model getModel(ResultSet resultSet)
		throws javax.naming.NamingException, SQLException {
		return null;
	}
}
