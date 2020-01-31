package tn.com.smartsoft.framework.presentation.utils;

import java.io.Serializable;

public class ActionMode implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final ActionMode CREATE = new ActionMode(1);
	public static final ActionMode READ = new ActionMode(2);
	public static final ActionMode UPDATE = new ActionMode(3);
	public static final ActionMode DELETE = new ActionMode(4);
	public static final ActionMode OTHER = new ActionMode(5);
	public static final ActionMode FIND = new ActionMode(6);

	private int mode;

	public ActionMode(int mode) {
		super();
		if (mode > 6)
			mode = 5;
		this.mode = mode;
	}

	public static ActionMode parse(String mode) {
		if ("C".equalsIgnoreCase(mode)) {
			return CREATE;
		}
		if ("R".equalsIgnoreCase(mode)) {
			return READ;
		}
		if ("U".equalsIgnoreCase(mode)) {
			return UPDATE;
		}
		if ("D".equalsIgnoreCase(mode)) {
			return DELETE;
		}
		if ("F".equalsIgnoreCase(mode)) {
			return FIND;
		}
		return OTHER;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + mode;
		return result;
	}

	public Long getActionTypeId() {
		return new Long(mode);
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ActionMode other = (ActionMode) obj;
		if (mode != other.mode)
			return false;
		return true;
	}

	public static boolean isCreateMode(ActionMode mode) {
		return CREATE.equals(mode);
	}

	public static boolean isReadMode(ActionMode mode) {
		return READ.equals(mode);
	}

	public static boolean isUpdateMode(ActionMode mode) {
		return UPDATE.equals(mode);
	}

	public static boolean isDeleteMode(ActionMode mode) {
		return DELETE.equals(mode);
	}

	public static boolean isFindMode(ActionMode mode) {
		return FIND.equals(mode);
	}
}