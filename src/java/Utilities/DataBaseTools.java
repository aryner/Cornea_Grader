/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Utilities;

import java.util.*;
import Model.*;

/**
 *
 * @author aryner
 */
public class DataBaseTools {
	public static void insertAndUpdateRecords(ArrayList<String> fileNames) {
		ArrayList<String> alreadyUploaded = findAlreadyUploaded(fileNames);
	}

	public static void insertAndUpdateRecords(ArrayList<String> fileNames, ArrayList<Integer> dslr, ArrayList<Integer> hdr, ArrayList<Integer> exposure) {
		ArrayList<ArrayList> alreadyUploaded = findAlreadyUploaded(fileNames, dslr, hdr, exposure);

		//update alreadyUploaded
		if(!alreadyUploaded.get(0).isEmpty()) {
			updateRecords(alreadyUploaded.get(0), alreadyUploaded.get(1), alreadyUploaded.get(2), alreadyUploaded.get(3));
		}

		//insert new files
		if(!fileNames.isEmpty()) {
			insertRecords(fileNames, dslr, hdr, exposure);
		}
	}

	public static void updateRecords(ArrayList<String> fileNames, ArrayList<Integer> dslr, ArrayList<Integer> hdr, ArrayList<Integer> exposure) {
		String query = "UPDATE picture SET patient_number = CASE name ";
		String queryEnd = "";
		for(String fileName : fileNames) {
			query += " WHEN '"+fileName+"' THEN '"+Tools.extractPatientNumber(fileName)+"' ";
			if(queryEnd.length() > 0) queryEnd += ", ";
			queryEnd += "'"+fileName+"'";
		}
		query += " END WHERE name IN ("+queryEnd+")";
		SQLCommands.update(query);

		query = "UPDATE picture SET DSLR_cellscope = CASE name "+
			generateCases(fileNames, dslr)+" END WHERE name IN ("+queryEnd+")";
		SQLCommands.update(query);

		query = "UPDATE picture SET HDR = CASE name "+
			generateCases(fileNames, hdr)+" END WHERE name IN ("+queryEnd+")";
		SQLCommands.update(query);

		query = "UPDATE picture SET plus_one_exposure = CASE name "+
			generateCases(fileNames, exposure)+" END WHERE name IN ("+queryEnd+")";
		SQLCommands.update(query);
	}

	private static String generateCases(ArrayList<String> when, ArrayList then) {
		String query = "";
		for(int i=0; i<when.size(); i++) {
			query += " WHEN '"+when.get(i)+"' THEN '"+ then.get(i).toString() +"' ";
		}
		return query;
	}

	public static void insertRecords(ArrayList<String> fileNames, ArrayList<Integer> dslr, ArrayList<Integer> hdr, ArrayList<Integer> exposure) {
		String query = "INSERT INTO picture (name, patient_number, uploaded, DSLR_cellscope, HDR, plus_one_exposure) VALUES ";
		for(int i=0; i<fileNames.size(); i++) {
			if(i>0) query += ", ";
			query += "('"+fileNames.get(i)+"', '"+Tools.extractPatientNumber(fileNames.get(i))+"', '"+
				Picture.NOT_UPLOADED+"', '"+dslr.get(i)+"', '"+hdr.get(i)+"', '"+exposure.get(i)+"')";
		}

		SQLCommands.update(query);
	}

	private static ArrayList<String> findAlreadyUploaded(ArrayList<String> fileNames) {
		String query = "SELECT * FROM picture WHERE ";
		for(int i=0; i<fileNames.size(); i++) {
			if(i>0) query += " OR ";
			query += " name='"+fileNames.get(i)+"' ";
		}

		ArrayList<String> alreadyUploaded = new ArrayList<String>();
		ArrayList<Model> inDb = SQLCommands.queryModel(query, Model.PICTURE);

		for(Model model : inDb) {
			String picName = ((Picture)model).getName();
			if(fileNames.contains(picName)) {
				alreadyUploaded.add(fileNames.remove(fileNames.lastIndexOf(picName)));
			}
		}

		return alreadyUploaded;
	}

	private static ArrayList<ArrayList> findAlreadyUploaded(ArrayList<String> fileNames, ArrayList<Integer> dslr, ArrayList<Integer> hdr, ArrayList<Integer> exposure) {
		String query = "SELECT * FROM picture WHERE ";
		for(int i=0; i<fileNames.size(); i++) {
			if(i>0) query += " OR ";
			query += " name='"+fileNames.get(i)+"' ";
		}

		ArrayList<Model> inDb = SQLCommands.queryModel(query, Model.PICTURE);
		ArrayList<ArrayList> alreadyUploaded = new ArrayList<ArrayList>();
		alreadyUploaded.add(new ArrayList<String>());
		for(int i=0; i<3; i++) alreadyUploaded.add(new ArrayList<Integer>());

		for(Model model : inDb) {
			String picName = ((Picture)model).getName();
			if(fileNames.contains(picName)) {
				int index = fileNames.lastIndexOf(picName);
				alreadyUploaded.get(0).add(fileNames.remove(index));
				alreadyUploaded.get(1).add(dslr.remove(index));
				alreadyUploaded.get(2).add(hdr.remove(index));
				alreadyUploaded.get(3).add(exposure.remove(index));
			}
		}

		return alreadyUploaded;
	}
}
