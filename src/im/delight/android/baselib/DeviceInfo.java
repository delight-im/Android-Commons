package im.delight.android.baselib;

import java.util.Locale;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Point;
import android.telephony.TelephonyManager;
import android.view.Display;
import android.view.WindowManager;

/**
 * Copyright 2014 www.delight.im <info@delight.im>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

public class DeviceInfo {
	
	/** This class may not be instantiated */
	private DeviceInfo() { }

	/**
	 * Get this app's version ID as installed on the user's device (or Integer.MAX_VALUE if not available)
	 * @param context Context reference to get the PackageManager from
	 * @return app version or Integer.MAX_VALUE
	 */
	public static int getAppVersion(Context context) {
		if (context == null) {
			return Integer.MAX_VALUE;
		}
		else {
			try {
				return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
			}
			catch (Exception e) {
				return Integer.MAX_VALUE;
			}
		}
	}
	
	/**
	 * Get ISO 3166-1 alpha-2 country code for this device (or null if not available)
	 * @param context Context reference to get the TelephonyManager instance from
	 * @return country code or null
	 */
	public static String getCountryISO2(Context context) {
		try {
			final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			final String simCountry = tm.getSimCountryIso();
			if (simCountry != null && simCountry.length() == 2) { // SIM country code is available
				return simCountry.toLowerCase(Locale.US);
			}
			else if (tm.getPhoneType() != TelephonyManager.PHONE_TYPE_CDMA) { // device is not 3G (would be unreliable)
				String networkCountry = tm.getNetworkCountryIso();
				if (networkCountry != null && networkCountry.length() == 2) { // network country code is available
					return networkCountry.toLowerCase(Locale.US);
				}
			}
		}
		catch (Exception e) { }
		return null;
	}
	
	/**
	 * Get the device's screen size as a Point instance (x for width and y for height)
	 * @param context Context reference to get the WindowManager from
	 * @return Point object holding width and height
	 */
	@SuppressLint("NewApi")
	@SuppressWarnings("deprecation")
	public static Point getScreenSize(Context context) {
		final WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		final Display display = wm.getDefaultDisplay();

		Point out = new Point();
		if (android.os.Build.VERSION.SDK_INT >= 13) {
			display.getSize(out);
		}
		else {
			out.x = display.getWidth();
			out.y = display.getHeight();
		}
		return out;
	}

}
