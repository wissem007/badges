package tn.com.smartsoft.framework.presentation.view.response.xl;

import java.util.HashMap;

import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.write.DateFormat;
import jxl.write.NumberFormat;
import jxl.write.NumberFormats;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WriteException;

public class XlCellFormatCollection {
	public HashMap<Integer, WritableCellFormat> formatMap;

	public static int FORMAT_TITRE = 0;

	public static int FORMAT_TITRE_TABLE = 1;

	public static int FORMAT_TITRE_REBRUQUE = 2;

	public static int FORMAT_TITRE_TOTAL = 3;

	public static int FORMAT_TITRE_TOTAL_BAG_GRAY = 4;

	public static int FORMAT_DOUBLE_BLUE = 5;

	public static int FORMAT_DOUBLE_RED = 6;

	public static int FORMAT_DOUBLE_BLUE_GR = 7;

	public static int FORMAT_DOUBLE_BLUE_GR_BAG_GRAY = 8;

	public static int FORMAT_DOUBLE_RED_GR = 9;

	public static int FORMAT_DOUBLE_RED_GR_BAG_GRAY = 10;

	public static int FORMAT_DOUBLE_GREEN_GR = 11;

	public static int FORMAT_DOUBLE_GREEN_GR_BAG_GRAY = 12;

	public static int FORMAT_DOUBLE_GREEN = 13;

	public static int FORMAT_INTGER_BLUE = 14;

	public static int FORMAT_INTGER_RED = 15;

	public static int FORMAT_INTGER_BLUE_GR = 17;

	public static int FORMAT_INTGER_RED_GR = 18;

	public static int FORMAT_INTGER_GREEN = 19;

	public static int FORMAT_INTGER_GREEN_GR = 20;

	public static int FORMAT_PERCENTAGE_BLUE_GR = 21;

	public static int FORMAT_PERCENTAGE_BLUE = 22;

	public static int FORMAT_STRING = 23;
	
	public static int FORMAT_DATE = 24;
	public XlCellFormatCollection() {
		formatMap = new HashMap<Integer, WritableCellFormat>();
		registerLabelFormat(FORMAT_TITRE, Alignment.CENTRE, 14, true, Colour.BLUE);
		registerLabelFormat(FORMAT_TITRE_TABLE, Alignment.CENTRE, 12, true, Colour.BLUE);
		registerLabelFormat(FORMAT_TITRE_REBRUQUE, Alignment.RIGHT, 12, false, Colour.BLACK);
		registerLabelFormat(FORMAT_TITRE_TOTAL, Alignment.LEFT, 12, true, Colour.BLACK);
		registerLabelFormat(FORMAT_STRING, Alignment.LEFT, 12, false, Colour.BLACK);
		registerDateFormat(FORMAT_DATE, Alignment.LEFT, 12, false, Colour.BLACK);

		registerDoubleFormat(FORMAT_DOUBLE_BLUE, 12, false, Colour.BLUE);
		registerDoubleFormat(FORMAT_DOUBLE_RED, 12, false, Colour.RED);
		registerDoubleFormat(FORMAT_DOUBLE_GREEN, 12, false, Colour.BLACK);
		registerDoubleFormat(FORMAT_DOUBLE_BLUE_GR, 12, true, Colour.BLUE);
		registerDoubleFormat(FORMAT_DOUBLE_RED_GR, 12, true, Colour.RED);
		registerDoubleFormat(FORMAT_DOUBLE_GREEN_GR, 12, true, Colour.GREEN);
		registerDoubleFormatBagGray(FORMAT_DOUBLE_BLUE_GR_BAG_GRAY, 12, true, Colour.BLUE);
		registerDoubleFormatBagGray(FORMAT_DOUBLE_RED_GR_BAG_GRAY, 12, true, Colour.RED);
		registerDoubleFormatBagGray(FORMAT_DOUBLE_GREEN_GR_BAG_GRAY, 12, true, Colour.GREEN);

		registerIntgerFormat(FORMAT_INTGER_BLUE, 12, false, Colour.BLUE);
		registerIntgerFormat(FORMAT_INTGER_RED, 12, false, Colour.RED);
		registerIntgerFormat(FORMAT_INTGER_GREEN, 12, false, Colour.GREEN);
		registerIntgerFormat(FORMAT_INTGER_BLUE_GR, 12, true, Colour.BLUE);
		registerIntgerFormat(FORMAT_INTGER_RED_GR, 12, true, Colour.RED);
		registerIntgerFormat(FORMAT_INTGER_GREEN_GR, 12, true, Colour.GREEN);

		registerPercentageFormat(FORMAT_PERCENTAGE_BLUE_GR, 12, true, Colour.BLUE);
		registerPercentageFormat(FORMAT_PERCENTAGE_BLUE, 12, false, Colour.BLUE);

		registerLabelFormatBagGray(FORMAT_TITRE_TOTAL_BAG_GRAY, Alignment.CENTRE, 12, true, Colour.BLACK);

	}

