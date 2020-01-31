package tn.com.smartsoft.framework.presentation.context;

import java.io.Serializable;

public class WebPath implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String path;

	public WebPath(String path) {
		this.path = normalize(path);
	}

	public WebPath(String parent, String child) {
		this(parent == null || parent.length() == 0 ? child : child == null || child.length() == 0 ? parent : parent + '/' + child);
	}

	public WebPath(WebPath parent, String child) {
		this(parent != null ? parent.getPath() : null, child);
	}

	public WebPath getChildPath(String child) {
		return new WebPath(this, child);
	}

	public String getPath() {
		return path;
	}

	private static final String normalize(String path) {
		if (path == null)
			return "";

		// remove consecutive slashes
		final StringBuffer sb = new StringBuffer(path);
		boolean slash = false;
		for (int j = 0, len = sb.length(); j < len; ++j) {
			final boolean curslash = sb.charAt(j) == '/';
			if (curslash && slash && j != 1) {
				sb.deleteCharAt(j);
				--j;
				--len;
			}
			slash = curslash;
		}

		if (sb.length() > 1 && slash) // remove ending slash except "/"
			sb.setLength(sb.length() - 1);

		// remove ./
		while (sb.length() >= 2 && sb.charAt(0) == '.' && sb.charAt(1) == '/')
			sb.delete(0, 2); // "./" -> ""

		// remove /./
		for (int j = 0; (j = sb.indexOf("/./", j)) >= 0;)
			sb.delete(j + 1, j + 3); // "/./" -> "/"

		// ends with "/."
		int len = sb.length();
		if (len >= 2 && sb.charAt(len - 1) == '.' && sb.charAt(len - 2) == '/')
			if (len == 2)
				return "/";
			else
				sb.delete(len - 2, len);

		// remove /../
		for (int j = 0; (j = sb.indexOf("/../", j)) >= 0;)
			j = removeDotDot(sb, j);

		// ends with "/.."
		len = sb.length();
		if (len >= 3 && sb.charAt(len - 1) == '.' && sb.charAt(len - 2) == '.' && sb.charAt(len - 3) == '/')
			if (len == 3)
				return "/";
			else
				removeDotDot(sb, len - 3);

		return sb.length() == path.length() ? path : sb.toString();
	}

	/**
	 * Removes "/..".
	 * 
	 * @param j
	 *            points '/' in "/.."
	 * @return the next index to search from
	 */
	static private int removeDotDot(StringBuffer sb, int j) {
		int k = j;
		while (--k >= 0 && sb.charAt(k) != '/')
			;

		if (k + 3 == j && sb.charAt(k + 1) == '.' && sb.charAt(k + 2) == '.')
			return j + 4; // don't touch: "../.."

		sb.delete(j, j + 3); // "/.." -> ""

		if (j == 0) // "/.."
			return 0;

		if (k < 0) { // "a/+" => kill "a/", "a" => kill a
			sb.delete(0, j < sb.length() ? j + 1 : j);
			return 0;
		}

		// "/a/+" => kill "/a", "/a" => kill "a"
		if (j >= sb.length())
			++k;
		sb.delete(k, j);
		return k;
	}

}
