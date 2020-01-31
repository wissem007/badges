package tn.com.smartsoft.framework.presentation.view.window.component.ext.event;

import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;

import tn.com.smartsoft.commons.web.js.JsMap;
import tn.com.smartsoft.framework.presentation.context.HttpRequestType;
import tn.com.smartsoft.framework.presentation.view.action.ActionControleur;
import tn.com.smartsoft.framework.presentation.view.comman.ClientEvent;
import tn.com.smartsoft.framework.presentation.view.window.UIContainerComponent;
import tn.com.smartsoft.framework.presentation.view.window.UIRendrableComponent;
import tn.com.smartsoft.framework.presentation.view.window.component.js.UIScriptJs;

public abstract class UIEvent extends UIContainerComponent implements UIRendrableComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	protected ActionControleur listener;
	private boolean rendred = true;
	private JsMap requestParams = new JsMap();
	private Boolean clientValidation;
	private String waitMessage;
	private Boolean isWait;
	private String actionPattern;
	private String componentId;
	private String msgConfirm;
	private String msgConfirmTitle;
	private String msgContainer;
	private Boolean isConfirmMsg;
	private String eventName;
	private ArrayList<String> handleParams = new ArrayList<String>();
	private Boolean isStartUp = Boolean.FALSE;
	private UIScriptJs condition;
	private String conditionHandler;

	public boolean isRendred() {
		return rendred;
	}

	public void setRendred(boolean rendred) {
		this.rendred = rendred;
	}

	public UIEvent() {
		super();
	}

	public UIEvent(String name, ActionControleur listener) {
		super();
		this.name = name;
		this.listener = listener;
	}

	public UIEvent(String name, String actionMethod) {
		super();
		this.name = name;
		this.setActionListener(actionMethod);
	}

	public UIEvent(String name, String actionMethod, Object contolerBean, Object delegateMethod) {
		this.name = name;
		this.listener = new UIEventListener(this, actionMethod, contolerBean, delegateMethod);
	}

	public UIEvent(String name, String actionMethod, Object contolerBean) {
		this.name = name;
		this.listener = new UIEventListener(this, actionMethod, contolerBean);
	}

	public UIEvent(ClientEvent name, ActionControleur listener) {
		this(name.toString(), listener);
	}

	public UIEvent(ClientEvent name, String actionMethod) {
		this(name.toString(), actionMethod);
	}

	public UIEvent(ClientEvent name, String actionMethod, Object contolerBean, Object delegateMethod) {
		this(name.toString(), actionMethod, contolerBean, delegateMethod);
	}

	public UIEvent(ClientEvent name, String actionMethod, Object contolerBean) {
		this(name.toString(), actionMethod, contolerBean);
	}

	public String getComponentId() {
		if (StringUtils.isEmpty(componentId))
			return this.getParent().getId();
		return componentId;
	}

	public String getEventName() {
		if (StringUtils.isEmpty(eventName))
			return this.getName();
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public boolean isEmptyEventName() {
		return StringUtils.isEmpty(eventName);
	}

	public boolean isEmptyComponentId() {
		return StringUtils.isEmpty(componentId);
	}

	public void setComponentId(String componentId) {
		this.componentId = componentId;
	}

	public ActionControleur getListener() {
		return listener;
	}

	public void setListener(ActionControleur listener) {
		this.listener = listener;
	}

	public void setActionListener(String actionMethod) {
		this.listener = new UIEventListener(this, actionMethod);
	}

	public String getActionPattern() {
		return actionPattern;
	}

	public void setActionPattern(String actionPattern) {
		this.actionPattern = actionPattern;
	}

	public String getMsgContainer() {
		return msgContainer;
	}

	public void setMsgContainer(String msgContainer) {
		this.msgContainer = msgContainer;
	}

	public String getId() {
		return name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean isWait() {
		return isWait;
	}

	public void setWait(Boolean isWait) {
		this.isWait = isWait;
	}

	public JsMap getRequestParams() {
		return requestParams;
	}

	public void setRequestParams(JsMap requestParams) {
		this.requestParams = requestParams;
	}

	public Boolean isClientValidation() {
		return clientValidation;
	}

	public void setClientValidation(Boolean clientValidation) {
		this.clientValidation = clientValidation;
	}

	public String getWaitMessage() {
		return waitMessage;
	}

	public void setWaitMessage(String waitMessage) {
		this.waitMessage = waitMessage;
	}

	public void addRequestParam(String name, String value, Boolean expected) {
		this.requestParams.addValue(name, value, expected.booleanValue());
	}

	public String getMsgConfirm() {
		if (StringUtils.isBlank(msgConfirm))
			return msgConfirm;
		else {
			Object libelle = getWindow().evalExpression(msgConfirm);
			if (libelle != null)
				return libelle.toString();
			else
				return msgConfirm;
		}
	}

	public void setMsgConfirm(String msgConfirm) {
		this.msgConfirm = msgConfirm;
	}

	public String getMsgConfirmTitle() {
		if (StringUtils.isBlank(msgConfirmTitle))
			return msgConfirmTitle;
		else {
			Object libelle = getWindow().evalExpression(msgConfirmTitle);
			if (libelle != null)
				return libelle.toString();
			else
				return msgConfirmTitle;
		}
	}

	public void setMsgConfirmTitle(String msgConfirmTitle) {
		this.msgConfirmTitle = msgConfirmTitle;
	}

	public Boolean isConfirmMsg() {
		return isConfirmMsg;
	}

	public void setConfirmMsg(Boolean isConfirmMsg) {
		this.isConfirmMsg = isConfirmMsg;
	}

	public void addHandleParam(String name) {
		this.handleParams.add(name);
	}

	public ArrayList<String> getHandleParams() {
		return handleParams;
	}

	public void clearHandleParamValue() {
		this.handleParams.clear();
	}

	public void setHandleParamValue(String params) {
		if (StringUtils.isNotBlank(params)) {
			String[] ps = params.split(",");
			for (int i = 0; i < ps.length; i++) {
				addHandleParam(ps[i]);
			}
		}
	}

	public UIScriptJs getCondition() {
		return condition;
	}

	public void setCondition(UIScriptJs condition) {
		this.condition = condition;
	}

	public String getConditionHandler() {
		return conditionHandler;
	}

	public void setConditionHandler(String conditionHandler) {
		this.conditionHandler = conditionHandler;
	}

	public Boolean getStartUp() {
		return isStartUp;
	}

	public void setStartUp(Boolean isStartUp) {
		this.isStartUp = isStartUp;
	}

	public abstract HttpRequestType httpRequestType();
}
