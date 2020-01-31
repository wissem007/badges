package tn.com.digivoip.comman.file;

import java.io.File;
import tn.com.digivoip.comman.string.StringPool;

public class FileNameUtil{

	/** The extension separator character. */
	private static final char	EXTENSION_SEPARATOR	= '.';
	/** The Unix separator character. */
	private static final char	UNIX_SEPARATOR		= '/';
	/** The Windows separator character. */
	private static final char	WINDOWS_SEPARATOR	= '\\';
	/** The system separator character. */
	private static final char	SYSTEM_SEPARATOR	= File.separatorChar;
	/** The separator character that is the opposite of the system separator. */
	private static final char	OTHER_SEPARATOR;
	static {
		if (FileNameUtil.SYSTEM_SEPARATOR == FileNameUtil.WINDOWS_SEPARATOR) {
			OTHER_SEPARATOR = FileNameUtil.UNIX_SEPARATOR;
		} else {
			OTHER_SEPARATOR = FileNameUtil.WINDOWS_SEPARATOR;
		}
	}

	/** Checks if the character is a separator. */
	private static boolean isSeparator(char ch) {
		return (ch == FileNameUtil.UNIX_SEPARATOR) || (ch == FileNameUtil.WINDOWS_SEPARATOR);
	}
	// ---------------------------------------------------------------- normalization
	public static String normalize(String filename) {
		return FileNameUtil.doNormalize(filename, FileNameUtil.SYSTEM_SEPARATOR, true);
	}
	/** Normalizes a path, removing double and single dot path steps.
	 * <p>
	 * This method normalizes a path to a standard format. The input may contain separators in either Unix or Windows format. The output will contain separators in the format of the system.
	 * <p>
	 * A trailing slash will be retained. A double slash will be merged to a single slash (but UNC names are handled). A single dot path segment will be removed. A double dot will cause that path
	 * segment and the one before to be removed. If the double dot has no parent path segment to work with, <code>null</code> is returned.
	 * <p>
	 * The output will be the same on both Unix and Windows except for the separator character.
	 * 
	 * <pre>
	 * /foo//               -->   /foo/
	 * /foo/./              -->   /foo/
	 * /foo/../bar          -->   /bar
	 * /foo/../bar/         -->   /bar/
	 * /foo/../bar/../baz   -->   /baz
	 * //foo//./bar         -->   /foo/bar
	 * /../                 -->   null
	 * ../foo               -->   null
	 * foo/bar/..           -->   foo/
	 * foo/../../bar        -->   null
	 * foo/../bar           -->   bar
	 * //server/foo/../bar  -->   //server/bar
	 * //server/../bar      -->   null
	 * C:\foo\..\bar        -->   C:\bar
	 * C:\..\bar            -->   null
	 * ~/foo/../bar/        -->   ~/bar/
	 * ~/../bar             -->   null
	 * </pre>
	 * (Note the file separator returned will be correct for Windows/Unix)
	 * @param filename the filename to normalize, null returns null
	 * @return the normalized filename, or null if invalid */
	public static String normalize(String filename, boolean unixSeparator) {
		char separator = (unixSeparator ? FileNameUtil.UNIX_SEPARATOR : FileNameUtil.WINDOWS_SEPARATOR);
		return FileNameUtil.doNormalize(filename, separator, true);
	}
	public static String normalizeNoEndSeparator(String filename) {
		return FileNameUtil.doNormalize(filename, FileNameUtil.SYSTEM_SEPARATOR, false);
	}
	/** Normalizes a path, removing double and single dot path steps, and removing any final directory separator.
	 * <p>
	 * This method normalizes a path to a standard format. The input may contain separators in either Unix or Windows format. The output will contain separators in the format of the system.
	 * <p>
	 * A trailing slash will be removed. A double slash will be merged to a single slash (but UNC names are handled). A single dot path segment will be removed. A double dot will cause that path
	 * segment and the one before to be removed. If the double dot has no parent path segment to work with, <code>null</code> is returned.
	 * <p>
	 * The output will be the same on both Unix and Windows except for the separator character.
	 * 
	 * <pre>
	 * /foo//               -->   /foo
	 * /foo/./              -->   /foo
	 * /foo/../bar          -->   /bar
	 * /foo/../bar/         -->   /bar
	 * /foo/../bar/../baz   -->   /baz
	 * /foo//./bar          -->   /foo/bar
	 * /../                 -->   null
	 * ../foo               -->   null
	 * foo/bar/..           -->   foo
	 * foo/../../bar        -->   null
	 * foo/../bar           -->   bar
	 * //server/foo/../bar  -->   //server/bar
	 * //server/../bar      -->   null
	 * C:\foo\..\bar        -->   C:\bar
	 * C:\..\bar            -->   null
	 * ~/foo/../bar/        -->   ~/bar
	 * ~/../bar             -->   null
	 * </pre>
	 * (Note the file separator returned will be correct for Windows/Unix)
	 * @param filename the filename to normalize, null returns null
	 * @return the normalized filename, or null if invalid */
	public static String normalizeNoEndSeparator(String filename, boolean unixSeparator) {
		char separator = (unixSeparator ? FileNameUtil.UNIX_SEPARATOR : FileNameUtil.WINDOWS_SEPARATOR);
		return FileNameUtil.doNormalize(filename, separator, false);
	}
	/** Internal method to perform the normalization.
	 * @param filename file name
	 * @param separator separator character to use
	 * @param keepSeparator <code>true</code> to keep the final separator
	 * @return normalized filename */
	private static String doNormalize(String filename, char separator, boolean keepSeparator) {
		if (filename == null) { return null; }
		int size = filename.length();
		if (size == 0) { return filename; }
		int prefix = FileNameUtil.getPrefixLength(filename);
		if (prefix < 0) { return null; }
		char[] array = new char[size + 2]; // +1 for possible extra slash, +2 for arraycopy
		filename.getChars(0, filename.length(), array, 0);
		// fix separators throughout
		char otherSeparator = (separator == FileNameUtil.SYSTEM_SEPARATOR ? FileNameUtil.OTHER_SEPARATOR : FileNameUtil.SYSTEM_SEPARATOR);
		for (int i = 0; i < array.length; i++) {
			if (array[i] == otherSeparator) {
				array[i] = separator;
			}
		}
		// add extra separator on the end to simplify code below
		boolean lastIsDirectory = true;
		if (array[size - 1] != separator) {
			array[size++] = separator;
			lastIsDirectory = false;
		}
		// adjoining slashes
		for (int i = prefix + 1; i < size; i++) {
			if (array[i] == separator && array[i - 1] == separator) {
				System.arraycopy(array, i, array, i - 1, size - i);
				size--;
				i--;
			}
		}
		// dot slash
		for (int i = prefix + 1; i < size; i++) {
			if (array[i] == separator && array[i - 1] == '.' && (i == prefix + 1 || array[i - 2] == separator)) {
				if (i == size - 1) {
					lastIsDirectory = true;
				}
				System.arraycopy(array, i + 1, array, i - 1, size - i);
				size -= 2;
				i--;
			}
		}
		// double dot slash
		outer: for (int i = prefix + 2; i < size; i++) {
			if (array[i] == separator && array[i - 1] == '.' && array[i - 2] == '.' && (i == prefix + 2 || array[i - 3] == separator)) {
				if (i == prefix + 2) { return null; }
				if (i == size - 1) {
					lastIsDirectory = true;
				}
				int j;
				for (j = i - 4; j >= prefix; j--) {
					if (array[j] == separator) {
						// remove b/../ from a/b/../c
						System.arraycopy(array, i + 1, array, j + 1, size - i);
						size -= (i - j);
						i = j + 1;
						continue outer;
					}
				}
				// remove a/../ from a/../c
				System.arraycopy(array, i + 1, array, prefix, size - i);
				size -= (i + 1 - prefix);
				i = prefix + 1;
			}
		}
		if (size <= 0) { // should never be less than 0
			return StringPool.EMPTY;
		}
		if (size <= prefix) { // should never be less than prefix
			return new String(array, 0, size);
		}
		if (lastIsDirectory && keepSeparator) { return new String(array, 0, size); // keep trailing separator
		}
		return new String(array, 0, size - 1); // lose trailing separator
	}
	// -----------------------------------------------------------------------
	/** Concatenates a filename to a base path using normal command line style rules.
	 * <p>
	 * The effect is equivalent to resultant directory after changing directory to the first argument, followed by changing directory to the second argument.
	 * <p>
	 * The first argument is the base path, the second is the path to concatenate. The returned path is always normalized via {@link #normalize(String)}, thus <code>..</code> is handled.
	 * <p>
	 * If <code>pathToAdd</code> is absolute (has an absolute prefix), then it will be normalized and returned. Otherwise, the paths will be joined, normalized and returned.
	 * <p>
	 * The output will be the same on both Unix and Windows except for the separator character.
	 * 
	 * <pre>
	 * /foo/ + bar          -->   /foo/bar
	 * /foo + bar           -->   /foo/bar
	 * /foo + /bar          -->   /bar
	 * /foo + C:/bar        -->   C:/bar
	 * /foo + C:bar         -->   C:bar (*)
	 * /foo/a/ + ../bar     -->   foo/bar
	 * /foo/ + ../../bar    -->   null
	 * /foo/ + /bar         -->   /bar
	 * /foo/.. + /bar       -->   /bar
	 * /foo + bar/c.txt     -->   /foo/bar/c.txt
	 * /foo/c.txt + bar     -->   /foo/c.txt/bar (!)
	 * </pre>
	 * (*) Note that the Windows relative drive prefix is unreliable when used with this method. (!) Note that the first parameter must be a path. If it ends with a name, then the name will be built
	 * into the concatenated path. If this might be a problem, use {@link #getFullPath(String)} on the base path argument.
	 * @param basePath the base path to attach to, always treated as a path
	 * @param fullFilenameToAdd the filename (or path) to attach to the base
	 * @return the concatenated path, or null if invalid */
	public static String concat(String basePath, String fullFilenameToAdd) {
		return FileNameUtil.doConcat(basePath, fullFilenameToAdd, FileNameUtil.SYSTEM_SEPARATOR);
	}
	public static String concat(String basePath, String fullFilenameToAdd, boolean unixSeparator) {
		char separator = (unixSeparator ? FileNameUtil.UNIX_SEPARATOR : FileNameUtil.WINDOWS_SEPARATOR);
		return FileNameUtil.doConcat(basePath, fullFilenameToAdd, separator);
	}
	public static String doConcat(String basePath, String fullFilenameToAdd, char separator) {
		int prefix = FileNameUtil.getPrefixLength(fullFilenameToAdd);
		if (prefix < 0) { return null; }
		if (prefix > 0) { return FileNameUtil.doNormalize(fullFilenameToAdd, separator, true); }
		if (basePath == null) { return null; }
		int len = basePath.length();
		if (len == 0) { return FileNameUtil.doNormalize(fullFilenameToAdd, separator, true); }
		char ch = basePath.charAt(len - 1);
		if (FileNameUtil.isSeparator(ch)) {
			return FileNameUtil.doNormalize(basePath + fullFilenameToAdd, separator, true);
		} else {
			return FileNameUtil.doNormalize(basePath + '/' + fullFilenameToAdd, separator, true);
		}
	}
	// ---------------------------------------------------------------- separator conversion
	/** Converts all separators to the Unix separator of forward slash.
	 * @param path the path to be changed, null ignored
	 * @return the updated path */
	public static String separatorsToUnix(String path) {
		if (path == null || path.indexOf(FileNameUtil.WINDOWS_SEPARATOR) == -1) { return path; }
		return path.replace(FileNameUtil.WINDOWS_SEPARATOR, FileNameUtil.UNIX_SEPARATOR);
	}
	/** Converts all separators to the Windows separator of backslash.
	 * @param path the path to be changed, null ignored
	 * @return the updated path */
	public static String separatorsToWindows(String path) {
		if (path == null || path.indexOf(FileNameUtil.UNIX_SEPARATOR) == -1) { return path; }
		return path.replace(FileNameUtil.UNIX_SEPARATOR, FileNameUtil.WINDOWS_SEPARATOR);
	}
	/** Converts all separators to the system separator.
	 * @param path the path to be changed, null ignored
	 * @return the updated path */
	public static String separatorsToSystem(String path) {
		if (path == null) { return null; }
		if (FileNameUtil.SYSTEM_SEPARATOR == FileNameUtil.WINDOWS_SEPARATOR) {
			return FileNameUtil.separatorsToWindows(path);
		} else {
			return FileNameUtil.separatorsToUnix(path);
		}
	}
	// ---------------------------------------------------------------- prefix
	/** Returns the length of the filename prefix, such as <code>C:/</code> or <code>~/</code>.
	 * <p>
	 * This method will handle a file in either Unix or Windows format.
	 * <p>
	 * The prefix length includes the first slash in the full filename if applicable. Thus, it is possible that the length returned is greater than the length of the input string.
	 * 
	 * <pre>
	 * Windows:
	 * a\b\c.txt           --> ""          --> relative
	 * \a\b\c.txt          --> "\"         --> current drive absolute
	 * C:a\b\c.txt         --> "C:"        --> drive relative
	 * C:\a\b\c.txt        --> "C:\"       --> absolute
	 * \\server\a\b\c.txt  --> "\\server\" --> UNC
	 *
	 * Unix:
	 * a/b/c.txt           --> ""          --> relative
	 * /a/b/c.txt          --> "/"         --> absolute
	 * ~/a/b/c.txt         --> "~/"        --> current user
	 * ~                   --> "~/"        --> current user (slash added)
	 * ~user/a/b/c.txt     --> "~user/"    --> named user
	 * ~user               --> "~user/"    --> named user (slash added)
	 * </pre>
	 * <p>
	 * The output will be the same irrespective of the machine that the code is running on. ie. both Unix and Windows prefixes are matched regardless.
	 * @param filename the filename to find the prefix in, null returns -1
	 * @return the length of the prefix, -1 if invalid or null */
	public static int getPrefixLength(String filename) {
		if (filename == null) { return -1; }
		int len = filename.length();
		if (len == 0) { return 0; }
		char ch0 = filename.charAt(0);
		if (ch0 == ':') { return -1; }
		if (len == 1) {
			if (ch0 == '~') { return 2; // return a length greater than the input
			}
			return (FileNameUtil.isSeparator(ch0) ? 1 : 0);
		} else {
			if (ch0 == '~') {
				int posUnix = filename.indexOf(FileNameUtil.UNIX_SEPARATOR, 1);
				int posWin = filename.indexOf(FileNameUtil.WINDOWS_SEPARATOR, 1);
				if (posUnix == -1 && posWin == -1) { return len + 1; // return a length greater than the input
				}
				posUnix = (posUnix == -1 ? posWin : posUnix);
				posWin = (posWin == -1 ? posUnix : posWin);
				return Math.min(posUnix, posWin) + 1;
			}
			char ch1 = filename.charAt(1);
			if (ch1 == ':') {
				ch0 = Character.toUpperCase(ch0);
				if (ch0 >= 'A' && ch0 <= 'Z') {
					if (len == 2 || FileNameUtil.isSeparator(filename.charAt(2)) == false) { return 2; }
					return 3;
				}
				return -1;
			} else if (FileNameUtil.isSeparator(ch0) && FileNameUtil.isSeparator(ch1)) {
				int posUnix = filename.indexOf(FileNameUtil.UNIX_SEPARATOR, 2);
				int posWin = filename.indexOf(FileNameUtil.WINDOWS_SEPARATOR, 2);
				if ((posUnix == -1 && posWin == -1) || posUnix == 2 || posWin == 2) { return -1; }
				posUnix = (posUnix == -1 ? posWin : posUnix);
				posWin = (posWin == -1 ? posUnix : posWin);
				return Math.min(posUnix, posWin) + 1;
			} else {
				return (FileNameUtil.isSeparator(ch0) ? 1 : 0);
			}
		}
	}
	/** Returns the index of the last directory separator character.
	 * <p>
	 * This method will handle a file in either Unix or Windows format. The position of the last forward or backslash is returned.
	 * <p>
	 * The output will be the same irrespective of the machine that the code is running on.
	 * @param filename the filename to find the last path separator in, null returns -1
	 * @return the index of the last separator character, or -1 if there is no such character */
	public static int indexOfLastSeparator(String filename) {
		if (filename == null) { return -1; }
		int lastUnixPos = filename.lastIndexOf(FileNameUtil.UNIX_SEPARATOR);
		int lastWindowsPos = filename.lastIndexOf(FileNameUtil.WINDOWS_SEPARATOR);
		return Math.max(lastUnixPos, lastWindowsPos);
	}
	/** Returns the index of the last extension separator character, which is a dot.
	 * <p>
	 * This method also checks that there is no directory separator after the last dot. To do this it uses {@link #indexOfLastSeparator(String)} which will handle a file in either Unix or Windows
	 * format.
	 * <p>
	 * The output will be the same irrespective of the machine that the code is running on.
	 * @param filename the filename to find the last path separator in, null returns -1
	 * @return the index of the last separator character, or -1 if there is no such character */
	public static int indexOfExtension(String filename) {
		if (filename == null) { return -1; }
		int extensionPos = filename.lastIndexOf(FileNameUtil.EXTENSION_SEPARATOR);
		int lastSeparator = FileNameUtil.indexOfLastSeparator(filename);
		return (lastSeparator > extensionPos ? -1 : extensionPos);
	}
	// ---------------------------------------------------------------- get
	/** Gets the prefix from a full filename, such as <code>C:/</code> or <code>~/</code>.
	 * <p>
	 * This method will handle a file in either Unix or Windows format. The prefix includes the first slash in the full filename where applicable.
	 * 
	 * <pre>
	 * Windows:
	 * a\b\c.txt           --> ""          --> relative
	 * \a\b\c.txt          --> "\"         --> current drive absolute
	 * C:a\b\c.txt         --> "C:"        --> drive relative
	 * C:\a\b\c.txt        --> "C:\"       --> absolute
	 * \\server\a\b\c.txt  --> "\\server\" --> UNC
	 *
	 * Unix:
	 * a/b/c.txt           --> ""          --> relative
	 * /a/b/c.txt          --> "/"         --> absolute
	 * ~/a/b/c.txt         --> "~/"        --> current user
	 * ~                   --> "~/"        --> current user (slash added)
	 * ~user/a/b/c.txt     --> "~user/"    --> named user
	 * ~user               --> "~user/"    --> named user (slash added)
	 * </pre>
	 * <p>
	 * The output will be the same irrespective of the machine that the code is running on. ie. both Unix and Windows prefixes are matched regardless.
	 * @param filename the filename to query, null returns null
	 * @return the prefix of the file, null if invalid */
	public static String getPrefix(String filename) {
		if (filename == null) { return null; }
		int len = FileNameUtil.getPrefixLength(filename);
		if (len < 0) { return null; }
		if (len > filename.length()) { return filename + FileNameUtil.UNIX_SEPARATOR; // we know this only happens for unix
		}
		return filename.substring(0, len);
	}
	/** Gets the path from a full filename, which excludes the prefix.
	 * <p>
	 * This method will handle a file in either Unix or Windows format. The method is entirely text based, and returns the text before and including the last forward or backslash.
	 * 
	 * <pre>
	 * C:\a\b\c.txt --> a\b\
	 * ~/a/b/c.txt  --> a/b/
	 * a.txt        --> ""
	 * a/b/c        --> a/b/
	 * a/b/c/       --> a/b/c/
	 * </pre>
	 * <p>
	 * The output will be the same irrespective of the machine that the code is running on.
	 * <p>
	 * This method drops the prefix from the result. See {@link #getFullPath(String)} for the method that retains the prefix.
	 * @param filename the filename to query, null returns null
	 * @return the path of the file, an empty string if none exists, null if invalid */
	public static String getPath(String filename) {
		return FileNameUtil.doGetPath(filename, 1);
	}
	/** Gets the path from a full filename, which excludes the prefix, and also excluding the final directory separator.
	 * <p>
	 * This method will handle a file in either Unix or Windows format. The method is entirely text based, and returns the text before the last forward or backslash.
	 * 
	 * <pre>
	 * C:\a\b\c.txt --> a\b
	 * ~/a/b/c.txt  --> a/b
	 * a.txt        --> ""
	 * a/b/c        --> a/b
	 * a/b/c/       --> a/b/c
	 * </pre>
	 * <p>
	 * The output will be the same irrespective of the machine that the code is running on.
	 * <p>
	 * This method drops the prefix from the result. See {@link #getFullPathNoEndSeparator(String)} for the method that retains the prefix.
	 * @param filename the filename to query, null returns null
	 * @return the path of the file, an empty string if none exists, null if invalid */
	public static String getPathNoEndSeparator(String filename) {
		return FileNameUtil.doGetPath(filename, 0);
	}
	/** Does the work of getting the path.
	 * @param filename the filename
	 * @param separatorAdd 0 to omit the end separator, 1 to return it
	 * @return the path */
	private static String doGetPath(String filename, int separatorAdd) {
		if (filename == null) { return null; }
		int prefix = FileNameUtil.getPrefixLength(filename);
		if (prefix < 0) { return null; }
		int index = FileNameUtil.indexOfLastSeparator(filename);
		int endIndex = index + separatorAdd;
		if (prefix >= filename.length() || index < 0 || prefix >= endIndex) { return StringPool.EMPTY; }
		return filename.substring(prefix, endIndex);
	}
	/** Gets the full path from a full filename, which is the prefix + path.
	 * <p>
	 * This method will handle a file in either Unix or Windows format. The method is entirely text based, and returns the text before and including the last forward or backslash.
	 * 
	 * <pre>
	 * C:\a\b\c.txt --> C:\a\b\
	 * ~/a/b/c.txt  --> ~/a/b/
	 * a.txt        --> ""
	 * a/b/c        --> a/b/
	 * a/b/c/       --> a/b/c/
	 * C:           --> C:
	 * C:\          --> C:\
	 * ~            --> ~/
	 * ~/           --> ~/
	 * ~user        --> ~user/
	 * ~user/       --> ~user/
	 * </pre>
	 * <p>
	 * The output will be the same irrespective of the machine that the code is running on.
	 * @param filename the filename to query, null returns null
	 * @return the path of the file, an empty string if none exists, null if invalid */
	public static String getFullPath(String filename) {
		return FileNameUtil.doGetFullPath(filename, true);
	}
	/** Gets the full path from a full filename, which is the prefix + path, and also excluding the final directory separator.
	 * <p>
	 * This method will handle a file in either Unix or Windows format. The method is entirely text based, and returns the text before the last forward or backslash.
	 * 
	 * <pre>
	 * C:\a\b\c.txt --> C:\a\b
	 * ~/a/b/c.txt  --> ~/a/b
	 * a.txt        --> ""
	 * a/b/c        --> a/b
	 * a/b/c/       --> a/b/c
	 * C:           --> C:
	 * C:\          --> C:\
	 * ~            --> ~
	 * ~/           --> ~
	 * ~user        --> ~user
	 * ~user/       --> ~user
	 * </pre>
	 * <p>
	 * The output will be the same irrespective of the machine that the code is running on.
	 * @param filename the filename to query, null returns null
	 * @return the path of the file, an empty string if none exists, null if invalid */
	public static String getFullPathNoEndSeparator(String filename) {
		return FileNameUtil.doGetFullPath(filename, false);
	}
	/** Does the work of getting the path.
	 * @param filename the filename
	 * @param includeSeparator true to include the end separator
	 * @return the path */
	private static String doGetFullPath(String filename, boolean includeSeparator) {
		if (filename == null) { return null; }
		int prefix = FileNameUtil.getPrefixLength(filename);
		if (prefix < 0) { return null; }
		if (prefix >= filename.length()) {
			if (includeSeparator) {
				return FileNameUtil.getPrefix(filename); // add end slash if necessary
			} else {
				return filename;
			}
		}
		int index = FileNameUtil.indexOfLastSeparator(filename);
		if (index < 0) { return filename.substring(0, prefix); }
		int end = index + (includeSeparator ? 1 : 0);
		if (end == 0) {
			end++;
		}
		return filename.substring(0, end);
	}
	/** Gets the name minus the path from a full filename.
	 * <p>
	 * This method will handle a file in either Unix or Windows format. The text after the last forward or backslash is returned.
	 * 
	 * <pre>
	 * a/b/c.txt --> c.txt
	 * a.txt     --> a.txt
	 * a/b/c     --> c
	 * a/b/c/    --> ""
	 * </pre>
	 * <p>
	 * The output will be the same irrespective of the machine that the code is running on.
	 * @param filename the filename to query, null returns null
	 * @return the name of the file without the path, or an empty string if none exists */
	public static String getName(String filename) {
		if (filename == null) { return null; }
		int index = FileNameUtil.indexOfLastSeparator(filename);
		return filename.substring(index + 1);
	}
	/** Gets the base name, minus the full path and extension, from a full filename.
	 * <p>
	 * This method will handle a file in either Unix or Windows format. The text after the last forward or backslash and before the last dot is returned.
	 * 
	 * <pre>
	 * a/b/c.txt --> c
	 * a.txt     --> a
	 * a/b/c     --> c
	 * a/b/c/    --> ""
	 * </pre>
	 * <p>
	 * The output will be the same irrespective of the machine that the code is running on.
	 * @param filename the filename to query, null returns null
	 * @return the name of the file without the path, or an empty string if none exists */
	public static String getBaseName(String filename) {
		return FileNameUtil.removeExtension(FileNameUtil.getName(filename));
	}
	/** Gets the extension of a filename.
	 * <p>
	 * This method returns the textual part of the filename after the last dot. There must be no directory separator after the dot.
	 * 
	 * <pre>
	 * foo.txt      --> "txt"
	 * a/b/c.jpg    --> "jpg"
	 * a/b.txt/c    --> ""
	 * a/b/c        --> ""
	 * </pre>
	 * <p>
	 * The output will be the same irrespective of the machine that the code is running on.
	 * @param filename the filename to retrieve the extension of.
	 * @return the extension of the file or an empty string if none exists. */
	public static String getExtension(String filename) {
		if (filename == null) { return null; }
		int index = FileNameUtil.indexOfExtension(filename);
		if (index == -1) {
			return StringPool.EMPTY;
		} else {
			return filename.substring(index + 1);
		}
	}
	// ----------------------------------------------------------------------- remove
	/** Removes the extension from a filename.
	 * <p>
	 * This method returns the textual part of the filename before the last dot. There must be no directory separator after the dot.
	 * 
	 * <pre>
	 * foo.txt    --> foo
	 * a\b\c.jpg  --> a\b\c
	 * a\b\c      --> a\b\c
	 * a.b\c      --> a.b\c
	 * </pre>
	 * <p>
	 * The output will be the same irrespective of the machine that the code is running on.
	 * @param filename the filename to query, null returns null
	 * @return the filename minus the extension */
	public static String removeExtension(String filename) {
		if (filename == null) { return null; }
		int index = FileNameUtil.indexOfExtension(filename);
		if (index == -1) {
			return filename;
		} else {
			return filename.substring(0, index);
		}
	}
	// ---------------------------------------------------------------- equals
	/** Checks whether two filenames are equal exactly. */
	public static boolean equals(String filename1, String filename2) {
		return FileNameUtil.equals(filename1, filename2, false);
	}
	/** Checks whether two filenames are equal using the case rules of the system. */
	public static boolean equalsOnSystem(String filename1, String filename2) {
		return FileNameUtil.equals(filename1, filename2, true);
	}
	/** Checks whether two filenames are equal optionally using the case rules of the system.
	 * <p>
	 * @param filename1 the first filename to query, may be null
	 * @param filename2 the second filename to query, may be null
	 * @param system whether to use the system (windows or unix)
	 * @return true if the filenames are equal, null equals null */
	private static boolean equals(String filename1, String filename2, boolean system) {
		// noinspection StringEquality
		if (filename1 == filename2) { return true; }
		if (filename1 == null || filename2 == null) { return false; }
		if (system && (FileNameUtil.SYSTEM_SEPARATOR == FileNameUtil.WINDOWS_SEPARATOR)) {
			return filename1.equalsIgnoreCase(filename2);
		} else {
			return filename1.equals(filename2);
		}
	}
	// ---------------------------------------------------------------- split
	/** Splits filename into a array of four Strings containing prefix, path, basename and extension. Path will contain ending separator. */
	public static String[] split(String filename) {
		String prefix = FileNameUtil.getPrefix(filename);
		if (prefix == null) {
			prefix = StringPool.EMPTY;
		}
		int lastSeparatorIndex = FileNameUtil.indexOfLastSeparator(filename);
		int lastExtensionIndex = FileNameUtil.indexOfExtension(filename);
		String path;
		String baseName;
		String extension;
		if (lastSeparatorIndex == -1) {
			path = StringPool.EMPTY;
			if (lastExtensionIndex == -1) {
				baseName = filename.substring(prefix.length());
				extension = StringPool.EMPTY;
			} else {
				baseName = filename.substring(prefix.length(), lastExtensionIndex);
				extension = filename.substring(lastExtensionIndex + 1);
			}
		} else {
			path = filename.substring(prefix.length(), lastSeparatorIndex + 1);
			if (lastExtensionIndex == -1) {
				baseName = filename.substring(prefix.length() + path.length());
				extension = StringPool.EMPTY;
			} else {
				baseName = filename.substring(prefix.length() + path.length(), lastExtensionIndex);
				extension = filename.substring(lastExtensionIndex + 1);
			}
		}
		return new String[] { prefix, path, baseName, extension };
	}
}