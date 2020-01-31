package tn.com.smartsoft.commons.web.extjs;

import java.util.HashMap;
import java.util.Map;

import tn.com.smartsoft.framework.presentation.view.window.utils.UIExtComponentJs;

public class ComponentMgr {
	private static Map<String, String> registerType = new HashMap<String, String>();
	private static Map<String, String> registerPlugin = new HashMap<String, String>();
	static {
		
	}
	
	public static boolean isRegistered(String xtype) {
		return registerType.containsKey(xtype);
	}
	
	public static boolean isPluginRegistered(String ptype) {
		return registerPlugin.containsKey(ptype);
	}
	
	public static void registerType(String xtype, String cls) {
		registerType.put(xtype, cls);
	}
	
	public static UIExtComponentJs create(String xtype, String instanceName) {
		return new UIExtComponentJs(instanceName, registerType.get(xtype));
	}
	
	public static void registerPlugin(String xtype, String cls) {
		registerPlugin.put(xtype, cls);
	}
	
	public static UIExtComponentJs createPlugin(String xtype, String instanceName) {
		return new UIExtComponentJs(instanceName, registerPlugin.get(xtype));
	}
	
}
