package tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field.render;

import org.apache.commons.lang.StringUtils;

import tn.com.smartsoft.commons.web.js.JsTable;
import tn.com.smartsoft.framework.presentation.view.window.UIRenderContext;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.UIExtComponent;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field.UIExtCompositeField;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.render.UIExtGenericContainerRender;

public class UIExtCompositeFieldRender extends UIExtGenericContainerRender {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JsTable items = new JsTable();

	public UIExtCompositeFieldRender(UIExtCompositeField renderComponent) {
		super(renderComponent, "Ext.form.CompositeField");
	}

	public void render(UIRenderContext context) {
		UIExtComponent extComponent = (UIExtComponent) renderComponent;
		if (!extComponent.isRendred())
			return;
		renderChilds(context);
		renderStart(context);
		renderEnd(context);
	}

	public void loadConfigs(UIRenderContext context) {
		super.loadConfigs(context);
		UIExtCompositeField extRenderComponent = (UIExtCompositeField) renderComponent;
		if (StringUtils.isNotBlank(extRenderComponent.getLabelExp())) {
			String libelle = extRenderComponent.getLabel();
			if (libelle == null || libelle.equals("null"))
				libelle = "invalide expression libelle :" + extRenderComponent.getLabelExp();
			extComponentJs().addStringValue("fieldLabel", libelle);
		}
		extComponentJs().addObjectValue("items", items.toJs());

	}
}
