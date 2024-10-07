import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String gamesPath = "C://Games/";
        StringBuilder log = new StringBuilder();


        createDirectory(gamesPath + "src", log);
        createDirectory(gamesPath + "res", log);
        createDirectory(gamesPath + "savegames", log);
        createDirectory(gamesPath + "temp", log);
        createDirectory(gamesPath + "src/main", log);
        createDirectory(gamesPath + "src/test", log);
        createFile(gamesPath + "src/main/Main.java", log);
        createFile(gamesPath + "src/main/Utils.java", log);
        createDirectory(gamesPath + "res/drawables", log);
        createDirectory(gamesPath + "res/vectors", log);
        createDirectory(gamesPath + "res/icons", log);
        createFile(gamesPath + "temp/temp.txt", log);

        try (FileWriter writer = new FileWriter(gamesPath + "temp/temp.txt")) {
            writer.write(log.toString());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }


        GameProgress game1 = new GameProgress(100, 2, 10, 254.32);
        GameProgress game2 = new GameProgress(80, 3, 12, 500.85);
        GameProgress game3 = new GameProgress(50, 1, 5, 150.0);

        GameSaver.saveGame(gamesPath + "savegames/save1.dat", game1);
        GameSaver.saveGame(gamesPath + "savegames/save2.dat", game2);
        GameSaver.saveGame(gamesPath + "savegames/save3.dat", game3);

        List<String> savedFiles = new ArrayList<>();
        savedFiles.add(gamesPath + "savegames/save1.dat");
        savedFiles.add(gamesPath + "savegames/save2.dat");
        savedFiles.add(gamesPath + "savegames/save3.dat");

        GameSaver.zipFiles(gamesPath + "savegames/zip.zip", savedFiles);

        for (String filePath : savedFiles) {
            File file = new File(filePath);
            if (file.delete()) {
                System.out.println("Файл " + filePath + " удален");
            }
        }


        GameLoader.openZip(gamesPath + "savegames/zip.zip", gamesPath + "savegames");
        GameProgress loadedGame = GameLoader.openProgress(gamesPath + "savegames/save2.dat");
        System.out.println(loadedGame);
    }

    public static void createDirectory(String path, StringBuilder log) {
        File dir = new File(path);
        if (dir.mkdir()) {
            log.append("Директория ").append(path).append(" создана\n");
        } else {
            log.append("Директория ").append(path).append(" не создана\n");
        }
    }

    public static void createFile(String path, StringBuilder log) {
        File file = new File(path);
        try {
            if (file.createNewFile()) {
                log.append("Файл ").append(path).append(" создан\n");
            } else {
                log.append("Файл ").append(path).append(" не создан\n");
            }
        } catch (IOException e) {
            log.append("Ошибка при создании файла ").append(path).append(": ").append(e.getMessage()).append("\n");
        }
    }
}