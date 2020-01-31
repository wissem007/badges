package tn.com.smartsoft.framework.presentation.view.window.component.ext.store;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import tn.com.smartsoft.commons.exceptions.FunctionalException;
import tn.com.smartsoft.commons.utils.BeanObjectUtils;
import tn.com.smartsoft.commons.utils.SorterType;
import tn.com.smartsoft.commons.web.js.JsMap;
import tn.com.smartsoft.commons.web.js.JsTable;
import tn.com.smartsoft.framework.presentation.view.comman.ClientEvent;
import tn.com.smartsoft.framework.presentation.view.listener.ListenerContext;
import tn.com.smartsoft.framework.presentation.view.response.json.JsonItemResponseModel;
import tn.com.smartsoft.framework.presentation.view.window.UIRender;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.event.UIAjaxEvent;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.store.render.UIExtStoreRender;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.store.utils.StoreUtils;

public class UIExtJsonStore extends UIExtStore {
	private static final String START_REQUEST_NAME = "start";
	private static final String LIMIT_REQUEST_NAME = "limit";
	private static final String DIR_REQUEST_NAME = "dir";
	private static final String SORT_REQUEST_NAME = "sort";

	private static final String TREE_NODE_REQUEST_NAME = "anode";
	public static final String TREE_LEVEL_NAME = "level";
	public static final String TREE_PARENT_NAME = "parent";
	public static final String TREE_ISLEAF_NAME = "isLeaf";
	public static final String TREE_RGT_NAME = "rgt";
	public static final String TREE_LFT_NAME = "lft";

	private static final long serialVersionUID = 1L;

	private String root = "data";
	private String successProperty = "success";
	private String totalProperty = "totalCount";
	private String messageProperty = "message";
	private JsMap reloadRequestParams = new JsMap();
	private JsTable reloadRequestFieldsParams = new JsTable();
	private String reloadHandlerRequestParams;
	private Boolean reloadAllRequestField = Boolean.FALSE;
	private String reloadMethod;
	private Boolean isExpandAll = Boolean.FALSE;

	public UIExtJsonStore() {
		super();
		addEvent(new UIAjaxEvent(ClientEvent.ON_INIT, "onLoadListener", this));
	}

	public void onLoadListener(ListenerContext context) throws FunctionalException {
		if (StringUtils.isNotBlank(this.getReloadMethod()) && context.webContext().request().requestParameter().getParameterAsBoolean("reload", false))
			context.userAction().getControleur().runAction(this.getReloadMethod(), context);
		if (StringUtils.isNotBlank(this.getQueryMethod())) {
			context.userAction().getControleur().runAction(this.getQueryMethod(), context);
		}
		String sortField = context.webContext().request().requestParameter().getParameter(SORT_REQUEST_NAME);
		String dirSort = context.webContext().request().requestParameter().getParameter(DIR_REQUEST_NAME);
		int limitRow = context.webContext().request().requestParameter().getParameterAsInteger(LIMIT_REQUEST_NAME, -1);
		int startRow = context.webContext().request().requestParameter().getParameterAsInteger(START_REQUEST_NAME, 0);
		if (StringUtils.isNotBlank(dirSort))
			this.sortByPropertyIndex(sortField, SorterType.parse(dirSort));
		List<?> listValue = getValue();
		List<Map<String, Object>> values;
		if (isTree()) {
			String parentTree = context.webContext().request().requestParameter().getParameter(TREE_NODE_REQUEST_NAME);
			values = new ArrayList<Map<String, Object>>();
			if (getTypeTree() != 2)
				createTreeJsonData(parentTree, values, 0);
			else
				createTreeJsonDatas(values);

		} else
			values = createJsonData(limitRow, startRow);
		JsonItemResponseModel jsonItemResponseModel = new JsonItemResponseModel();
		jsonItemResponseModel.addData(this.getRoot(), values);
		jsonItemResponseModel.addData("version", 1);
		if (!isTree())
			jsonItemResponseModel.addData(this.getTotalProperty(), listValue.size());
		else
			jsonItemResponseModel.addData(this.getTotalProperty(), values.size());
		context.webContext().response(JsonItemResponseModel.JSON_RESPONSE_NAME, jsonItemResponseModel);
	}

	private List<Map<String, Object>> createJsonData(int limitRow, int startRow) {
		List<?> listValue = getValue();
		int[] borneIndex = StoreUtils.getBorneIndex(limitRow, startRow, listValue);
		startRow = borneIndex[0];
		limitRow = borneIndex[1];
		List<Map<String, Object>> values = new ArrayList<Map<String, Object>>();
		for (int i = startRow; i < limitRow; i++) {
			values.add(StoreUtils.getJsonMap(String.valueOf(i), this, getModelName(), listValue.get(i)));
		}
		return values;
	}

