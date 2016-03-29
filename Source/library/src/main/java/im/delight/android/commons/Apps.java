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

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;
import java.util.ArrayList;

/** Utilities for viewing and managing the list of installed applications on the current device */
public final class Apps {

	private static PackageManager mPackageManager;

	/** This class may not be instantiated */
	private Apps() { }

	/**
	 * Retrieves the list of all apps installed on the current device
	 *
	 * @param context a context reference
	 * @return the list of all apps
	 */
	public static List<Entry> getList(final Context context) {
		final List<ApplicationInfo> apps = getInstalledPackages(context);

		final List<Entry> out = new ArrayList<Entry>(apps.size());
		for (ApplicationInfo appInfo : apps) {
			if (!appInfo.enabled) {
				continue;
			}

			out.add(new Entry(appInfo.packageName, getAppTitle(context, appInfo), isSystemApp(appInfo)));
		}

		return out;
	}

	private static String getAppTitle(final Context context, final ApplicationInfo packageInfo) {
		try {
			return getPackageManager(context).getApplicationLabel(packageInfo).toString();
		}
		catch  (Exception e) {
			return null;
		}
	}

	private static boolean isSystemApp(final ApplicationInfo packageInfo) {
		if ((packageInfo.flags & ApplicationInfo.FLAG_SYSTEM) == ApplicationInfo.FLAG_SYSTEM) {
			return true;
		}

		if ((packageInfo.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) == ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) {
			return true;
		}

		return false;
	}

	private static Drawable getAppIcon(final Context context, final String packageName) {
		try {
			return getPackageManager(context).getApplicationIcon(packageName);
		}
		catch  (Exception e) {
			return null;
		}
	}

	/**
	 * Launches the app with the specified package name
	 *
	 * @param context a context reference
	 * @param packageName the package name of the app to launch
	 */
	public static void launch(final Context context, final String packageName) {
		final Intent intent = getPackageManager(context).getLaunchIntentForPackage(packageName);

		if (intent == null) {
			throw new RuntimeException("Package with specified name `"+packageName+"` is not launchable");
		}

		context.startActivity(intent);
	}

	private static PackageManager getPackageManager(final Context context) {
		if (mPackageManager == null) {
			mPackageManager = context.getPackageManager();
		}

		return mPackageManager;
	}

	private static List<ApplicationInfo> getInstalledPackages(final Context context) {
		return getPackageManager(context).getInstalledApplications(PackageManager.GET_META_DATA);
	}

	/** Information about a single installed application */
	public static class Entry implements Parcelable {

		private final String mPackageName;
		private final String mTitle;
		private final boolean mSystemApp;

		private Entry(final String packageName, final String title, final boolean systemApp) {
			mPackageName = packageName;
			mTitle = title;
			mSystemApp = systemApp;
		}

		/**
		 * Returns the application's package name
		 *
		 * @return the package name
		 */
		public String getPackageName() {
			return mPackageName;
		}

		/**
		 * Returns the application's title
		 *
		 * @return the title
		 */
		public String getTitle() {
			return mTitle;
		}

        /**
         * Returns whether the application is a system application or not
         *
         * @return whether the application is a system application (`true`) or user-installed application (`false`)
         */
		public boolean isSystemApp() {
			return mSystemApp;
		}

		/**
		 * Returns the application's icon
		 *
		 * @param context a context reference
		 * @return the icon
		 */
		public Drawable getIcon(final Context context) {
			return getAppIcon(context, mPackageName);
		}

		/**
		 * Launches the application
		 *
		 * @param context a context reference
		 */
		public void launch(final Context context) {
			Apps.launch(context, mPackageName);
		}

		@Override
		public int describeContents() {
			return 0;
		}

		public static final Parcelable.Creator<Entry> CREATOR = new Parcelable.Creator<Entry>() {

			@Override
			public Entry createFromParcel(final Parcel in) {
				return new Entry(in);
			}

			@Override
			public Entry[] newArray(final int size) {
				return new Entry[size];
			}

		};

		private Entry(final Parcel in) {
			mPackageName = in.readString();
			mTitle = in.readString();
			mSystemApp = in.readByte() == 1;
		}

		@Override
		public void writeToParcel(final Parcel out, final int flags) {
			out.writeString(mPackageName);
			out.writeString(mTitle);
			out.writeByte((byte) (mSystemApp ? 1 : 0));
		}

	}

}
