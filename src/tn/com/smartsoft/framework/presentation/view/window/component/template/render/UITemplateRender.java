package tn.com.smartsoft.framework.presentation.view.window.component.template.render;

import java.util.Collection;
import java.util.Iterator;

import tn.com.smartsoft.commons.utils.velocity.VelocityTransformer;
import tn.com.smartsoft.framework.presentation.view.window.UIRender;
import tn.com.smartsoft.framework.presentation.view.window.UIRenderContext;
import tn.com.smartsoft.framework.presentation.view.window.component.template.UIModelTemplate;
import tn.com.smartsoft.framework.presentation.view.window.component.template.UITemplate;

public class UITemplateRender extends UIRender {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UITemplateRender(UITemplate renderComponent) {
		super(renderComponent);
	}

	public void render(UIRenderContext context) {
		UITemplate template = (UITemplate) renderComponent;
		VelocityTransformer velocityTransformer = VelocityTransformer.newInstance();
		Collection<UIModelTemplate> values = template.getModels().values();
		for (Iterator<UIModelTemplate> iterator = values.iterator(); iterator.hasNext();) {
			UIModelTemplate modelTemplate = (UIModelTemplate) iterator.next();
			velocityTransformer.setAttribute(modelTemplate.getName(), modelTemplate.getValue());
		}
		context.renderln(velocityTransformer.transform(template.getLocation()));
	}

}
