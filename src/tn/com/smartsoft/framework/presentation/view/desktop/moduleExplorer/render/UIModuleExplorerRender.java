package tn.com.smartsoft.framework.presentation.view.desktop.moduleExplorer.render;

import java.util.ArrayList;
import java.util.List;

import tn.com.smartsoft.commons.utils.BeanComparator;
import tn.com.smartsoft.commons.utils.SorterType;
import tn.com.smartsoft.commons.web.js.JsMap;
import tn.com.smartsoft.commons.web.utils.HtmlEncodeUtil;
import tn.com.smartsoft.framework.presentation.view.comman.ClientEvent;
import tn.com.smartsoft.framework.presentation.view.desktop.moduleExplorer.UIModuleExplorer;
import tn.com.smartsoft.framework.presentation.view.desktop.moduleExplorer.UIUserModule;
import tn.com.smartsoft.framework.presentation.view.window.UIRender;
import tn.com.smartsoft.framework.presentation.view.window.UIRenderContext;
import tn.com.smartsoft.framework.presentation.view.window.comman.UIRenderUtils;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.event.UIEventListener;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.event.UILinkedEvent;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.render.UIEventRenderUtils;

public class UIModuleExplorerRender extends UIRender {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UIModuleExplorerRender(UIModuleExplorer component) {
		super(component);
	}

	public void render(UIRenderContext context) {
		UIModuleExplorer objectExplorer = (UIModuleExplorer) renderComponent;
		if (!objectExplorer.isDisplayed())
			return;
		BeanComparator beanComparator = new BeanComparator("rang", SorterType.ASC, false);
		List<UIUserModule> list = new ArrayList<UIUserModule>(objectExplorer.getUserModules().values());
		beanComparator.sort(list);
		for (int i = 0; i < list.size(); i++) {
			UIUserModule userModule = (UIUserModule) list.get(i);
			renderJsModule(context, userModule);
		}
	}

	public void renderJsModule(UIRenderContext context, UIUserModule userModule) {
		UIModuleExplorer objectExplorer = (UIModuleExplorer) renderComponent;
		String libelle = HtmlEncodeUtil.encodeHtml(userModule.getLibelle());
		JsMap module = new JsMap();
		module.addStringValue("text", libelle);
		module.addStringValue("icon", UIRenderUtils.getImagePath(userModule.getIconUrl()).getPath());
		UILinkedEvent linkedEvent = new UILinkedEvent(ClientEvent.ON_CLICK, (UIEventListener) null);
		linkedEvent.setComponentId(userModule.getId());
		linkedEvent.setActionPattern(objectExplorer.desktopPartType().toString());
		UIEventRenderUtils.renderHrefEventToHandelConfig(context, this, linkedEvent, module);
		context.addModule(module);
	}
}
