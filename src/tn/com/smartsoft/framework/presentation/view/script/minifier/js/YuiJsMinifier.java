package tn.com.smartsoft.framework.presentation.view.script.minifier.js;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.Writer;

import tn.com.smartsoft.framework.presentation.view.script.minifier.IJsMinifier;
import tn.com.smartsoft.framework.presentation.view.script.minifier.YUICompressorAdaptor;

public class YuiJsMinifier implements IJsMinifier, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void minify(InputStream in, Writer out) throws Exception {
		YUICompressorAdaptor.compressJS(new InputStreamReader(in), out);
	}

}
