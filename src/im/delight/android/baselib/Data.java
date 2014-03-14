package im.delight.android.baselib;

import java.math.BigInteger;

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

public class Data {
	
	/** This class may not be instantiated */
	private Data() { }

	public static String binToHex(byte[] data) {
		return String.format("%0" + (data.length*2) + 'x', new BigInteger(1, data));
	}
	
	public static String colorToHex(int color) {
		return String.format("#%06X", (0xFFFFFF & color));
	}

}
