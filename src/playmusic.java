import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

/**
* fuer Musik. Es ist ein Thread, welche hundertmal dieselbe Musik abspielt.
 */
public class playmusic extends Thread{
    /**
     * Im start wird das Musik gestartet, und dann fuer so viel wartet es, bis es ab ist. Dann wird
     * es wieder abgespielt (100 mal).
     */
    @Override
    public void start() {
        int i = 100;
        while(i!=0){
        music();
            try {
                sleep(212000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            i--;
        }
    }
    /**
    * Oeffnet 'music.vaw' und startet es.
     */
    public void music() {
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File("src/music.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

