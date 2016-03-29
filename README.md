# Android-Commons

Reusable components and utilities for Android

## Installation

 * Add this library to your project
   * Declare the Gradle repository in your root `build.gradle`

     ```gradle
     allprojects {
         repositories {
             maven { url "https://jitpack.io" }
         }
     }
     ```

   * Declare the Gradle dependency in your app module's `build.gradle`

     ```gradle
     dependencies {
         compile 'com.github.delight-im:Android-Commons:v1.0.0'
     }
     ```

## Documentation

 * [Adapters](#adapters)
 * [Apps](#apps)
 * [AutoListPreference](#autolistpreference)
 * [Cache](#cache)
 * [Collections](#collections)
 * [Data](#data)
 * [DeviceInfo](#deviceinfo)
 * [Identity](#identity)
 * [ListEditText](#listedittext)
 * [LruCache](#lrucache)
 * [Notifications](#notifications)
 * [Phone](#phone)
 * [Screen](#screen)
 * [SimpleProgressDialog](#simpleprogressdialog)
 * [Social](#social)
 * [Strings](#strings)
 * [UI](#ui)
 * [ViewScreenshot](#viewscreenshot)

### Adapters

```java
/** Utilities for working with `android.widget.Adapter` and its subclasses */
public final class Adapters {

    /**
     * Sets the items of the given adapter to the specified collection
     *
     * This operation includes removing the old items (if any) and adding the new ones
     *
     * Any listeners will only be informed about the final result
     *
     * @param adapter the adapter for which to set the items
     * @param content the collection that has the new items
     */
    public static <T> void setItems(final ArrayAdapter<T> adapter, final Collection<T> content);

    /**
     * Adds all the items from the specified collection to the given adapter at once
     *
     * @param adapter the adapter to insert the items into
     * @param items the items to insert
     */
    public static <T> void addAll(final ArrayAdapter<T> adapter, final Collection<T> items);

}
```

### Apps

```java
/** Utilities for viewing and managing the list of installed applications on the current device */
public final class Apps {

    /**
     * Retrieves the list of all apps installed on the current device
     *
     * @param context a context reference
     * @return the list of all apps
     */
    public static List<Entry> getList(final Context context);

    /**
     * Launches the app with the specified package name
     *
     * @param context a context reference
     * @param packageName the package name of the app to launch
     */
    public static void launch(final Context context, final String packageName);

    /** Information about a single installed application */
    public static class Entry implements Parcelable {

        /**
         * Returns the application's package name
         *
         * @return the package name
         */
        public String getPackageName();

        /**
         * Returns the application's title
         *
         * @return the title
         */
        public String getTitle();

        /**
         * Returns whether the application is a system application or not
         *
         * @return whether the application is a system application (`true`) or user-installed application (`false`)
         */
        public boolean isSystemApp();

        /**
         * Returns the application's icon
         *
         * @param context a context reference
         * @return the icon
         */
        public Drawable getIcon(final Context context);

        /**
         * Launches the application
         *
         * @param context a context reference
         */
        public void launch(final Context context);

    }

}
```

### AutoListPreference

```java
/**
 * Preference extending `android.preference.ListPreference` that comes with an auto-updating summary
 *
 * Just add this preference to your XML preference definition as you would do with a normal `ListPreference`, but reference `im.delight.android.commons.AutoListPreference` instead of `ListPreference`
 *
 * In addition to that, add `android:summary="%s"` to the preference definition to enable updates of the summary
 */
public final class AutoListPreference extends ListPreference { }
```

### Cache

```java
/**
 * Cache that can hold a fixed number of elements in memory
 *
 * If the cache is full and a new entry is added, the oldest entry will be dropped
 *
 * This implementation is thread-safe
 *
 * @param <K> the type of the keys
 * @param <V> the type of the values
 */
public class Cache<K, V> {

    /**
     * Creates a new instance with the given cache size
     *
     * @param cacheSize the maximum number of elements to hold
     */
    public Cache(final int cacheSize);

    /**
     * Inserts a new element and possibly overwrites any previous value with the same key
     *
     * @param key the key to save the value for
     * @param value the value to save
     * @return the previous value or `null`
     */
    public synchronized V put(final K key, final V value);

    /**
     * Returns the value for the given key
     *
     * @param key the key to look up
     * @return the value that was found for the key or `null`
     */
    public synchronized V get(final K key);

    /**
     * Removes the entry with the specified key
     *
     * @param key the key to remove
     * @return the old value for the specified key
     */
    public synchronized V remove(final K key);

    /**
     * Returns all values that are currently in this cache
     *
     * @return a collection containing the values
     */
    public synchronized Collection<V> values();

    /**
     * Returns all keys that are currently in this cache
     *
     * @return a set containing the keys
     */
    public synchronized Set<K> keys();

    /**
     * Returns the current size of this cache
     *
     * @return the number of elements contained in this cache
     */
    public synchronized int size();

    /**
     * Override this method if you want to be informed whenever an entry is removed from the cache
     *
     * You may use this if you must manually release resources when an element is deleted
     *
     * The default implementation does nothing
     *
     * @param key the key that was removed
     * @param value the value that was removed
     * @param causedManually whether the removal was caused manually (through `put(...)` or `delete(...)`) or automatically
     */
    public void onEntryRemoved(final K key, final V value, final boolean causedManually);

}
```

### Collections

```java
/** Utilities for working with arrays and `java.util.Collection<E>` (including its various subclasses) */
public final class Collections {

    /**
     * Returns a new list that contains all unique items from the specified input list
     *
     * @param input the list to get the unique items from
     * @return a list containing only the unique items
     */
    public static <E> ArrayList<E> makeListUnique(final List<E> input);

    /**
     * Searches for the given object in the specified array and returns the object's index
     *
     * @param array the array to search in
     * @param search the object to search for
     * @return the object's index in the array or `-1`
     */
    public static <E> int arrayIndexOf(final E[] array, final E search);

    /**
     * Joins the given iterable object's elements and connects them with a delimiter string
     *
     * @param iterable the iterable object's whose items are to be joined
     * @param delimiter the string that will be used as a divider between the joined items
     * @return a string that contains all the joined items
     */
    public static String implode(final Iterable<?> iterable, final String delimiter);

}
```

### Data

```java
/** Utilities for working with data and primitive data types */
public final class Data {

    /**
     * Converts the specified binary data to its hexadecimal representation
     *
     * @param data the binary data to convert
     * @return the hexadecimal representation
     */
    public static String binToHex(final byte[] data);

    /**
     * Converts the specified color to its hexadecimal representation
     *
     * @param color the color to convert, as defined by the `android.graphics.Color` class
     * @return the hexadecimal representation
     */
    public static String colorToHex(final int color);

}
```

### DeviceInfo

```java
/** Exposes device information for the current device that can be be retrieved by the application */
public final class DeviceInfo {

    /**
     * Retrieves the current application's version ID as installed on the user's device
     *
     * @param context a context reference
     * @return the app's version ID or `Integer.MAX_VALUE` if not available
     */
    public static int getAppVersion(final Context context);

    /**
     * Retrieves the ISO 3166-1 alpha-2 country code for the current device
     *
     * @param context a context reference
     * @return the lowercase country code or `null` if not available
     */
    public static String getCountryISO2(final Context context);

    /**
     * Gets the screen size for the current device
     *
     * @param context a context reference
     * @return a `Point` object holding the width in `x` and the height in `y`
     */
    public static Point getScreenSize(final Context context);

}
```

### Identity

```java
/** Exposes identity information about the current user that can be retrieved by the application */
public final class Identity {

    /**
     * Returns an identifier that is unique for this application's installation
     *
     * The identifier is usually reset when the app is uninstalled or the application's data is cleared
     *
     * @param context a context reference
     * @return the unique identifier
     */
    public synchronized static String getInstallationId(final Context context);

    /**
     * Returns an identifier that is unique for this device
     *
     * The identifier is usually reset when performing a factory reset on the device
     *
     * On devices with multi-user capabilities, each user usually has their own identifier
     *
     * In general, you may not use this identifier for advertising purposes
     *
     * @param context a context reference
     * @return the unique identifier
     */
    public static String getDeviceId(final Context context);

}
```

### ListEditText

```java
/**
 * View extending `android.widget.EditText` whose value is set by choosing from a list
 *
 * Besides the displayed value, this view has an additional internal value which can be accessed via `getValue`
 *
 * Just add this view to your XML layout definition as you would do with a normal `EditText`, but reference `im.delight.android.commons.ListEditText` instead of `EditText`
 *
 * In your `android.app.Activity` instance's `onCreate` method, you must get a reference to this view and call `init` on it
 *
 * When manipulating the text of this view, use `setValue` and `getValue` instead of `setText` and `getText`
 */
public final class ListEditText extends EditText {

    /**
     * Registers an `OnChangeListener` that listens for updates to this component's value
     *
     * @param callback the listener
     */
    public void setCallback(final OnChangeListener callback);

    /**
     * Initializes this view so that the user can interact with it
     *
     * @param context a context reference
     * @param valuesHuman a string array containing the values to be displayed to the user
     * @param valuesMachine a string array containing the internal representations of the displayed values
     * @param titleRes a string resource ID for the title of the selection dialog
     * @param cancelRes a string resource ID for the caption of the cancel button
     */
    public void init(final Context context, final String[] valuesHuman, final String[] valuesMachine, final int titleRes, final int cancelRes);

    /**
     * Initializes this view so that the user can interact with it
     *
     * @param context a context reference
     * @param valuesHuman a string array containing the values to be displayed to the user
     * @param valuesMachine a string array containing the internal representations of the displayed values
     * @param titleRes a string resource ID for the title of the selection dialog
     * @param cancelRes a string resource ID for the caption of the cancel button
     * @param showInternalValues whether to show the internal values instead of the labels in the text field or not
     */
    public void init(final Context context, final String[] valuesHuman, final String[] valuesMachine, final int titleRes, final int cancelRes, final boolean showInternalValues);

    /**
     * Returns the internal value of this view (not the displayed value which can be accessed with `getText().toString()`)
     *
     * @return the internal value of this input field
     */
    public String getValue();

    /**
     * Sets the internal value of this view along with the displayed value (which is retrieved automatically)
     *
     * @param value the internal value to set/select
     */
    public void setValue(final String value);

    public static interface OnChangeListener {
        public void onValueChanged(String value);
    }

}
```

### LruCache

```java
/**
 * LRU (least recently used) cache that can hold a fixed number of elements in memory
 *
 * If the cache is full and a new entry is added, the least recently used entry will be dropped
 *
 * This implementation is thread-safe
 *
 * @param <K> the type of the keys
 * @param <V> the type of the values
 */
public final class LruCache<K, V> extends Cache<K, V> {

    /**
     * Creates a new instance with the given cache size
     *
     * @param cacheSize the maximum number of elements to hold
     */
    public LruCache(final int cacheSize);

}
```

### Notifications

```java
/** Show and manage notifications conveniently with full compatibility across platform levels */
public final class Notifications {

    /**
     * Returns an instance of this class
     *
     * @param context a context reference
     * @return the instance
     */
    public static Notifications getInstance(final Context context);

    /**
     * Shows a notification to the user
     *
     * @param targetActivityClass the target `android.app.Activity` class to launch
     * @param title the title of the notification
     * @param body the body text of the notification
     * @param smallIconRes a resource ID for the small notification icon
     * @return the notification ID
     */
    public int show(final Class<?> targetActivityClass, final String title, final String body, final int smallIconRes);

    /**
     * Shows a notification to the user
     *
     * @param targetActivityClass the target `android.app.Activity` class to launch
     * @param title the title of the notification
     * @param body the body text of the notification
     * @param smallIconRes a resource ID for the small notification icon
     * @param largeIconRes a resource ID for the large notification icon
     * @return the notification ID
     */
    public int show(final Class<?> targetActivityClass, final String title, final String body, final int smallIconRes, final int largeIconRes);

    /**
     * Shows a notification to the user
     *
     * @param targetActivityClass the target `android.app.Activity` class to launch
     * @param title the title of the notification
     * @param body the body text of the notification
     * @param smallIconRes a resource ID for the small notification icon
     * @param largeIconRes a resource ID for the large notification icon
     * @param notificationId the pre-defined notification ID to use
     * @return the notification ID
     */
    public int show(final Class<?> targetActivityClass, final String title, final String body, final int smallIconRes, final int largeIconRes, final int notificationId);

    /**
     * Cancels (i.e. hides or dismisses) the notification with the specified ID
     *
     * @param notificationId the ID of the notification to cancel
     */
    public void cancel(final int notificationId);

}
```

### Phone

```java
/** Utilities for working with phone numbers and phone data */
public final class Phone {

    /**
     * Gets the phone number for the current device
     *
     * Requires the following permission in your `AndroidManifest.xml`:
     *
     * `<uses-permission android:name="android.permission.READ_PHONE_STATE" />`
     *
     * @param context a context reference
     * @return the phone number or `null` if not available (e.g. on tablets without telephony features)
     */
    public static String getNumber(final ContextWrapper context);

    /**
     * Gets the ISO alpha-2 country code for the current device from the SIM card
     *
     * @param context a context reference
     * @return the country code or `null` if not available
     */
    public static String getCountry(final ContextWrapper context);

    /**
     * Gets the ISO alpha-2 country code for the current device from the SIM card
     *
     * @param context a context reference
     * @param defaultCountry the default country to return
     * @return the country code or the value from `defaultCountry` if not available
     */
    public static String getCountry(final ContextWrapper context, final String defaultCountry);

}
```

### Screen

```java
/** Utilities for working with screen sizes and orientations */
public final class Screen {

    /**
     * Locks the screen's orientation to the current setting
     *
     * @param activity an `Activity` reference
     */
    public static void lockOrientation(final Activity activity);

    /**
     * Unlocks the screen's orientation in case it has been locked before
     *
     * @param activity an `Activity` reference
     */
    public static void unlockOrientation(final Activity activity);

}
```

### SimpleProgressDialog

```java
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

    /**
     * Creates and displays a new progress dialog
     *
     * @param context a context reference
     * @return the dialog instance that can later be cancelled by calling `dismiss`
     */
    public static SimpleProgressDialog show(final Context context);

}
```

### Social

```java
/** Utilities for working with social features, communication and content sharing */
public final class Social {

    /**
     * Displays an application chooser and shares the specified plain text using the selected application
     *
     * @param context a context reference
     * @param windowTitle the title for the application chooser's window
     * @param plainTextToShare the plain text to be shared
     */
    public static void shareText(final Context context, final String windowTitle, final String plainTextToShare);

    /**
     * Displays an application chooser and shares the specified plain text and subject line using the selected application
     *
     * @param context a context reference
     * @param windowTitle the title for the application chooser's window
     * @param bodyTextToShare the body text to be shared
     * @param subjectTextToShare the title or subject for the message to be shared, if supported by the target application
     */
    public static void shareText(final Context context, final String windowTitle, final String bodyTextToShare, final String subjectTextToShare);

    /**
     * Displays an application chooser and shares the specified file using the selected application
     *
     * @param context a context reference
     * @param windowTitle the title for the application chooser's window
     * @param fileToShare the file to be shared
     * @param mimeTypeForFile the MIME type for the file to be shared (e.g. `image/jpeg`)
     */
    public static void shareFile(final Context context, final String windowTitle, final File fileToShare, final String mimeTypeForFile);

    /**
     * Displays an application chooser and shares the specified file using the selected application
     *
     * @param context a context reference
     * @param windowTitle the title for the application chooser's window
     * @param fileToShare the file to be shared
     * @param mimeTypeForFile the MIME type for the file to be shared (e.g. `image/jpeg`)
     * @param subjectTextToShare the message title or subject for the file, if supported by the target application
     */
    public static void shareFile(final Context context, final String windowTitle, final File fileToShare, final String mimeTypeForFile, final String subjectTextToShare);

    /**
     * Opens the specified user's Facebook profile, either in the app or on the web
     *
     * @param context a context reference
     * @param facebookId the user's Facebook ID or profile name
     */
    public static void openFacebookProfile(final Context context, final String facebookId);

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
    public static void sendMail(final String recipientEmail, final String subjectText, final String bodyText, final int captionRes, final Context context);

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
    public static void sendMail(final String recipientEmail, final String subjectText, final String bodyText, final int captionRes, final String restrictToPackage, final Context context);

    /**
     * Displays an application chooser and composes the described text message (SMS) using the selected application
     *
     * @param recipientPhone the recipient's phone number or `null`
     * @param bodyText the body text of the message
     * @param captionRes a string resource ID for the title of the application chooser's window
     * @param context a context reference
     * @throws Exception if there was an error trying to launch the SMS application
     */
    public static void sendSms(final String recipientPhone, final String bodyText, final int captionRes, final Context context);

    /**
     * Normalizes the specified phone number to its E.164 representation, if supported by the platform
     *
     * @param context a context reference
     * @param phoneNumber the phone number to normalize
     * @return the normalized phone number
     */
    public static String normalizePhoneNumber(final Context context, final String phoneNumber);

    /**
     * Returns a list of phone numbers for the contact with the given lookup ID
     *
     * @param contactLookupId a contact's lookup ID to get the phone numbers for
     * @param context a context reference
     * @return CharSequence[] a list of all phone numbers for the given contact or `null`
     */
    public static CharSequence[] getContactPhone(final String contactLookupId, final Context context);

    /**
     * Returns a list of email addresses for the contact with the given lookup ID
     *
     * @param contactLookupId a contact's lookup ID to get the email addresses for
     * @param context a context reference
     * @return CharSequence[] a list of all email addresses for the given contact or `null`
     */
    public static CharSequence[] getContactEmail(final String contactLookupId, final Context context);

    /**
     * Whether the specified person is known on the current device or not
     *
     * @param context a context reference
     * @param phoneNumber the phone number to look up
     * @return whether the phone number is in the local address book or not
     */
    public static boolean isPersonKnown(final Context context, final String phoneNumber);

}
```

### Strings

```java
/** Utilities for working with strings and characters */
public final class Strings {

    /**
     * Checks whether the given search string is contained in the subject string without regard to the strings' cases
     *
     * @param subject the string to search in
     * @param search the string to search for
     * @return whether the string is contained in the subject or not
     */
    public static boolean containsIgnoreCase(final String subject, final String search);

    /**
     * Repeats the given string so that it reaches the desired count
     *
     * @param str the string to repeat
     * @param count the desired number of repetitions
     * @return the new string with the desired number of repetitions
     */
    public static String repeat(final String str, final int count);

    /**
     * Pads the given string on the left side so that it reaches the desired length
     *
     * @param input the string to modify
     * @param length the desired length of the string
     * @return the padded string
     */
    public static String padLeft(final String input, final int length);

    /**
     * Pads the given string on the left side so that it reaches the desired length
     *
     * @param input the string to modify
     * @param length the desired length of the string
     * @param padChar the character used for padding
     * @return the padded string
     */
    public static String padLeft(final String input, final int length, final char padChar);

    /**
     * Pads the given string on the right side so that it reaches the desired length
     *
     * @param input the string to modify
     * @param length the desired length of the string
     * @return the padded string
     */
    public static String padRight(final String input, final int length);

    /**
     * Pads the given string on the right side so that it reaches the desired length
     *
     * @param input the string to modify
     * @param length the desired length of the string
     * @param padChar the character used for padding
     * @return the padded string
     */
    public static String padRight(final String input, final int length, final char padChar);

    /**
     * "Encrypts" the given string using the ROT-13 algorithm
     *
     * @param input the string to "encrypt"
     * @return the "encrypted" string
     */
    public static String rot13(final String input);

    /**
     * Splits the given string into chunks of the specified length
     *
     * @param textToSplit the text to split into chunks
     * @param chunkLength the desired length of the chunks
     * @return the list of chunks extrated from the text
     */
    public static String[] splitToChunks(final String textToSplit, final int chunkLength);

    /**
     * Ensures that the given string has the maximum length as specified
     *
     * @param input the string to ensure the length for
     * @param maxLength the length to guarantee
     * @return the (shortened) string
     */
    public static String ensureMaxLength(final String input, final int maxLength);

}
```

### UI

```java
/** Utilities for working with UI components and views such as `android.view.View` and its various subclasses */
public final class UI {

    /**
     * Gets the recommended text color against a background of the specified color
     *
     * @param backgroundColor the background color, as defined by the `android.graphics.Color` class
     * @return the recommended text color
     */
    public static int getTextColor(final int backgroundColor);

    /**
     * Generates a random color
     *
     * @return the color, as defined by the `android.graphics.Color` class
     */
    public static int getRandomColor();

    /**
     * Calculates the brightness of the specified color
     *
     * @param color the color, as defined by the `android.graphics.Color` class
     * @return the brightness of the color
     */
    public static double getColorBrightness(final int color);

    /**
     * Ensures that the given `EditText` component will have a maximum length as specified
     *
     * @param editText the `EditText` component
     * @param maxLength the maximum length to guarantee
     */
    public static void setMaxLength(final EditText editText, final int maxLength);

    /**
     * Ensures that the given `EditText` component will have a maximum length as specified
     *
     * @param editText the `EditText` component
     * @param maxLength the maximum length to guarantee
     * @param existingInputFilters a list of existing input filters to keep
     */
    public static void setMaxLength(final EditText editText, final int maxLength, final InputFilter[] existingInputFilters);

    /**
     * Moves the cursor to the end of the specified `EditText` component
     *
     * @param editText the `EditText` component
     */
    public static void putCursorToEnd(final EditText editText);

    /**
     * Scrolls to the bottom of the specified `ListView` component
     *
     * @param listView the `ListView` component
     */
    public static void scrollToBottom(final ListView listView);

    /**
     * Generates a screenshot of the specified `View`
     *
     * @param view the `View` component
     * @return the screenshot
     */
    public static Bitmap getViewScreenshot(final View view);

    /**
     * Forces the overflow menu to be shown in the `ActionBar`
     *
     * @param context a context reference
     */
    public static void forceOverflowMenu(final Context context);

    /**
     * Checks whether the given dialog is still showing up and closes it if necessary
     *
     * @param dialog the dialog to close
     */
    public static void closeDialog(final DialogInterface dialog);

    /**
     * Restarts the given `Activity`
     *
     * @param activity the `Activity` instance to restart (e.g. `MyActivity.this`)
     */
    public static void restartActivity(final Activity activity);

    /**
     * Show or hide the software keyboard as requested
     *
     * @param context a context reference
     * @param view the `View` to show or hide the software keyboard for
     * @param visible whether to show (`true`) or hide (`false`) the software keyboard
     */
    public static void setKeyboardVisibility(final Context context, final View view, final boolean visible);

    /**
     * Inserts the given text into the specified `EditText` instance at the current cursor position or replacing the current selection
     *
     * @param editText the `EditText` instance to insert the text into
     * @param textToInsert the text to insert
     */
    public static void insertTextAtCursorPosition(final EditText editText, final String textToInsert);

    /**
     * Replaces the given texts with the given image (as a `Spannable`) in the specified `EditText` instance
     *
     * @param context a context reference
     * @param editText the `EditText` instance to operate on
     * @param searchTexts the texts to replace
     * @param replacementImages the resource IDs of the images to insert
     */
    public static void replaceTextsWithImages(final Context context, final EditText editText, final String[] searchTexts, final int[] replacementImages);

    /**
     * Sets whether the year should be visible and selectable in the given `DatePicker` instance
     *
     * @param picker the `DatePicker` instance
     * @param yearVisible whether the year should be visible or not
     */
    public static void setDatePickerYearVisible(final DatePicker picker, final boolean yearVisible);

    /**
     * Sets the given `TextView` to be read-only or read-and-write
     *
     * @param view a `TextView` or one of its subclasses
     * @param readOnly whether the view should be read-only or not
     */
    public static void setReadOnly(final TextView view, final boolean readOnly);

}
```

### ViewScreenshot

```java
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
    public ViewScreenshot(final Activity activity, final Callback callback);

    /**
     * Specifies the view to take the screenshot of
     *
     * @param view the `View` to take the screenshot of
     * @return this instance for chaining
     */
    public ViewScreenshot from(final View view);

    /**
     * Specifies the filename to save the screenshot under
     *
     * @param filenameWithoutExtension the filename without any file extension
     * @return this instance for chaining
     */
    public ViewScreenshot asFile(final String filenameWithoutExtension);

    /**
     * Specifies the format to save the screenshot in
     *
     * @param format the format, either `ViewScreenshot.FORMAT_JPEG` or `ViewScreenshot.FORMAT_PNG`
     * @return this instance for chaining
     */
    public ViewScreenshot inFormat(final int format);

    /**
     * Builds and saves the screenshot
     */
    public void build();

}
```

## Contributing

All contributions are welcome! If you wish to contribute, please create an issue first so that your feature, problem or question can be discussed.

## License

```
Copyright (c) delight.im <info@delight.im>

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
