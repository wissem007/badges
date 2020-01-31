package tn.com.smartsoft.framework.presentation.context;

import java.util.Iterator;
import java.util.Map;

import tn.com.smartsoft.commons.web.multipart.MultipartFile;

public interface RequestParameters {
	
	public abstract boolean containsParameter(String name);
	
	public abstract MultipartFile getFile(String name);
	
	public abstract Map<String, MultipartFile> getFileMap();
	
	public abstract Iterator<String> getFileNames();
	
	public abstract String getParameter(String name);
	
	public abstract String getParameter(String name, String defaultValue);
	
	public abstract boolean getParameterAsBoolean(String name);
	
	public abstract boolean getParameterAsBoolean(String name, boolean defaultValue);
	
	public abstract boolean getParameterAsBoolean(String name, String trueValue, boolean defaultValue);
	
	public abstract int getParameterAsInteger(String name);
	
	public abstract int getParameterAsInteger(String name, int defaultValue);
	
	public abstract String[] getParameterAsStrings(String name);
	
	public abstract String[] getParameterNames();
	
	public abstract boolean isMultipart();
	
}