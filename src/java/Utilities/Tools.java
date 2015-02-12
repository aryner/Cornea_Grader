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

	public static ArrayList getNeighbors(ArrayList arrayList, int index) {
		ArrayList result = new ArrayList();

		//add the element before or the last if this is the first
		// element.  If there is only 1 element in the list add nothing
		if(index>0) {
			result.add(arrayList.get(index-1));
		} else if(arrayList.size() > 1) {
			result.add(arrayList.get(arrayList.size()-1));
		}

		//add the element after or the first element if this is the last
		// element.  If there is only 1 element in the list add nothing
		if(index<arrayList.size()-1) {
			result.add(arrayList.get(index+1));
		} else if(arrayList.size() > 1) {
			result.add(arrayList.get(0));
		}

		return result;
	}

	public static String getCSVFileName() {
		return Constants.DESKTOP_PATH + "grades"+getFormattedDate()+".csv";
	}

	public static String getFormattedDate() {
		String result = "";
		Date date = new Date(System.currentTimeMillis());
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		int day = cal.get(Calendar.DAY_OF_MONTH);
		if(day < 10) {
			result += "0";
		}
		result += day;

		int month = cal.get(Calendar.MONTH);

		switch (month) {
			case 0:
				result += "jan";
				break;
			case 1:
				result += "feb";
				break;
			case 2:
				result += "mar";
				break;
			case 3:
				result += "apr";
				break;
			case 4:
				result += "may";
				break;
			case 5:
				result += "jun";
				break;
			case 6:
				result += "jul";
				break;
			case 7:
				result += "aug";
				break;
			case 8:
				result += "sep";
				break;
			case 9:
				result += "oct";
				break;
			case 10:
				result += "nov";
				break;
			case 11:
				result += "dec";
				break;
		}

		result += cal.get(Calendar.YEAR);

		return result;
	}
}
