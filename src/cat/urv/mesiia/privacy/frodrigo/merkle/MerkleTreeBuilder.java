package cat.urv.mesiia.privacy.frodrigo.merkle;

import cat.urv.mesiia.privacy.frodrigo.utilities.Util;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by frodrigo on 27/03/2017.
 */
public class MerkleTreeBuilder {

    private Integer nLevels;
    private String algorithm;
    private String file;

    public MerkleTreeBuilder(String pathTofile, Integer nLevels, String algorithm) throws FileNotFoundException {
        this.nLevels = nLevels;
        this.algorithm = algorithm;
        this.file = Util.fileToString(pathTofile);
    }

    public MerkleTree buildMerkleTree(){
        Integer nLeafs = (int) Math.pow(2, (nLevels - 1));
        Integer nNodes = (int) Math.pow(2, nLevels) - 1;
        Integer slicesLength = file.length() / nLeafs;

        List<String> tokens = new ArrayList<>();

        int startIndex = 0;

        for(int i = 0; i < nLeafs; i++){
            if(i != nLeafs - 1) {
                tokens.add(file.substring(startIndex, startIndex + slicesLength));
                startIndex += slicesLength;
            }else{
                tokens.add(file.substring(startIndex, file.length()));
            }
        }

        List<String> treeHashes = new ArrayList<>();

        for (String node: tokens) {
            switch (algorithm) {
                case "MD5":
                    treeHashes.add(DigestUtils.md5Hex(node));
                    break;
                case "SHA-256":
                    treeHashes.add(DigestUtils.sha256Hex(node));
                    break;
                default:
                    treeHashes.add(DigestUtils.sha512Hex(node));
                    break;
            }
        }

        int j = 0;
        for(int i = 0; i < nNodes - nLeafs; i++){
            switch (algorithm) {
                case "MD5":
                    treeHashes.add(DigestUtils.md5Hex(treeHashes.get(j) + treeHashes.get(j+1)));
                    break;
                case "SHA-256":
                    treeHashes.add(DigestUtils.sha256Hex(treeHashes.get(j) + treeHashes.get(j+1)));
                    break;
                default:
                    treeHashes.add(DigestUtils.sha512Hex(treeHashes.get(j) + treeHashes.get(j+1)));
                    break;
            }

            j += 2;
        }

        return new MerkleTree(nLevels, treeHashes);
    }
}
