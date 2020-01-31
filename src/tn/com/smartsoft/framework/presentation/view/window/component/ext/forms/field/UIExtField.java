package tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import tn.com.smartsoft.framework.presentation.utils.ActionMode;
import tn.com.smartsoft.framework.presentation.view.action.model.PropertyModel;
import tn.com.smartsoft.framework.presentation.view.action.request.ControlerRequestField;
import tn.com.smartsoft.framework.presentation.view.action.request.RequestField;
import tn.com.smartsoft.framework.presentation.view.action.request.ValidatorRequestField;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.UIExtBoxComponent;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field.utlis.UIExtControlerRequestField;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.grid.UIExtGridColumn;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.store.UIExtFieldStore;

public abstract class UIExtField extends UIExtBoxComponent implements RequestField {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected boolean editable;
	protected boolean editableSet = false;
	protected boolean allowBlank = true;
	protected String nextElementId;
	protected boolean validateOnBlur;
	protected String messageBlankKey;
	protected String messageTypeKey;
	private String property;
	private String linkProperty;
	protected Boolean hideLabel = Boolean.FALSE;
	private String libelleExp;
	private int maxLength;
	private String displayField;
	private String indicatorIcon;
	private String indicatorTip;
	private String indicatorText;
	private String indicatorCls;
	private String note;
	private String noteAlign;
	private String noteCls;
	protected List<ValidatorRequestField> validatorsData = new ArrayList<ValidatorRequestField>();
	protected List<ValidatorRequestField> validators = new ArrayList<ValidatorRequestField>();
	
	public void addDataValidator(ValidatorRequestField validator) {
		validatorsData.add(validator);
	}
	
	public void addValidator(ValidatorRequestField validator) {
		validators.add(validator);
	}
	
	public List<ValidatorRequestField> getValidatorsData() {
		return validatorsData;
	}
	
	public List<ValidatorRequestField> getValidators() {
		return validators;
	}
	
	public int getMaxLength() {
		return maxLength;
	}
	
	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}
	
	public String getLibelleExp() {
		return libelleExp;
	}
	
	public void setLibelleExp(String libelleExp) {
		this.libelleExp = libelleExp;
	}
	
	public String getLibelle() {
		if (StringUtils.isBlank(getLibelleExp()))
			return model().getLibelle();
		else {
			return getWindow().evalExpressionToString(getLibelleExp());
		}
	}
	
	public PropertyModel model() {
		if (isParentGridColumn()) {
			return getParentGridColumn().model();
		}
		PropertyModel model = (PropertyModel) getUserAction().findPropertyModel(property);
		return model;
	}
	
	public String getLinkProperty() {
		return linkProperty;
	}
	
	public void setLinkProperty(String linkProperty) {
		this.linkProperty = linkProperty;
	}
	
	public String getProperty() {
		return property;
	}
	
	public void setProperty(String property) {
		this.property = property;
	}
	
	public String getNextElementId() {
		return nextElementId;
	}
	
	public void setNextElementId(String nextElementId) {
		this.nextElementId = nextElementId;
	}
	
	public boolean isEditable() {
		if (!editableSet)
			editable = !ActionMode.isReadMode(model().getMode()) && !ActionMode.isDeleteMode(model().getMode());
		return editable;
	}
	
	public void setEditable(boolean editable) {
		editableSet = true;
		this.editable = editable;
	}
	
	public boolean isAllowBlank() {
		return allowBlank;
	}
	
	public void setAllowBlank(boolean allowBlank) {
		this.allowBlank = allowBlank;
	}
	
	public boolean isValidateOnBlur() {
		return validateOnBlur;
	}
	
	public void setValidateOnBlur(boolean validateOnBlur) {
		this.validateOnBlur = validateOnBlur;
	}
	
	public String getMessageBlankKey() {
		return messageBlankKey;
	}
	
	public void setMessageBlankKey(String messageBlankKey) {
		this.messageBlankKey = messageBlankKey;
	}
	
	public String getMessageTypeKey() {
		return messageTypeKey;
	}
	
	public void setMessageTypeKey(String messageTypeKey) {
		this.messageTypeKey = messageTypeKey;
	}
	
	public String getCustomValue() {
		if (StringUtils.isBlank(property))
			return "";
		return getUserAction().getModel().getCustomValue(property);
	}
	
	public boolean isJsonField() {
		return false;
	}
	
	public Object getValue() {
		if (isParentGridColumn()) {
			return null;
		}
		return getUserAction().getModel().getValue(property);
	}
	
	public Object getValue(int index) {
		if (!isParentGridColumn()) {
			return null;
		}
		UIExtFieldStore fieldStore = getParentGridColumn().getFiledStore();
		return fieldStore.getValue(index);
	}
	
	public Boolean isHideLabel() {
		return hideLabel;
	}
	
	public void setHideLabel(Boolean hideLabel) {
		this.hideLabel = hideLabel;
	}
	
	public Class<?> getJavaType() {
		return model().getJavaType();
	}
	
	public String getUserType() {
		return model().getUserType();
	}
	
	public UIExtGridColumn getParentGridColumn() {
		return (UIExtGridColumn) getParent();
	}
	
	public boolean isParentGridColumn() {
		return (getParent() instanceof UIExtGridColumn);
	}
	
	public String getDisplayField() {
		return displayField;
	}
	
	public void setDisplayField(String displayField) {
		this.displayField = displayField;
	}
	
	public String getIndicatorIcon() {
		return indicatorIcon;
	}
	
	public void setIndicatorIcon(String indicatorIcon) {
		this.indicatorIcon = indicatorIcon;
	}
	
	public String getIndicatorTip() {
		return getWindow().evalExpressionToString(indicatorTip);
	}
	
	public void setIndicatorTip(String indicatorTip) {
		this.indicatorTip = indicatorTip;
	}
	
	public String getIndicatorText() {
		return getWindow().evalExpressionToString(indicatorText);
	}
	
	public void setIndicatorText(String indicatorText) {
		this.indicatorText = indicatorText;
	}
	
	public String getIndicatorCls() {
		return indicatorCls;
	}
	
	public void setIndicatorCls(String indicatorCls) {
		this.indicatorCls = indicatorCls;
	}
	
	public String getNote() {
		return getWindow().evalExpressionToString(note);
	}
	
	public void setNote(String note) {
		this.note = note;
	}
	
	public String getNoteAlign() {
		return noteAlign;
	}
	
	public void setNoteAlign(String noteAlign) {
		this.noteAlign = noteAlign;
	}
	
	public String getNoteCls() {
		return noteCls;
	}
	
	public void setNoteCls(String noteCls) {
		this.noteCls = noteCls;
	}
	
	public ControlerRequestField getControlerRequestField() {
		return new UIExtControlerRequestField(this);
	}
}
