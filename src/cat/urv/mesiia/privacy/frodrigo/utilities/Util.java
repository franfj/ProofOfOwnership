package cat.urv.mesiia.privacy.frodrigo.utilities;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by frodrigo on 27/03/2017.
 */
public class Util {

    public static String fileToString(String pathToFile) throws FileNotFoundException {
        StringBuilder sb = new StringBuilder();

        try (BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(pathToFile))) {

            for (int b; (b = inputStream.read()) != -1;) {
                String s = Integer.toHexString(b).toUpperCase();
                if (s.length() == 1) {
                    sb.append('0');
                }
                sb.append(s).append(' ');
            }

            return sb.toString().replace(" ", "");


        } catch (IOException e) {
            throw new FileNotFoundException();
        }
    }

}
