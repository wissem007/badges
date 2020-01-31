package tn.com.smartsoft.framework.presentation.view.action.model.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tn.com.smartsoft.commons.utils.OgnlUtil;
import tn.com.smartsoft.commons.utils.SorterType;
import tn.com.smartsoft.framework.presentation.view.action.model.BeanAccessorModel;
import tn.com.smartsoft.framework.presentation.view.action.model.BeanModel;
import tn.com.smartsoft.framework.presentation.view.action.model.PropertyModel;
import tn.com.smartsoft.framework.presentation.view.action.model.RootBeanModel;
import tn.com.smartsoft.configGenerale.devises.devise.beans.DeviseBean;
import tn.com.smartsoft.configGenerale.organisationSociete.pays.beans.PayBean;

public class Test {
	public static final String CON = "sdsdsds";

	public static void mainc(String[] args) {
		System.setProperty("catalina.base", "C:");

		BeanTest beanTest = new BeanTest();
		RootBeanModel rootModel = new RootBeanModel(BeanTest.class, null);
		BeanModel deviseModel = new BeanModel("devise", DeviseBean.class, BeanAccessorModel.LIST_TYPE, rootModel, null);
		deviseModel.addChildModel(new PropertyModel("deviseId"));
		deviseModel.addChildModel(new PropertyModel("symboleMonetaire"));
		deviseModel.addChildModel(new PropertyModel("libelle"));
		deviseModel.addChildModel(new PropertyModel("arrondissementMode"));
		deviseModel.addChildModel(new PropertyModel("arrondissementMontant"));
		rootModel.addChildModel(deviseModel);
		/** ****************************** */
		BeanModel paysModel = new BeanModel("pays", PayBean.class, BeanAccessorModel.BEAN_TYPE, rootModel, null);
		paysModel.addChildModel(new PropertyModel("paysId"));
		paysModel.addChildModel(new PropertyModel("libelle"));
		paysModel.addChildModel(new PropertyModel("libelleArabe"));
		paysModel.addChildModel(new PropertyModel("nationalite"));
		/** ****************************** */
		deviseModel.addChildModel(paysModel);

		deviseModel = new BeanModel("deviseBean", DeviseBean.class, BeanAccessorModel.BEAN_TYPE, rootModel, null);
		deviseModel.addChildModel(new PropertyModel("deviseId"));
		deviseModel.addChildModel(new PropertyModel("symboleMonetaire"));
		deviseModel.addChildModel(new PropertyModel("libelle"));
		deviseModel.addChildModel(new PropertyModel("arrondissementMode"));
		deviseModel.addChildModel(new PropertyModel("arrondissementMontant"));
		paysModel = new BeanModel("pays", PayBean.class, BeanAccessorModel.BEAN_TYPE, rootModel, null);
		paysModel.addChildModel(new PropertyModel("paysId"));
		paysModel.addChildModel(new PropertyModel("libelle"));
		paysModel.addChildModel(new PropertyModel("libelleArabe"));
		paysModel.addChildModel(new PropertyModel("nationalite"));
		/** ****************************** */
		deviseModel.addChildModel(paysModel);
		rootModel.addChildModel(deviseModel);

		/** ****************************** */
		for (int i = 0; i < 10; i++) {
			Long index = new Long((long) (Math.random() * 100));
			rootModel.setValue("devise[" + i + "].deviseId", index);
			rootModel.setValue("devise[" + i + "].libelle", new String("Libelle " + index.intValue()));
			rootModel.setValue("devise[" + i + "].pays.paysId", index);
		}
		System.out.println("************************************************");
		/** ****************************** */
		Long index = new Long((long) (Math.random() * 100));
		rootModel.setValue("deviseBean.deviseId", index);
		rootModel.setValue("deviseBean.libelle", new String("Libelle " + index.intValue()));
		rootModel.setValue("deviseBean.pays.paysId", index);
		System.out.println("************************************************");

		for (int i = 0; i < beanTest.getDevise().size(); i++) {
			System.out.print(beanTest.getDevise().get(i).getDeviseId());
			System.out.print(" ," + beanTest.getDevise().get(i).getLibelle());
			//System.out.println(" ," + beanTest.getDevise().get(i).getPays().getPaysId());
			System.out.println("-----------------------------------------------");
		}
		System.out.println("************************************************");
		System.out.print(beanTest.getDeviseBean().getDeviseId());
		System.out.print(" ," + beanTest.getDeviseBean().getLibelle());
		//System.out.println(" ," + beanTest.getDeviseBean().getPays().getPaysId());
		System.out.println("************************************************");
		Object exp = OgnlUtil.parseExpression("getValue(\"deviseBean.deviseId\")");
		Object o = OgnlUtil.getValue(exp, rootModel);
		System.out.println(o);
		System.out.println(rootModel.getExpressionValue("this.getValue(\"deviseBean.deviseId\")"));
		System.out.println(rootModel.getExpressionValue("this.getValue(\"devise\").size()"));
		Map<Object, Object> context = new HashMap<Object, Object>();
		context.put("i", 9);
		System.out.println(rootModel.getExpressionValue("this.getValue(\"devise\").get(i)", context));
		context = new HashMap<Object, Object>();
		context.put("pr", "devise.libelle");
		System.out.println(rootModel.getExpressionValue("this.findFieldModel(pr).libelle", context));
		StringBuffer sb = new StringBuffer();
		sb.append("#var = \"tst\",\n");
		sb.append("#@java.util.HashMap@{ \"foo\" : #var, \"bar\" : \"bar value\" }");
		exp = OgnlUtil.parseExpression(sb.toString());
		o = OgnlUtil.getValue(exp, rootModel);
		System.out.println(o.getClass());
	}

