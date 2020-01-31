package tn.com.smartsoft.framework.presentation.view.script.minifier;

import java.io.IOException;
import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;

import org.apache.log4j.Logger;
import org.mozilla.javascript.ErrorReporter;
import org.mozilla.javascript.EvaluatorException;

import com.yahoo.platform.yui.compressor.CssCompressor;
import com.yahoo.platform.yui.compressor.JavaScriptCompressor;

public class YUICompressorAdaptor implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// adds a line break after X chars
	private final static int LINE_BREAK_POS = 1000;
	private static final Logger log = Logger.getLogger(YUICompressorAdaptor.class);

	public static void compressCSS(Reader in, Writer out) throws IOException {
		CssCompressor compressor = new CssCompressor(in);
		compressor.compress(out, LINE_BREAK_POS);
	}

	public static void compressJS(Reader in, Writer out) throws IOException {
		try {
			JavaScriptCompressor compressor = new JavaScriptCompressor(in, new ErrorReporter() {
				public void warning(String message, String sourceName, int line, String lineSource, int lineOffset) {
					log.warn("[JAVASCRIPT COMPRESSOR WARN] in file : " + sourceName + "\n" + message + "\n" + line + ":" + lineOffset);
				}

				public void error(String message, String sourceName, int line, String lineSource, int lineOffset) {
					log.error("[JAVASCRIPT COMPRESSOR ERROR] in file : " + sourceName + "\nLine : " + lineSource + "\n" + message + "\n" + line + ":" + lineOffset);
				}

				public EvaluatorException runtimeError(String message, String sourceName, int line, String lineSource, int lineOffset) {
					error(message, sourceName, line, lineSource, lineOffset);
					return new EvaluatorException(message);
				}
			});

			compressor.compress(out, LINE_BREAK_POS, true, true, true, true);

		} catch (EvaluatorException e) {
			e.printStackTrace();
		}
	}
}