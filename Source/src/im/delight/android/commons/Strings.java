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

/** Utilities for working with strings and characters */
public final class Strings {

	private static final String ELLIPSIS = "\u2026";

	/** This class may not be instantiated */
	private Strings() { }

	/**
	 * Checks whether the given search string is contained in the subject string without regard to the strings' cases
	 *
	 * @param subject the string to search in
	 * @param search the string to search for
	 * @return whether the string is contained in the subject or not
	 */
	public static boolean containsIgnoreCase(final String subject, final String search) {
		if (search == null || subject == null) {
			return false;
		}
		else {
			final int nChars = search.length();
			final int searchLimit = subject.length()-nChars;

			for (int i = 0; i <= searchLimit; i++) {
				if (subject.regionMatches(true, i, search, 0, nChars)) {
					return true;
				}
			}

			return false;
		}
	}

	/**
	 * Repeats the given string so that it reaches the desired count
	 *
	 * @param str the string to repeat
	 * @param count the desired number of repetitions
	 * @return the new string with the desired number of repetitions
	 */
	public static String repeat(final String str, final int count) {
		return new String(new char[count]).replace("\0", str);
	}

	/**
	 * Pads the given string on the left side so that it reaches the desired length
	 *
	 * @param input the string to modify
	 * @param length the desired length of the string
	 * @return the padded string
	 */
	public static String padLeft(final String input, final int length) {
		if (input == null) {
			return null;
		}

		return String.format("%" + length + "s", input);
	}

	/**
	 * Pads the given string on the left side so that it reaches the desired length
	 *
	 * @param input the string to modify
	 * @param length the desired length of the string
	 * @param padChar the character used for padding
	 * @return the padded string
	 */
	public static String padLeft(final String input, final int length, final char padChar) {
		if (input == null) {
			return null;
		}

		return padLeft(input, length).replace(' ', padChar);
	}

	/**
	 * Pads the given string on the right side so that it reaches the desired length
	 *
	 * @param input the string to modify
	 * @param length the desired length of the string
	 * @return the padded string
	 */
	public static String padRight(final String input, final int length) {
		if (input == null) {
			return null;
		}

		return String.format("%-" + length + "s", input);
	}

	/**
	 * Pads the given string on the right side so that it reaches the desired length
	 *
	 * @param input the string to modify
	 * @param length the desired length of the string
	 * @param padChar the character used for padding
	 * @return the padded string
	 */
	public static String padRight(final String input, final int length, final char padChar) {
		if (input == null) {
			return null;
		}

		return padRight(input, length).replace(' ', padChar);
	}

	/**
	 * "Encrypts" the given string using the ROT-13 algorithm
	 *
	 * @param input the string to "encrypt"
	 * @return the "encrypted" string
	 */
	public static String rot13(final String input) {
		final StringBuilder out = new StringBuilder();

		char c;
		for (int i = 0; i < input.length(); i++) {
			c = input.charAt(i);

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

	/**
	 * Splits the given string into chunks of the specified length
	 *
	 * @param textToSplit the text to split into chunks
	 * @param chunkLength the desired length of the chunks
	 * @return the list of chunks extrated from the text
	 */
	public static String[] splitToChunks(final String textToSplit, final int chunkLength) {
		final int chunksCount = 1 + (textToSplit.length() / chunkLength);
		final String[] chunks = new String[chunksCount];

		int start;
		int end;
		for (int c = 0; c < chunksCount; c++) {
			start = c * chunkLength;
			end = start + chunkLength;

			if (end <= textToSplit.length()) {
				chunks[c] = textToSplit.substring(start, end);
			}
			else {
				chunks[c] = textToSplit.substring(start);
			}
		}

		return chunks;
	}

	/**
	 * Ensures that the given string has the maximum length as specified
	 *
	 * @param input the string to ensure the length for
	 * @param maxLength the length to guarantee
	 * @return the (shortened) string
	 */
	public static String ensureMaxLength(final String input, final int maxLength) {
		if (input == null) {
			return null;
		}

		if (input.length() <= maxLength) {
			return input;
		}

		return input.substring(0, maxLength - 1) + ELLIPSIS;
	}

}
