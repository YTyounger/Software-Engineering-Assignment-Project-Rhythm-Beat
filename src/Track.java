import javax.sound.sampled.*;

/**
 * Class for managing the music being played in the background
 */
public class Track extends Thread {
    private Clip clip;

    public Track(String filePath) {
        try {
            AudioInputStream original = AudioSystem.getAudioInputStream(new java.io.File(filePath));
            AudioFormat srcFmt = original.getFormat();

            AudioFormat targetFmt = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED,
                    srcFmt.getSampleRate(),
                    16,
                    srcFmt.getChannels(),
                    srcFmt.getChannels() * 2,
                    srcFmt.getSampleRate(),
                    false);

            AudioInputStream playableStream = (AudioSystem.isConversionSupported(targetFmt, srcFmt))
                    ? AudioSystem.getAudioInputStream(targetFmt, original)
                    : original;

            clip = (Clip) AudioSystem.getLine(new DataLine.Info(Clip.class, targetFmt));
            clip.open(playableStream);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void pause() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    @Override
    public void run() {
        if (clip != null) {
            clip.start();
        }
    }
}