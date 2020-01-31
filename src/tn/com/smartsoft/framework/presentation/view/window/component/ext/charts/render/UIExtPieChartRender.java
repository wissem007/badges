package tn.com.smartsoft.framework.presentation.view.window.component.ext.charts.render;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import tn.com.smartsoft.commons.web.js.JsMap;
import tn.com.smartsoft.commons.web.js.JsTable;
import tn.com.smartsoft.framework.presentation.view.window.UIRenderContext;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.charts.UIExtPieChart;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.render.UIExtGenericBoxComponentRender;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.store.UIExtStore;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.store.render.UIExtStoreRender;

public class UIExtPieChartRender extends UIExtGenericBoxComponentRender {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String storeId = null;

	public UIExtPieChartRender(UIExtPieChart renderComponent) {
		super(renderComponent, "Ext.chart.PieChart");
	}

	public void afterRender(UIRenderContext context) {
		super.afterRender(context);
		UIExtPieChart extPieChart = (UIExtPieChart) renderComponent;
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
		UIExtPieChart extPieChart = (UIExtPieChart) renderComponent;
		extComponentJs().addObjectValue("store", storeId);
		extComponentJs().addStringValue("dataField", extPieChart.getDataField());
		extComponentJs().addStringValue("categoryField", extPieChart.getCategoryField());
		
		JsMap styleMap = new JsMap();
		JsTable stylecolors = new JsTable();
		if (StringUtils.isNotBlank(extPieChart.getStyleColorsProperty())) {
			@SuppressWarnings("rawtypes")
			List listColor = (List) context.webContext().userDesktop().curentUserAction().getModel().getValue(extPieChart.getStyleColorsProperty());
			if (listColor != null) {
				for (int i = 0; i < listColor.size(); i++) {
					stylecolors.addStringOption((String) listColor.get(i));
				}
			}
		}
		styleMap.addObjectValue("colors", stylecolors);
		extComponentJs().addObjectValue("seriesStyles", styleMap);
		JsMap legendMap = new JsMap();
		legendMap.addStringValue("display", extPieChart.getDisplayLegend());
		legendMap.addObjectValue("padding", extPieChart.getPaddingLegend());
		JsMap fontMap = new JsMap();
		legendMap.addStringValue("family", extPieChart.getFamilyLegend());
		legendMap.addStringValue("size", extPieChart.getSizeLegend());
		styleMap = new JsMap();
		styleMap.addObjectValue("legend", legendMap);
		styleMap.addObjectValue("font", fontMap);
		extComponentJs().addObjectValue("extraStyle", styleMap);
	}

}
