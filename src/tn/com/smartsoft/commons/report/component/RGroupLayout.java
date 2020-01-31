package tn.com.smartsoft.commons.report.component;

public class RGroupLayout extends RConstant {

	private static final long serialVersionUID = 1L;

	/**
	 * When true, the column headers will be printed
	 */
	private boolean printHeaders;

	/**
	 * If true, the value will be printed in an isolated line. If printHeaders
	 * is true, the headers will be shown below the current value
	 */
	private boolean showValueInHeader;

	/**
	 * If true, the value of column used as group criteria will be shown for
	 * each row.
	 */
	private boolean showValueForEachRow;

	/**
	 * used with showValueInHeader, if both are true, the column name will be
	 * printed before the value, i.e: columnX: Value y
	 */
	private boolean showColumnName;

	/**
	 * If true, the column of this group will not be printed
	 */
	private boolean hideColumn;

	public RGroupLayout(boolean showValueInHeader, boolean showValueForEach, boolean showColumnName, boolean hideColumn, boolean printHeaders) {
		this.showValueInHeader = showValueInHeader;
		this.showValueForEachRow = showValueForEach;
		this.showColumnName = showColumnName;
		this.hideColumn = hideColumn;
		this.printHeaders = printHeaders;
	}

	public boolean isShowColumnName() {
		return showColumnName;
	}

	public boolean isShowValueForEachRow() {
		return showValueForEachRow;
	}

	public boolean isShowValueInHeader() {
		return showValueInHeader;
	}

	public static RGroupLayout VALUE_IN_HEADER_AND_FOR_EACH = new RGroupLayout(true, true, false, false, false);
	public static RGroupLayout VALUE_IN_HEADER_AND_FOR_EACH_WITH_HEADERS = new RGroupLayout(true, true, false, false, true);

	public static RGroupLayout VALUE_IN_HEADER = new RGroupLayout(true, false, false, true, false);
	public static RGroupLayout VALUE_IN_HEADER_WITH_HEADERS = new RGroupLayout(true, false, false, true, true);
	public static RGroupLayout VALUE_IN_HEADER_WITH_HEADERS_AND_COLUMN_NAME = new RGroupLayout(true, false, true, true, true);

	public static RGroupLayout VALUE_FOR_EACH = new RGroupLayout(false, true, false, false, false);
	public static RGroupLayout VALUE_FOR_EACH_WITH_HEADERS = new RGroupLayout(false, true, false, false, true);

	public static RGroupLayout EMPTY = new RGroupLayout(false, false, false, true, false);

	public static RGroupLayout DEFAULT = new RGroupLayout(true, false, false, false, false);
	public static RGroupLayout DEFAULT_WITH_HEADER = new RGroupLayout(true, false, false, false, true);

	public boolean isHideColumn() {
		return hideColumn;
	}

	public void setHideColumn(boolean hideColumn) {
		this.hideColumn = hideColumn;
	}

	public boolean isPrintHeaders() {
		return printHeaders;
	}

}
