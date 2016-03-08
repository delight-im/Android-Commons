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

import android.content.Context;
import android.content.ContextWrapper;
import android.telephony.TelephonyManager;

/** Utilities for working with phone numbers and phone data */
public final class Phone {

	/** This class may not be instantiated */
	private Phone() { }

	/**
	 * Gets the phone number for the current device
	 *
	 * Requires the following permission in your `AndroidManifest.xml`:
	 *
	 * `<uses-permission android:name="android.permission.READ_PHONE_STATE" />`
	 *
	 * @param context a context reference
	 * @return the phone number or `null` if not available (e.g. on tablets without telephony features)
	 */
	public static String getNumber(final ContextWrapper context) {
		try {
			TelephonyManager telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			return telephony.getLine1Number();
		}
		catch (Exception e) {
			return null;
		}
	}

	/**
	 * Gets the ISO alpha-2 country code for the current device from the SIM card
	 *
	 * @param context a context reference
	 * @return the country code or `null` if not available
	 */
	public static String getCountry(final ContextWrapper context) {
		return getCountry(context, null);
	}

	/**
	 * Gets the ISO alpha-2 country code for the current device from the SIM card
	 *
	 * @param context a context reference
	 * @param defaultCountry the default country to return
	 * @return the country code or the value from `defaultCountry` if not available
	 */
	public static String getCountry(final ContextWrapper context, final String defaultCountry) {
		try {
			final TelephonyManager telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			final String country = telephony.getSimCountryIso();

			if (country != null && country.length() == 2) {
				return country;
			}
		}
		catch (Exception e) { }

		return defaultCountry;
	}

}
