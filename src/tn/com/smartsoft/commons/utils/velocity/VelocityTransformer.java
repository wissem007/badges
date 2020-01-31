/** ------------------------------------ SweetDEV RIA library Copyright [2006] [Ideo Technologies] ------------------------------------ Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License. You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in
 * writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License. For more information, please contact us at: Ideo Technologies S.A 124 rue de Verdun 92800 Puteaux - France France & Europe Phone :
 * +33 1.46.25.09.60 USA & Canada Phone : (201) 984-7514 web : http://www.ideotechnologies.com email : Sweetdev_ria_sales@ideotechnologies.com
 * @version 2.2-SNAPSHOT
 * @author Ideo Technologies */
package tn.com.smartsoft.commons.utils.velocity;

import java.io.StringWriter;
import java.io.Writer;
import java.util.Iterator;
import java.util.Map;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.ResourceNotFoundException;
import tn.com.smartsoft.commons.log.Logger;
import tn.com.smartsoft.commons.utils.ClassUtilities;

public class VelocityTransformer{

	private static final Logger	LOG					= Logger.getLogger(VelocityTransformer.class);
	private VelocityContext		context				= null;
	public static String		DEFAULT_ENCONDING	= "ISO-8859-1";

	public VelocityTransformer() {
		context = new VelocityContext();
	}
	public void transform(Writer out, String templatePath) {
		VelocityEngine velocityEngine = null;
		try {
			velocityEngine = VelocityInstanceFactory.getVelocityEngine();
			Template template = velocityEngine.getTemplate(templatePath, "UTF8");
			template.merge(context, out);
		} catch (ResourceNotFoundException e) {
			LOG.error("Error on getting template", e);
		} catch (Exception e) {
			LOG.error("Error while merging template", e);
		}
	}
	public void transform(Writer out, Class className, String templateName) {
		transform(out, ClassUtilities.getRessourcePath(className, templateName));
	}
	public void evaluate(Writer out, String instream) {
		VelocityEngine velocityEngine = null;
		try {
			velocityEngine = VelocityInstanceFactory.getVelocityEngine();
			velocityEngine.evaluate(context, out, "templateName" + System.currentTimeMillis(), instream);
		} catch (ResourceNotFoundException e) {
			LOG.error("Error on getting template", e);
		} catch (Exception e) {
			LOG.error("Error while merging template", e);
		}
	}
	public String evaluate(String instream) {
		StringWriter writer = new StringWriter();
		evaluate(writer, instream);
		return writer.toString();
	}
	public String transform(Class className, String templateName) {
		return transform(ClassUtilities.getRessourcePath(className, templateName));
	}
	public String transform(String templatePath) {
		StringWriter writer = new StringWriter();
		transform(writer, templatePath);
		return writer.toString();
	}
	public Object getContext() {
		return context;
	}
	public void setAttribute(String name, Object value) {
		context.put(name, value);
	}
	public Object getAttribute(String name) {
		return context.get(name);
	}
	public void setAttributes(Map map) {
		Iterator keys = map.keySet().iterator();
		String key = null;
		while (keys.hasNext()) {
			key = (String) keys.next();
			setAttribute(key, map.get(key));
		}
	}
	public static VelocityTransformer newInstance() {
		return new VelocityTransformer();
	}
}
