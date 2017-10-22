package shit.helper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import shit.helper.stringcast.*;

/**
 * 反射工具类
 * 
 * @author GongTengPangYi
 *
 */
public class ShitReflectHelper {

	/**
	 * 需要被强制转换的参数类型的键值对
	 */
	private static Map<Class<?>, ShitStringCast> stringCastTypeMap = new HashMap<>();

	/**
	 * 初始化这个键值对
	 */
	static {
		stringCastTypeMap.put(java.lang.Boolean.class, new ShitStringCastBoolean());
		stringCastTypeMap.put(java.lang.Long.class, new ShitStringCastLong());
		stringCastTypeMap.put(java.lang.Integer.class, new ShitStringCastInteger());
		stringCastTypeMap.put(java.lang.Float.class, new ShitStringCastFloat());
		stringCastTypeMap.put(java.util.Date.class,
				ShitStringCastDate.newInstance(ShitStringCastDate.defaultDateFormatStr));
	}

	/**
	 * 类型转换匹配赋值
	 * 
	 * @param clazz
	 *            类
	 * @param stringCast
	 *            类型转换接口
	 */
	public static void setStringCastType(Class<?> clazz, ShitStringCast stringCast) {
		stringCastTypeMap.put(clazz, stringCast);
	}

	/**
	 * 初始化对象
	 * 
	 * @param clazz
	 *            类
	 * @param parameterTypes
	 *            构造函数参数类型
	 * @param params
	 *            构造函数参数
	 * @return 对象
	 * @throws ShitReflectException
	 *             反射异常
	 */
	public static Object newInstance(Class<?> clazz, Class<?>[] parameterTypes, Object... params)
			throws ShitReflectException {
		if (parameterTypes.length == 0) {
			try {
				return clazz.newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
				throw new ShitReflectException("\nReflectHelper.newInstance：无法实例化" + ".\n");
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				throw new ShitReflectException("\nReflectHelper.newInstance：参数不对" + ".\n");
			}
		} else {
			Constructor<?> constructor;
			try {
				constructor = clazz.getConstructor(parameterTypes);
				return instanceConstructor(constructor, params);
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
				throw new ShitReflectException("\nReflectHelper.findMethodByName：无此构造函数：" + ".\n");
			} catch (SecurityException e) {
				e.printStackTrace();
				throw new ShitReflectException("\nReflectHelper.findMethodByName：无访问权限" + ".\n");
			}
		}
	}

	/**
	 * 用构造函数初始化
	 * 
	 * @param constructor
	 *            构造方法
	 * @param params
	 *            参数
	 * @return 实例化的对象
	 * @throws ShitReflectException
	 *             反射过程异常
	 */
	public static Object instanceConstructor(Constructor<?> constructor, Object... params) throws ShitReflectException {
		try {
			return constructor.newInstance(params);
		} catch (InstantiationException e) {
			e.printStackTrace();
			throw new ShitReflectException("\nReflectHelper.instanceConstructor：无法实例化" + ".\n");
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new ShitReflectException("\nReflectHelper.instanceConstructor：参数不对" + ".\n");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			throw new ShitReflectException("\nReflectHelper.instanceConstructor：无访问权限" + ".\n");
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			throw new ShitReflectException("\nReflectHelper.instanceConstructor：执行目标不对" + ".\n");
		}
	}

	/**
	 * 根据Method对象调用方法
	 * 
	 * @param obj
	 *            执行对象
	 * @param method
	 *            执行方法
	 * @param params
	 *            方法参数
	 * @return 方法调用的返回值
	 * @throws ShitReflectException
	 *             反射异常
	 */
	public static Object invokeMethod(Object obj, Method method, Object... params) throws ShitReflectException {
		try {
			return method.invoke(obj, params);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new ShitReflectException("\nReflectHelper.invokeMethod：参数不对" + ".\n");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			throw new ShitReflectException("\nReflectHelper.invokeMethod：无访问权限" + ".\n");
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			throw new ShitReflectException("\nReflectHelper.invokeMethod：执行目标不对" + ".\n");
		}
	}

