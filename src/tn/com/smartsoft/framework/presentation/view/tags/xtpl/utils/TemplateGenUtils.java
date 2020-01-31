package tn.com.smartsoft.framework.presentation.view.tags.xtpl.utils;

import tn.com.smartsoft.commons.utils.velocity.VelocityTransformer;

public class TemplateGenUtils {
	public static StringBuffer generate(String path, String[] names, Object[] values) {
		StringBuffer sbs = new StringBuffer();
		VelocityTransformer velocityTransformer = VelocityTransformer.newInstance();
		for (int i = 0; i < values.length; i++) {
			velocityTransformer.setAttribute(names[i], values[i]);
		}
		velocityTransformer.setAttribute("xxx", "$");
		velocityTransformer.setAttribute("yyy", "#");
		sbs.append(velocityTransformer.transform(path));
		return sbs;
	}

}
