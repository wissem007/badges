package tn.com.smartsoft.framework.presentation.view.script.minifier.js;

import java.io.InputStream;
import java.io.Serializable;
import java.io.Writer;

import tn.com.smartsoft.framework.presentation.view.script.minifier.IJsMinifier;
import tn.com.smartsoft.framework.presentation.view.script.minifier.js.JSMin.UnterminatedCommentException;
import tn.com.smartsoft.framework.presentation.view.script.minifier.js.JSMin.UnterminatedRegExpLiteralException;
import tn.com.smartsoft.framework.presentation.view.script.minifier.js.JSMin.UnterminatedStringLiteralException;

public class JsMinMinifier implements IJsMinifier, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void minify(InputStream in, Writer out) throws Exception {
		try {
			new JSMin(in, out).jsmin();
			// out.flush();
		} catch (UnterminatedRegExpLiteralException e) {
			e.printStackTrace();
		} catch (UnterminatedCommentException e) {
			e.printStackTrace();
		} catch (UnterminatedStringLiteralException e) {
			e.printStackTrace();
		}
	}

}
