package tn.com.smartsoft.framework.presentation.view.response.xl;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jxl.Workbook;
import jxl.write.WritableWorkbook;
import tn.com.smartsoft.commons.exceptions.TechnicalException;
import tn.com.smartsoft.framework.presentation.context.WebContext;
import tn.com.smartsoft.framework.presentation.view.action.UserAction;

public class XlModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private WritableWorkbook xlWorkbook;
	private XlCellFormatCollection xlCelleFormatCollection;
	private List<XlSheetContent> xlContents = new ArrayList<XlSheetContent>();
	protected UserAction userAction;
	public static final String RESPONSE_NAME = "jxl";
	public String title;

	public XlModel(String title, UserAction userAction) {
		super();
		this.userAction = userAction;
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public XlModel() throws IOException {
		super();
	}

	public void addSheet(XlSheetContent sheet) {
		xlContents.add(sheet);
	}

	public WritableWorkbook getXlWorkbook() {
		return xlWorkbook;
	}

	public XlCellFormatCollection getXlCelleFormatCollection() {
		return xlCelleFormatCollection;
	}

	public UserAction userAction() {
		return userAction;
	}

	public void setUserAction(UserAction userAction) {
		this.userAction = userAction;
	}

	public void render(WebContext context) {
		try {
			this.xlWorkbook = Workbook.createWorkbook(context.response().getOutputStream());
			this.xlCelleFormatCollection = new XlCellFormatCollection();
			for (int i = 0; i < xlContents.size(); i++) {
				xlContents.get(i).process(context, this);
			}
			xlWorkbook.write();
			xlWorkbook.close();
		} catch (Exception e) {
			throw new TechnicalException(e);
		}
	}
}
