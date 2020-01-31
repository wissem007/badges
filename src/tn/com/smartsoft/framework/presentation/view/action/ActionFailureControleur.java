package tn.com.smartsoft.framework.presentation.view.action;

import tn.com.smartsoft.commons.utils.text.MessageUtil;
import tn.com.smartsoft.framework.presentation.context.HttpRequestType;
import tn.com.smartsoft.framework.presentation.context.WebContext;
import tn.com.smartsoft.framework.presentation.message.UIMessage;
import tn.com.smartsoft.framework.presentation.view.listener.ListenerContext;
import tn.com.smartsoft.framework.presentation.view.response.file.FileResponse;
import tn.com.smartsoft.framework.presentation.view.response.json.JsonItemResponseModel;
import tn.com.smartsoft.framework.presentation.view.window.UIComponent;
import tn.com.smartsoft.framework.presentation.view.window.comman.UIRenderUtils;
import tn.com.smartsoft.iap.dictionary.graphique.message.beans.MessageBean;

public enum ActionFailureControleur {

	AJAX(HttpRequestType.AJAX) {
		public void run(ListenerContext context) {
			JsonItemResponseModel jsonItemResponseModel = new JsonItemResponseModel();
			jsonItemResponseModel.setSuccess(false);
			String messageText = "";
			for (int i = 0; i < context.getMessages().size(); i++) {
				UIMessage message = context.getMessages().get(i);
				MessageBean messageBean = UIRenderUtils.getApplicationCacheDictionaryManager().getMessageBean(message.getCode());
				if (messageBean == null) {
					messageText = messageText + " " + message.getCode();
				} else
					messageText = messageText + " " + MessageUtil.substituteParamsFrLocal(messageBean.getLibelle(), message.getArguments());
			}
			jsonItemResponseModel.addData("message", messageText);
			context.webContext().userDesktop().messagesHandler().resetMessage();
			context.webContext().response(JsonItemResponseModel.JSON_RESPONSE_NAME, jsonItemResponseModel);
		}

	},
	RESOURCE(HttpRequestType.RESOURCE) {
		public void run(ListenerContext context) {
			FileResponse fileResponse = new FileResponse();
			context.webContext().response(FileResponse.FILE_RESPONSE, fileResponse);
		}

	},
	STANDAR(HttpRequestType.STANDAR) {
		public void run(ListenerContext context) {
			WebContext webContext = context.webContext();
			context.userDesktop().messagesHandler().addMessage(context.getMessages());
			((UIComponent) context.source()).getWindow().userAction().goToCurrentWindow(webContext);

		}

	};
	private HttpRequestType typeHttp;

	public HttpRequestType getHttpType() {
		return typeHttp;
	}

	private ActionFailureControleur(HttpRequestType typeHttp) {
		this.typeHttp = typeHttp;
	}

	public abstract void run(ListenerContext context);
}
