package tn.com.smartsoft.framework.presentation.view.script.processor;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import tn.com.smartsoft.commons.utils.FileLocator;
import tn.com.smartsoft.framework.presentation.definition.GroupScriptDefinition;
import tn.com.smartsoft.framework.presentation.view.script.minifier.IJsMinifier;
import tn.com.smartsoft.framework.presentation.view.script.minifier.js.YuiJsMinifier;

public class MinimizeJSProcessor implements ResourcesProcessor {

	private static MinimizeJSProcessor instance;
	private static IJsMinifier minifier = new YuiJsMinifier();
	private FileLocator fileLocator = new FileLocator();

	public static MinimizeJSProcessor getInstance() {
		if (instance == null)
			instance = new MinimizeJSProcessor();
		return instance;
	}

	private MinimizeJSProcessor() {
	}

	public void process(GroupScriptDefinition group, List<String> resourcesName, Writer out, String location) throws IOException {
		for (Iterator<Object> iterator = group.getSubgroups().iterator(); iterator.hasNext();) {
			GroupScriptDefinition subGroup = (GroupScriptDefinition) iterator.next();
			String subLocation = subGroup.getLocation() == null ? location : subGroup.getLocation();
			ResourcesProcessor subGroupProcessor = null;
			if (subGroup.getMinimize() == null)
				subGroupProcessor = this;
			else
				subGroupProcessor = subGroup.getJSProcessor();
			subGroupProcessor.process(subGroup, subGroup.getJsNames(), out, subLocation);
		}

		for (Iterator<String> it = resourcesName.iterator(); it.hasNext();) {
			String path = (String) it.next();
			URL url = null;
			if (location == null || "".equals(location)) {
				url = fileLocator.getConfURL(path);
			} else {
				url = fileLocator.getConfURL(location + path);
			}
			if (url == null)
				throw new IOException("The resources '" + path + "' could not be found neither in the webapp folder nor in a jar");
			InputStream in = url.openStream();

			try {
				minifier.minify(in, out);
			} catch (Exception e) {
				e.printStackTrace();
				throw new IOException("The JS minifier failed.");
			} finally {
				out.flush();
				in.close();
			}
			// YUICompressorAdaptor.compressJS(new InputStreamReader(in), out);
		}
	}

	public void setMinifier(IJsMinifier minifier) {
		MinimizeJSProcessor.minifier = minifier;
	}

}
