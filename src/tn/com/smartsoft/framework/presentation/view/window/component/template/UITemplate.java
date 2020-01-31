package tn.com.smartsoft.framework.presentation.view.window.component.template;

import java.util.HashMap;
import java.util.Map;

import tn.com.smartsoft.framework.presentation.view.window.UIComponent;
import tn.com.smartsoft.framework.presentation.view.window.UIRender;
import tn.com.smartsoft.framework.presentation.view.window.UIRendrableComponent;
import tn.com.smartsoft.framework.presentation.view.window.component.template.render.UITemplateRender;

public class UITemplate extends UIComponent implements UIRendrableComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String location;
	private Map<String, UIModelTemplate> models = new HashMap<String, UIModelTemplate>();
	private boolean rendred = true;

	public boolean isRendred() {
		return rendred;
	}

	public void setRendred(boolean rendred) {
		this.rendred = rendred;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void addModel(UIModelTemplate modelTemplate) {
		models.put(modelTemplate.getName(), modelTemplate);
	}

	public Map<String, UIModelTemplate> getModels() {
		return models;
	}

	public UIRender getRender() {
		return new UITemplateRender(this);
	}

}
