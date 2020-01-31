package tn.com.smartsoft.framework.presentation.view.script.minifier.js;

import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.io.Serializable;
import java.io.Writer;

public class JSMin implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final int EOF = -1;

	private PushbackInputStream in;

	private Writer out;

	private int theA;

	private int theB;

	public JSMin(InputStream in, Writer out) {
		this.in = new PushbackInputStream(in);
		this.out = out;
	}

	static boolean isAlphanum(int c) {
		return ((c >= 'a' && c <= 'z') || (c >= '0' && c <= '9') || (c >= 'A' && c <= 'Z') || c == '_' || c == '$' || c == '\\' || c > 126);
	}

	int get() throws IOException {
		int c = in.read();

		return getSimpleChar(c);
	}

	int getSimpleChar(int c) {
		if (c >= ' ' || c == '\n' || c == EOF) {
			return c;
		}

		if (c == '\r') {
			return '\n';
		}

		return ' ';
	}

	int peek() throws IOException {
		int lookaheadChar = in.read();
		in.unread(lookaheadChar);
		return lookaheadChar;
	}

	boolean match(String line) throws IOException {
		for (int i = 0; i < line.length(); ++i) {
			int c = get();
			if (!(line.charAt(i) == c)) {
				return false;
			}
		}
		return true;
	}

	int next() throws IOException, UnterminatedCommentException {
		int c = get();
		if (c == '/') {
			switch (peek()) {
			case '/':
				for (;;) {
					c = get();
					if (c <= '\n') {
						return c;
					}
				}

			case '*':
				if (match("*@cc_on")) {
					out.write(theA);
					out.write(("/*@cc_on"));
					return next();
				}
				get();
				for (;;) {
					switch (get()) {
					case '*':
						if (peek() == '/') {
							get();
							return ' ';
						}
						break;
					case EOF:
						throw new UnterminatedCommentException();
					}
				}

			default:
				return c;
			}

		}
		return c;
	}

	void action(int d) throws IOException, UnterminatedRegExpLiteralException, UnterminatedCommentException, UnterminatedStringLiteralException {
		switch (d) {
		case 1:
			out.write(theA);
		case 2:
			theA = theB;
			if (theA == '\'' || theA == '"') {
				for (;;) {
					out.write(theA);
					theA = get();
					if (theA == theB) {
						break;
					}
					if (theA <= '\n') {
						throw new UnterminatedStringLiteralException();
					}
					if (theA == '\\') {
						// manages \+\n, that is allowed in javascript to cut
						// string in end of lines
						if (getSimpleChar(peek()) == '\n') {
							boolean isBreak = false;
							do {
								// jump the line breaks
								while (getSimpleChar(peek()) == '\n')
									get();
								/*
								 * if the string doesnt ends at the begin of the
								 * next line, read the next char (and write at
								 * at the next iteration), else dont read to
								 * read it at the next 'for' start and exit
								 */
								if (peek() == theB) {
									theA = get();
									out.write(theA);
									theA = get();
									isBreak = true;
									break;
								}
								theA = get();
							} while (theA == '\\');
							if (isBreak)
								break;
						} else {// else act the normal way
							out.write(theA);
							theA = get();
						}
					}
				}
			}

		case 3:
			theB = next();
			if (theB == '/'
					&& (theA == '(' || theA == ',' || theA == '=' || theA == ':' || theA == '[' || theA == '!' || theA == '&' || theA == '|' || theA == '?'
							|| theA == '{' || theA == '}' || theA == ';' || theA == '\n')) {
				out.write(theA);
				out.write(theB);
				for (;;) {
					theA = get();
					if (theA == '/') {
						break;
					} else if (theA == '\\') {
						out.write(theA);
						theA = get();
					} else if (theA <= '\n') {
						throw new UnterminatedRegExpLiteralException();
					}
					out.write(theA);
				}
				theB = next();
			}
		}
	}

	public void jsmin() throws IOException, UnterminatedRegExpLiteralException, UnterminatedCommentException, UnterminatedStringLiteralException {
		theA = '\n';
		action(3);
		while (theA != EOF) {
			switch (theA) {
			case ' ':
				if (isAlphanum(theB)) {
					action(1);
				} else {
					action(2);
				}
				break;
			case '\n':
				switch (theB) {
				case '{':
				case '[':
				case '(':
				case '+':
				case '-':
					action(1);
					break;
				case ' ':
					action(3);
					break;
				default:
					if (isAlphanum(theB)) {
						action(1);
					} else {
						action(2);
					}
				}
				break;
			default:
				switch (theB) {
				case ' ':
					if (isAlphanum(theA)) {
						action(1);
						break;
					}
					action(3);
					break;
				case '\n':
					switch (theA) {
					case '}':
					case ']':
					case ')':
					case '+':
					case '-':
					case '"':
					case '\'':
						action(1);
						break;
					default:
						if (isAlphanum(theA)) {
							action(1);
						} else {
							action(3);
						}
					}
					break;
				default:
					action(1);
					break;
				}
			}
		}
		out.flush();
	}

	public class UnterminatedCommentException extends Exception {
		private static final long serialVersionUID = 2155522209874972337L;
	}

	public class UnterminatedStringLiteralException extends Exception {
		private static final long serialVersionUID = 5106848807818299409L;
	}

	public class UnterminatedRegExpLiteralException extends Exception {
		private static final long serialVersionUID = 6020201766077054692L;
	}
}