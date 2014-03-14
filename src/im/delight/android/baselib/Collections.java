package im.delight.android.baselib;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

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

public class Collections {
	
	/** This class may not be instantiated */
	private Collections() { }

	/**
	 * Returns a new List that contains all unique items from the given List
	 * @param input List to get the unique items from
	 * @return List containing only unique items
	 */
	public static <E> ArrayList<E> makeListUnique(List<E> input) {
		if (input == null) {
			return null;
		}
		else {
			return new ArrayList<E>(new HashSet<E>(input));
		}
	}
	
	/**
	 * Searches for the given object in the given array and returns the index (or -1 if not found)
	 * @param array the array to search in
	 * @param search the object to search for
	 * @return index or -1
	 */
	public static <E> int arrayIndexOf(E[] array, E search) {
		for (int i = 0; i < array.length; i++) {
			if ((array[i] != null && array[i].equals(search)) || (array[i] == null && search == null)) {
				return i;
			}
		}
		return -1;
	}

}
