import org.vosk.LibVosk;
import org.vosk.LogLevel;
import org.vosk.Model;
import org.vosk.Recognizer;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Main {
    public static void main(String[] args) throws IOException, UnsupportedAudioFileException {
        LibVosk.setLogLevel(LogLevel.DEBUG);

        try (Model model = new Model("vosk-model-small-ru-0.22");
             InputStream ais = AudioSystem.getAudioInputStream(new BufferedInputStream(new FileInputStream("audio.m4a")));
             Recognizer recognizer = new Recognizer(model, 16000)) {

            int nbytes;
            byte[] b = new byte[4096];
            while ((nbytes = ais.read(b)) >= 0) {
                if (recognizer.acceptWaveForm(b, nbytes)) {
                    System.out.println(recognizer.getResult());
                }
            }

            System.out.println(recognizer.getFinalResult());
        }
    }
}
