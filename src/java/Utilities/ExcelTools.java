/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Utilities;

import java.util.*;
import org.apache.poi.ss.usermodel.*; 

/**
 *
 * @author aryner
 */
public class ExcelTools {
	public static final int FILENAME_COL_INDEX = 0;
	public static final int DSLR_CELLSCOPE_COL_INDEX = 1;
	public static final int HDR_COL_INDEX = 2;
	public static final int PLUS_ONE_COL_INDEX = 3;

	private static final String FILENAME = "filename";
	private static final String DSLR_CELLSCOPE = "dslr";
	private static final String HDR = "hdr";
	private static final String PLUS_ONE = "exposure";

	public static int[] getColumnIndices(int colStart, int colEnd, Row row) {
		int [] indices = new int[4];
		Tools.setArray(indices, -1);

		for(int i=colStart; i<colEnd; i++) {
			Cell cell = row.getCell(i);
			if (cell == null || cell.getCellType() != Cell.CELL_TYPE_STRING) continue;
			String colName = cell.getRichStringCellValue().toString().toLowerCase();

			if(colName.contains(FILENAME)) indices[FILENAME_COL_INDEX] = i;
			else if(colName.contains(DSLR_CELLSCOPE)) indices[DSLR_CELLSCOPE_COL_INDEX] = i;
			else if(colName.contains(HDR)) indices[HDR_COL_INDEX] = i;
			else if(colName.contains(PLUS_ONE)) indices[PLUS_ONE_COL_INDEX] = i;
		}

		return indices;
	}

	public static ArrayList<String> readFile(int [] indices, Sheet sheet, int rowStart, int rowEnd) {
		ArrayList<String> errors = new ArrayList<String>();
		ArrayList<String> fileNames = new ArrayList<String>();
		ArrayList<Integer> dslr = new ArrayList<Integer>();
		ArrayList<Integer> hdr = new ArrayList<Integer>();
		ArrayList<Integer> exposure = new ArrayList<Integer>();

		for(int i=rowStart; i<rowEnd; i++) {
			Row row = sheet.getRow(i);

			Cell cell = row.getCell(indices[FILENAME_COL_INDEX]);
			extractString(errors, fileNames, cell);
			cell = row.getCell(indices[DSLR_CELLSCOPE_COL_INDEX]);
			extractInt(errors, dslr, cell);
			cell = row.getCell(indices[HDR_COL_INDEX]);
			extractInt(errors, hdr, cell);
			cell = row.getCell(indices[PLUS_ONE_COL_INDEX]);
			extractInt(errors, exposure, cell);
		}

		if(errors.size() > 0) return errors;

		DataBaseTools.insertAndUpdateRecords(fileNames, dslr, hdr, exposure);

		return errors;
	}

	private static void extractString(ArrayList<String> errors, ArrayList<String> texts, Cell cell) {
		if(cell == null) {
			errors.add(missingData(cell));
		} else if(cell.getCellType() != Cell.CELL_TYPE_STRING) {
			errors.add(wrongFormat(cell));
		} else {
			String text = cell.getRichStringCellValue().toString();
			if(text.isEmpty()) {
				errors.add(missingData(cell));
			}
			else {
				texts.add(text);
			}
		}
	}

	private static void extractInt(ArrayList<String> errors, ArrayList<Integer> ints, Cell cell) {
		if(cell == null) {
			errors.add(missingData(cell));
		} else if(cell.getCellType() != Cell.CELL_TYPE_NUMERIC) {
			errors.add(wrongFormat(cell));
		} else {
			ints.add((int)cell.getNumericCellValue());
		}
	}

	private static String missingData(Cell cell) {
		return "There is missing data in column "+cell.getColumnIndex()+", row "+cell.getRowIndex();
	}

	private static String wrongFormat(Cell cell) {
		return "The cell at column "+cell.getColumnIndex()+", row "+cell.getRowIndex()+" is not formated correctly";
	}
}
