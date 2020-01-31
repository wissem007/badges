package tn.com.smartsoft.framework.presentation.view.action.utils;

import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import tn.com.smartsoft.framework.presentation.UIObject;
import tn.com.smartsoft.framework.presentation.context.RequestParameters;
import tn.com.smartsoft.framework.presentation.view.action.request.CompositeRequestField;
import tn.com.smartsoft.framework.presentation.view.action.request.ControlerRequestField;
import tn.com.smartsoft.framework.presentation.view.action.request.RequestField;
import tn.com.smartsoft.framework.presentation.view.action.request.data.DataControlerRequestField;
import tn.com.smartsoft.framework.presentation.view.action.request.data.DataRequestField;
import tn.com.smartsoft.framework.presentation.view.action.request.data.DefauldDataRequestField;
import tn.com.smartsoft.framework.presentation.view.listener.ListenerContext;
import tn.com.smartsoft.framework.presentation.view.window.UIWindow;

public class FormRequestUtils {
	public static void handlerRequestData(ListenerContext context) {
		validateDataRequest(context);
		boolean isValid = context.isValidRequest();
		if (!isValid) {
			return;
		}
		Map<String, DataRequestField> dataInputFields = context.getDataRequestFields();
		Iterator<String> it = dataInputFields.keySet().iterator();
		while (it.hasNext()) {
			DataRequestField dataRequestField = context.getDataRequestField(it.next());
			if (dataRequestField instanceof DataControlerRequestField) {
				((DataControlerRequestField) dataRequestField).commit();
			}
		}
	}

	private static void validateDataRequest(ListenerContext context) {
		RequestParameters requestParameters = context.webContext().request().requestParameter();
		String[] parameterNames = requestParameters.getParameterNames();
		for (int i = 0; i < parameterNames.length; i++) {
			String parameterName = parameterNames[i];
			System.out.println("Request Param (" + parameterName + ")  :" + requestParameters.getParameter(parameterName));
			createRequestField(context, parameterName, requestParameters.getParameter(parameterName));
		}
		Iterator<String> fileNamesIt = requestParameters.getFileNames();
		while (fileNamesIt != null && fileNamesIt.hasNext()) {
			String parameterName = (String) fileNamesIt.next();
			System.out.println("Request Param (" + parameterName + ")  :" + requestParameters.getParameter(parameterName));
			createRequestField(context, parameterName, requestParameters.getFile(parameterName));
		}
		Map<String, DataRequestField> dataInputFields = context.getDataRequestFields();
		Iterator<String> it = dataInputFields.keySet().iterator();
		while (it.hasNext()) {
			DataRequestField dataRequestField = context.getDataRequestField(it.next());
			if (dataRequestField instanceof DataControlerRequestField) {
				((DataControlerRequestField) dataRequestField).validate();
			}
		}
	}

	private static void createRequestField(ListenerContext context, String parameterName, Object parameterValue) {
		final UIWindow uiWindow = context.uiWindow();
		String fieldId = StringUtils.substringBefore(parameterName, ":");
		String childFieldId = StringUtils.substringAfter(parameterName, ":");
		UIObject field = uiWindow.findChild(fieldId);
		if (field == null) {
			return;
		}
		if (StringUtils.isNotBlank(childFieldId) && field instanceof CompositeRequestField) {
			CompositeRequestField requestField = (CompositeRequestField) field;
			field = requestField.getRequestField(childFieldId);
			if (field == null) {
				return;
			}
		}
		if (field instanceof RequestField) {
			RequestField requestField = (RequestField) field;
			DefauldDataRequestField defauldDataRequestField = new DefauldDataRequestField(requestField.getControlerRequestField(), parameterValue, context);
			context.addDataRequestField(defauldDataRequestField);
			defauldDataRequestField.validateDataType();
		} else if (field instanceof ControlerRequestField) {
			ControlerRequestField controlerRequestField = (ControlerRequestField) field;
			DefauldDataRequestField dataRequestField = new DefauldDataRequestField(controlerRequestField, parameterValue, context);
			context.addDataRequestField(dataRequestField);
			dataRequestField.validateDataType();
		}
	}
}
