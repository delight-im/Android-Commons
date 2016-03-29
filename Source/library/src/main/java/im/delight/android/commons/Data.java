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

import java.math.BigInteger;

/** Utilities for working with data and primitive data types */
public final class Data {

	/** This class may not be instantiated */
	private Data() { }

	/**
	 * Converts the specified binary data to its hexadecimal representation
	 *
	 * @param data the binary data to convert
	 * @return the hexadecimal representation
	 */
	public static String binToHex(final byte[] data) {
		return String.format("%0" + (data.length * 2) + 'x', new BigInteger(1, data));
	}

	/**
	 * Converts the specified color to its hexadecimal representation
	 *
	 * @param color the color to convert, as defined by the `android.graphics.Color` class
	 * @return the hexadecimal representation
	 */
	public static String colorToHex(final int color) {
		return String.format("#%06X", (0xFFFFFF & color));
	}

}
