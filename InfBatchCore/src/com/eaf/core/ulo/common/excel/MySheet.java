package com.eaf.core.ulo.common.excel;

import java.math.BigDecimal;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

/**
 * Make sheet operation easier
 * 
 * @author Norrapat Nimmanee
 * @version initial
 */
public class MySheet {
	protected transient Logger logger = Logger.getLogger(this.getClass());

	private Sheet sheet;
	private int curRow = 0;
	private int curCol = 0;

	public static enum Direction {
		UP,
		DOWN,
		LEFT,
		RIGHT
	}

	public MySheet(Sheet sh) {
		sheet = sh;
	}

	public Sheet getSheet() {
		return sheet;
	}

	public void setSheet(Sheet sheet) {
		this.sheet = sheet;
	}

	public MySheet setCursor(int row, String column) {
		return setCursor(row, getCell(column.toUpperCase()));
	}

	public MySheet setCursor(int row, int column) {
		setCursorRow(row);
		setCursorColumn(column);
		return this;
	}

	public String getValue() {
		return getCellValue(getCursorRow(), getCursorColumn());
	}

	public MySheet setCursorRow(int row) {
		curRow = row;
		if (curRow < 1) {
			curRow = 1;
		}
		return this;
	}

	public int getCursorRow() {
		return curRow;
	}

	public MySheet setCursorColumn(int column) {
		curCol = column;
		if (curCol < 1) {
			curCol = 1;
		}
		return this;
	}

	public int getCursorColumn() {
		return curCol;
	}

	public MySheet up() {
		return up(1);
	}

	public MySheet up(int count) {
		return setCursorRow(getCursorRow() - count);
	}

	public MySheet down() {
		curRow++;
		return this;
	}

	public MySheet down(int count) {
		return setCursorRow(getCursorRow() + count);
	}

	public MySheet left() {
		return left(1);
	}

	public MySheet left(int count) {
		return setCursorColumn(getCursorColumn() - count);
	}

	public MySheet right() {
		return right(1);
	}

	public MySheet right(int count) {
		return setCursorColumn(getCursorColumn() + count);
	}

	public MySheet setCellValue(String value) {
		setCellValue(curRow, curCol, value);
		return this;
	}

	/**
	 * Easier to set cell value <br/>
	 * <b>ROW is start at 1</b>
	 * 
	 * @param column
	 *            Column is to Upper case automatically.
	 * @param row
	 *            Row start at 1 !
	 * @param value
	 *            String value to set on that cell
	 * @return Class to making chain call
	 */

	public void setCellValue(MySheet sheet, int row, String column, String value){
		try{
			sheet.setCellValue(row, column, new BigDecimal(value));
		}catch(Exception e){
			sheet.setCellValue(row, column, value);
		}
	}
	public MySheet setCellValue(int row, String column, String value) {
		setCellValue(row, getCell(column.toUpperCase()), value);
		logger.debug("Placing \"" + value + "\" to Cell [" + column + row + "]");
		return this;
	}
	
	public MySheet setCellValue(int row, int column, String value) {
		Row xRow = sheet.getRow(row - 1);
		Cell xcell = xRow.getCell(column - 1, Row.CREATE_NULL_AS_BLANK);
		xcell.setCellValue(value);
		logger.debug("Placing \"" + value + "\" to Cell [" + column + row + "]");
		return this;
	}
	
	public MySheet setCellValue(int row, String column, BigDecimal value) {
		setCellValue(row, getCell(column.toUpperCase()), value);
		logger.debug("Placing \"" + value + "\" to Cell [" + column + row + "]");
		return this;
	}
	
	public MySheet setCellValue(int row, int column, BigDecimal value) {
		Row xRow = sheet.getRow(row - 1);
		Cell xcell = xRow.getCell(column - 1, Row.CREATE_NULL_AS_BLANK);
		xcell.setCellValue(value.doubleValue());
		logger.debug("Placing \"" + value + "\" to Cell [" + column + row + "]");
		return this;
	}
	
	/**
	 * Set Cells value by Pattern automatically. And except Cell that contain
	 * DASH sign ( - )<br/>
	 * <b>Ex.</b><br/>
	 * You need to fill Data "1234" to Cell <b>|_|_|_|-|_|</b><br/>
	 * Numbers will place on blank Cells (NOT DASH CELL)<br/>
	 * Pattern for these Cell must set to "_,_,_,-,_"
	 * 
	 * @param String
	 *            or Text
	 * @param Pattern
	 * @return This class that you can do Chain calling
	 */
	public MySheet setCellsByPattern(String value, String pattern) {
		logger.debug("Beginning .. Set cell by pattern.");
		logger.debug("  Cursor at Row=" + getCursorRow() + ", Column=" + getCursorColumn());
		if (null == value || null == pattern) {
			logger.error("   ! Stopped. Some parameters is Null !");
			return this;
		}

		logger.debug("  Str=" + value);
		logger.debug("  Pattern=" + pattern);
		String[] sPattern = pattern.split(",+");
		char[] charValue = value.toCharArray();

		logger.debug("  Count " + sPattern.length + " blocks.");

		for (int i = 0, j = 0, count = charValue.length; j < count; i++) {
			if (sPattern[i].equals("_")) {
				setCellValue("" + charValue[j++]);
			}
			right();
		}

		logger.debug("Ended Set cell by pattern.");
		return this;
	}

	/**
	 * 
	 * @param values
	 * @param direction
	 * @return
	 */
	public MySheet setCellsValues(String[] values, Direction direction) {
		if (null == values || null == direction) {
			logger.debug("Can't set Cells values !");
			return this;
		}

		for (String value : values) {
			setCellValue(value);
			switch (direction) {
				case UP:
					up();
					break;
				case DOWN:
					down();
					break;
				case LEFT:
					left();
					break;
				case RIGHT:
					right();
					break;
				default:
			}
		}
		return this;
	}

	/**
	 * 
	 * @param column
	 * @param row
	 * @return
	 */
	public String getCellValue(int row, String column) {
		return getCellValue(row, getCell(column));
	}

	public String getCellValue(int row, int column) {
		Row xRow = sheet.getRow(row - 1);
		Cell xcell = xRow.getCell(column - 1, Row.CREATE_NULL_AS_BLANK);
		return xcell.getStringCellValue();
	}

	private int getCell(String column) {
		int result = 0;
		for (int i = 0; i < column.length(); i++) {
			result *= 26;
			result += column.charAt(i) - 'A' + 1;
		}
		return result;
	}
	public void setCellValue(MySheet sheet, int row, String column, String value,int cell,CellStyle cellStyle){
	 	Row r = sheet.getSheet().getRow(row);
	 	if (r == null) {
	 	   r = sheet.getSheet().createRow(row);
	 	}
	    Cell c = r.getCell(cell);
	    if (c == null) {
	        c = r.createCell(cell);
	    }
	    c.setCellType(Cell.CELL_TYPE_STRING);
	    c.setCellValue(value);
	    c.setCellStyle(cellStyle);
	    logger.debug("Placing \"" + value + "\" to Cell [" + column + row + "]");
	}
}