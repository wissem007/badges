package tn.com.smartsoft.framework.presentation.view.response;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import tn.com.smartsoft.framework.presentation.context.ResponseView;
import tn.com.smartsoft.framework.presentation.view.response.file.FileResponse;
import tn.com.smartsoft.iap.administration.referentiel.file.beans.FileBean;

public class ResponseFile extends ResponseView{

	public void response(Object model) {
		if (model instanceof FileResponse) {
			FileResponse fileResponse = (FileResponse) model;
			Map<String, Object> headers = fileResponse.getHeaders();
			Iterator<String> iterator = headers.keySet().iterator();
			while (iterator.hasNext()) {
				String key = (String) iterator.next();
				Object value = headers.get(key);
				if (value != null) {
					if (value instanceof Date) {
						context.response().setHeader(key, (Date) value);
					} else if (value instanceof Integer) {
						context.response().setHeader(key, (Integer) value);
					} else if (value instanceof Long) {
						context.response().setHeader(key, (Long) value);
					} else {
						context.response().setHeader(key, String.valueOf(value));
					}
				}
			}
			StringWriter out = fileResponse.getWriter();
			context.response().write(out.getBuffer());
			try {
				context.response().getOutputStream().flush();
			} catch (IOException e) {
				log.error(e);
			}
		} else if (model instanceof FileBean) {
			FileBean fileBean = (FileBean) model;
			if (!fileBean.isView()) {
				context.response().setHeader("Content-Disposition", "attachment; filename" + "=" + fileBean.getName());
			}
			if (fileBean.getContentType() != null) context.response().setHeader("Content-Type", fileBean.getContentType());
			if (fileBean.getSizeData() != null) context.response().setHeader("Content-Length", "" + fileBean.getSizeData());
			if (fileBean.getInputStream() != null) context.response().writeFrom(fileBean.getInputStream());
			try {
				context.response().getOutputStream().flush();
			} catch (IOException e) {
				log.error(e);
			}
		}
	}
}
