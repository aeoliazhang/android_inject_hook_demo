package com.example.javahook;

import java.io.FileDescriptor;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;

public class ImeiBinder implements IBinder {

	private static final String TAG = "HookIPhoneSubInfo";
	private static final java.lang.String DESCRIPTOR = "com.android.internal.telephony.IPhoneSubInfo";
	private IBinder binder = null;

	public ImeiBinder(IBinder test) {

		binder = test;
		if (null == test) {
			Log.e(TAG, "BinderTest constructor:null");
		} else {
			Log.e(TAG, "BinderTest constructor:" + test.hashCode());
		}
	}

	@Override
	public void dump(FileDescriptor arg0, String[] arg1) throws RemoteException {
		this.binder.dump(arg0, arg1);
	}

	@Override
	public String getInterfaceDescriptor() throws RemoteException {
		return this.binder.getInterfaceDescriptor();
	}

	@Override
	public boolean isBinderAlive() {
		return this.binder.isBinderAlive();
	}

	@Override
	public void linkToDeath(DeathRecipient arg0, int arg1)
			throws RemoteException {
		this.binder.linkToDeath(arg0, arg1);
	}

	@Override
	public boolean pingBinder() {
		return this.binder.pingBinder();
	}

	@Override
	public IInterface queryLocalInterface(String arg0) {
		return this.binder.queryLocalInterface(arg0);
	}

	@Override
	public boolean transact(int arg0, Parcel arg1, Parcel arg2, int arg3)
			throws RemoteException {
		Log.e(TAG, "transact====arg0:" + arg0 + " arg3:" + arg3);
		int getDeviceId = HookUtils.getStaticFiled(
				"com.android.internal.telephony.IPhoneSubInfo$Stub",
				"TRANSACTION_getDeviceId");
		int getSubscriberId = HookUtils.getStaticFiled(
				"com.android.internal.telephony.IPhoneSubInfo$Stub",
				"TRANSACTION_getSubscriberId");
		int getLine1Number = HookUtils.getStaticFiled(
				"com.android.internal.telephony.IPhoneSubInfo$Stub",
				"TRANSACTION_getLine1Number");
		if (arg0 == getDeviceId) {
			Log.e(TAG, "transact getDeviceId:" + getDeviceId);
			arg1.setDataPosition(0);
			arg2.setDataCapacity(0);
			arg1.enforceInterface(DESCRIPTOR);
			java.lang.String _result = "123456789876543212";
			arg2.writeNoException();
			arg2.writeString(_result);
			arg2.setDataPosition(0);
			return true;
		}
		if (arg0 == getLine1Number) {

			arg1.setDataPosition(0);
			arg2.setDataPosition(0);
			arg1.enforceInterface(DESCRIPTOR);
			arg2.writeNoException();
			arg2.writeString("110");
			arg2.setDataPosition(0);
			Log.e(TAG, "transact getLine1Number:" + getLine1Number);
			return true;

		}
		if (arg0 == getSubscriberId) {
			Log.e(TAG, "transact getSubscriberId:" + getSubscriberId);
			arg1.setDataPosition(0);
			arg2.setDataPosition(0);
			arg1.enforceInterface(DESCRIPTOR);
			arg2.writeNoException();
			arg2.writeString("356951040706073");
			arg2.setDataPosition(0);
			return true;
		}
		return this.binder.transact(arg0, arg1, arg2, arg3);
	}

	@Override
	public boolean unlinkToDeath(DeathRecipient arg0, int arg1) {
		return this.binder.unlinkToDeath(arg0, arg1);
	}

	@Override
	public void dumpAsync(FileDescriptor fd, String[] args)
			throws RemoteException {
		HookUtils.invokeMethod("android.os.IBinder", "dumpAsync", this.binder,
				new Class[] { FileDescriptor.class, String[].class },
				new Object[] { fd, args });
	}
}
