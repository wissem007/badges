package tn.com.smartsoft.commons.utils;

import java.io.IOException;

public final class AppendingStringBuffer implements java.io.Serializable, CharSequence {
	static final long serialVersionUID = 1L;

	private static final AppendingStringBuffer NULL = new AppendingStringBuffer("null");
	private static final StringBuffer SB_NULL = new StringBuffer("null");

	private char value[];

	private int count;

	public AppendingStringBuffer() {
		this(16);
	}

	public AppendingStringBuffer(int length) {
		value = new char[length];
	}

	public AppendingStringBuffer(CharSequence str) {
		this(str.length() + 16);
		append(str);
	}

	public int length() {
		return count;
	}

	public int capacity() {
		return value.length;
	}

	public void ensureCapacity(int minimumCapacity) {
		if (minimumCapacity > value.length) {
			expandCapacity(minimumCapacity);
		}
	}

	private void expandCapacity(int minimumCapacity) {
		int newCapacity = (value.length + 1) * 2;
		if (newCapacity < 0) {
			newCapacity = Integer.MAX_VALUE;
		} else if (minimumCapacity > newCapacity) {
			newCapacity = minimumCapacity;
		}

		char newValue[] = new char[newCapacity];
		System.arraycopy(value, 0, newValue, 0, count);
		value = newValue;
	}

	public void setLength(int newLength) {
		if (newLength < 0) {
			throw new StringIndexOutOfBoundsException(newLength);
		}

		if (newLength > value.length) {
			expandCapacity(newLength);
		}

		if (count < newLength) {
			for (; count < newLength; count++) {
				value[count] = '\0';
			}
		} else {
			count = newLength;
		}
	}

	public char charAt(int index) {
		if ((index < 0) || (index >= count)) {
			throw new StringIndexOutOfBoundsException(index);
		}
		return value[index];
	}

	public void getChars(int srcBegin, int srcEnd, char dst[], int dstBegin) {
		if (srcBegin < 0) {
			throw new StringIndexOutOfBoundsException(srcBegin);
		}
		if ((srcEnd < 0) || (srcEnd > count)) {
			throw new StringIndexOutOfBoundsException(srcEnd);
		}
		if (srcBegin > srcEnd) {
			throw new StringIndexOutOfBoundsException("srcBegin > srcEnd");
		}
		System.arraycopy(value, srcBegin, dst, dstBegin, srcEnd - srcBegin);
	}

	public void setCharAt(int index, char ch) {
		if ((index < 0) || (index >= count)) {
			throw new StringIndexOutOfBoundsException(index);
		}
		value[index] = ch;
	}

	public AppendingStringBuffer append(Object obj) {
		if (obj instanceof AppendingStringBuffer) {
			return append((AppendingStringBuffer) obj);
		} else if (obj instanceof StringBuffer) {
			return append((StringBuffer) obj);
		}
		return append(String.valueOf(obj));
	}

	public AppendingStringBuffer append(String str) {
		if (str == null) {
			str = String.valueOf(str);
		}

		int len = str.length();
		int newcount = count + len;
		if (newcount > value.length) {
			expandCapacity(newcount);
		}
		str.getChars(0, len, value, count);
		count = newcount;
		return this;
	}

	public AppendingStringBuffer append(AppendingStringBuffer sb) {
		if (sb == null) {
			sb = NULL;
		}

		int len = sb.length();
		int newcount = count + len;
		if (newcount > value.length) {
			expandCapacity(newcount);
		}
		sb.getChars(0, len, value, count);
		count = newcount;
		return this;
	}

	public AppendingStringBuffer append(StringBuffer sb) {
		if (sb == null) {
			sb = SB_NULL;
		}

		int len = sb.length();
		int newcount = count + len;
		if (newcount > value.length) {
			expandCapacity(newcount);
		}
		sb.getChars(0, len, value, count);
		count = newcount;
		return this;
	}

