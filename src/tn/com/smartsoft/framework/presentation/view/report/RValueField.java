package tn.com.smartsoft.framework.presentation.view.report;

import net.sf.jasperreports.engine.JRField;

import org.apache.commons.lang.StringUtils;

import tn.com.smartsoft.framework.presentation.view.action.UserActionModel;

public class RValueField implements RValue {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UserActionModel userActionModel;
	private int index = -1;
	private JRField field;
	private String modelName;
	private String property;
	private boolean isDs = false;

	public RValueField(UserActionModel userActionModel, String modelName, JRField field, int index) {
		super();
		this.userActionModel = userActionModel;
		this.modelName = modelName;
		this.field = field;
		this.index = index;
		this.extractProperty();
	}

	public boolean isDs() {
		return isDs;
	}

	private void extractProperty() {
		this.property = field.getDescription();
		if (StringUtils.isBlank(property))
			property = field.getName();
		if (property.startsWith("$ds{")) {
			isDs = true;
			property = property.substring(4);
			if (property.substring(property.length() - 1, property.length()).equals("}"))
				property = property.substring(0, property.length() - 1);
		}
	}

	public String getIndexedProperty(String property) {
		if (StringUtils.isBlank(property))
			return null;
		property = StringUtils.remove(property, "[$i]");
		String name = modelName + "[" + index + "]" + "." + property;
		return name;
	}

	public Object toObject() {
		if (!isDs)
			return userActionModel.getValue(getIndexedProperty(property));
		return new RDataSource(userActionModel.getUserAction(), getIndexedProperty(property));
	}

	public String toString() {
		if (!isDs)
			return userActionModel.getCustomValue(getIndexedProperty(property));
		return "RDataSource Value";
	}
}
