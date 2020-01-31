package tn.com.smartsoft.framework.presentation.servlets;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tn.com.smartsoft.commons.log.Logger;
import tn.com.smartsoft.commons.utils.ResourcesBuffer;
import tn.com.smartsoft.framework.configuration.ApplicationConfiguration;
import tn.com.smartsoft.framework.presentation.definition.GroupScriptDefinition;
import tn.com.smartsoft.framework.presentation.view.script.builder.AbstractGroupBuilder;

public class RessourceServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public final static String TIMESTAMP = "timestamp";
	private final static Logger log = Logger.getLogger(RessourceServlet.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doRequest(request, response, false);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doRequest(request, response, true);
	}

	protected void doRequest(HttpServletRequest request, HttpServletResponse response, boolean isPost) throws IOException {
		long timestamp = 0;
		if (request.getParameter(TIMESTAMP) != null)
			timestamp = Long.parseLong((String) request.getParameter(TIMESTAMP));
		String path = null;
		try {
			path = getFileRequested(request);
		} catch (ParseException e) {
			log.error("Parsing Error Occured", e);
			response.setStatus(404);
			response.flushBuffer();
			return;
		}
		int lastDot = path.lastIndexOf("."); // already tested in
		String grpName = getGroupNameRequested(path, lastDot);
		String ext = getExtFileRequested(path, lastDot);
		GroupScriptDefinition group = ApplicationConfiguration.applicationManager().applicationDefinition().getWebDefinition().getGroupScriptDefinition(grpName);
		ByteArrayOutputStream bufferOut = new ByteArrayOutputStream();
		response.setContentType("text/plain");
		ResourcesBuffer buffer = null;
		if (ext.startsWith("js")) {
			if (AbstractGroupBuilder.getInstance().buildGroupJsIfNeeded(group, bufferOut)) {
				group.getJsBuffer().update(bufferOut.toByteArray(), timestamp);
			}
			buffer = group.getJsBuffer();
		} else {
			if (AbstractGroupBuilder.getInstance().buildGroupCssIfNeeded(group, bufferOut)) {
				group.getCssBuffer().update(bufferOut.toByteArray(), timestamp);
			}
			buffer = group.getCssBuffer();
		}

		response.getOutputStream().write(buffer.getData(), 0, buffer.getData().length);
	}

	private String getFileRequested(HttpServletRequest request) throws ParseException {
		String path = request.getPathInfo();

		int lastSlash = path.lastIndexOf("/");
		if (lastSlash != -1)
			path = path.substring(lastSlash + 1);

		int lastDot = path.lastIndexOf(".");
		if (lastDot == -1) {
			throw new ParseException("Could not read the group requested from path request.", 0);
		}
		return path;
	}

	private String getGroupNameRequested(String fullFile, int lastDot) {
		return fullFile.substring(0, lastDot);
	}

	private String getExtFileRequested(String fullFile, int lastDot) {
		return fullFile.substring(lastDot + 1, fullFile.length());// extract
		// extension
		// file
	}
}
