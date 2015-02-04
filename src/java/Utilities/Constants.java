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

	public static final int UPLOAD_SIZE_THRESHOLD = 100 * 1024;
	public static final int MAX_UPLOAD_SIZE = 4000000 * 1024;

	public static final String TEMP_DIR = ".."+FILE_SEP+"temp";

	public static final String NOT_EXCEL = "You can only upload excel files that have excel extensions";
	public static final String IMPROPER_EXCEL_FORMAT = "The excel file uploaded was not formatted properly";
}
