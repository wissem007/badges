package tn.com.smartsoft.framework.presentation.view.response.xl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableHyperlink;
import jxl.write.WritableSheet;
import jxl.write.WriteException;
import tn.com.smartsoft.framework.presentation.context.WebContext;

public class XlSheetContent implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<XlContent> xlContents = new ArrayList<XlContent>();
	public String title;
	private WritableSheet xlSheet;
	private int numeroClaseur = 0;
	private XlModel model;

	public XlSheetContent(String title, int numeroClaseur) {
		super();
		this.title = title;
		this.numeroClaseur = numeroClaseur;
	}

	public void process(WebContext context, XlModel documentReport) throws WriteException {
		model = documentReport;
		this.xlSheet = documentReport.getXlWorkbook().createSheet(title, numeroClaseur);
		XlContent lastContent = null;
		for (int i = 0; i < xlContents.size(); i++) {
			XlContent xlContent = xlContents.get(i);
			if (lastContent != null) {
			 xlContent.setStartRow(lastContent.getLastRow());
			}
			xlContent.process(context, this);
			lastContent=xlContent;
		}
	}

	public XlModel getModel() {
		return model;
	}

	public void setXlSheet(WritableSheet xlSheet) {
		this.xlSheet = xlSheet;
	}

	public void addContent(XlContent sheet) {
		xlContents.add(sheet);
	}

	public WritableSheet getXlSheet() {
		return xlSheet;
	}

	public void mergeCells(int colDebut, int colFin, int rowDebut, int rowFin) throws WriteException {
		xlSheet.mergeCells(colDebut, rowDebut, colFin, rowFin);
	}

	public void mergeVerticalCells(int col, int row, int nbRow) throws WriteException {
		mergeCells(col, row, col, row + nbRow);
	}

	public void mergeHorizontalCells(int col, int row, int nbCol) throws WriteException {
		mergeCells(col, row, col + nbCol, row);
	}

	public void addLabelCellule(int col, int row, String value, WritableCellFormat cellformat) throws WriteException {
		Label colCell = new Label(col, row, value, cellformat);
		xlSheet.addCell(colCell);
	}

	public void addLabelLinkCellule(int col, int row, String value, WritableCellFormat cellformat) throws WriteException {
		WritableHyperlink colCell = new WritableHyperlink(col, row, value, xlSheet, 2, 2);
		xlSheet.addHyperlink(colCell);
	}

	public void addNumberCellule(int col, int row, Number value, WritableCellFormat cellformat) throws WriteException {
		if (value == null || value.doubleValue() == 0)
			return;
		jxl.write.Number colCell = new jxl.write.Number(col, row, value != null ? value.doubleValue() : 0, cellformat);
		xlSheet.addCell(colCell);
	}

	public void addDateCellule(int col, int row, Date value, WritableCellFormat cellformat) throws WriteException {
		if (value == null)
			return;
		jxl.write.DateTime colCell = new jxl.write.DateTime(col, row, value, cellformat);
		xlSheet.addCell(colCell);
	}
}
