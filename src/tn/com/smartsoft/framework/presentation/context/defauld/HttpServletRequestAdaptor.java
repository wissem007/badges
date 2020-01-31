package tn.com.smartsoft.framework.presentation.context.defauld;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import tn.com.smartsoft.commons.exceptions.TechnicalException;
import tn.com.smartsoft.framework.configuration.ApplicationConfiguration;
import tn.com.smartsoft.framework.presentation.context.Request;
import tn.com.smartsoft.framework.presentation.context.RequestEventInfo;
import tn.com.smartsoft.framework.presentation.context.RequestInfo;
import tn.com.smartsoft.framework.presentation.context.RequestParameters;

public class HttpServletRequestAdaptor implements Request, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected RequestInfo requestInfo;
	protected RequestEventInfo requestSourceEventInfo;
	protected RequestParameters requestSubmitedParameter;
	protected HttpWebContext context;

	public HttpServletRequestAdaptor(HttpServletRequest request, boolean isPost, HttpWebContext context) {
		super();
		this.context = context;
		try {
			if (ApplicationConfiguration.isStartUp())
				request.setCharacterEncoding(ApplicationConfiguration.applicationManager().applicationDefinition().getWebDefinition().getEncoding());
		} catch (Exception e) {
			throw new TechnicalException(e);
		}
		this.requestSubmitedParameter = new HttpRequestParameters(request);
		this.requestSourceEventInfo = new HttpRequestEventInfo(request);
		this.requestInfo = new HttpRequestInfo(request, isPost);

	}

	public RequestInfo requestInfo() {
		return requestInfo;
	}

	public RequestEventInfo requestEventInfo() {
		return requestSourceEventInfo;
	}

	public RequestParameters requestParameter() {
		return requestSubmitedParameter;
	}

}