	/**
	 * 更据名字来查询方法
	 * 
	 * @param clazz
	 *            类
	 * @param name
	 *            方法名
	 * @param parameterTypes
	 *            方法参数类型数组
	 * @param includeSuper
	 *            是否在父类中查找
	 * @return 方法
	 * @throws ShitReflectException
	 *             反射过程异常
	 */
	public static Method findMethodByName(Class<?> clazz, String name, Class<?>[] parameterTypes, boolean includeSuper)
			throws ShitReflectException {
		try {
			return clazz.getDeclaredMethod(name, parameterTypes);
		} catch (NoSuchMethodException e) {
			if (includeSuper && clazz.getGenericSuperclass() != null) {
				return findMethodByName(clazz.getSuperclass(), name, parameterTypes, includeSuper);
			} else {
				e.printStackTrace();
				throw new ShitReflectException("\nReflectHelper.findMethodByName：无此方法：" + name + ".\n");
			}
		} catch (SecurityException e) {
			e.printStackTrace();
			throw new ShitReflectException("\nReflectHelper.findMethodByName：无访问权限" + ".\n");
		}
	}

	/**
	 * 更据名字来反射执行方法
	 * 
	 * @param obj
	 *            执行的对象
	 * @param clazz
	 *            类
	 * @param name
	 *            方法名
	 * @param parameterTypes
	 *            方法参数类型数组
	 * @param includeSuper
	 *            是否在父类中查找
	 * @param params
	 *            方法参数
	 * @return 返回值
	 * @throws ShitReflectException
	 *             反射过程异常
	 */
	public static Object invokeMethodByName(Object obj, Class<?> clazz, String name, Class<?>[] parameterTypes,
			boolean includeSuper, Object... params) throws ShitReflectException {
		Method method = findMethodByName(clazz, name, parameterTypes, includeSuper);
		if (method != null) {
			return invokeMethod(obj, method, params);
		} else {
			return null;
		}
	}

	/**
	 * 更据名字来反射执行静态方法
	 * 
	 * @param obj
	 *            执行的对象
	 * @param clazz
	 *            类
	 * @param name
	 *            方法名
	 * @param parameterTypes
	 *            方法参数类型数组
	 * @param includeSuper
	 *            是否在父类中查找
	 * @param params
	 *            方法参数
	 * @return 返回值
	 * @throws ShitReflectException
	 *             反射过程异常
	 */
	public static Object invokeStaticMethodByName(Class<?> clazz, String name, Class<?>[] parameterTypes,
			boolean includeSuper, Object... params) throws ShitReflectException {
		return invokeMethodByName(null, clazz, name, parameterTypes, includeSuper, params);
	}

	/**
	 * 获取参数类型
	 * 
	 * @param field
	 *            参数
	 * @return 类型
	 */
	public static Class<?> getParamType(Field field) {
		return field.getType();
	}

	/**
	 * 更据名字获取字段
	 * 
	 * @param clazz
	 *            类
	 * @param name
	 *            字段名
	 * @param includeSuper
	 *            是否在父类查找
	 * @return 字段
	 * @throws ShitReflectException
	 *             反射过程异常
	 */
	public static Field findFieldByName(Class<?> clazz, String name, boolean includeSuper) throws ShitReflectException {
		try {
			return clazz.getDeclaredField(name);
		} catch (NoSuchFieldException e) {
			if (includeSuper && clazz.getGenericSuperclass() != null) {
				return findFieldByName(clazz.getSuperclass(), name, includeSuper);
			} else {
				e.printStackTrace();
				throw new ShitReflectException("\nReflectHelper.getFieldByName：无此参数：" + name + ".\n");
			}
		} catch (SecurityException e) {
			e.printStackTrace();
			throw new ShitReflectException("\nReflectHelper.getFieldByName：无访问权限" + ".\n");
		}
	}

	/**
	 * 查询所有字段
	 * 
	 * @param clazz
	 *            类
	 * @param includeSuper
	 *            是否包含父类
	 * @return 字段
	 */
	public static Field[] findFields(Class<?> clazz, boolean includeSuper) {
		Field[] fields = clazz.getDeclaredFields();
		if (includeSuper && clazz.getGenericSuperclass() != null) {
			Field[] superFields = findFields(clazz.getSuperclass(), includeSuper);
			Field[] result = Arrays.copyOf(fields, fields.length + superFields.length);
			System.arraycopy(superFields, 0, result, fields.length, superFields.length);
			return result;
		} else {
			return fields;
		}
	}

