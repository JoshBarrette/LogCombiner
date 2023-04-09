import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;

// Log zip URL format: "http://logs.tf/logs/log_" + logID + ".log.zip"

public class LogFileHandler {
    private static void downloadFile(String id) throws IOException {
        InputStream in = new URL("http://logs.tf/logs/log_" + id + ".log.zip").openStream();
        Files.copy(in, Paths.get("./temp/" + id + ".zip"), StandardCopyOption.REPLACE_EXISTING);
    }

    private static void downloadFiles(String[] ids) throws IOException {
        for (String id : ids) {
            downloadFile(id);
        }
    }

    private static void unzipFile(String id) throws ZipException {
        String source = "./temp/" + id + ".zip";
        String destination = "./temp/";

        ZipFile zipFile = new ZipFile(source);
        zipFile.extractAll(destination);
    }

    private static void unzipFiles(String[] ids) throws ZipException {
        for (String id : ids) {
            unzipFile(id);
        }
    }

    private static void deleteLog(String id) {
        File logZip = new File("./temp/" + id + ".zip");
        File logFile = new File("./temp/log_" + id + ".log");
        logZip.delete();
        logFile.delete();
    }

    private static void deleteLogs(String[] ids) {
        for (String id : ids) {
            deleteLog(id);
        }
    }

    public static void deleteFolder(String name) {
        File folder = new File(name);
        File[] filesInFolder = folder.listFiles();
        if (filesInFolder != null) {
            for (File f : filesInFolder) {
                f.delete();
            }
        }
        folder.delete();
    }

    public static void deleteFile(String name) {
        File fileToDelete = new File("/temp/" + name);
        fileToDelete.delete();
    }

    private static void combineFiles(String[] ids, String name) throws IOException {
        FileWriter combinedLogs = new FileWriter("./temp/" + name +  ".log");
        Scanner reader;
        for (String id : ids) {
            reader = new Scanner(new File("./temp/log_" + id + ".log"));
            while (reader.hasNext()) {
                combinedLogs.write(reader.nextLine() + "\n");
            }
            reader.close();
        }
        combinedLogs.close();
    }

    public static File combine(String[] ids, String name) throws IOException {
        File temp = new File("./temp");
        if (!temp.exists()) {
            temp.mkdir();
        }

        downloadFiles(ids);

        unzipFiles(ids);

        combineFiles(ids, name);

        return new File("./temp/" + name + ".log");
    }
}
