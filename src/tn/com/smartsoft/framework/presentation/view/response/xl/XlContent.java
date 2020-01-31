package tn.com.smartsoft.framework.presentation.view.response.xl;

import jxl.write.WriteException;
import tn.com.smartsoft.framework.presentation.context.WebContext;

public abstract class XlContent {
	protected int startRow = 0;
	private int lastRow = 0;
	protected int startCol = 0;

	public XlContent(int startRow, int startCol) {
		super();
		this.startRow = startRow;
		this.startCol = startCol;
	}

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public void incrumentRow() {
		lastRow++;
	}

	public void intFirstRow() {
		this.lastRow = startCol;
	}

	public int getLastRow() {
		return lastRow;
	}

	public abstract void process(WebContext context, XlSheetContent documentReport) throws WriteException;
}
