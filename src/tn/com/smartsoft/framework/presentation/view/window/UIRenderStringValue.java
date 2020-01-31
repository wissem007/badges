package tn.com.smartsoft.framework.presentation.view.window;

public class UIRenderStringValue implements UIRenderValue {
	private String script;
	private String tabRender = "";

	public UIRenderStringValue(String script, String tabRender) {
		super();
		this.script = script;
		this.tabRender = tabRender;
	}

	public UIRenderStringValue(String script) {
		super();
		this.script = script;
	}

	public void render(UIRenderContext context) {
		context.render(script, tabRender);
	}

	public UIRenderStringValue setScript(String script) {
		this.script = script;
		return this;
	}

	public String getScript() {
		return script;
	}

	public UIRenderStringValue setTabRender(String tabRender) {
		this.tabRender = tabRender;
		return this;
	}
}
