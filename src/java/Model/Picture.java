/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;


/**
 *
 * @author aryner
 */
public class Picture extends Model{
	private int id;
	private String name;
	private int patient_number;
	private int dslr_cellscope;
	private int hdr;
	private int plus_one_exposure;
	private int right_left;

	public static final int DSLR = 0;
	public static final int CELLSCOPE = 1;
	public static final int HDR = 1;
	public static final int PLUS_ONE_EXPOSURE = 1;

	public Picture( int id, String name, int patient_number, int dslr_cellscope,
			int hdr, int plus_one_exposure, int right_left) {
		this.id = id;
		this.name = name;
		this.patient_number = patient_number;
		this.dslr_cellscope = dslr_cellscope;
		this.hdr = hdr;
		this.plus_one_exposure = plus_one_exposure;
		this.right_left = right_left;
	}

	public static Picture getModel(java.sql.ResultSet resultSet) 
		throws java.sql.SQLException, javax.naming.NamingException {
		return new Picture(
				resultSet.getInt("id"),resultSet.getString("name"),
				resultSet.getInt("patient_number"),resultSet.getInt("DSLR_cellscope"),
				resultSet.getInt("HDR"),resultSet.getInt("plus_one_exposure"),
				resultSet.getInt("right_left")
		);
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
	
}
