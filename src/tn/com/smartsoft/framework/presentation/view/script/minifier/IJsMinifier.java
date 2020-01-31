package tn.com.smartsoft.framework.presentation.view.script.minifier;

import java.io.InputStream;
import java.io.Writer;

public interface IJsMinifier {
	public void minify(InputStream in, Writer out) throws Exception;
}
