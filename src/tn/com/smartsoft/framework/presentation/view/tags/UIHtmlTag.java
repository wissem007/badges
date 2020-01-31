package tn.com.smartsoft.framework.presentation.view.tags;

import tn.com.smartsoft.commons.web.markup.util.HtmlComponentUtils;
import tn.com.smartsoft.framework.presentation.view.window.component.html.UIHtmlComponent;
import tn.com.smartsoft.framework.presentation.view.window.component.html.UIHtmlContainerComponent;

public class UIHtmlTag extends UITag {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UIHtmlTag(String tag) {
		super();
		setTag(tag);
		setTagClass(HtmlComponentUtils.isMultiPartElement(getTag()) ? UIHtmlContainerComponent.class : UIHtmlComponent.class);

	}
}
