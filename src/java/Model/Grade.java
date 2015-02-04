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
public class Grade extends Model{
	private int id;
	private int grader_id;
	private int picture_id;
	private int grade;
	private int quality;

	public static final int YES_CORNEAL_OPACITY = 1;
	public static final int NO_CORNEAL_OPACITY = 0;
	public static final int POOR = 0;
	public static final int ACCEPTABLE = 1;
	public static final int GOOD = 2;

	public Grade(int id, int grader_id, int picture_id, int grade, int quality) {
		this.id = id;
		this.grader_id = grader_id;
		this.picture_id = picture_id;
		this.grade = grade;
		this.quality = quality;
	}

	public static Grade getModel(java.sql.ResultSet resultSet) 
		throws java.sql.SQLException, javax.naming.NamingException {
		return new Grade (
			resultSet.getInt("id"),resultSet.getInt("grader_id"),
			resultSet.getInt("picture_id"),resultSet.getInt("grade"),
			resultSet.getInt("quality")
		);
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
	 * @return the grader_id
	 */
	public int getGrader_id() {
		return grader_id;
	}

	/**
	 * @param grader_id the grader_id to set
	 */
	public void setGrader_id(int grader_id) {
		this.grader_id = grader_id;
	}

	/**
	 * @return the picture_id
	 */
	public int getPicture_id() {
		return picture_id;
	}

	/**
	 * @param picture_id the picture_id to set
	 */
	public void setPicture_id(int picture_id) {
		this.picture_id = picture_id;
	}

	/**
	 * @return the grade
	 */
	public int getGrade() {
		return grade;
	}

	/**
	 * @param grade the grade to set
	 */
	public void setGrade(int grade) {
		this.grade = grade;
	}

	/**
	 * @return the quality
	 */
	public int getQuality() {
		return quality;
	}

	/**
	 * @param quality the quality to set
	 */
	public void setQuality(int quality) {
		this.quality = quality;
	}
	
}
