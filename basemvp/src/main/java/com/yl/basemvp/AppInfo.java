package com.yl.basemvp;

// 数据模型
public class AppInfo {
    private String packageName;
    private String name;
    private String version;
    private String apkPath;
    private byte[] icon;

    // 构造方法、Getter/Setter

    public AppInfo(String packageName, String name, String version, String apkPath, byte[] icon) {
        this.packageName = packageName;
        this.name = name;
        this.version = version;
        this.apkPath = apkPath;
        this.icon = icon;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getApkPath() {
        return apkPath;
    }

    public void setApkPath(String apkPath) {
        this.apkPath = apkPath;
    }

    public byte[] getIcon() {
        return icon;
    }

    public void setIcon(byte[] icon) {
        this.icon = icon;
    }
}
