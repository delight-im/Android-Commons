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

import java.lang.reflect.Field;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.InputFilter;
import android.text.Spannable;
import android.text.Spannable.Factory;
import android.text.style.ImageSpan;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;

public class UI {

	/** This class may not be instantiated */
	private UI() { }

	public static int getTextColor(int backgroundColor) {
		return getColorBrightness(backgroundColor) > 125 ? Color.BLACK : Color.WHITE;
	}

	public static int getRandomColor() {
		Random random = new Random();
		return Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));
	}

	public static double getColorBrightness(int color) {
		int r = Color.red(color);
		int g = Color.green(color);
		int b = Color.blue(color);

		return Math.sqrt(0.299f * r * r + 0.587f * g * g + 0.114f * b * b);
	}

	public static void setMaxLength(EditText editText, int maxLength) {
		setMaxLength(editText, maxLength, null);
	}

	public static void setMaxLength(EditText editText, int maxLength, InputFilter[] existingInputFilters) {
		final InputFilter[] allInputFilters;
		if (existingInputFilters == null) {
			allInputFilters = new InputFilter[1];
			allInputFilters[0] = new InputFilter.LengthFilter(maxLength);
		}
		else {
			allInputFilters = new InputFilter[existingInputFilters.length+1];
			for (int i = 0; i < allInputFilters.length; i++) {
				if (i < existingInputFilters.length) {
					allInputFilters[i] = existingInputFilters[i];
				}
				else {
					allInputFilters[i] = new InputFilter.LengthFilter(maxLength);
				}
			}
		}
		editText.setFilters(allInputFilters);
	}

	public static void putCursorToEnd(EditText editText) {
		editText.setSelection(editText.getText().length());
	}

	public static void scrollToBottom(final ListView listView) {
		listView.post(new Runnable() {
	        @Override
	        public void run() {
	        	int itemCount = listView.getAdapter().getCount();
	        	if (itemCount > 0) {
	        		listView.setSelection(itemCount - 1);
	        	}
	        }
	    });
	}

	public static Bitmap getViewScreenshot(final View view) {
		// set up the drawing cache
		view.setDrawingCacheEnabled(true);
		view.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

		// get the screenshot
		final Bitmap viewScreenshot = view.getDrawingCache(true);
		final Bitmap output = viewScreenshot.copy(viewScreenshot.getConfig(), false);

		// disable the drawing cache again
		view.destroyDrawingCache();
		view.setDrawingCacheEnabled(false);

		return output;
	}

	public static void forceOverflowMenu(Context context) {
		try {
			ViewConfiguration config = ViewConfiguration.get(context);
			Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
			if (menuKeyField != null) {
				menuKeyField.setAccessible(true);
				menuKeyField.setBoolean(config, false);
			}
		}
		catch (Exception e) { }
	}

	/**
	 * Checks whether the given Dialog instance is still showing up and closes it if necessary
	 * @param dialog Dialog instance to close
	 */
	public static void closeDialog(DialogInterface dialog) {
		if (dialog != null) {
			if (dialog instanceof Dialog) {
				if (((Dialog) dialog).isShowing()) {
					dialog.dismiss();
				}
			}
			else {
				dialog.dismiss();
			}
		}
	}

	/**
	 * Restarts the given Activity
	 * @param activity Activity instance to restart (e.g. MyActivity.this)
	 */
	public static void restartActivity(Activity activity) {
		Intent restart = new Intent(activity, activity.getClass());
		restart.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		activity.finish();
		activity.overridePendingTransition(0, 0);
		activity.startActivity(restart);
		activity.overridePendingTransition(0, 0);
	}

	/**
	 * Either shows or hides the software keyboard
	 * @param context Context reference to get the InputMethodManager from
	 * @param view the View to show/hide the software keyboard for
	 * @param visible whether to show (true) or hide (false) the keyboard
	 */
	public static void setKeyboardVisibility(final Context context, final View view, final boolean visible) {
		if (visible) {
			view.requestFocus();
		}
		else {
			view.clearFocus();
		}
		try {
			view.postDelayed(new Runnable() {

				@Override
				public void run() {
					android.view.inputmethod.InputMethodManager keyboard = (android.view.inputmethod.InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
					if (keyboard != null) {
						if (visible) {
							keyboard.showSoftInput(view, 0);
						}
						else {
							keyboard.hideSoftInputFromWindow(view.getWindowToken(), 0);
						}
					}
				}

			}, 150);
		}
		catch (Exception e) { }
	}

	/**
	 * Inserts the given text into the given EditText view at the current cursor position or replacing the current selection
	 * @param editText the EditText view to insert the text into
	 * @param textToInsert the text to insert
	 */
	public static void insertTextAtCursorPosition(final EditText editText, final String textToInsert) {
		final int start = Math.max(editText.getSelectionStart(), 0); // may return -1 which would cause an "out of bounds" exception
		final int end = Math.max(editText.getSelectionEnd(), 0); // may return -1 which would cause an "out of bounds" exception
		final int startNormalized = Math.min(start, end); // selecting text backwards causes start and end to be in the "wrong" order for us
		final int endNormalized = Math.max(start, end); // selecting text backwards causes start and end to be in the "wrong" order for us
		editText.getText().replace(startNormalized, endNormalized, textToInsert, 0, textToInsert.length()); // insert text at current cursor position or by replacing the current selection
	}

	/**
	 * Replaces the given text with the given image (as a spannable) in the given EditText
	 * @param editText the EditText view to operate on
	 * @param searchTexts the texts to replace
	 * @param replacementImages the resource IDs of the images to insert
	 * @param context the Context instance to use
	 */
	public static void replaceTextsWithImages(final Context context, final EditText editText, final String[] searchTexts, final int[] replacementImages) {
		if (searchTexts.length != replacementImages.length) {
			throw new RuntimeException("Number of search texts must match the number of replacement images");
		}
		final int oldCursorPosition = editText.getSelectionStart();
		final Factory spannableFactory = Spannable.Factory.getInstance();
		final Spannable spannable = spannableFactory.newSpannable(editText.getText().toString());

		for (int i = 0; i < searchTexts.length; i++) {
			final Pattern pattern = Pattern.compile(Pattern.quote(searchTexts[i]));
			final Matcher matcher = pattern.matcher(spannable);
			while (matcher.find()) {
				boolean set = true;
				for (ImageSpan span : spannable.getSpans(matcher.start(), matcher.end(), ImageSpan.class)) {
					if (spannable.getSpanStart(span) >= matcher.start() && spannable.getSpanEnd(span) <= matcher.end()) {
						spannable.removeSpan(span);
					}
					else {
						set = false;
						break;
					}
				}
				if (set) {
					spannable.setSpan(new ImageSpan(context, replacementImages[i]), matcher.start(), matcher.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				}
			}
		}

		editText.setText(spannable);
		if (oldCursorPosition >= 0) {
			editText.setSelection(oldCursorPosition);
		}
	}

    public static void setDatePickerYearVisible(final DatePicker picker, final boolean yearVisible) {
    	try {
	    	Field f[] = picker.getClass().getDeclaredFields();
	    	for (Field field : f) {
		    	if (field.getName().equals("mYearPicker") || field.getName().equals("mYearSpinner")) {
			    	field.setAccessible(true);
			    	Object yearPicker = new Object();
			    	yearPicker = field.get(picker);
			    	((View) yearPicker).setVisibility(yearVisible ? View.VISIBLE : View.GONE);
		    	}
	    	}
    	}
    	catch (Exception e) { }
    }

}