	private void registerLabelFormat(int cellFormatKey, Alignment alignment, int ps, boolean blod, Colour color) {
		try {
			WritableFont font = getFont(false, ps, false, blod, color);
			WritableCellFormat cellformat = new WritableCellFormat(font);
			cellformat.setAlignment(alignment);
			cellformat.setWrap(false);
			formatMap.put(cellFormatKey, cellformat);
		} catch (WriteException e) {
			e.printStackTrace();
		}
	}

	private void registerDoubleFormatBagGray(int cellFormatKey, int ps, boolean blod, Colour color) {
		try {
			WritableFont font = getFont(false, ps, false, blod, color);

			NumberFormat numberFormat = new NumberFormat("#,###.000");
			WritableCellFormat cellformat = new WritableCellFormat(font, numberFormat);
			cellformat.setAlignment(Alignment.RIGHT);
			cellformat.setWrap(false);
			cellformat.setBackground(Colour.GRAY_25);
			formatMap.put(cellFormatKey, cellformat);
		} catch (WriteException e) {
			e.printStackTrace();
		}
	}
	private void registerDateFormat(int cellFormatKey, Alignment alignment,int ps, boolean blod, Colour color) {
		try {
			WritableFont font = getFont(false, ps, false, blod, color);
     		DateFormat numberFormat = new DateFormat("dd/MM/yyyy");
			WritableCellFormat cellformat = new WritableCellFormat(font, numberFormat);
			cellformat.setAlignment(alignment);
			cellformat.setWrap(false);
		 	formatMap.put(cellFormatKey, cellformat);
		} catch (WriteException e) {
			e.printStackTrace();
		}
	}

	private void registerPercentageFormat(int cellFormatKey, int ps, boolean blod, Colour color) {
		try {
			WritableFont font = getFont(false, ps, false, blod, color);
			WritableCellFormat cellformat = new WritableCellFormat(font, NumberFormats.PERCENT_FLOAT);
			cellformat.setAlignment(Alignment.RIGHT);
			cellformat.setWrap(false);
			formatMap.put(cellFormatKey, cellformat);
		} catch (WriteException e) {
			e.printStackTrace();
		}
	}

	private void registerDoubleFormat(int cellFormatKey, int ps, boolean blod, Colour color) {
		try {
			WritableFont font = getFont(false, ps, false, blod, color);
			NumberFormat numberFormat = new NumberFormat("#,###.000");
			WritableCellFormat cellformat = new WritableCellFormat(font, numberFormat);
			cellformat.setAlignment(Alignment.RIGHT);
			cellformat.setWrap(false);
			formatMap.put(cellFormatKey, cellformat);
		} catch (WriteException e) {
			e.printStackTrace();
		}
	}

	private void registerLabelFormatBagGray(int cellFormatKey, Alignment alignment, int ps, boolean blod, Colour color) {
		try {
			WritableFont font = getFont(false, ps, false, blod, color);
			WritableCellFormat cellformat = new WritableCellFormat(font);
			cellformat.setAlignment(alignment);
			cellformat.setWrap(false);
			cellformat.setBackground(Colour.GRAY_25);
			formatMap.put(cellFormatKey, cellformat);
		} catch (WriteException e) {
			e.printStackTrace();
		}
	}

	private void registerIntgerFormat(int cellFormatKey, int ps, boolean blod, Colour color) {
		try {
			WritableFont font = getFont(false, ps, false, blod, color);
			WritableCellFormat cellformat = new WritableCellFormat(font, NumberFormats.THOUSANDS_INTEGER);
			cellformat.setAlignment(Alignment.RIGHT);
			cellformat.setWrap(false);
			formatMap.put(cellFormatKey, cellformat);
		} catch (WriteException e) {
			e.printStackTrace();
		}
	}

	public WritableCellFormat getCellFormat(int key) {
		return (WritableCellFormat) formatMap.get(key);
	}

	private WritableFont getFont(boolean arial, int ps, boolean italic, boolean blod, Colour color) throws WriteException {
		WritableFont font = null;
		if (arial)
			font = new WritableFont(WritableFont.ARIAL, ps);
		else
			font = new WritableFont(WritableFont.TIMES, ps);
		font.setColour(color);
		font.setItalic(italic);
		if (blod)
			font.setBoldStyle(WritableFont.BOLD);
		else
			font.setBoldStyle(WritableFont.NO_BOLD);
		return font;
	}
}
