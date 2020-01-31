package tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field.utlis;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import tn.com.smartsoft.framework.presentation.view.action.request.ControlerRequestField;
import tn.com.smartsoft.framework.presentation.view.action.request.ValidatorRequestField;
import tn.com.smartsoft.framework.presentation.view.action.request.data.DataRequestField;
import tn.com.smartsoft.framework.presentation.view.action.request.validator.VType;
import tn.com.smartsoft.framework.presentation.view.action.request.validator.VTypeMaxValue;
import tn.com.smartsoft.framework.presentation.view.action.request.validator.VTypeMinValue;
import tn.com.smartsoft.framework.presentation.view.listener.ListenerContext;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field.UIExtComboBoxField;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field.UIExtComparableDateField;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field.UIExtField;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field.UIExtSuggestField;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.store.UIExtFieldStore;

public class UIExtControlerRequestField implements ControlerRequestField {
	/**
	 * 
	 */
	private static final long				serialVersionUID	= 1L;
	private UIExtField						extField;
	protected List<ValidatorRequestField>	validatorsData		= new ArrayList<ValidatorRequestField>();
	protected List<ValidatorRequestField>	validators;
	protected int							row;
	public static final String				MAPPED_DELIM		= "[";
	public static final String				MAPPED_DELIM2		= "]";
	
	public UIExtControlerRequestField(UIExtField extField) {
		super();
		this.extField = extField;
		this.validatorsData.add(new VType(extField.model(), extField.isAllowBlank(), extField.getMessageTypeKey(), extField.getMessageBlankKey()));
		if (extField instanceof UIExtComparableDateField) {
			UIExtComparableDateField extComparableDateField = (UIExtComparableDateField) extField;
			this.validatorsData.add(new VTypeMaxValue(extComparableDateField.getMaxValue(), extComparableDateField.getMessageMaxValueKey()));
			this.validatorsData.add(new VTypeMinValue(extComparableDateField.getMinValue(), extComparableDateField.getMessageMinValueKey()));
		}
		this.validatorsData.addAll(extField.getValidatorsData());
		this.validators = extField.getValidators();
	}
	
	public void setRow(int row) {
		this.row = row;
	}
	
	public UIExtField getExtField() {
		return extField;
	}
	
	public void setExtField(UIExtField extField) {
		this.extField = extField;
	}
	
	public void validateDataType(DataRequestField dataRequestField) {
		for (int i = 0; i < validatorsData.size(); i++) {
			ValidatorRequestField validator = validatorsData.get(i);
			validator.onValidator(dataRequestField);
			if (!dataRequestField.isValidRequestValue())
				break;
		}
	}
	
	public void validate(DataRequestField dataRequestField) {
		for (int i = 0; i < validators.size(); i++) {
			ValidatorRequestField validator = validators.get(i);
			validator.onValidator(dataRequestField);
		}
	}
	
	public void commit(DataRequestField dataRequestField) {
		ListenerContext context = dataRequestField.getListenerContext();
		if (StringUtils.isNotBlank(getProperty()))
			context.userAction().getModel().setValue(getProperty(), dataRequestField.getValue());
		if (StringUtils.isNotBlank(getLinkProperty()))
			context.userAction().getModel().setValue(getLinkProperty(), dataRequestField.getValue());
		if (extField instanceof UIExtComboBoxField || extField instanceof UIExtSuggestField) {
			UIExtComboBoxField extComboBoxField = (UIExtComboBoxField) extField;
			if (StringUtils.isNotBlank(getDisplayProperty(extComboBoxField))) {
				context.userAction().getModel().setValue(getDisplayProperty(extComboBoxField), getDisplayValue(extComboBoxField));
			}
			if (StringUtils.isNotBlank(getKeyProperty(extComboBoxField))) {
				if (dataRequestField.getValue() != null) {
					int row = extComboBoxField.getStore().findIndexById(dataRequestField.getValue());
					if (row > -1) {
						Object value = extComboBoxField.getStore().getField(extComboBoxField.getKeyFieldIndex()).getValue(row);
						context.userAction().getModel().setValue(getKeyProperty(extComboBoxField), value);
					} else
						context.userAction().getModel().setValue(getKeyProperty(extComboBoxField), null);
				} else {
					context.userAction().getModel().setValue(getKeyProperty(extComboBoxField), null);
				}
			}
		}
	}
	
	private Object getDisplayValue(UIExtComboBoxField extComboBoxField) {
		return extComboBoxField.getDisplayValue();
	}
	
	private String getDisplayProperty(UIExtComboBoxField extComboBoxField) {
		return extComboBoxField.getDisplayProperty();
	}
	
	private String getKeyProperty(UIExtComboBoxField extComboBoxField) {
		return extComboBoxField.getKeyProperty();
	}
	
	private String getLinkProperty() {
		if (extField.isParentGridColumn()) {
			UIExtFieldStore fieldStore = extField.getParentGridColumn().getFiledStore();
			return fieldStore.getIndexedProperty(extField.getLinkProperty(), row);
		}
		return extField.getLinkProperty();
	}
	
	private String getProperty() {
		if (extField.isParentGridColumn()) {
			UIExtFieldStore fieldStore = extField.getParentGridColumn().getFiledStore();
			return fieldStore.getIndexedProperty(row);
		}
		return extField.getProperty();
	}
	
	public String getLibelle() {
		if (extField.isParentGridColumn()) {
			return extField.getParentGridColumn().getLibelle();
		}
		return extField.getLibelle();
	}
	
	public String getId() {
		if (extField.isParentGridColumn()) {
			return extField.getParentGridColumn().getDataIndex() + MAPPED_DELIM + row + MAPPED_DELIM2;
		}
		return extField.getId();
	}
	
}
