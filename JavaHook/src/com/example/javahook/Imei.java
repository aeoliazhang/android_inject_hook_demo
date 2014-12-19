package com.example.javahook;


import android.os.IBinder;
import android.util.Log;

public class Imei {

	private static final String TAG = "phone";
	public static void hook() {
		Log.e(TAG, "hook start");
		IBinder binder = HookUtils.getCacheBinder("iphonesubinfo");
		String classNameString = binder.getClass().getName();
		Log.e(TAG, "before hook binder class:"
				+ binder.getClass().getName());
		if (classNameString.equals("android.os.BinderProxy")) {
			ImeiBinder binderTest = new ImeiBinder(
					binder);
			HookUtils.addTosCache("iphonesubinfo", binderTest);
		}
		IBinder now = HookUtils.getCacheBinder("iphonesubinfo");
		Log.e(TAG, "after hook binder class:" + now.getClass().getName());
		Log.e(TAG, "hook end");
	}
}
