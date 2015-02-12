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
public class Constants {
	public static final String FILE_SEP = System.getProperty("file.separator");
	public static final String PORT = System.getProperty("os.name").contains("Windows") ? "8084" : "8080";
	public static final String HOME = System.getProperty("user.home");

	public static final int UPLOAD_SIZE_THRESHOLD = 100 * 1024;
	public static final int MAX_UPLOAD_SIZE = 4000000 * 1024;

	public static final String TEMP_DIR = ".."+FILE_SEP+"webapps"+FILE_SEP+"temp"+FILE_SEP;
	public static final String PICTURE_DIR = ".."+FILE_SEP+"webapps"+FILE_SEP+"Cornea_Grader"+FILE_SEP+"pictures"+FILE_SEP;
	public static final String PICTURE_PATH = "http://localhost:"+PORT+"/Cornea_Grader/img";
	public static final String DESKTOP_PATH = HOME + FILE_SEP + "Desktop" + FILE_SEP;

	public static final String NOT_EXCEL = "You can only upload excel files that have excel extensions";
	public static final String NOT_PICTURE = "You can only upload files that have picture extensions (jpeg, jpg, png, gif)";
	public static final String IMPROPER_EXCEL_FORMAT = "The excel file uploaded was not formatted properly";
	public static final String TAKEN_USERNAME = "That user name has been taken";
	public static final String PASSWORDS_DONT_MATCH = "Passwords do not match";
	public static final String INCORRECT_NAME_PASS = "Incorrect name or password";
}
