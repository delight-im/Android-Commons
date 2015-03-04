# Collections

## `private Collections()`

This class may not be instantiated

## `public static <E> ArrayList<E> makeListUnique(List<E> input)`

Returns a new List that contains all unique items from the given List

 * **Parameters:** `input` — List to get the unique items from
 * **Returns:** List containing only unique items

## `public static <E> int arrayIndexOf(E[] array, E search)`

Searches for the given object in the given array and returns the index (or -1 if not found)

 * **Parameters:**
   * `array` — the array to search in
   * `search` — the object to search for
 * **Returns:** index or -1

## `public static String implode(final Iterable<?> iterable, final String delimiter)`

Joins the given Iterable's elements and connects them with a delimiter string

 * **Parameters:**
   * `iterable` — the Iterable instance whose items are to be joined
   * `delimiter` — the String that will be used as a divider between the joined items
 * **Returns:** a String that contains all the joined items
