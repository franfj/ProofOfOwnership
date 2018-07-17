package cat.urv.mesiia.privacy.frodrigo.server;

import cat.urv.mesiia.privacy.frodrigo.merkle.MerkleTree;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;
import java.io.Serializable;

public class UploadedFile implements Serializable {

    private String fileHash;
    private String fileString;
    private File file;
    private MerkleTree tree;

    public UploadedFile(File file, String fileString, MerkleTree tree) {
        this.fileHash = DigestUtils.sha256Hex(fileString);
        this.file = file;
        this.fileString = fileString;
        this.tree = tree;
    }

    public String getFileHash() {
        return fileHash;
    }

    public MerkleTree getTree() {
        return tree;
    }

    @Override
    public String toString() {
        return "UploadedFile{" + "fileHash='" + fileHash + '\'' + '}';
    }
}
