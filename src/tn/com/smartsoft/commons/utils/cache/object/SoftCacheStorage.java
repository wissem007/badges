package tn.com.smartsoft.commons.utils.cache.object;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import tn.com.smartsoft.commons.exceptions.TechnicalException;
import tn.com.smartsoft.commons.utils.ConcurrentMapFactory;

public class SoftCacheStorage implements ConcurrentCacheStorage {
	private static final Method atomicRemove = getAtomicRemoveMethod();

	private final ReferenceQueue<Object> queue = new ReferenceQueue<Object>();
	private final Map<Object, SoftValueReference> map;
	private final boolean concurrent;

	public SoftCacheStorage() {
		this(ConcurrentMapFactory.createMap());
	}

	public boolean isConcurrent() {
		return concurrent;
	}

	public SoftCacheStorage(Map<Object, SoftValueReference> backingMap) {
		map = backingMap;
		this.concurrent = ConcurrentMapFactory.isConcurrent(map);
	}

	public Object get(Object key) {
		processQueue();
		Reference ref = (Reference) map.get(key);
		return ref == null ? null : ref.get();
	}

	public void put(Object key, Object value) {
		processQueue();
		map.put(key, new SoftValueReference(key, value, queue));
	}

	public void remove(Object key) {
		processQueue();
		map.remove(key);
	}

	public void clear() {
		map.clear();
		processQueue();
	}

	private void processQueue() {
		for (;;) {
			SoftValueReference ref = (SoftValueReference) queue.poll();
			if (ref == null) {
				return;
			}
			Object key = ref.getKey();
			if (concurrent) {
				try {
					atomicRemove.invoke(map, new Object[] { key, ref });
				} catch (IllegalAccessException e) {
					throw new TechnicalException(e);
				} catch (InvocationTargetException e) {
					throw new TechnicalException(e);
				}
			} else if (map.get(key) == ref) {
				map.remove(key);
			}
		}
	}

	private static final class SoftValueReference extends SoftReference {
		private final Object key;

		SoftValueReference(Object key, Object value, ReferenceQueue queue) {
			super(value, queue);
			this.key = key;
		}

		Object getKey() {
			return key;
		}
	}

	private static Method getAtomicRemoveMethod() {
		try {
			return java.util.concurrent.ConcurrentMap.class.getMethod("remove", new Class[] { Object.class, Object.class });
		} catch (NoSuchMethodException e) {
			throw new TechnicalException(e);
		}
	}
}