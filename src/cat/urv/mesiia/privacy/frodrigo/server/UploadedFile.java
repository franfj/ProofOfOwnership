package cat.urv.mesiia.privacy.frodrigo.server;

import cat.urv.mesiia.privacy.frodrigo.merkle.MerkleTree;

/**
 * Created by frodrigo on 27/03/2017.
 */
public class UploadedFile {

    private String fileHash;
    private String file;
    private MerkleTree tree;

    public UploadedFile(String fileHash, String file, MerkleTree tree) {
        this.fileHash = fileHash;
        this.file = file;
        this.tree = tree;
    }

    public String getFileHash() {
        return fileHash;
    }

    public void setFileHash(String fileHash) {
        this.fileHash = fileHash;
    }
}
