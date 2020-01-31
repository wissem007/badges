package tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field;

import java.util.List;
import tn.com.smartsoft.framework.presentation.view.window.UIRender;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field.render.UIExtTriggerFieldRender;
import tn.com.smartsoft.framework.presentation.view.window.handler.UIDefaultComponentHandler;

public class UIExtTriggerField extends UIExtTextField{

	/**
	 * 
	 */
	private static final long								serialVersionUID	= 1L;
	private UIDefaultComponentHandler<UIExtFieldTrigger>	triggers			= new UIDefaultComponentHandler<UIExtFieldTrigger>(this);
	private String											onCustomTriggerClick;

	public void addFieldTrigger(UIExtFieldTrigger FieldTrigger) {
		triggers.addItem(FieldTrigger);
	}
	public void removeFieldTrigger(String id) {
		triggers.removeItem(id);
	}
	public void removeFieldTrigger(String[] id) {
		triggers.removeItem(id);
	}
	public void removeFieldTrigger(int start, int end) {
		triggers.removeItem(start, end);
	}
	public void removeFieldTrigger(int start) {
		triggers.removeItem(start);
	}
	public List<String> getFieldTriggerIds() {
		return triggers.itemIds();
	}
	public UIExtFieldTrigger getFieldTrigger(String id) {
		return (UIExtFieldTrigger) triggers.getItem(id);
	}
	public String getOnCustomTriggerClick() {
		return onCustomTriggerClick;
	}
	public void setOnCustomTriggerClick(String onCustomTriggerClick) {
		this.onCustomTriggerClick = onCustomTriggerClick;
	}
	public UIRender getRender() {
		return new UIExtTriggerFieldRender(this);
	}
}
