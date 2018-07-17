package cat.urv.mesiia.privacy.frodrigo;

import cat.urv.mesiia.privacy.frodrigo.client.Client;
import cat.urv.mesiia.privacy.frodrigo.server.Server;
import cat.urv.mesiia.privacy.frodrigo.server.Storage;
import cat.urv.mesiia.privacy.frodrigo.server.UploadedFile;
import cat.urv.mesiia.privacy.frodrigo.utilities.Properties;
import cat.urv.mesiia.privacy.frodrigo.utilities.SerializeStorage;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;


public class App {
    //args[0] pathToFile
    //args[1] hashcode
    public static void main(String[] arg) throws IOException {

        Properties.readProperties();

        List<String> args = new LinkedList();

        args.add("./files/filesM.r04");
        args.add("ec94329254874b90dbb96c860e2eca4786da9867a4657b49b0807cfb28b57229");

        Server.addFileToServer(args.get(0));

        try {
            Storage.setInstance(SerializeStorage.deserializeStorage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(Storage.getInstance().toString());

//        Client.run(Arrays.asList(arg));
        Client.run(args);

        SerializeStorage.serializeStorage(Storage.getInstance());

    }

}
