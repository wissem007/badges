package tn.com.smartsoft.framework.presentation.view.window.component.ext;

import java.util.ArrayList;
import java.util.List;

import tn.com.smartsoft.framework.presentation.view.window.UIComponent;
import tn.com.smartsoft.framework.presentation.view.window.UIRender;
import tn.com.smartsoft.framework.presentation.view.window.UIRendrableComponent;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.render.UIExtTemplateRender;

public class UIExtTemplate extends UIComponent implements UIRendrableComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<UIExtTemplateArg> argumentds = new ArrayList<UIExtTemplateArg>();
	private boolean rendred = true;

	public boolean isRendred() {
		return rendred;
	}

	public void setRendred(boolean rendred) {
		this.rendred = rendred;
	}

	public UIRender getRender() {
		return new UIExtTemplateRender(this);
	}

	public void addArgument(UIExtTemplateArg templateArg) {
		templateArg.setParent(this);
		argumentds.add(templateArg);
	}

	public List<UIExtTemplateArg> getArgumentds() {
		return argumentds;
	}

}
