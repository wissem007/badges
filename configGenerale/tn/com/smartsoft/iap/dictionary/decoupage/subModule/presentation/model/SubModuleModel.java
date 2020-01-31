package tn.com.smartsoft.iap.dictionary.decoupage.subModule.presentation.model;
import java.util.List;
import tn.com.smartsoft.framework.presentation.model.GenericEntiteModel;

public class SubModuleModel extends GenericEntiteModel {
	private static final long serialVersionUID = 1L;
	
	private List listModule;

	public List getListModule() {
		return listModule;
	}

	public void setListModule(List listModule) {
		this.listModule = listModule;
	}
		
}
