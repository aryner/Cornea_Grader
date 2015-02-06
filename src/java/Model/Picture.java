/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

import java.util.*;
import java.io.*;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.fileupload.FileItem; 
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload; 
import org.apache.poi.xssf.usermodel.*; 
import Utilities.*;

/**
 *
 * @author aryner
 */
public class Picture extends Model{
	private int id;
	private String name;
	private int patient_number;
	private int uploaded;
	private int dslr_cellscope;
	private int hdr;
	private int plus_one_exposure;
	private int right_left;

	public static final int UNKNOWN = -1;

	public static final int DSLR = 0;
	public static final int CELLSCOPE = 1;

	public static final int NOT_HDR = 0;
	public static final int HDR = 1;

	public static final int NOT_PLUS_ONE = 0;
	public static final int PLUS_ONE_EXPOSURE = 1;

	public static final int NOT_UPLOADED = 0;
	public static final int UPLOADED = 1;

	public static final int EXCEL_UPLOAD = 0;
	public static final int PICTURE_UPLOAD = 1;

	public Picture( int id, String name, int patient_number, int uploaded, 
			int dslr_cellscope, int hdr, int plus_one_exposure, int right_left) {
		this.id = id;
		this.name = name;
		this.patient_number = patient_number;
		this.uploaded = uploaded;
		this.dslr_cellscope = dslr_cellscope;
		this.hdr = hdr;
		this.plus_one_exposure = plus_one_exposure;
		this.right_left = right_left;
	}

	public static Picture getPicture(String fileName) {
		String query = "SELECT * FROM picture WHERE name='"+fileName+"'";
		return (Picture)(SQLCommands.queryModel(query,Model.PICTURE).get(0));
	}

	public static Picture getModel(java.sql.ResultSet resultSet) 
		throws java.sql.SQLException, javax.naming.NamingException {
		return new Picture(
				resultSet.getInt("id"),resultSet.getString("name"),
				resultSet.getInt("patient_number"),resultSet.getInt("uploaded"),
				resultSet.getInt("DSLR_cellscope"),resultSet.getInt("HDR"),
				resultSet.getInt("plus_one_exposure"),resultSet.getInt("right_left")
		);
	}

	public static ArrayList<String> upload(HttpServletRequest request, int type) {
		ArrayList<String> errors = new ArrayList<String>();
		ArrayList<String> pictureNames = new ArrayList<String>();

		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(Constants.UPLOAD_SIZE_THRESHOLD);
		new File(Constants.TEMP_DIR).mkdirs();
		factory.setRepository(new File(Constants.TEMP_DIR));

		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setSizeMax(Constants.MAX_UPLOAD_SIZE);

		try {
			List fileItems = upload.parseRequest(request);
			Iterator i = fileItems.iterator();

			while(i.hasNext()) {
				FileItem fileItem = (FileItem)i.next();
				String fileName = fileItem.getName();

				if(type == EXCEL_UPLOAD) 
					errors.addAll(upload_excel(fileName, fileItem));
				else if(type == PICTURE_UPLOAD) 
					errors.addAll(upload_picture(fileName, fileItem, pictureNames));
			}
		} catch(org.apache.commons.fileupload.FileUploadException e) {
			e.printStackTrace(System.out);
		} catch(Exception e) {
			e.printStackTrace(System.out);
		}

		if(type == PICTURE_UPLOAD) DataBaseTools.insertAndUpdateRecords(pictureNames);

		return errors;
	}

	private static ArrayList<String> upload_picture(String fileName, FileItem fileItem, ArrayList<String> pictureNames) 
			throws Exception{
		ArrayList<String> errors = new ArrayList<String>();

		if(Tools.pictureExtension(Tools.getExtension(fileName))) {
			new File(Constants.PICTURE_DIR).mkdirs();
			File file = new File(Constants.PICTURE_DIR+fileName);
			fileItem.write(file);

			pictureNames.add(fileName);
		}
		else errors.add(Constants.NOT_PICTURE);

		return errors;
	}

