package cat.urv.mesiia.privacy.frodrigo.server;

import java.util.List;

/**
 * Created by frodrigo on 27/03/2017.
 */
public class Storage {
    private static Storage instance = null;

    private List<UploadedFile> files;
    private Storage() {}

    public static Storage getInstance() {
        if(instance == null) {
            instance = new Storage();
        }

        return instance;
    }

    public void addFile(UploadedFile file){
        files.add(file);
    }

    public boolean checkHashExists(String hash){
        for (UploadedFile file: files) {
            if(file.getFileHash().equals(hash)){
                return true;
            }
        }
        return false;
    }


}