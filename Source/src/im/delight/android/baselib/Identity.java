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

import java.util.UUID;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.io.IOException;
import java.io.File;
import android.annotation.SuppressLint;
import android.content.Context;
import android.provider.Settings.Secure;
import android.os.Build;

public class Identity {

	private static final String INSTALLATION_ID_FILENAME = "INSTALLATION_ID";
	private static final String FILE_MODE_READ_ONLY = "r";
	private static String mInstallationId;
	private static String mDeviceId;

	/** This class may not be instantiated */
	private Identity() { }

	/**
	 * Returns an identifier that is unique for this app installation
	 *
	 * The identifier is usually reset when the app is uninstalled or the app's data is cleared
	 *
	 * @param context a valid `Context` reference
	 * @return the unique identifier
	 */
	public synchronized static String getInstallationId(final Context context) {
		if (mInstallationId == null) {
			final File installation = new File(context.getFilesDir(), INSTALLATION_ID_FILENAME);
			try {
				if (!installation.exists()) {
					writeInstallationId(installation);
				}
				mInstallationId = readInstallationId(installation);
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		return mInstallationId;
	}

	private static String readInstallationId(final File installation) throws IOException {
		final RandomAccessFile f = new RandomAccessFile(installation, FILE_MODE_READ_ONLY);
		final byte[] bytes = new byte[(int) f.length()];
		f.readFully(bytes);
		f.close();
		return new String(bytes);
	}

	private static void writeInstallationId(final File installation) throws IOException {
		final FileOutputStream out = new FileOutputStream(installation);
		final String id = UUID.randomUUID().toString();
		out.write(id.getBytes());
		out.close();
	}

	/**
	 * Returns an identifier that is unique for this device
	 *
	 * The identifier is usually reset when performing a factory reset on the device
	 *
	 * On devices with multi-user capabilities, each user has their own identifier, in most cases
	 *
	 * In general, you may not use this identifier for advertising purposes
	 *
	 * @param context a valid `Context` reference
	 * @return the unique identifier
	 */
	@SuppressLint("NewApi")
	public static String getDeviceId(final Context context) {
		if (mDeviceId == null) {
			final String androidId = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
			if (androidId != null && !androidId.equals("") && !androidId.equalsIgnoreCase("9774d56d682e549c")) {
				mDeviceId = androidId;
			}
			else {
				if (Build.VERSION.SDK_INT >= 9) {
					if (Build.SERIAL != null && !Build.SERIAL.equals("")) {
						mDeviceId = Build.SERIAL;
					}
					else {
						mDeviceId = getInstallationId(context);
					}
				}
				else {
					mDeviceId = getInstallationId(context);
				}
			}
		}

		return mDeviceId;
	}

}
