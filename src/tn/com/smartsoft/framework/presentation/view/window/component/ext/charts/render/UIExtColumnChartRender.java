package tn.com.smartsoft.framework.presentation.view.window.component.ext.charts.render;

import java.util.List;

import tn.com.smartsoft.commons.web.js.JsMap;
import tn.com.smartsoft.commons.web.js.JsTable;
import tn.com.smartsoft.framework.presentation.view.window.UIRenderContext;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.charts.UIExtYFieldChart;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.charts.UIExtColumnChart;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.render.UIExtGenericBoxComponentRender;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.store.UIExtStore;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.store.render.UIExtStoreRender;

public class UIExtColumnChartRender extends UIExtGenericBoxComponentRender {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String storeId = null;

	public UIExtColumnChartRender(UIExtColumnChart renderComponent) {
		super(renderComponent, "Ext.chart.ColumnChart");
	}

	public void afterRender(UIRenderContext context) {
		super.afterRender(context);
		UIExtColumnChart extPieChart = (UIExtColumnChart) renderComponent;
		storeId = extPieChart.getStoreId();
		UIExtStore store = extPieChart.getStore();
		if (extPieChart.isLocalStore()) {
			UIExtStoreRender renderStore = (UIExtStoreRender) store.getRender();
			renderStore.render(context);
		}
		if (store != null)
			extComponentJs().addStringValue("queryParam", store.getQueryParam());
	}

	public void loadConfigs(UIRenderContext context) {
		super.loadConfigs(context);
		UIExtColumnChart extPieChart = (UIExtColumnChart) renderComponent;
		extComponentJs().addObjectValue("store", storeId);
		extComponentJs().addStringValue("xField", extPieChart.getxField());
		extComponentJs().addStringValue("yField", extPieChart.getyField());

		// /

		List listYFields = extPieChart.getListYFields();
		JsTable all = new JsTable();
		if (listYFields != null) {
			for (int i = 0; i < listYFields.size(); i++) {
				UIExtYFieldChart bean = (UIExtYFieldChart) listYFields.get(i);
				JsMap fieldMap = new JsMap();
				JsMap styleMap = new JsMap();
				JsTable stylecolors = new JsTable();

				stylecolors.addStringOption(bean.getColor());
				styleMap.addObjectValue("color", stylecolors);
				fieldMap.addStringValue("yField", bean.getyField());
				fieldMap.addStringValue("displayName", bean.getDisplayName());
				fieldMap.addObjectValue("style", styleMap);
				all.addObjectValue(fieldMap);
			}

			extComponentJs().addObjectValue("series", all);

			JsMap legendMap = new JsMap();
			legendMap.addStringValue("display", "bottom");
			JsMap styleMap = new JsMap();
			styleMap.addObjectValue("legend", legendMap);
			extComponentJs().addObjectValue("extraStyle", styleMap);
		}

	}

}
