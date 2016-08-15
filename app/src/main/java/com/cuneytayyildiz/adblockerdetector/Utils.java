package com.cuneytayyildiz.adblockerdetector;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class Utils {

    /**
     * Quit the current application.
     */
    public static void quitApplication() {
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

    /**
     * Propose user to send an email with pre-filled fields.
     */
    public static void sendEMail(final Context context, final String dialogTitle, final String to, final String subject, final String body) {
        final Intent send = new Intent(Intent.ACTION_SENDTO);
        final String uriText =
                "mailto:" + Uri.encode(to) +
                        "?subject=" + Uri.encode(subject) +
                        "&body=" + Uri.encode(body);
        send.setData(Uri.parse(uriText));
        context.startActivity(Intent.createChooser(send, dialogTitle));
    }

    /**
     * Propose user to uninstall the given application
     *
     * @param context
     * @param packageName package of the application to uninstall
     */
    public static void uinstallApplication(final Context context, final String packageName) {
        final Intent intent = new Intent(Intent.ACTION_DELETE, Uri.fromParts("package", packageName, null));
        context.startActivity(intent);
    }

}
