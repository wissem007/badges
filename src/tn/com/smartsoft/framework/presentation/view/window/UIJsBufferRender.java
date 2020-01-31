package tn.com.smartsoft.framework.presentation.view.window;

import java.util.ArrayList;
import java.util.List;

import tn.com.smartsoft.framework.presentation.UIObject;

public class UIJsBufferRender implements UIObject, UIRenderValue {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<UIRenderValue> jsBufferRender = new ArrayList<UIRenderValue>();

	public UIJsBufferRender addBufferRender(UIRenderValue script) {
		jsBufferRender.add(script);
		return this;
	}

	public UIJsBufferRender addBufferRender(final UIRender render) {
		addBufferRender(render);
		return this;
	}

	public UIJsBufferRender addBufferRender(final String script) {
		addBufferRender(new UIRenderStringValue(script, "    "));
		return this;
	}

	public UIJsBufferRender addBufferRender(final UIJsBufferRender script) {
		addBufferRender(script);
		return this;
	}

	public void render(UIRenderContext context) {
		for (int i = 0; i < jsBufferRender.size(); i++) {
			UIRenderValue renderValue = jsBufferRender.get(i);
			renderValue.render(context);
		}
	}

}
