package tn.com.smartsoft.framework.presentation.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import tn.com.smartsoft.framework.beans.DataBusinessBean;

public class GenericEntiteModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DataBusinessBean searcheBean;
	private DataBusinessBean detailBean;
	private List<DataBusinessBean> listBean;
	private int beanIndex;
	private Map<String, Object> dynamiqueValue = new HashMap<String, Object>();

	public int getBeanIndex() {
		return beanIndex;
	}

	public void setBeanIndex(int beanIndex) {
		if (beanIndex > -1 && beanIndex < listBean.size())
			this.beanIndex = beanIndex;
	}

	public DataBusinessBean getSearcheBean() {
		return searcheBean;
	}

	public void setSearcheBean(DataBusinessBean searcheBean) {
		this.searcheBean = searcheBean;
	}

	public DataBusinessBean getDetailBean() {
		return detailBean;
	}

	public void setDetailBean(DataBusinessBean detailBean) {
		this.detailBean = detailBean;
	}

	public List<DataBusinessBean> getListBean() {
		return listBean;
	}

	public void setListBean(List<DataBusinessBean> listBean) {
		this.listBean = listBean;
	}

	public void setDynamiqueValue(Map<String, Object> dynamiqueValue) {
		this.dynamiqueValue = dynamiqueValue;
	}

	public void setDynamicPropertyValue(String property, Object value) {
		if (value == null)
			getDynamiqueValue().remove(property);
		else
			getDynamiqueValue().put(property, value);
	}

	public Object getDynamicPropertyValue(String property) {
		return getDynamiqueValue().get(property);
	}

	public String[] getDynamicPropertyNames() {
		Set<String> keySet = getDynamiqueValue().keySet();
		String[] ns = new String[getDynamiqueValue().size()];
		return keySet.toArray(ns);
	}

	public Map<String, Object> getDynamiqueValue() {
		if (dynamiqueValue == null)
			dynamiqueValue = new HashMap<String, Object>();
		return dynamiqueValue;
	}
}
