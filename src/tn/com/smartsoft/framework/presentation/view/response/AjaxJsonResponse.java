package tn.com.smartsoft.framework.presentation.view.response;

import java.io.IOException;

import tn.com.smartsoft.commons.exceptions.TechnicalException;
import tn.com.smartsoft.framework.presentation.context.ResponseView;
import tn.com.smartsoft.framework.presentation.view.response.json.AjaxJsonlHelper;
import tn.com.smartsoft.framework.presentation.view.response.json.JsonItemResponseModel;
import tn.com.smartsoft.framework.presentation.view.response.json.JsonResponseModel;

public class AjaxJsonResponse extends ResponseView {

	public void response(Object model) {
		try {
			populateHeaders();
			populateContentType();
			if (model instanceof JsonResponseModel) {
				JsonResponseModel jsonResponseModel = (JsonResponseModel) model;
				AjaxJsonlHelper.writeJsonResponse(context, jsonResponseModel);
			} else if (model instanceof JsonItemResponseModel) {
				AjaxJsonlHelper.writeJsonItemResponse(context, (JsonItemResponseModel) model);
			}
		} catch (IOException e) {
			throw new TechnicalException(e);
		}
	}
}
