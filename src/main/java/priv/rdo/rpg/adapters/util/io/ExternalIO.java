package priv.rdo.rpg.adapters.util.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ExternalIO extends IOBase {
    private static final String RESOURCES = "src/main/resources/";

    private ExternalIO() {
    }

    public static ObjectOutputStream objectOutputStream(String basePath, String filename) throws IOException {
        File file = createFileIfDoesNotExist(absolutePath(basePath, filename));
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        return new ObjectOutputStream(fileOutputStream);
    }

    public static void stringToFile(String content, String basePath, String filename) throws IOException {
        File file = createFileIfDoesNotExist(absolutePath(basePath, filename));
        Files.write(Paths.get(file.getPath()), content.getBytes());
    }

    public static ObjectInputStream objectInputStream(String basePath, String filename) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(absolutePath(basePath, filename));
        return new ObjectInputStream(fileInputStream);
    }

    public static String resourcesPath() {
        return RESOURCES;
    }

    protected static String absolutePath(String basePath, String pathToFile) {
        return Paths.get(basePath, pathToFile).toAbsolutePath().toString();
    }

}
