package im.delight.android.commons;

/*
 * Copyright (c) delight.im <info@delight.im>
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

import java.util.Locale;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Point;
import android.telephony.TelephonyManager;
import android.view.Display;
import android.view.WindowManager;

/** Exposes device information for the current device that can be be retrieved by the application */
public final class DeviceInfo {

	/** This class may not be instantiated */
	private DeviceInfo() { }

	/**
	 * Retrieves the current application's version ID as installed on the user's device
	 *
	 * @param context a context reference
	 * @return the app's version ID or `Integer.MAX_VALUE` if not available
	 */
	public static int getAppVersion(final Context context) {
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
	 * Retrieves the ISO 3166-1 alpha-2 country code for the current device
	 *
	 * @param context a context reference
	 * @return the lowercase country code or `null` if not available
	 */
	public static String getCountryISO2(final Context context) {
		try {
			final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			final String simCountry = tm.getSimCountryIso();

			// if the SIM country code is available
			if (simCountry != null && simCountry.length() == 2) {
				return simCountry.toLowerCase(Locale.US);
			}
			// if the device is not 3G (would be unreliable)
			else if (tm.getPhoneType() != TelephonyManager.PHONE_TYPE_CDMA) {
				final String networkCountry = tm.getNetworkCountryIso();

				// if the network country code is available
				if (networkCountry != null && networkCountry.length() == 2) {
					return networkCountry.toLowerCase(Locale.US);
				}
			}
		}
		catch (Exception e) { }

		return null;
	}

	/**
	 * Gets the screen size for the current device
	 *
	 * @param context a context reference
	 * @return a `Point` object holding the width in `x` and the height in `y`
	 */
	@SuppressLint("NewApi")
	@SuppressWarnings("deprecation")
	public static Point getScreenSize(final Context context) {
		final WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		final Display display = wm.getDefaultDisplay();

		final Point out = new Point();

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
