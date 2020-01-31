package tn.com.smartsoft.framework.presentation.view.response;

import org.apache.commons.lang.StringUtils;

import tn.com.smartsoft.framework.presentation.context.ResponseView;
import tn.com.smartsoft.framework.presentation.view.response.xl.XlModel;

public class XlResponse extends ResponseView {

	public void response(Object model) {
		XlModel xlITextModel = (XlModel) model;
		populateHeaders();
		if (StringUtils.isNotBlank(xlITextModel.getTitle()))
			populateFileName(xlITextModel.getTitle().toLowerCase(), "xls");
		else
			populateFileName("", "xls");
		populateContentType();
		xlITextModel.render(context);
	}

}
