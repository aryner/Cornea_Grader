/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Utilities;

/**
 *
 * @author aryner
 */
public class Tools {
	public static String getExtension(String fileName) {
		return fileName.substring(fileName.lastIndexOf(".")+1, fileName.length());
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
}
