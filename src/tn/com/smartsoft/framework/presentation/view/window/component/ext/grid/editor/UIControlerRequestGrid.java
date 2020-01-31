package tn.com.smartsoft.framework.presentation.view.window.component.ext.grid.editor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import tn.com.smartsoft.commons.log.Logger;
import tn.com.smartsoft.framework.presentation.view.action.model.CompositeModel;
import tn.com.smartsoft.framework.presentation.view.action.request.ControlerRequestField;
import tn.com.smartsoft.framework.presentation.view.action.request.data.DataControlerRequestField;
import tn.com.smartsoft.framework.presentation.view.action.request.data.DataRequestField;
import tn.com.smartsoft.framework.presentation.view.action.request.data.DefauldDataRequestField;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field.utlis.UIExtControlerRequestField;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.grid.UIExtGrid;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.grid.UIExtGridColumn;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.store.UIExtFieldStore;

public class UIControlerRequestGrid implements ControlerRequestField{
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	private UIExtGrid			extGrid;
	public static final String	MAPPED_DELIM		= "[";
	public static final String	MAPPED_DELIM2		= "]";
	
	public UIControlerRequestGrid(UIExtGrid extGrid) {
		super();
		this.extGrid = extGrid;
	}
	public void validateDataType(DataRequestField dataRequestField) {
		String requestValue = dataRequestField.getRequestValue() == null ? "" : dataRequestField.getRequestValue().toString();
		if (StringUtils.isBlank(requestValue)) { return; }
		UIDataRequestGrid dataRequestGrid = new UIDataRequestGrid();
		try {
			dataRequestGrid.deserialize(requestValue);
		} catch (Exception e) {
			dataRequestField.setValidRequestValue(false);
			dataRequestField.getMessagesHandler().addMessage("", extGrid.getTitle());
			return;
		}
		if (!dataRequestGrid.isEmptyValue()) {
			Iterator<String> keyValues = dataRequestGrid.keyValues();
			while (keyValues.hasNext()) {
				String keyValue = keyValues.next();
				Object value = dataRequestGrid.getValue(keyValue);
				ControlerRequestField controlerRequestField = getControlerRequestField(keyValue);
				if (controlerRequestField != null) {
					DefauldDataRequestField childRequestField = new DefauldDataRequestField(controlerRequestField, value, dataRequestField.getListenerContext());
					childRequestField.validateDataType();
					if (!childRequestField.isValidRequestValue()) {
						dataRequestField.setValidRequestValue(false);
					}
					dataRequestGrid.addDataRequestField(childRequestField);
				} else {
					Logger.getLogger(this).warn("invalid field in grid :" + extGrid.getId() + " and fieldId :" + keyValue);
				}
			}
		}
		dataRequestField.setValue(dataRequestGrid);
	}
	public void validate(DataRequestField dataRequestField) {
		UIDataRequestGrid dataRequestGrid = (UIDataRequestGrid) dataRequestField.getValue();
		Map<String, DataRequestField> dataInputFields = dataRequestGrid.getDataRequestFields();
		Iterator<String> it = dataInputFields.keySet().iterator();
		while (it.hasNext()) {
			DataRequestField childRequestField = dataRequestGrid.getDataRequestField(it.next());
			((DataControlerRequestField) childRequestField).validate();
			if (!childRequestField.isValidRequestValue()) {
				dataRequestField.setValidRequestValue(false);
			}
		}
	}
	public void commit(DataRequestField dataRequestField) {
		@SuppressWarnings("unchecked")
		List<Object> list = (List<Object>) extGrid.getStore().getValue();
		CompositeModel storeModel = (CompositeModel) extGrid.getStore().getUserAction().getModel().findFieldModel(extGrid.getStore().getModelName());
		UIDataRequestGrid dataRequestGrid = (UIDataRequestGrid) dataRequestField.getValue();
		Map<String, DataRequestField> dataInputFields = dataRequestGrid.getDataRequestFields();
		if (dataRequestGrid.getIndexed() != null) {
			HashMap<String, Integer> indexed = dataRequestGrid.getIndexed();
			Object[] listValue = new Object[indexed.size()];
			Iterator<String> itind = indexed.keySet().iterator();
			while (itind.hasNext()) {
				Integer iClient = Integer.parseInt(itind.next());
				Integer iServer = indexed.get(String.valueOf(iClient));
				if (iServer.intValue() < list.size()) {
					listValue[iClient] = list.get(iServer);
				} else {
					listValue[iClient] = storeModel.getBeanAccessorModel().newValue();
				}
			}
			List<Object> listN = Arrays.asList(listValue);
			list.clear();
			list.addAll(listN);
		}
		Iterator<String> it = dataInputFields.keySet().iterator();
		while (it.hasNext()) {
			DataRequestField childRequestField = dataRequestGrid.getDataRequestField(it.next());
			((DataControlerRequestField) childRequestField).commit();
		}
	}
	public String getLibelle() {
		return extGrid.getTitle();
	}
	public String getId() {
		return extGrid.getId();
	}
	public ControlerRequestField getControlerRequestField(String id) {
		int delimMap1 = StringUtils.indexOf(id, MAPPED_DELIM);
		int delimMap2 = StringUtils.indexOf(id, MAPPED_DELIM2);
		if (delimMap1 > 0 && delimMap2 > delimMap1 && delimMap2 == (id.length() - 1)) {
			String dataIndex = StringUtils.substring(id, 0, delimMap1);
			final String row = StringUtils.substring(id, delimMap1 + 1, delimMap2);
			if (!StringUtils.isNumeric(row)) return null;
			final UIExtGridColumn gridColumn = extGrid.getColumnByIndex(dataIndex);
			if (gridColumn == null || gridColumn.getEditor() == null) {
				if (!extGrid.getStore().hasField(dataIndex)) return null;
				final UIExtFieldStore fieldStore = extGrid.getStore().getField(dataIndex);
				return new ControlerRequestField(){
					
					private static final long	serialVersionUID	= 1L;
					
					public void validateDataType(DataRequestField dataRequestField) {}
					public void validate(DataRequestField dataRequestField) {}
					public void commit(DataRequestField dataRequestField) {
						fieldStore.setValue(Integer.parseInt(row), dataRequestField.getRequestValue());
					}
					public String getLibelle() {
						return fieldStore.model().getLibelle();
					}
					public String getId() {
						return fieldStore.getId();
					}
				};
			}
			UIExtControlerRequestField controlerRequestField = (UIExtControlerRequestField) gridColumn.getEditor().getControlerRequestField();
			controlerRequestField.setRow(Integer.parseInt(row));
			return controlerRequestField;
		}
		return null;
	}
}
