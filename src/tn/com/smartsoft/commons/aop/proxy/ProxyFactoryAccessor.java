package tn.com.smartsoft.commons.aop.proxy;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.proxy.ObjectProvider;
import org.apache.commons.proxy.provider.ConstantProvider;
import org.apache.commons.proxy.provider.NullProvider;

import tn.com.smartsoft.commons.aop.Aspect;
import tn.com.smartsoft.commons.aop.InvocationAspect;

public abstract class ProxyFactoryAccessor {
	protected List aspects = new LinkedList();
	protected Aspect[] aspectArray = new Aspect[0];

	protected List invocationAspects = new LinkedList();
	protected InvocationAspect[] invocationAspectArray = new InvocationAspect[0];

	protected Object targetObjectProvider;

	protected ObjectProvider objectProvider;

	protected Class[] interfaces;

	public void setTarget(Object target) {
		this.targetObjectProvider = target;
		this.objectProvider = new ConstantProvider(target);
	}

	public ObjectProvider getObjectProvider() {
		this.objectProvider = (this.objectProvider != null ? this.objectProvider : new NullProvider());
		return this.objectProvider;
	}

	public Class[] getInterfaces() {
		if ((this.interfaces == null || this.interfaces.length == 0) && this.targetObjectProvider != null) {
			this.interfaces = this.targetObjectProvider.getClass().getInterfaces();
			this.interfaces = (Class[]) ArrayUtils.add(this.interfaces, targetObjectProvider.getClass());
		}
		return this.interfaces;
	}

	public void setInterfaces(Class[] interfaces) {
		this.interfaces = interfaces;
	}

	public void setObjectProvider(ObjectProvider objectProvider) {
		this.objectProvider = objectProvider;
	}

	public void addAspect(Aspect aspect) {
		int pos = (this.aspects != null) ? this.aspects.size() : 0;
		addAspect(pos, aspect);
		updateAspectArray();
	}

	public void addAspect(int pos, Aspect aspect) {
		this.aspects.add(pos, aspect);
		updateAspectArray();
	}

	public void addAllAspects(Aspect[] aspects) {
		for (int i = 0; i < aspects.length; i++) {
			Aspect aspect = aspects[i];
			this.aspects.add(aspect);
		}
		updateAspectArray();
	}

	public final boolean removeAspect(Aspect aspect) {
		int index = indexOf(aspect);
		if (index == -1) {
			return false;
		} else {
			removeAspect(index);
			return true;
		}
	}

	public void removeAspect(int index) {
		this.aspects.remove(index);
		updateAspectArray();
	}

	public int indexOf(Aspect aspect) {
		for (int i = 0; i < this.aspects.size(); i++) {
			Aspect advisor = (Aspect) this.aspects.get(i);
			if (advisor.getAdvice() == advisor) {
				return i;
			}
		}
		return -1;
	}

	protected final void updateAspectArray() {
		this.aspectArray = (Aspect[]) this.aspects.toArray(new Aspect[this.aspects.size()]);
	}

	public final Aspect[] getAspects() {
		return this.aspectArray;
	}

	public void addInvocationAspect(InvocationAspect aspect) {
		int pos = (this.invocationAspects != null) ? this.invocationAspects.size() : 0;
		addInvocationAspect(pos, aspect);
		updateInvocationAspectArray();
	}

	public void addInvocationAspect(int pos, InvocationAspect aspect) {
		this.invocationAspects.add(pos, aspect);
		updateInvocationAspectArray();
	}

	public void addAllInvocationAspects(InvocationAspect[] aspects) {
		for (int i = 0; i < aspects.length; i++) {
			InvocationAspect aspect = aspects[i];
			this.invocationAspects.add(aspect);
		}
		updateInvocationAspectArray();
	}

	public final boolean removeInvocationAspect(InvocationAspect aspect) {
		int index = indexInvocationOf(aspect);
		if (index == -1) {
			return false;
		} else {
			removeInvocationAspect(index);
			return true;
		}
	}

	public void removeInvocationAspect(int index) {
		this.invocationAspects.remove(index);
		updateInvocationAspectArray();
	}

	public int indexInvocationOf(InvocationAspect aspect) {
		for (int i = 0; i < this.invocationAspects.size(); i++) {
			InvocationAspect advisor = (InvocationAspect) this.invocationAspects.get(i);
			if (advisor.getInvocationAdvice() == advisor) {
				return i;
			}
		}
		return -1;
	}

	protected final void updateInvocationAspectArray() {
		this.invocationAspectArray = (InvocationAspect[]) this.invocationAspects.toArray(new InvocationAspect[this.invocationAspects.size()]);
	}

	public InvocationAspect[] getInvocationAspects() {
		return invocationAspectArray;
	}

}