	public AppendingStringBuffer append(StringBuffer sb, int from, int length) {
		if (sb == null) {
			sb = SB_NULL;
		}

		int newcount = count + length;
		if (newcount > value.length) {
			expandCapacity(newcount);
		}
		sb.getChars(from, length, value, count);
		count = newcount;
		return this;
	}

	public AppendingStringBuffer append(char str[]) {
		int len = str.length;
		int newcount = count + len;
		if (newcount > value.length) {
			expandCapacity(newcount);
		}
		System.arraycopy(str, 0, value, count, len);
		count = newcount;
		return this;
	}

	public AppendingStringBuffer append(char str[], int offset, int len) {
		int newcount = count + len;
		if (newcount > value.length) {
			expandCapacity(newcount);
		}
		System.arraycopy(str, offset, value, count, len);
		count = newcount;
		return this;
	}

	public AppendingStringBuffer append(boolean b) {
		if (b) {
			int newcount = count + 4;
			if (newcount > value.length) {
				expandCapacity(newcount);
			}
			value[count++] = 't';
			value[count++] = 'r';
			value[count++] = 'u';
			value[count++] = 'e';
		} else {
			int newcount = count + 5;
			if (newcount > value.length) {
				expandCapacity(newcount);
			}
			value[count++] = 'f';
			value[count++] = 'a';
			value[count++] = 'l';
			value[count++] = 's';
			value[count++] = 'e';
		}
		return this;
	}

	public AppendingStringBuffer append(char c) {
		int newcount = count + 1;
		if (newcount > value.length) {
			expandCapacity(newcount);
		}
		value[count++] = c;
		return this;
	}

	public AppendingStringBuffer append(int i) {
		return append(String.valueOf(i));
	}

	public AppendingStringBuffer append(long l) {
		return append(String.valueOf(l));
	}

	public AppendingStringBuffer append(float f) {
		return append(String.valueOf(f));
	}

	public AppendingStringBuffer append(double d) {
		return append(String.valueOf(d));
	}

	public AppendingStringBuffer delete(int start, int end) {
		if (start < 0) {
			throw new StringIndexOutOfBoundsException(start);
		}
		if (end > count) {
			end = count;
		}
		if (start > end) {
			throw new StringIndexOutOfBoundsException();
		}

		int len = end - start;
		if (len > 0) {
			System.arraycopy(value, start + len, value, start, count - end);
			count -= len;
		}
		return this;
	}

	public AppendingStringBuffer deleteCharAt(int index) {
		if ((index < 0) || (index >= count)) {
			throw new StringIndexOutOfBoundsException();
		}
		System.arraycopy(value, index + 1, value, index, count - index - 1);
		count--;
		return this;
	}

	public AppendingStringBuffer replace(int start, int end, String str) {
		if (start < 0) {
			throw new StringIndexOutOfBoundsException(start);
		}
		if (end > count) {
			end = count;
		}
		if (start > end) {
			throw new StringIndexOutOfBoundsException();
		}

		int len = str.length();
		int newCount = count + len - (end - start);
		if (newCount > value.length) {
			expandCapacity(newCount);
		}

		System.arraycopy(value, end, value, start + len, count - end);
		str.getChars(0, len, value, start);
		count = newCount;
		return this;
	}

	public String substring(int start) {
		return substring(start, count);
	}

	public CharSequence subSequence(int start, int end) {
		return this.substring(start, end);
	}

	public String substring(int start, int end) {
		if (start < 0) {
			throw new StringIndexOutOfBoundsException(start);
		}
		if (end > count) {
			throw new StringIndexOutOfBoundsException(end);
		}
		if (start > end) {
			throw new StringIndexOutOfBoundsException(end - start);
		}
		return new String(value, start, end - start);
	}

