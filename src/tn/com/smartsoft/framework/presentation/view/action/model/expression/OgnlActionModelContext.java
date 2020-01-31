package tn.com.smartsoft.framework.presentation.view.action.model.expression;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import tn.com.smartsoft.commons.utils.BeanObjectUtils;
import tn.com.smartsoft.commons.utils.text.MessageUtil;
import tn.com.smartsoft.framework.configuration.ApplicationConfiguration;
import tn.com.smartsoft.framework.context.UserContext;
import tn.com.smartsoft.framework.presentation.formater.Formater;
import tn.com.smartsoft.framework.presentation.formater.FormaterManger;
import tn.com.smartsoft.framework.presentation.view.action.UserActionModel;
import tn.com.smartsoft.framework.presentation.view.action.model.ItemModel;
import tn.com.smartsoft.framework.presentation.view.window.UIComponent;
import tn.com.smartsoft.framework.presentation.view.window.UIWindow;
import tn.com.smartsoft.iap.dictionary.graphique.libelle.beans.LibelleBean;
import tn.com.smartsoft.iap.dictionary.graphique.vue.beans.ViewBean;
import tn.com.smartsoft.iap.dictionary.graphique.vue.beans.ViewLibelleBean;

public class OgnlActionModelContext implements Serializable,ActionModelContext{

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	private UserActionModel		userActionModel;
	public Map<Object, Object>	values;

	public OgnlActionModelContext(UserActionModel userActionModel, Map<Object, Object> values) {
		super();
		this.userActionModel = userActionModel;
		this.values = values;
	}
	public Object getProperty(String property) {
		return new PropertyData(this, property);
	}
	public Object getRandom() {
		return new Double(Math.random());
	}
	public Object getValue(String property) {
		Object value = null;
		if (values != null) value = values.get(property);
		if (value == null && property instanceof String) {
			value = userActionModel.getValue((String) property);
		}
		return value;
	}
	public Map<Object, Object> getValues() {
		return values;
	}
	public String getCustomValue(String property) {
		Object value = null;
		if (values != null) value = values.get(property);
		if (value == null && property instanceof String) { return userActionModel.getCustomValue(property); }
		if (value == null) return "";
		FormaterManger formaterManger = ApplicationConfiguration.applicationManager().formaterManger();
		Formater formater = formaterManger.getFormatter(formaterManger.getMapedTypeClass(value.getClass()));
		return formater.getAsString(value);
	}
	public Object getLibelle(String libelleKey) {
		LibelleBean libelleBean = ApplicationConfiguration.applicationManager().applicationCacheDictionaryManager().getLibelleBean(libelleKey);
		String sysValue = null;
		if (libelleBean != null) sysValue = libelleBean.getLibelle();
		if (sysValue == null) sysValue = "null";
		return sysValue;
	}
	public Object getEditerPar() {
		UserContext userContext = userActionModel.getUserAction().userDesktop().userContext();
		String userText = userContext.getUser().getDisplayName();
		LibelleBean libelleBean = ApplicationConfiguration.applicationManager().applicationCacheDictionaryManager().getLibelleBean("editerFormat");
		String editePar = libelleBean != null ? libelleBean.getLibelle() : "";
		String dateText = ApplicationConfiguration.applicationManager().formaterManger().getAsString(new Date());
		String timeText = ApplicationConfiguration.applicationManager().formaterManger().getAsString(new Time(new Date().getTime()));
		editePar = MessageUtil.substituteParamsFrLocal(editePar, new String[] { userText, dateText, timeText });
		return editePar;
	}
	public Object getUser(String property) {
		UserContext userContext = userActionModel.getUserAction().userDesktop().userContext();
		return BeanObjectUtils.getPropertyValue(userContext.getUser(), property);
	}
	public Object getOrganisme(String property) {
		UserContext userContext = userActionModel.getUserAction().userDesktop().userContext();
		return BeanObjectUtils.getPropertyValue(userContext.getCurrentUserOrganisme().getOrganismeBean(), property);
	}
	public Object getSociete(String property) {
		UserContext userContext = userActionModel.getUserAction().userDesktop().userContext();
		return BeanObjectUtils.getPropertyValue(userContext.getCurrentUserOrganisme().getOrganismeBean().getParentSociete(), property);
	}
	public Object getViewLibelle(String viewId, String labelId) {
		UIWindow window = userActionModel.getUserAction().getWindow(viewId);
		if (window != null) {
			ViewBean viewBean = window.viewBean();
			String sysValue = null;
			if (viewBean != null) {
				ViewLibelleBean viewLibelleBean = viewBean.getViewLibelle(labelId);
				if (viewLibelleBean != null) {
					sysValue = viewLibelleBean.getLibelle();
				}
			}
			if (sysValue == null) return viewId + "." + labelId;
			return sysValue;
		}
		return viewId + "." + labelId;
	}
	public Object getViewLibelle(String labelId) {
		if (StringUtils.indexOf(labelId, ".") >= 0) {
			String windowId = StringUtils.substring(labelId, 0, StringUtils.indexOf(labelId, "."));
			labelId = StringUtils.substring(labelId, StringUtils.indexOf(labelId, ".") + 1, labelId.length());
			return getViewLibelle(windowId, labelId);
		}
		return labelId;
	}
	public Object getViewComponetProperty(String windowId, String componetId, String property) {
		UIWindow window = userActionModel.getUserAction().getWindow(windowId);
		if (window != null) {
			UIComponent component = null;
			if (StringUtils.isBlank(componetId)) component = (UIComponent) window;
			else component = window.findChild(componetId);
			Object sysValue = null;
			if (component != null) {
				sysValue = BeanObjectUtils.getPropertyValue(component, property);
			}
			if (sysValue == null) sysValue = "null";
			return sysValue.toString();
		}
		return windowId + "." + componetId + "." + property;
	}
	public Object getViewComponetProperty(String componetId) {
		if (StringUtils.indexOf(componetId, ".") >= 0) {
			String windowId = StringUtils.substring(componetId, 0, StringUtils.indexOf(componetId, "."));
			componetId = StringUtils.substring(componetId, StringUtils.indexOf(componetId, ".") + 1, componetId.length());
			String property = null;
			if (StringUtils.indexOf(componetId, ".") >= 0) {
				property = StringUtils.substring(componetId, StringUtils.indexOf(componetId, ".") + 1, componetId.length());
				componetId = StringUtils.substring(componetId, 0, StringUtils.indexOf(componetId, "."));
			}
			return getViewComponetProperty(windowId, componetId, property);
		}
		return componetId;
	}
	public Object getPropertyApplication(String property) {
		Object bean = ApplicationConfiguration.applicationManager().applicationCacheDictionaryManager().getApplicationBean();
		Object value = BeanObjectUtils.getPropertyValue(bean, property);
		return value;
	}
	public Object getPropertyLibelle(String propertyId) {
		ItemModel propertyModel = userActionModel.findFieldModel(propertyId);
		String sysValue = propertyId;
		if (propertyModel != null) sysValue = propertyModel.getLibelle();
		return sysValue;
	}
}
