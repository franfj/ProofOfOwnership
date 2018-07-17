package cat.urv.mesiia.privacy.frodrigo.server;

import cat.urv.mesiia.privacy.frodrigo.merkle.MerkleTree;
import cat.urv.mesiia.privacy.frodrigo.merkle.MerkleTreeBuilder;
import cat.urv.mesiia.privacy.frodrigo.utilities.PowType;
import cat.urv.mesiia.privacy.frodrigo.utilities.Tuple;
import cat.urv.mesiia.privacy.frodrigo.utilities.Util;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

public class Server {

    public static void addFileToServer(String path) {
        MerkleTreeBuilder merkleTreeBuilder = new MerkleTreeBuilder(PowType.ERASURE_CODE_BASED, path);
        MerkleTree tree = merkleTreeBuilder.buildMerkleTree();
        UploadedFile newUploadedFile = new UploadedFile(Util.getFile(path), Util.filePathToString(path), tree);

        if (getFile(newUploadedFile.getFileHash()) == null) {
            Storage.getInstance().addFile(newUploadedFile);
        }
    }

    private static UploadedFile getFile(String hash) {
        return Storage.getInstance().getFile(hash);
    }

    private static Map<Integer, String> getSuperLogarithmicLeaves(String hash) {
        Map selectedLeafs = new LinkedHashMap();
        UploadedFile file = getFile(hash);
        Random random = new Random();

        double nLeaves = Math.log(file.getTree().getTree().size()) / Math.log(2) * 20;

        while (selectedLeafs.size() < nLeaves) {
            int randomIndex = Math.abs(random.nextInt()) % file.getTree().getTree().size();
            if (!selectedLeafs.containsKey(randomIndex)) {
                selectedLeafs.put(randomIndex, "");
            }
        }

        return selectedLeafs;
    }

    public static Tuple<Boolean, Map<Integer, String>> isInServer(String hash) {
        if (!Storage.getInstance().checkHashExists(hash)) {
            Map<Integer, String> none = new LinkedHashMap();
            return new Tuple<>(false, none);
        } else {
            return new Tuple<>(true, getSuperLogarithmicLeaves(hash));
        }
    }

    public static UploadedFile validateClientResponse(String hash, Map<Integer, String> leafsFromClient) {
        for (Map.Entry<Integer, String> leaf : leafsFromClient.entrySet()) {
            if (!leaf.getValue().equals(getFile(hash).getTree().getTree().get(leaf.getKey()))) {
                return null;
            }
        }
        return getFile(hash);
    }

}
