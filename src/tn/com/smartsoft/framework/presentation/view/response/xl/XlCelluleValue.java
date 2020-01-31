package tn.com.smartsoft.framework.presentation.view.response.xl;

import java.io.Serializable;

public class XlCelluleValue implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Object value;
	private int formatType;
	private int cellType;
	private boolean isCustomerValue;

	public XlCelluleValue(Object value, int formatType, int cellType, boolean isCustomerValue) {
		super();
		this.value = value;
		this.formatType = formatType;
		this.cellType = cellType;
		this.isCustomerValue = isCustomerValue;
	}

	public XlCelluleValue(Object value, int formatType, int cellType ) {
	 this(value, formatType, cellType, false);
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public Object getValue() {
		return value;
	}

	public int getFormatType() {
		return formatType;
	}

	public int getCellType() {
		return cellType;
	}

	public boolean isCustomerValue() {
		return isCustomerValue;
	}

}