	private int createTreeJsonData(String anodeTree, List<Map<String, Object>> values, int start) {
		int[] treeIndex = StoreUtils.getTreeIndex(anodeTree);
		List<?> listValueIntValue = getValue();
		Integer level = treeIndex.length + 1;
		anodeTree = StringUtils.isNotBlank(anodeTree) ? anodeTree : null;
		List<?> listValue = StoreUtils.findChildrenNode(treeIndex, listValueIntValue, getChildrenProperty());
		for (int i = 0; i < listValue.size(); i++) {
			start++;
			String treeDisplayIndex = StoreUtils.getTreeDisplayIndex(anodeTree, i);
			Map<String, Object> row = StoreUtils.getJsonMap(treeDisplayIndex, this, getModelName(), listValue.get(i));
			row.put(TREE_LEVEL_NAME, level);
			row.put(TREE_PARENT_NAME, anodeTree);
			List<?> childeren = (List<?>) BeanObjectUtils.getPropertyValue(listValue.get(i), getChildrenProperty());
			boolean isLeaf = childeren == null || childeren.size() == 0;
			row.put(TREE_ISLEAF_NAME, isLeaf);
			values.add(row);
			if (getTypeTree() != 0) {
				row.put(TREE_LFT_NAME, start);
				if (!isLeaf)
					start = createTreeJsonData(treeDisplayIndex, values, start);
				start++;
				row.put(TREE_RGT_NAME, start);
			}
		}
		return start;
	}

	private void createTreeJsonDatas(List<Map<String, Object>> values) {
		int start = 1;
		List<?> listValue = getValue();
		for (int i = 0; i < listValue.size(); i++) {
			Object parentValue = BeanObjectUtils.getPropertyValue(listValue.get(i), getParentProperty());
			if (parentValue == null)
				start = createTreeJsonDatas(listValue, -1, i, 1, values, start);
		}
	}

	private int createTreeJsonDatas(List<?> listValue, int parentIndex, int index, int level, List<Map<String, Object>> values, int start) {
		Object bean = listValue.get(index);
		Map<String, Object> row = StoreUtils.getJsonMap(String.valueOf(index), this, getModelName(), bean);
		row.put(TREE_LEVEL_NAME, level);
		if (parentIndex != -1)
			row.put(TREE_PARENT_NAME, String.valueOf(parentIndex));
		row.put(TREE_LFT_NAME, start);
		values.add(row);
		int sstart = start;
		Object idPropertyValue = BeanObjectUtils.getPropertyValue(bean, getIdProperty());
		for (int i = 0; i < listValue.size(); i++) {
			Object child = listValue.get(i);
			Object parentValue = BeanObjectUtils.getPropertyValue(child, getParentProperty());
			if (parentValue != null && idPropertyValue.equals(parentValue)) {
				start++;
				start = createTreeJsonDatas(listValue, index, i, level + 1, values, start);
			}
		}
		row.put(TREE_ISLEAF_NAME, sstart == start);
		start++;
		row.put(TREE_RGT_NAME, start);
		return start;
	}

	public Boolean getExpandAll() {
		return isExpandAll;
	}

	public void setExpandAll(Boolean isExpandAll) {
		this.isExpandAll = isExpandAll;
	}

	public String getRoot() {
		return this.root;
	}

	public void setRoot(String root) {
		this.root = root;
	}

	public String getReloadMethod() {
		return reloadMethod;
	}

	public void setReloadMethod(String reloadMethod) {
		this.reloadMethod = reloadMethod;
	}

	public String getSuccessProperty() {
		return this.successProperty;
	}

	public String getReloadHandlerRequestParams() {
		return reloadHandlerRequestParams;
	}

	public void setReloadHandlerRequestParams(String reloadHandlerRequestParams) {
		this.reloadHandlerRequestParams = reloadHandlerRequestParams;
	}

	public void addReloadRequestParam(String name, String value, Boolean expected) {
		this.reloadRequestParams.addValue(name, value, expected.booleanValue());
	}

	public void addReloadRequestFieldParam(String field) {
		this.reloadRequestFieldsParams.addStringOption(field);
	}

	public Boolean getReloadAllRequestField() {
		return reloadAllRequestField;
	}

	public JsMap getReloadRequestParams() {
		return reloadRequestParams;
	}

	public JsTable getReloadRequestFieldsParams() {
		return reloadRequestFieldsParams;
	}

	public void setReloadAllRequestField(Boolean reloadAllRequestField) {
		this.reloadAllRequestField = reloadAllRequestField;
	}

	public void setSuccessProperty(String successProperty) {
		this.successProperty = successProperty;
	}

	public String getTotalProperty() {
		return this.totalProperty;
	}

	public void setTotalProperty(String totalProperty) {
		this.totalProperty = totalProperty;
	}

	public String getMessageProperty() {
		return messageProperty;
	}

	public void setMessageProperty(String messageProperty) {
		this.messageProperty = messageProperty;
	}

	public UIRender getRender() {
		return new UIExtStoreRender(this);
	}
}