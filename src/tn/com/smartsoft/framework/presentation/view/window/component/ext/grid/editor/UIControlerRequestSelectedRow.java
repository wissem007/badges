package tn.com.smartsoft.framework.presentation.view.window.component.ext.grid.editor;

import org.apache.commons.lang.StringUtils;

import tn.com.smartsoft.framework.presentation.view.action.request.ControlerRequestField;
import tn.com.smartsoft.framework.presentation.view.action.request.data.DataRequestField;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.grid.UIExtGrid;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.store.utils.StoreUtils;

public class UIControlerRequestSelectedRow implements ControlerRequestField {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UIExtGrid extGrid;

	public UIControlerRequestSelectedRow(UIExtGrid extGrid) {
		super();
		this.extGrid = extGrid;
	}

	public void validateDataType(DataRequestField dataRequestField) {
		String requestValue = dataRequestField.getRequestValue() == null ? "" : dataRequestField.getRequestValue().toString();
		if (!extGrid.isTree() || extGrid.getTypeTree() == 2) {
			if (StringUtils.isNotBlank(requestValue) && StringUtils.isNumeric(requestValue)) {
				dataRequestField.setValue(new Integer(Integer.parseInt(requestValue)));
			}
		} else {
			int[] treeIndex = StoreUtils.getTreeIndex(requestValue);
			dataRequestField.setValue(treeIndex);
		}
	}

	public void validate(DataRequestField dataRequestField) {
	}

	public void commit(DataRequestField dataRequestField) {
		if (dataRequestField.getValue() == null)
			return;
		if (dataRequestField.getValue() instanceof Integer) {
			extGrid.setSelectedRow(((Integer) dataRequestField.getValue()).intValue());
		} else if (dataRequestField.getValue() instanceof int[]) {
			extGrid.setSelectedTreeRow((int[]) dataRequestField.getValue());
		}
	}

	public String getLibelle() {
		return "selectedRow";
	}

	public String getId() {
		return "selectedRow";
	}

}
