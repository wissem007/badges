package tn.com.smartsoft.framework.presentation.view.response.xl;

import java.util.Date;

import jxl.write.WritableCellFormat;
import jxl.write.WriteException;
import tn.com.smartsoft.framework.presentation.context.WebContext;

public class XlCellContent extends XlContent {
	private Object value;
	private String userType;
	private WritableCellFormat format;
	private boolean isIncrumentRow;

	public XlCellContent(Object value, String userType, WritableCellFormat format, int startRow, int startCol, boolean isIncrumentRow) {
		super(startRow, startCol);
		this.value = value;
		this.userType = userType;
		this.format = format;
	}

	public XlCellContent(Object value, String userType, int startRow, int startCol, boolean isIncrumentRow) {
		this(value, userType, null, startRow, startCol, isIncrumentRow);
	}

	public XlCellContent(Object value, String userType, int startCol, boolean isIncrumentRow) {
		this(value, userType, null, 0, startCol, isIncrumentRow);
	}

	public void process(WebContext context, XlSheetContent xlSheet) throws WriteException {
		XlCelluleValue celluleValue = XlDefaultColumnRender.createXlCelluleValue(userType, value);
		WritableCellFormat cellFormat = format;
		if (format == null)
			cellFormat = xlSheet.getModel().getXlCelleFormatCollection().getCellFormat(celluleValue.getFormatType());
		int cellType = celluleValue.getCellType();
		if (cellType == XlColumnRender.TYPE_LABEL) {
			xlSheet.addLabelCellule(startCol, getLastRow(), (String) celluleValue.getValue(), cellFormat);
		} else if (cellType == XlColumnRender.TYPE_NUMBER) {
			xlSheet.addNumberCellule(startCol, getLastRow(), (Number) celluleValue.getValue(), cellFormat);
		}
		if (cellType == XlColumnRender.TYPE_DATE) {
			xlSheet.addDateCellule(startCol, getLastRow(), (Date) celluleValue.getValue(), cellFormat);
		}
		if (isIncrumentRow)
			incrumentRow();
	}
}
