package tn.com.smartsoft.framework.presentation.view.response.ajax;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import tn.com.smartsoft.commons.utils.text.MessageUtil;
import tn.com.smartsoft.commons.xml.utils.NodeValue;
import tn.com.smartsoft.framework.configuration.ApplicationConfiguration;
import tn.com.smartsoft.framework.presentation.context.WebContext;
import tn.com.smartsoft.framework.presentation.message.UIMessage;
import tn.com.smartsoft.iap.dictionary.graphique.message.beans.MessageBean;

public class AjaxXmlHelper {

	public static void writeAjaxResponseModel(WebContext context, AjaxResponseModel ajaxResponseModel) throws IOException {
		StringWriter writer = new StringWriter();
		NodeValue documentNode = new NodeValue("ajax-response", false, true);
		documentNode.createStarNode(writer);
		AjaxItemResponseModel[] ajaxIdentifyModels = ajaxResponseModel.getAjaxElementResponse();
		NodeValue responsesNode = new NodeValue("responses", false, true);
		responsesNode.createStarNode(writer);
		for (int i = 0; i < ajaxIdentifyModels.length; i++) {
			AjaxItemResponseModel ajaxResponse = ajaxIdentifyModels[i];
			writeAjaxResponse(context, writer, ajaxResponse);
		}
		responsesNode.createEndNode(writer);
		writeMessages(context, writer);
		writeUserScript(context, ajaxResponseModel.getUserScript(), writer);
		documentNode.createEndNode(writer);
		context.response().write(writer.toString());
	}

	private static void writeAjaxResponse(WebContext context, StringWriter writer, AjaxItemResponseModel ajaxResponse) throws IOException {
		NodeValue ajaxResponseNode = new NodeValue("response", false, true);
		ajaxResponseNode.setAttribute("type", ajaxResponse.getType().toString());
		ajaxResponseNode.setAttribute("id", ajaxResponse.getId());
		ajaxResponseNode.createStarNode(writer);
		writer.write(ajaxResponse.getXmlContent());
		writeUserScript(context, ajaxResponse.getUserScript(), writer);
		ajaxResponseNode.createEndNode(writer);
	}

	private static void writeUserScript(WebContext context, String userScript, StringWriter writer) throws IOException {
		if (StringUtils.isBlank(userScript))
			return;
		NodeValue ajaxScriptNode = new NodeValue("script", true, true);
		ajaxScriptNode.createStarNode(writer);
		writer.write(userScript);
		ajaxScriptNode.createEndNode(writer);
	}

	private static void writeMessages(WebContext context, StringWriter writer) throws IOException {
		NodeValue ajaxMessagesNode = new NodeValue("messages", false, true);
		ajaxMessagesNode.createStarNode(writer);
		List<UIMessage> messages = context.userDesktop().messagesHandler().getMessages();
		for (int i = 0; i < messages.size(); i++) {
			UIMessage message = messages.get(i);
			MessageBean messageBean = ApplicationConfiguration.applicationManager().applicationCacheDictionaryManager().getMessageBean(message.getCode());
			NodeValue ajaxMessageNode = new NodeValue("message", true, true);
			ajaxMessageNode.createStarNode(writer);
			if (messageBean == null)
				writer.append(message.getCode());
			else
				writer.append(MessageUtil.substituteParamsFrLocal(messageBean.getLibelle(), message.getArguments())).append("\n");
			ajaxMessageNode.createEndNode(writer);
		}
		ajaxMessagesNode.createEndNode(writer);
	}

}
