import java.awt.FileDialog;
import java.awt.Frame;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class MusicPlayer {
    private static Clip clip;

    public static void main(String[] args) {
        FileDialog fileDialog = new FileDialog((Frame)null, "Select an audio file");
        fileDialog.setFile("*.mp3;*.wav;*.ogg");
        fileDialog.setVisible(true);
        String file = fileDialog.getFile();
        if (file != null) {
            String filePath = fileDialog.getDirectory() + file;
            playMusic(filePath);
        }

        while (true) {
            System.out.println("1. Select a file");
            System.out.println("2. Stop music");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            String choice = System.console().readLine();

            if (choice.equals("1")) {
                fileDialog.setVisible(true);
                file = fileDialog.getFile();
                if (file != null) {
                    String filePath = fileDialog.getDirectory() + file;
                    playMusic(filePath);
                }
            } else if (choice.equals("2")) {
                stopMusic();
            } else if (choice.equals("3")) {
                break;
            }
        }

        if (clip != null) {
            clip.close();
        }
    }

    private static void playMusic(String filePath) {
        try {
            File musicFile = new File(filePath);
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicFile);
            clip = AudioSystem.getClip();
            clip.open(audioInput);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void stopMusic() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            clip.flush();
            clip.setFramePosition(0);
        }
    }
}
