package tn.com.smartsoft.framework.presentation.view.desktop;

import java.io.File;
import java.io.IOException;
import org.apache.commons.lang.StringUtils;
import tn.com.digivoip.comman.file.FileUtil;
import tn.com.smartsoft.framework.presentation.context.RequestEventInfo;
import tn.com.smartsoft.framework.presentation.context.WebContext;
import tn.com.smartsoft.framework.presentation.utils.ResolveEventPathUtils;
import tn.com.smartsoft.framework.presentation.view.response.file.FileResponse;
import tn.com.smartsoft.iap.administration.referentiel.file.beans.FileBean;

public class UIRessourceManager implements DesktopElement {

	public void fireActionEvent(WebContext context) {
		String expression = context.request().requestParameter().getParameter("expression");
		expression = StringUtils.replace(expression, "[", "{");
		expression = StringUtils.replace(expression, "]", "}");
		expression = StringUtils.replace(expression, "*s", "[");
		expression = StringUtils.replace(expression, "*e", "]");
		RequestEventInfo requestEventInfo = context.request().requestEventInfo();
		String windowSourceEvent = requestEventInfo.windowSourceEvent();
		FileBean file = (FileBean) context.userDesktop().curentUserAction().getModel().evalExpression(expression, windowSourceEvent);
		if (file != null && file.getBinaryData() != null)
			context.response(FileResponse.FILE_RESPONSE, file);
		else {
			File f = new File(context.session().getServletContext().getRealPath("/images/default/s.gif"));
			file = new FileBean();
			try {
				file.setName("elbow-plus-nl.gif");
				file.setContentType("image/gif");
				file.setBinaryData(FileUtil.readBytes(f));
			} catch (IOException e) {
			}
			context.response(FileResponse.FILE_RESPONSE, file);
		}
	}

	public DesktopPartType desktopPartType() {
		return DesktopPartType.RESSOURCE_PART;
	}

	public String resolvePath(WebContext context, String page, String componentId, String event) {
		return ResolveEventPathUtils.resolveEventPath(this, context, page, componentId, event);
	}

	public String resolvePath(WebContext context, String page, String componentId, String event, String expression) {
		return ResolveEventPathUtils.resolveEventPath(this, context, page, componentId, event) + "?expression=" + expression;
	}
}
