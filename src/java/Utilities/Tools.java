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
	public final String FILE_SEP = System.getProperty("file.separator");
	public final String PORT = System.getProperty("os.name").contains("Windows") ? "8084" : "8080";
}
