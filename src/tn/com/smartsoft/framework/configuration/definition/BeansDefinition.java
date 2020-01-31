package tn.com.smartsoft.framework.configuration.definition;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import tn.com.smartsoft.commons.xml.utils.ParserDefinition;
import tn.com.smartsoft.commons.xml.utils.ParserDefinitionUtils;
import tn.com.smartsoft.framework.beans.definition.BeanDefinition;
import tn.com.smartsoft.framework.business.definition.BusinessBeanDefinition;
import tn.com.smartsoft.framework.dao.definition.DaoBeanDefinition;
import tn.com.smartsoft.framework.dao.definition.DaoParseBeanDefinition;
import tn.com.smartsoft.framework.presentation.definition.BindingModelDefinition;
import tn.com.smartsoft.framework.presentation.definition.ControleBeanDefinition;
import tn.com.smartsoft.framework.presentation.definition.UserActionDefinition;
import tn.com.smartsoft.framework.presentation.definition.WindowDefinition;

public class BeansDefinition implements IDefinition, ParserDefinition {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Map<Serializable, BeanDefinition> beansDefinition = new HashMap<Serializable, BeanDefinition>();
	protected Map<String, DaoBeanDefinition> daoBeansDefinition = new HashMap<String, DaoBeanDefinition>();
	protected Map<String, BusinessBeanDefinition> businessBeansDefinition = new HashMap<String, BusinessBeanDefinition>();
	protected Map<String, ControleBeanDefinition> actionContoleurBeansDefinition = new HashMap<String, ControleBeanDefinition>();
	protected Map<String, UserActionDefinition> actionApplicationBeansDefinition = new HashMap<String, UserActionDefinition>();
	private Map<String, String> variableSystem = new HashMap<String, String>();
	private CompositeBeansDefinition compositeBeansDefinition;
	protected Map<String, DaoParseBeanDefinition> daoParseBeansDefinition = new HashMap<String, DaoParseBeanDefinition>();
	protected Map<String, BindingModelDefinition> bindingModelDefinitions = new HashMap<String, BindingModelDefinition>();
	protected Map<String, WindowDefinition> windowBeansDefinition = new HashMap<String, WindowDefinition>();

	public void addVariableSystem(String name, String value) {
		variableSystem.put(name, value);
	}

	public String parse(String value) {
		return ParserDefinitionUtils.parse(value, this);
	}

	public String getVariableSystem(String name) {
		return (String) variableSystem.get(name);
	}

	public void addDaoParseBeanDefinition(DaoParseBeanDefinition value) {
		if (compositeBeansDefinition == null)
			daoParseBeansDefinition.put(value.getId(), value);
		else
			compositeBeansDefinition.getCompositeBeansDefinition().addDaoParseBeanDefinition(value);
	}

	public void addBeanDefinition(BeanDefinition value) {
		if (compositeBeansDefinition == null)
			beansDefinition.put(value.getId(), value);
		else
			compositeBeansDefinition.getCompositeBeansDefinition().addBeanDefinition(value);
	}

	public void addBusinessBeanDefinition(BusinessBeanDefinition value) {
		if (compositeBeansDefinition == null)
			businessBeansDefinition.put(value.getName(), value);
		else
			compositeBeansDefinition.getCompositeBeansDefinition().addBusinessBeanDefinition(value);
	}

	public void addControleBeanDefinition(ControleBeanDefinition value) {
		if (compositeBeansDefinition == null)
			actionContoleurBeansDefinition.put(value.getName(), value);
		else
			compositeBeansDefinition.getCompositeBeansDefinition().addControleBeanDefinition(value);
	}

	public void addDaoBeanDefinition(DaoBeanDefinition value) {
		if (compositeBeansDefinition == null)
			daoBeansDefinition.put(value.getName(), value);
		else
			compositeBeansDefinition.getCompositeBeansDefinition().addDaoBeanDefinition(value);
	}

	public void addUserActionDefinition(UserActionDefinition value) {
		if (compositeBeansDefinition == null)
			actionApplicationBeansDefinition.put(value.getName(), value);
		else
			compositeBeansDefinition.getCompositeBeansDefinition().addUserActionDefinition(value);
	}

	public UserActionDefinition getUserActionDefinition(String name) {
		return (UserActionDefinition) actionApplicationBeansDefinition.get(name);
	}

	public void addWindowBeanDefinition(WindowDefinition value) {
		if (compositeBeansDefinition == null)
			windowBeansDefinition.put(value.getId(), value);
		else
			compositeBeansDefinition.getCompositeBeansDefinition().addWindowBeanDefinition(value);
	}

	public WindowDefinition getWindowBeanDefinition(String id) {
		return (WindowDefinition) windowBeansDefinition.get(id);
	}

	public void addBindingModelDefinition(BindingModelDefinition value) {
		if (compositeBeansDefinition == null)
			bindingModelDefinitions.put(value.getName(), value);
		else
			compositeBeansDefinition.getCompositeBeansDefinition().addBindingModelDefinition(value);
	}

	public BindingModelDefinition getBindingModelDefinition(String name) {
		return (BindingModelDefinition) bindingModelDefinitions.get(name);
	}

	public BusinessBeanDefinition getBusinessBeanDefinition(String name) {
		return (BusinessBeanDefinition) businessBeansDefinition.get(name);
	}

	public DaoParseBeanDefinition getDaoParseBeanDefinition(String name) {
		return (DaoParseBeanDefinition) daoParseBeansDefinition.get(name);
	}

	public DaoBeanDefinition getDaoBeanDefinition(String name) {
		return (DaoBeanDefinition) daoBeansDefinition.get(name);
	}

	public BeanDefinition getBeanDefinition(String name) {
		return (BeanDefinition) beansDefinition.get(name);
	}

	public ControleBeanDefinition getControleBeanDefinition(String name) {
		ControleBeanDefinition controleBeanDefinition = (ControleBeanDefinition) actionContoleurBeansDefinition.get(name);
		return controleBeanDefinition;
	}

	public CompositeBeansDefinition getCompositeBeansDefinition() {
		return compositeBeansDefinition;
	}

	public void setCompositeBeansDefinition(CompositeBeansDefinition compositeBeansDefinition) {
		this.compositeBeansDefinition = compositeBeansDefinition;
	}
}