	/**
	 * 查询所有方法
	 * 
	 * @param clazz
	 *            类
	 * @param includeSuperIndex
	 *            包含父类的级数(不包含父类则0)
	 * @return 方法
	 */
	public static Method[] findMethods(Class<?> clazz, int includeSuperIndex) {
		Method[] methods = clazz.getDeclaredMethods();
		if (includeSuperIndex-- > 0 && clazz.getGenericSuperclass() != null) {
			Method[] superMethods = findMethods(clazz.getSuperclass(), includeSuperIndex);
			Method[] result = Arrays.copyOf(methods, methods.length + superMethods.length);
			System.arraycopy(superMethods, 0, result, methods.length, superMethods.length);
			return result;
		} else {
			return methods;
		}
	}

	/**
	 * 根据方法名来反射执行对象方法
	 * 
	 * @param obj
	 *            对象
	 * @param clazz
	 *            类
	 * @param methodName
	 *            方法名
	 * @param paramsName
	 *            参数名
	 * @param includeSuper
	 *            是否在父类中查找
	 * @param values
	 *            字符串参数
	 * @return 执行返回值
	 * @throws ShitReflectException
	 *             反射过程异常
	 */
	public static Object invokeObjectMethodByName(Object obj, Class<?> clazz, String methodName, String[] paramsName,
			boolean includeSuper, String... values) throws ShitReflectException {
		int length = paramsName.length;
		Class<?>[] parameterTypes = new Class<?>[length];
		Object[] params = new Object[length];
		for (int i = 0; i < length; i++) {
			Field field = findFieldByName(clazz, paramsName[i], includeSuper);
			Class<?> type = getParamType(field);
			parameterTypes[i] = type;
			ShitStringCast stringCast = stringCastTypeMap.get(type);
			params[i] = stringCast != null ? stringCast.cast(values[i]) : values[i];
		}
		return invokeMethodByName(obj, clazz, methodName, parameterTypes, includeSuper, params);
	}

	/**
	 * 根据方法名来反射执行对象方法，不带class对象
	 * 
	 * @param obj
	 *            对象
	 * @param methodName
	 *            方法名
	 * @param paramsName
	 *            参数名
	 * @param includeSuper
	 *            是否在父类中查找
	 * @param values
	 *            字符串参数
	 * @return 执行返回值
	 * @throws ShitReflectException
	 *             反射过程异常
	 */
	public static Object invokeObjectMethodByName(Object obj, String methodName, String[] paramsName,
			boolean includeSuper, String... values) throws ShitReflectException {
		return invokeObjectMethodByName(obj, obj.getClass(), methodName, paramsName, includeSuper, values);
	}

	/**
	 * 字符串转换接口
	 * 
	 * @author ASUS
	 *
	 */
	public interface ShitStringCast {

		/**
		 * 字符串转化为其他类型
		 * 
		 * @param value
		 *            元数据
		 * @return 转换后的数据
		 */
		Object cast(String value);
	}

	/**
	 * 动态执行set方法，set的类型必须是String可转化的
	 * 
	 * @param obj
	 *            对象
	 * @param fieldName
	 *            字段名
	 * @param value
	 *            参数值
	 * @param includeSuper
	 *            是否包含父类
	 * @throws ShitReflectException
	 *             反射过程异常
	 */
	public static void setValue(Object obj, String fieldName, String value, boolean includeSuper)
			throws ShitReflectException {
		String methodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
		String[] paramsName = { fieldName };
		String[] values = { value };
		invokeObjectMethodByName(obj, methodName, paramsName, includeSuper, values);
	}

	/**
	 * 动态执行String不可转化的set方法
	 * 
	 * @param obj
	 *            对象
	 * @param fieldName
	 *            字段名
	 * @param value
	 *            参数值
	 * @param includeSuper
	 *            是否包含父类
	 * @throws ShitReflectException
	 *             反射过程异常
	 */
	public static void setNotStrCastValue(Object obj, String fieldName, Object value, boolean includeSuper)
			throws ShitReflectException {
		String methodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
		Object[] values = { value };
		Field field = findFieldByName(obj.getClass(), fieldName, includeSuper);
		Class<?>[] types = { field.getType() };
		invokeMethodByName(obj, obj.getClass(), methodName, types, includeSuper, values);
	}

	/**
	 * 动态执行get方法
	 * 
	 * @param obj
	 *            对象
	 * @param fieldName
	 *            字段名
	 * @param includeSuper
	 *            是否包含父类
	 * @return get返回值
	 * @throws ShitReflectException
	 *             反射过程异常
	 */
	public static Object getValue(Object obj, String fieldName, boolean includeSuper) throws ShitReflectException {
		String methodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
		String[] paramsName = {};
		String[] values = {};
		return invokeObjectMethodByName(obj, methodName, paramsName, includeSuper, values);
	}

}
