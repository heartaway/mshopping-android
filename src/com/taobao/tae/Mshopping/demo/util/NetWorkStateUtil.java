package com.taobao.tae.Mshopping.demo.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetWorkStateUtil {
	public static final int GPRS = 0;
	public static final int WIFI = 1;

	public static int getConnectedType(Context paramContext) {
		if (paramContext != null) {
			NetworkInfo localNetworkInfo = ((ConnectivityManager) paramContext
					.getSystemService("connectivity")).getActiveNetworkInfo();
			if ((localNetworkInfo != null) && (localNetworkInfo.isAvailable()))
				return localNetworkInfo.getType();
		}
		return -1;
	}

	public static boolean isByTypeConnected(Context paramContext, int paramInt) {
		if (paramContext != null) {
			NetworkInfo localNetworkInfo = ((ConnectivityManager) paramContext
					.getSystemService("connectivity")).getNetworkInfo(paramInt);
			if (localNetworkInfo != null)
				return localNetworkInfo.isAvailable();
		}
		return false;
	}

	public static boolean isConnected(Context paramContext) {
		if (paramContext != null) {
			NetworkInfo localNetworkInfo = ((ConnectivityManager) paramContext
					.getSystemService("connectivity")).getActiveNetworkInfo();
			if (localNetworkInfo != null)
				return localNetworkInfo.isAvailable();
		}
		return false;
	}
}