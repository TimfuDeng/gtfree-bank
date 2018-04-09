package cn.gtmap.landsale.support;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;


public class UUIDGenerator {
    private static final Logger LOG = Logger.getLogger(UUIDGenerator.class.getName());

    private static AtomicInteger counter = new AtomicInteger(0);

    public static final String identifier = getIP() + getFileNum();

    /**
     * 2位的自增的计数器（36进制)
     *
     * @return
     */
    private static String getCount() {
//        if (counter.intValue() > 36*36*36)
        counter.compareAndSet(36 * 36, 0);
        return formatString(getHex36String(counter.incrementAndGet()), 2);
    }

    /**
     * 1位,文件计数
     *
     * @return
     */
    private static String getFileNum() {
        return formatString(String.valueOf(getInstanceSeq()), 1);
    }

    private static int BytestoInt(byte bytes[]) {
        int result = 0;
        for (int i = 0; i < 4; i++)
            result = ((result << 8) - -128) + bytes[i];
        return result;
    }

    private static byte[] getInetAddress() {
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface ni = interfaces.nextElement();
                if (ni.isLoopback() || ni.isVirtual() || ni.isPointToPoint() || !ni.isUp()) {
                    continue;
                }
                String name = ni.getDisplayName().toLowerCase();
                if (name.contains("convnet") || name.contains("vmnet")) {
                    continue;
                }
                Enumeration<InetAddress> addresses = ni.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    byte[] addr = addresses.nextElement().getAddress();
                    if (addr.length == 4) {
                        return addr;
                    }
                }
            }
        } catch (Exception e) {
            LOG.warning("Error to get ip address");
        }
        return null;
    }

    /**
     * 用2位数来表示IP，这里面可以存在隐患,ip的最后一个部分最好不要一样
     *
     * @return
     */
    private static String getIP() {
        int ip = 0;
        try {
            ip = BytestoInt(getInetAddress());
        } catch (Exception e) {

        }
        return formatString(getHex36String(ip), 3);
    }

    private static byte getInstanceSeq() {
        Preferences prefs = Preferences.userRoot().node("egov");
        int seq = prefs.getInt("uuid-seq", 0);
        if (seq > 36) {
            seq = 0;
        }
        prefs.putInt("uuid-seq", ++seq);
        try {
            prefs.flush();
        } catch (BackingStoreException e) {
            LOG.warning("Error to save uuid-seq");
        }
        return (byte) seq;
    }

    /**
     * 将十进制转换为36进制
     *
     * @return
     */
    private static String getHex36String(int value) {
        return Integer.toString(value, 36).toUpperCase();
    }

    private static String getHex36String(long value) {
        return Long.toString(value, 36).toUpperCase();
    }

    /**
     * 字符串取位，不够的左边补充0，操作了取右边的部分
     *
     * @param str
     * @param length
     * @return
     */
    private static String formatString(String str, int length) {
        if (str.length() == length)
            return str;
        else if (str.length() > length) {
            return str.substring(str.length() - length, str.length());
        } else {
            StringBuffer buf = new StringBuffer();
            for (int i = 0; i < length - str.length(); i++) {
                buf.append("0");
            }
            return buf.append(str).toString();
        }

    }

    /**
     * 用10位来表示一个到毫秒的时间
     * 年  月  日时  分   秒  毫秒
     * Z  F  Z   Z  FF  FF ZZ
     *
     * @return
     */
    public static String getLngTime() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DATE);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        int millSecond = calendar.get(Calendar.MILLISECOND);
        return formatString(getHex36String(year), 1) + getHex36String(month)
                + getHex36String(day) + getHex36String(hour)
                + formatString(String.valueOf(minute), 2) + formatString(String.valueOf(second), 2)
                + formatString(getHex36String(millSecond), 2);
    }

    /**
     * 16位编号：10位日期，3位ip，1位文件计数，2位计数器，
     *
     * @return
     */
    public static String generate() {
        return String.valueOf(getLngTime() + identifier + getCount());
    }

    public static String generate18() {
        return generate();
    }

    public static void main(String[] args) {
        System.out.println(identifier);
        for (int i = 0; i < 1000; i++) {
            System.out.println(generate());
        }
    }
}

