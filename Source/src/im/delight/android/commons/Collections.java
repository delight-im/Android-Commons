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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/** Utilities for working with arrays and `java.util.Collection<E>` (including its various subclasses) */
public final class Collections {

	/** This class may not be instantiated */
	private Collections() { }

	/**
	 * Returns a new list that contains all unique items from the specified input list
	 *
	 * @param input the list to get the unique items from
	 * @return a list containing only the unique items
	 */
	public static <E> ArrayList<E> makeListUnique(final List<E> input) {
		if (input == null) {
			return null;
		}
		else {
			return new ArrayList<E>(new HashSet<E>(input));
		}
	}

	/**
	 * Searches for the given object in the specified array and returns the object's index
	 *
	 * @param array the array to search in
	 * @param search the object to search for
	 * @return the object's index in the array or `-1`
	 */
	public static <E> int arrayIndexOf(final E[] array, final E search) {
		for (int i = 0; i < array.length; i++) {
			if ((array[i] != null && array[i].equals(search)) || (array[i] == null && search == null)) {
				return i;
			}
		}

		return -1;
	}

	/**
	 * Joins the given iterable object's elements and connects them with a delimiter string
	 *
	 * @param iterable the iterable object's whose items are to be joined
	 * @param delimiter the string that will be used as a divider between the joined items
	 * @return a string that contains all the joined items
	 */
	public static String implode(final Iterable<?> iterable, final String delimiter) {
		final StringBuilder out = new StringBuilder();

		int counter = 0;
		for (Object obj : iterable) {
			if (obj != null) {
				if (counter > 0) {
					out.append(delimiter);
				}

				if (obj instanceof String) {
					out.append(obj);
				}
				else {
					out.append(obj.toString());
				}

				counter++;
			}
		}

		return out.toString();
	}

}
