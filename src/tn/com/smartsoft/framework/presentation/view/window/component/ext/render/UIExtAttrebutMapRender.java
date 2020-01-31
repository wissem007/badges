package tn.com.smartsoft.framework.presentation.view.window.component.ext.render;

import org.apache.commons.lang.StringUtils;

import tn.com.smartsoft.commons.web.js.JSEncodeUtil;
import tn.com.smartsoft.framework.presentation.view.window.UIComponent;
import tn.com.smartsoft.framework.presentation.view.window.UIRender;
import tn.com.smartsoft.framework.presentation.view.window.UIRenderContext;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.UIExtAttrebutComponent;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.UIExtAttrebutMap;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.UIExtHtml;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.event.UIEvent;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.event.render.UIInterfaceEventRender;
import tn.com.smartsoft.framework.presentation.view.window.utils.UIExtComponentJs;

public class UIExtAttrebutMapRender extends UIExtAttrebutComponentRender {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UIExtAttrebutMapRender(UIExtAttrebutMap extAttrebutMap) {
		super(extAttrebutMap);
	}

	public void renderStart(UIRenderContext context, StringBuffer sb) {
		UIExtAttrebutMap extAttrebutMap = (UIExtAttrebutMap) renderComponent;
		String[] atts = extAttrebutMap.geAttributesName();
		sb.append("{");
		for (int i = 0; i < atts.length; i++) {
			String key = atts[i];
			String value = extAttrebutMap.getAttribut(atts[i]);
			if (value.startsWith("@")) {
				value = StringUtils.replace(value, "@", "$");
				value = StringUtils.replace(value, "{", "[");
				value = StringUtils.replace(value, "}", "]");
				value = context.webContext().userDesktop().ressourceManager().resolvePath(context.webContext(), extAttrebutMap.getWindow().getId(), extAttrebutMap.getId(), "load", value);
			} else
				value = extAttrebutMap.getWindow().evalExpressionToString(value);

			if (!StringUtils.isNumeric(value) && StringUtils.equalsIgnoreCase(value, "true") && !StringUtils.equalsIgnoreCase(value, "false"))
				sb.append("'").append(key).append("':'").append(JSEncodeUtil.encodeJavaScript(value)).append("'");
			else
				sb.append("'").append(key).append("':").append(value);
			if (i < (atts.length - 1))
				sb.append(",");
		}
		// sb.append("}");
	}

	public void renderChilds(UIRenderContext context, StringBuffer sb) {
		UIExtAttrebutMap extAttrebutMap = (UIExtAttrebutMap) renderComponent;
		for (int i = 0; i < extAttrebutMap.itemIds().size(); i++) {
			UIComponent child = extAttrebutMap.getItem((String) extAttrebutMap.itemIds().get(i));
			if (child instanceof UIExtHtml) {
				UIExtAttrebutComponent rendrableComponent = (UIExtAttrebutComponent) child;
				UIExtAttrebutMapRender uiRender = (UIExtAttrebutMapRender) rendrableComponent.getRender();
				uiRender.setParentRender(this);
				uiRender.setTabRender(getTabRender() + "  ");
				uiRender.render(context, sb);
			}
		}
	}

	public void renderEnd(UIRenderContext context, StringBuffer sb) {
		UIExtAttrebutMap extAttrebutMap = (UIExtAttrebutMap) renderComponent;
		String[] actionEventNames = extAttrebutMap.getEventNames();
		for (int i = 0; i < actionEventNames.length; i++) {
			final UIEvent uiEvent = extAttrebutMap.getEvent(actionEventNames[i]);
			if (uiEvent.isRendred()) {
				UIRender eventRender = uiEvent.getRender();
				eventRender.setParentRender(parentRender);
				eventRender.render(context);
				if (uiEvent.getStartUp())
					context.addOnStartUpAction(uiEvent.getComponentId(), uiEvent.getEventName());
				if (eventRender instanceof UIInterfaceEventRender) {
					context.addEndlaodDesktopJs("Ext.EventManager.on(\"" + extAttrebutMap.getId() + "\", \"" + actionEventNames[i] + "\", "
							+ ((UIInterfaceEventRender) eventRender).createHandelServerAction(context) + ");");
				}
			}
		}
	}

	public void render(UIRenderContext context, StringBuffer sb) {
		renderStart(context, sb);
		renderChilds(context, sb);
		renderEnd(context, sb);
	}

	public void render(UIRenderContext context) {
		StringBuffer sb = new StringBuffer();
		render(context, sb);
		context.renderln(sb.toString());
	}

	public void render(UIRenderContext context, UIExtComponentJs componentJs) {
		UIExtHtml extHtml = (UIExtHtml) renderComponent;
		StringBuffer sb = new StringBuffer();
		render(context, sb);
		componentJs.addStringValue(extHtml.getExtAttrebut(), StringUtils.chomp(sb.toString()));
	}
}
