package cat.urv.mesiia.privacy.frodrigo.merkle;

import cat.urv.mesiia.privacy.frodrigo.utilities.ErasureEncoder;
import cat.urv.mesiia.privacy.frodrigo.utilities.PowType;
import cat.urv.mesiia.privacy.frodrigo.utilities.Properties;
import cat.urv.mesiia.privacy.frodrigo.utilities.Util;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.ArrayList;
import java.util.List;

public class MerkleTreeBuilder {

    String pathToFile;
    private Integer nLevels;
    private String file;
    private PowType type;

    public MerkleTreeBuilder(PowType type, String pathTofile) {
        this.type = type;
        this.nLevels = Properties.NLEVELS;
        this.pathToFile = pathTofile;
        this.file = Util.filePathToString(pathTofile);
    }

    public void changePowType(PowType type) {
        this.type = type;
    }

    public MerkleTree buildMerkleTree() {
        Integer nLeaves = Properties.NLEAVES;
        Integer nNodes = (int) Math.pow(2, nLevels + 1) - 1;
        Integer slicesLength = file.length() / nLeaves;

        String stringToMerkle = getStringToBeMerkled();
        List<String> tokens = getStringToBeMerkledSplittedEqually(nLeaves, slicesLength, stringToMerkle);
        List<String> treeHashes = buildTreeHashes(nLeaves, nNodes, tokens);

        return new MerkleTree(nLevels, treeHashes);
    }

    private List<String> buildTreeHashes(Integer nLeaves, Integer nNodes, List<String> tokens) {
        List<String> treeHashes = new ArrayList<>();

        for (String node : tokens) {
            switch (Properties.ALGO) {
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
        for (int i = 0; i < nNodes - nLeaves; i++) {
            switch (Properties.ALGO) {
                case "MD5":
                    treeHashes.add(DigestUtils.md5Hex(treeHashes.get(j) + treeHashes.get(j + 1)));
                    break;
                case "SHA-256":
                    treeHashes.add(DigestUtils.sha256Hex(treeHashes.get(j) + treeHashes.get(j + 1)));
                    break;
                default:
                    treeHashes.add(DigestUtils.sha512Hex(treeHashes.get(j) + treeHashes.get(j + 1)));
                    break;
            }

            j += 2;
        }
        return treeHashes;
    }

    private List<String> getStringToBeMerkledSplittedEqually(Integer nLeaves, Integer slicesLength, String stringToMerkle) {
        List<String> tokens = new ArrayList<>();

        int startIndex = 0;
        for (int i = 0; i < nLeaves; i++) {
            if (i != nLeaves - 1) {
                tokens.add(stringToMerkle.substring(startIndex, startIndex + slicesLength));
                startIndex += slicesLength;
            } else {
                tokens.add(stringToMerkle.substring(startIndex, stringToMerkle.length()));
            }
        }
        return tokens;
    }

    private String getStringToBeMerkled() {
        switch (type) {
            case ERASURE_CODE_BASED:
                return ErasureEncoder.encodeFile(pathToFile);

            case REDUCTION_BUFFER_BASED:
                return "";

            case STREAMING_PROTOCOL_BASED:
                return "";

            default:
                return file;
        }
    }
}
