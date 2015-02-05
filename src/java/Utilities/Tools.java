/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Utilities;

import java.util.*;

/**
 *
 * @author aryner
 */
public class Tools {
	public static String getExtension(String fileName) {
		return fileName.substring(fileName.lastIndexOf(".")+1, fileName.length());
	}

	public static boolean pictureExtension(String extension) {
		extension = extension.toLowerCase();
		if(extension.equals("jpg"))
			return true;
		if(extension.equals("jpeg"))
			return true;
		if(extension.equals("png"))
			return true;
		return extension.equals("gif");
	}

	public static boolean excelExtension(String extension) {
		extension = extension.toLowerCase();
		if(extension.equals("xls"))
			return true;
		if(extension.equals("xlt"))
			return true;
		if(extension.equals("xlm"))
			return true;
		if(extension.equals("xlsx"))
			return true;
		if(extension.equals("xlsm"))
			return true;
		if(extension.equals("xltx"))
			return true;
		if(extension.equals("xltm"))
			return true;
		if(extension.equals("xlsb"))
			return true;
		if(extension.equals("xla"))
			return true;
		if(extension.equals("xlam"))
			return true;
		if(extension.equals("xll"))
			return true;
		return extension.equals("xlw");
	}

	public static String singleQuoteCommaSeparated(ArrayList<String> values) {
		String result = "";
		for(String value : values) {
			result += "'"+value+"', ";
		}

		return result.substring(0, result.lastIndexOf(","));
	}

	public static void populateArrayList(ArrayList<Integer> arrayList, int value, int count) {
		for(int i=0; i<count; i++) arrayList.add(value);
	}

	public static void setArray(int [] array, int set) {
		for(int i=0; i<array.length; i++) array[i] = set;
	}

	public static boolean arrayContains(int [] hayStack, int needle) {
		for(int check : hayStack) 
			if(check == needle)
				return true;
		return false;
	}

	public static String extractPatientNumber(String fileName) {
		return fileName.substring(2,5);
	}

	public static ArrayList<String> getPatientNumbers(ArrayList<String> names) {
		ArrayList<String> numbers = new ArrayList<String>();
		for(String name : names) numbers.add(extractPatientNumber(name));

		return numbers;
	}
}
