/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

import java.util.*;
import Utilities.*;


/**
 *
 * @author aryner
 */
public class Grade extends Model{
	private int id;
	private int grader_id;
	private int patient_number;
	private int grade_type;
	private int side;
	private int grade;
	private int quality;

	public static final int NOT_PLUS_ONE = 0;
	public static final int PLUS_ONE = 1;
	public static final int HDR = 2;
	public static final int DSLR = 3;
	public static final int RIGHT = Picture.RIGHT;
	public static final int LEFT = Picture.LEFT;
	public static final int YES_CORNEAL_OPACITY = 1;
	public static final int NO_CORNEAL_OPACITY = 0;
	public static final int POOR = 0;
	public static final int ACCEPTABLE = 1;
	public static final int GOOD = 2;

	public Grade(int id, int grader_id, int picture_id, int grade_type, int side, int grade, int quality) {
		this.id = id;
		this.grader_id = grader_id;
		this.patient_number = picture_id;
		this.grade_type = grade_type;
		this.side = side;
		this.grade = grade;
		this.quality = quality;
	}

	public static Grade getModel(java.sql.ResultSet resultSet) 
		throws java.sql.SQLException, javax.naming.NamingException {
		return new Grade (
			resultSet.getInt("id"),resultSet.getInt("grader_id"),
			resultSet.getInt("patient_number"),resultSet.getInt("grade_type"),
			resultSet.getInt("side"),resultSet.getInt("grade"),
			resultSet.getInt("quality")
		);
	}

	public static int getNextPatient(User user) {
		String query = "SELECT * FROM picture WHERE patient_number NOT IN (SELECT patient_number "+
				"FROM grade WHERE grader_id="+user.getId()+" GROUP BY patient_number HAVING COUNT(*)=8)"+
				" AND uploaded="+Picture.UPLOADED+" AND right_left>0";
		ArrayList<Picture> pictures = (ArrayList)SQLCommands.queryModel(query, Model.PICTURE);

		if(pictures.isEmpty()) return -1;
		return pictures.get(0).getPatient_number();
	}

	public static int getGradeType(User user, int patient_num) {
		if(patient_num < 0) return -1;

		String query = "SELECT * FROM grade WHERE grader_id="+user.getId()+" AND "+
				"patient_number="+patient_num;
		ArrayList<Grade> alreadyGraded = (ArrayList)SQLCommands.queryModel(query, Model.GRADE);
		ArrayList<Integer> notGradedTypes = new ArrayList<Integer>();

		if(alreadyGraded.isEmpty()) {
			notGradedTypes.add(NOT_PLUS_ONE);
			notGradedTypes.add(PLUS_ONE);
			notGradedTypes.add(HDR);
			notGradedTypes.add(DSLR);
		}
		else {
			ArrayList<Integer> gradedTypes = new ArrayList<Integer>();
			for(Grade grade : alreadyGraded) gradedTypes.add(grade.getGrade_type());
			if(!gradedTypes.contains(NOT_PLUS_ONE)) notGradedTypes.add(NOT_PLUS_ONE);
			if(!gradedTypes.contains(PLUS_ONE)) notGradedTypes.add(PLUS_ONE);
			if(!gradedTypes.contains(HDR)) notGradedTypes.add(HDR);
			if(!gradedTypes.contains(DSLR)) notGradedTypes.add(DSLR);
		}

		Random rand = new Random();

		return notGradedTypes.get(rand.nextInt((notGradedTypes.size())));
	}

	public static int getSide(User user, int patient_num, int grade_type) {
		String query = "SELECT * FROM grade WHERE grader_id="+user.getId()+" AND "+
				"patient_number="+patient_num+" AND grade_type="+grade_type;
		ArrayList<Grade> graded = (ArrayList)SQLCommands.queryModel(query, Model.GRADE);

		if(graded.isEmpty()) return (int)(Math.random()*2);
		if(graded.get(0).getSide()==RIGHT) return LEFT;
		return RIGHT;
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
	 * @return the patient_number
	 */
	public int getPatient_number() {
		return patient_number;
	}

	/**
	 * @param patient_number the patient_number to set
	 */
	public void setPatient_number(int patient_number) {
		this.patient_number = patient_number;
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

	/**
	 * @return the grade_type
	 */
	public int getGrade_type() {
		return grade_type;
	}

	/**
	 * @param grade_type the grade_type to set
	 */
	public void setGrade_type(int grade_type) {
		this.grade_type = grade_type;
	}

	/**
	 * @return the side
	 */
	public int getSide() {
		return side;
	}

	/**
	 * @param side the side to set
	 */
	public void setSide(int side) {
		this.side = side;
	}
	
}
