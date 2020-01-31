package tn.com.smartsoft.framework.presentation.view.comman;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ClientEvent implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private static Map<String, ClientEvent> CLIENT_EVENTS = new HashMap<String, ClientEvent>();
	public static final ClientEvent ON_BLUR = new ClientEvent("Blur");
	public static final ClientEvent ON_CHANGE = new ClientEvent("Change");
	public static final ClientEvent ON_CLICK = new ClientEvent("Click");
	public static final ClientEvent ON_DBLCLICK = new ClientEvent("DblClick");
	public static final ClientEvent ON_DEACTIVATE = new ClientEvent("Deactivate");
	public static final ClientEvent ON_DRAG = new ClientEvent("Drag");
	public static final ClientEvent ON_DROP = new ClientEvent("Drop");
	public static final ClientEvent ON_FOCUS = new ClientEvent("Focus");
	public static final ClientEvent ON_FOCUSIN = new ClientEvent("FocusIn");
	public static final ClientEvent ON_FOCUSOUT = new ClientEvent("FocusOut");
	public static final ClientEvent ON_HELP = new ClientEvent("Help");
	public static final ClientEvent ON_KEYDOWN = new ClientEvent("KeyDown");
	public static final ClientEvent ON_KEYPRESS = new ClientEvent("KeyPress");
	public static final ClientEvent ON_KEYUP = new ClientEvent("KeyUp");
	public static final ClientEvent ON_LOAD = new ClientEvent("Load");
	public static final ClientEvent ON_RELOAD_LOAD = new ClientEvent("reload");
	public static final ClientEvent ON_MOUSEDOWN = new ClientEvent("MouseDown");
	public static final ClientEvent ON_MOUSEENTER = new ClientEvent("MouseEnter");
	public static final ClientEvent ON_MOUSELEAVE = new ClientEvent("MouseLeave");
	public static final ClientEvent ON_MOUSEMOVE = new ClientEvent("MouseMove");
	public static final ClientEvent ON_MOUSEOUT = new ClientEvent("MouseOut");
	public static final ClientEvent ON_MOUSEOVER = new ClientEvent("MouseOver");
	public static final ClientEvent ON_MOUSEUP = new ClientEvent("MouseUp");
	public static final ClientEvent ON_MOUSEWHEEL = new ClientEvent("MouseWheel");
	public static final ClientEvent ON_RESET = new ClientEvent("Reset");
	public static final ClientEvent ON_RESIZE = new ClientEvent("Resize");
	public static final ClientEvent ON_SCROLL = new ClientEvent("Scroll");
	public static final ClientEvent ON_SELECT = new ClientEvent("Select");
	public static final ClientEvent ON_SUBMIT = new ClientEvent("Submit");
	public static final ClientEvent ON_UNLOAD = new ClientEvent("Unload");
	public static final ClientEvent ON_INIT = new ClientEvent("Init");
	public static final ClientEvent ON_FETCH = new ClientEvent("Fetch");
	public static final ClientEvent ON_CELL_DBL_CLICK = new ClientEvent("celldblclick");

	static {
		addClientEvent(ON_BLUR);
		addClientEvent(ON_CHANGE);
		addClientEvent(ON_CLICK);
		addClientEvent(ON_DBLCLICK);
		addClientEvent(ON_DEACTIVATE);
		addClientEvent(ON_DRAG);
		addClientEvent(ON_DROP);
		addClientEvent(ON_FOCUS);
		addClientEvent(ON_FOCUSIN);
		addClientEvent(ON_FOCUSOUT);
		addClientEvent(ON_HELP);
		addClientEvent(ON_KEYDOWN);
		addClientEvent(ON_KEYPRESS);
		addClientEvent(ON_KEYUP);
		addClientEvent(ON_LOAD);
		addClientEvent(ON_MOUSEDOWN);
		addClientEvent(ON_MOUSEENTER);
		addClientEvent(ON_MOUSELEAVE);
		addClientEvent(ON_MOUSEMOVE);
		addClientEvent(ON_MOUSEOUT);
		addClientEvent(ON_MOUSEOVER);
		addClientEvent(ON_MOUSEUP);
		addClientEvent(ON_MOUSEWHEEL);
		addClientEvent(ON_RESET);
		addClientEvent(ON_RESIZE);
		addClientEvent(ON_SCROLL);
		addClientEvent(ON_SELECT);
		addClientEvent(ON_SUBMIT);
		addClientEvent(ON_UNLOAD);
		addClientEvent(ON_INIT);
		addClientEvent(ON_FETCH);
	}

	private static void addClientEvent(ClientEvent clientEvent) {
		CLIENT_EVENTS.put(clientEvent.getName().toLowerCase(), clientEvent);
	}

	public static ClientEvent getClientEvent(String name) {
		if (!CLIENT_EVENTS.containsKey(name))
			return new ClientEvent(name);
		return CLIENT_EVENTS.get(name.toLowerCase());
	}

	private ClientEvent(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj instanceof String)
			return name.equals(obj);
		if (obj instanceof ClientEvent)
			return name.equals(((ClientEvent) obj).name);
		else
			return false;
	}

	public String toString() {
		return name;
	}

}
