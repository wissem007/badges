package tn.com.smartsoft.commons.web.js;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

public class JSEncodeUtil {

	public static void encodeJavaScript(Writer out, String str) throws IOException {
		escapeJavaStyleString(out, str, false);
	}

	public static String encodeJavaScript(String str) {
		return escapeJavaStyleString(str, true);
	}

	public static String escapeJava(String str) {
		return escapeJavaStyleString(str, false);
	}

	private static String escapeJavaStyleString(String str, boolean escapeSingleQuotes) {
		if (str == null) {
			return null;
		}
		try {
			StringWriter writer = new StringWriter(str.length() * 2);
			escapeJavaStyleString(writer, str, escapeSingleQuotes);
			return writer.toString();
		} catch (IOException ioe) {
			// this should never ever happen while writing to a StringWriter
			ioe.printStackTrace();
			return null;
		}
	}

	private static void escapeJavaStyleString(Writer out, String str, boolean escapeSingleQuote) throws IOException {
		if (out == null) {
			throw new IllegalArgumentException("The Writer must not be null");
		}
		if (str == null) {
			return;
		}
		int sz;
		sz = str.length();
		for (int i = 0; i < sz; i++) {
			char ch = str.charAt(i);

			// handle unicode
			 if (ch < 32) {
				switch (ch) {
				case '\b':
					out.write('\\');
					out.write('b');
					break;
				case '\n':
					out.write('\\');
					out.write('n');
					break;
				case '\t':
					out.write('\\');
					out.write('t');
					break;
				case '\f':
					out.write('\\');
					out.write('f');
					break;
				case '\r':
					out.write('\\');
					out.write('r');
					break;
				default:
					if (ch > 0xf) {
						out.write("\\u00" + hex(ch));
					} else {
						out.write("\\u000" + hex(ch));
					}
					break;
				}
			} else {
				switch (ch) {
				case '\'':
					if (escapeSingleQuote) {
						out.write('\\');
					}
					out.write('\'');
					break;
				case '"':
					out.write('\\');
					out.write('"');
					break;
				case '\\':
					out.write('\\');
					out.write('\\');
					break;
				default:
					out.write(ch);
					break;
				}
			}
		}
	}

	private static String hex(char ch) {
		return Integer.toHexString(ch).toUpperCase();
	}

	public static String stripBlankLines(String text) {
		if (text == null) {
			return null;
		}
		BufferedReader in = null;
		try {
			StringBuffer output = new StringBuffer();
			in = new BufferedReader(new StringReader(text));
			boolean doneOneLine = false;
			while (true) {
				String line = in.readLine();
				if (line == null) {
					break;
				}
				if (line.trim().length() > 0) {
					output.append(line);
					output.append('\n');
					doneOneLine = true;
				}
			}
			if (!doneOneLine) {
				output.append('\n');
			}
			return output.toString();
		} catch (IOException ex) {
			throw new IllegalArgumentException("IOExecption unexpected.");
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (IOException ex) {
			}
		}
	}
}
