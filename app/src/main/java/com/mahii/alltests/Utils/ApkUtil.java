package com.mahii.alltests.Utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.text.TextUtils;

import java.io.File;

/**
 * @author  i_m_mahii
 */

public class ApkUtil {

    /**
     * get UnInstallApkPackageName
     *
     * @param context Context
     * @param apkPath apkPath
     * @return apk PackageName
     */
    public static String getUnInstallApkPackageName(Context context, String apkPath) {
        PackageManager pm = context.getPackageManager();
        PackageInfo info = pm.getPackageArchiveInfo(apkPath, PackageManager.GET_ACTIVITIES);
        if (info != null) {
            ApplicationInfo appInfo = info.applicationInfo;
            if (appInfo != null) {
                return appInfo.packageName;
            }
        }
        return null;
    }

    /**
     * install an apk bu apkPath
     *
     * @param context Context
     * @param apkPath apkPath
     */
    public static final void installApk(Context context, String apkPath) {
        if (TextUtils.isEmpty(apkPath)) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.fromFile(new File(apkPath)), "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    /**
     * check whether app installed
     *
     * @param context Context
     * @param packageName app package name
     * @return true or false
     */
    public static boolean checkAppInstalled(Context context, String packageName) {
        if (TextUtils.isEmpty(packageName)) {
            return false;
        }
        try {
            context.getPackageManager().getApplicationInfo(packageName, PackageManager.GET_INSTRUMENTATION);
            return true;
        } catch (NameNotFoundException e) {
            return false;
        }
    }

}
