package tn.com.smartsoft.framework.presentation.view.tags.xtpl;

import java.io.Serializable;
import java.io.StringWriter;

public interface XTplNode extends Serializable {
	public void write(StringWriter buffer);
}
