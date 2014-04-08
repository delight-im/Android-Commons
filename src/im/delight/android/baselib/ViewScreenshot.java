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

import im.delight.android.baselib.UI;
import java.io.File;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;

/**
 * Takes a screenshot from a given View and optionally saves it to a (public) file on the internal storage
 * <p>
 * Usage:
 * <p>
 * ViewScreenshot(activity).from(view).asFile(string).build();
 */
public class ViewScreenshot {
	
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

	public ViewScreenshot(Activity activity, Callback callback) {
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
	
	public ViewScreenshot from(View view) {
		if (view == null) {
			throw new RuntimeException("view must not be null");
		}
		mView = view;
		return this;
	}
	
	public ViewScreenshot asFile(String filename) {
		if (filename == null || filename.length() == 0) {
			throw new RuntimeException("filename must not be null or empty");
		}
		mFilename = filename;
		return this;
	}
	
	public ViewScreenshot inFormat(int format) {
		if (format != FORMAT_JPEG && format != FORMAT_PNG) {
			throw new RuntimeException("format must be either FORMAT_JPEG or FORMAT_PNG");
		}
		mFormat = format;
		return this;
	}
	
	public void build() {
		// get the screenshot
		final Bitmap viewScreenshot = UI.getViewScreenshot(mView);
		
		new Thread() {
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
						public void run() {
							mCallback.onError();
						}
					});
				}
			}
		}.start();
	}

	@SuppressLint("WorldReadableFiles")
	private static File saveBitmapToPublicStorage(Context context, String filenameWithoutExtension, Bitmap bitmap, int format) throws Exception {
		// get the output directory
		final File outputDir = context.getFilesDir();
		
		// create the .nomedia file which prevents images from showing up in gallery
		try {
			File noMedia = new File(outputDir, NO_MEDIA_FILENAME);
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
		
		// generate the full filename for the file to be written
		final String outputFileName = filenameWithoutExtension+fileExtension;
		// create a file object for the new file
		final File outputFile = new File(outputDir, outputFileName);
		// write the data to the new file
		bitmap.compress(bitmapFormat, 90, context.openFileOutput(outputFileName, Context.MODE_WORLD_READABLE));
		
		// return the file reference
		return outputFile;
	}

}
