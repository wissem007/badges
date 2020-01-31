package tn.com.smartsoft.framework.presentation.view.response;

import java.io.IOException;

import tn.com.smartsoft.commons.exceptions.TechnicalException;
import tn.com.smartsoft.framework.presentation.context.ResponseView;
import tn.com.smartsoft.framework.presentation.view.response.ajax.AjaxItemResponseModel;
import tn.com.smartsoft.framework.presentation.view.response.ajax.AjaxResponseModel;
import tn.com.smartsoft.framework.presentation.view.response.ajax.AjaxXmlHelper;

public class AjaxXmlResponse extends ResponseView {

	public void response(Object model) {
		try {
			populateHeaders();
			populateContentType();
			if (model instanceof AjaxResponseModel) {
				AjaxResponseModel ajaxResponse = (AjaxResponseModel) model;
				AjaxXmlHelper.writeAjaxResponseModel(context, ajaxResponse);
			} else if (model instanceof AjaxItemResponseModel) {
				AjaxResponseModel ajaxResponse = new AjaxResponseModel((AjaxItemResponseModel) model);
				AjaxXmlHelper.writeAjaxResponseModel(context, ajaxResponse);
			}
		} catch (IOException e) {
			throw new TechnicalException(e);
		}
	}

}
