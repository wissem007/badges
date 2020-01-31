package tn.com.smartsoft.framework.test;

import org.apache.commons.beanutils.ConstructorUtils;
import org.apache.commons.beanutils.MethodUtils;

import tn.com.smartsoft.framework.configuration.ApplicationConfiguration;
import tn.com.smartsoft.framework.configuration.ApplicationManager;

public class TestSuiteBean {
	private static boolean isStart = false;
	private static ApplicationManager applicationManager;

	private static void runStart() {
		ApplicationConfiguration.startUp();
		isStart = ApplicationConfiguration.isStartUp();
		applicationManager = ApplicationConfiguration.applicationManager();
		if (!ApplicationConfiguration.isStartUp()) {
			try {
				throw ApplicationConfiguration.getExceptionStartUp();
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
		}
	}

	public static void runTest(Class<?> testClass, String testMethode) {
		runTest(testClass, new String[] { testMethode });
	}

	public static void runTest(Class<?> testClass, String testMethode0, String testMethode1) {
		runTest(testClass, new String[] { testMethode0, testMethode1 });
	}

	public static void runTest(Class<?> testClass, String testMethode0, String testMethode1, String testMethode2) {
		runTest(testClass, new String[] { testMethode0, testMethode1, testMethode2 });
	}

	public static void runTest(Class<?> testClass, String[] testMethodes) {
		if (!isStart)
			runStart();
		try {
			AbstractTestBean inst = (AbstractTestBean) ConstructorUtils.invokeConstructor(testClass, applicationManager);
			for (int i = 0; i < testMethodes.length; i++) {
				try {
					MethodUtils.invokeExactMethod(inst, testMethodes[i], null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}
