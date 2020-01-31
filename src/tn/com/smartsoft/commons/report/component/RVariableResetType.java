package tn.com.smartsoft.commons.report.component;

public class RVariableResetType extends RConstant {

	private static final long serialVersionUID = 1L;

	private byte value;

	public static final RVariableResetType REPORT = new RVariableResetType((byte) 1);
	public static final RVariableResetType PAGE = new RVariableResetType((byte) 2);
	public static final RVariableResetType COLUMN = new RVariableResetType((byte) 3);
	public static final RVariableResetType GROUP = new RVariableResetType((byte) 4);
	public static final RVariableResetType NONE = new RVariableResetType((byte) 5);

	public RVariableResetType(byte val) {
		this.value = val;
	}

	public byte getValue() {
		return value;
	}

}
