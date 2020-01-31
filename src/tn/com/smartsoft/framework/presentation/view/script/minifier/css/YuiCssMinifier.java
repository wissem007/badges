package tn.com.smartsoft.framework.presentation.view.script.minifier.css;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.Writer;

import tn.com.smartsoft.framework.presentation.view.script.minifier.ICssMinifier;
import tn.com.smartsoft.framework.presentation.view.script.minifier.YUICompressorAdaptor;

public class YuiCssMinifier implements ICssMinifier, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void minify(InputStream in, Writer out) throws Exception {
		YUICompressorAdaptor.compressCSS(new InputStreamReader(in), out);
	}

}
