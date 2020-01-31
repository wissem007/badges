package tn.com.smartsoft.framework.presentation.view.response.xl;

import java.io.Serializable;

public interface XlColumnRender extends Serializable {
	public static int TYPE_DATE = 1;
	public static int TYPE_LABEL = 0;
	public static int TYPE_NUMBER = 2;
 
	public XlCelluleValue render(XlTableContent xlTable, String columnId, int row);

}
