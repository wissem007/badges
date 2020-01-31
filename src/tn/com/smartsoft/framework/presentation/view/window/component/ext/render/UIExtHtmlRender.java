package tn.com.smartsoft.framework.presentation.view.window.component.ext.render;

import org.apache.commons.lang.StringUtils;

import tn.com.smartsoft.commons.web.markup.ConcreteElement;
import tn.com.smartsoft.commons.web.markup.util.HtmlComponentUtils;
import tn.com.smartsoft.framework.presentation.view.window.UIComponent;
import tn.com.smartsoft.framework.presentation.view.window.UIRender;
import tn.com.smartsoft.framework.presentation.view.window.UIRenderContext;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.UIExtAttrebutComponent;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.UIExtHtml;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.event.UIEvent;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.event.render.UIInterfaceEventRender;
import tn.com.smartsoft.framework.presentation.view.window.utils.UIExtComponentJs;

public class UIExtHtmlRender extends UIExtAttrebutComponentRender {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ConcreteElement markupElement;

	public UIExtHtmlRender(UIExtAttrebutComponent uiExtHtml) {
		super(uiExtHtml);
	}

	public void renderStart(UIRenderContext context, StringBuffer sb) {
		UIExtHtml uiExtHtml = (UIExtHtml) renderComponent;
		if (StringUtils.isBlank(uiExtHtml.getTag())) {
			sb.append(uiExtHtml.getWindow().evalExpressionToString(uiExtHtml.getContent()));
			return;
		}
		markupElement = HtmlComponentUtils.createElement(uiExtHtml.getTag());
		if (markupElement == null) {
			markupElement = new ConcreteElement();
			markupElement.setElementType(uiExtHtml.getTag());
		}
		if (!uiExtHtml.isRenderBegin())
			return;

		String[] atts = uiExtHtml.geAttributesName();
		for (int i = 0; i < atts.length; i++) {
			String attribut = uiExtHtml.getAttribut(atts[i]);
			if (attribut.startsWith("@")) {
				attribut = StringUtils.replace(attribut, "@", "$");
				attribut = StringUtils.replace(attribut, "{", "[");
				attribut = StringUtils.replace(attribut, "}", "]");
				attribut = context.webContext().userDesktop().ressourceManager().resolvePath(context.webContext(), uiExtHtml.getWindow().getId(), uiExtHtml.getId(), "load", attribut);
				markupElement.addAttribute(atts[i], attribut);
			} else
				markupElement.addAttribute(atts[i], uiExtHtml.getWindow().evalExpressionToString(attribut));
		}

		sb.append(markupElement.createStartTag().replaceAll("'", "\""));
	}

	public void renderChilds(UIRenderContext context, StringBuffer sb) {
		UIExtHtml extHtml = (UIExtHtml) renderComponent;
		if (StringUtils.isBlank(extHtml.getTag())) {
			return;
		}
		if (StringUtils.isNotBlank(extHtml.getContent())) {
			sb.append(extHtml.getWindow().evalExpressionToString(extHtml.getContent()));
		}
		for (int i = 0; i < extHtml.itemIds().size(); i++) {
			UIComponent child = extHtml.getItem((String) extHtml.itemIds().get(i));
			if (child instanceof UIExtHtml) {
				UIExtAttrebutComponent rendrableComponent = (UIExtAttrebutComponent) child;
				UIExtHtmlRender uiRender = (UIExtHtmlRender) rendrableComponent.getRender();
				uiRender.setParentRender(this);
				uiRender.setTabRender(getTabRender() + "  ");
				uiRender.render(context, sb);
			}
		}
	}

	public void renderEnd(UIRenderContext context, StringBuffer sb) {
		UIExtHtml extHtml = (UIExtHtml) renderComponent;
		if (StringUtils.isBlank(extHtml.getTag())) {
			return;
		}
		if (!extHtml.isRenderEnd())
			return;
		if (extHtml.itemSize() == 0 || StringUtils.isNotBlank(extHtml.getContent())) {
			return;
		}
		sb.append(markupElement.createEndTag().replaceAll("'", "\""));
		String[] actionEventNames = extHtml.getEventNames();
		for (int i = 0; i < actionEventNames.length; i++) {
			final UIEvent uiEvent = extHtml.getEvent(actionEventNames[i]);
			if (uiEvent.isRendred()) {
				UIRender eventRender = uiEvent.getRender();
				eventRender.setParentRender(parentRender);
				eventRender.render(context);
				if (uiEvent.getStartUp())
					context.addOnStartUpAction(uiEvent.getComponentId(), uiEvent.getEventName());
				if (eventRender instanceof UIInterfaceEventRender) {
					context.addEndlaodDesktopJs("Ext.EventManager.on(\"" + extHtml.getId() + "\", \"" + actionEventNames[i] + "\", "
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