	public AppendingStringBuffer insert(int index, char str[], int offset, int len) {
		if ((index < 0) || (index > count)) {
			throw new StringIndexOutOfBoundsException();
		}
		if ((offset < 0) || (offset + len < 0) || (offset + len > str.length)) {
			throw new StringIndexOutOfBoundsException(offset);
		}
		if (len < 0) {
			throw new StringIndexOutOfBoundsException(len);
		}
		int newCount = count + len;
		if (newCount > value.length) {
			expandCapacity(newCount);
		}
		System.arraycopy(value, index, value, index + len, count - index);
		System.arraycopy(str, offset, value, index, len);
		count = newCount;
		return this;
	}

	public AppendingStringBuffer insert(int offset, Object obj) {
		if (obj instanceof AppendingStringBuffer) {
			AppendingStringBuffer asb = (AppendingStringBuffer) obj;
			return insert(offset, asb.value, 0, asb.count);
		} else if (obj instanceof StringBuffer) {
			return insert(offset, (StringBuffer) obj);
		}
		return insert(offset, String.valueOf(obj));
	}

	public AppendingStringBuffer insert(int offset, String str) {
		if ((offset < 0) || (offset > count)) {
			throw new StringIndexOutOfBoundsException();
		}

		if (str == null) {
			str = String.valueOf(str);
		}
		int len = str.length();
		int newcount = count + len;
		if (newcount > value.length) {
			expandCapacity(newcount);
		}
		System.arraycopy(value, offset, value, offset + len, count - offset);
		str.getChars(0, len, value, offset);
		count = newcount;
		return this;
	}

	public AppendingStringBuffer insert(int offset, StringBuffer str) {
		if ((offset < 0) || (offset > count)) {
			throw new StringIndexOutOfBoundsException();
		}

		if (str == null) {
			str = SB_NULL;
		}
		int len = str.length();
		int newcount = count + len;
		if (newcount > value.length) {
			expandCapacity(newcount);
		}
		System.arraycopy(value, offset, value, offset + len, count - offset);
		str.getChars(0, len, value, offset);
		count = newcount;
		return this;
	}

	public AppendingStringBuffer insert(int offset, char str[]) {
		if ((offset < 0) || (offset > count)) {
			throw new StringIndexOutOfBoundsException();
		}
		int len = str.length;
		int newcount = count + len;
		if (newcount > value.length) {
			expandCapacity(newcount);
		}
		System.arraycopy(value, offset, value, offset + len, count - offset);
		System.arraycopy(str, 0, value, offset, len);
		count = newcount;
		return this;
	}

	public AppendingStringBuffer insert(int offset, boolean b) {
		return insert(offset, String.valueOf(b));
	}

	public AppendingStringBuffer insert(int offset, char c) {
		int newcount = count + 1;
		if (newcount > value.length) {
			expandCapacity(newcount);
		}
		System.arraycopy(value, offset, value, offset + 1, count - offset);
		value[offset] = c;
		count = newcount;
		return this;
	}

	public AppendingStringBuffer insert(int offset, int i) {
		return insert(offset, String.valueOf(i));
	}

	public AppendingStringBuffer insert(int offset, long l) {
		return insert(offset, String.valueOf(l));
	}

	public AppendingStringBuffer insert(int offset, float f) {
		return insert(offset, String.valueOf(f));
	}

	public AppendingStringBuffer insert(int offset, double d) {
		return insert(offset, String.valueOf(d));
	}

	public int indexOf(String str) {
		return indexOf(str, 0);
	}

	public int indexOf(String str, int fromIndex) {
		return indexOf(value, 0, count, str.toCharArray(), 0, str.length(), fromIndex);
	}

	static int indexOf(char[] source, int sourceOffset, int sourceCount, char[] target, int targetOffset, int targetCount, int fromIndex) {
		if (fromIndex >= sourceCount) {
			return (targetCount == 0 ? sourceCount : -1);
		}
		if (fromIndex < 0) {
			fromIndex = 0;
		}
		if (targetCount == 0) {
			return fromIndex;
		}

		char first = target[targetOffset];
		int i = sourceOffset + fromIndex;
		int max = sourceOffset + (sourceCount - targetCount);

		startSearchForFirstChar: while (true) {
			/* Look for first character. */
			while (i <= max && source[i] != first) {
				i++;
			}
			if (i > max) {
				return -1;
			}

			/* Found first character, now look at the rest of v2 */
			int j = i + 1;
			int end = j + targetCount - 1;
			int k = targetOffset + 1;
			while (j < end) {
				if (source[j++] != target[k++]) {
					i++;
					/* Look for str's first char again. */
					continue startSearchForFirstChar;
				}
			}
			return i - sourceOffset; /* Found whole string. */
		}
	}

