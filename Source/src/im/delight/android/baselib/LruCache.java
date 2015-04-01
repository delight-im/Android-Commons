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

/**
 * LRU (least recently used) cache that can hold a fixed number of elements
 * <p>
 * If the cache is full and a new entry is added, the eldest entry is dropped
 * <p>
 * This implementation is thread-safe
 *
 * @param <K> the type of the keys
 * @param <V> the type of the values
 */
public class LruCache<K, V> extends Cache<K, V> {

	/**
	 * Creates a new instance with the given cache size
	 *
	 * @param cacheSize the maximum number of elements to hold
	 */
	public LruCache(final int cacheSize) {
		// create a new instance that uses access order (and not insertion order)
		super(cacheSize, true);
	}

}
