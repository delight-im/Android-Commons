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

import android.graphics.Color;
import android.app.Dialog;
import android.content.Context;
import android.view.ViewGroup.LayoutParams;
import android.widget.ProgressBar;

/**
 * Effective progress dialog that grays out the `Activity` behind it and displays an indeterminate spinner
 *
 * Showing the dialog:
 *
 * `SimpleProgressDialog progressDialog = SimpleProgressDialog.show(this);`
 *
 * Hiding the dialog again:
 *
 * `progressDialog.dismiss();`
 */
public final class SimpleProgressDialog extends Dialog {

	private SimpleProgressDialog(final Context context) {
		super(context, android.R.style.Theme_Dialog);

		getWindow().getDecorView().setBackgroundColor(Color.TRANSPARENT);
	}

	/**
	 * Creates and displays a new progress dialog
	 *
	 * @param context a context reference
	 * @return the dialog instance that can later be cancelled by calling `dismiss`
	 */
	public static SimpleProgressDialog show(final Context context) {
		final SimpleProgressDialog dialog = new SimpleProgressDialog(context);

		dialog.setTitle(null);
		dialog.setCancelable(false);
		dialog.setOnCancelListener(null);

		dialog.addContentView(new ProgressBar(context), new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		dialog.show();

		return dialog;
	}

}
