package com.example.android.complaintcrmd;

/**
 * Created by Kartik Sethi on 04-Jul-16.
 */

import android.content.Context;
import android.content.Intent;

public final class CommonUtilities {

    // give your server registration url here
    static final String SERVER_URL = "http://2f1a2ffe.ngrok.io/ComplaintPortal/home/add_gcm";

    // Google project id
    static final String SENDER_ID = "235524580076";

    /**
     * Tag used on log messages.
     */
    static final String TAG = "ComplaintCRMD";

    static final String DISPLAY_MESSAGE_ACTION =
            "com.example.android.complaintcrmd.DISPLAY_MESSAGE";

    static final String EXTRA_MESSAGE = "message";

    /**
     * Notifies UI to display a message.
     * <p>
     * This method is defined in the common helper because it's used both by
     * the UI and the background service.
     *
     * @param context application's context.
     * @param message message to be displayed.
     */
    static void displayMessage(Context context, String message) {
        Intent intent = new Intent(DISPLAY_MESSAGE_ACTION);
        intent.putExtra(EXTRA_MESSAGE, message);
        context.sendBroadcast(intent);
    }
}