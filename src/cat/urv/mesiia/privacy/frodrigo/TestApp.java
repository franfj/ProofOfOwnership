package cat.urv.mesiia.privacy.frodrigo;

import cat.urv.mesiia.privacy.frodrigo.merkle.MerkleTreeBuilder;

import java.io.FileNotFoundException;

/**
 * Created by frodrigo on 27/03/2017.
 */
public class TestApp {

    public static void main(String [ ] args) {

        MerkleTreeBuilder merkleTreeBuilder = null;
        try {
            merkleTreeBuilder = new MerkleTreeBuilder("../../backgroundDefault.jpg", 3, "SHA-256");
        } catch (FileNotFoundException e) {
            return;
        }
        merkleTreeBuilder.buildMerkleTree();

    }

}