	public static RootBeanModel getModel() {
		RootBeanModel rootModel = new RootBeanModel(BeanTest.class, null);
		BeanModel deviseModel = new BeanModel("devise", DeviseBean.class, BeanAccessorModel.LIST_TYPE, rootModel, null);
		deviseModel.addChildModel(new PropertyModel("deviseId"));
		deviseModel.addChildModel(new PropertyModel("symboleMonetaire"));
		deviseModel.addChildModel(new PropertyModel("libelle"));
		deviseModel.addChildModel(new PropertyModel("arrondissementMode"));
		deviseModel.addChildModel(new PropertyModel("arrondissementMontant"));
		rootModel.addChildModel(deviseModel);
		/** ****************************** */
		BeanModel paysModel = new BeanModel("pays", PayBean.class, BeanAccessorModel.BEAN_TYPE, rootModel, null);
		paysModel.addChildModel(new PropertyModel("paysId"));
		paysModel.addChildModel(new PropertyModel("libelle"));
		paysModel.addChildModel(new PropertyModel("libelleArabe"));
		paysModel.addChildModel(new PropertyModel("nationalite"));
		/** ****************************** */
		deviseModel.addChildModel(paysModel);
		return rootModel;
	}

	public static void loadData(RootBeanModel rootModel) {
		for (int i = 0; i < 10; i++) {
			Long index = new Long((long) (Math.random() * 100));
			rootModel.setValue("devise[" + i + "].deviseId", index);
			rootModel.setValue("devise[" + i + "].libelle", new String("Libelle " + index.intValue()));
			rootModel.setValue("devise[" + i + "].pays.paysId", index);
		}
	}

	public static void displayData(BeanTest beanTest) {
		for (int i = 0; i < beanTest.getDevise().size(); i++) {
			System.out.print(beanTest.getDevise().get(i).getDeviseId());
			System.out.print(" ," + beanTest.getDevise().get(i).getLibelle());
			//System.out.println(" ," + beanTest.getDevise().get(i).getPays().getPaysId());
			System.out.println("-----------------------------------------------");
		}
	}

	public static void displayData(RootBeanModel rootModel) {
		int size = (Integer) rootModel.getExpressionValue("$V(\"devise\").size()");
		for (int i = 0; i < size; i++) {
			System.out.print(rootModel.getExpressionValue("$V(\"devise[" + i + "].deviseId\")"));
			System.out.print(" ," + rootModel.getExpressionValue("$V(\"devise[" + i + "].libelle\")"));
			System.out.println(" ," + rootModel.getExpressionValue("$V(\"devise[" + i + "].pays.paysId\")"));
			System.out.println("-----------------------------------------------");
		}
	}

	public static void displayDatas(RootBeanModel rootModel) {
		int size = (Integer) rootModel.getExpressionValue("$V(\"devise\").size()");
		for (int i = 0; i < size; i++) {
			System.out.print(rootModel.getValue("devise[" + i + "].deviseId"));
			System.out.print(" ," + rootModel.getValue("devise[" + i + "].libelle"));
			System.out.println(" ," + rootModel.getValue("devise[" + i + "].pays.paysId"));
			System.out.println("-----------------------------------------------");
		}
	}

	public static void main(String[] args) {
		System.setProperty("catalina.base", "C:");
		/** ****************************** */
		BeanTest beanTest = new BeanTest();
		RootBeanModel rootModel = getModel();
		System.out.println("**********************method loadData**************************");
		long o = System.currentTimeMillis();
		loadData(rootModel);
		System.out.println("TimeMillis :" + (System.currentTimeMillis() - o));
		System.out.println("**********************method displayData**************************");
		o = System.currentTimeMillis();
		displayData(beanTest);
		System.out.println("TimeMillis :" + (System.currentTimeMillis() - o));
		System.out.println("**********************method sort**************************");
		o = System.currentTimeMillis();
		rootModel.sort("devise.deviseId", SorterType.DESC);
		System.out.println("TimeMillis :" + (System.currentTimeMillis() - o));
		System.out.println("**********************method displayDatas**************************");
		o = System.currentTimeMillis();
		displayDatas(rootModel);
		System.out.println("TimeMillis :" + (System.currentTimeMillis() - o));
		System.out.println("**********************method displayData**************************");
		o = System.currentTimeMillis();
		displayData(rootModel);
		System.out.println("TimeMillis :" + (System.currentTimeMillis() - o));
		System.out.println("**********************method getExpressionValue**************************");
		System.out.println(rootModel.getExpressionValue("$V(\"devise\")"));
		System.out.println(rootModel.getExpressionValue("$V(\"devise[1].deviseId\")"));
	}

	public static class BeanTest {
		List<DeviseBean> devise = new ArrayList<DeviseBean>();
		DeviseBean deviseBean;

		public DeviseBean getDeviseBean() {
			return deviseBean;
		}

		public void setDeviseBean(DeviseBean deviseBean) {
			this.deviseBean = deviseBean;
		}

		public List<DeviseBean> getDevise() {
			return devise;
		}

	}
}
