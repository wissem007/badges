package tn.com.smartsoft.framework.presentation.view.response.json;

import java.io.Serializable;
import java.util.Hashtable;

public class JsonResponseModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String JSON_RESPONSE_NAME = "json";
	private Hashtable<String, JsonItemResponseModel> handlerList;
	private String userScript;
	private boolean isSuccess = true;

	public JsonResponseModel() {
		handlerList = null;
	}

	public JsonResponseModel(JsonItemResponseModel ajaxResponse) {
		this();
		setJsonResponse(ajaxResponse);
	}

	public JsonItemResponseModel[] getJsonItemResponse() {
		if (handlerList == null) {
			return new JsonItemResponseModel[0];
		} else {
			JsonItemResponseModel jsonIdentifyModel[] = new JsonItemResponseModel[handlerList.size()];
			return (JsonItemResponseModel[]) handlerList.values().toArray(jsonIdentifyModel);
		}
	}

	public void setJsonResponse(JsonItemResponseModel ajaxResponse) {
		if (handlerList == null)
			handlerList = new Hashtable<String, JsonItemResponseModel>();
		if (ajaxResponse == null)
			return;
		handlerList.remove(ajaxResponse.getId());
		handlerList.put(ajaxResponse.getId(), ajaxResponse);
	}

	public String getUserScript() {
		return userScript;
	}

	public void setUserScript(String userScript) {
		this.userScript = userScript;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

}
