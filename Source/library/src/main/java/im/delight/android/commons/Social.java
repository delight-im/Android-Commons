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

import org.apache.http.protocol.HTTP;
import java.util.Locale;
import java.io.File;
import android.content.ActivityNotFoundException;
import android.telephony.PhoneNumberUtils;
import android.os.Build;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

/** Utilities for working with social features, communication and content sharing */
public final class Social {

	/** This class may not be instantiated */
	private Social() { }

	/**
	 * Displays an application chooser and shares the specified plain text using the selected application
	 *
	 * @param context a context reference
	 * @param windowTitle the title for the application chooser's window
	 * @param plainTextToShare the plain text to be shared
	 */
	public static void shareText(final Context context, final String windowTitle, final String plainTextToShare) {
		shareText(context, windowTitle, plainTextToShare, "");
	}

	/**
	 * Displays an application chooser and shares the specified plain text and subject line using the selected application
	 *
	 * @param context a context reference
	 * @param windowTitle the title for the application chooser's window
	 * @param bodyTextToShare the body text to be shared
	 * @param subjectTextToShare the title or subject for the message to be shared, if supported by the target application
	 */
	public static void shareText(final Context context, final String windowTitle, final String bodyTextToShare, final String subjectTextToShare) {
		final Intent intentInvite = new Intent(Intent.ACTION_SEND);
		intentInvite.setType(HTTP.PLAIN_TEXT_TYPE);
		intentInvite.putExtra(Intent.EXTRA_SUBJECT, subjectTextToShare);
		intentInvite.putExtra(Intent.EXTRA_TEXT, bodyTextToShare);

		context.startActivity(Intent.createChooser(intentInvite, windowTitle));
	}

	/**
	 * Displays an application chooser and shares the specified file using the selected application
	 *
	 * @param context a context reference
	 * @param windowTitle the title for the application chooser's window
	 * @param fileToShare the file to be shared
	 * @param mimeTypeForFile the MIME type for the file to be shared (e.g. `image/jpeg`)
	 */
	public static void shareFile(final Context context, final String windowTitle, final File fileToShare, final String mimeTypeForFile) {
		shareFile(context, windowTitle, fileToShare, mimeTypeForFile, "");
	}

