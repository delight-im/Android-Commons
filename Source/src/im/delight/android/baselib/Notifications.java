package im.delight.android.baselib;

/**
 * Copyright 2014 www.delight.im <info@delight.im>
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

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.content.Context;

/** Show and manage notifications conveniently with full compatibility across platform levels */
public class Notifications {

	private static final int NOTIFICATION_ID_DEFAULT = 1;
	private static final int LIGHTS_COLOR_DEFAULT = 0xffffffff;
	private static Notifications mInstance;
	private final Context mContext;

	private Notifications(final Context context) {
		mContext = context.getApplicationContext();
	}

	public static Notifications getInstance(final Context context) {
		if (mInstance == null) {
			mInstance = new Notifications(context);
		}

		return mInstance;
	}

	public int show(final Class<?> targetActivityClass, final String title, final String body, final int smallIconRes) {
		return show(targetActivityClass, title, body, smallIconRes, 0);
	}

	public int show(final Class<?> targetActivityClass, final String title, final String body, final int smallIconRes, final int largeIconRes) {
		return show(targetActivityClass, title, body, smallIconRes, largeIconRes, NOTIFICATION_ID_DEFAULT);
	}

    @SuppressLint("NewApi")
	@SuppressWarnings("deprecation")
	public int show(final Class<?> targetActivityClass, final String title, final String body, final int smallIconRes, final int largeIconRes, final int notificationId) {
    	final Intent resultIntent = new Intent(mContext, targetActivityClass);
    	final PendingIntent resultPendingIntent = PendingIntent.getActivity(mContext, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

    	final Notification notification;
    	if (Build.VERSION.SDK_INT >= 11) {
	    	final Notification.Builder builder = new Notification.Builder(mContext);
	    	builder.setContentIntent(resultPendingIntent);
	    	builder.setSmallIcon(smallIconRes);
	    	if (largeIconRes != 0) {
	    		builder.setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), largeIconRes));
	    	}
	    	builder.setWhen(System.currentTimeMillis());
	    	builder.setAutoCancel(true);
	    	builder.setContentTitle(title);
	    	builder.setContentText(body);
	    	builder.setLights(LIGHTS_COLOR_DEFAULT, 500, 2000);

	    	if (Build.VERSION.SDK_INT >= 16) {
	    		notification = builder.build();
	    	}
	    	else {
	    		notification = builder.getNotification();
	    	}
    	}
    	else {
    		notification = new Notification(smallIconRes, title, System.currentTimeMillis());
    		notification.setLatestEventInfo(mContext, title, body, resultPendingIntent);
    		notification.flags |= Notification.FLAG_AUTO_CANCEL;
    		notification.defaults = 0;
    		notification.flags |= Notification.FLAG_SHOW_LIGHTS;
    		notification.ledARGB = LIGHTS_COLOR_DEFAULT;
    		notification.ledOnMS = 500;
    		notification.ledOffMS = 2000;
    	}

    	final NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
    	notificationManager.notify(notificationId, notification);

    	return notificationId;
    }

    public void cancel(final int notificationId) {
    	final NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
    	try {
    		notificationManager.cancel(notificationId);
    	}
    	catch (Exception e) { }
    }

}
