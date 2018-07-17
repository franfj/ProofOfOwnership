package cat.urv.mesiia.privacy.frodrigo.utilities;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Util {

    private static final char[] hexArray = "0123456789ABCDEF".toCharArray();

    private Util() {
    }

    public static File getFile(String pathToFile) {
        return new File(pathToFile);
    }

    public static String fileToString(File file) {
        StringBuilder sb = new StringBuilder();

        try (BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file))) {

            for (int b; (b = inputStream.read()) != -1; ) {
                String s = Integer.toHexString(b).toUpperCase();
                if (s.length() == 1) {
                    sb.append('0');
                }
                sb.append(s).append(' ');
            }

            return sb.toString().replace(" ", "");

        } catch (IOException e) {
            Logger logger = Logger.getAnonymousLogger();
            logger.log(Level.SEVERE, "Error while reading file.", e);
            return null;
        }
    }

    public static String filePathToString(String pathToFile) {
        StringBuilder sb = new StringBuilder();

        try (BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(pathToFile))) {

            for (int b; (b = inputStream.read()) != -1; ) {
                String s = Integer.toHexString(b).toUpperCase();
                if (s.length() == 1) {
                    sb.append('0');
                }
                sb.append(s).append(' ');
            }

            return sb.toString().replace(" ", "");

        } catch (IOException e) {
            Logger logger = Logger.getAnonymousLogger();
            logger.log(Level.SEVERE, "Error while reading file.", e);
            return null;
        }
    }

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];

        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static byte[] hexStringToByteArray(String hexString) {

        int len = hexString.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
                    + Character.digit(hexString.charAt(i + 1), 16));
        }
        return data;
    }

    public static boolean isInteger(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        if (length == 0) {
            return false;
        }
        int i = 0;
        if (str.charAt(0) == '-') {
            if (length == 1) {
                return false;
            }
            i = 1;
        }
        for (; i < length; i++) {
            char c = str.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }

}
