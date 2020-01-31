package tn.com.smartsoft.framework.presentation.view.window.component.ext.grid;

import java.util.List;

import tn.com.smartsoft.framework.presentation.view.window.UIComponent;
import tn.com.smartsoft.framework.presentation.view.window.handler.UIDefaultComponentHandler;

public class UIExtHeaderRowGrid extends UIComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UIDefaultComponentHandler<UIExtGridHeaderColumn> columnHandler = new UIDefaultComponentHandler<UIExtGridHeaderColumn>(this);

	public void addColumn(UIExtGridHeaderColumn column) {
		columnHandler.addItem(column);
	}

	public List<String> getColumnIds() {
		return columnHandler.itemIds();
	}

	public UIExtGridHeaderColumn getColumn(String id) {
		return (UIExtGridHeaderColumn) columnHandler.getItem(id);
	}

	public void removeColumn(String id) {
		columnHandler.removeItem(id);
	}

	public void removeColumn(String[] id) {
		columnHandler.removeItem(id);
	}
}
