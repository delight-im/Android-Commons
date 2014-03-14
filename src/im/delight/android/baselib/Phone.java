package im.delight.android.baselib;

import android.content.Context;
import android.content.ContextWrapper;
import android.telephony.TelephonyManager;

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

public class Phone {
	
	/** This class may not be instantiated */
	private Phone() { }
	
	public static String getNumber(ContextWrapper context) {
		try {
			TelephonyManager telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			return telephony.getLine1Number();
		}
		catch (Exception e) {
			return null;
		}
	}
	
	public static String getCountry(ContextWrapper context) {
		return getCountry(context, null);
	}
	
	public static String getCountry(ContextWrapper context, String defaultCountry) {
		try {
			TelephonyManager telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			String country = telephony.getSimCountryIso();
			if (country != null && country.length() == 2) {
				return country;
			}
		}
		catch (Exception e) { }
		return defaultCountry;
	}

}