	public int lastIndexOf(String str) {
		return lastIndexOf(str, count);
	}

	public int lastIndexOf(String str, int fromIndex) {
		return lastIndexOf(value, 0, count, str.toCharArray(), 0, str.length(), fromIndex);
	}

	static int lastIndexOf(char[] source, int sourceOffset, int sourceCount, char[] target, int targetOffset, int targetCount, int fromIndex) {
		/*
		 * Check arguments; return immediately where possible. For consistency,
		 * don't check for null str.
		 */
		int rightIndex = sourceCount - targetCount;
		if (fromIndex < 0) {
			return -1;
		}
		if (fromIndex > rightIndex) {
			fromIndex = rightIndex;
		}
		/* Empty string always matches. */
		if (targetCount == 0) {
			return fromIndex;
		}

		int strLastIndex = targetOffset + targetCount - 1;
		char strLastChar = target[strLastIndex];
		int min = sourceOffset + targetCount - 1;
		int i = min + fromIndex;

		startSearchForLastChar: while (true) {
			while (i >= min && source[i] != strLastChar) {
				i--;
			}
			if (i < min) {
				return -1;
			}
			int j = i - 1;
			int start = j - (targetCount - 1);
			int k = strLastIndex - 1;

			while (j > start) {
				if (source[j--] != target[k--]) {
					i--;
					continue startSearchForLastChar;
				}
			}
			return start - sourceOffset + 1;
		}
	}

	public boolean startsWith(CharSequence prefix, int toffset) {
		char ta[] = value;
		int to = toffset;
		int po = 0;
		int pc = prefix.length();
		// Note: toffset might be near -1>>>1.
		if ((toffset < 0) || (toffset > count - pc)) {
			return false;
		}
		while (--pc >= 0) {
			if (ta[to++] != prefix.charAt(po++)) {
				return false;
			}
		}
		return true;
	}

	public boolean startsWith(CharSequence prefix) {
		return startsWith(prefix, 0);
	}

	public boolean endsWith(CharSequence suffix) {
		return startsWith(suffix, count - suffix.length());
	}

	public String toString() {
		return new String(this.value, 0, count);
	}

	public final char[] getValue() {
		return value;
	}

	private void readObject(java.io.ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		value = (char[]) value.clone();
	}

	public boolean equals(Object anObject) {
		if (this == anObject) {
			return true;
		}
		if (anObject instanceof AppendingStringBuffer) {
			AppendingStringBuffer anotherString = (AppendingStringBuffer) anObject;
			int n = count;
			if (n == anotherString.count) {
				char v1[] = value;
				char v2[] = anotherString.value;
				int i = 0;
				while (n-- != 0) {
					if (v1[i] != v2[i++]) {
						return false;
					}
				}
				return true;
			}
		} else if (anObject instanceof CharSequence) {
			CharSequence sequence = (CharSequence) anObject;
			int n = count;
			if (sequence.length() == count) {
				char v1[] = value;
				int i = 0;
				while (n-- != 0) {
					if (v1[i] != sequence.charAt(i++)) {
						return false;
					}
				}
				return true;
			}
		}
		return false;
	}

	public int hashCode() {
		int h = 0;
		if (h == 0) {
			int off = 0;
			char val[] = value;
			int len = count;

			for (int i = 0; i < len; i++) {
				h = 31 * h + val[off++];
			}
		}
		return h;
	}

	public void clear() {
		count = 0;
	}
}