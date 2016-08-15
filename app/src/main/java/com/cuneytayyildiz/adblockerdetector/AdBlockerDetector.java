
package com.cuneytayyildiz.adblockerdetector;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.InetAddress;

import static com.cuneytayyildiz.adblockerdetector.Constants.BLOCKED_HOSTS;
import static com.cuneytayyildiz.adblockerdetector.Constants.Info;
import static com.cuneytayyildiz.adblockerdetector.Constants.Method;

/**
 * Created by cuneyt on 015, 15, 08, 2016.
 */
public class AdBlockerDetector {

    private WeakReference<Context> context;

    /**
     * @param context can be null, in this case the method using package name is not used.
     */
    public AdBlockerDetector(Context context) {
        this.context = new WeakReference<>(context);
    }

    /**
     * Asynchronous ad-blockers detection.
     * Callback is called in GUI thread.
     *
     * @param callback
     */
    public void detectAdBlockers(Constants.AdBlockerCallback callback) {
        new DetectTask(callback).execute();
    }

    /**
     * Synchronous ad-blockers detection
     * This is blocking and should be called in a separated thread.
     * In Android activities, prefer the asynchronous version.
     *
     * @param info if not null, it will be filled.
     * @return true if an ad-blocker is detected
     */
    public boolean detectAdBlockers(Info info) {
        if (info != null) {
            info.method = Method.NONE;
            info.details1 = "";
            info.details2 = "";
        }
        return detectAppNames(info) || (detectHostName(info)) || detectInHostFile(info);
    }

    /**
     * Synchronous ad-blockers detection
     * This is blocking and should be called in a separated thread.
     * In Android activities, prefer the asynchronous version.
     *
     * @return true if an AdBlocker is detected
     */
    public boolean detectAdBlockers() {
        return detectAdBlockers((Info) null);
    }

    private boolean detectInHostFile(Info info) {
        // search a readable hosts file
        File hostsFile = null;
        for (final String fileName : Constants.HOSTS_FILES) {
            hostsFile = new File(fileName);
            if (hostsFile.canRead())
                break;
        }
        // and read it
        if (hostsFile != null && hostsFile.canRead()) {
            BufferedReader in = null;
            try {
                in = new BufferedReader(new FileReader(hostsFile));
                String line;
                while ((line = in.readLine()) != null) {
                    line = line.trim();
                    if (line.length() > 0 && line.charAt(0) != '#') {
                        for (final String pattern : Constants.HOSTS_FILE_PATTERNS) {
                            if (line.contains(pattern)) {
                                if (info != null) {
                                    info.method = Method.BY_HOSTS_FILE;
                                    info.details1 = hostsFile.getAbsolutePath();
                                    info.details2 = line;
                                }
                                return true;
                            }
                        }
                    }
                }
            } catch (Exception e) {
                return false;
            } finally {
                try {
                    if (in != null)
                        in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    private boolean detectHostName(Info info) {
        for (final String host : BLOCKED_HOSTS) {
            final String address = isLocalHost(host);
            if (address != null) {
                if (info != null) {
                    info.method = Method.BY_HOST_RESOLUTION;
                    info.details1 = host;
                    info.details2 = address;
                }
                return true;
            }
        }
        return false;
    }

    private String isLocalHost(String hostName) {
        try {
            final InetAddress a = InetAddress.getByName(hostName);
            if (a != null && (a.isAnyLocalAddress() || a.isLinkLocalAddress() || a.isLoopbackAddress()))
                return a.getHostAddress();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private boolean detectAppNames(Info info) {
        if (context != null) {
            for (final String app : Constants.BLOCKERS_APP_NAMES) {
                if (isAppInstalled(app)) {
                    if (info != null) {
                        info.method = Method.BY_APP_NAME;
                        info.details1 = app;
                    }
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isAppInstalled(String packageName) {
        try {
            final Context c = context.get();
            return (c != null) && (c.getPackageManager().getPackageInfo(packageName, PackageManager.GET_ACTIVITIES) != null);
        } catch (Exception e) {
            return false;
        }
    }


    private class DetectTask extends AsyncTask<Void, Void, Boolean> {
        private WeakReference<Constants.AdBlockerCallback> callback;
        private Constants.Info info;

        DetectTask(Constants.AdBlockerCallback c) {
            callback = new WeakReference<>(c);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                info = new Constants.Info();
                return detectAdBlockers(info);
            } catch (Throwable t) {
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean r) {
            final Constants.AdBlockerCallback c = callback.get();
            if (c != null && r != null)
                c.onResult(r, info);
        }
    }
}
