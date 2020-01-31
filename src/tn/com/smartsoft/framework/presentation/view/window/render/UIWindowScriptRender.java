package tn.com.smartsoft.framework.presentation.view.window.render;

import tn.com.smartsoft.commons.web.markup.html.Head;
import tn.com.smartsoft.commons.web.markup.html.Title;
import tn.com.smartsoft.framework.presentation.view.script.UIScript;
import tn.com.smartsoft.framework.presentation.view.window.UIRender;
import tn.com.smartsoft.framework.presentation.view.window.UIRenderContext;
import tn.com.smartsoft.framework.presentation.view.window.UIWindowScript;

public class UIWindowScriptRender extends UIRender {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public UIWindowScriptRender(UIWindowScript renderComponent) {
		super(renderComponent);
	}
	
	public void render(UIRenderContext context) {
		UIWindowScript windowScript = (UIWindowScript) renderComponent;
		context.renderln(1, new Head().createStartTag());
		context.renderln(2, "<link rel=\"shortcut icon\" href=\"favicon.gif\" /> ");
		context.render(getTabRender());
		context.render(1, new Title().createStartTag());
		if (context.webContext().userDesktop().curentUserModule() != null) {
			context.render(0, context.webContext().userDesktop().curentUserModule().getLibelle());
			
		} else
			context.render(0, windowScript.getWindow().getId());
		context.renderln(1, new Title().createEndTag());
		for (int i = 0; i < windowScript.getScripts().size(); i++) {
			UIScript script = (UIScript) windowScript.getScripts().get(i);
			script.getRender().render(context);
		}
		context.renderln(1, new Head().createEndTag());
	}
	
}
