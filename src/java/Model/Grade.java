/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

import java.util.*;
import Utilities.*;
import java.io.*;
import javax.servlet.http.HttpServletRequest;

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

	public static final int YES_CORNEAL_OPACITY = 3;
	public static final int PROBABLY_CORNEAL_OPACITY = 2;
	public static final int POSSIBLY_CORNEAL_OPACITY = 1;
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

	public static void submitGrade(HttpServletRequest request, User user) {
		String patient_number = request.getParameter("patient_number");
		String grade_type = request.getParameter("grade_type");
		String side = request.getParameter("side");
		String grade = request.getParameter("grade");
		String quality = request.getParameter("quality");

		String query = "INSERT INTO grade (grader_id, patient_number, grade_type, side, grade, quality) "+
				"VALUES ('"+user.getId()+"', '"+patient_number+"', '"+grade_type+"', "+
				"'"+side+"', '"+grade+"', '"+quality+"')";

		SQLCommands.update(query);
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
			int [] gradedTypes = {0,0,0,0};
			for(Grade grade : alreadyGraded) 
				gradedTypes[grade.getGrade_type()]++;
			if(gradedTypes[NOT_PLUS_ONE]<2) notGradedTypes.add(NOT_PLUS_ONE);
			if(gradedTypes[PLUS_ONE]<2) notGradedTypes.add(PLUS_ONE);
			if(gradedTypes[HDR]<2) notGradedTypes.add(HDR);
			if(gradedTypes[DSLR]<2) notGradedTypes.add(DSLR);
		}

		Random rand = new Random();

		if(notGradedTypes.isEmpty()) return -1;
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

	public static ArrayList<String> get_CSV_lines() {
		String query = "SELECT * FROM grade";
		ArrayList<Grade> grades = (ArrayList)SQLCommands.queryModel(query, Model.GRADE);
		Map<Integer, String> users = User.userNamesAndIds();

		ArrayList<String> lines = new ArrayList<String>();
		lines.add("grader, patient number, grade type, side, grade, quality");
		for(Grade grade : grades) {
			String line = users.get(grade.getGrader_id())+", "+grade.getPatient_number()+", "+
					grade.getGrade_type()+", "+grade.getSide()+", "+ grade.getGrade()+", "+
					grade.getQuality();
			lines.add(line);
		}

		return lines;
	}

	public static void printGradeCSV() {
		String fileName = Tools.getCSVFileName();
		ArrayList<String> csvLines = get_CSV_lines();

		try {
			new File(Constants.DESKTOP_PATH + fileName).delete();
			FileOutputStream fileOut = new FileOutputStream(fileName);
			ArrayList<String> lines = get_CSV_lines();

			for(String line : lines) {
				fileOut.write((line+"\n").getBytes());
			}

			fileOut.close();
		}
		catch(FileNotFoundException e) {
			e.printStackTrace(System.out);
		}
		catch(IOException e) {
			e.printStackTrace(System.out);
		}
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
