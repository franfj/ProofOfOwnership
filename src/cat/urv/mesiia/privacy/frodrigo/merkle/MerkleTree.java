package cat.urv.mesiia.privacy.frodrigo.merkle;

import java.util.List;

/**
 * Created by frodrigo on 27/03/2017.
 */
public class MerkleTree {

    private Integer nLevels;
    private List<String> tree;

    public MerkleTree(Integer nLevels, List<String> tree) {
        this.nLevels = nLevels;
        this.tree = tree;
    }
}
