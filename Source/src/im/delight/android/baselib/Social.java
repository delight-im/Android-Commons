package im.delight.android.baselib;

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

import org.apache.http.protocol.HTTP;
import java.util.Locale;
import java.io.File;
import android.telephony.PhoneNumberUtils;
import android.os.Build;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

public class Social {

	/** This class may not be instantiated */
	private Social() { }

	/**
	 * Constructs an Intent for sharing/sending plain text and starts Activity chooser for that Intent
	 *
	 * @param context Context reference to start the Activity chooser from
	 * @param windowTitle the string to be used as the window title for the Activity chooser
	 * @param messageText the body text for the message to be shared
	 */
	public static void shareText(Context context, String windowTitle, String messageText) {
		shareText(context, windowTitle, messageText, "");
	}

	/**
	 * Constructs an Intent for sharing/sending plain text and starts Activity chooser for that Intent
	 *
	 * @param context Context reference to start the Activity chooser from
	 * @param windowTitle the string to be used as the window title for the Activity chooser
	 * @param messageText the body text for the message to be shared
	 * @param messageTitle the title/subject for the message to be shared, if supported by the target app
	 */
	public static void shareText(Context context, String windowTitle, String messageText, String messageTitle) {
		Intent intentInvite = new Intent(Intent.ACTION_SEND);
		intentInvite.setType(HTTP.PLAIN_TEXT_TYPE);
		intentInvite.putExtra(Intent.EXTRA_SUBJECT, messageTitle);
		intentInvite.putExtra(Intent.EXTRA_TEXT, messageText);
		context.startActivity(Intent.createChooser(intentInvite, windowTitle));
	}

	/**
	 * Constructs an Intent for sharing/sending a file and starts Activity chooser for that Intent
	 *
	 * @param context Context reference to start the Activity chooser from
	 * @param windowTitle the string to be used as the window title for the Activity chooser
	 * @param file the File instance to be shared
	 * @param mimeType the MIME type for the file to be shared (e.g. image/jpeg)
	 */
	public static void shareFile(Context context, String windowTitle, File file, final String mimeType) {
		shareFile(context, windowTitle, file, mimeType, "");
	}

