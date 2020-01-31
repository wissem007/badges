package tn.com.smartsoft.framework.presentation.view.response.xl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.write.WritableCellFormat;
import jxl.write.WriteException;
import tn.com.smartsoft.framework.presentation.context.WebContext;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.grid.UIExtGrid;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.grid.UIExtGridColumn;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.grid.UIExtGridHeaderColumn;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.grid.UIExtHeaderRowGrid;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.store.UIExtStore;

public class XlTableContent extends XlContent {

	private XlSheetContent xlSheet;
	private UIExtGrid grid;
	private int size = -1;
	private Map<String, XlColumnRender> columnRenders = new HashMap<String, XlColumnRender>();
	private XlColumnRender defaultColumnRender = new XlDefaultColumnRender();

	public XlTableContent(UIExtGrid grid, int startRow, int startCol) {
		super(startRow, startCol);
		this.grid = grid;
	}

	public XlTableContent(UIExtGrid grid, int startRow) {
		this(grid, startRow, 0);
	}

	public XlTableContent(UIExtGrid grid, int startRow, XlColumnRender defaultColumnRender) {
		this(grid, startRow, 0);
		this.defaultColumnRender = defaultColumnRender;
	}

	public XlTableContent(UIExtGrid grid, XlColumnRender defaultColumnRender) {
		this(grid, 0, 0);
		this.defaultColumnRender = defaultColumnRender;
	}

	public void process(WebContext context, XlSheetContent sheetContent) throws WriteException {
		this.xlSheet = sheetContent;
		intFirstRow();
		processHeader();
		processDetail();
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int rowSize() {
		if (this.size == -1) {
			UIExtStore store = grid.getStore();
			this.size = store.size();
		}
		return this.size;
	}

	private void processDetail() throws WriteException {
		for (int i = 0; i < rowSize(); i++) {
			List<String> columnIds = grid.getColumnIds();
			int col = startCol;
			for (int j = 0; j < columnIds.size(); j++) {
				UIExtGridColumn column = grid.getColumn((String) columnIds.get(j));
				if (column.isPrint()) {
					XlColumnRender columnRender = getColumnRender(columnIds.get(j));
					XlCelluleValue celluleValue = columnRender.render(this, columnIds.get(j), i);
					WritableCellFormat cellFormat = this.xlSheet.getModel().getXlCelleFormatCollection().getCellFormat(celluleValue.getFormatType());
					int cellType = celluleValue.getCellType();
					if (cellType == XlColumnRender.TYPE_LABEL) {
						this.xlSheet.addLabelCellule(col, getLastRow(), (String) celluleValue.getValue(), cellFormat);
					} else if (cellType == XlColumnRender.TYPE_NUMBER) {
						this.xlSheet.addNumberCellule(col, getLastRow(), (Number) celluleValue.getValue(), cellFormat);
					}
					if (cellType == XlColumnRender.TYPE_DATE) {
						this.xlSheet.addDateCellule(col, getLastRow(), (Date) celluleValue.getValue(), cellFormat);
					}
					col++;
				}
			}
			incrumentRow();
		}
	}

	private void processHeader() throws WriteException {
		WritableCellFormat headFormat = this.xlSheet.getModel().getXlCelleFormatCollection().getCellFormat(XlCellFormatCollection.FORMAT_TITRE_TOTAL_BAG_GRAY);
		List<String> listHederId = grid.getHeaderRowIds();
		if (!listHederId.isEmpty()) {
			for (int i = 0; i < listHederId.size(); i++) {
				int lastCol = startCol;
				UIExtHeaderRowGrid headerRowGrid = grid.getHeaderRow(listHederId.get(i));
				List<String> listColHederId = headerRowGrid.getColumnIds();
				for (int j = 0; j < listColHederId.size(); j++) {
					int col = lastCol;
					UIExtGridHeaderColumn headerColumn = headerRowGrid.getColumn(listColHederId.get(j));
					if (headerColumn.isPrint()) {
						Object libelle = grid.getWindow().evalExpression(headerColumn.getHeader());
						libelle = libelle == null ? "" : libelle;
						lastCol = lastCol + headerColumn.getColspan() - 1;
						this.xlSheet.addLabelCellule(col, i, libelle.toString(), headFormat);
						this.xlSheet.mergeCells(col, lastCol, i, i);
						lastCol++;
					}
				}
				incrumentRow();
			}
		}
		List<String> columnIds = grid.getColumnIds();
		int col = startCol;
		for (int i = 0; i < columnIds.size(); i++) {
			UIExtGridColumn column = grid.getColumn((String) columnIds.get(i));
			if (column.isPrint()) {
				String libelle = column.getLibelle();
				this.xlSheet.addLabelCellule(col, getLastRow(), libelle, headFormat);
				xlSheet.getXlSheet().setColumnView(col, column.getXlWidth());
				col++;
			}
		}
		incrumentRow();
	}

	public XlColumnRender getColumnRender(String columnId) {
		if (columnRenders.containsKey(columnId))
			return columnRenders.get(columnId);
		return defaultColumnRender;
	}

	public void addColumnRender(String columnId, XlColumnRender xlColumnRender) {
		columnRenders.put(columnId, xlColumnRender);
	}

	public XlColumnRender getDefaultColumnRender() {
		return defaultColumnRender;
	}

	public void setDefaultColumnRender(XlColumnRender defaultColumnRender) {
		this.defaultColumnRender = defaultColumnRender;
	}

	public UIExtStore getStore() {
		return grid.getStore();
	}

	public UIExtGrid getGrid() {
		return grid;
	}

	public XlModel getXlModel() {
		return xlSheet.getModel();
	}

}