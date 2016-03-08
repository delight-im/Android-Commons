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

import android.annotation.SuppressLint;
import android.os.Build;
import android.widget.ArrayAdapter;
import java.util.Collection;

/** Utilities for working with `android.widget.Adapter` and its subclasses */
public final class Adapters {

	/** This class may not be instantiated */
	private Adapters() { }

	/**
	 * Sets the items of the given adapter to the specified collection
	 *
	 * This operation includes removing the old items (if any) and adding the new ones
	 *
	 * Any listeners will only be informed about the final result
	 *
	 * @param adapter the adapter for which to set the items
	 * @param content the collection that has the new items
	 */
	public static <T> void setItems(final ArrayAdapter<T> adapter, final Collection<T> content) {
    	adapter.setNotifyOnChange(false);

    	adapter.clear();
    	addAll(adapter, content);

    	adapter.notifyDataSetChanged();
    }

    /**
     * Adds all the items from the specified collection to the given adapter at once
     *
     * @param adapter the adapter to insert the items into
     * @param items the items to insert
     */
	@SuppressLint("NewApi")
	public static <T> void addAll(final ArrayAdapter<T> adapter, final Collection<T> items) {
    	if (Build.VERSION.SDK_INT >= 11) {
        	adapter.addAll(items);
    	}
    	else {
    		for (T item : items) {
    			adapter.add(item);
    		}
    	}
    }

}
