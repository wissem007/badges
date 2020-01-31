package tn.com.smartsoft.framework.presentation.definition;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import tn.com.smartsoft.commons.utils.FileLocator;
import tn.com.smartsoft.commons.utils.ResourcesBuffer;
import tn.com.smartsoft.framework.configuration.definition.IDefinition;
import tn.com.smartsoft.framework.presentation.view.script.processor.MergeProcessor;
import tn.com.smartsoft.framework.presentation.view.script.processor.MinimizeJSProcessor;
import tn.com.smartsoft.framework.presentation.view.script.processor.ResourcesProcessor;

public class GroupScriptDefinition implements IDefinition {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String location;
	private List<String> jsNames = new ArrayList<String>();
	private List<String> cssNames = new ArrayList<String>();
	private List<Object> subgroups = new ArrayList<Object>();

	private Boolean minimize;
	private Boolean minimizeCss;
	private boolean retention;
	private WebDefinition webDefinition;

	private ResourcesBuffer minimizedCache = new ResourcesBuffer();
	private ResourcesBuffer cssBuffer = new ResourcesBuffer();
	private ResourcesBuffer jsBuffer = new ResourcesBuffer();
	private ResourcesProcessor jsProcessor;
	private ResourcesProcessor cssProcessor;
	private long lastJsLoadTime = 0;
	private long lastCssLoadTime = 0;
	private List<String> deepJsNames;
	private List<String> deepCssNames;
	private FileLocator fileLocator = new FileLocator();

	public List<String> getJsNames() {
		return jsNames;
	}

	public void setWebDefinition(WebDefinition webDefinition) {
		this.webDefinition = webDefinition;
	}

	public void addJsName(String location) {
		this.jsNames.add(location);
	}

	public void addSubgroup(String name) {
		this.subgroups.add(name);
	}

	public void addCssName(String location) {
		this.cssNames.add(location);
	}

	public Boolean getMinimize() {
		return minimize;
	}

	public void setMinimize(Boolean minimize) {
		if (Boolean.FALSE.equals(minimize))
			jsProcessor = MergeProcessor.getInstance();
		else
			jsProcessor = MinimizeJSProcessor.getInstance();
		this.minimize = minimize;
	}

	public Boolean getMinimizeCss() {
		return minimizeCss;
	}

	public void setMinimizeCss(Boolean minimizeCss) {
		if (Boolean.FALSE.equals(minimizeCss)) {
			cssProcessor = MergeProcessor.getInstance();
		} else
			cssProcessor = MergeProcessor.getInstance();// TODO
		this.minimizeCss = minimizeCss;
	}

	public boolean isRetention() {
		return retention;
	}

	public void setRetention(boolean retention) {
		this.retention = retention;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Object> getSubgroups() {
		return subgroups;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public List<String> getCssNames() {
		return cssNames;
	}

	public ResourcesProcessor getJSProcessor() {
		return jsProcessor;
	}

	public ResourcesProcessor getCSSProcessor() {
		return cssProcessor;
	}

	public ResourcesBuffer getJsBuffer() {
		return jsBuffer;
	}

	public ResourcesBuffer getCssBuffer() {
		return cssBuffer;
	}

	public ResourcesBuffer getMinimizedCache() {
		return minimizedCache;
	}

	public long getLastJsLoadTime() {
		return lastJsLoadTime;
	}

	public void setLastJsLoadTime(long lastJsLoadTime) {
		this.lastJsLoadTime = lastJsLoadTime;
	}

	public long getLastCssLoadTime() {
		return lastCssLoadTime;
	}

	public void setLastCssLoadTime(long lastCssLoadTime) {
		this.lastCssLoadTime = lastCssLoadTime;
	}

	public long computeMaxCSSTimestamp() {
		long maxCSSTimeStamp = cssBuffer.getTimestamp();
		for (int i = 0; i < subgroups.size(); i++) {
			GroupScriptDefinition subGroup = (GroupScriptDefinition) subgroups.get(i);
			long mx = subGroup.computeMaxCSSTimestamp();
			if (mx > maxCSSTimeStamp)
				maxCSSTimeStamp = mx;
		}

		List<String> files = getCssNames();
		for (int i = 0; i < files.size(); i++) {
			String fileName = (String) files.get(i);
			long mx = getFileTimeStamp(fileName);
			if (mx > maxCSSTimeStamp)
				maxCSSTimeStamp = mx;
		}

		return maxCSSTimeStamp;
	}

	public long getFileTimeStamp(String fileName) {
		if (fileName != null && fileName.length() > 0) {
			long lastModif = fileLocator.getConfFile(fileName).lastModified();
			return lastModif;
		}
		return 0;
	}

	public long computeMaxJSTimestamp() {
		long maxJSTimeStamp = jsBuffer.getTimestamp();
		for (int i = 0; i < subgroups.size(); i++) {
			GroupScriptDefinition subGroup = (GroupScriptDefinition) subgroups.get(i);
			long mx = subGroup.computeMaxJSTimestamp();
			if (mx > maxJSTimeStamp)
				maxJSTimeStamp = mx;
		}

		List<String> files = getJsNames();
		for (int i = 0; i < files.size(); i++) {
			String fileName = (String) files.get(i);
			long mx = getFileTimeStamp(fileName);
			if (mx > maxJSTimeStamp)
				maxJSTimeStamp = mx;
		}

		return maxJSTimeStamp;
	}

	public List<String> getDeepCssNames() {
		if (deepCssNames == null) {
			List<String> res = new LinkedList<String>();
			for (Iterator<Object> iterator = getSubgroups().iterator(); iterator.hasNext();) {
				GroupScriptDefinition subGroup = webDefinition.getGroupScriptDefinition((String) iterator.next());
				if (subGroup != null)
					res.addAll(subGroup.getCssNames());
			}
			res.addAll(getCssNames());
			deepCssNames = res;
		}
		return deepCssNames;
	}

	public List<String> getDeepJsNames() {
		if (deepJsNames == null) {
			List<String> res = new LinkedList<String>();
			for (Iterator<?> iterator = getSubgroups().iterator(); iterator.hasNext();) {
				GroupScriptDefinition subGroup = webDefinition.getGroupScriptDefinition((String) iterator.next());
				if (subGroup != null)
					res.addAll(subGroup.getDeepJsNames());
			}
			res.addAll(getJsNames());

			deepJsNames = res;
		}
		return deepJsNames;
	}
}
