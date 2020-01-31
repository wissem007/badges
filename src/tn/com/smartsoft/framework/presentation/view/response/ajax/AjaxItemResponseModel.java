package tn.com.smartsoft.framework.presentation.view.response.ajax;

import java.io.Serializable;

public class AjaxItemResponseModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public final static String ELEMENT = new String("element");
	public final static String OBJECT = new String("object");
	private String id;
	private String xmlContent;
	private String userScript;
	private String type;
	public static final String AJAX_RESPONSE_NAME = AjaxResponseModel.AJAX_RESPONSE_NAME;

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

	public void setXmlContent(String xmlContent) {
		this.xmlContent = xmlContent;
	}

	public String getXmlContent() {
		return xmlContent;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
