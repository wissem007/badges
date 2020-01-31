package tn.com.smartsoft.iap.system.test;

import java.util.Date;

import tn.com.smartsoft.framework.beans.Money;
import tn.com.smartsoft.framework.configuration.ApplicationManager;
import tn.com.smartsoft.framework.test.AbstractTestBean;
import tn.com.smartsoft.framework.test.TestSuiteBean;

public class TestLoader extends AbstractTestBean {

	public TestLoader(ApplicationManager applicationManager) {
		super(applicationManager);
	}

	public void test() {
		Money money = new Money("1222122.20565");
		Money money1 = new Money("10");
		System.out.println(applicationManager.formaterManger().getAsString(money));
		System.out.println(applicationManager.formaterManger().getAsString(money1));
		System.out.println(applicationManager.formaterManger().getAsString(money1.add(money)));
		System.out.println(applicationManager.formaterManger().getAsString(new Date()));
		System.out.println(applicationManager.formaterManger().getAsObject("montant", "120,12", Money.class));
	}

	public static void main(String[] args) {
		TestSuiteBean.runTest(TestLoader.class, new String[] { "test" });
	}
}
