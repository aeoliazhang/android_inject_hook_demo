package com.example.javahook;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import android.os.IBinder;

public class HookUtils {

	private static final String TAG = "HookUtil";

	public static IBinder getCacheBinder(String name) {

		IBinder retBinder = null;
		try {
			// Ibind
			Class class1 = Class.forName("android.os.ServiceManager");
			Method method = class1.getDeclaredMethod("getService",
					new Class[] { java.lang.String.class });
			method.setAccessible(true);
			retBinder = (IBinder) method.invoke(class1, new Object[] { name });
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();

		}
		return retBinder;

	}

	public static Boolean addTosCache(String paramString, IBinder paramIBinder) {
		Boolean retBoolean = false;
		try {
			Field localField = Class.forName("android.os.ServiceManager")
					.getDeclaredField("sCache");
			localField.setAccessible(true);
			((HashMap<String, IBinder>) localField.get(null)).put(paramString,
					paramIBinder);

			retBoolean = true;
			return retBoolean;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retBoolean;
	}

	public static Object getFiled(String p1, String p2, Object obj) {

		try {
			Field field = Class.forName(p1).getDeclaredField(p2);
			field.setAccessible(true);
			Object localObject = field.get(obj);
			return localObject;
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void setStaticFiled(String className, String filedName,
			Object p1, Object p2) {
		try {
			Class class1 = Class.forName(className);
			Field field = class1.getDeclaredField(filedName);
			field.setAccessible(true);
			Object object = field.get(p1);
			if (null != object) {
				field.set(p1, p2);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

	}

	public static int getStaticFiled(String parm1, String parm2) {
		try {
			int ret = ((Integer) getFiled(parm1, parm2, null)).intValue();
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return -1;
	}

	public static Object invokeMethod(String paramString1, String paramString2,
			Object paramObject, Class[] paramArrayOfClass,
			Object[] paramArrayOfObject) {
		try {
			Method localMethod = Class.forName(paramString1).getDeclaredMethod(
					paramString2, paramArrayOfClass);
			localMethod.setAccessible(true);
			Object localObject2 = localMethod.invoke(paramObject,
					paramArrayOfObject);
			return localObject2;
		} catch (InvocationTargetException localInvocationTargetException) {
			return null;
		} catch (ClassNotFoundException localClassNotFoundException) {
			localClassNotFoundException.printStackTrace();
			return null;
		} catch (SecurityException localSecurityException) {
			localSecurityException.printStackTrace();
			return null;
		} catch (NoSuchMethodException localNoSuchMethodException) {
			localNoSuchMethodException.printStackTrace();
			return null;
		} catch (IllegalArgumentException localIllegalArgumentException) {
			localIllegalArgumentException.printStackTrace();
			return null;
		} catch (IllegalAccessException localIllegalAccessException) {
			localIllegalAccessException.printStackTrace();
		}
		return null;
	}

}
