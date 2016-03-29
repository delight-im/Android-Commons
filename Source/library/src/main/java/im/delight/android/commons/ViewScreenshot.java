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

import im.delight.android.commons.UI;
import java.io.File;
import java.io.FileOutputStream;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;

/**
 * Takes a screenshot from any given `android.view.View` instance and stores it in a file on the external storage
 *
 * The external storage must be mounted and writable.
 *
 * Before Android 4.4, this requires the following permission in your `AndroidManifest.xml`:
 *
 * `<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />`
 *
 * Note that you may not use this class before `onWindowFocusChanged` has been called in your `Activity`
 *
 * Usage:
 *
 * `new ViewScreenshot(activity, new ViewScreenshot.Callback() {}).from(view).asFile(string).build()`
 */
public final class ViewScreenshot {

	public static final int FORMAT_JPEG = 1;
	public static final int FORMAT_PNG = 2;
	private static final String NO_MEDIA_FILENAME = ".nomedia";
	private final Activity mActivity;
	private final Callback mCallback;
	private View mView;
	private String mFilename;
	private int mFormat;

	public static interface Callback {
		public void onSuccess(File file);
		public void onError();
	}

	/**
	 * Prepares a new screenshot helper for the given `Activity`
	 *
	 * @param activity the `Activity` instance
	 * @param callback the callback to notify on success or error
	 */
	public ViewScreenshot(final Activity activity, final Callback callback) {
		if (activity == null) {
			throw new RuntimeException("activity must not be null");
		}

		if (callback == null) {
			throw new RuntimeException("callback must not be null");
		}

		mActivity = activity;
		mCallback = callback;
		mView = null;
		mFilename = null;
		mFormat = FORMAT_PNG;
	}

	/**
	 * Specifies the view to take the screenshot of
	 *
	 * @param view the `View` to take the screenshot of
	 * @return this instance for chaining
	 */
	public ViewScreenshot from(final View view) {
		if (view == null) {
			throw new RuntimeException("view must not be null");
		}

		mView = view;

		return this;
	}

	/**
	 * Specifies the filename to save the screenshot under
	 *
	 * @param filenameWithoutExtension the filename without any file extension
	 * @return this instance for chaining
	 */
	public ViewScreenshot asFile(final String filenameWithoutExtension) {
		if (filenameWithoutExtension == null || filenameWithoutExtension.length() == 0) {
			throw new RuntimeException("filename must not be null or empty");
		}

		mFilename = filenameWithoutExtension;

		return this;
	}

	/**
	 * Specifies the format to save the screenshot in
	 *
	 * @param format the format, either `ViewScreenshot.FORMAT_JPEG` or `ViewScreenshot.FORMAT_PNG`
	 * @return this instance for chaining
	 */
	public ViewScreenshot inFormat(final int format) {
		if (format != FORMAT_JPEG && format != FORMAT_PNG) {
			throw new RuntimeException("format must be either FORMAT_JPEG or FORMAT_PNG");
		}

		mFormat = format;

		return this;
	}

	/**
	 * Builds and saves the screenshot
	 */
	public void build() {
		// get the screenshot
		final Bitmap viewScreenshot = UI.getViewScreenshot(mView);

		new Thread() {

			@Override
			public void run() {
				if (mView == null) {
					throw new RuntimeException("You must call from(...) before calling build(...)");
				}
				if (mFilename == null) {
					throw new RuntimeException("You must call asFile(...) before calling build(...)");
				}

				// prepare sharing of the screenshot
				try {
					// save screenshot (bitmap) to publicly accessible storage
					final File screenshotFile = saveBitmapToPublicStorage(mActivity, mFilename, viewScreenshot, mFormat);

					// share the saved file
					mActivity.runOnUiThread(new Runnable() {

						@Override
						public void run() {
							// if the file could not be saved
							if (screenshotFile == null) {
								mCallback.onError();
							}
							// if the file has successfully been saved
							else {
								mCallback.onSuccess(screenshotFile);
							}
						}

					});
				}
				catch (Exception e) {
					// file could not be saved
					mActivity.runOnUiThread(new Runnable() {

						@Override
						public void run() {
							mCallback.onError();
						}

					});
				}
			}

		}.start();
	}

	private static File saveBitmapToPublicStorage(final Context context, final String filenameWithoutExtension, final Bitmap bitmap, final int format) throws Exception {
		// get the output directory
		final File applicationDir = context.getExternalFilesDir(null);
		final File libraryDir = new File(applicationDir, "im.delight.android.commons");
		final File outputDir = new File(libraryDir, "screenshots");

		// create the output directory if it doesn't exist
		outputDir.mkdirs();

		// create the .nomedia file which prevents images from showing up in gallery
		try {
			final File noMedia = new File(outputDir, NO_MEDIA_FILENAME);
			noMedia.createNewFile();
		}
		// ignore if the file does already exist or cannot be created
		catch (Exception e) { }

		// set up variables for file format
		final Bitmap.CompressFormat bitmapFormat;
		final String fileExtension;
		if (format == FORMAT_JPEG) {
			bitmapFormat = Bitmap.CompressFormat.JPEG;
			fileExtension = ".jpg";
		}
		else if (format == FORMAT_PNG) {
			bitmapFormat = Bitmap.CompressFormat.PNG;
			fileExtension = ".png";
		}
		else {
			throw new Exception("Unknown format: "+format);
		}

		// get a reference to the new file
		final File outputFile = new File(outputDir, filenameWithoutExtension + fileExtension);
		// if the output file already exists
		if (outputFile.exists()) {
			// delete it first
			outputFile.delete();
		}
		// create an output stream for the new file
		final FileOutputStream outputStream = new FileOutputStream(outputFile);
		// write the data to the new file
		bitmap.compress(bitmapFormat, 90, outputStream);
		// flush the output stream
		outputStream.flush();
		// close the output stream
		outputStream.close();

		// return the file reference
		return outputFile;
	}

}
