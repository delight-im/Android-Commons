# UI

## `private UI()`

This class may not be instantiated

## `public static void closeDialog(DialogInterface dialog)`

Checks whether the given Dialog instance is still showing up and closes it if necessary

 * **Parameters:** `dialog` — Dialog instance to close

## `public static void restartActivity(Activity activity)`

Restarts the given Activity

 * **Parameters:** `activity` — Activity instance to restart (e.g. MyActivity.this)

## `public static void setKeyboardVisibility(final Context context, final View view, final boolean visible)`

Either shows or hides the software keyboard

 * **Parameters:**
   * `context` — Context reference to get the InputMethodManager from
   * `view` — the View to show/hide the software keyboard for
   * `visible` — whether to show (true) or hide (false) the keyboard

## `public static void insertTextAtCursorPosition(final EditText editText, final String textToInsert)`

Inserts the given text into the given EditText view at the current cursor position or replacing the current selection

 * **Parameters:**
   * `editText` — the EditText view to insert the text into
   * `textToInsert` — the text to insert

## `public static void replaceTextsWithImages(final Context context, final EditText editText, final String[] searchTexts, final int[] replacementImages)`

Replaces the given text with the given image (as a spannable) in the given EditText

 * **Parameters:**
   * `editText` — the EditText view to operate on
   * `searchTexts` — the texts to replace
   * `replacementImages` — the resource IDs of the images to insert
   * `context` — the Context instance to use