	/**
	 * Constructs an Intent for sharing/sending a file and starts Activity chooser for that Intent
	 *
	 * @param context Context reference to start the Activity chooser from
	 * @param windowTitle the string to be used as the window title for the Activity chooser
	 * @param file the File instance to be shared
	 * @param mimeType the MIME type for the file to be shared (e.g. image/jpeg)
	 * @param messageTitle the message title/subject that may be provided in addition to the file, if supported by the target app
	 */
	public static void shareFile(Context context, String windowTitle, File file, final String mimeType, String messageTitle) {
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_SEND);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setType(mimeType);
		intent.putExtra(Intent.EXTRA_SUBJECT, messageTitle);
		intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
		context.startActivity(Intent.createChooser(intent, windowTitle));
	}

	/**
	 * Opens the given user's Facebook profile
	 *
	 * @param context Context instance to get the PackageManager from
	 * @param facebookID the user's Facebook ID
	 */
	public static void openFacebookProfile(Context context, String facebookID) {
		Intent intent;
		try {
			context.getPackageManager().getPackageInfo("com.facebook.katana", 0); // throws exception if not installed
			intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://profile/"+facebookID));
		}
		catch (Exception e) {
			intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/"+facebookID));
		}
		try {
			context.startActivity(intent);
		}
		catch (Exception e) { }
	}

	/**
	 * Constructs an email Intent for the given message details and opens the application choooser for this Intent
	 *
	 * @param recipient the recipient's email address
	 * @param subject the subject of the message
	 * @param body the body text as a string
	 * @param captionRes the string resource ID for the application chooser's window title
	 * @param context the Context instance to start the Intent from
	 * @throws Exception if there was an error trying to launch the email Intent
	 */
	public static void sendMail(final String recipient, final String subject, final String body, final int captionRes, final Context context) throws Exception {
		sendMail(recipient, subject, body, captionRes, null, context);
	}

	/**
	 * Constructs an email Intent for the given message details and opens the application choooser for this Intent
	 *
	 * @param recipient the recipient's email address
	 * @param subject the subject of the message
	 * @param body the body text as a string
	 * @param captionRes the string resource ID for the application chooser's window title
	 * @param restrictToPackage an optional package name that the Intent may be restricted to (or null)
	 * @param context the Context instance to start the Intent from
	 * @throws Exception if there was an error trying to launch the email Intent
	 */
	public static void sendMail(final String recipient, final String subject, final String body, final int captionRes, final String restrictToPackage, final Context context) throws Exception {
		final String uriString = "mailto:"+Uri.encode(recipient)+"?subject="+Uri.encode(subject)+"&body="+Uri.encode(body);
		final Uri uri = Uri.parse(uriString);
		final Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
		emailIntent.setData(uri);
		if (restrictToPackage != null && restrictToPackage.length() > 0) {
			emailIntent.setPackage(restrictToPackage);
			if (context != null) {
				// launch the target app directly
				context.startActivity(emailIntent);
			}
		}
		else {
			if (context != null) {
				// offer a selection of all applications that can handle the email Intent
				context.startActivity(Intent.createChooser(emailIntent, context.getString(captionRes)));
			}
		}
	}

	/**
	 * Constructs an SMS Intent for the given message details and opens the application chooser for this Intent
	 *
	 * @param recipient the recipient's phone number or `null`
	 * @param body the body of the message
	 * @param captionRes the string resource ID for the application chooser's window title
	 * @param context the Context instance to start the Intent from
	 * @throws Exception if there was an error trying to launch the SMS Intent
	 */
	public static void sendSMS(final String recipient, final String body, final int captionRes, final Context context) throws Exception {
		final Intent intent = new Intent(Intent.ACTION_SENDTO);
		intent.setType(HTTP.PLAIN_TEXT_TYPE);
		if (recipient != null && recipient.length() > 0) {
			intent.setData(Uri.parse("smsto:"+recipient));
		}
		else {
			intent.setData(Uri.parse("sms:"));
		}
		intent.putExtra("sms_body", body);
		intent.putExtra(Intent.EXTRA_TEXT, body);
		if (context != null) {
			// offer a selection of all applications that can handle the SMS Intent
			context.startActivity(Intent.createChooser(intent, context.getString(captionRes)));
		}
	}

	@SuppressLint("NewApi")
	public static String normalizePhoneNumber(final Context context, final String phoneNumber) {
		if (Build.VERSION.SDK_INT >= 21) {
			final String countryIso2 = DeviceInfo.getCountryISO2(context);
			if (countryIso2 != null) {
				final String formatted = PhoneNumberUtils.formatNumberToE164(phoneNumber, countryIso2.toUpperCase(Locale.US));
				if (formatted != null) {
					return formatted;
				}
			}
		}

		return phoneNumber;
	}

	/**
	 * Returns a list of phone numbers for the contact with the given lookup ID
	 *
	 * @param lookupID the lookup ID to get the phone numbers for
	 * @param context Context instance to get the ContentResolver from
	 * @return CharSequence[] containing all phone numbers for the given contact
	 */
	public static CharSequence[] getContactPhone(String lookupID, Context context) {
		Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
		String[] projection = new String[] { ContactsContract.CommonDataKinds.Phone.NUMBER };
		String where = ContactsContract.Contacts.LOOKUP_KEY +" = ?";
		String[] selectionArgs = new String[] { lookupID };
		String sortOrder = null;
		Cursor result = context.getContentResolver().query(uri, projection, where, selectionArgs, sortOrder);
		String phone;
		if (result != null) {
			if (result.getCount() > 0) {
				CharSequence[] res = new CharSequence[result.getCount()];
				int i = 0;
				while (result.moveToNext()) {
					phone = result.getString(result.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
					if (phone != null) {
						res[i] = phone;
						i++;
					}
				}
				result.close();
				return res;
			}
			else {
				result.close();
				return null;
			}
		}
		else {
			return null;
		}
	}

	/**
	 * Returns a list of email addresses for the contact with the given lookup ID
	 *
	 * @param lookupID the lookup ID to get the email addresses for
	 * @param context Context instance to get the ContentResolver from
	 * @return CharSequence[] containing all email addresses for the given contact
	 */
	public static CharSequence[] getContactEmail(String lookupID, Context context) {
		Uri uri = ContactsContract.CommonDataKinds.Email.CONTENT_URI;
		String[] projection = new String[] { ContactsContract.CommonDataKinds.Email.DATA };
		String where = ContactsContract.Contacts.LOOKUP_KEY +" = ?";
		String[] selectionArgs = new String[] { lookupID };
		String sortOrder = null;
		Cursor result = context.getContentResolver().query(uri, projection, where, selectionArgs, sortOrder);
		String email;
		if (result != null) {
			if (result.getCount() > 0) {
				CharSequence[] res = new CharSequence[result.getCount()];
				int i = 0;
				while (result.moveToNext()) {
					email = result.getString(result.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
					if (email != null) {
						res[i] = email;
						i++;
					}
				}
				result.close();
				return res;
			}
			else {
				result.close();
				return null;
			}
		}
		else {
			return null;
		}
	}

	/**
	 * Whether the given person (represented by phone number) is known on the current device (i.e. in the address book) or not
	 *
	 * @param context the Context reference to get the ContentResolver from
	 * @param phoneNumber the phone number to look up
	 * @return whether the phone number is in the contacts list (true) or not (false)
	 */
	public static boolean isPersonKnown(Context context, String phoneNumber) {
		try {
			Uri phoneUri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phoneNumber));
			Cursor phoneEntries = context.getContentResolver().query(phoneUri, new String[] { android.provider.ContactsContract.PhoneLookup.DISPLAY_NAME }, null, null, null);
			return phoneEntries.getCount() > 0;
		}
		catch (Exception e) {
			return false;
		}
	}

}
