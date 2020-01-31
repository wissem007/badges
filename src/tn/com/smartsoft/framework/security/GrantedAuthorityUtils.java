package tn.com.smartsoft.framework.security;

import org.apache.commons.lang.StringUtils;

import tn.com.smartsoft.framework.presentation.utils.ActionMode;

public class GrantedAuthorityUtils {
	public static int getMappedMode(ActionMode mode) {
		if (ActionMode.isFindMode(mode)) {
			return 0;
		}
		if (ActionMode.isReadMode(mode)) {
			return 1;
		}
		if (ActionMode.isCreateMode(mode)) {
			return 2;
		}
		if (ActionMode.isUpdateMode(mode)) {
			return 3;
		}
		if (ActionMode.isDeleteMode(mode)) {
			return 4;
		}
		return -1;
	}

	public static int getFieldsPermission(ActionMode mode, Long hex) {
		char[] perm = createFieldsPermission(hex);
		return perm[getMappedMode(mode)];
	}

	public static char[] createFieldsPermission(Long hex) {
		String out = "";
		if (hex != null) {
			String str = Integer.toBinaryString(hex.intValue());
			str = StringUtils.trim(str);
			if (str.length() < 4)
				for (int i = 0; i < 4 - str.length(); i++) {
					out += "0";
				}
			out += str;
		}
		out = StringUtils.isEmpty(out) ? "0000" : out;
		return out.toCharArray();
	}
}
