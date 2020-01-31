package tn.com.smartsoft.framework.presentation.view.window.comman;

import tn.com.smartsoft.commons.web.markup.html.Link;
import tn.com.smartsoft.commons.web.markup.html.Script;
import tn.com.smartsoft.framework.configuration.ApplicationCacheDictionaryManager;
import tn.com.smartsoft.framework.configuration.ApplicationConfiguration;
import tn.com.smartsoft.framework.configuration.ApplicationManager;
import tn.com.smartsoft.framework.presentation.context.WebContext;
import tn.com.smartsoft.framework.presentation.context.WebPath;
import tn.com.smartsoft.framework.presentation.definition.ImageDefinition;
import tn.com.smartsoft.framework.presentation.definition.WebDefinition;
import tn.com.smartsoft.iap.dictionary.graphique.libelle.beans.LibelleBean;

public class UIRenderUtils {

	private static final String CHAR_SPACE = " ";

	public static void renderSpace(WebContext context, int nbSpace) {
		for (int i = 0; i < nbSpace; i++) {
			render(context, CHAR_SPACE);
		}
	}

	public static void render(WebContext context, String value) {
		context.response().write(value);
	}

	public static void render(WebContext context, int nbSpace, String value) {
		render(context, value);
		renderSpace(context, nbSpace);
	}

	public static void render(WebContext context, String value, String tabRender) {
		render(context, tabRender);
		render(context, value);
	}

	public static void renderSpace(WebContext context) {
		render(context, CHAR_SPACE);
	}

	public static void renderln(WebContext context) {
		render(context, "\n");
	}

	public static void renderln(WebContext context, String value, String tabRender) {
		render(context, value, tabRender);
		render(context, "\n");
	}

	public static void renderln(WebContext context, String value) {
		render(context, value);
		render(context, "\n");
	}

	public static void renderln(WebContext context, int nbSpace, String value) {
		renderSpace(context, nbSpace);
		renderln(context, value);
	}

	public static void renderSrcJs(WebContext context, String src, String tabRender) {
		render(context, tabRender);
		renderSpace(context);
		renderln(context, new Script().createStartTag());
		renderln(context, src);
		render(context, tabRender);
		renderSpace(context);
		renderln(context, new Script().createEndTag());
	}

	public static void renderJs(WebContext context, String url, String tabRender) {
		WebPath path = new WebPath(url);
		render(context, tabRender);
		renderSpace(context);
		Script htmlScript = new Script().setSrc(path.getPath());
		renderln(context, htmlScript.toString());
	}

	public static void renderStartJs(WebContext context, String tabRender) {
		render(context, tabRender);
		renderSpace(context);
		Script htmlScript = new Script();
		renderln(context, htmlScript.createStartTag());
	}

	public static void renderEndJs(WebContext context, String tabRender) {
		render(context, tabRender);
		renderSpace(context);
		Script htmlScript = new Script();
		renderln(context, htmlScript.createEndTag());
	}

	public static void renderCss(WebContext context, String src, String tabRender) {
		Link link = new Link();
		link.setRel("stylesheet");
		link.setType("text/css");
		WebPath path = new WebPath(src);
		link.setHref(path.getPath());
		render(context, tabRender);
		renderSpace(context);
		renderln(context, link.toString());
	}

	public static WebDefinition getWebDefinition() {
		return getApplicationManager().applicationDefinition().getWebDefinition();
	}

	public static ImageDefinition getImagePath(String pathId) {
		ImageDefinition imageDefinition = getWebDefinition().getImageDefinition(pathId);
		if (imageDefinition != null)
			return imageDefinition;
		imageDefinition = new ImageDefinition();
		imageDefinition.setPath(pathId);
		return imageDefinition;
	}

	public static LibelleBean getLibelle(String id) {
		return getApplicationCacheDictionaryManager().getLibelleBean(id);
	}

	public static String getLibelleValue(String id) {
		LibelleBean libelleBean = getLibelle(id);
		return libelleBean == null ? "" : libelleBean.getLibelle();
	}

	public static ApplicationCacheDictionaryManager getApplicationCacheDictionaryManager() {
		return getApplicationManager().applicationCacheDictionaryManager();
	}

	public static ApplicationManager getApplicationManager() {
		return ApplicationConfiguration.applicationManager();
	}
}
