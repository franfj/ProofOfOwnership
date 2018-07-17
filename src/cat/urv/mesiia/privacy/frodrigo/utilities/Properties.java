package cat.urv.mesiia.privacy.frodrigo.utilities;

import java.io.*;
import java.nio.charset.Charset;

public class Properties {

    public static String ALGO = "SHA-512";
    public static Integer NLEAVES = 256;
    public static Integer NLEVELS = 8;
    public static Integer NDATASHARDS = 230;

    public static void readProperties() {
        String line;
        try (
                InputStream fis = new FileInputStream("./prop.txt");
                InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
                BufferedReader br = new BufferedReader(isr);
        ) {
            while (!(line = br.readLine()).contains("end_of_file")) {
                setAlgo(line);
                setNLeaves(line);
                setNDataShards(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void setNLeaves(String line) {
        if (line.contains("nLeaves")) {
            String value = line.replace("nLeaves", "").replace("=", "").trim();
            if (Util.isInteger(value)) {
                NLEAVES = Math.abs(Integer.parseInt(value));
                NLEVELS = ((Double) (Math.log(NLEAVES) / Math.log(2))).intValue();
            }
        }
    }

    private static void setNDataShards(String line) {
        if (line.contains("dataShards")) {
            String value = line.replace("dataShards", "").replace("=", "").trim();
            if (Util.isInteger(value)) {
                NDATASHARDS = Math.abs(Integer.parseInt(value));
            }
        }
    }

    private static void setAlgo(String line) {
        if (line.contains("hashAlgo")) {
            if (line.contains("MD5")) {
                ALGO = "MD5";
            } else if (line.contains("SHA-256")) {
                ALGO = "SHA-256";
            } else {
                ALGO = "SHA-512";
            }
        }
    }
}
