package tn.com.smartsoft.framework.presentation.view.response.json;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import tn.com.smartsoft.commons.utils.text.MessageUtil;
import tn.com.smartsoft.commons.web.js.JSEncodeUtil;
import tn.com.smartsoft.framework.configuration.ApplicationConfiguration;
import tn.com.smartsoft.framework.presentation.context.WebContext;
import tn.com.smartsoft.framework.presentation.message.UIMessage;
import tn.com.smartsoft.iap.dictionary.graphique.message.beans.MessageBean;
import flexjson.JSONSerializer;

public class AjaxJsonlHelper {
	public static void writeJsonResponse(WebContext context, JsonResponseModel jsonResponseModel) throws IOException {
		JsonItemResponseModel[] jsonIdentifyModels = jsonResponseModel.getJsonItemResponse();
		Hashtable<String, DataJsonItemResponseModel> datas = new Hashtable<String, DataJsonItemResponseModel>();
		datas.put("success", new DataJsonItemResponseModel("success", jsonResponseModel.isSuccess(), null, new String[] { "*" }));
		if (StringUtils.isNotBlank(jsonResponseModel.getUserScript()))
			datas.put("script", new DataJsonItemResponseModel("script", jsonResponseModel.getUserScript(), null, new String[] { "*" }));
		writeMessages(context, datas);
		for (int i = 0; i < jsonIdentifyModels.length; i++) {
			JsonItemResponseModel jsonResponse = jsonIdentifyModels[i];
			datas.putAll(jsonResponse.getDatas());
		}
		writeJSON(context, datas);
	}

	public static void writeJsonItemResponse(WebContext context, JsonItemResponseModel jsonResponse) throws IOException {
		Hashtable<String, DataJsonItemResponseModel> datas = new Hashtable<String, DataJsonItemResponseModel>();
		datas.put("success", new DataJsonItemResponseModel("success", jsonResponse.isSuccess(), null, new String[] { "*" }));
		if (StringUtils.isNotBlank(jsonResponse.getUserScript()))
			datas.put("script", new DataJsonItemResponseModel("script", jsonResponse.getUserScript(), null, new String[] { "*" }));
		datas.putAll(jsonResponse.getDatas());
		writeMessages(context, datas);
		writeJSON(context, datas);
	}

	private static void writeJSON(WebContext context, Hashtable<String, DataJsonItemResponseModel> datasJson) {
		JSONSerializer jSONSerializer = new JSONSerializer();
		StringWriter sWriter = new StringWriter();
		Hashtable<String, Object> datas = new Hashtable<String, Object>();
		Enumeration<String> keys = datasJson.keys();
		while (keys.hasMoreElements()) {
			String property = (String) keys.nextElement();
			DataJsonItemResponseModel dataJson = datasJson.get(property);
			datas.put(dataJson.getProperty(), dataJson.getValue());
			if (dataJson.getInclude() != null) {
				for (int i = 0; i < dataJson.getInclude().length; i++) {
					String include = dataJson.getProperty() + "." + dataJson.getInclude()[i];
					jSONSerializer.include(include);
				}
			}
			if (dataJson.getExclude() != null) {
				for (int i = 0; i < dataJson.getExclude().length; i++) {
					String exclude = dataJson.getProperty() + "." + dataJson.getExclude()[i];
					jSONSerializer.exclude(exclude);
				}
			}
		}
		System.out.println(datas);
		jSONSerializer.serialize(datas, sWriter);
		context.response().write(sWriter.getBuffer().toString());
	}

	private static void writeMessages(WebContext context, Hashtable<String, DataJsonItemResponseModel> datas) throws IOException {
		List<UIMessage> messages = context.userDesktop().messagesHandler().getMessages();
		if (messages.isEmpty())
			return;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < messages.size(); i++) {
			UIMessage message = messages.get(i);
			MessageBean messageBean = ApplicationConfiguration.applicationManager().applicationCacheDictionaryManager().getMessageBean(message.getCode());
			if (messageBean == null)
				sb.append(message.getCode());
			else
				sb.append(MessageUtil.substituteParamsFrLocal(messageBean.getLibelle(), message.getArguments())).append("</br>");
		}
		context.userDesktop().messagesHandler().resetMessage();
		datas.put("message", new DataJsonItemResponseModel("message", JSEncodeUtil.encodeJavaScript(sb.toString()), null, new String[] { "*" }));
	}

}
