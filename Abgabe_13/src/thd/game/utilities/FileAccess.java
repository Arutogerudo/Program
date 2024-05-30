package thd.game.utilities;

import thd.game.level.Difficulty;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Utility Class for permanent data saving.
 */
public class FileAccess {
    private static final Path WICHTEL_GAME_FILE = Path.of(System.getProperty("user.home")).resolve("wichtelgame.txt");
    private static final Path HIGHSCORE_FILE = Path.of(System.getProperty("user.home")).resolve("highscore.txt");

    /**
     * Updates difficulty to the file.
     * Ends the Programm in case of a fail.
     *
     * @param difficulty difficulty to write to Disk.
     */
    public static void writeDifficultyToDisc(Difficulty difficulty) {
        try {
            Files.writeString(WICHTEL_GAME_FILE, difficulty.name(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Reads the difficulty from the disk.
     *
     * @return read difficulty or Standard in case of an error.
     */
    public static Difficulty readDifficultyFromDisc() {
        try {
            String text = Files.readString(WICHTEL_GAME_FILE, StandardCharsets.UTF_8);
            return Difficulty.valueOf(text);
        } catch (IllegalArgumentException | IOException e) {
            writeDifficultyToDisc(Difficulty.STANDARD);
            return Difficulty.STANDARD;
        }
    }


    /**
     * Updates new highscore to the file.
     * Ends the Programm in case of a fail.
     *
     * @param highscore highscore to write to Disk.
     */
    public static void writeHighscoreToDisc(int highscore) {
        try {
            Files.writeString(HIGHSCORE_FILE, "" + highscore, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Reads the highscore from the disk.
     *
     * @return read highscore or 0 in case of an error.
     */
    public static int readHighscoreFromDisc() {
        try {
            String text = Files.readString(HIGHSCORE_FILE, StandardCharsets.UTF_8);
            return Integer.parseInt(text);
        } catch (IllegalArgumentException | IOException e) {
            return 0;
        }
    }
}
