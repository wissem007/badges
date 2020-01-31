package tn.com.smartsoft.commons.web.multipart;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;

public class DefaultMultipartHttpServletRequest extends AbstractMultipartHttpServletRequest{
	
	private final Map<String, String[]>	multipartParameters;
	
	public DefaultMultipartHttpServletRequest(HttpServletRequest request, Map<String, MultipartFile> multipartFiles, Map<String, String[]> multipartParameters) {
		super(request);
		setMultipartFiles(multipartFiles);
		this.multipartParameters = multipartParameters;
	}
	@SuppressWarnings("unchecked")
	public Enumeration<String> getParameterNames() {
		Set<String> paramNames = new HashSet<String>();
		Enumeration<String> paramEnum = super.getParameterNames();
		while (paramEnum.hasMoreElements()) {
			paramNames.add(paramEnum.nextElement());
		}
		paramNames.addAll(this.multipartParameters.keySet());
		return Collections.enumeration(paramNames);
	}
	public String getParameter(String name) {
		String[] values = (String[]) this.multipartParameters.get(name);
		if (values != null) { return (values.length > 0 ? values[0] : null); }
		return super.getParameter(name);
	}
	public String[] getParameterValues(String name) {
		String[] values = (String[]) this.multipartParameters.get(name);
		if (values != null) { return values; }
		return super.getParameterValues(name);
	}
	public Map<String, Object> getParameterMap() {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.putAll(super.getParameterMap());
		paramMap.putAll(this.multipartParameters);
		return paramMap;
	}
}
