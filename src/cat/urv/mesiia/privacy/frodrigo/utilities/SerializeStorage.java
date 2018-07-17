package cat.urv.mesiia.privacy.frodrigo.utilities;

import cat.urv.mesiia.privacy.frodrigo.server.Storage;

import java.io.*;

public class SerializeStorage {

    public static void serializeStorage(Storage storage) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("./storage.ser"))) {
            oos.writeObject(storage);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static Storage deserializeStorage() throws Exception {
        try (InputStream file = new FileInputStream("storage.ser");
             InputStream buffer = new BufferedInputStream(file);
             ObjectInput input = new ObjectInputStream(buffer)
        ) {
            return (Storage) input.readObject();
        }
    }

}
