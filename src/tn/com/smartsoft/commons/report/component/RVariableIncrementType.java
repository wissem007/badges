package tn.com.smartsoft.commons.report.component;

public class RVariableIncrementType extends RConstant {

	private static final long serialVersionUID = 1L;

	private byte value;

	public static final RVariableIncrementType REPORT = new RVariableIncrementType((byte) 1);
	public static final RVariableIncrementType PAGE = new RVariableIncrementType((byte) 2);
	public static final RVariableIncrementType COLUMN = new RVariableIncrementType((byte) 3);
	public static final RVariableIncrementType GROUP = new RVariableIncrementType((byte) 4);
	public static final RVariableIncrementType NONE = new RVariableIncrementType((byte) 5);

	public RVariableIncrementType(byte val) {
		this.value = val;
	}

	public byte getValue() {
		return value;
	}

}
