package tn.com.smartsoft.framework.presentation.view.script.processor;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.IOUtils;

import tn.com.smartsoft.commons.log.Logger;
import tn.com.smartsoft.commons.utils.FileLocator;
import tn.com.smartsoft.framework.presentation.definition.GroupScriptDefinition;

public class MergeProcessor implements ResourcesProcessor {
	private final static Logger log = Logger.getLogger(MergeProcessor.class);
	private static MergeProcessor instance;
	private FileLocator fileLocator = new FileLocator();

	public static MergeProcessor getInstance() {
		if (instance == null)
			instance = new MergeProcessor();
		return instance;
	}

	private MergeProcessor() {
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
			URL url = null;
			String path = (String) it.next();
			if (location == null || "".equals(location)) {
				url = fileLocator.getConfURL(path);
			} else {
				url = fileLocator.getConfURL(location + path);
			}
			if (url == null)
				throw new IOException("The resources '" + path + "' could not be found neither in the webapp folder nor in a jar");
			log.debug("Merging content of group : " + group.getName());
			InputStream in = url.openStream();
			IOUtils.copy(in, out, DEFAULT_ENCODING);
			out.write("\n\n");
			out.flush();
		}
	}
}
