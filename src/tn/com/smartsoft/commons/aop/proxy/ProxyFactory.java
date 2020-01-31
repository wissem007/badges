package tn.com.smartsoft.commons.aop.proxy;

import org.apache.commons.proxy.invoker.InvocationHandlerAdapter;

import tn.com.smartsoft.commons.aop.AopProxy;

public class ProxyFactory extends ProxyFactoryAccessor implements AopProxy {
	private org.apache.commons.proxy.ProxyFactory proxyFactory;

	public ProxyFactory() {
		proxyFactory = new org.apache.commons.proxy.factory.javassist.JavassistProxyFactory();

	}

	public Object getProxy() {
		if (this.targetObjectProvider != null) {
			return proxyFactory.createInterceptorProxy(getObjectProvider().getObject(), new AspectsIterceptor(getAspects(), getInvocationAspects()), getInterfaces());
		} else {
			AspectsInvocationHandler aspectsInvocationHandler = new AspectsInvocationHandler(getAspects(), getInvocationAspects());
			InvocationHandlerAdapter invocationHandlerAdapter = new InvocationHandlerAdapter(aspectsInvocationHandler);
			return proxyFactory.createInvokerProxy(invocationHandlerAdapter, getInterfaces());
		}
	}

	public Object getProxy(ClassLoader classLoader) {
		if (this.targetObjectProvider != null)
			return proxyFactory.createInterceptorProxy(classLoader, getObjectProvider().getObject(), new AspectsIterceptor(getAspects(), getInvocationAspects()),
					getInterfaces());
		else
			return proxyFactory.createInvokerProxy(classLoader, new InvocationHandlerAdapter(new AspectsInvocationHandler(getAspects(), getInvocationAspects())),
					getInterfaces());
	}

}
