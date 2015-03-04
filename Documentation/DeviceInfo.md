# DeviceInfo

## `private DeviceInfo()`

This class may not be instantiated

## `public static int getAppVersion(Context context)`

Get this app's version ID as installed on the user's device (or Integer.MAX_VALUE if not available)

 * **Parameters:** `context` — Context reference to get the PackageManager from
 * **Returns:** app version or Integer.MAX_VALUE

## `public static String getCountryISO2(Context context)`

Get ISO 3166-1 alpha-2 country code for this device (or null if not available)

 * **Parameters:** `context` — Context reference to get the TelephonyManager instance from
 * **Returns:** country code or null

## `@SuppressLint("NewApi")  @SuppressWarnings("deprecation")  public static Point getScreenSize(Context context)`

Get the device's screen size as a Point instance (x for width and y for height)

 * **Parameters:** `context` — Context reference to get the WindowManager from
 * **Returns:** Point object holding width and height
