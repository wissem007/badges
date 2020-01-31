package tn.com.digivoip.comman.config;

import tn.com.digivoip.framework.xml.XmlElement;

public class GuiItem extends DefaultItem{

	public final static String	THEME				= "theme";			//$NON-NLS-1$
	public final static String	NAME				= "name";			//$NON-NLS-1$
	public final static String	FONT				= "fonts";			//$NON-NLS-1$
	public final static String	FONT_MAIN			= "fonts/main";	//$NON-NLS-1$
	public final static String	FONT_TEXT			= "fonts/text";	//$NON-NLS-1$
	public final static String	OVERWRITE_BOOL		= "overwrite";		//$NON-NLS-1$
	public final static String	SIZE_INT			= "size";			//$NON-NLS-1$
	public final static String	TOOLBAR				= "toolbar";		//$NON-NLS-1$
	public final static String	TEXT_POSITION_BOOL	= "text_position";	//$NON-NLS-1$
	public final static String	ENABLE_ICON_BOOL	= "enable_icon";	//$NON-NLS-1$
	public final static String	ENABLE_TEXT_BOOL	= "enable_text";	//$NON-NLS-1$

	public GuiItem(final XmlElement theRoot) {
		super(theRoot);
	}
}
