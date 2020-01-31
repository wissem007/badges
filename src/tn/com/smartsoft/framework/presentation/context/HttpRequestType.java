package tn.com.smartsoft.framework.presentation.context;

public enum HttpRequestType {
	AJAX("AJAX"), STANDAR("STANDAR"), RESOURCE("RESOURCE");
	private String type;

	private HttpRequestType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
}
