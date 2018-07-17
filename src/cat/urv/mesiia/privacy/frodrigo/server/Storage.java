package cat.urv.mesiia.privacy.frodrigo.server;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class Storage implements Serializable {
    private static Storage instance = null;

    private List<UploadedFile> files = new LinkedList<>();

    private Storage() {
    }

    public static Storage getInstance() {
        if (instance == null) {
            instance = new Storage();
        }

        return instance;
    }

    public static void setInstance(Storage storage) {
        instance = storage;
    }

    public void addFile(UploadedFile file) {
        files.add(file);
    }

    public boolean checkHashExists(String hash) {
        if (!files.isEmpty()) {
            for (UploadedFile file : files) {
                if (file.getFileHash().equals(hash)) {
                    return true;
                }
            }
        }
        return false;
    }

    public UploadedFile getFile(String hash) {
        if (!files.isEmpty()) {
            for (UploadedFile file : files) {
                if (file.getFileHash().equals(hash)) {
                    return file;
                }
            }
        }
        return null;
    }

    public List<UploadedFile> getFiles() {
        return files;
    }

    @Override
    public String toString() {
        return "Storage{" + "files=" + files + '}';
    }
}