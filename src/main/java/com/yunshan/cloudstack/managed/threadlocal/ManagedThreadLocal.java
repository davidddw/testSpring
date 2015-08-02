package com.yunshan.cloudstack.managed.threadlocal;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yunshan.cloudstack.managed.context.ManagedContextUtils;

public class ManagedThreadLocal<T> extends ThreadLocal<T> {

	private static final ThreadLocal<Map<Object, Object>> MANAGED_THREAD_LOCAL = new ThreadLocal<Map<Object, Object>>() {
		@Override
		protected Map<Object, Object> initialValue() {
			return new HashMap<Object, Object>();
		}
	};

	private static boolean s_validateContext = false;
	private static final Logger log = LoggerFactory
			.getLogger(ManagedThreadLocal.class);

	@SuppressWarnings("unchecked")
	@Override
	public T get() {
		validateInContext(this);
		Map<Object, Object> map = MANAGED_THREAD_LOCAL.get();
		Object result = map.get(this);
		if (result == null) {
			result = initialValue();
			map.put(this, result);
		}
		return (T) result;
	}

	@Override
	public void set(T value) {
		validateInContext(this);
		Map<Object, Object> map = MANAGED_THREAD_LOCAL.get();
		map.put(this, value);
	}

	public static void reset() {
		validateInContext(null);
		MANAGED_THREAD_LOCAL.remove();
	}

	@Override
	public void remove() {
		Map<Object, Object> map = MANAGED_THREAD_LOCAL.get();
		map.remove(this);
	}

	private static void validateInContext(Object tl) {
		if (s_validateContext && !ManagedContextUtils.isInContext()) {
			String msg = "Using a managed thread local in a non managed context this WILL cause errors at runtime. TL ["
					+ tl + "]";
			log.error(msg, new IllegalStateException(msg));
		}
	}

	public static void setValidateInContext(boolean validate) {
		s_validateContext = validate;
	}
}
