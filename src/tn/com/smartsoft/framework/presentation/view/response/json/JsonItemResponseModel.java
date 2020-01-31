package tn.com.smartsoft.framework.presentation.view.response.json;

import java.io.Serializable;
import java.util.Hashtable;

import org.apache.commons.lang.StringUtils;

import tn.com.smartsoft.framework.presentation.view.listener.ListenerContext;

public class JsonItemResponseModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private Hashtable<String, DataJsonItemResponseModel> datas = new Hashtable<String, DataJsonItemResponseModel>();
	public static final String JSON_RESPONSE_NAME = JsonResponseModel.JSON_RESPONSE_NAME;
	private boolean isSuccess = true;
	private String userScript;

	public void addData(DataJsonItemResponseModel data) {
		if (StringUtils.isBlank(data.getProperty()))
			return;
		if (data.getValue() == null) {
			datas.remove(data.getProperty());
		} else {
			datas.put(data.getProperty(), data);
		}
	}

	public static void reponseEmpty(ListenerContext context) {
		JsonItemResponseModel jsonItemResponseModel = new JsonItemResponseModel();
		context.webContext().response(JsonItemResponseModel.JSON_RESPONSE_NAME, jsonItemResponseModel);
	}

	public void addData(String property, Object value, String[] include, String[] exclude) {
		addData(new DataJsonItemResponseModel(property, value, exclude, include));
	}

	public void addData(String property, Object value, String[] include) {
		addData(property, value, include, new String[] { "*" });
	}

	public void addData(String property, Object value) {
		addData(property, value, new String[] { "*" }, null);
	}

	public Hashtable<String, DataJsonItemResponseModel> getDatas() {
		return datas;
	}

	public void setDatas(Hashtable<String, DataJsonItemResponseModel> datas) {
		this.datas = datas;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getUserScript() {
		return userScript;
	}

	public void setUserScript(String userScript) {
		this.userScript = userScript;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