	private static ArrayList<String> upload_excel(String fileName, FileItem fileItem)
			throws Exception {
		ArrayList<String> errors = new ArrayList<String>();

		if(Tools.excelExtension(Tools.getExtension(fileName))) {
			new File(Constants.TEMP_DIR).mkdirs();
			File file = new File(Constants.TEMP_DIR+fileName);
			fileItem.write(file);

			errors.addAll(enterUploadedData(fileName));
			file.delete();
		}
		else errors.add(Constants.NOT_EXCEL);

		return errors;
	}

	private static ArrayList<String> enterUploadedData(String fileName) {
		ArrayList<String> errors = new ArrayList<String>();

		try {
			FileInputStream file = new FileInputStream(Constants.TEMP_DIR+fileName);
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			XSSFSheet sheet = workbook.getSheetAt(0);

			int rowStart = sheet.getFirstRowNum();
			int rowEnd = sheet.getLastRowNum()+1;
			int colStart = sheet.getRow(rowStart).getFirstCellNum();
			int colEnd = sheet.getRow(rowStart).getLastCellNum();

			int [] indices = ExcelTools.getColumnIndices(colStart, colEnd, sheet.getRow(rowStart));
			if(Tools.arrayContains(indices, -1)) {
				errors.add(Constants.IMPROPER_EXCEL_FORMAT);
				return errors;
			}

			errors.addAll(ExcelTools.readFile(indices, sheet, rowStart+1, rowEnd));

		} catch (IOException e) {
			e.printStackTrace(System.out);
		}
		return errors;
	}

	public static String assign_right_left(HttpServletRequest request) {

		return request.getParameter("nextFile");
	}

	public static ArrayList<Model> getUploadedPictures() {
		String query = "SELECT * FROM picture WHERE uploaded="+UPLOADED;
		return SQLCommands.queryModel(query,Model.PICTURE);
	}

	public static ArrayList<Model> getNotUploadedPictures() {
		String query = "SELECT * FROM picture WHERE uploaded="+NOT_UPLOADED;
		return SQLCommands.queryModel(query,Model.PICTURE);
	}

	public static ArrayList<Picture> getNeighbors(String fileName) {
		ArrayList<Picture> neighbors = new ArrayList<Picture>();
		ArrayList<Picture> uploaded = (ArrayList)getUploadedPictures();

		for(int i=0; i<uploaded.size() && neighbors.isEmpty(); i++) {
			if(uploaded.get(i).getName().equals(fileName)) {
				neighbors.addAll(Tools.getNeighbors(uploaded, i));
			}
		}

		return neighbors;
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
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
	 * @return the dslr_cellscope
	 */
	public int getDslr_cellscope() {
		return dslr_cellscope;
	}

	/**
	 * @param dslr_cellscope the dslr_cellscope to set
	 */
	public void setDslr_cellscope(int dslr_cellscope) {
		this.dslr_cellscope = dslr_cellscope;
	}

	/**
	 * @return the hdr
	 */
	public int getHdr() {
		return hdr;
	}

	/**
	 * @param hdr the hdr to set
	 */
	public void setHdr(int hdr) {
		this.hdr = hdr;
	}

	/**
	 * @return the plus_one_exposure
	 */
	public int getPlus_one_exposure() {
		return plus_one_exposure;
	}

	/**
	 * @param plus_one_exposure the plus_one_exposure to set
	 */
	public void setPlus_one_exposure(int plus_one_exposure) {
		this.plus_one_exposure = plus_one_exposure;
	}

	/**
	 * @return the right_left
	 */
	public int getRight_left() {
		return right_left;
	}

	/**
	 * @param right_left the right_left to set
	 */
	public void setRight_left(int right_left) {
		this.right_left = right_left;
	}

	/**
	 * @return the uploaded
	 */
	public int getUploaded() {
		return uploaded;
	}

	/**
	 * @param uploaded the uploaded to set
	 */
	public void setUploaded(int uploaded) {
		this.uploaded = uploaded;
	}
	
}
