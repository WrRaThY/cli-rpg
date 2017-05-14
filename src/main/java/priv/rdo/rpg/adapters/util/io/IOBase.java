package priv.rdo.rpg.adapters.util.io;

import java.io.File;
import java.io.IOException;

public abstract class IOBase {

    protected static File createFileIfDoesNotExist(String absolutePath) throws IOException {
        File file = new File(absolutePath);
        if (file.exists()) {
            return file;
        }

        file.getParentFile().mkdirs();
        file.createNewFile();
        return file;
    }
}
