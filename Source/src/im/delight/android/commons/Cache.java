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

import java.util.Set;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Cache that can hold a fixed number of elements
 * <p>
 * If the cache is full and a new entry is added, the eldest entry is dropped
 * <p>
 * This implementation is thread-safe
 *
 * @param <K> the type of the keys
 * @param <V> the type of the values
 */
public class Cache<K, V> {

	private static final float MAP_LOAD_FACTOR = 0.75f;
	private final int mCacheSize;
	private final Map<K, V> mMap;

	/**
	 * Creates a new instance with the given cache size
	 *
	 * @param cacheSize the maximum number of elements to hold
	 */
	public Cache(final int cacheSize) {
		// create a new instance that uses insertion order (and not access order)
		this(cacheSize, false);
	}

	protected Cache(final int cacheSize, final boolean useAccessOrder) {
		mCacheSize = cacheSize;
		mMap = new LinkedHashMap<K, V>(mCacheSize, MAP_LOAD_FACTOR, useAccessOrder) {

			private static final long serialVersionUID = 3042571622151610748L;

			@Override
			protected boolean removeEldestEntry(final Map.Entry<K, V> eldest) {
				// remove the eldest entry when the size exceeds the maximum
				if (size() > mCacheSize) {
					if (eldest != null) {
						onEntryRemoved(eldest.getKey(), eldest.getValue(), false);
					}

					return true;
				}
				else {
					return false;
				}
			}

		};
	}

	/**
	 * Inserts a new element and possibly overwrite any previous value with the same key
	 *
	 * @param key the key to save the value for
	 * @param value the value to save
	 * @return the previous value or `null`
	 */
	public synchronized V put(final K key, final V value) {
		final V previous = mMap.put(key, value);

		if (previous != null) {
			onEntryRemoved(key, previous, true);
		}

		return previous;
	}

	/**
	 * Returns the value for the given key
	 *
	 * @param key the key to look up
	 * @return the value that was found for the key or `null`
	 */
	public synchronized V get(final K key) {
		return mMap.get(key);
	}

	/**
	 * Removes the entry with the specified key
	 *
	 * @param key the key to remove
	 * @return the old value for the specified key
	 */
	public synchronized V remove(final K key) {
		onEntryRemoved(key, mMap.get(key), true);

		return mMap.remove(key);
	}

	/**
	 * Returns all values that are currently in this cache
	 *
	 * @return the values
	 */
	public synchronized Collection<V> values() {
		return mMap.values();
	}

	/**
	 * Returns all ekys that are currently in this cache
	 *
	 * @return the keys
	 */
	public synchronized Set<K> keys() {
		return mMap.keySet();
	}

	/**
	 * Returns the current size of this cache
	 *
	 * @return the number of elements contained in this cache
	 */
	public synchronized int size() {
		return mMap.size();
	}

	/**
	 * Override this method if you want to be informed whenever an entry is removed from this cache
	 * <p>
	 * You may use this if you must manually release resources whe an element is deleted
	 * <p>
	 * The default implementation does nothing
	 *
	 * @param key the key that was removed
	 * @param value the value that was removed
	 * @param causedManually whether the removal was caused manually (through `put(...)` or `delete(...)`)
	 */
	@SuppressWarnings("unused")
	public void onEntryRemoved(final K key, final V value, final boolean causedManually) { }

}