	/**
	 * Displays an application chooser and shares the specified file using the selected application
	 *
	 * @param context a context reference
	 * @param windowTitle the title for the application chooser's window
	 * @param fileToShare the file to be shared
	 * @param mimeTypeForFile the MIME type for the file to be shared (e.g. `image/jpeg`)
	 * @param subjectTextToShare the message title or subject for the file, if supported by the target application
	 */
	public static void shareFile(final Context context, final String windowTitle, final File fileToShare, final String mimeTypeForFile, final String subjectTextToShare) {
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_SEND);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setType(mimeTypeForFile);
		intent.putExtra(Intent.EXTRA_SUBJECT, subjectTextToShare);
		intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(fileToShare));
		context.startActivity(Intent.createChooser(intent, windowTitle));
	}

	/**
	 * Opens the specified user's Facebook profile, either in the app or on the web
	 *
	 * @param context a context reference
	 * @param facebookId the user's Facebook ID or profile name
	 */
	public static void openFacebookProfile(final Context context, final String facebookId) {
		try {
			context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/" + facebookId)));
		}
		catch (ActivityNotFoundException ignored) {}
	}

	/**
	 * Displays an application chooser and composes the described email using the selected application
	 *
	 * @param recipientEmail the recipient's email address
	 * @param subjectText the subject line of the message
	 * @param bodyText the body text of the message
	 * @param captionRes a string resource ID for the title of the application chooser's window
	 * @param context a context reference
	 * @throws Exception if there was an error trying to launch the email application
	 */
	public static void sendMail(final String recipientEmail, final String subjectText, final String bodyText, final int captionRes, final Context context) throws Exception {
		sendMail(recipientEmail, subjectText, bodyText, captionRes, null, context);
	}

	/**
	 * Displays an application chooser and composes the described email using the selected application
	 *
	 * @param recipientEmail the recipient's email address
	 * @param subjectText the subject line of the message
	 * @param bodyText the body text of the message
	 * @param captionRes a string resource ID for the title of the application chooser's window
	 * @param restrictToPackage an application's package name to restricted the selection to
	 * @param context a context reference
	 * @throws Exception if there was an error trying to launch the email application
	 */
	public static void sendMail(final String recipientEmail, final String subjectText, final String bodyText, final int captionRes, final String restrictToPackage, final Context context) throws Exception {
		final String uriString = "mailto:"+Uri.encode(recipientEmail)+"?subject="+Uri.encode(subjectText)+"&body="+Uri.encode(bodyText);
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
	 * Displays an application chooser and composes the described text message (SMS) using the selected application
	 *
	 * @param recipientPhone the recipient's phone number or `null`
	 * @param bodyText the body text of the message
	 * @param captionRes a string resource ID for the title of the application chooser's window
	 * @param context a context reference
	 * @throws Exception if there was an error trying to launch the SMS application
	 */
	public static void sendSms(final String recipientPhone, final String bodyText, final int captionRes, final Context context) throws Exception {
		final Intent intent = new Intent(Intent.ACTION_SENDTO);
		intent.setType(HTTP.PLAIN_TEXT_TYPE);

		if (recipientPhone != null && recipientPhone.length() > 0) {
			intent.setData(Uri.parse("smsto:"+recipientPhone));
		}
		else {
			intent.setData(Uri.parse("sms:"));
		}

		intent.putExtra("sms_body", bodyText);
		intent.putExtra(Intent.EXTRA_TEXT, bodyText);

		if (context != null) {
			// offer a selection of all applications that can handle the SMS Intent
			context.startActivity(Intent.createChooser(intent, context.getString(captionRes)));
		}
	}

	/**
	 * Normalizes the specified phone number to its E.164 representation, if supported by the platform
	 *
	 * @param context a context reference
	 * @param phoneNumber the phone number to normalize
	 * @return the normalized phone number
	 */
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
	 * @param contactLookupId a contact's lookup ID to get the phone numbers for
	 * @param context a context reference
	 * @return CharSequence[] a list of all phone numbers for the given contact or `null`
	 */
	public static CharSequence[] getContactPhone(final String contactLookupId, final Context context) {
		final Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
		final String[] projection = new String[] { ContactsContract.CommonDataKinds.Phone.NUMBER };
		final String where = ContactsContract.Contacts.LOOKUP_KEY +" = ?";
		final String[] selectionArgs = new String[] { contactLookupId };
		final String sortOrder = null;

		Cursor result = context.getContentResolver().query(uri, projection, where, selectionArgs, sortOrder);

		String phone;
		if (result != null) {
			if (result.getCount() > 0) {
				final CharSequence[] res = new CharSequence[result.getCount()];

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
	 * @param contactLookupId a contact's lookup ID to get the email addresses for
	 * @param context a context reference
	 * @return CharSequence[] a list of all email addresses for the given contact or `null`
	 */
	public static CharSequence[] getContactEmail(final String contactLookupId, final Context context) {
		final Uri uri = ContactsContract.CommonDataKinds.Email.CONTENT_URI;
		final String[] projection = new String[] { ContactsContract.CommonDataKinds.Email.DATA };
		final String where = ContactsContract.Contacts.LOOKUP_KEY +" = ?";
		final String[] selectionArgs = new String[] { contactLookupId };
		final String sortOrder = null;

		Cursor result = context.getContentResolver().query(uri, projection, where, selectionArgs, sortOrder);

		String email;
		if (result != null) {
			if (result.getCount() > 0) {
				final CharSequence[] res = new CharSequence[result.getCount()];

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
	 * Whether the specified person is known on the current device or not
	 *
	 * @param context a context reference
	 * @param phoneNumber the phone number to look up
	 * @return whether the phone number is in the local address book or not
	 */
	public static boolean isPersonKnown(final Context context, final String phoneNumber) {
		try {
			final Uri phoneUri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phoneNumber));
			final Cursor phoneEntries = context.getContentResolver().query(phoneUri, new String[] { android.provider.ContactsContract.PhoneLookup.DISPLAY_NAME }, null, null, null);

			return phoneEntries.getCount() > 0;
		}
		catch (Exception e) {
			return false;
		}
	}

}
