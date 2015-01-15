package im.delight.android.baselib;

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

import android.content.Context;
import android.os.Build;
import android.preference.ListPreference;
import android.util.AttributeSet;

/** ListPreference with auto-updating summary (use <code>android:summary="%s"</code> in XML) */
public class AutoListPreference extends ListPreference {
	
	public AutoListPreference(Context context) {
		super(context);
	}

	public AutoListPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public void setValue(String value) {
		// if the API level is 19 or above
		if (Build.VERSION.SDK_INT >= 19) {
			// we can just call the default implementation
			super.setValue(value);
		}
		// if the API level is below 19
		else {
			// get the old value first
			String oldValue = getValue();
			// call the default implementation
			super.setValue(value);
			// if the new and old value differ
			if (!value.equals(oldValue)) {
				// notify the super class of the change
				notifyChanged();
			}
		}
	}

}
