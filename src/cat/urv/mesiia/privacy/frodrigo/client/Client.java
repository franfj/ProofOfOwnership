package cat.urv.mesiia.privacy.frodrigo.client;

import cat.urv.mesiia.privacy.frodrigo.merkle.MerkleTree;
import cat.urv.mesiia.privacy.frodrigo.merkle.MerkleTreeBuilder;
import cat.urv.mesiia.privacy.frodrigo.server.Server;
import cat.urv.mesiia.privacy.frodrigo.utilities.PowType;
import cat.urv.mesiia.privacy.frodrigo.utilities.Properties;
import cat.urv.mesiia.privacy.frodrigo.utilities.Tuple;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Client {

    public static void run(List<String> args) {
        String pathToFile = args.get(0);
        String hashcode = args.get(1);

        Tuple<Boolean, Map<Integer, String>> serverResponse = Server.isInServer(hashcode);

        if (serverResponse.getFirst()) {
            Long beginTime = System.currentTimeMillis();

            System.out.println("The file exists on the server, entering PoW protocol");

            MerkleTreeBuilder merkleTreeBuilder = new MerkleTreeBuilder(PowType.ERASURE_CODE_BASED, pathToFile);
            MerkleTree tree = merkleTreeBuilder.buildMerkleTree();

            Map<Integer, String> leafsToReply = (LinkedHashMap) serverResponse.getSecond();

            for (Map.Entry<Integer, String> leaf : leafsToReply.entrySet()) {
                leafsToReply.put(leaf.getKey(), tree.getTree().get(leaf.getKey()));
            }

            System.out.println(Server.validateClientResponse(hashcode, leafsToReply));

            System.out.println("Protocol time (ms.): " + Long.toString((System.currentTimeMillis() - beginTime)));

        } else {
            System.out.println("The file doesn't exists on the server, it will be uploaded");

            Server.addFileToServer(pathToFile);

            System.out.println("File uploaded to server!");
        }

    }

}

