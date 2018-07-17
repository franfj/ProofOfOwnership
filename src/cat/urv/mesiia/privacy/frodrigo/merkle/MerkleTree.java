package cat.urv.mesiia.privacy.frodrigo.merkle;

import java.io.Serializable;
import java.util.List;

public class MerkleTree implements Serializable {

    private Integer nLevels;
    private List<String> tree;

    MerkleTree(Integer nLevels, List<String> tree) {
        this.nLevels = nLevels;
        this.tree = tree;
    }

    public void printTree() {
        Integer nLeaves = (int) Math.pow(2, nLevels - 1);

        int i = 0;
        while (i < tree.size()) {
            for (int j = 0; j < nLeaves; ++j) {
                System.out.print("[ " + tree.get(i) + " ]");
                if (j != nLeaves - 1) {
                    System.out.print(" - ");
                }
                i++;
            }
            System.out.println();
            nLeaves /= 2;
        }
    }

    public List<String> getTree() {
        return tree;
    }
}
