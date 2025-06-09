package com.yl.basemvp;

import net.dongliu.apk.parser.AbstractApkFile;
import net.dongliu.apk.parser.ApkFile;
import net.dongliu.apk.parser.bean.ApkMeta;

import java.io.File;
import java.io.IOException;

public class ApkParser {
    public static AppInfo parseApk(File apkFile) {
        try {
            AbstractApkFile apk = new ApkFile(apkFile);
            ApkMeta meta = apk.getApkMeta();
            return new AppInfo(
                    meta.getPackageName(),
                    meta.getName(),
                    meta.getVersionName(),
                    apkFile.getAbsolutePath(),
                    apk.getIconFile().getData()
            );
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

