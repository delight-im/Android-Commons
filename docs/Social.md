# Social

## `private Social()`

This class may not be instantiated

## `public static void shareText(Context context, String windowTitle, String messageText)`

Constructs an Intent for sharing/sending plain text and starts Activity chooser for that Intent

 * **Parameters:**
   * `context` — Context reference to start the Activity chooser from
   * `windowTitle` — the string to be used as the window title for the Activity chooser
   * `messageText` — the body text for the message to be shared

## `public static void shareText(Context context, String windowTitle, String messageText, String messageTitle)`

Constructs an Intent for sharing/sending plain text and starts Activity chooser for that Intent

 * **Parameters:**
   * `context` — Context reference to start the Activity chooser from
   * `windowTitle` — the string to be used as the window title for the Activity chooser
   * `messageText` — the body text for the message to be shared
   * `messageTitle` — the title/subject for the message to be shared, if supported by the target app

## `public static void shareFile(Context context, String windowTitle, File file, final String mimeType)`

Constructs an Intent for sharing/sending a file and starts Activity chooser for that Intent

 * **Parameters:**
   * `context` — Context reference to start the Activity chooser from
   * `windowTitle` — the string to be used as the window title for the Activity chooser
   * `file` — the File instance to be shared
   * `mimeType` — the MIME type for the file to be shared (e.g. image/jpeg)

## `public static void shareFile(Context context, String windowTitle, File file, final String mimeType, String messageTitle)`

Constructs an Intent for sharing/sending a file and starts Activity chooser for that Intent

 * **Parameters:**
   * `context` — Context reference to start the Activity chooser from
   * `windowTitle` — the string to be used as the window title for the Activity chooser
   * `file` — the File instance to be shared
   * `mimeType` — the MIME type for the file to be shared (e.g. image/jpeg)
   * `messageTitle` — the message title/subject that may be provided in addition to the file, if supported by the target app

## `public static void openFacebookProfile(Context context, String facebookID)`

Opens the given user's Facebook profile

 * **Parameters:**
   * `context` — Context instance to get the PackageManager from
   * `facebookID` — the user's Facebook ID

## `public static void sendMail(final String recipient, final String subject, final String body, final int captionRes, final Context context) throws Exception`

Constructs an email Intent for the given message details and opens the application choooser for this Intent

 * **Parameters:**
   * `recipient` — the recipient's email address
   * `subject` — the subject of the message
   * `captionRes` — the string resource ID for the application chooser's window title
   * `context` — the Context instance to start the Intent from
 * **Exceptions:** `Exception` — if there was an error trying to launch the email Intent

## `public static void sendMail(final String recipient, final String subject, final String body, final int captionRes, final String restrictToPackage, final Context context) throws Exception`

Constructs an email Intent for the given message details and opens the application choooser for this Intent

 * **Parameters:**
   * `recipient` — the recipient's email address
   * `subject` — the subject of the message
   * `captionRes` — the string resource ID for the application chooser's window title
   * `restrictToPackage` — an optional package name that the Intent may be restricted to (or null)
   * `context` — the Context instance to start the Intent from
 * **Exceptions:** `Exception` — if there was an error trying to launch the email Intent

## `public static void sendSMS(final String recipient, final String body, final int captionRes, final Context context) throws Exception`

Constructs an SMS Intent for the given message details and opens the application chooser for this Intent

 * **Parameters:**
   * `recipient` — the recipient's phone number
   * `body` — the body of the message
   * `captionRes` — the string resource ID for the application chooser's window title
   * `context` — the Context instance to start the Intent from
 * **Exceptions:** `Exception` — if there was an error trying to launch the SMS Intent

## `public static CharSequence[] getContactPhone(String lookupID, Context context)`

Returns a list of phone numbers for the contact with the given lookup ID

 * **Parameters:**
   * `lookupID` — the lookup ID to get the phone numbers for
   * `context` — Context instance to get the ContentResolver from
 * **Returns:** CharSequence[] containing all phone numbers for the given contact

## `public static CharSequence[] getContactEmail(String lookupID, Context context)`

Returns a list of email addresses for the contact with the given lookup ID

 * **Parameters:**
   * `lookupID` — the lookup ID to get the email addresses for
   * `context` — Context instance to get the ContentResolver from
 * **Returns:** CharSequence[] containing all email addresses for the given contact

## `public static boolean isPersonKnown(Context context, String phoneNumber)`

Whether the given person (represented by phone number) is known on the current device (i.e. in the address book) or not

 * **Parameters:**
   * `context` — the Context reference to get the ContentResolver from
   * `phoneNumber` — the phone number to look up
 * **Returns:** whether the phone number is in the contacts list (true) or not (false)
