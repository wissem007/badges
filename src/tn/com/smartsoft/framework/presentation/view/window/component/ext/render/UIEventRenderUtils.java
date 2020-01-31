package tn.com.smartsoft.framework.presentation.view.window.component.ext.render;

import tn.com.smartsoft.commons.web.js.JsMap;
import tn.com.smartsoft.framework.presentation.view.comman.ClientEvent;
import tn.com.smartsoft.framework.presentation.view.window.UIRender;
import tn.com.smartsoft.framework.presentation.view.window.UIRenderContext;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.event.UIEvent;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.event.UIEventHandler;
import tn.com.smartsoft.framework.presentation.view.window.component.ext.event.render.UIInterfaceEventRender;
import tn.com.smartsoft.framework.presentation.view.window.utils.UIExtComponentJs;

public class UIEventRenderUtils {
	public static void renderEvents(UIRenderContext context, UIEventHandler extComponent, UIRender parentRender, UIExtComponentJs extComponentJs) {
		String[] actionEventNames = extComponent.getEventNames();
		for (int i = 0; i < actionEventNames.length; i++) {
			final UIEvent uiEvent = extComponent.getEvent(actionEventNames[i]);
			if (uiEvent.isRendred() && !uiEvent.getEventName().equalsIgnoreCase(ClientEvent.ON_INIT.toString())) {
				UIRender eventRender = uiEvent.getRender();
				eventRender.setParentRender(parentRender);
				eventRender.render(context);
				if (uiEvent.getStartUp())
					context.addOnStartUpAction(uiEvent.getComponentId(), uiEvent.getEventName());
				if (eventRender instanceof UIInterfaceEventRender) {
					if (uiEvent.getName().equalsIgnoreCase("handler")) {
						extComponentJs.addObjectValue("handler", ((UIInterfaceEventRender) eventRender).createHandelServerAction(context));
					} else
						extComponentJs.addEventHandler(actionEventNames[i], ((UIInterfaceEventRender) eventRender).createHandelServerAction(context));
				}
			}
		}
	}

	public static void renderClickEventToHandelConfig(UIRenderContext context, UIEventHandler extComponent, UIRender parentRender, JsMap jsMap) {
		UIEvent uiEvent = extComponent.getEvent("click");
		if (uiEvent != null) {
			UIRender eventRender = uiEvent.getRender();
			eventRender.setParentRender(parentRender);
			eventRender.render(context);
			if (eventRender instanceof UIInterfaceEventRender) {
				jsMap.addObjectValue("handler", ((UIInterfaceEventRender) eventRender).createHandelServerAction(context));
			}
		}
	}

	public static void renderHrefEventToHandelConfig(UIRenderContext context, UIRender parentRender, UIEvent uiEvent, JsMap jsMap) {
		if (uiEvent != null) {
			UIRender eventRender = uiEvent.getRender();
			eventRender.setParentRender(parentRender);
			eventRender.render(context);
			if (eventRender instanceof UIInterfaceEventRender) {
				jsMap.addObjectValue("handler", ((UIInterfaceEventRender) eventRender).createHandelServerAction(context));
			}
		}
	}

	public static void renderHrefEventToHandelConfig(UIRenderContext context, UIRender parentRender, UIEvent uiEvent, UIExtComponentJs extComponentJs) {
		if (uiEvent != null) {
			UIRender eventRender = uiEvent.getRender();
			eventRender.setParentRender(parentRender);
			eventRender.render(context);
			if (eventRender instanceof UIInterfaceEventRender) {
				extComponentJs.addObjectValue("handler", ((UIInterfaceEventRender) eventRender).createHandelServerAction(context));
			}
		}
	}

	public static void renderWindowEvents(UIRenderContext context, UIEventHandler extComponent, UIRender parentRender) {
		String[] actionEventNames = extComponent.getEventNames();
		for (int i = 0; i < actionEventNames.length; i++) {
			final UIEvent uiEvent = extComponent.getEvent(actionEventNames[i]);
			if (uiEvent.isRendred()) {
				UIRender eventRender = uiEvent.getRender();
				eventRender.setParentRender(parentRender);
				eventRender.render(context);
				if (eventRender instanceof UIInterfaceEventRender) {
					context.renderAddListenerContainer(actionEventNames[i], ((UIInterfaceEventRender) eventRender).createHandelServerAction(context));
				}
			}
		}
	}

}
