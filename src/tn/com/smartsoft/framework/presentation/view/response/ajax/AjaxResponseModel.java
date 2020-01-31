package tn.com.smartsoft.framework.presentation.view.response.ajax;

import java.io.Serializable;
import java.util.Hashtable;

public class AjaxResponseModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Hashtable<String, AjaxItemResponseModel> handlerList;
	private String userScript;
	public static final String AJAX_RESPONSE_NAME = "ajax";

	public AjaxResponseModel() {
		handlerList = null;
	}

	public AjaxResponseModel(AjaxItemResponseModel ajaxResponse) {
		this();
		setAjaxResponse(ajaxResponse);
	}

	public AjaxItemResponseModel[] getAjaxElementResponse() {
		if (handlerList == null) {
			return new AjaxItemResponseModel[0];
		} else {
			AjaxItemResponseModel ajaxIdentifyModel[] = new AjaxItemResponseModel[handlerList.size()];
			return (AjaxItemResponseModel[]) handlerList.values().toArray(ajaxIdentifyModel);
		}
	}

	public void setAjaxResponse(AjaxItemResponseModel ajaxResponse) {
		if (handlerList == null)
			handlerList = new Hashtable<String, AjaxItemResponseModel>();
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

}
