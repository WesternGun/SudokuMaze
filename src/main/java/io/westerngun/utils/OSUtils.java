package io.westerngun.utils;

import lombok.Getter;


/**
 * <p>Util class to determine OS type by getting system property "os.name". </p>
 *
 * <p>Covers several commons OS types:
 * <ul>
 *     <li>Windows</li>
 *     <li>Linux/Unix</li>
 *     <li>Mac OS</li>
 *     <li>Solaris</li>
 *     <li>FreeBSD</li>
 *     <li>other OS, otherwise</li>
 * </ul>
 *
 * The result is stored in static fields for repetive usage; conditions are only judged
 * once in all lifecycle of the application. Lombok <code>Getter</code> is used instead of
 * <code>Data</code> to avoid external change to the values.
 * </p>
 *
 * @author WesternGun
 */
@Getter
public class OSUtils {
    private static Boolean isWindows = null;
    private static Boolean isMac = null;
    private static Boolean isUnixLinux = null;
    private static Boolean isFreeBSD = null;
    private static Boolean isSolaris = null;
    private static Boolean isOtherOS = null;

    private static String getOSName() {
        return System.getProperty("os.name").toLowerCase();
    }

    public static boolean isWindows() {
        if (isWindows == null) {
            isWindows = getOSName().contains("windows");
        }
        return isWindows;
    }

    public static boolean isMac() {
        if (isMac == null) {
            isMac = getOSName().contains("mac os");
        }
        return isMac;
    }

    public static boolean isUnixLinux() {
        if (isUnixLinux == null) {
            String osName = getOSName();
            isUnixLinux = osName.contains("linux")
                    || osName.contains("unix")
                    || osName.contains("aix"); // IBM exclusive linux
        }
        return isUnixLinux;
    }

    public static boolean isFreeBSD() {
        if (isFreeBSD == null) {
            isFreeBSD = getOSName().contains("freebsd");
        }
        return isFreeBSD;
    }

    public static boolean isSolaris() {
        if (isSolaris == null) {
            isSolaris = getOSName().contains("sunos");
        }
        return isSolaris;
    }

    public static boolean isOtherOS() {
        return !isWindows() && !isMac() && !isUnixLinux() &&
            !isSolaris() && !isFreeBSD();
    }


    public static String getOSVersion() {
        return System.getProperty("os.version");
    }

    /**
     * <p>Get the "os.arch" system property. </p>
     *
     * <strong>This is more of JVM arch than OS arch! If we install a 32 bit JVM on
     * a 64 bit Windows machine, this method will return "x86"!</strong> Better to do some
     * extra check.
     *
     * @return "os.arch" sys prop
     */
    public static String getArch() {
        return System.getProperty("os.arch");
    }

}
