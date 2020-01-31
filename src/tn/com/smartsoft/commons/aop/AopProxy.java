package tn.com.smartsoft.commons.aop;

public interface AopProxy {

	Object getProxy();

	Object getProxy(ClassLoader classLoader);
}
