# ListEditText

## `public class ListEditText extends EditText`

EditText view of which the value is set by choosing from a list 

Besides the displayed value, this view has an additional internal value which can be accessed via getValue() 

Just add this view to XML as you would do with a normal EditText, but use im.delight.helpers.ListEditText instead of EditText 

In the Activity's onCreate(...), you must get a reference to this view and call init(...) 

When manipulating the text of this view, use setValue(...) and getValue() instead of setText(...) and getText()

## `public void init(Context context, String[] valuesHuman, String[] valuesMachine, int titleRes, int cancelRes)`

Initializes this view so that the user can interact with it

 * **Parameters:**
   * `context` — Context reference from the activity (this)
   * `valuesHuman` — String array containing the values to display in the selection
   * `valuesMachine` — String array containing the internal representations of the displayed values
   * `titleRes` — String resource ID for the title of the selection dialog
   * `cancelRes` — String resource ID for the cancel button

## `public void init(Context context, String[] valuesHuman, String[] valuesMachine, int titleRes, int cancelRes, boolean showInternalValues)`

Initializes this view so that the user can interact with it

 * **Parameters:**
   * `context` — Context reference from the activity (this)
   * `valuesHuman` — String array containing the values to display in the selection
   * `valuesMachine` — String array containing the internal representations of the displayed values
   * `titleRes` — String resource ID for the title of the selection dialog
   * `cancelRes` — String resource ID for the cancel button
   * `showInternalValues` — whether to show the internal values instead of the labels in the text field (true) or not (false)

## `public String getValue()`

Returns the internal value of this view (not the displayed value which can be accessed with getText().toString())

## `public void setValue(final String value)`

Sets the internal value of this view along with the displayed value (which is retrieved automatically)
