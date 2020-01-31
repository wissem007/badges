package tn.com.smartsoft.framework.presentation.view.report;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import tn.com.smartsoft.framework.presentation.view.action.UserAction;

public class RDataSource implements JRDataSource, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UserAction userAction;
	private String modelName;
	private int index = -1;

	public RDataSource(UserAction userAction, String modelName) {
		super();
		this.modelName = modelName;
		this.userAction = userAction;
	}

	public Object getFieldValue(JRField field) throws JRException {
		return new RValueField(userAction.getModel(), modelName, field, index);
	}

	private UserAction getUserAction() {
		return userAction;
	}

	public List<?> getValue() {
		List<?> value = (List<?>) getUserAction().getModel().getValue(modelName);
		if (value == null)
			value = new ArrayList<Object>();
		return value;
	}

	public boolean next() throws JRException {
		if (getValue() == null || getValue().isEmpty())
			return false;
		index++;
		return (index < getValue().size());
	}

}
