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

public class Strings {
	
	/** This class may not be instantiated */
	private Strings() { }
		
	/**
	 * Checks whether the given search String is contained in the subject String without regarding the Strings' cases
	 * @param subject String to search in
	 * @param search String to search for
	 * @return whether the String is contained (true) or not (false)
	 */
	public static boolean containsIgnoreCase(String subject, String search) {
		if (search == null || subject == null) {
			return false;
		}
		else {
			int nChars = search.length();
			int searchLimit = subject.length()-nChars;
			for (int i = 0; i <= searchLimit; i++) {
				if (subject.regionMatches(true, i, search, 0, nChars)) {
					return true;
				}
			}
			return false;
		}
	}

	/**
	 * Repeats the given String
	 * @param str the String to repeat
	 * @param count the desired number of repetitions
	 * @return the new String with the desired number of repetitions
	 */
	public static String repeat(String str, int count) {
		return new String(new char[count]).replace("\0", str);
	}
	
	/**
	 * Fills the given String with spaces at the left until it has the desired length
	 * @param str the String to pad
	 * @param length the desired length
	 * @return the new padded String
	 */
	public static String padLeft(String str, int length) {
		return String.format("%1$"+length+"s", str);
	}
	
	public static String rot13(String input) {
		StringBuilder out = new StringBuilder();
		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if (c >= 'a' && c <= 'm') {
				c += 13;
			}
			else if (c >= 'A' && c <= 'M') {
				c += 13;
			}
			else if (c >= 'n' && c <= 'z') {
				c -= 13;
			}
			else if (c >= 'N' && c <= 'Z') {
				c -= 13;
			}
			out.append(c);
		}
		return out.toString();
	}
	
	public static String[] splitToChunks(final String text, final int chunkLength) {
		int chunksCount = 1 + (text.length() / chunkLength);
		String[] chunks = new String[chunksCount];
		int start;
		int end;
		
		for (int c = 0; c < chunksCount; c++) {
			start = c * chunkLength;
			end = start + chunkLength;
			if (end <= text.length()) {
				chunks[c] = text.substring(start, end);
			}
			else {
				chunks[c] = text.substring(start);
			}
		}
		
		return chunks;
	}

}
