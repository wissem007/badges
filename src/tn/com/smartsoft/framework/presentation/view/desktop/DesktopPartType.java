package tn.com.smartsoft.framework.presentation.view.desktop;

import java.io.Serializable;

public class DesktopPartType implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String methode;

	public static DesktopPartType ACCES_APPLICATION_PART = new DesktopPartType("acess", "accesApplication");
	public static DesktopPartType TOOLBAR_PART = new DesktopPartType("toolBar", "toolbars");
	public static DesktopPartType MODULE_EXPLORER_PART = new DesktopPartType("module", "moduleExplorer");
	public static DesktopPartType MENUBAR_PART = new DesktopPartType("menu", "menuBar");
	public static DesktopPartType RESSOURCE_PART = new DesktopPartType("res", "ressourceManager");
	public static DesktopPartType ACTION_PART = new DesktopPartType("action", "curentUserAction");
	public static DesktopPartType VIEW = new DesktopPartType("view", "");
	private static final DesktopPartType ALL[] = (new DesktopPartType[] { VIEW, RESSOURCE_PART, TOOLBAR_PART, MODULE_EXPLORER_PART, MENUBAR_PART, ACTION_PART, ACCES_APPLICATION_PART });

	private DesktopPartType(String name, String methode) {
		super();
		this.name = name;
		this.methode = methode;
	}

	public static boolean isView(String pattern) {
		return DesktopPartType.VIEW.equals(pattern);
	}

	public static DesktopPartType parse(String name) {
		for (int i = 0; i < ALL.length; i++)
			if (ALL[i].equals(name))
				return ALL[i];
		return ACCES_APPLICATION_PART;
	}

	public boolean isView() {
		return DesktopPartType.VIEW.equals(this);
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	public String getMethode() {
		return methode;
	}

	public String toString() {
		return name;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj instanceof String)
			return name.equalsIgnoreCase((String) obj);
		if (obj instanceof DesktopPartType)
			return name.equalsIgnoreCase(((DesktopPartType) obj).name);
		else
			return false;
	}

}
