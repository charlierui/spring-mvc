package com.app.util;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BaseObject<T> implements Serializable {

	private static final long serialVersionUID = 1L;

    /*
     * copy from
     * 将传过来的对象中的属性值赋给本对象相应的参数。
     * */
	public T copy(T model) {
		Class clazz = model.getClass();
		for (Method method : clazz.getMethods()) {
			if (method.getName().indexOf("set") == 0) {
				String temp = method.getName().substring(3);
				String fieldName = temp.substring(0, 1).toLowerCase()
						+ temp.substring(1);

				Method getMothod = null;
				Object object = null;
				try {
					getMothod = clazz.getMethod("get" + temp);
					object = getMothod.invoke(model);
				} catch (NoSuchMethodException e) {
					try {
						getMothod = clazz.getMethod("is" + temp);
						object = getMothod.invoke(model);
					} catch (Exception e1) {
					}
				} catch (Exception e) {
				}
				if (object != null) {
					if (!(object instanceof Map || object instanceof List
							|| object instanceof Set || object instanceof BaseObject)) {
						try {
							method.invoke(this, object);
						} catch (Exception e) {
						}
					}
				}

			}

		}
		return model;
	}
	
	
	public void copyAnyway(BaseObject model) {
		if(model==null)return;
		Class clazz = this.getClass();
		Class clatt = model.getClass();
		for (Method method : clazz.getMethods()) {
			if (method.getName().indexOf("set") == 0) {
				String temp = method.getName().substring(3);
				String fieldName = temp.substring(0, 1).toLowerCase()
						+ temp.substring(1);

				Method getMothod = null;
				Object object = null;
				boolean hasField = false;
				try {
					getMothod = clatt.getMethod("get" + temp);
					object = getMothod.invoke(model);
					hasField = true;
				} catch (NoSuchMethodException e) {
					try {
						getMothod = clatt.getMethod("is" + temp);
						object = getMothod.invoke(model);
						hasField = true;
					} catch (Exception e1) {
					}
				} catch (Exception e) {
				}
				if (hasField) {
					if (!(object instanceof Map || object instanceof List
							|| object instanceof Set || object instanceof BaseObject)) {
						try {
							method.invoke(this, object);
						} catch (Exception e) {
						}
					}
				}

			}

		}
	}
}
