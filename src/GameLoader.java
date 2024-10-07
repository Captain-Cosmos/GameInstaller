import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class GameLoader {
    public static void openZip(String zipPath, String extractPath) {
        try (ZipInputStream zin = new ZipInputStream(new FileInputStream(zipPath))) {
            ZipEntry entry;
            String name;
            while ((entry = zin.getNextEntry()) != null) {
                name = entry.getName();
                FileOutputStream fout = new FileOutputStream(new File(extractPath, name));
                for (int c = zin.read(); c != -1; c = zin.read()) {
                    fout.write(c);
                }
                fout.flush();
                zin.closeEntry();
                fout.close();
            }
            System.out.println("Архив распакован в: " + extractPath);
        } catch (IOException e) {
            System.out.println("Ошибка при распаковке архива: " + e.getMessage());
        }
    }

    public static GameProgress openProgress(String savePath) {
        GameProgress gameProgress = null;
        try (FileInputStream fis = new FileInputStream(savePath);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            gameProgress = (GameProgress) ois.readObject();
            System.out.println("Игра загружена из файла: " + savePath);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Ошибка при загрузке игры: " + e.getMessage());
        }
        return gameProgress;
    }
}