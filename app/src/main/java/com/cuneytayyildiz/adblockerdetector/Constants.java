package com.cuneytayyildiz.adblockerdetector;

import android.annotation.SuppressLint;

/**
 * Created by cuneyt on 015, 15, 08, 2016.
 */

public class Constants {
    // Asynchronous callback
    public interface AdBlockerCallback {
        void onResult(boolean adBlockerFound, Info info);
    }

    // Detection method
    public enum Method {
        NONE, BY_HOSTS_FILE, BY_APP_NAME, BY_HOST_RESOLUTION, BY_LOCAL_PROXY
    }

    // Name of known ad blockers

    public static final String[] BLOCKERS_APP_NAMES =
            {
                    "de.ub0r.android.adBlock",
                    "org.adblockplus.android",
                    "com.bigtincan.android.adfree",
                    "org.adaway",
                    "org.czzsunset.adblock",
                    "com.pasvante.adblocker",
                    "com.perlapps.MyInternetSecurity",
                    "net.xdevelop.adblocker_t",
                    "net.xdevelop.adblocker",
                    "com.jrummy.apps.ad.blocker",
                    "com.atejapps.advanishlite",
                    "com.atejapps.advanish",
                    "pl.adblocker.free",
                    "de.resolution.blockit"
            };


    // Name of known blocked hosts
    public static final String[] BLOCKED_HOSTS =
            {
                    "a.admob.com",
                    "mm.admob.com",
                    "p.admob.com",
                    "r.admob.com",
                    "mmv.admob.com",
                    "aax-fe-sin.amazon-adsystem.com",
                    "rcm-na.amazon-adsystem.com",
                    "aax-us-east.amazon-adsystem.com",
                    "ir-na.amazon-adsystem.com",
                    "aax-eu.amazon-adsystem.com"
            };

    // "hosts" file possible paths
    @SuppressLint("SdCardPath")
    public static final String[] HOSTS_FILES = {"/etc/hosts", "/system/etc/hosts", "/data/data/hosts"};

    // Pattern to search in hosts file

    public static final String[] HOSTS_FILE_PATTERNS = {"admob", "amazon-adsystem"};

    // Give information on how ad blocker was detected
    public static class Info {
        public Method method;
        public String details1;
        public String details2;
    }
}
