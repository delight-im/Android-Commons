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

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;

/**
 * EditText view of which the value is set by choosing from a list
 * <p>
 * Besides the displayed value, this view has an additional internal value which can be accessed via getValue()
 * <p>
 * Just add this view to XML as you would do with a normal EditText, but use im.delight.helpers.ListEditText instead of EditText
 * <p>
 * In the Activity's onCreate(...), you must get a reference to this view and call init(...)
 * <p>
 * When manipulating the text of this view, use setValue(...) and getValue() instead of setText(...) and getText()
 */
public class ListEditText extends EditText {

	protected String[] mValuesHuman;
	protected String[] mValuesMachine;
	protected int mTitleRes;
	protected int mCancelRes;
	protected Context mContext;
	protected String mValue;
	protected boolean mShowInternalValues;
	protected OnChangeListener mCallback;

	public ListEditText(Context context) {
		super(context);
		initView();
	}

	public ListEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	public ListEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView();
	}

	public void setCallback(OnChangeListener callback) {
		mCallback = callback;
	}

	protected void initView() {
		mValue = "";
		getBackground().setColorFilter(Color.rgb(205, 205, 205), PorterDuff.Mode.MULTIPLY); // change color of EditText without overwriting the native look
		setInputType(InputType.TYPE_NULL);
		setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				openSelection();
			}
		});
		clearFocus();
		setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					openSelection();
				}
			}
		});
	}

	/**
	 * Initializes this view so that the user can interact with it
	 * @param context Context reference from the activity (this)
	 * @param valuesHuman String array containing the values to display in the selection
	 * @param valuesMachine String array containing the internal representations of the displayed values
	 * @param titleRes String resource ID for the title of the selection dialog
	 * @param cancelRes String resource ID for the cancel button
	 */
	public void init(Context context, String[] valuesHuman, String[] valuesMachine, int titleRes, int cancelRes) {
		init(context, valuesHuman, valuesMachine, titleRes, cancelRes, false);
	}

	/**
	 * Initializes this view so that the user can interact with it
	 * @param context Context reference from the activity (this)
	 * @param valuesHuman String array containing the values to display in the selection
	 * @param valuesMachine String array containing the internal representations of the displayed values
	 * @param titleRes String resource ID for the title of the selection dialog
	 * @param cancelRes String resource ID for the cancel button
	 * @param showInternalValues whether to show the internal values instead of the labels in the text field (true) or not (false)
	 */
	public void init(Context context, String[] valuesHuman, String[] valuesMachine, int titleRes, int cancelRes, boolean showInternalValues) {
		if (valuesHuman == null || valuesMachine == null) {
			throw new RuntimeException("Neither valuesHuman nor valuesMachine may be null");
		}
		else if (valuesHuman.length == 0 || valuesMachine.length == 0) {
			throw new RuntimeException("Neither valuesHuman nor valuesMachine may be empty");
		}
		else if (valuesHuman.length != valuesMachine.length) {
			throw new RuntimeException("Sizes of valuesHuman and valuesMachine must match");
		}
		else if (titleRes <= 0) {
			throw new RuntimeException("You must pass a valid String resource ID for titleRes");
		}
		else if (context == null) {
			throw new RuntimeException("You must provide a valid Context instance");
		}
		else {
			mContext = context;
			mValuesHuman = valuesHuman;
			mValuesMachine = valuesMachine;
			mTitleRes = titleRes;
			mCancelRes = cancelRes;
			mShowInternalValues = showInternalValues;
		}
	}

	protected void openSelection() {
		if (mValuesHuman == null || mValuesMachine == null || mTitleRes == 0 || mCancelRes == 0 || mContext == null) {
			throw new RuntimeException("You must call setData(...) on this ListEditText before it can be used");
		}
		else {
			AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
			builder.setTitle(mTitleRes);
			builder.setItems(mValuesHuman, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					if (which < mValuesMachine.length) {
						setValue(mValuesMachine[which]);
						if (mCallback != null) {
							mCallback.onValueChanged(mValuesMachine[which]);
						}
					}
				}
			});
			builder.setNeutralButton(mCancelRes, null);
			builder.show();
		}
	}

	/**
	 * Returns the internal value of this view (not the displayed value which can be accessed with `getText().toString()`)
	 *
	 * @return the internal value of this input field
	 */
	public String getValue() {
		return mValue;
	}

	/**
	 * Sets the internal value of this view along with the displayed value (which is retrieved automatically)
	 *
	 * @param value the internal value to set/select
	 */
	public void setValue(final String value) {
		int displayTextIndex = Collections.arrayIndexOf(mValuesMachine, value);
		if (displayTextIndex >= 0) {
			mValue = value;
			if (mShowInternalValues) {
				setText(value);
			}
			else {
				setText(mValuesHuman[displayTextIndex]);
			}
		}
		else {
			mValue = "";
			setText("");
		}
	}

	public static interface OnChangeListener {
		public void onValueChanged(String value);
	}

}
