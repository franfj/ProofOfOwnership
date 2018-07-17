package cat.urv.mesiia.privacy.frodrigo.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ErasureEncoder {

    private static final int DATA_SHARDS = Properties.NDATASHARDS;
    private static final int TOTAL_SHARDS = Properties.NLEAVES;
    private static final int BYTES_IN_INT = 4;

    private ErasureEncoder() {

    }

    public static String encodeFile(String pathToFile) {
        File inputFile = new File(pathToFile);

        final int fileSize = (int) inputFile.length();
        final int storedSize = fileSize + BYTES_IN_INT;
        final int shardSize = (storedSize + DATA_SHARDS - 1) / DATA_SHARDS;
        final int bufferSize = shardSize * DATA_SHARDS;
        final byte[] allBytes = new byte[bufferSize];

        ByteBuffer.wrap(allBytes).putInt(fileSize);

        try {
            int bytesRead;
            try (InputStream in = new FileInputStream(inputFile)) {
                bytesRead = in.read(allBytes, BYTES_IN_INT, fileSize);
                in.close();
            }

            if (bytesRead != fileSize) {
                throw new IOException("Not enough bytes read");
            }

        } catch (IOException e) {
            Logger logger = Logger.getAnonymousLogger();
            logger.log(Level.SEVERE, "Error reading file while encoding.", e);
        }

        // Make the buffers to hold the shards.
        byte[][] shards = new byte[TOTAL_SHARDS][shardSize];

        // Fill in the data shards
        for (int i = 0; i < DATA_SHARDS; i++) {
            System.arraycopy(allBytes, i * shardSize, shards[i], 0, shardSize);
        }

        byte[] bytes = new byte[shards.length * shards[0].length];
        for (int i = 0; i < shards.length; i++) {
            byte[] row = shards[i];
            System.arraycopy(shards[i], 0, bytes, i * row.length, row.length);
        }

        String hexDump = Util.bytesToHex(bytes);
        byte[] qwe = Util.hexStringToByteArray(hexDump);

        return Util.bytesToHex(bytes);
    }

}
