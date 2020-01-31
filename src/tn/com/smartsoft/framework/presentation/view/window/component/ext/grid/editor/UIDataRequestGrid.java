package tn.com.smartsoft.framework.presentation.view.window.component.ext.grid.editor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import tn.com.smartsoft.framework.presentation.view.action.request.data.DataRequestField;
import tn.com.smartsoft.framework.presentation.view.action.request.data.DefauldDataRequestField;
import flexjson.JSONDeserializer;

public class UIDataRequestGrid implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HashMap<String, Object> values;
	private ArrayList<Integer> deleted;
	private HashMap<String, Integer> indexed;
	private Map<String, DataRequestField> dataRequestFields = new HashMap<String, DataRequestField>();
	private boolean isValidRequest = true;
	
	public UIDataRequestGrid() {
		super();
	}
	
	@SuppressWarnings("unchecked")
	public UIDataRequestGrid(Map<String, Object> requestValues) {
		super();
		if (requestValues != null) {
			this.values = (HashMap<String, Object>) requestValues.get("values");
			this.deleted = (ArrayList<Integer>) requestValues.get("deleted");
			this.indexed = (HashMap<String, Integer>) requestValues.get("indexed");
		}
	}
	
	@SuppressWarnings("unchecked")
	public void deserialize(String requestValuesString) throws Exception {
		Map<String, Object> requestValues = new JSONDeserializer<HashMap<String, Object>>().deserialize((String) requestValuesString, HashMap.class);
		if (requestValues != null) {
			this.values = (HashMap<String, Object>) requestValues.get("values");
			this.deleted = (ArrayList<Integer>) requestValues.get("deleted");
			this.indexed = (HashMap<String, Integer>) requestValues.get("indexed");
		}
	}
	
	public Object getValue(String key) {
		return values.get(key);
	}
	
	public Iterator<String> keyValues() {
		return values.keySet().iterator();
	}
	
	public boolean isEmptyValue() {
		if (values != null)
			return values.isEmpty();
		return true;
	}
	
	public ArrayList<Integer> getDeleted() {
		return deleted;
	}
	
	public boolean isEmptyDeleted() {
		if (deleted != null)
			return deleted.isEmpty();
		return true;
	}
	
	public HashMap<String, Integer> getIndexed() {
		return indexed;
	}
	
	public Integer getIndexed(String indexClient) {
		return indexed.get(indexClient);
	}
	
	public Iterator<String> keyIndexed() {
		return indexed.keySet().iterator();
	}
	
	public boolean isEmptyIndexed() {
		if (indexed != null)
			return indexed.isEmpty();
		return true;
	}
	
	public void setValidRequest(boolean isValidRequest) {
		this.isValidRequest = isValidRequest;
	}
	
	public boolean isValidRequest() {
		return isValidRequest;
	}
	
	public Map<String, DataRequestField> getDataRequestFields() {
		return dataRequestFields;
	}
	
	public DataRequestField getDataRequestField(String id) {
		return dataRequestFields.get(id);
	}
	
	public void addDataRequestField(DefauldDataRequestField dataRequestField) {
		if (!dataRequestField.isValidRequestValue())
			isValidRequest = false;
		dataRequestFields.put(dataRequestField.getId(), dataRequestField);
	}
	
	public boolean isDataInputFields() {
		return dataRequestFields.isEmpty();
	}
	
	public String toString() {
		return "UIGridFormModel [values=" + values + ", deleted=" + deleted + ", indexed=" + indexed + "]";
	}
}
