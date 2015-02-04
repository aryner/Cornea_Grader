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
	public static void insertAndUpdateRecords(ArrayList<String> fileNames, ArrayList<Integer> dslr, ArrayList<Integer> hdr, ArrayList<Integer> exposure) {
		ArrayList<String> alreadyUploaded = findAlreadyUploaded(fileNames);

		//update alreadyUploaded
		if(!alreadyUploaded.isEmpty()) {
			updateRecords(alreadyUploaded, dslr, hdr, exposure);
		}

		//insert new files
		insertRecords(fileNames, dslr, hdr, exposure);
	}

	public static void updateRecords(ArrayList<String> fileNames, ArrayList<Integer> dslr, ArrayList<Integer> hdr, ArrayList<Integer> exposure) {
		String query = "UPDATE picture SET patient_number = CASE name ";
		String queryEnd = "";
		for(String fileName : fileNames) {
			query += " WHEN '"+fileName+"' THEN '"+Tools.extractPatientNumber(fileName)+"' ";
			if(queryEnd.length() > 0) queryEnd += ", ";
			queryEnd += "'"+fileName+"'";
		}
		query += " END, SET DSLR_cellscope = CASE name ";
		for(int i=0; i<fileNames.size(); i++) {
			query += " WHEN '"+fileNames.get(i)+"' THEN '"+ dslr.get(i) +"' ";
		}
		query += " END, SET HDR = CASE name ";
		for(int i=0; i<fileNames.size(); i++) {
			query += " WHEN '"+fileNames.get(i)+"' THEN '"+ hdr.get(i) +"' ";
		}
		query += " END, SET plus_one_exposure = CASE name ";
		for(int i=0; i<fileNames.size(); i++) {
			query += " WHEN '"+fileNames.get(i)+"' THEN '"+ exposure.get(i) +"' ";
		}
		query += " END WHERE name IN ("+queryEnd+")";

		SQLCommands.update(query);
	}

	public static void insertRecords(ArrayList<String> fileNames, ArrayList<Integer> dslr, ArrayList<Integer> hdr, ArrayList<Integer> exposure) {
		String query = "INSERT INTO picture (name, patient_number, uploaded, DSLR_cellscope, HDR, plus_one_exposure) VALUES ";
	}

	public static ArrayList<String> findAlreadyUploaded(ArrayList<String> fileNames) {
		String query = "SELECT * FROM picture WHERE ";
		for(int i=0; i<fileNames.size(); i++) {
			if(i>0) query += " OR ";
			query += " name='"+fileNames.get(i)+"' ";
		}

		ArrayList<Model> inDb = SQLCommands.queryModel(query, Model.PICTURE);
		ArrayList<String> alreadyUploaded = new ArrayList<String>();

		for(Model pic : inDb) {
			String picName = ((Picture)pic).getName();
			if(fileNames.contains(picName)) {
				fileNames.remove(picName);
				alreadyUploaded.add(picName);
			}
		}

		return alreadyUploaded;
	}
}
