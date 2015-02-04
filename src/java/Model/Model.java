/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;


import java.sql.*;
import javax.naming.NamingException;

/**
 *
 * @author aryner
 */
public abstract class Model {
	public static final int USER = 1;
	public static final int PICTURE = 2;
	public static final int GRADE = 3;
	
	public static Model getModel(ResultSet resultSet)
		throws NamingException, SQLException {
		return null;
	}

	public static Model getModel(ResultSet resultSet, int modelType) 
		throws NamingException, SQLException {
		switch (modelType) {
			case Model.USER:
				return User.getModel(resultSet);
			case Model.PICTURE:
				return Picture.getModel(resultSet);
			case Model.GRADE:
				return Grade.getModel(resultSet);
			default:
				return null;
		}
	}
}
