package tn.com.smartsoft.framework.presentation.view.response.xl;

import org.apache.commons.lang.StringUtils;

import tn.com.smartsoft.framework.presentation.view.window.component.ext.grid.UIExtGridColumn;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.store.UIExtFieldStore;

public class XlDefaultColumnRender implements XlColumnRender {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	 
	public XlCelluleValue render(XlTableContent xlTable, String columnId, int row) {
		UIExtGridColumn column = xlTable.getGrid().getColumn(columnId);
		UIExtFieldStore fieldStore = column.getFiledStore();
		String userType = fieldStore.model().getUserType();
		Object value = fieldStore.getValue(row);
		String customValue = fieldStore.getCustomValue(row);
		XlCelluleValue xlCelluleValue = XlDefaultColumnRender.createXlCelluleValue(userType, value);
		if (xlCelluleValue.isCustomerValue())
			xlCelluleValue.setValue(customValue);
		return xlCelluleValue;
	}

	public static XlCelluleValue createXlCelluleValue(String userType, Object value) {
		if (StringUtils.indexOfAny(userType, new String[] { "date" }) > -1) {
			return new XlCelluleValue(value, XlCellFormatCollection.FORMAT_DATE, XlColumnRender.TYPE_DATE);
		} else if (StringUtils.indexOfAny(userType, new String[] { "datetime" }) > -1) {
			return new XlCelluleValue(value, XlCellFormatCollection.FORMAT_DATE, XlColumnRender.TYPE_DATE);
		} else if (StringUtils.indexOfAny(userType, new String[] { "time" }) > -1) {
			return new XlCelluleValue(value, XlCellFormatCollection.FORMAT_DATE, XlColumnRender.TYPE_DATE);
		} else if (StringUtils.indexOfAny(userType, new String[] { "montant" }) > -1) {
			return new XlCelluleValue(value, XlCellFormatCollection.FORMAT_DOUBLE_GREEN, XlColumnRender.TYPE_NUMBER);
		} else if (StringUtils.indexOfAny(userType, new String[] { "numeric", "numerickey" }) > -1) {
			return new XlCelluleValue(value, XlCellFormatCollection.FORMAT_DOUBLE_GREEN, XlColumnRender.TYPE_NUMBER);
		} else if (StringUtils.indexOfAny(userType, new String[] { "boolean" }) > -1) {
			return new XlCelluleValue(value, XlCellFormatCollection.FORMAT_DOUBLE_GREEN, XlColumnRender.TYPE_NUMBER);
		} else {
			return new XlCelluleValue(value, XlCellFormatCollection.FORMAT_STRING, XlColumnRender.TYPE_LABEL, true);
		}
	}
 
}
