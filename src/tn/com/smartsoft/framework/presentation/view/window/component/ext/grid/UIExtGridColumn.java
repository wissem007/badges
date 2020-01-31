package tn.com.smartsoft.framework.presentation.view.window.component.ext.grid;

import org.apache.commons.lang.StringUtils;
import tn.com.smartsoft.framework.presentation.view.action.model.PropertyModel;
import tn.com.smartsoft.framework.presentation.view.window.UIGenericRendrableComponent;
import tn.com.smartsoft.framework.presentation.view.window.UIRender;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field.UIExtCheckboxField;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field.UIExtComboBoxField;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field.UIExtDateField;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.forms.field.UIExtField;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.grid.render.UIExtGridColumnRender;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.store.UIExtFieldStore;

public class UIExtGridColumn extends UIGenericRendrableComponent{
	
	/**
	 * 
	 */
	private static final long	serialVersionUID		= 1L;
	private String				dataIndex;
	private String				dataIndexDisplay;
	private String				width;
	private boolean				isPrint					= true;
	private String				renderer;
	private Boolean				isResizable;
	private Boolean				isSortable				= true;
	private String				cls;
	private String				libelleExp;
	private Boolean				isLocked;
	private UIExtField			editor;
	private boolean				rendred					= true;
	private String				summaryType;
	private String				summaryRenderer;
	private String				align;
	private String				xtype					= "defauld";
	private Boolean				hideable;
	private Boolean				forceEditable			= false;
	private Integer				xlWidth					= 30;
	private Boolean				readonlyIndexeSearche	= false;
	private Boolean				disableIndexesSearche	= false;
	
	public String getDataIndexDisplay() {
		return dataIndexDisplay;
	}
	public String getXtype() {
		UIExtField field = this.getEditor();
		boolean isCheck = field != null ? field instanceof UIExtCheckboxField : false;
		boolean isDate = field != null ? field instanceof UIExtDateField : false;
		boolean isCombo = field != null ? field instanceof UIExtComboBoxField : false;
		if (xtype == null || xtype.equalsIgnoreCase("defauld")) return isCheck ? "check" : isDate ? "date" : isCombo ? "combo" : "defauld";
		return xtype;
	}
	public Boolean getForceEditable() {
		return forceEditable;
	}
	public void setForceEditable(Boolean forceEditable) {
		this.forceEditable = forceEditable;
	}
	public Boolean getHideable() {
		return hideable;
	}
	public void setHideable(Boolean hideable) {
		this.hideable = hideable;
	}
	public void setXtype(String xtype) {
		this.xtype = xtype;
	}
	public Integer getXlWidth() {
		return xlWidth;
	}
	public void setXlWidth(Integer xlWidth) {
		this.xlWidth = xlWidth;
	}
	public void setDataIndexDisplay(String dataIndexDisplay) {
		this.dataIndexDisplay = dataIndexDisplay;
	}
	public String getAlign() {
		if (StringUtils.isBlank(align) && model() != null && model().getFormatter().getUserType() != null) {
			this.align = model().getFormatter().getUserType().isLtr() ? "left" : "right";
		}
		return align;
	}
	public void setAlign(String align) {
		this.align = align;
	}
	public String getSummaryType() {
		return summaryType;
	}
	public void setSummaryType(String summaryType) {
		this.summaryType = summaryType;
	}
	public String getSummaryRenderer() {
		return summaryRenderer;
	}
	public void setSummaryRenderer(String summaryRenderer) {
		this.summaryRenderer = summaryRenderer;
	}
	public boolean isRendred() {
		return rendred;
	}
	public void setRendred(boolean rendred) {
		this.rendred = rendred;
	}
	public PropertyModel model() {
		if (getFiledStore() != null) return getFiledStore().model();
		return null;
	}
	public UIExtFieldStore getFiledStore() {
		if (getDataIndex() != null) return ((UIExtGrid) getParent()).getStore().getField(getDataIndex());
		return null;
	}
	public Boolean isLocked() {
		return isLocked;
	}
	public void setLocked(Boolean isLocked) {
		this.isLocked = isLocked;
	}
	public String getLibelle() {
		if (StringUtils.isBlank(getLibelleExp()) && model() != null) return model().getLibelle();
		else if (StringUtils.isNotBlank(getLibelleExp())) {
			Object libelle = getWindow().evalExpression(getLibelleExp());
			if (libelle != null) return libelle.toString();
			else return "invalid expression label with :" + getLibelleExp();
		}
		return "";
	}
	public String getLibelleExp() {
		return libelleExp;
	}
	public UIExtField getEditor() {
		return editor;
	}
	public void setEditor(UIExtField editor) {
		if (editor != null) {
			editor.setParent(this);
		}
		this.editor = editor;
	}
	public void setLibelleExp(String libelleExp) {
		this.libelleExp = libelleExp;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public boolean isPrint() {
		return isPrint;
	}
	public void setPrint(boolean isPrint) {
		this.isPrint = isPrint;
	}
	public Boolean isResizable() {
		return isResizable;
	}
	public void setResizable(Boolean isResizable) {
		this.isResizable = isResizable;
	}
	public String getRenderer() {
		return renderer;
	}
	public void setRenderer(String renderer) {
		this.renderer = renderer;
	}
	public Boolean isSortable() {
		return isSortable;
	}
	public void setSortable(Boolean isSortable) {
		this.isSortable = isSortable;
	}
	public String getCls() {
		return cls;
	}
	public void setCls(String cls) {
		this.cls = cls;
	}
	public String getDataIndex() {
		return dataIndex;
	}
	public void setDataIndex(String dataIndex) {
		this.dataIndex = dataIndex;
	}
	public UIRender getRender() {
		return new UIExtGridColumnRender(this);
	}
	public Boolean getReadonlyIndexeSearche() {
		return readonlyIndexeSearche;
	}
	public void setReadonlyIndexeSearche(Boolean readonlyIndexeSearche) {
		this.readonlyIndexeSearche = readonlyIndexeSearche;
	}
	public Boolean getDisableIndexesSearche() {
		return disableIndexesSearche;
	}
	public void setDisableIndexesSearche(Boolean disableIndexesSearche) {
		this.disableIndexesSearche = disableIndexesSearche;
	}
}
